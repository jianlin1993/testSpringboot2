package com.wxy.wjl.testspringboot2.job.service;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.job.dal.dao.OpsJobInfMapper;
import com.wxy.wjl.testspringboot2.job.dal.dao.OpsJobJnlMapper;
import com.wxy.wjl.testspringboot2.job.dal.entity.OpsJobInfDO;
import com.wxy.wjl.testspringboot2.job.utils.OpsUtil;
import com.wxy.wjl.testspringboot2.job.utils.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 定时服务
 *
 * @author xu_lw
 * @version 5.0.0
 * created  at 2020-02-04 09:08
 * copyright @2018 北京沐融信息科技股份有限公司
 */
@Slf4j
@Service
public class SchedulerService implements InitializingBean, DisposableBean, Runnable {
	@Value("${ecp.ops.quartzThreadSize:20}")
	private int threadSize;

	@Autowired
	private Environment env;

	@Autowired
	private OpsJobInfMapper jobInfMapper;

	@Autowired
	private OpsJobJnlMapper jobJnlMapper;
	@Autowired
	SchLockInfService schLockInfService;

	private Scheduler scheduler;

	private Map<String, JobKeyInfo> jobKeyMap = new HashMap<String, JobKeyInfo>();

	private Thread thread = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		SystemService.setMaster(false);
		String enableJob= System.getProperty("enableJob");
		if(StringUtils.equalsIgnoreCase(enableJob,"false")){
			log.info("Do not start scheduled job!");
			return;
		}
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void destroy() throws Exception {
		if (scheduler != null) {
			scheduler.shutdown();
		}
	}

	public void addJob(OpsJobInfDO jobInfDO) throws SchedulerException {
		JobKeyInfo jobKeyInfo = jobKeyMap.get(jobInfDO.getName());
		// 如果此定时任务已删除，但仍在运行列表中，则删除此定时任务
		if (jobKeyMap.containsKey(jobInfDO.getName()) && StringUtils.equals("1", jobInfDO.getDeleteFlag())) {
			scheduler.deleteJob(jobKeyInfo.jobKey);
			jobKeyMap.remove(jobInfDO.getName());
			return;
		}

		if( jobKeyInfo != null ){
			if( "0".equals(jobInfDO.getStatus()) ) {
				scheduler.deleteJob(jobKeyInfo.jobKey);
				jobKeyMap.remove(jobInfDO.getName());
				return;
			} else {
				if (StringUtils.equals(jobKeyInfo.cron, jobInfDO.getCron())
						&& StringUtils.equals(jobInfDO.getUrl(), jobKeyInfo.url)
						&& jobInfDO.getTmOut().equals(jobKeyInfo.tm_out)) {
					return;
				} else {
					scheduler.deleteJob(jobKeyInfo.jobKey);
					jobKeyMap.remove(jobInfDO.getName());
					log.info("------->update jobinf，old cron：" + jobKeyInfo.cron + "，old tm_out:" + jobKeyInfo.tm_out + "，old url：" + jobKeyInfo.url);
				}
			}

		}

		if( "0".equals(jobInfDO.getStatus()) ) {
			return;
		}

		if (StringUtils.isBlank(jobInfDO.getCron())) {
			return;
		}

		JobDetail jb = JobBuilder.newJob(SchedulerJob.class)
				.withDescription(jobInfDO.getName())
				.withIdentity(jobInfDO.getName(), "ECP-Job")
				.build();

		JobDataMap jobDataMap = jb.getJobDataMap();

		jobDataMap.put("jobInfDO", jobInfDO);
		jobDataMap.put("jobJnlMapper", jobJnlMapper);
		jobDataMap.put("jobInfMapper", jobInfMapper);
		jobDataMap.put("env", env);

		// 当前时间 + 3秒
		long time = System.currentTimeMillis() + 3 * 1000L;
		Date statTime = new Date(time);
		// 触发器：简单的讲就是调度作业，什么时候开始执行，什么时候结束执行
		Trigger t = TriggerBuilder.newTrigger()
				.withDescription(jobInfDO.getName() + "Trigger")
				.withIdentity(jobInfDO.getName() + "Trigger", "ecp-TriggerGroup")
				.startAt(statTime)
				.withSchedule(CronScheduleBuilder.cronSchedule(jobInfDO.getCron()))
				.build();

		scheduler.scheduleJob(jb, t);

		jobKeyMap.put(jobInfDO.getName(), new JobKeyInfo(jb.getKey(), jobInfDO.getCron(), jobInfDO.getUrl(), jobInfDO.getTmOut()));

		log.info("add job:" + jobInfDO.getName() + ", cron:" + jobInfDO.getCron());
	}

	static class JobKeyInfo {
		JobKey jobKey;
		String cron;
		String url;
		Long tm_out;

		public JobKeyInfo(JobKey jobKey, String cron, String url, Long tm_out) {
			this.jobKey = jobKey;
			this.cron = cron;
			this.url = url;
			this.tm_out = tm_out;
		}
	}

	@Override
	public void run() {
		OpsUtil.sleepQuietly(10);
		while (true) {
			// 每隔10秒执行一次
			OpsUtil.sleepQuietly(10);
			try {
				doRun();
			} catch (Exception e) {
				log.info(e.getMessage());
			} finally {
				//bizCtx.getDataBaseUtil().closeAll();
			}
		}
	}

	public void doRun() {
		try {
			boolean flag = schLockInfService.tryLock( "ecp-job");
			SystemService.setMaster(flag);

			if (!flag) {
				log.info("can't obtain ops-job lock");
				if (scheduler != null) {
					scheduler.shutdown();
					scheduler = null;
				}
			} else {
				log.info("obtain ops-job lock");
				if (scheduler == null) {
					// 20200716 依据版本同步修改
					Properties prop = new Properties();
					prop.put("org.quartz.scheduler.instanceName", "ECPScheduler");
					prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
					prop.put("org.quartz.threadPool.threadCount", String.valueOf(threadSize));
					prop.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
					StdSchedulerFactory fact = new StdSchedulerFactory();
					fact.initialize(prop);
					scheduler = fact.getScheduler();
					//scheduler = StdSchedulerFactory.getDefaultScheduler();
					scheduler.start();
				}
				List<OpsJobInfDO> jobInfDOList = jobInfMapper.selectAll();
				System.out.println(JSON.toJSONString(jobInfDOList));
				String ids = "";
				int i = 0;
				for (OpsJobInfDO jobInfDO : jobInfDOList) {
					addJob(jobInfDO);
					// 1：已删除
					if (StringUtils.equals("1", jobInfDO.getDeleteFlag())) {
						ids += jobInfDO.getId() + ",";
						i++;
					}
				}
				// 物理删除这些已经删除的定时任务，由于库表限制name值唯一，所以这里必须要添加这个物理删除操作
/*				if (!StringUtils.equals("", ids)) {
					ids = ids.substring(0, ids.length() - 1);
					String sql = "delete from T_OPS_JOBINF where id in (" + ids + ") and delete_flag='1'";

					// 执行sql，从库表物理删除数据
					int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql);
					if (ret != i) {
						log.info("---->delete error，should delete：" + i + "，actually：" + ret + "！");
					}
				}*/
			}
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
	}
}

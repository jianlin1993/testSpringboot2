package com.wxy.wjl.testspringboot2.job.service;

import com.alibaba.fastjson.JSONObject;

import com.wxy.wjl.testspringboot2.job.dal.dao.OpsJobInfMapper;
import com.wxy.wjl.testspringboot2.job.dal.dao.OpsJobJnlMapper;
import com.wxy.wjl.testspringboot2.job.dal.entity.OpsJobInfDO;
import com.wxy.wjl.testspringboot2.job.dal.entity.OpsJobJnlDO;
import com.wxy.wjl.testspringboot2.job.utils.MrSnowflakeKeyGenerator;
import com.wxy.wjl.testspringboot2.job.utils.SystemService;
import com.wxy.wjl.testspringboot2.job.utils.UrlUtil;
import com.wxy.wjl.testspringboot2.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时服务
 *
 * @author xu_lw
 * @version 5.0.0
 * created  at 2020-02-04 09:08
 * copyright @2018 北京沐融信息科技股份有限公司
 */
@Slf4j
public class SchedulerJob implements Job {
	static Map jobs = new HashMap();

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		if(!SystemService.isMaster()){
		    log.info("no master!");
		    return;
        }

		OpsJobInfDO jobInfDO = (OpsJobInfDO) jobExecutionContext.getMergedJobDataMap().get("jobInfDO");
		if( jobs.containsKey(jobInfDO.getName())) {
			log.info("job name:" + jobInfDO.getName() + " processing, skip");
			return;
		}

		try {
			jobs.put(jobInfDO.getName(), jobInfDO);
			doExecute(jobExecutionContext);
		} catch (Throwable t) {
			log.error(t.getMessage());
		} finally {
			jobs.remove(jobInfDO.getName());
			//bizCtx.getDataBaseUtil().closeAll();
		}
	}

	public void doExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		OpsJobInfDO jobInfDO = (OpsJobInfDO) jobExecutionContext.getMergedJobDataMap().get("jobInfDO");
		OpsJobJnlMapper jobJnlMapper = (OpsJobJnlMapper) jobExecutionContext.getMergedJobDataMap().get("jobJnlMapper");
		OpsJobInfMapper jobInfMapper = (OpsJobInfMapper) jobExecutionContext.getMergedJobDataMap().get("jobInfMapper");
		Environment env = (Environment) jobExecutionContext.getMergedJobDataMap().get("env");

		// http 执行
		//
		// http invoke
		//

		jobInfDO = jobInfMapper.selectByName(jobInfDO.getName());
		if( jobInfDO == null ) {
			log.info("have a job not found");
			return;
		}else if(StringUtils.equals("1",jobInfDO.getDeleteFlag())){
			log.info("job name :"+jobInfDO.getName() + "has been deleted!");
			return;
		}else if(StringUtils.equals("0",jobInfDO.getStatus())){
			log.info("job name :"+jobInfDO.getName() + "has been stoped!");
			return;
		}

		OpsJobJnlDO jobJnlDO = new OpsJobJnlDO();

		BeanUtils.copyProperties(jobInfDO, jobJnlDO);

		jobJnlDO.setId(MrSnowflakeKeyGenerator.nextId());
		jobJnlDO.setBegTm(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		jobJnlDO.setStatus("I");
//		System.out.println(JSON.toJSONString(jobJnlDO));
		jobJnlMapper.insertSelective(jobJnlDO);

		try {
			// modified by liao_rh @2020年4月9日
			Map map = UrlUtil.getParameter(jobInfDO.getUrl());
			String url = processUrl( jobInfDO.getUrl(), env);
			url = url.split("[?]")[0];
			JSONObject parm=new JSONObject(map);
			//String data = YGHttpClient.get(url, map, jobInfDO.getTmOut().intValue());
			String data = HttpUtils.doPostTimeOut(parm.toJSONString(), url, jobInfDO.getTmOut().intValue());
			log.info("invoke url:" + url + ", response:" + data);
			JSONObject jsonObject = JSONObject.parseObject(data);
			JSONObject gdaObject = jsonObject.getJSONObject("gda");
			if( gdaObject != null ) {
				data = gdaObject.toJSONString();
			}
			jobJnlDO.setResultMsg(data);
			jobJnlDO.setStatus("S");
		} catch (Exception e) {
			log.info("invoke url:" + jobInfDO.getUrl() + " failure", e);
			jobJnlDO.setStatus("F");
			jobJnlDO.setResultMsg(e.toString());
		}

		jobJnlDO.setEndTm(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));

		jobJnlMapper.updateByPrimaryKeySelective(jobJnlDO);
		return;
	}

	private String processUrl(String url, Environment env) {

		StringBuffer buf = new StringBuffer();
		int offset = 0;
		while( true ) {
			int idx1 = url.indexOf("${", offset);
			if( idx1 == -1 ) {
				break;
			}
			int idx2 = url.indexOf("}", idx1 + 2);
			if( idx2 == -1 ) {
				break;
			}
			String name = url.substring(idx1+2, idx2);
			String value = env.getProperty(name);

			buf.append(url.substring(offset, idx1));
			if( value != null ) {
				buf.append(value);
			} else {
				buf.append("${" + name + "}");
			}

			offset = idx2 + 1;
		}
		buf.append(url.substring(offset));
		return buf.toString();

	}
}

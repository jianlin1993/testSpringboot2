package com.wxy.wjl.testspringboot2.job.service;

import com.wxy.wjl.testspringboot2.job.dal.dao.OpsLockInfMapper;
import com.wxy.wjl.testspringboot2.job.dal.entity.OpsLockInfDO;
import com.wxy.wjl.testspringboot2.job.utils.OpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;

/**
 * xxx
 *
 * @author xu_lw
 * @version 5.0.0
 * created  at 2020-02-04 09:31
 * copyright @2018 北京沐融信息科技股份有限公司
 */
@Slf4j
@Service
public class SchLockInfService {

	@Autowired
	OpsLockInfMapper opsLockInfMapper;


	public boolean tryLock( String name) throws Exception, ParseException {
		//
		String nodeId = OpsUtil.getNodeId();

		HashMap rec = null;
		while (true) {
			OpsLockInfDO opsLockInfDO=new OpsLockInfDO();
			opsLockInfDO.setName(name);
			rec=opsLockInfMapper.selectByPrimaryKeyForUpdate(opsLockInfDO);
			if (rec == null || rec.isEmpty()) {
				//sql = "insert into t_ops_lockinf(name,upd_opr,owner,lock_time) values(?, ?, ?, ?)";
				try {
					// modified by Leo @2020年3月21日   System.currentTimeMillis() -> getDatabaseGMTTime(bizCtx)
					long time = getDatabaseGMTTime();
					opsLockInfDO.setName(name);
					opsLockInfDO.setUpdOpr("SYS");
					opsLockInfDO.setOwner(nodeId);
					opsLockInfDO.setLockTime(String.valueOf(time));
					opsLockInfDO.setTmSmp(getDatabaseTime());
					opsLockInfDO.setNodId(nodeId);
					opsLockInfDO.setReqId("req");

					//int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, name, "SYS", nodeId, String.valueOf(time));
					int ret=opsLockInfMapper.insert(opsLockInfDO);
					log.info("ret:" + ret);
				} catch (Exception e) {
					log.error(e.toString(), e);
					return false;
				}
				continue;
			} else {
				break;
			}
		}

		long lockTime = NumberUtils.toLong((String) rec.get("lock_time"));
		String owner = (String) rec.get("owner");

		//String tmp = getDatabaseGMTTime();
		long time = getDatabaseGMTTime();
		if (time - lockTime > 120 * 1000) {
			log.info("Time out more than 120S, will try get schedule lock");
			// 超过1200s则获取锁 ;  modified by Leo @2020年3月21日   System.currentTimeMillis() -> getDatabaseGMTTime(bizCtx)
			// 锁sql修改 @2020年7月8日 update t_ops_lockinf set lock_time =? where name=? and owner=? ->
			lockTime = getDatabaseGMTTime();
			//sql = "update t_ops_lockinf set lock_time =?, owner=?, tm_smp=? where name=?";
			try {
				//int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, String.valueOf(lockTime), nodeId, DateUtil.getTmSmp(), name);
				OpsLockInfDO opsLockInfDO=new OpsLockInfDO();
				opsLockInfDO.setLockTime( String.valueOf(lockTime));
				opsLockInfDO.setOwner(nodeId);
				opsLockInfDO.setTmSmp(getDatabaseTime());
				opsLockInfDO.setName(name);
				int ret =opsLockInfMapper.updateByPrimaryKey(opsLockInfDO);
				log.info("ret:" + ret);
				if (ret != 1) {
					log.warn("Time out! Snatch schedule lock failed");
					return false;
				}
			} catch (Exception e) {
				log.error(e.toString(), e);
				return false;
			}
			return true;
		} else {
			if (StringUtils.equals(owner, nodeId)) {
				// modified by Leo @2020年3月21日   System.currentTimeMillis() -> getDatabaseGMTTime(bizCtx)
				// 锁sql修改 @2020年7月8日 update t_ops_lockinf set lock_time =? where name=? and owner=? ->
				lockTime = getDatabaseGMTTime();
				//sql = "update t_ops_lockinf set lock_time =? where name=? and owner=?";
				try {
					// 更新时间戳
					//int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, String.valueOf(lockTime), name, nodeId);
					OpsLockInfDO opsLockInfDO=new OpsLockInfDO();
					opsLockInfDO.setLockTime( String.valueOf(lockTime));
					opsLockInfDO.setOwner(nodeId);
					opsLockInfDO.setTmSmp(getDatabaseTime());
					opsLockInfDO.setName(name);
					int ret =opsLockInfMapper.updateByPrimaryKey(opsLockInfDO);
					log.info("ret:" + ret);
					if (ret != 1) {
						log.warn("Get schedule lock failed");
						return false;
					}
				} catch (Exception e) {
					log.error(e.toString(), e);
					return false;
				}
				return true;
			} else {
				log.info("Not time out, get schLock failed! local nodeId/owner=[" + nodeId + "]; database nodeId/owner=[" + owner + "]");
				return false;
			}
		}
	}

	/**
	 * @description: 获取GMT时间
	 * @author: Leo
	 * @date: 2020/3/21
	 * @param: 
	 * @return: 
	 * @throws: 
	 * @modified by: 
	 * @modified description: 
	*/
	private Long getDatabaseGMTTime( ) throws  ParseException {
		HashMap rec =opsLockInfMapper.selectSystemTime();
		String tmp = (String) rec.get("system_time");
		long time = DateUtils.parseDate(tmp, "yyyyMMddHHmmss").getTime();
		return time;
	}

	private String getDatabaseTime( )  {
		HashMap rec =opsLockInfMapper.selectSystemTime();
		String tmp = (String) rec.get("system_time");
		return tmp;
	}
}

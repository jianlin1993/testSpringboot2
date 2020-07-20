package com.wxy.wjl.testspringboot2.job.service;

import com.wxy.wjl.testspringboot2.job.utils.OpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

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
public class SchLockInfService {

	public static boolean tryLock( String name) throws Exception, ParseException {
		//
		String nodeId = OpsUtil.getNodeId();

		HashMap rec = null;
		while (true) {
			String sql = "select * from t_ops_lockinf where name='" + name + "' for update";
			rec = bizCtx.getDataBaseUtil().readRecord(sql);
			if (rec == null || rec.isEmpty()) {
				//
				sql = "insert into t_ops_lockinf(name,upd_opr,owner,lock_time) values(?, ?, ?, ?)";
				try {
					// modified by Leo @2020年3月21日   System.currentTimeMillis() -> getDatabaseGMTTime(bizCtx)
					long time = getDatabaseGMTTime(bizCtx);
					int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, name, "SYS", nodeId, String.valueOf(time));
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

		String sql = "select to_char(sysdate,'yyyymmddhh24miss') system_time from dual";
		rec = bizCtx.getDataBaseUtil().readRecord(sql);
		String tmp = (String) rec.get("system_time");
		long time = DateUtils.parseDate(tmp, "yyyyMMddHHmmss").getTime();
		if (time - lockTime > 120 * 1000) {
			log.info("Time out more than 120S, will try get schedule lock");
			// 超过1200s则获取锁 ;  modified by Leo @2020年3月21日   System.currentTimeMillis() -> getDatabaseGMTTime(bizCtx)
			// 锁sql修改 @2020年7月8日 update t_ops_lockinf set lock_time =? where name=? and owner=? ->
			lockTime = getDatabaseGMTTime(bizCtx);
			sql = "update t_ops_lockinf set lock_time =?, owner=?, tm_smp=? where name=?";
			try {
				int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, String.valueOf(lockTime), nodeId, DateUtil.getTmSmp(), name);
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
				lockTime = getDatabaseGMTTime(bizCtx);
				sql = "update t_ops_lockinf set lock_time =? where name=? and owner=?";
				try {
					// 更新时间戳
					int ret = bizCtx.getDataBaseUtil().execUpdateBind(sql, String.valueOf(lockTime), name, nodeId);
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
	private static Long getDatabaseGMTTime(YGBizMessageContext bizCtx) throws YGException, ParseException {
		String sql = "select to_char(sysdate,'yyyymmddhh24miss') system_time from dual";
		HashMap rec = bizCtx.getDataBaseUtil().readRecord(sql);
		String tmp = (String) rec.get("system_time");
		long time = DateUtils.parseDate(tmp, "yyyyMMddHHmmss").getTime();
		return time;
	}
}

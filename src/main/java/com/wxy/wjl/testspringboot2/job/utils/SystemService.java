package com.wxy.wjl.testspringboot2.job.utils;

/**
 * xxx
 *
 * @author xu_lw
 * @version 5.0.0
 * created  at 2020-02-18 16:54
 * copyright @2018 北京沐融信息科技股份有限公司
 */
public class SystemService {
	private static boolean master = false;

	public static boolean isMaster() {
		return master;
	}

	public static void setMaster(boolean flag) {
		master = flag;
	}
}

/**
 * OpsJobJnlDO.java
 * Copyright(C) 北京沐融信息科技有限公司
 * ------------------------------------------------
 * Created by xu_lw on 2020-02-21 16:03:17.
 */
package com.wxy.wjl.testspringboot2.job.dal.entity;

import com.murong.ecp.bp.common.util.BaseDO;
import lombok.Data;

/**
 * 定时任务流水表
 */
@Data
public class OpsJobJnlDO extends BaseDO {

    /**
    * ID编号
    */
    private Long id;

    /**
    * Name
    */
    private String name;

    /**
    * GroupName
    */
    private String grpNm;

    /**
    * Url
    */
    private String url;

    /**
    * Cron
    */
    private String cron;

    /**
    * TimeOut
    */
    private Long tmOut;

    /**
    * Remark
    */
    private String remark;

    /**
    * BeginTime
    */
    private String begTm;

    /**
    * EndTime
    */
    private String endTm;

    /**
    * Status
    */
    private String status;

    /**
    * Result
    */
    private String resultMsg;
}

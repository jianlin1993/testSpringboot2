/**
 * OpsJobInfDO.java
 * Copyright(C) 北京沐融信息科技有限公司
 * ------------------------------------------------
 * Created by xu_lw on 2020-02-21 16:03:05.
 */
package com.wxy.wjl.testspringboot2.job.dal.entity;

import com.murong.ecp.bp.common.util.BaseDO;
import lombok.Data;

/**
 * 定时任务信息表
 */
@Data
public class OpsJobInfDO extends BaseDO {

    /**
    * ID编号
    */
    private Long id;

    /**
    * 名称
    */
    private String name;

    /**
    * 组名
    */
    private String grpNm;

    /**
    * 服务URL
    */
    private String url;

    /**
    * 定时表达式
    */
    private String cron;

    /**
    * 超时时间
    */
    private Long tmOut;

    /**
    * 配置描述
    */
    private String remark;

    /**
    * 状态
    */
    private String status;

    /**
     * 是否删除标志  0:未删除，1:已删除
     */
    private String deleteFlag;
}

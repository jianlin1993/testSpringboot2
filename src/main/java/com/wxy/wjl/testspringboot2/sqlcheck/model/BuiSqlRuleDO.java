package com.wxy.wjl.testspringboot2.sqlcheck.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BuiSqlRuleDO implements Serializable {
    private static final long serialVersionUID = 3644375389167467447L;

    /**
     * ids id集合
     */
    private String ids;

    /**
     * id
     */
    private String id;

    /**
     * 规则编号 每种规则编号固定
     */
    private String ruleId;

    /**
     * 规则描述
     */
    private String ruleDesc;

    /**
     * 规则类型 1-DML 2-DDL create table 3-DDL alter table 4-sequence
     */
    private String ruleType;

    /**
     * 生效标志 Y生效 N失效
     */
    private String effFlg;
    /**
     * 请求id
     */
    private String reqId;
}

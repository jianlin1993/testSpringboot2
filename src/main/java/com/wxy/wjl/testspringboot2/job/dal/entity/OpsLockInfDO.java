package com.wxy.wjl.testspringboot2.job.dal.entity;

import lombok.Data;

@Data
public class OpsLockInfDO {
    private String name;
    private String updOpr;
    private String owner;
    private String lockTime;
    private String tmSmp;
    private String nodId;
    private String reqId;
}

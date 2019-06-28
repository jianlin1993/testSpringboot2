package com.wxy.wjl.testspringboot2.domain;


import lombok.Data;

@Data
public class Bill {
    private int no;
    private String txTyp;
    private String remark;
    private String usrNo;
    private String cnlNo;
    private String txAmt;
    private String txDt;
}

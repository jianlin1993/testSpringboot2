package com.wxy.wjl.testspringboot2.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Bill implements Serializable {
    private static final long serialVersionUID = 6236436211510552555L;

    private int no;
    private String txTyp;
    private String remark;
    private String usrNo;
    private String cnlNo;
    private String txAmt;
    private String txDt;
}

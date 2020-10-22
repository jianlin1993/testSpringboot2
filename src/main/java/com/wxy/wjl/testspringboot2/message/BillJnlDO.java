package com.wxy.wjl.testspringboot2.message;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BillJnlDO implements Serializable {

    /** 序列化ID */
    private static final long serialVersionUID = 202115324290428740L;

    private String jrnNo;

    private Integer usrNo;

    private String cnlNo;
    private String txAmt;
    private String creDt;
}

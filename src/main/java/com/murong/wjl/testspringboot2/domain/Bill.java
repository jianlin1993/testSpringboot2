package com.murong.wjl.testspringboot2.domain;

public class Bill {
    private String jrnNo;
    private String txTyp;
    private String remark;

    public String getJrnNo() {
        return jrnNo;
    }

    public void setJrnNo(String jrnNo) {
        this.jrnNo = jrnNo;
    }

    public String getTxTyp() {
        return txTyp;
    }

    public void setTxTyp(String txTyp) {
        this.txTyp = txTyp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

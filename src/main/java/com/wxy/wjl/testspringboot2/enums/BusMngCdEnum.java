package com.wxy.wjl.testspringboot2.enums;


/**
 * @author R&D Center - Lebron Guo
 * @version 5.0.0
 * @date 2019/9/11 11:11
 * copyright @2019 Beijing Murong IT Corp. Ltd.
 */
public enum BusMngCdEnum  {

    /**
     * Abstract business management code
     */
    OPERATOR_MANAGEMENT("operatorManagement", "Operator management"),
    ;

    BusMngCdEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private String value;
    private String desc;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static BusMngCdEnum getByValue(String value) {
        if (null == value) {
            return null;
        }
        for (BusMngCdEnum busCdEnum : values()) {
            if (busCdEnum.getValue().equals(value)) {
                return busCdEnum;
            }
        }
        return null;
    }
}

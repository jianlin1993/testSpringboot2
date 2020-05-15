package com.wxy.wjl.testspringboot2.enums;


import java.util.ArrayList;
import java.util.List;

import static com.wxy.wjl.testspringboot2.enums.BusMngCdEnum.*;


/**
 * @author R&D Center - Lebron Guo
 * @version 5.0.0
 * @date 2019/9/11 11:11
 * copyright @2019 Beijing Murong IT Corp. Ltd.
 */
public enum BusCdEnum  {

    /**
     * Business code
     */
    ENG_RUL_CONF("engRuleUpdate", "Update engine rule",null),
    OPER_ADD("operatorAdd","Operator add",OPERATOR_MANAGEMENT),
    OPER_UPD("operatorUpdate","Operator update",OPERATOR_MANAGEMENT),
    OPER_DEL("operatorDelete","Operator delete",OPERATOR_MANAGEMENT),
    ;

    BusCdEnum(String value, String desc,BusMngCdEnum busMngCdEnum) {
        this.value = value;
        this.desc = desc;
        this.busMngCdEnum = busMngCdEnum;
    }

    private String value;
    private String desc;
    private BusMngCdEnum busMngCdEnum;

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

    public BusMngCdEnum getBusMngCdEnum() {
        return this.busMngCdEnum;
    }

    public void setBusMngCdEnum(BusMngCdEnum busMngCdEnum) {
        this.busMngCdEnum = busMngCdEnum;
    }

    public static BusCdEnum getByValue(String value) {
        if (null == value) {
            return null;
        }
        for (BusCdEnum busCdEnum : values()) {
            if (busCdEnum.getValue().equals(value)) {
                return busCdEnum;
            }
        }
        return null;
    }

    public static List<BusCdEnum> getByBusMngCd(BusMngCdEnum busMngCdEnum) {
        if (null == busMngCdEnum) {
            return null;
        }
        List<BusCdEnum> list=new ArrayList<>(10);
        for (BusCdEnum busCdEnum : values()) {
            if (busMngCdEnum.equals(busCdEnum.getBusMngCdEnum())) {
                list.add(busCdEnum);
            }
        }
        return list;
    }

}

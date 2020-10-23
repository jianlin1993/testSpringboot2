package com.wxy.wjl.testspringboot2.sqlcheck.enums;


/**
 * sql 规则类型枚举
 */
public enum SqlRuleTypeEnum {
    RULE_1("1","UPDATE语句禁止出现ORDER BY子句"),
    RULE_2("2","UPDATE语句必须出现WHERE子句"),
    RULE_3("3","DELETE语句必须出现WHERE子句"),
    RULE_4("4","DELETE语句禁止出现ORDER BY子句"),
    RULE_5("5","SELECT语句必须出现WHERE子句"),
    RULE_6("6","禁止DROP操作"),
    RULE_7("7","禁止TRUNCATE操作"),
    RULE_8("8","表必须有注释"),
    RULE_9("9","字段必须有注释(数据字典名称 | 注释)"),
    RULE_10("10","非CLOB字段必须NOT NULL"),
    RULE_11("11","非CLOB字段必须有默认值"),
    RULE_12("12","表必须有主键(PK_TABLENAME)"),
    RULE_13("13","字段定义必须符合字典定义"),
    RULE_14("14","表必须有TM_SMP、NOD_ID、REQ_ID公共字段"),
    RULE_15("15","表必须有CRE_OPR、UPD_OPR、CRE_TS、UPD_TS公共字段"),
    RULE_16("16","表名命名不符合规范(T_XXX_*)"),
    RULE_17("17","字段名命名不符合规范"),
    RULE_18("18","索引命名不符合规范(UI1_TABLENAME、NI1_TABLENAME)"),
    RULE_19("19","索引字段超过5个"),
    RULE_20("20","索引个数超过4个"),
    RULE_21("21","冗余索引(指该索引可以使用其他索引替代)"),
    RULE_22("22","索引必须指定TABLESPACE"),
    RULE_23("23","SEQUENCE命名不符合规范(S_XX)"),
    ;
    private final String ruleId;
    private final String ruleDesc;

    public String getRuleId() {
        return ruleId;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    SqlRuleTypeEnum(String ruleId, String ruleDesc) {
        this.ruleId = ruleId;
        this.ruleDesc = ruleDesc;
    }

    public static String getRuleDescByRuleId(String ruleId) {
        if (null == ruleId) {
            return null;
        }
        for (SqlRuleTypeEnum sqlRuleTypeEnum : values()) {
            if (sqlRuleTypeEnum.getRuleId().equals(ruleId)) {
                return sqlRuleTypeEnum.getRuleDesc();
            }
        }
        return null;
    }

    public static SqlRuleTypeEnum getSqlRuleTypeEnumByRuleId(String ruleId) {
        if (null == ruleId) {
            return null;
        }
        for (SqlRuleTypeEnum sqlRuleTypeEnum : values()) {
            if (sqlRuleTypeEnum.getRuleId().equals(ruleId)) {
                return sqlRuleTypeEnum;
            }
        }
        return null;
    }
}

package com.wxy.wjl.testspringboot2.sqlcheck.enums;

/**
 * SQL操作类型
 */
public enum SqlTypeEnum {
    DML("1","DML"),
    DDL_CREATE("2","DDL(CREATE TABLE)"),
    DDL_ALTER("3","DDL(ALTER TABLE)"),
    SEQUENCE("4","SEQUENCE"),
    ;
    private final String value;
    private final String desc;

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    SqlTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDescByvalue(String value) {
        if (null == value) {
            return null;
        }
        for (SqlTypeEnum sqlTypeEnum : values()) {
            if (sqlTypeEnum.getValue().equals(value)) {
                return sqlTypeEnum.getDesc();
            }
        }
        return null;
    }

    public static SqlTypeEnum getSqlTypeEnumByValue(String value) {
        if (null == value) {
            return null;
        }
        for (SqlTypeEnum sqlTypeEnum : values()) {
            if (sqlTypeEnum.getValue().equals(value)) {
                return sqlTypeEnum;
            }
        }
        return null;
    }
}

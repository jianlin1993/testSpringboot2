package com.wxy.wjl.testspringboot2.sqlcheck.model;

import lombok.Data;

/**
 * @author: Leo
 * @description: oracle数据表列属性
 *   typeName = VARCHAR2  ;columnName = AC_NO ;dataType = 12  ;columnSize = 32 ;nullAble = 0 ;remarks = AC_NO|Account number
 * @date: 2020/3/19
 * @modified by:
 * @modified description:
 */
@Data
public class Column {
    /**
     * 操作类型 ADD：添加字段 MODIFY 修改字段
     */
    private String operType;
    /**
     * table name
     */
    private String tableName;
    /***
     * column name
     */
    private String columnName;
    /***
     * type Name
     */
    private String typeName;
    /***
     * column Size
     */
    private String columnSize;
    /***
     * remark
     */
    private String remarks;
    /***
     * data Type eg:
     */
    private String dataType;
    /***
     * nullAble Y可空 N：NOT NULL
     */
    private String nullAble;
    /***
     * column sql
     */
    private String columnSql;
    /***
     * 是否有默认值 Y:是 N:否
     */
    private String hasDefaultValue;
}

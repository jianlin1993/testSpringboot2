package com.wxy.wjl.testspringboot2.sqlcheck.model;

import lombok.Data;

import java.util.List;

/**
 * table属性
 */
@Data
public class Table {

    /**
     * schema
     */
    private String schema;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 备注
     */
    private String comment;

    /**
     * 是否有主键 Y:是 N:否
     */
    private String hasPrimaryKey;


    private List<Index> indexList;

    private List<Column> columnList;

}

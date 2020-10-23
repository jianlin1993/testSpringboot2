package com.wxy.wjl.testspringboot2.sqlcheck.model;

import lombok.Data;

/**
 * 数据库索引属性
 */
@Data
public class Index {

    /**
     * 索引名
     */
    private String indexName;

    /**
     * 是否是主键 Y:是 N:否
     */
    private String primaryKey;

    /**
     * 是否是唯一索引 Y:是 N:否
     */
    private String uniqueIndex;

    /**
     * 索引字段 多个字段以逗号分隔，并且注意顺序
     */
    private String indexColumn;

    /**
     * 表空间
     */
    private String tableSpace;

    /**
     * 表名
     */
    private String tableName;
}

package com.wxy.wjl.testspringboot2.database.blob;

import lombok.Data;

import java.io.Serializable;

/**
 * 测试mybatis中插入和查询blob字段
 * 代码测试类在BlobMybatisTest中
 */
@Data
public class TestBlobDO implements Serializable {
    private static final long serialVersionUID = 6236436211510552555L;

    private Integer id;

    private byte[] bytes;

}

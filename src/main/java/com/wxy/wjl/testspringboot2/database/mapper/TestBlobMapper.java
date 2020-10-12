package com.wxy.wjl.testspringboot2.database.mapper;

import com.wxy.wjl.testspringboot2.database.blob.TestBlobDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestBlobMapper {

    Integer add(TestBlobDO testBlobDO);

    TestBlobDO find(int id);
}

package com.wxy.wjl.testspringboot2.mapper;


import com.wxy.wjl.testspringboot2.message.BillJnlDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillJnlMapper {

    int add(BillJnlDO bill);
}

package com.murong.wjl.testspringboot2.mapper;

import com.murong.wjl.testspringboot2.domain.Bill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {
    Bill getBill(String jrnNo);
    List<Bill> getAll();
}

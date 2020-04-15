package com.wxy.wjl.testspringboot2.mapper;

import com.wxy.wjl.testspringboot2.domain.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillMapper {
    Bill getBill(Integer jrnNo);
    List<Bill> getAll();
    List<Bill> getBillList( @Param("str") String str );
    Bill getBillByNo(int no);
    int add(Bill bill);
}

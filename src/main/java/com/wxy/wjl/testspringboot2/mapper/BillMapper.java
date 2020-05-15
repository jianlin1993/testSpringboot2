package com.wxy.wjl.testspringboot2.mapper;

import com.wxy.wjl.testspringboot2.config.SpringCacheConfig;
import com.wxy.wjl.testspringboot2.domain.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface BillMapper {

    @Cacheable(value=SpringCacheConfig.CACHE_NAME,key = "'BillMapper.' +  methodName +'.' + #p0", unless = "#result==null")
    Bill getBill(Integer jrnNo);
    List<Bill> getAll();
    List<Bill> getBillList( @Param("str") String str );
    Bill getBillByNo(int no);
    int add(Bill bill);
}

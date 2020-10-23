package com.wxy.wjl.testspringboot2.mapper;

import com.wxy.wjl.testspringboot2.sqlcheck.model.BuiSqlRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BuiSqlRuleMapper {

    List<BuiSqlRuleDO> selectByRuleType(String ruleType);

    int updateByPrimaryKeySelective(BuiSqlRuleDO buiSqlRuleDO);

   // List<BuiSqlRuleDO> selectPage(YGPageEntity pageEntity, BuiSqlRuleDO entity);
}

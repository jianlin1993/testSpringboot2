package com.wxy.wjl.testspringboot2.job.dal.dao;

import com.wxy.wjl.testspringboot2.job.dal.entity.OpsLockInfDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface OpsLockInfMapper {
    OpsLockInfDO selectByPrimaryKey(OpsLockInfDO entity);

    int updateByPrimaryKey(OpsLockInfDO entity);

    int insert(OpsLockInfDO entity);

    HashMap selectByPrimaryKeyForUpdate(OpsLockInfDO entity);

    HashMap selectSystemTime( );

}

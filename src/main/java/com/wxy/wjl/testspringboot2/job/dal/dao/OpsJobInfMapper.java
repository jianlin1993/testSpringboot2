/**
 * OpsJobInfMapper.java
 * Copyright(C) 北京沐融信息科技股份有限公司
 * All rights reserved
 * ------------------------------------------------
 * Created by xu_lw on 2020-02-20 11:45:57.
 */
package com.wxy.wjl.testspringboot2.job.dal.dao;



import com.wxy.wjl.testspringboot2.job.dal.entity.OpsJobInfDO;

import java.util.List;

public interface OpsJobInfMapper {
	OpsJobInfDO selectByName(String name);

	OpsJobInfDO selectByPrimaryKey(OpsJobInfDO entity);

	int updateByPrimaryKey(OpsJobInfDO entity);

	int updateByPrimaryKeySelective(OpsJobInfDO entity);

	int deleteByIds(String ids);

	int deleteByPrimaryKey(OpsJobInfDO entity);

	int insertSelective(OpsJobInfDO entity);

	int insert(OpsJobInfDO entity);


	List<OpsJobInfDO> selectAll();

	int updateByPrimaryKey2(String id);
}

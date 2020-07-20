/**
 * OpsJobJnlMapper.java
 * Copyright(C) 北京沐融信息科技股份有限公司
 * All rights reserved
 * ------------------------------------------------
 * Created by xu_lw on 2020-02-20 22:09:13.
 */
package com.wxy.wjl.testspringboot2.job.dal.dao;


import com.wxy.wjl.testspringboot2.job.dal.entity.OpsJobJnlDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpsJobJnlMapper {
	OpsJobJnlDO selectByPrimaryKey(OpsJobJnlDO entity);

	int updateByPrimaryKey(OpsJobJnlDO entity);

	int updateByPrimaryKeySelective(OpsJobJnlDO entity);

	int deleteByIds(String ids);

	int deleteByPrimaryKey(OpsJobJnlDO entity);

	int insertSelective(OpsJobJnlDO entity);

	int insert(OpsJobJnlDO entity);

}

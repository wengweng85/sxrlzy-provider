package com.insigma.mvc.dao.resources.SXJY_RLZYSC_009_001;

import com.insigma.mvc.model.Ec13;

public interface UserLogInfoManageMapper {
    int deleteByPrimaryKey(String eec131);

	int insert(Ec13 record);

	int insertSelective(Ec13 record);

	Ec13 selectByPrimaryKey(String eec131);

	int updateByPrimaryKeySelective(Ec13 record);

	int updateByPrimaryKey(Ec13 record);

}
package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001;

import com.insigma.mvc.model.Ec12;

public interface UserInfoEc12Mapper {
	
	    int deleteByPrimaryKey(String eec121);

		int insert(Ec12 record);

		int insertSelective(Ec12 record);

		Ec12 selectByPrimaryKey(String eec121);

		int updateByPrimaryKeySelective(Ec12 record);

		int updateByPrimaryKey(Ec12 record);

}

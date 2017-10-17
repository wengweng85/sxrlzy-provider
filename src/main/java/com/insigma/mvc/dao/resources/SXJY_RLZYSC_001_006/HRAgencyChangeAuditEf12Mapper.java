package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_006;

import com.insigma.mvc.model.Ef12;

public interface HRAgencyChangeAuditEf12Mapper {
	int deleteByPrimaryKey(String eef121);

    int insert(Ef12 record);

    int insertSelective(Ef12 record);

    Ef12 selectByPrimaryKey(String eef121);

    int updateByPrimaryKeySelective(Ef12 record);

    int updateByPrimaryKey(Ef12 record);
}

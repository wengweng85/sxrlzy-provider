package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003;

import java.util.List;

import com.insigma.mvc.model.Ee16;

public interface BusinessReportEe16Mapper {
	
	int deleteByPrimaryKey(String eee161);

    int insert(Ee16 record);

    int insertSelective(Ee16 record);

    Ee16 selectByPrimaryKey(String eee161);

    int updateByPrimaryKeySelective(Ee16 record);

    int updateByPrimaryKey(Ee16 record);

    //根据机构编码查询信息
	Ee16 selectByOrganId(String aef101);

	List<Ee16> selectByAef101(String aef101);

}

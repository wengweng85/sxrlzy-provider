package com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.Ec11;

public interface HRAgencyApplyPersonMapper {
    int deleteByPrimaryKey(String eec111);

	int insert(Ec11 record);

	int insertSelective(Ec11 record);

	Ec11 selectByPrimaryKey(String eec111);
	
	Ec11 selectNameByPrimaryKey(String eec111);

	int updateByPrimaryKeySelective(Ec11 record);

	int updateByPrimaryKey(Ec11 record);
	
	List<Ec11> getEc11List(Ec11 ec11);
	
	int batDeleteAgencyPersonInfo(String[] ids);
	
	int batupdateAgencyPersonArray(Map<String,Object> map);
	
	int deleteAagencyPersonByEec111(String eec111);

}
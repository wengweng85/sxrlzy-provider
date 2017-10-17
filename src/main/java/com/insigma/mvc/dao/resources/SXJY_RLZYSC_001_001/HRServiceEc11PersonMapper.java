package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.Ec11;

public interface HRServiceEc11PersonMapper {
	
	 int deleteByPrimaryKey(String eec111);

		int insert(Ec11 record);

		int insertSelective(Ec11 record);

		Ec11 selectByPrimaryKey(String eec111);

		int updateByPrimaryKeySelective(Ec11 record);

		int updateByPrimaryKey(Ec11 record);

		//ÅúÁ¿É¾³ý
		int batupdateAgencyPersonArray(Map<String, Object> map);

		int deleteAagencyPersonByEec111(String eec111);

		List<Ec11> getEc11List(Ec11 ec11);

		Ec11 selectNameByPrimaryKey(String eec111);

}

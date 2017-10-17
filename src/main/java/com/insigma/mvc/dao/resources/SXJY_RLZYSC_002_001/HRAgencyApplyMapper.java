package com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001;

import java.util.List;

import com.insigma.mvc.model.Ef11;

public interface HRAgencyApplyMapper {
	int deleteByPrimaryKey(String aef101);

    int insert(Ef11 record);

    int insertSelective(Ef11 record);

    Ef11 selectByPrimaryKey(String aef101);
    
    Ef11 selectAgencyInfoByAab998(String aab998);
    
    Ef11 selectAgencyEf11InfoByAab998(String aab998);

    int updateByPrimaryKeySelective(Ef11 record);

    int updateByPrimaryKey(Ef11 record);
    
    int selectByAef104(Ef11 record);
    
    int selectByAab998(Ef11 record);
    
    List<Ef11> getEf11List(Ef11 ef11 );
    
    Ef11 selectEf11ByAef104(String aef104);

	Ef11 getAef101ByAab998(String aab998);

}
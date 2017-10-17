package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_006;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;

public interface HRAgencyChangeAuditMapper {
	int deleteByPrimaryKey(String aef101);

    int insert(Ef11 record);

    int insertSelective(Ef11 record);

    Ef11 selectByPrimaryKey(String aef101);

    int updateByPrimaryKeySelective(Ef11 record);

    int updateByPrimaryKey(Ef11 ef11);
    int updateByPrimaryKey(String aef101);
    
    int selectByAef104(Ef11 record);
    
    int selectByAab998(Ef11 record);
    
    List<Ef11> getEf11List(Ef11 ef11 );
    
    List<Ef11> getEf12List(Ef11 ef11 );
    
    Ef11 selectEf11ByAef104(String aef104);
    
    int batUpdateData(String[] ids);

	int batAuditHRAgencyRegdata(List<Ef12> listResult);

	int singleAuditHRAgencyRegdata(Map<String, String> map);

	int saveNotPassAuditData(Map<String, String> map);
//ÉóºË²»Í¨¹ý
	Ef11 getEf11InfoNoAudit(Ef11 ef11_temp);

	int updateEf11ForEae052ByAef101(Ef12 ef12);

	int updateEf11ForEae052ByAef102(Ef12 ef12);

}

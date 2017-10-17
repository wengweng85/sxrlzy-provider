package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_005;

import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;

public interface HRAgencyRegAuditMapper {

    Ef11 selectByPrimaryKey(String aab998);
    
    Ef11 selectHRAgencyInfoByAab998(Ef11 ef11);
    
    EF11_DECLARE selectHRAgencyDeclareInfoByAab998(Ef11 ef11);
    
    int insertSelective(EF11_DECLARE record);

    int updateByPrimaryKeySelective(Ef11 record);

    int updateByPrimaryKey(Ef11 record);
    
    List<Ef11> getEf11List(Ef11 ef11 );
    
    List<Ef11> getEf11ListNext(Ef11 ef11 );
    
    Ef11 getEf11InfoNoAudit(Ef11 ef11 );
    
    Ef11 getEf11InfoNoAuditOther(Ef11 ef11 );
    
    Ef11 selectEf11ByAef104(String aef104);
    
    int batAuditHRAgencyRegdata(List<Ef12> list);
    
    int singleAuditHRAgencyRegdata(Map<String, String> map);
    
    int saveNotPassAuditData(Map<String, String> map);
    
    int batInsertAuditRegdata(List<Ef12> list);
    
    String selectEf11ByAef131();
    
    int updateLicenseByPrimaryKey(Ef11 ef11);
    
    int updateEf11ForEae052ByAef101(Ef11 ef11);

}
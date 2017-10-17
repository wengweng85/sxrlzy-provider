package com.insigma.mvc.dao.resources;

import com.insigma.mvc.model.EF11_DECLARE;

public interface EF11_DECLAREMapper {
    int deleteByPrimaryKey(String eef101);

    int insert(EF11_DECLARE record);

    int insertSelective(EF11_DECLARE record);

    EF11_DECLARE selectByPrimaryKey(String eef101);

    int updateByPrimaryKeySelective(EF11_DECLARE record);
    
    int updateEf11DeclareStateByPrimaryKey(EF11_DECLARE record);

    int updateByPrimaryKey(EF11_DECLARE record);
    
    EF11_DECLARE selectEf11DeclareForNoAuditById(String aab998);

    EF11_DECLARE selectHRAgencyDeclareInfoById(String aab998);
    
}
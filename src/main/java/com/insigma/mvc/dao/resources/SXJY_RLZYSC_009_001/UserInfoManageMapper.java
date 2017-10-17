package com.insigma.mvc.dao.resources.SXJY_RLZYSC_009_001;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.insigma.mvc.model.Ec12;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.model.SUser;

public interface UserInfoManageMapper {


    int insert(Ec12 record);

    int insertSelective(Ec12 record);

    Ec12 selectByPrimaryKey(String eec121);
    
    int updateByPrimaryKeySelective(Ec12 record);

    int updateByPrimaryKey(Ec12 record);
    
    int updateEc12ByAec101(Ec12 record);
    
    int updateEc12ForAab998ByAec101(Ec12 record);
    
    int updateSUserByAec101(SUser record);
    
    Ec12 selectUserInfoByAec101(String aec101);
    
    SUser selectSysUserInfoByAec101(String aec101);
    
    SUser getHRAgencyUserPwdById(@Param("aec101") String id,@Param("aec102") String pwd);
    
    List<Ec12> getHRAgencyUserInfoList(Ec12 record);
    
    int updateResetPwdEc12Data(Ec12 record);
    
    int updateBatResetPwdEc12Data(List<Ec12> list);
    
    Ec12 selectAgencyUserOnlyByAec101(String aec101);
    
    
}
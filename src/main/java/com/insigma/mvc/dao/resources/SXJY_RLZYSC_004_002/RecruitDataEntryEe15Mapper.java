package com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_002;

import java.util.List;

import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;


public interface RecruitDataEntryEe15Mapper {

	int deleteByPrimaryKey(String eee151);

    int insert(Ee15 record);

    int insertSelective(Ee15 record);

    Ee15 selectByPrimaryKey(String eee151);
    
    Ee15 selectByPrimaryKeyOne(Ee15 ee15);

    int updateByPrimaryKeySelective(Ee15 record);

    int updateByPrimaryKey(Ee15 record);
    
    //查询全部数据
    List<Ee15> getRecruitDataList(Ee15 ee15);
    
    List<Ee15> getListData(Ee15 ee15);

    
}
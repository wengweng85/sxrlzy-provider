package com.insigma.mvc.dao.resources.SXJY_RLZYSC_006_001;

import java.util.List;

import com.insigma.mvc.model.Ee14;

public interface InfoReleaseManageMapper {

	int deleteByPrimaryKey(String eee141);

    int insert(Ee14 record);

    int insertSelective(Ee14 record);

    Ee14 selectByPrimaryKey(String eee141);
    
    Ee14 selectNameByPrimaryKey(String eee141);

    int updateByPrimaryKeySelective(Ee14 record);

    int updateByPrimaryKey(Ee14 record);
    
    int updateReleaseStateByPrimaryKey(String eee141);
    
    int batUpdateReleaseStateData(String[] ids);
    
    List<Ee14> getEe14List(Ee14 ee14 );
}
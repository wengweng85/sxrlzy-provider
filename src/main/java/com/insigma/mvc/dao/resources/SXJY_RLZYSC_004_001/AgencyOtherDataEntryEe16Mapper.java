package com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_001;

import java.util.List;

import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ee16;

public interface AgencyOtherDataEntryEe16Mapper {

	
		int deleteByPrimaryKey(String eee161);

	    int insert(Ee16 record);
        
	    //增加录取招聘数据
	    int insertSelective(Ee16 record);
        
	    //根据Id查询数据		
	    Ee16 selectByPrimaryKey(String eee161);
 
	    int updateByPrimaryKeySelective(Ee16 record);

	    int updateByPrimaryKey(Ee16 record);
	    
	    //查询数据
	    List<Ee15> getRecruitListdata(Ee15 ee15);
	
}

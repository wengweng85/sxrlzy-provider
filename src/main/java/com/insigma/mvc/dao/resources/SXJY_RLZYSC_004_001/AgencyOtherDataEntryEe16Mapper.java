package com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_001;

import java.util.List;

import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ee16;

public interface AgencyOtherDataEntryEe16Mapper {

	
		int deleteByPrimaryKey(String eee161);

	    int insert(Ee16 record);
        
	    //����¼ȡ��Ƹ����
	    int insertSelective(Ee16 record);
        
	    //����Id��ѯ����		
	    Ee16 selectByPrimaryKey(String eee161);
 
	    int updateByPrimaryKeySelective(Ee16 record);

	    int updateByPrimaryKey(Ee16 record);
	    
	    //��ѯ����
	    List<Ee15> getRecruitListdata(Ee15 ee15);
	
}

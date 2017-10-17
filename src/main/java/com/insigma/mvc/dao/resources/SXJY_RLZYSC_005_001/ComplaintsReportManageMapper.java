package com.insigma.mvc.dao.resources.SXJY_RLZYSC_005_001;

import java.util.List;


import com.insigma.mvc.model.Ee13;

public interface ComplaintsReportManageMapper {
	
	int deleteByPrimaryKey(String eee131);

    int insert(Ee13 record);

    int insertSelective(Ee13 record);

    Ee13 selectByPrimaryKey(String eee131);

    int updateByPrimaryKeySelective(Ee13 record);

    int updateByPrimaryKey(String eee131);
    
	Ee13 selectNameByPrimaryKey(String eee131);
   
	int batDeleteData(String[] ids);

	List<Ee13> getEe13List(Ee13 ee13);

	int updateByIdSelective(Ee13 ee13);

	List<Ee13> getEe13ByPhone(Ee13 ee13);

	
    

}

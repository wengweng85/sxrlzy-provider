package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003;

import com.insigma.mvc.model.EE18;

public interface BusinessRecruitmentEe18Mapper {
	
	  int deleteByPrimaryKey(String eee181);

	    int insert(EE18 record);

	    int insertSelective(EE18 record);

	    EE18 selectByPrimaryKey(String eee181);

	    int updateByPrimaryKeySelective(EE18 record);

	    int updateByPrimaryKey(EE18 record);

		EE18 selectByAef101(String aef101);
       
		EE18 selectBusinessReportInfoByAef101(EE18 ee18);
		
		EE18 selectEe18ReportInfoByAab998(EE18 ee18);

}

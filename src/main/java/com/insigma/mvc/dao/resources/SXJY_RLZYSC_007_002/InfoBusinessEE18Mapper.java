package com.insigma.mvc.dao.resources.SXJY_RLZYSC_007_002;

import java.util.List;

import com.insigma.mvc.model.EE18;


public interface InfoBusinessEE18Mapper {

	//��ѯȫ������
    List<EE18> getRecruitDataList(EE18 ee18);
    
    EE18 selectByPrimaryKey(String eee181);
}

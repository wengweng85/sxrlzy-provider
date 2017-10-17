package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003;

import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;

public interface RecruitmentDataEe15Mapper {
	
	int deleteByPrimaryKey(String eee151);

    int insert(Ee15 record);

    int insertSelective(Ee15 record); 

    Ee15 selectByPrimaryKey(String eee151);

    int updateByPrimaryKeySelective(Ee15 record);

    int updateByPrimaryKey(Ee15 record);
    
    AjaxReturnMsg getEe15ByAef101(String aef101);

    //��ѯ���� ����EE15 ��Ef11
	List<Ee15> selectByAef101(String aef101,String aae105);

	//ҵ�񱨸�����
	List<Ee15> getEe15ByAab998(Ee15 aab998);

	List<Ee15> selectByAef101(Ee15 aef101);
    //��ȡEe15��Ϣ
	Ee15 selectReportInfoByAab998(String aab998);
//������ѯ  Ee15
	List getEe15singleByAab998(Ee15 ee15);

	
	

}

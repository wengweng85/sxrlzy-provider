package com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001;

import java.util.List;

import com.insigma.mvc.model.Ef12;

public interface HRAgencyApplyEf12Mapper {

	int deleteByPrimaryKey(String eef121);

    int insert(Ef12 record);

    int insertSelective(Ef12 record);

    Ef12 selectByPrimaryKey(String eef121);

    int updateByPrimaryKeySelective(Ef12 record);

    int updateByPrimaryKey(Ef12 record);

	Ef12 selectEf12ByAef101(String aef101);

	/**
	 *ҵ�񱨸����-����ͳһ���ô����ȡ��Ϣ 
	 * @param aab998 
	 * */
	Ef12 getEf12ByAab998(String aab998);
	/**
	 *ҵ�񱨸����-����������������
	 * */
    int updateEf12ByPK(String eef121);
    /**
	 *ע�����-����ͳһ���ô����ȡ��Ϣ 
	 * @param aab998 
	 * */
    Ef12 getEf12CancleByAab998(String aab998);
    /**
   	 *������-����ͳһ���ô����ȡ��Ϣ 
   	 * @param aab998 
   	 * */

	Ef12 getEf12updateByAab998(String aab998);
	
	
	List<Ef12> selectBusinessReportByAef101(Ef12 ef12);
   

	
}
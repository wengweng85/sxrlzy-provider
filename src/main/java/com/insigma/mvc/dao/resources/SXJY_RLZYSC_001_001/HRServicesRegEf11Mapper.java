package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001;


import java.util.List;

import com.insigma.mvc.model.Ac01;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;

public interface HRServicesRegEf11Mapper {
	/**
	 *�������� 
	 * */
	 //�жϻ������Ƿ�һ��
    int selectByAef104(Ef11 record);
     //�ж�ͳһ���ô����Ƿ�һ��
    int selectByAab998(Ef11 record);
     //�������-����
  	int insertSelective(Ef11 record);
     //���ݻ������ֲ�ѯef11��������
    Ef11 selectEf11ByAef101(String aef101);
    //����µ�ְ���ֶ�
    int insertNewSelective(EF11_DECLARE record);
	
    /**
     * ��������
     * */
   //��ѯȫ������=��ʼ����ȡ��Ӧ�����ݡ���getEf11List
    List<Ef11> getEf11List(Ef11 ef11 );
   
    //����Id��ѯ����
    Ef11 selectByPrimaryKey(String aef101);
    
    Ef11 selectAgencyInfoByAab998(String aab998);
    
    Ef11 selectByPrimaryKeyAef(String aef101);
    
    /**
     * ����+ע������
     * */
    
    //���������޸Ķ�Ӧ������
    int updateByPrimaryKeySelective(Ef11 record);	
  	
   
  	
    
    
    
	//��������ɾ������
    int deleteByPrimaryKey(String aef101);
    
   
    //����ɾ��
    int batDeleteData(String []  ids);
    //���ݶ����ȡ�ж���
    int selectByAef101(String aef101);
   
    
    //�����ı���ֵ
	int insertTextSelective(Ef11 ef11);
	//���븴ѡ��ֵ
	int insertCheckSelective(Ef11 ef11);
	//��ȡ��������
	List<Ef11> getAef104ByAab998(String aab998);
	//��ȡef11
	Ef11 getEf11Info(String aef101);
	
	

}

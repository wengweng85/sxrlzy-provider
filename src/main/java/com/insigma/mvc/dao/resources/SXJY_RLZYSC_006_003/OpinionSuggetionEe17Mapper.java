package com.insigma.mvc.dao.resources.SXJY_RLZYSC_006_003;

import java.util.List;

import com.insigma.mvc.model.Ee17;

public interface OpinionSuggetionEe17Mapper {
	//��������ɾ������
	int deleteByPrimaryKey(String eee171);

	int insert(Ee17 record);
    //�������-����
	int insertSelective(Ee17 record);
    //����Id��ѯ����
	Ee17 selectByPrimaryKey(String eee171);
	
	//����Id��ѯ����ONE
	Ee17 selectByPrimaryOpoinion(String eee171);
	
	Ee17 selectNameByPrimaryKey(String eee171);
	
    //���������޸Ķ�Ӧ������
	int updateByPrimaryKeySelective(Ee17 record);
	
	//�޸� ���
	int updateAdviceSelective(Ee17 record);
	
    int updateByPrimaryKey(Ee17 record);
   
  //��ҳ��ѯ+��ѯ��ȡ�����Ϣ
  	List<Ee17> getEe17List(Ee17 ee17);
    
    // ���ݱ������Ϣ
	List<Ee17> selectByHui(Ee17 aee150);
	
	//����ɾ��
	int batDeleteData(String[] ids); 
	
	
	int batUpdateReleaseStateData(String[] ids);

}

package com.insigma.mvc.dao.resources.SXJY_RLZYSC_003_001;


import java.util.List;

import com.insigma.mvc.model.Ac01;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.Ee11;
import com.insigma.mvc.model.SGroup;

public interface AnnouncementInformEe11Mapper {
	
	//��������
	 int insertSelective(Ee11 record);
	//����������ѯ����
	 Ee11 selectByPrimaryKey(String eee111);
	//��ת��ҳ�����Ϣ
	Ee11 selectNameByPrimaryKey(String eee111);
	//��ҳ��ѯ+��ѯ��ȡ������Ϣ
	List<Ee11> getEe11List(Ee11 ee11);
	//ͨ��idɾ����ԱEe11��Ϣ 
	int deleteByPrimaryKey(String eee111);
	//����ɾ��
	int batDeleteData(String[] ids);

	List<CodeValue> getCodeValueTree(CodeValue codevalue);
	//���������
	List<CodeValue> getAllGroupList(CodeValue codevalue);
	String getGroupDataById(String groupid); 

	int updateByPrimaryKey(Ee11 record);
}

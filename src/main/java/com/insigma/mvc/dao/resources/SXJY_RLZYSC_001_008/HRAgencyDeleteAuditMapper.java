package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_008;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;

public interface HRAgencyDeleteAuditMapper {

	//��ȡ������Ϣ
	List<Ef11> getEf11List(Ef11 ef11);

	//��˲�ͨ������id��ȡ����
	Ef11 getEf11InfoNoAudit(Ef11 ef11_temp);
    //���治ͨ��������
	int saveNotPassAuditData(Map<String, String> map);

	//��ϸҳ����ݾ�id��ѯ
	Ef11 selectByPrimaryKey(String aab998);

	//�������  ��ѯ����nilai
	Ef11 selectHRAgencyInfoByAab998(Ef11 ef11Info);
//��������״̬
	int updateEf11ForEae052ByAef101(Ef12 ef11);
	
	int updateEf11ForEae052ByAef102(Ef12 ef11);

}

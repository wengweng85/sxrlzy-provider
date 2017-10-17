package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_008;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;

public interface HRAgencyDeleteAuditMapper {

	//获取机构信息
	List<Ef11> getEf11List(Ef11 ef11);

	//审核不通过根据id获取数据
	Ef11 getEf11InfoNoAudit(Ef11 ef11_temp);
    //储存不通过的数据
	int saveNotPassAuditData(Map<String, String> map);

	//详细页面根据据id查询
	Ef11 selectByPrimaryKey(String aab998);

	//批量审核  查询对象nilai
	Ef11 selectHRAgencyInfoByAab998(Ef11 ef11Info);
//批量更新状态
	int updateEf11ForEae052ByAef101(Ef12 ef11);
	
	int updateEf11ForEae052ByAef102(Ef12 ef11);

}

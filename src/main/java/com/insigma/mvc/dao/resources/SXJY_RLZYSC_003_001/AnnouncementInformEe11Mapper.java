package com.insigma.mvc.dao.resources.SXJY_RLZYSC_003_001;


import java.util.List;

import com.insigma.mvc.model.Ac01;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.Ee11;
import com.insigma.mvc.model.SGroup;

public interface AnnouncementInformEe11Mapper {
	
	//插入数据
	 int insertSelective(Ee11 record);
	//根据主键查询数据
	 Ee11 selectByPrimaryKey(String eee111);
	//跳转至页面的信息
	Ee11 selectNameByPrimaryKey(String eee111);
	//分页查询+查询获取公告信息
	List<Ee11> getEe11List(Ee11 ee11);
	//通过id删除人员Ee11信息 
	int deleteByPrimaryKey(String eee111);
	//批量删除
	int batDeleteData(String[] ids);

	List<CodeValue> getCodeValueTree(CodeValue codevalue);
	//服务机构树
	List<CodeValue> getAllGroupList(CodeValue codevalue);
	String getGroupDataById(String groupid); 

	int updateByPrimaryKey(Ee11 record);
}

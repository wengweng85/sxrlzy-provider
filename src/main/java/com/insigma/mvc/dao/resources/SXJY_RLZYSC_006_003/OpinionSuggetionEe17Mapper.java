package com.insigma.mvc.dao.resources.SXJY_RLZYSC_006_003;

import java.util.List;

import com.insigma.mvc.model.Ee17;

public interface OpinionSuggetionEe17Mapper {
	//根据主键删除数据
	int deleteByPrimaryKey(String eee171);

	int insert(Ee17 record);
    //添加数据-保存
	int insertSelective(Ee17 record);
    //根据Id查询数据
	Ee17 selectByPrimaryKey(String eee171);
	
	//根据Id查询数据ONE
	Ee17 selectByPrimaryOpoinion(String eee171);
	
	Ee17 selectNameByPrimaryKey(String eee171);
	
    //根据主键修改对应的数据
	int updateByPrimaryKeySelective(Ee17 record);
	
	//修改 意见
	int updateAdviceSelective(Ee17 record);
	
    int updateByPrimaryKey(Ee17 record);
   
  //分页查询+查询获取意见信息
  	List<Ee17> getEe17List(Ee17 ee17);
    
    // 根据标题查信息
	List<Ee17> selectByHui(Ee17 aee150);
	
	//批量删除
	int batDeleteData(String[] ids); 
	
	
	int batUpdateReleaseStateData(String[] ids);

}

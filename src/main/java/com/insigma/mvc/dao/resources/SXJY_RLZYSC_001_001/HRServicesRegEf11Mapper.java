package com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001;


import java.util.List;

import com.insigma.mvc.model.Ac01;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;

public interface HRServicesRegEf11Mapper {
	/**
	 *保存数据 
	 * */
	 //判断机构名是否一致
    int selectByAef104(Ef11 record);
     //判断统一信用代码是否一致
    int selectByAab998(Ef11 record);
     //添加数据-保存
  	int insertSelective(Ef11 record);
     //根据机构名字查询ef11所有数据
    Ef11 selectEf11ByAef101(String aef101);
    //添加新的职介字段
    int insertNewSelective(EF11_DECLARE record);
	
    /**
     * 更改数据
     * */
   //查询全部数据=初始化获取相应的数据——getEf11List
    List<Ef11> getEf11List(Ef11 ef11 );
   
    //根据Id查询数据
    Ef11 selectByPrimaryKey(String aef101);
    
    Ef11 selectAgencyInfoByAab998(String aab998);
    
    Ef11 selectByPrimaryKeyAef(String aef101);
    
    /**
     * 更改+注销数据
     * */
    
    //根据主键修改对应的数据
    int updateByPrimaryKeySelective(Ef11 record);	
  	
   
  	
    
    
    
	//根据主键删除数据
    int deleteByPrimaryKey(String aef101);
    
   
    //批量删除
    int batDeleteData(String []  ids);
    //根据对象获取行对象
    int selectByAef101(String aef101);
   
    
    //插入文本框值
	int insertTextSelective(Ef11 ef11);
	//插入复选框值
	int insertCheckSelective(Ef11 ef11);
	//获取机构名称
	List<Ef11> getAef104ByAab998(String aab998);
	//获取ef11
	Ef11 getEf11Info(String aef101);
	
	

}

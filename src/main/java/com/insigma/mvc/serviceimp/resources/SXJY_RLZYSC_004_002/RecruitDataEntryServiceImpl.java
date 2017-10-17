package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_004_002;

import java.util.HashMap;


import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_002.RecruitDataEntryEe15Mapper;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_004_002.RecruitDataEntryService;

@Component("RecruitDataEntryService")
public class RecruitDataEntryServiceImpl extends MvcHelper implements RecruitDataEntryService{

	//调用dao(mapper接口)的方法
	@Resource
	private RecruitDataEntryEe15Mapper recruitDataEntryEe15Mapper;
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	
	
	@Override
	public HashMap<String, Object> getRecruitData(Ee15 ee15) {
		// TODO Auto-generated method stub
		PageHelper.offsetPage(ee15.getOffset(),ee15.getLimit()); 
	/*	ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());//申报用户对应的统一社会信用代码
*/		List<Ee15> list=recruitDataEntryEe15Mapper.getRecruitDataList(ee15);
		PageInfo<Ee15> pageinfo = new PageInfo<Ee15>(list);
		return this.success_hashmap_response(pageinfo);
	}

	//通过机构编号获取机构的名称
	@Override
	public Ef11 getOrganizationNameById(String aef101) {
		// TODO Auto-generated method stub
		return hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
	}

	/**
	 * 保存招聘数据
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveRecruitData(Ee15 ee15) {
		//ee15.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
	/*	ee15.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ee15.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee15.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
		ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());*/
		Ee15 eee151 = recruitDataEntryEe15Mapper.selectByPrimaryKeyOne(ee15);
		if(eee151!=null){
			return this.error("数据所属期已存在，请重新输入");
		}else{
		if(ee15.getEee151()==null || "".equals(ee15.getEee151())){
			//执行新增操作
			int insertNum = recruitDataEntryEe15Mapper.insertSelective(ee15);
			if(insertNum==1 ){
				return this.success("您好,人力资源招聘数据录入成功！");
			}else{
				return this.error("您好,人力资源招聘数据录入失败！");
			}
		}else{
			//执行更新操作
			int updateNum = recruitDataEntryEe15Mapper.updateByPrimaryKeySelective(ee15);
			if(updateNum==1 ){
				return this.success("您好,人力资源招聘数据修改成功！");
			}else{
				return this.error("您好,人力资源招聘数据修改失败！");
			}
		}
		
		}
	}

	/**
	 * 修改界面获取数据
	 */
	@Override
	public Ee15 getEe15ById(String eee151) {
		// TODO Auto-generated method stub
		return recruitDataEntryEe15Mapper.selectByPrimaryKey(eee151);
	}

	@Override
	public AjaxReturnMsg upDateAgencyData(Ee15 ee15) {
		// TODO Auto-generated method stub		
		int updateNum = recruitDataEntryEe15Mapper.updateByPrimaryKeySelective(ee15);
		if(updateNum==1 ){
			return this.success("您好,修改人力资源招聘数据成功！");
		}else{
			return this.error("您好,修改人力资源招聘数据失败！");
		}
	}

	@Override
	public HashMap<String, Object> getData(Ee15 ee15) {
		// TODO Auto-generated method stub
		PageHelper.offsetPage(ee15.getOffset(),ee15.getLimit()); 
		List<Ee15> list=recruitDataEntryEe15Mapper.getListData(ee15);
		PageInfo<Ee15> pageinfo = new PageInfo<Ee15>(list);
		return this.success_hashmap_response(pageinfo);
	}
	

}

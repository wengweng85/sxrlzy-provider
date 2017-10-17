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

	//����dao(mapper�ӿ�)�ķ���
	@Resource
	private RecruitDataEntryEe15Mapper recruitDataEntryEe15Mapper;
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	
	
	@Override
	public HashMap<String, Object> getRecruitData(Ee15 ee15) {
		// TODO Auto-generated method stub
		PageHelper.offsetPage(ee15.getOffset(),ee15.getLimit()); 
	/*	ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());//�걨�û���Ӧ��ͳһ������ô���
*/		List<Ee15> list=recruitDataEntryEe15Mapper.getRecruitDataList(ee15);
		PageInfo<Ee15> pageinfo = new PageInfo<Ee15>(list);
		return this.success_hashmap_response(pageinfo);
	}

	//ͨ��������Ż�ȡ����������
	@Override
	public Ef11 getOrganizationNameById(String aef101) {
		// TODO Auto-generated method stub
		return hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
	}

	/**
	 * ������Ƹ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveRecruitData(Ee15 ee15) {
		//ee15.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
	/*	ee15.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ee15.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ee15.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
		ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());*/
		Ee15 eee151 = recruitDataEntryEe15Mapper.selectByPrimaryKeyOne(ee15);
		if(eee151!=null){
			return this.error("�����������Ѵ��ڣ�����������");
		}else{
		if(ee15.getEee151()==null || "".equals(ee15.getEee151())){
			//ִ����������
			int insertNum = recruitDataEntryEe15Mapper.insertSelective(ee15);
			if(insertNum==1 ){
				return this.success("����,������Դ��Ƹ����¼��ɹ���");
			}else{
				return this.error("����,������Դ��Ƹ����¼��ʧ�ܣ�");
			}
		}else{
			//ִ�и��²���
			int updateNum = recruitDataEntryEe15Mapper.updateByPrimaryKeySelective(ee15);
			if(updateNum==1 ){
				return this.success("����,������Դ��Ƹ�����޸ĳɹ���");
			}else{
				return this.error("����,������Դ��Ƹ�����޸�ʧ�ܣ�");
			}
		}
		
		}
	}

	/**
	 * �޸Ľ����ȡ����
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
			return this.success("����,�޸�������Դ��Ƹ���ݳɹ���");
		}else{
			return this.error("����,�޸�������Դ��Ƹ����ʧ�ܣ�");
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

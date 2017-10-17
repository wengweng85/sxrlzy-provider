package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_007_002;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_007_002.InfoBusinessEE18Mapper;
import com.insigma.mvc.model.EE18;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_007_002.InfoBusinessEE18Service;

@Component("InfoBusinessEE18Service")
public class InfoBusinessEE18ServiceImpl extends MvcHelper implements InfoBusinessEE18Service{

	@Resource
	private InfoBusinessEE18Mapper infoBusinessEE18Mapper;
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	
	/**
	 * ��ȡ����ҵ����Ϣ
	 */
	@Override
	public HashMap<String, Object> getRecruitData(EE18 ee18) {
		// TODO Auto-generated method stub
		PageHelper.offsetPage(ee18.getOffset(),ee18.getLimit());
		/*ee18.setAab998(SysUserUtil.getCurrentUser().getAab998());//�걨�û���Ӧ��ͳһ������ô���
*/		List<EE18> list = infoBusinessEE18Mapper.getRecruitDataList(ee18);
		PageInfo<EE18> pageinfo = new PageInfo<EE18>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * ͨ��������Ż�ȡ��������
	 */
	@Override
	public Ef11 getOrganizationNameById(String aef101) {
		// TODO Auto-generated method stub
		return hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
	}
	@Override
	public EE18 getEe18ById(String eee181) {
		// TODO Auto-generated method stub
		return infoBusinessEE18Mapper.selectByPrimaryKey(eee181);
	}
	
	

}

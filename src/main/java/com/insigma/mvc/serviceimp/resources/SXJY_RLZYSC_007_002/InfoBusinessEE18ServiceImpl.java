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
	 * 获取机构业务信息
	 */
	@Override
	public HashMap<String, Object> getRecruitData(EE18 ee18) {
		// TODO Auto-generated method stub
		PageHelper.offsetPage(ee18.getOffset(),ee18.getLimit());
		/*ee18.setAab998(SysUserUtil.getCurrentUser().getAab998());//申报用户对应的统一社会信用代码
*/		List<EE18> list = infoBusinessEE18Mapper.getRecruitDataList(ee18);
		PageInfo<EE18> pageinfo = new PageInfo<EE18>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * 通过机构编号获取机构名称
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

package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_003;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.BusinessReportEe16Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.RecruitmentDataEe15Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ee16;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_003.RecruitmentDataEe15Service;

@Component("RecruitmentDataEe15Service")
public class RecruitmentDataEe15ServiceImpl  extends MvcHelper implements RecruitmentDataEe15Service{

	@Resource
	private   RecruitmentDataEe15Mapper    recruitmentDataEe15Mapper;
	@Resource
	private   BusinessReportEe16Mapper     businessReportEe16Mapper;
	@Resource
	private   HRAgencyApplyMapper   hragencyApplyMapper;
	
	@Override
	public List<Ee15> getEe15ById(Ee15 aef101) {
		return recruitmentDataEe15Mapper.selectByAef101(aef101);
	}
	@Override
	public List<Ee16> getEe16ById(String aef101) {
		return businessReportEe16Mapper.selectByAef101(aef101);
	}
	//审核业务报告
	@Override
	public List<Ee15> getEe15ByAab998(Ee15 ee15) {
		
		return recruitmentDataEe15Mapper.getEe15ByAab998(ee15);
	}
	@Override
	public AjaxReturnMsg getEe15ByAef101(String id) {
		
		return null;
	}
	//单个查询EE15数据
	@Override
	public  List<Ee15> getEe15SingleByAab998(Ee15 ee15) {
		
		return recruitmentDataEe15Mapper.getEe15singleByAab998(ee15);
	}

}

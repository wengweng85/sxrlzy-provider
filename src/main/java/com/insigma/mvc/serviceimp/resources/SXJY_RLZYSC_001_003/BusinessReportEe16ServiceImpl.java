package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_003;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.BusinessReportEe16Mapper;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_003.BusinessReportEe16Service;


@Component("BusinessReportEe16Service")
public class BusinessReportEe16ServiceImpl  extends MvcHelper  implements BusinessReportEe16Service {
	
	@Resource
	private  BusinessReportEe16Mapper businessReportEe16Mapper;
	
	
	
	

	
}

package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_003;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.BusinessRecruitmentEe18Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.RecruitmentDataEe15Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_002.RecruitDataEntryEe15Mapper;
import com.insigma.mvc.model.EE18;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_003.BusinessRecruitmentEe18Service;
import com.insigma.mvc.utils.HRAgencyUtils;

@Component("BusinessRecruitmentEe18Service")
public class BusinessRecruitmentEe18ServiceImpl  extends MvcHelper implements BusinessRecruitmentEe18Service{
	@Resource
	private BusinessRecruitmentEe18Mapper businessRecruitmentEe18Mapper;
	@Resource
	private RecruitmentDataEe15Mapper  recruitmentDataEe15Mapper;
	@Resource
	private HRServicesRegEf11Mapper  hrServicesRegEf11Mapper;
	@Resource
	private HRAgencyApplyEf12Mapper hrAgencyApplyEf12Mapper;
	
	@Resource
	private RecruitDataEntryEe15Mapper recruitDataEntryEe15Mapper;
	
	
	/**
	 * 管理机构业务报告保存
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveData(EE18 ee18) {
		
		/*ee18.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ee18.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee18.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
		ee18.setAab301(SysUserUtil.getCurrentUser().getAab301());*/
		Ee15 tempEe15 = new Ee15();
		tempEe15.setAef101(ee18.getAef101());
		List<Ee15> ee15_ = recruitDataEntryEe15Mapper.getRecruitDataList(tempEe15);
		if(ee15_.size()==0){
			return this.error("您好,该服务机构未录入业务数据,请先录入数据再做业务报告");
		}
		
		 int insertEe18Num =0;
		 //判断EF12表该机构业务报告是否已经审核
		 Ef12 ef12_ = new Ef12();
		 ef12_.setAef101(ee18.getAef101());
		 ef12_.setAee150(ee18.getAee150());
		 List<Ef12> ef12 = hrAgencyApplyEf12Mapper.selectBusinessReportByAef101(ef12_);
		 if(ef12.size()>0){
			 return this.error("您好,该人力资源服务机构"+ee18.getAee150()+"年度业务报告已经审核通过，请勿重新申请。");
		 }
		 //判断该机构当前年度是否已经录入数据
	     EE18 ee18_  = businessRecruitmentEe18Mapper.selectBusinessReportInfoByAef101(ee18);
	     if(ee18_==null){
	    	 insertEe18Num = businessRecruitmentEe18Mapper.insertSelective(ee18);
	     }else {
	    	 ee18.setEee181(ee18_.getEee181());
	    	 insertEe18Num = businessRecruitmentEe18Mapper.updateByPrimaryKeySelective(ee18);
	     }
	     int insertEf12Num = insertEf12ByAef101(ee18.getAef101(),HRAgencyUtils.AEF132_3);
		 if(insertEe18Num==1 && insertEf12Num==1){
              return this.success("您好,人力资源服务机构业务报告业务办理成功。");
		 }else{
		      return this.error("您好,人力资源服务机构业务报告业务办理失败。");
	     }
	}
			
		

	//保存写入业务变更
	private int insertEf12ByAef101(String aef101, String aef132) {
		
		
		 Ef12 ef12 = new Ef12();
         ef12.setAef101(aef101);
		 Ef11 ef11=hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
		 ef12.setAef104(ef11.getAef104());
		 ef12.setAab301(ef11.getAef134());
		 ef12.setAab998(ef11.getAab998());
	
		if((HRAgencyUtils.AEF132_1).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_1); //变更类型为机构设立
		}else if((HRAgencyUtils.AEF132_2).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_2); //变更类型为机构变更
		}else if((HRAgencyUtils.AEF132_3).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_3); //变更类型为机构业务报告
		}else if((HRAgencyUtils.AEF132_4).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_4); //变更类型为机构注销
		}
		
		ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
		ef12.setAef132(HRAgencyUtils.AEF132_3);//业务报告类型
		ef12.setAef133(HRAgencyUtils.AEF133_1);//初审
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		
		int insertEf12 =hrAgencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}


	/**
	 * 服务机构业务办理
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveauditingData(EE18 ee18) {
		
		/*ee18.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ee18.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee18.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		
		Ee15 tempEe15 = new Ee15();
		tempEe15.setAef101(ee18.getAef101());
		List<Ee15> ee15_ = recruitDataEntryEe15Mapper.getRecruitDataList(tempEe15);
		if(ee15_.size()==0){
			return this.error("您好,该服务机构未录入业务数据,请先录入数据再做业务报告");
		}
		
		int insertEe18Num =0;
		
		 ee18.setAef101(ee18.getAef101());
		 /*ee18.setAab301(SysUserUtil.getCurrentUser().getGroupid());*/
		 //判断EF12表该机构业务报告是否已经审核
		 Ef12 ef12_ = new Ef12();
		 ef12_.setAef101(ee18.getAef101());
		 ef12_.setAee150(ee18.getAee150());
		 List<Ef12> ef12 = hrAgencyApplyEf12Mapper.selectBusinessReportByAef101(ef12_);
		 if(ef12.size()>0){
			 return this.error("您好,该人力资源服务机构"+ee18.getAee150()+"年度业务报告已经审核通过，请勿重新申请。");
		 }
		 //判断该机构当前年度是否已经录入数据
	     EE18 ee18_  = businessRecruitmentEe18Mapper.selectBusinessReportInfoByAef101(ee18);
	     if(ee18_==null){
	    	 insertEe18Num = businessRecruitmentEe18Mapper.insertSelective(ee18);
	     }else {
	    	 ee18.setEee181(ee18_.getEee181());
	    	 insertEe18Num = businessRecruitmentEe18Mapper.updateByPrimaryKeySelective(ee18);
	     }
	     int insertEf12Num = insertEf12ByAef101(ee18.getAef101(),HRAgencyUtils.AEF132_3);
		 if(insertEe18Num==1 && insertEf12Num==1){
              return this.success("您好,人力资源服务机构业务报告申请成功。");
		 }else{
		      return this.error("您好,人力资源服务机构业务报告申请失败。");
	     }			 

		}
		
}
		


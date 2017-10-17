package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_002_002;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_002_002.HRAgencyEditApplyService;
import com.insigma.mvc.utils.HRAgencyUtils;

@Component("HRAgencyEditApplyService")
public class HRAgencyEditApplyServiceImpl   extends MvcHelper implements HRAgencyEditApplyService{
	
	@Resource
	private HRAgencyApplyMapper hragencyApplyMapper;
	
	@Resource
	private EF11_DECLAREMapper ef11_declareMapper;
	
	@Resource
	private HRAgencyApplyEf12Mapper hragencyApplyEf12Mapper;

	/**
	 * 机构信息变更
	 */
	@Override
	@Transactional
	public AjaxReturnMsg editEf11Data(Ef11 ef11) {
		
		ef11.setAae010(ef11.getAef121());//经办人姓名
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		String aef132 = "";//机构业务类型
		//执行更新操作
		//根据机构编号查询返回集合并获取对应字段值
		List<Ef11> list=hragencyApplyMapper.getEf11List(ef11);
		String eae052 ="",aef133="";
		if(list.get(0).getEae052()==null ){
			eae052 ="";
		}else {
			eae052 =list.get(0).getEae052().toString();
		}
		if(list.get(0).getAef133()==null ){
			aef133 = "";
		}else {
			aef133 = list.get(0).getAef133().toString();
		}
		aef132 = HRAgencyUtils.AEF132_2;
		
		//初审审核不通过退回时，机构重新修改申请记录将审核状态修改为未审核
		if((HRAgencyUtils.EAE052_9).equals(eae052)&&(HRAgencyUtils.AEF133_1).equals(aef133)){
			ef11.setEae052(HRAgencyUtils.EAE052_0);//未审核
		}
		//更新EF11表
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//插入机构变更EF12表
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("人力资源服务机构修改申请成功，请等待审核！");
		}else{
			return this.error("人力资源服务机修改申请失败,请确认此机构是否正常！");
		}
			
	}

	/**
	 * 写入机构业务变更表
	 * @param aab998
	 * @param aef132
	 * @return
	 */
	public int  insertEf12byAef104(String aab998,String aef132) {
		EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(aab998);
		Ef11 ef11 = hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998);
		
		Ef12 ef12 = new Ef12();
		ef12.setAef101(ef11.getAef101());
		ef12.setAab998(ef11_declare.getAab998());
		ef12.setAef104(ef11_declare.getAef104());
		ef12.setAab301(ef11_declare.getAef134());
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
		ef12.setAef133(HRAgencyUtils.AEF133_1);
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		ef12.setAae010(ef11_declare.getAef121());//经办人姓名
		/*ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}

}

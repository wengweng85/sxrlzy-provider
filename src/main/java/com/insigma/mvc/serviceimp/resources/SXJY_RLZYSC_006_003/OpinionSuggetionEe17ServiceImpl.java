package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_006_003;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_006_003.OpinionSuggetionEe17Mapper;
import com.insigma.mvc.model.Ee17;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_006_003.OpinionSuggetionEe17Service;
import com.insigma.mvc.utils.HRAgencyUtils;

@Component("OpinionSuggetionEe17Service")
public class OpinionSuggetionEe17ServiceImpl extends MvcHelper<Ee17> implements OpinionSuggetionEe17Service{
    
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	@Resource
	private OpinionSuggetionEe17Mapper opinionSuggetionEe17Mapper;
	@Resource
	private HRAgencyApplyMapper hragencyApplyMapper;
	
	 /**
	 * 查询意见信息+分页查询
	 */
	@Override
	public HashMap<String, Object> getEe17List(Ee17 ee17) {		
		PageHelper.offsetPage(ee17.getOffset(), ee17.getLimit());
		/*ee17.setAab998(SysUserUtil.getCurrentUser().getAab998());//申报用户对应的统一社会信用代码
*/		List<Ee17> list = new ArrayList<Ee17>();
		if(StringUtils.isNotEmpty(ee17.getAee152())){			
			ee17.setA_aee152(ee17.getAee152().split(","));
		}
		 list =	opinionSuggetionEe17Mapper.getEe17List(ee17);
		 PageInfo<Ee17> pageinfo = new PageInfo<Ee17>(list);
		return this.success_hashmap_response(pageinfo);	
	}
	
	/**
	 * 条件查询意见信息
	 */
	@Override
	public HashMap<String, Object> selectHui(Ee17 ee17) {
			PageHelper.offsetPage(ee17.getOffset(), ee17.getLimit());
			/*ee17.setAab998(SysUserUtil.getCurrentUser().getAab998());//申报用户对应的统一社会信用代码
*/			List<Ee17> list = new ArrayList<Ee17>();
			if(StringUtils.isNotEmpty(ee17.getAee152())){
				ee17.setA_aee152(ee17.getAee152().split(","));
			}
			/* ee17.setAab301(SysUserUtil.getCurrentUser().getAab301());//行政区划代码
*/			 list =	opinionSuggetionEe17Mapper.selectByHui(ee17);
			 PageInfo<Ee17> pageinfo = new PageInfo<Ee17>(list);
			return this.success_hashmap_response(pageinfo);
					
	}
	
	/**
	 * 根据信息发布ID查询详细信息
	 */
	@Override
	public Ee17 getEe17NameById(String eee171) {
		// TODO Auto-generated method stub
		return opinionSuggetionEe17Mapper.selectNameByPrimaryKey(eee171);
		
	}
	
	
	/**
	 * 增加服务机构意见建议管理
	 */
	@Override
	public AjaxReturnMsg addSuggetion(Ee17 ee17,Ef11 ef11) {
	    ee17.setAee152(HRAgencyUtils.AEE152_0);//未回复状态
	    ee17.setAae100(HRAgencyUtils.AAE100_1);// 有效
	      
	    	//获取ID,执行添加意见操作
	    	int insertNum = opinionSuggetionEe17Mapper.insertSelective(ee17);
	    	if(insertNum==1 ){
	    		return this.success("您好，添加服务机构意见建议成功");
			}else{
				return this.error("您好，添加服务机构意见建议失败！");
			}		
	}
	
    /**
     * 修改服务机构意见建议管理
     */
	@Override
	public AjaxReturnMsg updateSuggetion(Ee17 ee17) {		 
                //ee17.setAee154(new Date());//提交建议时间
			   	//执行修改意见操作
		    	if(ee17.getAee150()!=null || ee17.getAee151()!=null ){
//		    		ee17.setAee152(HRAgencyUtils.AEE152_0);
		    		opinionSuggetionEe17Mapper.updateAdviceSelective(ee17);
		    		return this.success("您好，修改服务机构意见建议成功"); 	
				}else{
					return this.error("您好，修改服务机构意见建议失败！");				
				}	
}			
	/**
	 *  受理服务机构意见
	 */
	@Override
	public AjaxReturnMsg replySuggetion(Ee17 ee17) {
		if(ee17.getAee150()!=null || ee17.getAee151()!=null ){
			ee17.setAee152(HRAgencyUtils.AEE152_1);
    		opinionSuggetionEe17Mapper.updateByPrimaryKeySelective(ee17);
    		return this.success("您好，受理服务机构意见建议成功"); 	
		}else{
			return this.error("您好，受理服务机构意见建议失败！");				
		}	
		
	}

	
	/**
	 * 删除意见建议
	 */

//	@Override
//	public AjaxReturnMsg batDeleteDemoData(Ee17 ee17) {
//		String [] ids=ee17.getSelectnodes().split(",");
//		int batdeletenum=opinionSuggetionEe17Mapper.batDeleteData(ids);
//		if(batdeletenum==ids.length){
//			return this.success("批量删除成功");
//		}else{
//			return this.success("批量删除成功,但存在失败的数据,请检查");
//		}
//	}
	
	@Override
	public AjaxReturnMsg<String> batDeleteReleasedata(Ee17 ee17) {
		String [] ids=ee17.getSelectnodes().split(",");
		int batdeletenum=opinionSuggetionEe17Mapper.batUpdateReleaseStateData(ids);
		if(batdeletenum==ids.length){
			return this.success("批量删除成功");
		}else{
			return this.success("批量删除成功,但存在失败的数据,请检查");
		}
		
	}

	@Override
	public Ee17 getEe17ById(String eee171) {
		
		return opinionSuggetionEe17Mapper.selectNameByPrimaryKey(eee171);
	}


	//通过机构编号获取机构的名称
		@Override
		public Ef11 getOrganizationNameById(String aab998) {
			// TODO Auto-generated method stub
			return hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998);
		}

		@Override
		public Ee17 getEe17ByIdOpin(String eee171) {
			// TODO Auto-generated method stub
			return opinionSuggetionEe17Mapper.selectByPrimaryOpoinion(eee171);
		}	

	




}

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
	 * ��ѯ�����Ϣ+��ҳ��ѯ
	 */
	@Override
	public HashMap<String, Object> getEe17List(Ee17 ee17) {		
		PageHelper.offsetPage(ee17.getOffset(), ee17.getLimit());
		/*ee17.setAab998(SysUserUtil.getCurrentUser().getAab998());//�걨�û���Ӧ��ͳһ������ô���
*/		List<Ee17> list = new ArrayList<Ee17>();
		if(StringUtils.isNotEmpty(ee17.getAee152())){			
			ee17.setA_aee152(ee17.getAee152().split(","));
		}
		 list =	opinionSuggetionEe17Mapper.getEe17List(ee17);
		 PageInfo<Ee17> pageinfo = new PageInfo<Ee17>(list);
		return this.success_hashmap_response(pageinfo);	
	}
	
	/**
	 * ������ѯ�����Ϣ
	 */
	@Override
	public HashMap<String, Object> selectHui(Ee17 ee17) {
			PageHelper.offsetPage(ee17.getOffset(), ee17.getLimit());
			/*ee17.setAab998(SysUserUtil.getCurrentUser().getAab998());//�걨�û���Ӧ��ͳһ������ô���
*/			List<Ee17> list = new ArrayList<Ee17>();
			if(StringUtils.isNotEmpty(ee17.getAee152())){
				ee17.setA_aee152(ee17.getAee152().split(","));
			}
			/* ee17.setAab301(SysUserUtil.getCurrentUser().getAab301());//������������
*/			 list =	opinionSuggetionEe17Mapper.selectByHui(ee17);
			 PageInfo<Ee17> pageinfo = new PageInfo<Ee17>(list);
			return this.success_hashmap_response(pageinfo);
					
	}
	
	/**
	 * ������Ϣ����ID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ee17 getEe17NameById(String eee171) {
		// TODO Auto-generated method stub
		return opinionSuggetionEe17Mapper.selectNameByPrimaryKey(eee171);
		
	}
	
	
	/**
	 * ���ӷ����������������
	 */
	@Override
	public AjaxReturnMsg addSuggetion(Ee17 ee17,Ef11 ef11) {
	    ee17.setAee152(HRAgencyUtils.AEE152_0);//δ�ظ�״̬
	    ee17.setAae100(HRAgencyUtils.AAE100_1);// ��Ч
	      
	    	//��ȡID,ִ������������
	    	int insertNum = opinionSuggetionEe17Mapper.insertSelective(ee17);
	    	if(insertNum==1 ){
	    		return this.success("���ã���ӷ�������������ɹ�");
			}else{
				return this.error("���ã���ӷ�������������ʧ�ܣ�");
			}		
	}
	
    /**
     * �޸ķ����������������
     */
	@Override
	public AjaxReturnMsg updateSuggetion(Ee17 ee17) {		 
                //ee17.setAee154(new Date());//�ύ����ʱ��
			   	//ִ���޸��������
		    	if(ee17.getAee150()!=null || ee17.getAee151()!=null ){
//		    		ee17.setAee152(HRAgencyUtils.AEE152_0);
		    		opinionSuggetionEe17Mapper.updateAdviceSelective(ee17);
		    		return this.success("���ã��޸ķ�������������ɹ�"); 	
				}else{
					return this.error("���ã��޸ķ�������������ʧ�ܣ�");				
				}	
}			
	/**
	 *  �������������
	 */
	@Override
	public AjaxReturnMsg replySuggetion(Ee17 ee17) {
		if(ee17.getAee150()!=null || ee17.getAee151()!=null ){
			ee17.setAee152(HRAgencyUtils.AEE152_1);
    		opinionSuggetionEe17Mapper.updateByPrimaryKeySelective(ee17);
    		return this.success("���ã������������������ɹ�"); 	
		}else{
			return this.error("���ã������������������ʧ�ܣ�");				
		}	
		
	}

	
	/**
	 * ɾ���������
	 */

//	@Override
//	public AjaxReturnMsg batDeleteDemoData(Ee17 ee17) {
//		String [] ids=ee17.getSelectnodes().split(",");
//		int batdeletenum=opinionSuggetionEe17Mapper.batDeleteData(ids);
//		if(batdeletenum==ids.length){
//			return this.success("����ɾ���ɹ�");
//		}else{
//			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
//		}
//	}
	
	@Override
	public AjaxReturnMsg<String> batDeleteReleasedata(Ee17 ee17) {
		String [] ids=ee17.getSelectnodes().split(",");
		int batdeletenum=opinionSuggetionEe17Mapper.batUpdateReleaseStateData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}
		
	}

	@Override
	public Ee17 getEe17ById(String eee171) {
		
		return opinionSuggetionEe17Mapper.selectNameByPrimaryKey(eee171);
	}


	//ͨ��������Ż�ȡ����������
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

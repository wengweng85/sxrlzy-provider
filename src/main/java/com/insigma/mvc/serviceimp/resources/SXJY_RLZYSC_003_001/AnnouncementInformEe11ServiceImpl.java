package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_003_001;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Element;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.common.util.EhCacheUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_003_001.AnnouncementInformEe11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_003_001.ReceivingAgencyEe12Mapper;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.Ee11;
import com.insigma.mvc.model.Ee12;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_003_001.AnnouncementInformEe11Service;
import com.insigma.mvc.utils.HRAgencyUtils;


@Component("AnnouncementInformEe11Service")
public class AnnouncementInformEe11ServiceImpl extends MvcHelper  implements AnnouncementInformEe11Service{

	@Resource
	private ReceivingAgencyEe12Mapper receivingAgencyEe12Mapper;
    @Resource
	private AnnouncementInformEe11Mapper announcementInformEe11Mapper;
    @Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
    
    /**
	 * ����
	 */
	@SuppressWarnings("unused")
	@Override
	public AjaxReturnMsg saveDemoData(Ee11 ee11) {
		//ee11.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
	/*	ee11.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������=������
		ee11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ee11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		ee11.setAae036(new Date());//����ʱ��
		int insertnum =0;
		//��ȡ����������
		Ef11 ef11=new Ef11();
		List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
		 ee11.setAab301(list.get(0).getAef107());//��������ϵ�������ز�����������
		 ee11.setAae100(HRAgencyUtils.AAE100_1);//��Ч
		 if(ee11.getEee111()==null){
			 insertnum= announcementInformEe11Mapper.insertSelective (ee11);//���뵽ee11
		     int insertEe12 =insertEe12byEee111(ee11.getEee111());//���뵽���ܻ���(ee12)
		 }else {
			 insertnum= announcementInformEe11Mapper.updateByPrimaryKey(ee11);//���뵽ee11
		 }
	 	 if(insertnum == 1){
			return this.success("������Ϣ�����ɹ���");
			
		  }else{
			return this.error("������Ϣ�޸�ʧ�ܡ�");
		  }	
		
		
		}
	    /**
		 * ������뵽ee12��(���ܻ���)
		 */
	 private int insertEe12byEee111(String eee111) {
		 //��ѯ��ee11����
		 Ee11 new_ee11 = announcementInformEe11Mapper.selectByPrimaryKey(eee111);
		 //����ee12����
		 Ee12 ee12=new Ee12();
		 ee12.setEee111(eee111);
		 ee12.setAab301(new_ee11.getAab301());
		 ee12.setAef101(new_ee11.getAef101());
		 ee12.setAae100("0"); //Ĭ������Ч��
	     int insertEf12 = receivingAgencyEe12Mapper.insertSelective(ee12);
		return insertEf12;
	 }
	 
		 /**
		 * ��ת����ҳ(������Ϣ)
		 */
		@Override
		public Ee11 getEe11NameById(String eee111) {
			// TODO Auto-generated method stub
			return announcementInformEe11Mapper.selectNameByPrimaryKey(eee111);
		}
		
		 /**
		 * ��ѯ������Ϣ+��ҳ��ѯ
		 */   
		@Override
		public HashMap<String, Object> getEe11List(Ee11 ee11) {
				PageHelper.offsetPage(ee11.getOffset(), ee11.getLimit());
				List<Ee11> list = announcementInformEe11Mapper.getEe11List(ee11);
				PageInfo<Ee11> pageinfo = new PageInfo<Ee11>(list);
				return this.success_hashmap_response(pageinfo);
			}
		/**
		 *   
		 *ͨ��idɾ����ԱEe11��Ϣ 
		 * */
		@Override
		public AjaxReturnMsg deleteDemoById(String eee111) {
			int deletenum=announcementInformEe11Mapper.deleteByPrimaryKey(eee111);
			if(deletenum==1){
				return this.success("ɾ���ɹ�");
			}else{
				return this.error("ɾ��ʧ��,��ȷ�������Ѿ���ɾ����");
			}
		}
		
		/**
		 * 
		 *ͨ������ɾ��Ee11��Ϣ
		 * */
		@Override
		public AjaxReturnMsg batDeleteDemoData(Ee11 ee11) {
			String [] ids=ee11.getSelectnodes().split(",");
			int batdeletenum=announcementInformEe11Mapper.batDeleteData(ids);
			if(batdeletenum==ids.length){
				return this.success("����ɾ���ɹ�");
			}else{
				return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
			}
		}
		/**
		 * 
		 *ͨ��������ȡEe11��Ϣ
		 * */
		@Override
		public Ee11 getEe11ById(String eee111) {
			// TODO Auto-generated method stub
			return announcementInformEe11Mapper.selectByPrimaryKey(eee111);
		}
	
		@Override
		public List<CodeValue> getCodeValueTree(CodeValue codevalue) {
			 return announcementInformEe11Mapper.getCodeValueTree(codevalue);
		}
		/**
		 * �ӻ����л�ȡ����ֵ
		 * 
		 * @param request
		 * @param response
		 * @return
		 * @throws com.insigma.resolver.AppException
		 */
		@Override
		public HashMap<String, List<CodeValue>> getCodeValueFromCache(CodeValue codevalue) {
			Element element = EhCacheUtil.getManager().getCache("webcache").get(codevalue.getCode_type().toUpperCase());
			List<CodeValue> list = (List<CodeValue>) element.getValue();
			HashMap map=new HashMap();
			map.put("value", list);
			return map;
		}
		//���������
		@Override
		public List<CodeValue> getAllGroupList(CodeValue codevalue) {
			 return announcementInformEe11Mapper.getAllGroupList(codevalue);   
		}
		
		/**
		 * ��ȡ������Ϣ
		 */
		@Override
		public AjaxReturnMsg getGroupDataById(String groupid) {
			return this.success(announcementInformEe11Mapper.getGroupDataById(groupid));
		}
		

		}
		
		

	




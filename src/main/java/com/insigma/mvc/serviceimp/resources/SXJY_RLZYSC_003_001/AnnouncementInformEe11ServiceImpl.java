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
	 * 保存
	 */
	@SuppressWarnings("unused")
	@Override
	public AjaxReturnMsg saveDemoData(Ee11 ee11) {
		//ee11.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
	/*	ee11.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名=发布者
		ee11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		ee11.setAae036(new Date());//经办时间
		int insertnum =0;
		//获取到行政区划
		Ef11 ef11=new Ef11();
		List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
		 ee11.setAab301(list.get(0).getAef107());//将隶属关系所属区县插入行政区划
		 ee11.setAae100(HRAgencyUtils.AAE100_1);//有效
		 if(ee11.getEee111()==null){
			 insertnum= announcementInformEe11Mapper.insertSelective (ee11);//插入到ee11
		     int insertEe12 =insertEe12byEee111(ee11.getEee111());//插入到接受机构(ee12)
		 }else {
			 insertnum= announcementInformEe11Mapper.updateByPrimaryKey(ee11);//插入到ee11
		 }
	 	 if(insertnum == 1){
			return this.success("公告信息操作成功。");
			
		  }else{
			return this.error("公告信息修改失败。");
		  }	
		
		
		}
	    /**
		 * 公告插入到ee12表(接受机构)
		 */
	 private int insertEe12byEee111(String eee111) {
		 //查询出ee11数据
		 Ee11 new_ee11 = announcementInformEe11Mapper.selectByPrimaryKey(eee111);
		 //创建ee12对象
		 Ee12 ee12=new Ee12();
		 ee12.setEee111(eee111);
		 ee12.setAab301(new_ee11.getAab301());
		 ee12.setAef101(new_ee11.getAef101());
		 ee12.setAae100("0"); //默认是有效的
	     int insertEf12 = receivingAgencyEe12Mapper.insertSelective(ee12);
		return insertEf12;
	 }
	 
		 /**
		 * 跳转到主页(公告信息)
		 */
		@Override
		public Ee11 getEe11NameById(String eee111) {
			// TODO Auto-generated method stub
			return announcementInformEe11Mapper.selectNameByPrimaryKey(eee111);
		}
		
		 /**
		 * 查询公告信息+分页查询
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
		 *通过id删除人员Ee11信息 
		 * */
		@Override
		public AjaxReturnMsg deleteDemoById(String eee111) {
			int deletenum=announcementInformEe11Mapper.deleteByPrimaryKey(eee111);
			if(deletenum==1){
				return this.success("删除成功");
			}else{
				return this.error("删除失败,请确定此人已经被删除了");
			}
		}
		
		/**
		 * 
		 *通过批量删除Ee11信息
		 * */
		@Override
		public AjaxReturnMsg batDeleteDemoData(Ee11 ee11) {
			String [] ids=ee11.getSelectnodes().split(",");
			int batdeletenum=announcementInformEe11Mapper.batDeleteData(ids);
			if(batdeletenum==ids.length){
				return this.success("批量删除成功");
			}else{
				return this.success("批量删除成功,但存在失败的数据,请检查");
			}
		}
		/**
		 * 
		 *通过主键获取Ee11信息
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
		 * 从缓存中获取代码值
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
		//服务机构树
		@Override
		public List<CodeValue> getAllGroupList(CodeValue codevalue) {
			 return announcementInformEe11Mapper.getAllGroupList(codevalue);   
		}
		
		/**
		 * 获取机构信息
		 */
		@Override
		public AjaxReturnMsg getGroupDataById(String groupid) {
			return this.success(announcementInformEe11Mapper.getGroupDataById(groupid));
		}
		

		}
		
		

	




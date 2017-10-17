package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_005_001;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
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
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_005_001.ComplaintsReportManageMapper;
import com.insigma.mvc.model.Ee13;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_005_001.ComplaintsReportManageService;
import com.insigma.mvc.utils.HRAgencyUtils;

@Component("ComplaintsReportManageService")
public class ComplaintsReportManageServiceImpl extends MvcHelper<Ee13> implements ComplaintsReportManageService {
	
	@Resource
	private ComplaintsReportManageMapper complaintsReportManageMapper;
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;

	@Override
	public HashMap<String, Object> getEe13List(Ee13 ee13) {
		PageHelper.offsetPage(ee13.getOffset(), ee13.getLimit());
		  if(StringUtils.isNotEmpty(ee13.getAee115())){
	    	ee13.setA_aee115(ee13.getAee115().split(","));
		}
		 /* ee13.setAab301(SysUserUtil.getCurrentUser().getAab301());//行政区划代码
*/		  List<Ee13> list = complaintsReportManageMapper.getEe13List(ee13);
		PageInfo<Ee13> pageinfo = new PageInfo<Ee13>(list);
		return this.success_hashmap_response(pageinfo);
	}

	@Override
	public AjaxReturnMsg<String> deleteEe13ById(String eee131) {
		
                Ee13 ee13 = complaintsReportManageMapper.selectByPrimaryKey(eee131);
                String aae100 = ee13.getAae100();
		       if(aae100.contains("0")){
		       }else{
			//执行注销操作
		    	   ee13.setAae100(HRAgencyUtils.AAE100_0); //机构有效标志更新为无效
		    	   ee13.setEee131(eee131);
			//更新EF11表
			int deletenum=complaintsReportManageMapper.updateByIdSelective(ee13);
			if(deletenum==1 ){
				return this.success("人力资源服务机构注销成功。");
			}else{
				return this.error("人力资源服务机注销失败。");
			}	
		}
		return this.error("您好,该条数据已经注销,不能再次操作。");
	}

	@Override
	public AjaxReturnMsg<String> batDeleteEe13Data(Ee13 ee13) {
		String [] ids=ee13.getSelectnodes().split(",");
		int batdeletenum=complaintsReportManageMapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("批量删除成功");
		}else{
			return this.success("批量删除成功,但存在失败的数据,请检查");
		}
	}

	@Override
	public Ee13 getEe13ById(String eee131) {
		// TODO Auto-generated method stub
		return   complaintsReportManageMapper.selectByPrimaryKey(eee131);
	}

	@Override
	public Ee13 getEe13NameById(String eee131) {
		// TODO Auto-generated method stub
		return complaintsReportManageMapper.selectNameByPrimaryKey(eee131);
	}

	@Override
	public AjaxReturnMsg<String> saveEe13Data(Ee13 ee13) {
		
		//ee13.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		/*ee13.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ee13.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee13.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		ee13.setAae036(new Date());//经办时间
		//新增数据 默认为未受理状态
		ee13.setAee115("0");
		//1 有效
		ee13.setAae100("1");
		//添加机构编码
		ee13.setAef101(ee13.getAee114());
		//投诉时间
		ee13.setAee116(new Date());
		
		//添加机构名称  Aee114   行政区划
		String aef101 = ee13.getAee114();
		Ef11 ef11 = hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
		//AEE114
		ee13.setAee114(ef11.getAef104());
		ee13.setAab301(ef11.getAef134());
		
		//投诉这Ip
	    String ip=" ";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ee13.setAee117(ip);
		//判断是否是更新
		if(StringUtils.isEmpty(ee13.getEee131())){
			int insertnum= complaintsReportManageMapper.insertSelective (ee13);
			if(insertnum==1){
				return this.success("投诉举报成功,请您耐心等待处理结果。");
			}else{
				return this.error("投诉举报失败");
			}
		}else{
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1){
				return this.success("更新成功");
			}else{
				return this.error("更新失败,请确认此人已经被删除");
			}
		}
	}

	@Override
	public AjaxReturnMsg<String> updateEe13FileUuid(Ee13 ee13) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxReturnMsg<String> deletefile(Ee13 ee13) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AjaxReturnMsg updateEe13data(Ee13 ee13) {
		
		//变更受理的状态 AEE115
		ee13.setAee115(HRAgencyUtils.AEE115_1); //已受理
			//更新Ee13表
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1){
				return this.success("人力资源服务机构受理成功。");
			}else{
				return this.error("人力资源服务机受理失败。");
			}
		
	}

	@Override
	public AjaxReturnMsg deleteEe13Data(Ee13 ee13) {
		
		List<Ee13> list = complaintsReportManageMapper.getEe13List(ee13);
		//页面参照list-拿出注销的字段
		String aae100="";
		if(list.get(0).getAae100()!=null){
			aae100 = list.get(0).getAae100().toString();
		}
		if(aae100.contains("0")){
			
		}else{
			//执行注销操作
			ee13.setAae100(HRAgencyUtils.AAE100_0); //机构有效标志更新为无效
			//更新EF11表
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1 ){
				return this.success("人力资源服务机构注销成功。");
			}else{
				return this.error("人力资源服务机注销失败。");
			}	
		}
		return this.error("您好,该条数据已经注销,不能再次操作。");
	}

	@Override
	public HashMap<String, Object> getEe13ByPhone(Ee13 ee13) {
		PageHelper.offsetPage(ee13.getOffset(), ee13.getLimit());
        List<Ee13> list = complaintsReportManageMapper.getEe13ByPhone(ee13);
		PageInfo<Ee13> pageinfo = new PageInfo<Ee13>(list);
		return this.success_hashmap_response(pageinfo);
	}

	

}

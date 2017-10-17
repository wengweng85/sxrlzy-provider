package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_006;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_005.HRAgencyRegAuditMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_006.HRAgencyChangeAuditMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_006.HRAgencyChangeAuditService;
import com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_005.PrimaryGenerater;
import com.insigma.mvc.utils.HRAgencyUtils;

/**
 * 
 * @author xizh
 *
 */

@Component("HRAgencyChangeAuditService")
public class HRAgencyChangeAuditServiceImpl extends MvcHelper implements HRAgencyChangeAuditService {

	@Resource
	private HRAgencyChangeAuditMapper hragencyChangeAuditMapper;
	@Resource
	private HRAgencyApplyEf12Mapper hrAgencyApplyEf12Mapper;
	
	@Resource
	private HRAgencyRegAuditMapper hrAgencyRegAuditMapper;
   
	/**
	 * 查询机构信息
	 */
	@Override
	public HashMap<String, Object> getEf11List(Ef11 ef11) {
		
		PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
		List<Ef11> list = new ArrayList();
	
		//审核状态
		if(StringUtils.isNotEmpty(ef11.getEae052())){
			ef11.setA_eae052(ef11.getEae052().split(","));
		}
		
		ef11.setAef132(HRAgencyUtils.AEF132_2);//变更
		ef11.setAef133(HRAgencyUtils.AEF133_1);//初审
		/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//行政区划代码
*/		list=hragencyChangeAuditMapper.getEf11List(ef11);
		PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list);
		return this.success_hashmap_response(pageinfo);
		
	}

	
	/**
	 * 单个审核通过
	 */
	@Override
	@Transactional
	public AjaxReturnMsg singleAuditHRAgencyRegdata(Ef12 ef12) {
		
		/* //获取该审核的数据统一信用代码
        String aab998 = ef12.getAab998();
	    //获取该条数据
        ef12=hrAgencyApplyEf12Mapper.getEf12updateByAab998(aab998);*/
        ef12.setEae052(HRAgencyUtils.EAE052_1);
        Ef11 ef11Info = new Ef11();
		ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		ef11Info.setAef132(HRAgencyUtils.AEF132_2);
		ef11Info.setAab998(ef12.getAab998());
		ef11Info.setAef133(ef12.getAef133());
		//根据统一信用代码查询网上申报信息
		Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
        int updatenum = this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_1);
        
        if(updatenum == 1){
        	return this.success("人力资源服务机构"+ef11.getAef104()+"业务变更审核通过");
        }else{
        	return this.success("人力资源服务机构"+ef11.getAef104()+"业务变更审核失败");
        }
	}
	
	/**
	 * 单个写入机构业务变更表
	 * @param aef133
	 * @return
	 */
	public int  insertSingleEf12byAef133(Ef11 ef11,String aef133) {
		Ef12 ef12 = new Ef12();
		ef12.setAef101(ef11.getAef101());
		ef12.setAef104(ef11.getAef104());
		ef12.setAab998(ef11.getAab998());
		ef12.setAab301(ef11.getAab301());
		ef12.setAef133(aef133);
		ef12.setAef132(HRAgencyUtils.AEF132_2);//机构登记
		ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		int insertNum = hrAgencyApplyEf12Mapper.insertSelective(ef12);
		return insertNum;
	}
	
	/**
	 * 单个更新机构状态信息
	 * @param aef133
	 * @return
	 */
	public int  updateSingleEf12byAef133(Ef11 ef11,String aef133) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef11.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_1);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", aef133);
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//审核人编号
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//审核人姓名
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//审核机构编号
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//审核机构名称
*/		int updateNum = hragencyChangeAuditMapper.singleAuditHRAgencyRegdata(map);
		return updateNum;
	}


	@Override
	public AjaxReturnMsg saveNotPassAuditData(Ef12 ef12) {
		
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef12.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_9);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", HRAgencyUtils.AEF133_1);
		map.put("aef132", HRAgencyUtils.AEF132_4);
		map.put("aae203", ef12.getAae203());//审核不通过原因
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//审核人姓名
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//审核机构编号
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//审核机构名称
*/		
		int batupdatenum=hragencyChangeAuditMapper.saveNotPassAuditData(map);
		
		if(batupdatenum==1){
			return this.success("您操作变更审核模块的不通过审核业务办理成功。");
		}else {
			return this.error("对不起，您操作变更审核模块的不通过审核业务办理失败。");
		}
	}

	//获取审核不通过的数据据
	@Override
	public Ef11 getEf11InfoById(String aab998) {
		Ef11 ef11_temp = new Ef11();
		ef11_temp.setAef132(HRAgencyUtils.AEF132_4);
		ef11_temp.setEae052(HRAgencyUtils.EAE052_0);
		ef11_temp.setAab998(aab998);
		ef11_temp.setAef133(HRAgencyUtils.AEF133_1);
		Ef11  ef11 = new Ef11();
	    ef11 = hragencyChangeAuditMapper.getEf11InfoNoAudit(ef11_temp);
		return ef11;
	}


	/**
	 * 批量审核通过
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batAuditHRAgencyRegdata(Ef12 ef12) {
	ef12.setEae052(HRAgencyUtils.EAE052_1);	
	int update11num=hragencyChangeAuditMapper.updateEf11ForEae052ByAef101(ef12);
	int update12num=hragencyChangeAuditMapper.updateEf11ForEae052ByAef102(ef12);
	if(update11num != 0 && update12num != 0 ){
		return  this.success("您操作的变更审核模块批量审核通过业务办理成功。");
	}else{
		return  this.error("对不起，您操作的变更审核模块批量审核通过业务办理失败。");
	}
		
	
	}


	@Override
	public Ef11 getEf11ById(String aab998) {
		
		return hragencyChangeAuditMapper.selectByPrimaryKey(aab998);
	}
	
	
}

package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_004_001;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_001.AgencyOtherDataEntryEe16Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_004_002.RecruitDataEntryEe15Mapper;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_004_001.AgencyOtherDataEntryService;

@Component("AgencyOtherDataEntryService")
public class AgencyOtherDataEntryServiceImpl extends MvcHelper implements AgencyOtherDataEntryService{

	@Resource
	private AgencyOtherDataEntryEe16Mapper agencyOtherDataEntryEe16Mapper;
	
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	
	@Resource
	private RecruitDataEntryEe15Mapper recruitDataEntryEe15Mapper;
	
	@Resource
	private HRAgencyApplyMapper hragencyApplyMapper;
	
	/**
	 *  获取列表信息
	 */
	@Override
	public HashMap<String, Object> getAgencyData(Ee15 ee15) {
		PageHelper.offsetPage(ee15.getOffset(),ee15.getLimit());
		/*ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());//申报用户对应的统一社会信用代码
*/		List<Ee15> list = recruitDataEntryEe15Mapper.getRecruitDataList(ee15);
		PageInfo<Ee15> pageinfo = new PageInfo<Ee15>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * 增加服务其他招聘数据
	 */
	@Override
	public AjaxReturnMsg saveAddRecruitData(Ee15 ee15){
		/*ee15.setAab998(SysUserUtil.getCurrentUser().getAab998());*/
		Ee15 eee151 = recruitDataEntryEe15Mapper.selectByPrimaryKeyOne(ee15);		
		if(eee151!=null){
				return this.error("对不起，数据所属期已存在，请重新输入");
		}else{		
			int insertNum = recruitDataEntryEe15Mapper.insertSelective(ee15);
			if(insertNum==1 ){
				return this.success("您好，人力资源其他数据录入成功");
			}else{
				return this.error("您好，人力资源其他数据录入失败！");
			}
		}
		
	}

	/**
	 * 修改招聘数据
	 */
	@Override
	public AjaxReturnMsg upDateAgencyData(Ee15 ee15) {
		if(ee15.getAee124()!=null){
			recruitDataEntryEe15Mapper.updateByPrimaryKeySelective(ee15);
			return this.success("您好，人力资源其他数据修改成功");
		}else{
			return this.success("您好，人力资源其他数据修改失败");
		}
	
	}

	/**
	 * 修改界面获取数据
	 */
	@Override
	public Ee15 getEe15ById(String eee151) {
		
		return recruitDataEntryEe15Mapper.selectByPrimaryKey(eee151);
	}
	
	//通过机构编号获取机构的名称
	@Override
	public Ef11 getOrganizationNameById(String aab998) {
		// TODO Auto-generated method stub
		return hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998);
	}	


	
}

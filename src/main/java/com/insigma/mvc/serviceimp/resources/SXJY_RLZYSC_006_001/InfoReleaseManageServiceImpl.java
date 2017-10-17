package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_006_001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_006_001.InfoReleaseManageMapper;
import com.insigma.mvc.model.Ee14;
import com.insigma.mvc.service.common.fileupload.FileLoadService;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_006_001.InfoReleaseManageService;
import com.insigma.mvc.utils.HRAgencyUtils;


/**
 * ee14 service impl
 * @author pangyy
 *
 */

@Component("InfoReleaseManageService")
public class InfoReleaseManageServiceImpl extends MvcHelper implements InfoReleaseManageService {

	
	@Resource
	private InfoReleaseManageMapper infoReleaseManageMapper;
	
	@Resource
	private FileLoadService fileLoadService;


	/**
	 * 查询信息发布记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> getEe14List(Ee14 ee14) {
		PageHelper.offsetPage(ee14.getOffset(), ee14.getLimit());
		List<Ee14> list = new ArrayList<Ee14>();
		if(StringUtils.isNotEmpty(ee14.getAee118())){
			ee14.setA_aee118(ee14.getAee118().split(","));
		}
		list=infoReleaseManageMapper.getEe14List(ee14);
		PageInfo<Ee14> pageinfo = new PageInfo<Ee14>(list);
		return this.success_hashmap_response(pageinfo);
	}


	/**
	 * 保存发布信息内容
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEe14Data(Ee14 ee14) {
		//ee14.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		/*ee14.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ee14.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ee14.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		ee14.setAae100(HRAgencyUtils.AAE100_1);
		if(ee14.getEee141()==null){
			//执行新增操作
			int insertNum = infoReleaseManageMapper.insertSelective(ee14);
			//更新公告附件信息
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("file_bus_id", ee14.getEee141());
			map.put("bus_uuids",ee14.getSelectnodes().split(","));
			fileLoadService.batupdateBusIdByBusUuidArray(map);
			if(insertNum==1 ){
				return this.success("您好,信息发布成功！");
			}else{
				return this.error("您好,信息发布失败！");
			}
		}else{
			//执行更新操作
			int updateNum = infoReleaseManageMapper.updateByPrimaryKeySelective(ee14);
			//更新公告附件信息
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("file_bus_id", ee14.getEee141());
			map.put("bus_uuids",ee14.getSelectnodes().split(","));
			fileLoadService.batupdateBusIdByBusUuidArray(map);
			if(updateNum==1 ){
				return this.success("您好,信息修改成功！");
			}else{
				return this.error("您好,信息修改失败！");
			}
			
		}
		
	}
	
	/**
	 * 根据信息发布ID查询详细信息
	 */
	@Override
	public Ee14 getEe14NameById(String eee141) {
		// TODO Auto-generated method stub
		return infoReleaseManageMapper.selectNameByPrimaryKey(eee141);
		
	}
	
	
	/**
	 * 根据信息发布ID查询详细信息
	 */
	@Override
	public Ee14 getEe14ById(String eee141) {
		// TODO Auto-generated method stub
		return infoReleaseManageMapper.selectByPrimaryKey(eee141);
		
	}
	/**
	 * 通过id删除已发布数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AjaxReturnMsg<String> deleteReleaseInfoById(String eee141) {
		int updateNum=infoReleaseManageMapper.updateReleaseStateByPrimaryKey(eee141);
		if(updateNum==1){
			return this.success("删除成功！");
		}else{
			return this.error("删除失败！");
		}
	
	}
	/**
	 * 批量删除
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AjaxReturnMsg<String> batDeleteReleasedata(Ee14 ee14) {
		String [] ids=ee14.getSelectnodes().split(",");
		int batdeletenum=infoReleaseManageMapper.batUpdateReleaseStateData(ids);
		if(batdeletenum==ids.length){
			return this.success("批量删除成功");
		}else{
			return this.success("批量删除成功,但存在失败的数据,请检查");
		}
	}
}

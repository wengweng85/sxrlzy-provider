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
	 * ��ѯ��Ϣ������¼
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
	 * ���淢����Ϣ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEe14Data(Ee14 ee14) {
		//ee14.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		/*ee14.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ee14.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ee14.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/		ee14.setAae100(HRAgencyUtils.AAE100_1);
		if(ee14.getEee141()==null){
			//ִ����������
			int insertNum = infoReleaseManageMapper.insertSelective(ee14);
			//���¹��渽����Ϣ
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("file_bus_id", ee14.getEee141());
			map.put("bus_uuids",ee14.getSelectnodes().split(","));
			fileLoadService.batupdateBusIdByBusUuidArray(map);
			if(insertNum==1 ){
				return this.success("����,��Ϣ�����ɹ���");
			}else{
				return this.error("����,��Ϣ����ʧ�ܣ�");
			}
		}else{
			//ִ�и��²���
			int updateNum = infoReleaseManageMapper.updateByPrimaryKeySelective(ee14);
			//���¹��渽����Ϣ
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("file_bus_id", ee14.getEee141());
			map.put("bus_uuids",ee14.getSelectnodes().split(","));
			fileLoadService.batupdateBusIdByBusUuidArray(map);
			if(updateNum==1 ){
				return this.success("����,��Ϣ�޸ĳɹ���");
			}else{
				return this.error("����,��Ϣ�޸�ʧ�ܣ�");
			}
			
		}
		
	}
	
	/**
	 * ������Ϣ����ID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ee14 getEe14NameById(String eee141) {
		// TODO Auto-generated method stub
		return infoReleaseManageMapper.selectNameByPrimaryKey(eee141);
		
	}
	
	
	/**
	 * ������Ϣ����ID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ee14 getEe14ById(String eee141) {
		// TODO Auto-generated method stub
		return infoReleaseManageMapper.selectByPrimaryKey(eee141);
		
	}
	/**
	 * ͨ��idɾ���ѷ�������
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AjaxReturnMsg<String> deleteReleaseInfoById(String eee141) {
		int updateNum=infoReleaseManageMapper.updateReleaseStateByPrimaryKey(eee141);
		if(updateNum==1){
			return this.success("ɾ���ɹ���");
		}else{
			return this.error("ɾ��ʧ�ܣ�");
		}
	
	}
	/**
	 * ����ɾ��
	 */
	@SuppressWarnings("unchecked")
	@Override
	public AjaxReturnMsg<String> batDeleteReleasedata(Ee14 ee14) {
		String [] ids=ee14.getSelectnodes().split(",");
		int batdeletenum=infoReleaseManageMapper.batUpdateReleaseStateData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}
	}
}

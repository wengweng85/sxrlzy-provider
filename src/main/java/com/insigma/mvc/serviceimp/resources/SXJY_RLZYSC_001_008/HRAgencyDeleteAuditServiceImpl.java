package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_008;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_008.HRAgencyDeleteAuditMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_008.HRAgencyDeleteAuditService;
import com.insigma.mvc.utils.HRAgencyUtils;

@Component("HRAgencyDeleteAuditService")
public class HRAgencyDeleteAuditServiceImpl extends MvcHelper  implements HRAgencyDeleteAuditService {
	
	@Resource
	private  HRAgencyDeleteAuditMapper hrAgencyDeleteAuditMapper;
	//д��12��
		@Resource
		private HRAgencyApplyEf12Mapper hrAgencyApplyEf12Mapper;
		@Resource
		private HRServicesRegEf11Mapper  hrServicesRegEf11Mapper;

	@Override
	public HashMap<String, Object> getEf11List(Ef11 ef11) {
		
		PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
		List<Ef11> list = new ArrayList();
		//ef11.setEae052(HRAgencyUtils.EAE052_0);//Ĭ�ϲ�ѯδ���״̬
		if(StringUtils.isNotEmpty(ef11.getEae052())){
			ef11.setA_eae052(ef11.getEae052().split(","));
		}
		ef11.setAef132(HRAgencyUtils.AEF132_4);
		ef11.setAef133(HRAgencyUtils.AEF133_1);
		/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//������������
*/		List<Ef11> list2 = hrAgencyDeleteAuditMapper.getEf11List(ef11);
		PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list2);
		return this.success_hashmap_response(pageinfo);
	}

	@Override
	public AjaxReturnMsg singleAuditHRAgencyReportdata(Ef12 ef12) {
		 //��ȡ����˵�����ͳһ���ô���
        String aab998 = ef12.getAab998();
	    //��ȡ��������
        ef12=hrAgencyApplyEf12Mapper.getEf12CancleByAab998(aab998);
        Ef11 ef11 = hrAgencyDeleteAuditMapper.selectByPrimaryKey(aab998);
        ef11.setAae100(HRAgencyUtils.AAE100_0); //��Ч
        int update11num = hrServicesRegEf11Mapper.updateByPrimaryKeySelective(ef11);
        ef12.setEae052(HRAgencyUtils.EAE052_1);
        int update12num = hrAgencyApplyEf12Mapper.updateByPrimaryKeySelective(ef12);
        
        if(update11num==1 && update12num == 1){
        	return this.success("��˳ɹ�");
        }else{
        	return this.success("���ʧ��");
        }
	}

	/**
	 * ����ͳһ���ô����ѯ������ϸ��Ϣ(��˲�ͨ��)
	 */
	@Override
	public Ef11 getEf11InfoById(String aab998,String aef133) {

		Ef11 ef11_temp = new Ef11();
		ef11_temp.setAef132(HRAgencyUtils.AEF132_4);
		ef11_temp.setEae052(HRAgencyUtils.EAE052_0);
		ef11_temp.setAab998(aab998);
		ef11_temp.setAef133(HRAgencyUtils.AEF133_1);
		Ef11  ef11 = new Ef11();
	    ef11 = hrAgencyDeleteAuditMapper.getEf11InfoNoAudit(ef11_temp);
		return ef11;
	}

	@Override
	public AjaxReturnMsg saveNotPassAuditData(Ef12 ef12) {
		
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef12.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_9);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", HRAgencyUtils.AEF133_1);
		map.put("aef132", HRAgencyUtils.AEF132_4);
		map.put("aae203", ef12.getAae203());//��˲�ͨ��ԭ��
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//���������
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//��˻������
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//��˻�������
*/		
		int batupdatenum=hrAgencyDeleteAuditMapper.saveNotPassAuditData(map);
		
		if(batupdatenum==1){
			return this.success("������Դ���������˲�ͨ�������ɹ�");
		}else {
			return this.error("������Դ���������˲�ͨ������ʧ��");
		}
	}

	/**
	 * ���ݻ�����Ų�ѯ������ϸ��Ϣ
	 */
	@Override
	public Ef11 getEf11ById(String aab998) {
	
		return hrAgencyDeleteAuditMapper.selectByPrimaryKey(aab998);
		
	}

	/**
	 * �������ͨ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batAuditHRAgencyRegdata(Ef12 ef12) {
	ef12.setEae052(HRAgencyUtils.EAE052_1);	
	int update11num=hrAgencyDeleteAuditMapper.updateEf11ForEae052ByAef101(ef12);
	int update12num=hrAgencyDeleteAuditMapper.updateEf11ForEae052ByAef102(ef12);
	if(update11num != 0 && update12num != 0 ){
		return  this.success("��˳ɹ�");
	}else{
		return  this.error("���ʧ��");
	}
		
	
	}
	
}

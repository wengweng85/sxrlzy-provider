package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_007;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.BusinessRecruitmentEe18Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_003.RecruitmentDataEe15Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_005.HRAgencyRegAuditMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_007.HRAgencyReportMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.model.EE18;
import com.insigma.mvc.model.Ee15;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_007.HRAgencyReportService;
import com.insigma.mvc.utils.HRAgencyUtils;


@Component("HRAgencyReportService")
public class HRAgencyReportServiceImpl extends MvcHelper implements HRAgencyReportService{
	
	@Resource
	private HRAgencyReportMapper  hrAgencyReportMapper;
	//��ѯͳһ���ô���
	@Resource
	private HRAgencyRegAuditMapper hrAgencyRegAuditMapper;
	//д��12��
	@Resource
	private HRAgencyApplyEf12Mapper hrAgencyApplyEf12Mapper;
	//ҵ�񱨸���Ϣ
	@Resource
	private RecruitmentDataEe15Mapper recruitmentDataEe15Mapper;
	
	@Resource
	private BusinessRecruitmentEe18Mapper businessRecruitmentEe18Mapper;
	
	/**
	 * ��ȡ���ҵ�������Ϣ�б�
	 * @param request
	 * @return
	 */
	@Override
	public HashMap<String, Object> getEf11List(Ef11 ef11) {

		PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
		List<Ef11> list = new ArrayList();
		//���״̬
		if(StringUtils.isNotEmpty(ef11.getEae052())){
			ef11.setA_eae052(ef11.getEae052().split(","));
		}
		//ҵ�񱨸�
		ef11.setAef132(HRAgencyUtils.AEF132_3);
		ef11.setAef133(HRAgencyUtils.AEF133_1);//����
		List<Ef11> list2 = hrAgencyReportMapper.getEf11List(ef11);
		/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//������������
*/		PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list2);
		return this.success_hashmap_response(pageinfo);

	}

	/**
	 * �������ͨ��
	 * @param request
	 * @param model
	 * @param ef11
	 * @return
	 */
	@Override
	public AjaxReturnMsg singleAuditHRAgencyReportdata(Ef12 ef12) {
		
		    Ef11 ef11Info = new Ef11();
		    ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		    ef11Info.setAef132(HRAgencyUtils.AEF132_3);
		    ef11Info.setAab998(ef12.getAab998());
		    ef11Info.setAef133(HRAgencyUtils.AEF133_1);
		    Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
            /*//��ȡ����˵�����ͳһ���ô���
	        String aab998 = ef12.getAab998();
		    //��ȡ��������
	        ef12=hrAgencyApplyEf12Mapper.getEf12ByAab998(aab998);
	        ef12.setEae052(HRAgencyUtils.EAE052_1);
	        ef12.setAae199(SysUserUtil.getCurrentUser().getCnname());//���������
			ef12.setAae201(SysUserUtil.getCurrentUser().getGroupid());//��˻������
			ef12.setAae198(SysUserUtil.getCurrentUser().getGroupname());//��˻�������
	        int updatenum = hrAgencyApplyEf12Mapper.updateByPrimaryKeySelective(ef12);*/
	        
	        int updatenum = this.updateSingleEf12byAef133(ef11,HRAgencyUtils.AEF133_1);
	        
	        if(updatenum == 1){
	        	return this.success("������Դ�������ҵ�񱨸���˳ɹ�");
	        }else{
	        	return this.success("������Դ�������ҵ�񱨸����ʧ��");
	        }
		}

	/**
	 * �������δͨ����ת��ҳ��(��ȡ������Ϣ)
	 * @param request
	 * @param model
	 * @param ef11
	 * @return
	 */
	@Override
	public Ef11 getEf11InfoById(String aab998) {
	
				Ef11 ef11_temp = new Ef11();
				ef11_temp.setAef132(HRAgencyUtils.AEF132_3);
				ef11_temp.setEae052(HRAgencyUtils.EAE052_0);
				ef11_temp.setAab998(aab998);
				Ef11  ef11 = new Ef11();
				ef11 = hrAgencyReportMapper.getEf11InfoNoAudit(ef11_temp);
				return ef11;
	}
	/**
	 *���� ��˲�ͨ������
	 */
	@Override
	public AjaxReturnMsg saveNotPassAuditData(Ef12 ef12) {
		
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef12.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_9);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", ef12.getAef133());
		map.put("aae203", ef12.getAae203());//��˲�ͨ��ԭ��
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//����˱��
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//���������
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//��˻������
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//��˻�������
*/		
		int batupdatenum=hrAgencyReportMapper.saveNotPassAuditData(map);
		if(batupdatenum==1){
			return this.success("������Դ�������ҵ�񱨸���˲�ͨ�������ɹ�");
		}else {
			return this.error("������Դ�������ҵ�񱨸���˲�ͨ������ʧ��");
		}
	}

	/**
	 *��ʾ��ϸҳ��
	 */
	@Override
	public Ee15 getEf11Ee15Info(String aab998) {
		
		return recruitmentDataEe15Mapper.selectReportInfoByAab998(aab998);
	}

	/**
	 * �������
	 */
	@Override
	public AjaxReturnMsg batAuditHRAgencyRegdata(Ef12 ef12) {
		
		Ef11 ef11Info = new Ef11();
		ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		ef11Info.setAef132(HRAgencyUtils.AEF132_3);
		ef11Info.setAab998(ef12.getAab998());
		ef11Info.setAef133(ef12.getAef133());
		Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
		
		int flag = 0;//�������¼�¼��
		//int update12num =0;
		for(String aab998:ef12.getSelectnodes()){
			if(aab998!=null && aab998.length()!=0){
				this.updateSingleEf12byAef133(ef11,HRAgencyUtils.AEF133_1);
			}
			flag++;
		}
		
		if(flag == ef12.getSelectnodes().length ){
			return  this.success("������Դ�������ҵ�񱨸�������˳ɹ�");
		}else{
			return  this.error("������Դ�������ҵ�񱨸��������ʧ��");
		}
	}

	/**
	 * �������»���״̬��Ϣ
	 * @param aef133
	 * @return
	 */
	public int  updateSingleEf12byAef133(Ef11 ef11,String aef133) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef11.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_1);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", aef133);
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//����˱��
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//���������
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//��˻������
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//��˻�������
*/		map.put("aef132", HRAgencyUtils.AEF132_3);
		int updateNum = hrAgencyRegAuditMapper.singleAuditHRAgencyRegdata(map);
		return updateNum;
	}
	@Override
	public EE18 getEf11Ee18Info(EE18 ee18) {
		;
		return businessRecruitmentEe18Mapper.selectEe18ReportInfoByAab998(ee18);
	}
    
	
}

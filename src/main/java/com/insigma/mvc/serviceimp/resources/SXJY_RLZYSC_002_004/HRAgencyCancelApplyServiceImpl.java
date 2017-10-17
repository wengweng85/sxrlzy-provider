package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_002_004;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_002_004.HRAgencyCancelApplyService;
import com.insigma.mvc.utils.HRAgencyUtils;


@Component("HRAgencyCancelApplyService")
public class HRAgencyCancelApplyServiceImpl  extends MvcHelper implements HRAgencyCancelApplyService  {
	
	@Resource
	private HRAgencyApplyMapper hragencyApplyMapper;

	@Resource
	private HRAgencyApplyEf12Mapper hragencyApplyEf12Mapper;
	
	@Resource
	private EF11_DECLAREMapper ef11_declareMapper;
	/**
	 * ������Ϣע��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg cancelEf11Data(Ef11 ef11) {
		
		ef11.setAae010(ef11.getAef121());//����������
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		String aef132 = "";//����ҵ������
		//����EF11��
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//����������EF12��
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("������Դ�������ע������ɹ�����ȴ���ˣ�");
		}else{
			return this.error("������Դ�����ע������ʧ��,��ȷ�ϴ˻����Ƿ�������");
		}	
			
	}

	private int insertEf12byAef104(String aab998, String aef132) {
		
		EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(aab998);
		Ef12 ef12 = new Ef12();
		ef12.setAef101(ef11_declare.getAef101());
		ef12.setAab998(ef11_declare.getAab998());
		ef12.setAef104(ef11_declare.getAef104());
		ef12.setAab301(ef11_declare.getAef134());
		
		ef12.setEae052(HRAgencyUtils.EAE052_0);//δ���
		ef12.setAef133(HRAgencyUtils.AEF133_1);
		ef12.setAef132(HRAgencyUtils.AEF132_4);//ҵ������Ϊע��
		ef12.setAae010(ef11_declare.getAef121());//����������
		/*ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}

}

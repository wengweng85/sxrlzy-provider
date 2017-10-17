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
		 /* ee13.setAab301(SysUserUtil.getCurrentUser().getAab301());//������������
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
			//ִ��ע������
		    	   ee13.setAae100(HRAgencyUtils.AAE100_0); //������Ч��־����Ϊ��Ч
		    	   ee13.setEee131(eee131);
			//����EF11��
			int deletenum=complaintsReportManageMapper.updateByIdSelective(ee13);
			if(deletenum==1 ){
				return this.success("������Դ�������ע���ɹ���");
			}else{
				return this.error("������Դ�����ע��ʧ�ܡ�");
			}	
		}
		return this.error("����,���������Ѿ�ע��,�����ٴβ�����");
	}

	@Override
	public AjaxReturnMsg<String> batDeleteEe13Data(Ee13 ee13) {
		String [] ids=ee13.getSelectnodes().split(",");
		int batdeletenum=complaintsReportManageMapper.batDeleteData(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
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
		
		//ee13.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		/*ee13.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ee13.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ee13.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		ee13.setAae036(new Date());//����ʱ��
		//�������� Ĭ��Ϊδ����״̬
		ee13.setAee115("0");
		//1 ��Ч
		ee13.setAae100("1");
		//��ӻ�������
		ee13.setAef101(ee13.getAee114());
		//Ͷ��ʱ��
		ee13.setAee116(new Date());
		
		//��ӻ�������  Aee114   ��������
		String aef101 = ee13.getAee114();
		Ef11 ef11 = hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
		//AEE114
		ee13.setAee114(ef11.getAef104());
		ee13.setAab301(ef11.getAef134());
		
		//Ͷ����Ip
	    String ip=" ";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ee13.setAee117(ip);
		//�ж��Ƿ��Ǹ���
		if(StringUtils.isEmpty(ee13.getEee131())){
			int insertnum= complaintsReportManageMapper.insertSelective (ee13);
			if(insertnum==1){
				return this.success("Ͷ�߾ٱ��ɹ�,�������ĵȴ���������");
			}else{
				return this.error("Ͷ�߾ٱ�ʧ��");
			}
		}else{
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1){
				return this.success("���³ɹ�");
			}else{
				return this.error("����ʧ��,��ȷ�ϴ����Ѿ���ɾ��");
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
		
		//��������״̬ AEE115
		ee13.setAee115(HRAgencyUtils.AEE115_1); //������
			//����Ee13��
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1){
				return this.success("������Դ�����������ɹ���");
			}else{
				return this.error("������Դ���������ʧ�ܡ�");
			}
		
	}

	@Override
	public AjaxReturnMsg deleteEe13Data(Ee13 ee13) {
		
		List<Ee13> list = complaintsReportManageMapper.getEe13List(ee13);
		//ҳ�����list-�ó�ע�����ֶ�
		String aae100="";
		if(list.get(0).getAae100()!=null){
			aae100 = list.get(0).getAae100().toString();
		}
		if(aae100.contains("0")){
			
		}else{
			//ִ��ע������
			ee13.setAae100(HRAgencyUtils.AAE100_0); //������Ч��־����Ϊ��Ч
			//����EF11��
			int updatenum=complaintsReportManageMapper.updateByPrimaryKeySelective(ee13);
			if(updatenum==1 ){
				return this.success("������Դ�������ע���ɹ���");
			}else{
				return this.error("������Դ�����ע��ʧ�ܡ�");
			}	
		}
		return this.error("����,���������Ѿ�ע��,�����ٴβ�����");
	}

	@Override
	public HashMap<String, Object> getEe13ByPhone(Ee13 ee13) {
		PageHelper.offsetPage(ee13.getOffset(), ee13.getLimit());
        List<Ee13> list = complaintsReportManageMapper.getEe13ByPhone(ee13);
		PageInfo<Ee13> pageinfo = new PageInfo<Ee13>(list);
		return this.success_hashmap_response(pageinfo);
	}

	

}

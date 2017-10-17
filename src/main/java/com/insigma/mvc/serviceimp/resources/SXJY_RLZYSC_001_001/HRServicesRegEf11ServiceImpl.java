package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_001;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.common.util.DateUtil;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServiceEc11PersonMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_001.UserInfoEc12Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.Ec11;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_001.HRServicesRegEf11Service;
import com.insigma.mvc.utils.HRAgencyUtils;

import net.sf.json.JSONObject;


@Component("HRServicesRegEf11Service")
public class HRServicesRegEf11ServiceImpl  extends MvcHelper implements  HRServicesRegEf11Service{
	
	//����dao(mapper�ӿ�)�ķ���
	@Resource
	private HRServicesRegEf11Mapper hrServicesRegEf11Mapper;
	@Resource
	private HRAgencyApplyEf12Mapper hragencyApplyEf12Mapper;
	@Resource
	private EF11_DECLAREMapper ef11_declareMapper;
	@Resource
	private HRServiceEc11PersonMapper hrServiceEc11PersonMapper;
	@Resource
	private UserInfoEc12Mapper userInfoEc12Mapper;
	@Resource
	private SysCodeTypeMapper sysCodeTypeMapper; //code_value
    /**
     *���� 
     * */
	public AjaxReturnMsg saveEf11Data(Ef11 ef11) {
		
	
		/*ef11.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		ef11.setAae036(new Date());//��������
		String aef132 = "";//����ҵ������
	
		//�жϻ������ơ�ͳһ������ô����Ƿ��ظ�
		ef11.setAef104(ef11.getAef104());
		ef11.setAab998(ef11.getAab998());
		int aef104num=hrServicesRegEf11Mapper.selectByAef104(ef11);
		int aab998num=hrServicesRegEf11Mapper.selectByAab998(ef11);
     	if(aef104num>0){
			return this.error("�û��������Ѿ����ڣ������ظ�,��������ȷ�Ļ������ƣ�");
		}else if(aab998num>0){
			return this.error("��ͳһ������ô���"+ef11.getAab998()+"�Ѿ�����,��������ȷ��ͳһ������ô��룡");
		}
     	//�жϾ�Ӫ���ͣ����Ϊְ�����˲����Ͷ�Ӧ�ľ�Ӫ��Χ��գ�����֮��
     	if("0".equals(ef11.getAef109())){
			ef11.setAef110("");
	    }else {
			ef11.setAef110_1("");
		}		
     	//�жϻ�������
     	if(ef11.getAef106().contains("0")){
			ef11.setEae052(HRAgencyUtils.EAE052_1); //���ͨ��
			ef11.setAae133(HRAgencyUtils.AEF133_3); //����
		}else{
			ef11.setEae052(HRAgencyUtils.EAE052_0);//δ���
		}		
     	//ִ���������� �ı���
     	ef11.setAab301(ef11.getAef134());//��������ϵ�������ز�����������
		aef132 =HRAgencyUtils.AEF132_1; //ע��
		ef11.setAae100(HRAgencyUtils.AAE100_1);//��Ч
		
	    int insertnum= hrServicesRegEf11Mapper.insertSelective (ef11);
		int insertEf12 =insertEf12byAef104(ef11.getAef101(),aef132);
		
		//���»���������Ա״̬Ϊ��Ч
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("aab998",ef11.getAab998());
				map.put("eec111s",ef11.getSelectnodes().split(","));
				hrServiceEc11PersonMapper.batupdateAgencyPersonArray(map);
				
		if(insertnum == 1&& insertEf12 == 1){
			return this.success("������Դ��������Ǽǳɹ���");
			
		}else{
			return this.error("������Դ��������Ǽ�ʧ�ܡ�");
		}	
	}
	
	//���ݻ���������ef12����
	private int insertEf12byAef104(String aef101, String aef132) {
		
		 Ef11 new_ef11 = hrServicesRegEf11Mapper.selectEf11ByAef101(aef101);
		 Ef12 ef12 = new Ef12();
		 ef12.setAef101(new_ef11.getAef101());
		 ef12.setAef104(new_ef11.getAef104());
		 ef12.setAab301(new_ef11.getAef134());
		 ef12.setAab998(new_ef11.getAab998());
	
		if((HRAgencyUtils.AEF132_1).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_1); //�������Ϊ��������
		}else if((HRAgencyUtils.AEF132_2).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_2); //�������Ϊ�������
		}else if((HRAgencyUtils.AEF132_3).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_3); //�������Ϊ����ҵ�񱨸�
		}else if((HRAgencyUtils.AEF132_4).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_4); //�������Ϊ����ע��
		}
		
		ef12.setEae052(HRAgencyUtils.EAE052_0);//δ���
		ef12.setAef133(HRAgencyUtils.AEF132_1);
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}
	
	    //��������
		@SuppressWarnings("unused")
		@Override
		public AjaxReturnMsg editHRServicesRegData(Ef11 ef11) {
			/*ef11.setAae133(SysUserUtil.getCurrentUser().getCnname());//�޸�������
			ef11.setAae132(SysUserUtil.getCurrentUser().getGroupname());//�޸Ļ�������
			ef11.setAae135(SysUserUtil.getCurrentUser().getGroupid());//�޸Ļ�������
*/			String aef132 = "";//����ҵ������
			
			//ִ�и��²���
			aef132 = HRAgencyUtils.AEF132_2;  //�������
			ef11.setAef133(HRAgencyUtils.AEF133_1);
			ef11.setAab301(ef11.getAef134());//��������ϵ�������ز�����������
			//����EF11��  aef104
			int updatenum=hrServicesRegEf11Mapper.updateByPrimaryKeySelective(ef11);
			//����������EF12��
			int insertEf12 = insertEf12byAef104(ef11.getAef101(),aef132);
			if(updatenum==1 && insertEf12==1){
				return this.success("������Դ��������޸ĳɹ���");
			}else{
				return this.error("������Դ������޸�ʧ�ܡ�");
			}	
			
		}
		 //��ȡ������Ϣ=��ȡef11����
		@Override
		public HashMap<String, Object> getEf11List(Ef11 ef11) {
			
				PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
				/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//���ݵ�ǰ��������
*/				List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
				PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list);
				return this.success_hashmap_response(pageinfo);

		}
		/**
		 * ͨ��������Ż�ȡ��Ϣ
		 */
		//  ��ת�����༭ҳ��
		@Override
		public Ef11 getEf11ById(String aef101) {
			return hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
		}
		//ע������
		@Override
		public AjaxReturnMsg cancelEf11Data(Ef11 ef11) {
			
		/*	ef11.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
			ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
			ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/			String aef132 = "";//����ҵ������
			//���ݻ�����Ų�ѯ���ؼ��ϲ���ȡ��Ӧ�ֶ�ֵ
			List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
			//ҳ�����list-�ó�ע�����ֶ�
			String aae100="";
			if(list.get(0).getAae100()!=null){
				aae100 = list.get(0).getAae100().toString();
			}
			//�ж��Ѿ�ע���ľͲ����ٽ��в���
			if(aae100.contains("0")){
				
			}else{
				//ִ��ע������
				aef132 = HRAgencyUtils.AEF132_4;//ҵ������Ϊע��
				//����EF11��
				int updatenum=hrServicesRegEf11Mapper.updateByPrimaryKeySelective(ef11);
				//����������EF12��
				int insertEf12 = insertEf12byAef104(ef11.getAef101(),aef132);
				if(updatenum==1 && insertEf12==1){
					return this.success("������Դ�������ע���ɹ���");
				}else{
					return this.error("������Դ�����ע��ʧ�ܡ�");
				}	
			}
			return this.error("����,���������Ѿ�ע��,�����ٴβ�����");
			
		}

	//��ȡ��������
	@Override
	public List<Ef11> getEf11ByAef101(String aab998) {

		return hrServicesRegEf11Mapper.getAef104ByAab998(aab998);
	}

	@Override
	public Ef11 getEf11ByAef(String aef101) {
	
		return hrServicesRegEf11Mapper.selectByPrimaryKeyAef(aef101);
	}

	@Override
	public Ec11 getEc11ById(String  eec111) {
	
		return hrServiceEc11PersonMapper.selectByPrimaryKey(eec111);
	}

	@Override
	public AjaxReturnMsg<String> saveEc11Data(Ec11 ec11) {
		  ec11.setAab998("9999999999999999");//д����ʱͳһ���ô���
			ec11.setAec100(HRAgencyUtils.AEC100_0);//��Ч
			if(ec11.getEec111()==null){
				int	insertnum = hrServiceEc11PersonMapper.insertSelective (ec11); 
				if(insertnum == 1 ){
					ec11.setAef002_string(DateUtil.dateToString(ec11.getAef002(),"yyyy-MM-dd"));
					ec11.setAac004(this.getCodeName("AAC004", ec11.getAac004()));
					ec11.setAac011(this.getCodeName("AAC011", ec11.getAac011()));
					ec11.setAac024(this.getCodeName("AAC024", ec11.getAac024()));
					return this.success(JSONObject.fromObject(ec11).toString());
				}else{
					return this.error("������Դ�������������Ա����ʧ��");
				}
			}else {
				int	updateNum = hrServiceEc11PersonMapper.updateByPrimaryKey(ec11); 
				if(updateNum == 1 ){
					return this.success(JSONObject.fromObject(ec11).toString());
				}else{
					return this.error("������Դ�������������Ա�޸�ʧ��");
				}
			}
		
	  
	}
	
	/**
	 * ����code_Type��ȡcode_name
	 */
	public  String getCodeName(String code_type,String code_value) {
    	CodeValue codevalue = new CodeValue();
    	codevalue.setCode_type(code_type);
		codevalue.setCode_value(code_value);
    	CodeValue  codeValueResult = sysCodeTypeMapper.getCodeValueByValue(codevalue);
    	
		return codeValueResult.getCode_name();
    }

	@Override
	public AjaxReturnMsg deleteAagencyPersonByEec111(String eec111) {
		//��¼ɾ��
				int deletenum= hrServiceEc11PersonMapper.deleteAagencyPersonByEec111(eec111);
				if(deletenum==1){
					return this.success("ɾ���ɹ�");
				}else{
					return this.error("ɾ��ʧ��,��ȷ���ļ��Ƿ����");
				}
	}

	@Override
	public AjaxReturnMsg<String> batDeleteAgencyPersonInfo(Ec11 ec11) {
		String [] ids=ec11.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteAagencyPersonByEec111(ids[i]);
		}
		return this.success("����ɾ���ɹ�");
	}

	
	@Override
	public HashMap<String, Object> getEc11List(Ec11 ec11) {
		PageHelper.offsetPage(ec11.getOffset(), ec11.getLimit());
		List<Ec11> list = new ArrayList();
	/*	ec11.setAab998(SysUserUtil.getCurrentUser().getAab998());*/
			list=hrServiceEc11PersonMapper.getEc11List(ec11);
		PageInfo<Ec11> pageinfo = new PageInfo<Ec11>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * ���ݻ���������ԱID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ec11 getEc11NameById(String eec111) {
		// TODO Auto-generated method stub
		return hrServiceEc11PersonMapper.selectNameByPrimaryKey(eec111);
		
	}
  
	}
	
	
	
	
	

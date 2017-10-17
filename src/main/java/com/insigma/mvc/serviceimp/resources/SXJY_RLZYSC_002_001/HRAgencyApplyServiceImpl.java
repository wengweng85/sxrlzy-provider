package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_002_001;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.common.util.DateUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyPersonMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_009_001.UserInfoManageMapper;
import com.insigma.mvc.dao.sysmanager.codetype.SysCodeTypeMapper;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ec11;
import com.insigma.mvc.model.Ec12;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_002_001.HRAgencyApplyService;
import com.insigma.mvc.utils.HRAgencyUtils;


/**
 * ef11 service impl
 * @author pangyy
 *
 */

@Component("HRAgencyApplyService")
public class HRAgencyApplyServiceImpl extends MvcHelper implements HRAgencyApplyService {

	
	@Resource
	private HRAgencyApplyMapper hragencyApplyMapper;
	
	@Resource
	private HRAgencyApplyEf12Mapper hragencyApplyEf12Mapper;
	
	@Resource
	private EF11_DECLAREMapper ef11_declareMapper;
	
	@Resource
	private HRAgencyApplyPersonMapper hragencyApplyPersonMapper;

	@Resource
	private SysCodeTypeMapper sysCodeTypeMapper;
	
	@Resource
	private UserInfoManageMapper userInfoManageMapper;
	
	/**
	 * ͨ��������Ż�ȡ��Ϣ
	 */
	@Override
	public AjaxReturnMsg getHRAgencyChangeById(String aab998) {
		Ef11 ef11 = hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998);
		if(ef11!= null){
			return this.success(ef11);
		}else {
			return this.error("��������Դ����������δͨ������ʱ���ܰ����ҵ��");
		}
		
	}

	/**
	 * ����ͳһ������ô����ѯ������Ϣ-������
	 */
	public AjaxReturnMsg getAgencyInfoByAab998(String aab998) {
		Ef11 ef11 = hragencyApplyMapper.selectAgencyInfoByAab998(aab998); 
		if(ef11!=null){
			ef11.setAab998(aab998);
		}
		return this.success(ef11);
	}
	
	/**
	 * ����ͳһ������ô����ѯ������Ϣ-EF11
	 */
	public AjaxReturnMsg getAgencyEf11InfoByAab998(String aab998) {
		Ef11 ef11 = hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998); 
		if(ef11!=null){
			return this.success(ef11);
		}else {
			return this.error("error");
		}
		
	}
	/**
	 * ����ͳһ������ô����ѯ������Ϣ-�걨��
	 */
	public AjaxReturnMsg getHRAgencyDeclareInfoById(String aab998) {
		EF11_DECLARE ef11_declare = ef11_declareMapper.selectEf11DeclareForNoAuditById(aab998);
		if(ef11_declare!=null){
			//δ��˺ͳ���δͨ��ʱ�����޸��걨��Ϣ
			if(ef11_declare.getAef135()==null||HRAgencyUtils.AEF135_0.equals(ef11_declare.getAef135()) || HRAgencyUtils.AEF135_2.equals(ef11_declare.getAef135())){
				return this.success(ef11_declare);
			}else if(HRAgencyUtils.AEF135_5.equals(ef11_declare.getAef135())){//����ͨ��
				return this.success("aa", ef11_declare);
			}else {
				return this.error("��������Ϊ:"+ef11_declare.getAef104()+"��������У������ĵȴ�������ѯ�������״̬��������[������Դ���������Ϣ��ѯ]ģ��鿴��");
			}
		}else {
			return this.error("������Ϣ�쳣������ϵ���ܻ�����");
		}
		
		
	}
	/**
	 * д�����ҵ������
	 * @param aab998
	 * @param aef132
	 * @return
	 */
	public int  insertEf12byAef104(String aab998,String aef132) {
		EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(aab998);
		Ef12 ef12 = new Ef12();
		ef12.setAab998(ef11_declare.getAab998());
		ef12.setAef104(ef11_declare.getAef104());
		ef12.setAab301(ef11_declare.getAef134());
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
		ef12.setAef133(HRAgencyUtils.AEF133_1);
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		ef12.setAae010(ef11_declare.getAef121());//����������
		/*ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}
	/**
	 * ������Ϣ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEf11Data(EF11_DECLARE ef11_declare) {
		String aef132 = HRAgencyUtils.AEF132_1;//����ҵ������
		//ef11_declare.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		ef11_declare.setAae010(ef11_declare.getAef121());//����������
		/*ef11_declare.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef11_declare.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/		ef11_declare.setAab301(ef11_declare.getAef134());//��������ϵ�������ز�����������
     	ef11_declare.setEae052(HRAgencyUtils.EAE052_0);//δ���
     	ef11_declare.setAae100(HRAgencyUtils.AAE100_1);//��Ч
     	ef11_declare.setAef135(HRAgencyUtils.AEF135_0);//�����걨����-�����
		//1.�жϱ���EF11���л������ơ�ͳһ������ô����Ƿ��ظ�
		Ef11 ef11 = new Ef11();
		ef11.setAef104(ef11_declare.getAef104());
		ef11.setAab998(ef11_declare.getAab998());
		int aef104num=hragencyApplyMapper.selectByAef104(ef11);
		int aab998num=hragencyApplyMapper.selectByAab998(ef11);
		if(aef104num>0){
			return this.error("�û��������Ѿ����ڣ������ظ�,��������ȷ�Ļ������ƣ�");
		}else if(aab998num>0){
			return this.error("��ͳһ������ô���"+ef11_declare.getAab998()+"�Ѿ�����,��������ȷ��ͳһ������ô��룡");
		}
		//�жϵ�¼�û��Ƿ��Ѿ�����ͨ�������������ʾ�����ٴ��ύ
		/*Ec12 ec12 = userInfoManageMapper.selectAgencyUserOnlyByAec101(SysUserUtil.getCurrentUser().getUsername());*/
		Ec12 ec12=null;
		if(ec12.getAef135()!=null&&HRAgencyUtils.AEF135_5.equals(ec12.getAef135())){
			return this.error("����,��������Ѿ����ͨ��,�������Ϣ���ڷ������������빦�ܲ�����");
		}else if(HRAgencyUtils.AEF135_1.equals(ec12.getAef135()) || HRAgencyUtils.AEF135_3.equals(ec12.getAef135())){
			return this.error("��������Ϊ:"+ef11_declare.getAef104()+"��������У������ĵȴ�������ѯ�������״̬��������[������Դ���������Ϣ��ѯ]ģ��鿴��");
		}else {
			
		}
		//���aab998��Ϊ�գ������ע���ͳһ���ô��롣�������ٴε�¼ϵͳ��AAB998���ʱ��
		if(ef11_declare.getAab998()!=null){
			Ec12 ec12_temp = new Ec12();
			/*ec12_temp.setAab998(SysUserUtil.getCurrentUser().getAab998());
			ec12_temp.setAec101(SysUserUtil.getCurrentUser().getUsername());*/
			userInfoManageMapper.updateEc12ForAab998ByAec101(ec12_temp);
		}
		//�жϾ�Ӫ���ͣ����Ϊְ�����˲����Ͷ�Ӧ�ľ�Ӫ��Χ��գ�����֮��
		if("0".equals(ef11_declare.getAef109())){
			ef11_declare.setAef110("");
		}else {
			ef11_declare.setAef110_1("");
		}
		
		//2.ִ����������,�״�ע���Ѿ������걨���¼����˸��´˱��걨��Ϣ���浽EF11_DECLARE����
     	int insertnum = 0 ;
     	if(!"".equals(ef11_declare.getEef101())){
     		insertnum = ef11_declareMapper.updateByPrimaryKeySelective(ef11_declare);//���걨δ���ʱ�����걨��Ϣ
     	}
     	//3.�������¼д��EF12��
		int insertEf12 =insertEf12byAef104(ef11_declare.getAab998(),aef132);
		//���»���������Ա״̬Ϊ��Ч
		Map<String,Object> map =new HashMap<String,Object>();
		/*map.put("aab998",SysUserUtil.getCurrentUser().getAab998());*/
		map.put("eec111s",ef11_declare.getSelectnodes().split(","));
		hragencyApplyPersonMapper.batupdateAgencyPersonArray(map);
		
		if(insertnum == 1 && insertEf12 == 1){
			return this.success("������Դ���������������ɹ�����ȴ���ˣ�");
		}else{
			return this.error("������Դ���������������ʧ��,��ȷ�ϴ˻����Ƿ���ڣ�");
		}	
	}

	/**
	 * ������Ϣ���
	 */
	@Override
	@Transactional
	public AjaxReturnMsg editEf11Data(Ef11 ef11) {
		
		ef11.setAae010(ef11.getAef121());//����������
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		String aef132 = HRAgencyUtils.AEF132_2;//����ҵ������
		//����EF11��
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//����������EF12��
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("������Դ��������޸�����ɹ�����ȴ���ˣ�");
		}else{
			return this.error("������Դ������޸�����ʧ��,��ȷ�ϴ˻����Ƿ�������");
		}
			
	}
	
	
	/**
	 * ������Ϣע��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg cancelEf11Data(Ef11 ef11) {
		//ef11.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		ef11.setAae010(ef11.getAef121());//����������
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//����������
*/		String aef132 = HRAgencyUtils.AEF132_4;//ҵ������Ϊע��
		//ִ��ע������
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//����������EF12��
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("������Դ�������ע������ɹ�����ȴ���ˣ�");
		}else{
			return this.error("������Դ�����ע������ʧ��,��ȷ�ϴ˻����Ƿ�������");
		}	
			
	}
	
	/**
	 * ��ѯ����������Ա��Ϣ
	 */
	@Override
	public HashMap<String, Object> getEc11List(Ec11 ec11) {
		PageHelper.offsetPage(ec11.getOffset(), ec11.getLimit());
		List<Ec11> list = new ArrayList();
		/*if(SysUserUtil.getCurrentUser().getAab998()==null){
			ec11.setAab998("admin");//�����걨�û���¼ʱ�����趨ֵ
		}else {
			ec11.setAab998(SysUserUtil.getCurrentUser().getAab998());
		}*/
		list=hragencyApplyPersonMapper.getEc11List(ec11);
		PageInfo<Ec11> pageinfo = new PageInfo<Ec11>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * ����������Ա��Ϣ����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEc11Data(Ec11 ec11) {
		ec11.setAab998("9999999999999999");//д����ʱͳһ���ô���
		//ec11.setAab998(SysUserUtil.getCurrentUser().getAab998());//ͳһ���ô���
		ec11.setAec100(HRAgencyUtils.AEC100_0);//��Ч
		if(ec11.getEec111()==null){
			int	insertnum = hragencyApplyPersonMapper.insertSelective (ec11); 
			if(insertnum == 1 ){
				if(ec11.getAef002()!=null){
					ec11.setAef002_string(DateUtil.dateToString(ec11.getAef002(),"yyyy-MM-dd"));
				}
				ec11.setAac004(this.getCodeName("AAC004", ec11.getAac004()));
				ec11.setAac011(this.getCodeName("AAC011", ec11.getAac011()));
				ec11.setAac024(this.getCodeName("AAC024", ec11.getAac024()));
				return this.success(JSONObject.fromObject(ec11).toString());
			}else{
				return this.error("������Դ�������������Ա����ʧ��");
			}
		}else {
			int	updateNum = hragencyApplyPersonMapper.updateByPrimaryKeySelective(ec11); 
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
	/**
	 * ����������Ա��Ϣ�޸�
	 */
	@Override
	@Transactional
	public AjaxReturnMsg editEc11Data(Ec11 ec11) {
     	int	insertnum = hragencyApplyPersonMapper.updateByPrimaryKeySelective (ec11); 
		if(insertnum == 1 ){
			return this.success("������Դ�������������Ա�޸ĳɹ�");
		}else{
			return this.error("������Դ�������������Ա�޸�ʧ��");
		}	
	}
	
	/**
	 * ����������Ա��Ϣɾ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deleteEc11Data(Ec11 ec11) {
     	int	insertnum = hragencyApplyPersonMapper.deleteByPrimaryKey (ec11.getEec111()); 
		if(insertnum == 1 ){
			return this.success("������Դ�������������Աɾ���ɹ�");
		}else{
			return this.error("������Դ�������������Աɾ��ʧ��");
		}	
	}
	
	/**
	 * ���ݻ���������ԱID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ec11 getEc11ById(String eec111) {
		// TODO Auto-generated method stub
		return hragencyApplyPersonMapper.selectByPrimaryKey(eec111);
		
	}
	
	/**
	 * ���ݻ���������ԱID��ѯ��ϸ��Ϣ
	 */
	@Override
	public Ec11 getEc11NameById(String eec111) {
		// TODO Auto-generated method stub
		return hragencyApplyPersonMapper.selectNameByPrimaryKey(eec111);
		
	}
    /**
     * ɾ������������Ա
     */
	@Override
	public AjaxReturnMsg deleteAgencyPersonInfoById(String eec111) {
		int updateNum=hragencyApplyPersonMapper.deleteAagencyPersonByEec111(eec111);
		if(updateNum==1){
			return this.success("ɾ���ɹ���");
		}else{
			return this.error("ɾ��ʧ�ܣ�");
		}
	}

	@Override
	public AjaxReturnMsg batDeleteAgencyPersonInfo(Ec11 ec11) {
		String [] ids=ec11.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteAagencyPersonByEec111(ids[i]);
		}
		return this.success("����ɾ���ɹ�");
		//String [] ids=ec11.getSelectnodes().split(",");
		
		/*int batdeletenum=hragencyApplyPersonMapper.batDeleteAgencyPersonInfo(ids);
		if(batdeletenum==ids.length){
			return this.success("����ɾ���ɹ�");
		}else{
			return this.success("����ɾ���ɹ�,������ʧ�ܵ�����,����");
		}*/
	}
	
	@Override
	public AjaxReturnMsg deleteAagencyPersonByEec111(String eec111) {
		//��¼ɾ��
		int deletenum= hragencyApplyPersonMapper.deleteAagencyPersonByEec111(eec111);
		if(deletenum==1){
			return this.success("ɾ���ɹ�");
		}else{
			return this.error("ɾ��ʧ��,��ȷ���ļ��Ƿ����");
		}
	}

}

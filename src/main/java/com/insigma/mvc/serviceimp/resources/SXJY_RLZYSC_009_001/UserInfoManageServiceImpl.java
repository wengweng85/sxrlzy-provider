package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_009_001;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.common.util.MD5Util;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_009_001.UserInfoManageMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_009_001.UserLogInfoManageMapper;
import com.insigma.mvc.dao.sysmanager.userrole.SysUserRoleMapper;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ec12;
import com.insigma.mvc.model.Ec13;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SUser;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_009_001.UserInfoManageService;
import com.insigma.mvc.utils.HRAgencyUtils;


/**
 * ec12 service impl
 * @author pangyy
 *
 */

@Component("UserInfoManageService")
public class UserInfoManageServiceImpl extends MvcHelper implements UserInfoManageService {

	
	@Resource
	private UserInfoManageMapper userInfoManageMapper;
	
	@Resource
	private UserLogInfoManageMapper userLogInfoManageMapper;
	
	@Resource
	private EF11_DECLAREMapper ef11_declareMapper;
	
	@Resource
	private HRAgencyApplyMapper hrAgencyApplyMapper;
	
	@Resource
    private SysUserRoleMapper sysUserRoleMapper;


	/**
	 * ����ע����Ϣ
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEc12Data(Ec12 ec12,Ec13 ec13) {
		try{
			//��ѯ�û����Ƿ����
			Ec12 ec12_search = userInfoManageMapper.selectUserInfoByAec101(ec12.getAec101().trim());
			if(ec12_search!=null){
				return this.error("����,���û����Ѵ���,�뻻һ�����ԣ�");
			}
			//��ѯͳһ���ô����Ƿ����
			EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(ec12.getAab998().trim());//�걨��EF11_DECLARE
			Ef11 ef11_search = hrAgencyApplyMapper.selectAgencyEf11InfoByAab998(ec12.getAab998().trim());//����ͨ����EF11
			if(ef11_declare!=null || ef11_search!=null){
				return this.error("����,��ͳһ������ô����Ѵ���,���������룡");
			}else {
				//MD5����
				ec12.setAec102(MD5Util.MD5Encode(ec12.getAec102()));
				ec12.setAec103("2");//�û�����Ϊ��ͨ�û�-2
				//ִ����������
				int insertNum = userInfoManageMapper.insertSelective(ec12);
				//д��EF11_DECLARE �걨��
				EF11_DECLARE ef11_declare_ = new EF11_DECLARE();
				ef11_declare_.setAab998(ec12.getAab998());
				int insertEf11Num = ef11_declareMapper.insertSelective(ef11_declare_);
				if(insertNum==1 && insertEf11Num==1){
					ec13.setAec101(ec12.getAec101());
					//ע��ɹ�����¼�û���־��
					int ec13Num = userLogInfoManageMapper.insertSelective(ec13);
					//ע��ɹ���д���û���ɫ������
					SRole sRole=new SRole();
					sRole.setRoleid(HRAgencyUtils.ROLEID);//�����걨�û���ɫID
					sRole.setUserid(ec12.getEec121());
					int userRoleNum = sysUserRoleMapper.saveUserRole(sRole);
					if(ec13Num==1 && userRoleNum==1){
						return this.success("����,ע��ɹ���");
					}else {
						return this.success("����,ע��ɹ�,����־��¼ʧ�ܣ�");
					}
					
				}else{
					return this.error("����,ע��ʧ�ܣ�");
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
			return this.error("�����쳣,����ϵ����Ա");
		}
	}
	/**
	 * �����û�����ѯ���û��Ƿ����
	 */
	public AjaxReturnMsg getUserInfoByAec101(String id) {
		try{
			//��ѯ�û����Ƿ����
			Ec12 ec12_search = userInfoManageMapper.selectUserInfoByAec101(id.trim());
			if(ec12_search!=null){
				return this.error("����,���û����Ѵ���,�뻻һ�����ԣ�");
			}else{
				return this.success("�û����Ϸ�");
			}		
		}catch(Exception e){
			e.printStackTrace();
			return this.error("�����쳣,����ϵ����Ա");
		}
	}
	/**
	 * ��֤ͳһ������ô����Ƿ����
	 */
	public AjaxReturnMsg getUserInfoByAab998(String id) {
		try{
			//��ѯͳһ���ô����Ƿ����
			EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(id.trim());//�걨��EF11_DECLARE
			Ef11 ef11_search = hrAgencyApplyMapper.selectAgencyEf11InfoByAab998(id.trim());//����ͨ����EF11
			if(ef11_declare!=null || ef11_search!=null){
				return this.error("����,��ͳһ������ô����Ѵ���,���������룡");
			}else{
				return this.success("ͳһ������ô���Ϸ�");
			}
	    }catch(Exception e){
		    e.printStackTrace();
		    return this.error("�����쳣,����ϵ����Ա");
	    }
	}
	/**
	 * �����û��������ѯ�û����Ƿ����
	 */
	public AjaxReturnMsg getHRAgencyUserPwdById(String id,String pwd) {
		try{
			String pwd_encoder = MD5Util.MD5Encode(pwd);
			SUser sUser = userInfoManageMapper.getHRAgencyUserPwdById(id,pwd_encoder);
			if(sUser ==null){
				return this.error("������ľ����벻��ȷ,����������");
			}else {
				return this.success("��ѯ����ɹ���");
			}
	    }catch(Exception e){
	        e.printStackTrace();
	        return this.error("�����쳣,����ϵ����Ա");
        }
	}
	/**
	 * �޸�����
	 */
	@Override
	@Transactional
	public AjaxReturnMsg chanagePwdEc12Data(Ec12 ec12) {
		//MD5����
		ec12.setAec102(MD5Util.MD5Encode(ec12.getAec102_1()));
		//ִ�и��²���
		int updateNum = 0;
		//��ѯҵ���û����Ƿ�����û���Ϣ
		Ec12 ec12_local = userInfoManageMapper.selectUserInfoByAec101(ec12.getAec101());
		if(ec12_local!=null){
			updateNum = userInfoManageMapper.updateEc12ByAec101(ec12);
		}else{
			//��ѯϵͳ�û����Ƿ�����û���Ϣ
			SUser sys_user = userInfoManageMapper.selectSysUserInfoByAec101(ec12.getAec101());
			if(sys_user!=null){
				SUser sUser = new SUser();
				sUser.setPassword(MD5Util.MD5Encode(ec12.getAec102_1()));
				sUser.setUsername(sys_user.getUsername());
				updateNum = userInfoManageMapper.updateSUserByAec101(sUser);
			}
		}
		if(updateNum==1){
			return this.success("����,�����޸ĳɹ���");
		}else{
			return this.error("����,�����޸�ʧ�ܣ�");
		}
			
	}
	/**
	 * ��ѯ�����û���Ϣ
	 */
	@Override
	public HashMap<String, Object> getHRAgencyUserInfoList(Ec12 ec12) {
		PageHelper.offsetPage(ec12.getOffset(), ec12.getLimit());
		/*ec12.setAab301(SysUserUtil.getCurrentUser().getAab301());*/
		List<Ec12> list=userInfoManageMapper.getHRAgencyUserInfoList(ec12);
		PageInfo<Ec12> pageinfo = new PageInfo<Ec12>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * ��������
	 */
	@Override
	@Transactional
	public AjaxReturnMsg resetPwdEc12Data(Ec12 ec12) {
		ec12.setAec102(MD5Util.MD5Encode(HRAgencyUtils.RESET_PWD_DEFAULT));
		int updateNum = userInfoManageMapper.updateResetPwdEc12Data(ec12);
		if(updateNum==1){
		     return this.success("����,�������óɹ���");
	     }else{
		     return this.error("����,��������ʧ�ܣ�");
	     }
	}
	
	/**
	 * �û�������������
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batResetPwdEc12Data(Ec12 ec12) {
		int flag = 0;//�������¼�¼��
		List<Ec12> listResult = new ArrayList<Ec12>();
        for(String eec121:ec12.getSelectnodes()){
            if(eec121!=null && eec121.length()!=0){
            	ec12.setAec102(MD5Util.MD5Encode(HRAgencyUtils.RESET_PWD_DEFAULT));
            	ec12.setEec121(eec121);
            	listResult.add(ec12);
            }
            flag++;
        }
        //������������
		userInfoManageMapper.updateBatResetPwdEc12Data(listResult);
		if(flag==ec12.getSelectnodes().length){
		     return this.success("����,�û������������óɹ���");
	     }else{
		     return this.error("����,�û�������������ʧ�ܣ�");
	     }
	}
	
}

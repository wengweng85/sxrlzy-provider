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
	 * 保存注册信息
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEc12Data(Ec12 ec12,Ec13 ec13) {
		try{
			//查询用户名是否存在
			Ec12 ec12_search = userInfoManageMapper.selectUserInfoByAec101(ec12.getAec101().trim());
			if(ec12_search!=null){
				return this.error("您好,该用户名已存在,请换一个试试！");
			}
			//查询统一信用代码是否存在
			EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(ec12.getAab998().trim());//申报库EF11_DECLARE
			Ef11 ef11_search = hrAgencyApplyMapper.selectAgencyEf11InfoByAab998(ec12.getAab998().trim());//初审通过库EF11
			if(ef11_declare!=null || ef11_search!=null){
				return this.error("您好,该统一社会信用代码已存在,请重新输入！");
			}else {
				//MD5加密
				ec12.setAec102(MD5Util.MD5Encode(ec12.getAec102()));
				ec12.setAec103("2");//用户类型为普通用户-2
				//执行新增操作
				int insertNum = userInfoManageMapper.insertSelective(ec12);
				//写入EF11_DECLARE 申报表
				EF11_DECLARE ef11_declare_ = new EF11_DECLARE();
				ef11_declare_.setAab998(ec12.getAab998());
				int insertEf11Num = ef11_declareMapper.insertSelective(ef11_declare_);
				if(insertNum==1 && insertEf11Num==1){
					ec13.setAec101(ec12.getAec101());
					//注册成功，记录用户日志表
					int ec13Num = userLogInfoManageMapper.insertSelective(ec13);
					//注册成功，写入用户角色关联表
					SRole sRole=new SRole();
					sRole.setRoleid(HRAgencyUtils.ROLEID);//网上申报用户角色ID
					sRole.setUserid(ec12.getEec121());
					int userRoleNum = sysUserRoleMapper.saveUserRole(sRole);
					if(ec13Num==1 && userRoleNum==1){
						return this.success("您好,注册成功！");
					}else {
						return this.success("您好,注册成功,但日志记录失败！");
					}
					
				}else{
					return this.error("您好,注册失败！");
				}
			  }
		}catch(Exception e){
			e.printStackTrace();
			return this.error("数据异常,请联系管理员");
		}
	}
	/**
	 * 根据用户名查询该用户是否存在
	 */
	public AjaxReturnMsg getUserInfoByAec101(String id) {
		try{
			//查询用户名是否存在
			Ec12 ec12_search = userInfoManageMapper.selectUserInfoByAec101(id.trim());
			if(ec12_search!=null){
				return this.error("您好,该用户名已存在,请换一个试试！");
			}else{
				return this.success("用户名合法");
			}		
		}catch(Exception e){
			e.printStackTrace();
			return this.error("数据异常,请联系管理员");
		}
	}
	/**
	 * 验证统一社会信用代码是否存在
	 */
	public AjaxReturnMsg getUserInfoByAab998(String id) {
		try{
			//查询统一信用代码是否存在
			EF11_DECLARE ef11_declare = ef11_declareMapper.selectHRAgencyDeclareInfoById(id.trim());//申报库EF11_DECLARE
			Ef11 ef11_search = hrAgencyApplyMapper.selectAgencyEf11InfoByAab998(id.trim());//初审通过库EF11
			if(ef11_declare!=null || ef11_search!=null){
				return this.error("您好,该统一社会信用代码已存在,请重新输入！");
			}else{
				return this.success("统一社会信用代码合法");
			}
	    }catch(Exception e){
		    e.printStackTrace();
		    return this.error("数据异常,请联系管理员");
	    }
	}
	/**
	 * 根据用户名密码查询用户表是否存在
	 */
	public AjaxReturnMsg getHRAgencyUserPwdById(String id,String pwd) {
		try{
			String pwd_encoder = MD5Util.MD5Encode(pwd);
			SUser sUser = userInfoManageMapper.getHRAgencyUserPwdById(id,pwd_encoder);
			if(sUser ==null){
				return this.error("您输入的旧密码不正确,请重新输入");
			}else {
				return this.success("查询密码成功！");
			}
	    }catch(Exception e){
	        e.printStackTrace();
	        return this.error("数据异常,请联系管理员");
        }
	}
	/**
	 * 修改密码
	 */
	@Override
	@Transactional
	public AjaxReturnMsg chanagePwdEc12Data(Ec12 ec12) {
		//MD5加密
		ec12.setAec102(MD5Util.MD5Encode(ec12.getAec102_1()));
		//执行更新操作
		int updateNum = 0;
		//查询业务用户表是否存在用户信息
		Ec12 ec12_local = userInfoManageMapper.selectUserInfoByAec101(ec12.getAec101());
		if(ec12_local!=null){
			updateNum = userInfoManageMapper.updateEc12ByAec101(ec12);
		}else{
			//查询系统用户表是否存在用户信息
			SUser sys_user = userInfoManageMapper.selectSysUserInfoByAec101(ec12.getAec101());
			if(sys_user!=null){
				SUser sUser = new SUser();
				sUser.setPassword(MD5Util.MD5Encode(ec12.getAec102_1()));
				sUser.setUsername(sys_user.getUsername());
				updateNum = userInfoManageMapper.updateSUserByAec101(sUser);
			}
		}
		if(updateNum==1){
			return this.success("您好,密码修改成功！");
		}else{
			return this.error("您好,密码修改失败！");
		}
			
	}
	/**
	 * 查询机构用户信息
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
	 * 密码重置
	 */
	@Override
	@Transactional
	public AjaxReturnMsg resetPwdEc12Data(Ec12 ec12) {
		ec12.setAec102(MD5Util.MD5Encode(HRAgencyUtils.RESET_PWD_DEFAULT));
		int updateNum = userInfoManageMapper.updateResetPwdEc12Data(ec12);
		if(updateNum==1){
		     return this.success("您好,密码重置成功！");
	     }else{
		     return this.error("您好,密码重置失败！");
	     }
	}
	
	/**
	 * 用户密码批量重置
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batResetPwdEc12Data(Ec12 ec12) {
		int flag = 0;//批量更新记录数
		List<Ec12> listResult = new ArrayList<Ec12>();
        for(String eec121:ec12.getSelectnodes()){
            if(eec121!=null && eec121.length()!=0){
            	ec12.setAec102(MD5Util.MD5Encode(HRAgencyUtils.RESET_PWD_DEFAULT));
            	ec12.setEec121(eec121);
            	listResult.add(ec12);
            }
            flag++;
        }
        //批量重置密码
		userInfoManageMapper.updateBatResetPwdEc12Data(listResult);
		if(flag==ec12.getSelectnodes().length){
		     return this.success("您好,用户密码批量重置成功！");
	     }else{
		     return this.error("您好,用户密码批量重置失败！");
	     }
	}
	
}

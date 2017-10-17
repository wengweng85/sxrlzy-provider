package com.insigma.mvc.serviceimp.resources.SXJY_RLZYSC_001_005;

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
import com.insigma.mvc.dao.resources.EF11_DECLAREMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_001_005.HRAgencyRegAuditMapper;
import com.insigma.mvc.dao.resources.SXJY_RLZYSC_002_001.HRAgencyApplyEf12Mapper;
import com.insigma.mvc.model.EF11_DECLARE;
import com.insigma.mvc.model.Ef11;
import com.insigma.mvc.model.Ef12;
import com.insigma.mvc.service.resources.SXJY_RLZYSC_001_005.HRAgencyRegAuditService;
import com.insigma.mvc.utils.HRAgencyUtils;


/**
 * ef11 service impl
 * @author pangyy
 *
 */

@Component("HRAgencyRegAuditService")
public class HRAgencyRegAuditServiceImpl extends MvcHelper implements HRAgencyRegAuditService {

	
	@Resource
	private HRAgencyRegAuditMapper hrAgencyRegAuditMapper;
	
	@Resource
	private HRAgencyApplyEf12Mapper hrAgencyApplyEf12Mapper;
	
	@Resource
	private EF11_DECLAREMapper ef11DeclareMapper;


	/**
	 * 查询机构信息
	 */
	@Override
	public HashMap<String, Object> getEf11List(Ef11 ef11) {
		PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
		List<Ef11> list = new ArrayList();
		//ef11.setEae052(HRAgencyUtils.EAE052_0);//默认查询未审核状态
		if(StringUtils.isNotEmpty(ef11.getEae052())){
			ef11.setA_eae052(ef11.getEae052().split(","));
		}
		ef11.setAef132(HRAgencyUtils.AEF132_1);
		/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//行政区划
*/		//判断审核级别
		if(HRAgencyUtils.AEF133_1.equals(ef11.getAef133())){//一级审核查询EF11、EF11_DECLARE
			list=hrAgencyRegAuditMapper.getEf11List(ef11);
		}else {
			list=hrAgencyRegAuditMapper.getEf11ListNext(ef11);//二级、三级审核查询EF11
		}
		
		PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * 根据机构编号查询机构详细信息
	 */
	@Override
	public Ef11 getEf11ById(String aab998) {
		// TODO Auto-generated method stub
		return hrAgencyRegAuditMapper.selectByPrimaryKey(aab998);
		
	}
	/**
	 * 根据统一信用代码查询机构详细信息
	 */
	@Override
	public Ef11 getEf11InfoById(String aab998,String aef133) {
		// TODO Auto-generated method stub
		Ef11 ef11_temp = new Ef11();
		ef11_temp.setAef132(HRAgencyUtils.AEF132_1);
		ef11_temp.setEae052(HRAgencyUtils.EAE052_0);
		ef11_temp.setAab998(aab998);
		ef11_temp.setAef133(aef133);
		Ef11  ef11 = new Ef11();
		//判断审核级别
		if(HRAgencyUtils.AEF133_1.equals(aef133)){//一级审核查询EF11、EF11_DECLARE
			ef11 = hrAgencyRegAuditMapper.getEf11InfoNoAudit(ef11_temp);
		}else {
			ef11 = hrAgencyRegAuditMapper.getEf11InfoNoAuditOther(ef11_temp);
		}
		return ef11;
		
	}
	
	/**
	 * 根据机构编号查询机构详细信息
	 */
	@Override
	public EF11_DECLARE getEf11DeclareByAef101(String aab998) {
		// TODO Auto-generated method stub
		return ef11DeclareMapper.selectByPrimaryKey(aab998);
		
	}
	/**
	 * 批量写入机构业务变更表
	 * @param ef11
	 * @param aef133
	 * @return
	 */
	public int  insertEf12byAef133(Ef12 ef12,String aef133) {
		List<Ef12> listResult = new ArrayList<Ef12>();
        for(String aab998:ef12.getSelectnodes()){
            if(aab998!=null && aab998.length()!=0){
            	Ef11 ef11 = hrAgencyRegAuditMapper.selectByPrimaryKey(aab998);
            	ef12.setAef101(aab998);
    			ef12.setAef104(ef11.getAef104());
    			ef12.setAef133(aef133);
    			ef12.setAef132(HRAgencyUtils.AEF132_1);//机构登记
    			ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
    			//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
    			/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
    			ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
    			ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/    			listResult.add(ef12);
    			
            }
        }
		int insertNum = hrAgencyRegAuditMapper.batInsertAuditRegdata(listResult);
		return insertNum;
	}
	
	/**
	 * 批量更新机构状态信息
	 * @param ef11
	 * @param aef133
	 * @return
	 */
	public int  updateEf12byAef133(Ef12 ef12,String aef133) {
		List<Ef12> listResult = new ArrayList<Ef12>();
        for(String aab998:ef12.getSelectnodes()){
            if(aab998!=null && aab998.length()!=0){
            	ef12.setAab998(aab998);
    			ef12.setAef133(aef133);
    			ef12.setAef132(HRAgencyUtils.AEF132_1);//机构登记
    			/**
    			 * eae052_1,eae052_2批量业务动态传值使用
    			 */
    			ef12.setEae052_1(HRAgencyUtils.EAE052_1);
    			ef12.setEae052_2(HRAgencyUtils.EAE052_0);
    			//ef12.setAae200(SysUserUtil.getCurrentUser().getUserid());//审核人编号
    			/*ef12.setAae199(SysUserUtil.getCurrentUser().getCnname());//审核人姓名
    			ef12.setAae201(SysUserUtil.getCurrentUser().getGroupid());//审核机构编号
    			ef12.setAae198(SysUserUtil.getCurrentUser().getGroupname());//审核机构名称
*/    			listResult.add(ef12);
            }
        }
		int updateNum = hrAgencyRegAuditMapper.batAuditHRAgencyRegdata(listResult);
		return updateNum;
	}
	/**
	 * 批量审核通过
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batAuditHRAgencyRegdata(Ef12 ef12) {
		int batupdatenum=0;
		int flag = 0;//批量更新记录数
		for(String aab998:ef12.getSelectnodes()){
			if(aab998!=null && aab998.length()!=0){
				Ef11 ef11Info = new Ef11();
				ef11Info.setEae052(HRAgencyUtils.EAE052_0);
				ef11Info.setAef132(HRAgencyUtils.AEF132_1);
				ef11Info.setAab998(aab998);
				ef11Info.setAef133(ef12.getAef133());
				//根据统一信用代码查询网上申报信息
				EF11_DECLARE ef11_declare = hrAgencyRegAuditMapper.selectHRAgencyDeclareInfoByAab998(ef11Info);
				Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
				//判断审核级别
				if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//一级审核
					if(ef11_declare!=null){
						//将申报表记录插入EF11机构信息表
						hrAgencyRegAuditMapper.insertSelective(ef11_declare);
						//更新申报表AEF135网上申报进度为初审通过
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_1);
						//查询上一步申报已经审核机构信息
						Ef11 ef11_declare_success = hrAgencyRegAuditMapper.selectByPrimaryKey(aab998);
						Ef11 ef11_ = new Ef11();
						ef11_.setAef101(ef11_declare_success.getAef101());
						ef11_.setAef104(ef11_declare.getAef104());
						ef11_.setEef121(ef11_declare.getEef121());
						ef11_.setAab998(ef11_declare.getAab998());
						ef11_.setAab301(ef11_declare.getAab301());
						batupdatenum=this.updateSingleEf12byAef133(ef11_, HRAgencyUtils.AEF133_1);
						if(batupdatenum==1){
							this.insertSingleEf12byAef133(ef11_, HRAgencyUtils.AEF133_2);
						}
					}else {
						batupdatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_1);
						if(batupdatenum==1){
							this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
						}	
					}
					
				}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//二级审核
					batupdatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
					if(batupdatenum==1){
						this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
					}
					
					/**
					 * 生成服务许可证编号
					 */
					String aef131_max = hrAgencyRegAuditMapper.selectEf11ByAef131();
					if(aef131_max.length()==12){
						ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber(aef131_max,ef11.getAab301()));
					}else {
						ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber("",ef11.getAab301()));
					}
					//更新EF11表中许可证编号
					hrAgencyRegAuditMapper.updateLicenseByPrimaryKey(ef11);
							
					/**
					 * 更新申报表AEF135网上申报进度为复审通过
					 */
					if(ef11_declare!=null){
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_3);
					}
				}else {//三级审核
					batupdatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
					/**
					 * 更新申报表AEF135网上申报进度为终审通过
					 */
					if(ef11_declare!=null){
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_5);
					}
					//终审通过，更新EF11表机构审核状态
					this.updateEf11ForEae052ByAef101(ef11);
				}
			}
			flag++;
		}
		
		if(flag==ef12.getSelectnodes().length){
			return this.success("人力资源服务机构设立批量审核成功");
		}else if(flag==0){
			return this.error("人力资源服务机构设立批量审核失败");
		}else{
			return this.success("人力资源服务机构设立批量审核成功,但存在失败的数据,请检查");
		}
	    
	}
	/**
	 * 单个审核通过
	 */
	@Override
	@Transactional
	public AjaxReturnMsg singleAuditHRAgencyRegdata(Ef12 ef12) {
		
		Ef11 ef11Info = new Ef11();
		ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		ef11Info.setAef132(HRAgencyUtils.AEF132_1);
		ef11Info.setAab998(ef12.getAab998());
		ef11Info.setAef133(ef12.getAef133());
		//根据统一信用代码查询网上申报信息
		EF11_DECLARE ef11_declare = hrAgencyRegAuditMapper.selectHRAgencyDeclareInfoByAab998(ef11Info);
		Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
		int updatenum=0;
		//判断审核级别
		if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//一级审核
			if(ef11_declare!=null){
				//将申报表记录插入EF11机构信息表
				hrAgencyRegAuditMapper.insertSelective(ef11_declare);
				//更新申报表AEF135网上申报进度为初审通过
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_1);
				//查询申报已经审核机构信息
				Ef11 ef11_declare_success = hrAgencyRegAuditMapper.selectByPrimaryKey(ef12.getAab998());
				Ef11 ef11_ = new Ef11();
				ef11_.setAef101(ef11_declare_success.getAef101());
				ef11_.setAef104(ef11_declare.getAef104());
				ef11_.setEef121(ef11_declare.getEef121());
				ef11_.setAab998(ef11_declare.getAab998());
				ef11_.setAab301(ef11_declare.getAab301());
				updatenum=this.updateSingleEf12byAef133(ef11_, HRAgencyUtils.AEF133_1);
				if(updatenum==1){
					this.insertSingleEf12byAef133(ef11_, HRAgencyUtils.AEF133_2);
				}
				
			}else {
				updatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_1);
				if(updatenum==1){
					this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
				}	
			}
			
		}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//二级审核
			updatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
			if(updatenum==1){
				this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
			}
			
			/**
			 * 生成服务许可证编号
			 */
			String aef131_max = hrAgencyRegAuditMapper.selectEf11ByAef131();
			if(aef131_max.length()==12){
				ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber(aef131_max,ef11.getAab301()));
			}else {
				ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber("",ef11.getAab301()));
			}
			//更新EF11表中许可证编号
			hrAgencyRegAuditMapper.updateLicenseByPrimaryKey(ef11);
					
			/**
			 * 更新申报表AEF135网上申报进度为复审通过
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_3);
			}
		}else {//三级审核
			updatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
			/**
			 * 更新申报表AEF135网上申报进度为终审通过
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_5);
			}
			//终审通过，更新EF11表机构审核状态
			this.updateEf11ForEae052ByAef101(ef11);
		}
		if(updatenum==1){
			return this.success("人力资源服务机构审核通过操作成功");
		}else {
			return this.error("人力资源服务机构审核通过操作失败");
		}
	}
	/**
	 * 单个写入机构业务变更表
	 * @param aef133
	 * @return
	 */
	public int  insertSingleEf12byAef133(Ef11 ef11,String aef133) {
		Ef12 ef12 = new Ef12();
		ef12.setAef101(ef11.getAef101());
		ef12.setAef104(ef11.getAef104());
		ef12.setAab998(ef11.getAab998());
		ef12.setAab301(ef11.getAab301());
		ef12.setAef133(aef133);
		ef12.setAef132(HRAgencyUtils.AEF132_1);//机构登记
		ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		int insertNum = hrAgencyApplyEf12Mapper.insertSelective(ef12);
		return insertNum;
	}
	
	/**
	 * 单个更新机构状态信息
	 * @param aef133
	 * @return
	 */
	public int  updateSingleEf12byAef133(Ef11 ef11,String aef133) {
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef11.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_1);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", aef133);
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//审核人编号
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//审核人姓名
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//审核机构编号
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//审核机构名称
*/		map.put("aef132", HRAgencyUtils.AEF132_1);
		int updateNum = hrAgencyRegAuditMapper.singleAuditHRAgencyRegdata(map);
		return updateNum;
	}
	/**
	 * 更新机构申报状态信息
	 * @param aef133
	 * @return
	 */
	public int  updateEf11DeclareByEef101(EF11_DECLARE ef11_declare,String aef135) {
		ef11_declare.setEef101(ef11_declare.getEef101());
		ef11_declare.setAef135(aef135);
		int updateNum = ef11DeclareMapper.updateEf11DeclareStateByPrimaryKey(ef11_declare);
		return updateNum;
	}
	
	/**
	 * 更新机构审核状态信息
	 * @param aef133
	 * @return
	 */
	public int  updateEf11ForEae052ByAef101(Ef11 ef11) {
		ef11.setAef101(ef11.getAef101());
		ef11.setEae052(HRAgencyUtils.EAE052_1);
		int updateNum = hrAgencyRegAuditMapper.updateEf11ForEae052ByAef101(ef11);
		return updateNum;
	}
	
	/**
	 * 审核不通过
	 */
	@Override	
	@Transactional
	public AjaxReturnMsg saveNotPassAuditData(Ef12 ef12) {
		Ef11 ef11Info = new Ef11();
		ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		ef11Info.setAef132(HRAgencyUtils.AEF132_1);
		ef11Info.setAab998(ef12.getAab998());
		ef11Info.setAef133(ef12.getAef133());
		EF11_DECLARE ef11_declare = hrAgencyRegAuditMapper.selectHRAgencyDeclareInfoByAab998(ef11Info);
		Map<String,String> map=new HashMap<String,String>(); 
		map.put("eef121", ef12.getEef121());
		map.put("eae052_1", HRAgencyUtils.EAE052_9);
		map.put("eae052_2", HRAgencyUtils.EAE052_0);
		map.put("aef133", ef12.getAef133());
		map.put("aae203", ef12.getAae203());//审核不通过原因
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//审核人编号
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//审核人姓名
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//审核机构编号
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//审核机构名称
*/		
		if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//一级审核
			/**
			 * 更新申报表AEF135网上申报进度 2
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_2);
			}
		}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//一级审核
			/**
			 * 更新申报表AEF135网上申报进度 4
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_4);
			}
		}else {
			/**
			 * 更新申报表AEF135网上申报进度 6
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_6);
			}
		}
		int batupdatenum=hrAgencyRegAuditMapper.saveNotPassAuditData(map);
		
		if(batupdatenum==1){
			return this.success("人力资源服务机构审核不通过操作成功");
		}else {
			return this.error("人力资源服务机构审核不通过操作失败");
		}
	    
	}
}

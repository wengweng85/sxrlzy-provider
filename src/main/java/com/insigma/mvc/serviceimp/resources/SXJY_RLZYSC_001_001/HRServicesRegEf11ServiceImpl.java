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
	
	//调用dao(mapper接口)的方法
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
     *保存 
     * */
	public AjaxReturnMsg saveEf11Data(Ef11 ef11) {
		
	
		/*ef11.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		ef11.setAae036(new Date());//经办日期
		String aef132 = "";//机构业务类型
	
		//判断机构名称、统一社会信用代码是否重复
		ef11.setAef104(ef11.getAef104());
		ef11.setAab998(ef11.getAab998());
		int aef104num=hrServicesRegEf11Mapper.selectByAef104(ef11);
		int aab998num=hrServicesRegEf11Mapper.selectByAab998(ef11);
     	if(aef104num>0){
			return this.error("该机构名称已经存在，不能重复,请输入正确的机构名称！");
		}else if(aab998num>0){
			return this.error("该统一社会信用代码"+ef11.getAab998()+"已经存在,请输入正确的统一社会信用代码！");
		}
     	//判断经营类型，如果为职介则将人才类型对应的经营范围清空，否则反之。
     	if("0".equals(ef11.getAef109())){
			ef11.setAef110("");
	    }else {
			ef11.setAef110_1("");
		}		
     	//判断机构性质
     	if(ef11.getAef106().contains("0")){
			ef11.setEae052(HRAgencyUtils.EAE052_1); //审核通过
			ef11.setAae133(HRAgencyUtils.AEF133_3); //终审
		}else{
			ef11.setEae052(HRAgencyUtils.EAE052_0);//未审核
		}		
     	//执行新增操作 文本框
     	ef11.setAab301(ef11.getAef134());//将隶属关系所属区县插入行政区划
		aef132 =HRAgencyUtils.AEF132_1; //注册
		ef11.setAae100(HRAgencyUtils.AAE100_1);//有效
		
	    int insertnum= hrServicesRegEf11Mapper.insertSelective (ef11);
		int insertEf12 =insertEf12byAef104(ef11.getAef101(),aef132);
		
		//更新机构工作人员状态为有效
				Map<String,Object> map =new HashMap<String,Object>();
				map.put("aab998",ef11.getAab998());
				map.put("eec111s",ef11.getSelectnodes().split(","));
				hrServiceEc11PersonMapper.batupdateAgencyPersonArray(map);
				
		if(insertnum == 1&& insertEf12 == 1){
			return this.success("人力资源服务机构登记成功。");
			
		}else{
			return this.error("人力资源服务机构登记失败。");
		}	
	}
	
	//根据机构名插入ef12数据
	private int insertEf12byAef104(String aef101, String aef132) {
		
		 Ef11 new_ef11 = hrServicesRegEf11Mapper.selectEf11ByAef101(aef101);
		 Ef12 ef12 = new Ef12();
		 ef12.setAef101(new_ef11.getAef101());
		 ef12.setAef104(new_ef11.getAef104());
		 ef12.setAab301(new_ef11.getAef134());
		 ef12.setAab998(new_ef11.getAab998());
	
		if((HRAgencyUtils.AEF132_1).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_1); //变更类型为机构设立
		}else if((HRAgencyUtils.AEF132_2).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_2); //变更类型为机构变更
		}else if((HRAgencyUtils.AEF132_3).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_3); //变更类型为机构业务报告
		}else if((HRAgencyUtils.AEF132_4).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_4); //变更类型为机构注销
		}
		
		ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
		ef12.setAef133(HRAgencyUtils.AEF132_1);
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}
	
	    //更改数据
		@SuppressWarnings("unused")
		@Override
		public AjaxReturnMsg editHRServicesRegData(Ef11 ef11) {
			/*ef11.setAae133(SysUserUtil.getCurrentUser().getCnname());//修改人姓名
			ef11.setAae132(SysUserUtil.getCurrentUser().getGroupname());//修改机构名称
			ef11.setAae135(SysUserUtil.getCurrentUser().getGroupid());//修改机构编码
*/			String aef132 = "";//机构业务类型
			
			//执行更新操作
			aef132 = HRAgencyUtils.AEF132_2;  //变更操作
			ef11.setAef133(HRAgencyUtils.AEF133_1);
			ef11.setAab301(ef11.getAef134());//将隶属关系所属区县插入行政区划
			//更新EF11表  aef104
			int updatenum=hrServicesRegEf11Mapper.updateByPrimaryKeySelective(ef11);
			//插入机构变更EF12表
			int insertEf12 = insertEf12byAef104(ef11.getAef101(),aef132);
			if(updatenum==1 && insertEf12==1){
				return this.success("人力资源服务机构修改成功。");
			}else{
				return this.error("人力资源服务机修改失败。");
			}	
			
		}
		 //获取机构信息=获取ef11数据
		@Override
		public HashMap<String, Object> getEf11List(Ef11 ef11) {
			
				PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
				/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//根据当前行政区划
*/				List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
				PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list);
				return this.success_hashmap_response(pageinfo);

		}
		/**
		 * 通过机构编号获取信息
		 */
		//  跳转新增编辑页面
		@Override
		public Ef11 getEf11ById(String aef101) {
			return hrServicesRegEf11Mapper.selectByPrimaryKey(aef101);
		}
		//注销数据
		@Override
		public AjaxReturnMsg cancelEf11Data(Ef11 ef11) {
			
		/*	ef11.setAae010(SysUserUtil.getCurrentUser().getCnname());//经办人姓名
			ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
			ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/			String aef132 = "";//机构业务类型
			//根据机构编号查询返回集合并获取对应字段值
			List<Ef11> list=hrServicesRegEf11Mapper.getEf11List(ef11);
			//页面参照list-拿出注销的字段
			String aae100="";
			if(list.get(0).getAae100()!=null){
				aae100 = list.get(0).getAae100().toString();
			}
			//判断已经注销的就不能再进行操作
			if(aae100.contains("0")){
				
			}else{
				//执行注销操作
				aef132 = HRAgencyUtils.AEF132_4;//业务类型为注销
				//更新EF11表
				int updatenum=hrServicesRegEf11Mapper.updateByPrimaryKeySelective(ef11);
				//插入机构变更EF12表
				int insertEf12 = insertEf12byAef104(ef11.getAef101(),aef132);
				if(updatenum==1 && insertEf12==1){
					return this.success("人力资源服务机构注销成功。");
				}else{
					return this.error("人力资源服务机注销失败。");
				}	
			}
			return this.error("您好,该条数据已经注销,不能再次操作。");
			
		}

	//获取机构名称
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
		  ec11.setAab998("9999999999999999");//写入临时统一信用代码
			ec11.setAec100(HRAgencyUtils.AEC100_0);//无效
			if(ec11.getEec111()==null){
				int	insertnum = hrServiceEc11PersonMapper.insertSelective (ec11); 
				if(insertnum == 1 ){
					ec11.setAef002_string(DateUtil.dateToString(ec11.getAef002(),"yyyy-MM-dd"));
					ec11.setAac004(this.getCodeName("AAC004", ec11.getAac004()));
					ec11.setAac011(this.getCodeName("AAC011", ec11.getAac011()));
					ec11.setAac024(this.getCodeName("AAC024", ec11.getAac024()));
					return this.success(JSONObject.fromObject(ec11).toString());
				}else{
					return this.error("人力资源服务机构工作人员新增失败");
				}
			}else {
				int	updateNum = hrServiceEc11PersonMapper.updateByPrimaryKey(ec11); 
				if(updateNum == 1 ){
					return this.success(JSONObject.fromObject(ec11).toString());
				}else{
					return this.error("人力资源服务机构工作人员修改失败");
				}
			}
		
	  
	}
	
	/**
	 * 根据code_Type获取code_name
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
		//记录删除
				int deletenum= hrServiceEc11PersonMapper.deleteAagencyPersonByEec111(eec111);
				if(deletenum==1){
					return this.success("删除成功");
				}else{
					return this.error("删除失败,请确定文件是否存在");
				}
	}

	@Override
	public AjaxReturnMsg<String> batDeleteAgencyPersonInfo(Ec11 ec11) {
		String [] ids=ec11.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteAagencyPersonByEec111(ids[i]);
		}
		return this.success("批量删除成功");
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
	 * 根据机构工作人员ID查询详细信息
	 */
	@Override
	public Ec11 getEc11NameById(String eec111) {
		// TODO Auto-generated method stub
		return hrServiceEc11PersonMapper.selectNameByPrimaryKey(eec111);
		
	}
  
	}
	
	
	
	
	

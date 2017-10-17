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
	 * 通过机构编号获取信息
	 */
	@Override
	public AjaxReturnMsg getHRAgencyChangeById(String aab998) {
		Ef11 ef11 = hragencyApplyMapper.selectAgencyEf11InfoByAab998(aab998);
		if(ef11!= null){
			return this.success(ef11);
		}else {
			return this.error("该人力资源服务机构审核未通过，暂时不能办理此业务。");
		}
		
	}

	/**
	 * 根据统一社会信用代码查询机构信息-基础库
	 */
	public AjaxReturnMsg getAgencyInfoByAab998(String aab998) {
		Ef11 ef11 = hragencyApplyMapper.selectAgencyInfoByAab998(aab998); 
		if(ef11!=null){
			ef11.setAab998(aab998);
		}
		return this.success(ef11);
	}
	
	/**
	 * 根据统一社会信用代码查询机构信息-EF11
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
	 * 根据统一社会信用代码查询机构信息-申报表
	 */
	public AjaxReturnMsg getHRAgencyDeclareInfoById(String aab998) {
		EF11_DECLARE ef11_declare = ef11_declareMapper.selectEf11DeclareForNoAuditById(aab998);
		if(ef11_declare!=null){
			//未审核和初审未通过时可以修改申报信息
			if(ef11_declare.getAef135()==null||HRAgencyUtils.AEF135_0.equals(ef11_declare.getAef135()) || HRAgencyUtils.AEF135_2.equals(ef11_declare.getAef135())){
				return this.success(ef11_declare);
			}else if(HRAgencyUtils.AEF135_5.equals(ef11_declare.getAef135())){//终审通过
				return this.success("aa", ef11_declare);
			}else {
				return this.error("机构名称为:"+ef11_declare.getAef104()+"正在审核中，请耐心等待。若查询机构审核状态，可以在[人力资源服务机构信息查询]模块查看。");
			}
		}else {
			return this.error("机构信息异常，请联系主管机构！");
		}
		
		
	}
	/**
	 * 写入机构业务变更表
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
			ef12.setAef132(HRAgencyUtils.AEF132_1); //变更类型为机构设立
		}else if((HRAgencyUtils.AEF132_2).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_2); //变更类型为机构变更
		}else if((HRAgencyUtils.AEF132_3).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_3); //变更类型为机构业务报告
		}else if((HRAgencyUtils.AEF132_4).equals(aef132)){
			ef12.setAef132(HRAgencyUtils.AEF132_4); //变更类型为机构注销
		}
		ef12.setEae052(HRAgencyUtils.EAE052_0);//未审核
		ef12.setAef133(HRAgencyUtils.AEF133_1);
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		ef12.setAae010(ef11_declare.getAef121());//经办人姓名
		/*ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		
		int insertEf12 = hragencyApplyEf12Mapper.insertSelective(ef12);
		return insertEf12;
	}
	/**
	 * 机构信息保存
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEf11Data(EF11_DECLARE ef11_declare) {
		String aef132 = HRAgencyUtils.AEF132_1;//机构业务类型
		//ef11_declare.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		ef11_declare.setAae010(ef11_declare.getAef121());//经办人姓名
		/*ef11_declare.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef11_declare.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构名称
*/		ef11_declare.setAab301(ef11_declare.getAef134());//将隶属关系所属区县插入行政区划
     	ef11_declare.setEae052(HRAgencyUtils.EAE052_0);//未审核
     	ef11_declare.setAae100(HRAgencyUtils.AAE100_1);//有效
     	ef11_declare.setAef135(HRAgencyUtils.AEF135_0);//网上申报进度-待审核
		//1.判断本地EF11表中机构名称、统一社会信用代码是否重复
		Ef11 ef11 = new Ef11();
		ef11.setAef104(ef11_declare.getAef104());
		ef11.setAab998(ef11_declare.getAab998());
		int aef104num=hragencyApplyMapper.selectByAef104(ef11);
		int aab998num=hragencyApplyMapper.selectByAab998(ef11);
		if(aef104num>0){
			return this.error("该机构名称已经存在，不能重复,请输入正确的机构名称！");
		}else if(aab998num>0){
			return this.error("该统一社会信用代码"+ef11_declare.getAab998()+"已经存在,请输入正确的统一社会信用代码！");
		}
		//判断登录用户是否已经终审通过，如果是则提示不能再次提交
		/*Ec12 ec12 = userInfoManageMapper.selectAgencyUserOnlyByAec101(SysUserUtil.getCurrentUser().getUsername());*/
		Ec12 ec12=null;
		if(ec12.getAef135()!=null&&HRAgencyUtils.AEF135_5.equals(ec12.getAef135())){
			return this.error("您好,服务机构已经审核通过,若变更信息请在服务机构变更申请功能操作！");
		}else if(HRAgencyUtils.AEF135_1.equals(ec12.getAef135()) || HRAgencyUtils.AEF135_3.equals(ec12.getAef135())){
			return this.error("机构名称为:"+ef11_declare.getAef104()+"正在审核中，请耐心等待。若查询机构审核状态，可以在[人力资源服务机构信息查询]模块查看。");
		}else {
			
		}
		//如果aab998不为空，则更新注册表统一信用代码。否则不能再次登录系统（AAB998变更时）
		if(ef11_declare.getAab998()!=null){
			Ec12 ec12_temp = new Ec12();
			/*ec12_temp.setAab998(SysUserUtil.getCurrentUser().getAab998());
			ec12_temp.setAec101(SysUserUtil.getCurrentUser().getUsername());*/
			userInfoManageMapper.updateEc12ForAab998ByAec101(ec12_temp);
		}
		//判断经营类型，如果为职介则将人才类型对应的经营范围清空，否则反之。
		if("0".equals(ef11_declare.getAef109())){
			ef11_declare.setAef110("");
		}else {
			ef11_declare.setAef110_1("");
		}
		
		//2.执行新增操作,首次注册已经插入申报表记录，因此更新此表将申报信息保存到EF11_DECLARE表中
     	int insertnum = 0 ;
     	if(!"".equals(ef11_declare.getEef101())){
     		insertnum = ef11_declareMapper.updateByPrimaryKeySelective(ef11_declare);//已申报未审核时更新申报信息
     	}
     	//3.将变更记录写入EF12表
		int insertEf12 =insertEf12byAef104(ef11_declare.getAab998(),aef132);
		//更新机构工作人员状态为有效
		Map<String,Object> map =new HashMap<String,Object>();
		/*map.put("aab998",SysUserUtil.getCurrentUser().getAab998());*/
		map.put("eec111s",ef11_declare.getSelectnodes().split(","));
		hragencyApplyPersonMapper.batupdateAgencyPersonArray(map);
		
		if(insertnum == 1 && insertEf12 == 1){
			return this.success("人力资源服务机构设立申请成功，请等待审核！");
		}else{
			return this.error("人力资源服务机构设立申请失败,请确认此机构是否存在！");
		}	
	}

	/**
	 * 机构信息变更
	 */
	@Override
	@Transactional
	public AjaxReturnMsg editEf11Data(Ef11 ef11) {
		
		ef11.setAae010(ef11.getAef121());//经办人姓名
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		String aef132 = HRAgencyUtils.AEF132_2;//机构业务类型
		//更新EF11表
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//插入机构变更EF12表
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("人力资源服务机构修改申请成功，请等待审核！");
		}else{
			return this.error("人力资源服务机修改申请失败,请确认此机构是否正常！");
		}
			
	}
	
	
	/**
	 * 机构信息注销
	 */
	@Override
	@Transactional
	public AjaxReturnMsg cancelEf11Data(Ef11 ef11) {
		//ef11.setAae011(SysUserUtil.getCurrentUser().getUserid());//经办人编号
		ef11.setAae010(ef11.getAef121());//经办人姓名
		/*ef11.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//经办机构编号
		ef11.setAae009(SysUserUtil.getCurrentUser().getGroupname());//经办机构编号
*/		String aef132 = HRAgencyUtils.AEF132_4;//业务类型为注销
		//执行注销操作
		int updatenum=hragencyApplyMapper.updateByPrimaryKeySelective(ef11);
		//插入机构变更EF12表
		int insertEf12 = insertEf12byAef104(ef11.getAab998(),aef132);
		if(updatenum==1 && insertEf12==1){
			return this.success("人力资源服务机构注销申请成功，请等待审核！");
		}else{
			return this.error("人力资源服务机注销申请失败,请确认此机构是否正常！");
		}	
			
	}
	
	/**
	 * 查询机构工作人员信息
	 */
	@Override
	public HashMap<String, Object> getEc11List(Ec11 ec11) {
		PageHelper.offsetPage(ec11.getOffset(), ec11.getLimit());
		List<Ec11> list = new ArrayList();
		/*if(SysUserUtil.getCurrentUser().getAab998()==null){
			ec11.setAab998("admin");//不是申报用户登录时，赋予定值
		}else {
			ec11.setAab998(SysUserUtil.getCurrentUser().getAab998());
		}*/
		list=hragencyApplyPersonMapper.getEc11List(ec11);
		PageInfo<Ec11> pageinfo = new PageInfo<Ec11>(list);
		return this.success_hashmap_response(pageinfo);
	}
	
	/**
	 * 机构工作人员信息保存
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveEc11Data(Ec11 ec11) {
		ec11.setAab998("9999999999999999");//写入临时统一信用代码
		//ec11.setAab998(SysUserUtil.getCurrentUser().getAab998());//统一信用代码
		ec11.setAec100(HRAgencyUtils.AEC100_0);//无效
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
				return this.error("人力资源服务机构工作人员新增失败");
			}
		}else {
			int	updateNum = hragencyApplyPersonMapper.updateByPrimaryKeySelective(ec11); 
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
	/**
	 * 机构工作人员信息修改
	 */
	@Override
	@Transactional
	public AjaxReturnMsg editEc11Data(Ec11 ec11) {
     	int	insertnum = hragencyApplyPersonMapper.updateByPrimaryKeySelective (ec11); 
		if(insertnum == 1 ){
			return this.success("人力资源服务机构工作人员修改成功");
		}else{
			return this.error("人力资源服务机构工作人员修改失败");
		}	
	}
	
	/**
	 * 机构工作人员信息删除
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deleteEc11Data(Ec11 ec11) {
     	int	insertnum = hragencyApplyPersonMapper.deleteByPrimaryKey (ec11.getEec111()); 
		if(insertnum == 1 ){
			return this.success("人力资源服务机构工作人员删除成功");
		}else{
			return this.error("人力资源服务机构工作人员删除失败");
		}	
	}
	
	/**
	 * 根据机构工作人员ID查询详细信息
	 */
	@Override
	public Ec11 getEc11ById(String eec111) {
		// TODO Auto-generated method stub
		return hragencyApplyPersonMapper.selectByPrimaryKey(eec111);
		
	}
	
	/**
	 * 根据机构工作人员ID查询详细信息
	 */
	@Override
	public Ec11 getEc11NameById(String eec111) {
		// TODO Auto-generated method stub
		return hragencyApplyPersonMapper.selectNameByPrimaryKey(eec111);
		
	}
    /**
     * 删除机构工作人员
     */
	@Override
	public AjaxReturnMsg deleteAgencyPersonInfoById(String eec111) {
		int updateNum=hragencyApplyPersonMapper.deleteAagencyPersonByEec111(eec111);
		if(updateNum==1){
			return this.success("删除成功！");
		}else{
			return this.error("删除失败！");
		}
	}

	@Override
	public AjaxReturnMsg batDeleteAgencyPersonInfo(Ec11 ec11) {
		String [] ids=ec11.getSelectnodes().split(",");
		for(int i=0;i<ids.length;i++){
			deleteAagencyPersonByEec111(ids[i]);
		}
		return this.success("批量删除成功");
		//String [] ids=ec11.getSelectnodes().split(",");
		
		/*int batdeletenum=hragencyApplyPersonMapper.batDeleteAgencyPersonInfo(ids);
		if(batdeletenum==ids.length){
			return this.success("批量删除成功");
		}else{
			return this.success("批量删除成功,但存在失败的数据,请检查");
		}*/
	}
	
	@Override
	public AjaxReturnMsg deleteAagencyPersonByEec111(String eec111) {
		//记录删除
		int deletenum= hragencyApplyPersonMapper.deleteAagencyPersonByEec111(eec111);
		if(deletenum==1){
			return this.success("删除成功");
		}else{
			return this.error("删除失败,请确定文件是否存在");
		}
	}

}

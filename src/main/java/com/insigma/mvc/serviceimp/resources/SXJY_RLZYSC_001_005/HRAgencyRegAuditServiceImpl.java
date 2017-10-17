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
	 * ��ѯ������Ϣ
	 */
	@Override
	public HashMap<String, Object> getEf11List(Ef11 ef11) {
		PageHelper.offsetPage(ef11.getOffset(), ef11.getLimit());
		List<Ef11> list = new ArrayList();
		//ef11.setEae052(HRAgencyUtils.EAE052_0);//Ĭ�ϲ�ѯδ���״̬
		if(StringUtils.isNotEmpty(ef11.getEae052())){
			ef11.setA_eae052(ef11.getEae052().split(","));
		}
		ef11.setAef132(HRAgencyUtils.AEF132_1);
		/*ef11.setAab301(SysUserUtil.getCurrentUser().getAab301());//��������
*/		//�ж���˼���
		if(HRAgencyUtils.AEF133_1.equals(ef11.getAef133())){//һ����˲�ѯEF11��EF11_DECLARE
			list=hrAgencyRegAuditMapper.getEf11List(ef11);
		}else {
			list=hrAgencyRegAuditMapper.getEf11ListNext(ef11);//������������˲�ѯEF11
		}
		
		PageInfo<Ef11> pageinfo = new PageInfo<Ef11>(list);
		return this.success_hashmap_response(pageinfo);
	}

	/**
	 * ���ݻ�����Ų�ѯ������ϸ��Ϣ
	 */
	@Override
	public Ef11 getEf11ById(String aab998) {
		// TODO Auto-generated method stub
		return hrAgencyRegAuditMapper.selectByPrimaryKey(aab998);
		
	}
	/**
	 * ����ͳһ���ô����ѯ������ϸ��Ϣ
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
		//�ж���˼���
		if(HRAgencyUtils.AEF133_1.equals(aef133)){//һ����˲�ѯEF11��EF11_DECLARE
			ef11 = hrAgencyRegAuditMapper.getEf11InfoNoAudit(ef11_temp);
		}else {
			ef11 = hrAgencyRegAuditMapper.getEf11InfoNoAuditOther(ef11_temp);
		}
		return ef11;
		
	}
	
	/**
	 * ���ݻ�����Ų�ѯ������ϸ��Ϣ
	 */
	@Override
	public EF11_DECLARE getEf11DeclareByAef101(String aab998) {
		// TODO Auto-generated method stub
		return ef11DeclareMapper.selectByPrimaryKey(aab998);
		
	}
	/**
	 * ����д�����ҵ������
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
    			ef12.setAef132(HRAgencyUtils.AEF132_1);//�����Ǽ�
    			ef12.setEae052(HRAgencyUtils.EAE052_0);//δ���
    			//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
    			/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
    			ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
    			ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/    			listResult.add(ef12);
    			
            }
        }
		int insertNum = hrAgencyRegAuditMapper.batInsertAuditRegdata(listResult);
		return insertNum;
	}
	
	/**
	 * �������»���״̬��Ϣ
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
    			ef12.setAef132(HRAgencyUtils.AEF132_1);//�����Ǽ�
    			/**
    			 * eae052_1,eae052_2����ҵ��̬��ֵʹ��
    			 */
    			ef12.setEae052_1(HRAgencyUtils.EAE052_1);
    			ef12.setEae052_2(HRAgencyUtils.EAE052_0);
    			//ef12.setAae200(SysUserUtil.getCurrentUser().getUserid());//����˱��
    			/*ef12.setAae199(SysUserUtil.getCurrentUser().getCnname());//���������
    			ef12.setAae201(SysUserUtil.getCurrentUser().getGroupid());//��˻������
    			ef12.setAae198(SysUserUtil.getCurrentUser().getGroupname());//��˻�������
*/    			listResult.add(ef12);
            }
        }
		int updateNum = hrAgencyRegAuditMapper.batAuditHRAgencyRegdata(listResult);
		return updateNum;
	}
	/**
	 * �������ͨ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg batAuditHRAgencyRegdata(Ef12 ef12) {
		int batupdatenum=0;
		int flag = 0;//�������¼�¼��
		for(String aab998:ef12.getSelectnodes()){
			if(aab998!=null && aab998.length()!=0){
				Ef11 ef11Info = new Ef11();
				ef11Info.setEae052(HRAgencyUtils.EAE052_0);
				ef11Info.setAef132(HRAgencyUtils.AEF132_1);
				ef11Info.setAab998(aab998);
				ef11Info.setAef133(ef12.getAef133());
				//����ͳһ���ô����ѯ�����걨��Ϣ
				EF11_DECLARE ef11_declare = hrAgencyRegAuditMapper.selectHRAgencyDeclareInfoByAab998(ef11Info);
				Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
				//�ж���˼���
				if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//һ�����
					if(ef11_declare!=null){
						//���걨���¼����EF11������Ϣ��
						hrAgencyRegAuditMapper.insertSelective(ef11_declare);
						//�����걨��AEF135�����걨����Ϊ����ͨ��
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_1);
						//��ѯ��һ���걨�Ѿ���˻�����Ϣ
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
					
				}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//�������
					batupdatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
					if(batupdatenum==1){
						this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
					}
					
					/**
					 * ���ɷ������֤���
					 */
					String aef131_max = hrAgencyRegAuditMapper.selectEf11ByAef131();
					if(aef131_max.length()==12){
						ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber(aef131_max,ef11.getAab301()));
					}else {
						ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber("",ef11.getAab301()));
					}
					//����EF11�������֤���
					hrAgencyRegAuditMapper.updateLicenseByPrimaryKey(ef11);
							
					/**
					 * �����걨��AEF135�����걨����Ϊ����ͨ��
					 */
					if(ef11_declare!=null){
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_3);
					}
				}else {//�������
					batupdatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
					/**
					 * �����걨��AEF135�����걨����Ϊ����ͨ��
					 */
					if(ef11_declare!=null){
						this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_5);
					}
					//����ͨ��������EF11��������״̬
					this.updateEf11ForEae052ByAef101(ef11);
				}
			}
			flag++;
		}
		
		if(flag==ef12.getSelectnodes().length){
			return this.success("������Դ�����������������˳ɹ�");
		}else if(flag==0){
			return this.error("������Դ������������������ʧ��");
		}else{
			return this.success("������Դ�����������������˳ɹ�,������ʧ�ܵ�����,����");
		}
	    
	}
	/**
	 * �������ͨ��
	 */
	@Override
	@Transactional
	public AjaxReturnMsg singleAuditHRAgencyRegdata(Ef12 ef12) {
		
		Ef11 ef11Info = new Ef11();
		ef11Info.setEae052(HRAgencyUtils.EAE052_0);
		ef11Info.setAef132(HRAgencyUtils.AEF132_1);
		ef11Info.setAab998(ef12.getAab998());
		ef11Info.setAef133(ef12.getAef133());
		//����ͳһ���ô����ѯ�����걨��Ϣ
		EF11_DECLARE ef11_declare = hrAgencyRegAuditMapper.selectHRAgencyDeclareInfoByAab998(ef11Info);
		Ef11 ef11 = hrAgencyRegAuditMapper.selectHRAgencyInfoByAab998(ef11Info);
		int updatenum=0;
		//�ж���˼���
		if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//һ�����
			if(ef11_declare!=null){
				//���걨���¼����EF11������Ϣ��
				hrAgencyRegAuditMapper.insertSelective(ef11_declare);
				//�����걨��AEF135�����걨����Ϊ����ͨ��
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_1);
				//��ѯ�걨�Ѿ���˻�����Ϣ
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
			
		}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//�������
			updatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_2);
			if(updatenum==1){
				this.insertSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
			}
			
			/**
			 * ���ɷ������֤���
			 */
			String aef131_max = hrAgencyRegAuditMapper.selectEf11ByAef131();
			if(aef131_max.length()==12){
				ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber(aef131_max,ef11.getAab301()));
			}else {
				ef11.setAef131(PrimaryGenerater.getInstance().generaterNextNumber("",ef11.getAab301()));
			}
			//����EF11�������֤���
			hrAgencyRegAuditMapper.updateLicenseByPrimaryKey(ef11);
					
			/**
			 * �����걨��AEF135�����걨����Ϊ����ͨ��
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_3);
			}
		}else {//�������
			updatenum=this.updateSingleEf12byAef133(ef11, HRAgencyUtils.AEF133_3);
			/**
			 * �����걨��AEF135�����걨����Ϊ����ͨ��
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_5);
			}
			//����ͨ��������EF11��������״̬
			this.updateEf11ForEae052ByAef101(ef11);
		}
		if(updatenum==1){
			return this.success("������Դ����������ͨ�������ɹ�");
		}else {
			return this.error("������Դ����������ͨ������ʧ��");
		}
	}
	/**
	 * ����д�����ҵ������
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
		ef12.setAef132(HRAgencyUtils.AEF132_1);//�����Ǽ�
		ef12.setEae052(HRAgencyUtils.EAE052_0);//δ���
		//ef12.setAae011(SysUserUtil.getCurrentUser().getUserid());//�����˱��
		/*ef12.setAae010(SysUserUtil.getCurrentUser().getCnname());//����������
		ef12.setAaf011(SysUserUtil.getCurrentUser().getGroupid());//����������
		ef12.setAae009(SysUserUtil.getCurrentUser().getGroupname());//�����������
*/		int insertNum = hrAgencyApplyEf12Mapper.insertSelective(ef12);
		return insertNum;
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
*/		map.put("aef132", HRAgencyUtils.AEF132_1);
		int updateNum = hrAgencyRegAuditMapper.singleAuditHRAgencyRegdata(map);
		return updateNum;
	}
	/**
	 * ���»����걨״̬��Ϣ
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
	 * ���»������״̬��Ϣ
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
	 * ��˲�ͨ��
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
		map.put("aae203", ef12.getAae203());//��˲�ͨ��ԭ��
		//map.put("aae200", SysUserUtil.getCurrentUser().getUserid());//����˱��
		/*map.put("aae199", SysUserUtil.getCurrentUser().getCnname());//���������
		map.put("aae201", SysUserUtil.getCurrentUser().getGroupid());//��˻������
		map.put("aae198", SysUserUtil.getCurrentUser().getGroupname());//��˻�������
*/		
		if(HRAgencyUtils.AEF133_1.equals(ef12.getAef133())){//һ�����
			/**
			 * �����걨��AEF135�����걨���� 2
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_2);
			}
		}else if(HRAgencyUtils.AEF133_2.equals(ef12.getAef133())){//һ�����
			/**
			 * �����걨��AEF135�����걨���� 4
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_4);
			}
		}else {
			/**
			 * �����걨��AEF135�����걨���� 6
			 */
			if(ef11_declare!=null){
				this.updateEf11DeclareByEef101(ef11_declare, HRAgencyUtils.AEF135_6);
			}
		}
		int batupdatenum=hrAgencyRegAuditMapper.saveNotPassAuditData(map);
		
		if(batupdatenum==1){
			return this.success("������Դ���������˲�ͨ�������ɹ�");
		}else {
			return this.error("������Դ���������˲�ͨ������ʧ��");
		}
	    
	}
}

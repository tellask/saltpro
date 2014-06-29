package com.subject.dao.impl;

import java.util.List;

import marst.component.FinalString;
import marst.util.ComStringUtil;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.dbcom.dataset.QueryPageResult;
import com.subject.dao.SystemMenusDao;
import com.subject.pojo.SystemMenus;
@Repository(value="systemMenusDao")
public class SystemMenusDaoImpl extends BaseDao implements SystemMenusDao {

	@Override
	public List<SystemMenus> getAllList() throws Exception {
		List<SystemMenus> resList=null;
		try {
			String sql="select * from system_Menus where status=0 order by menusCode";
			DataSet res=super.executeQuery(sql);
			resList=super.toObjects(res, SystemMenus.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

	@Override
	public QueryPageResult getMenusListForBg(SystemMenus systemMenus, String beginDate,
			String endDate,String pageIndex,String pagesize,String sortname,String sortorder) throws Exception {
		//计算开始行数
		int startRowNum=0;
		if(ComStringUtil.isEmpty(pagesize)){pagesize=FinalString.PG_PAGESIZE;}
		if(ComStringUtil.isEmpty(pageIndex)){
			pageIndex=FinalString.PG_PAGEINDEX;
		}else{
			startRowNum=(Integer.parseInt(pageIndex)-1)*(Integer.parseInt(pagesize));
		}
		String sql="select sysms.id,sysms.menusCode,sysms.menusName,sysms.menusUrl,sysms.menusImg,sysms.parentMenusId,cmty.comTypeName,"+
		" rg.rightsTypeName,sysms.createTime,atu.userName,case when sysms.status=0 then '启用'  when sysms.status=1 then '禁用' end as status "+
		" from system_Menus sysms "+
		" left join company_Type cmty on sysms.comTypeId=cmty.id "+
		" left join account_User atu on sysms.createUserId=atu.id "+
		" left join system_rightsType rg on rg.id=sysms.rightsType ";
		QueryPageResult queryPageResult=new QueryPageResult();
		try {
			if(!ComStringUtil.isEmpty(systemMenus.getMenusCode())){
				sql+=" and sysms.menusCode like '%"+systemMenus.getMenusCode()+"%'";
			}
			if(!ComStringUtil.isEmpty(systemMenus.getMenusName())){
				sql+=" and sysms.menusName like '%"+systemMenus.getMenusName()+"%'";
			}
			if(!ComStringUtil.isEmpty(systemMenus.getMenusUrl())){
				sql+=" and sysms.menusUrl like '%"+systemMenus.getMenusUrl()+"%'";
			}
			if(!ComStringUtil.isEmpty(systemMenus.getMenusImg())){
				sql+=" and sysms.menusImg like '%"+systemMenus.getMenusImg()+"%'";
			}
			if(systemMenus.getComTypeId()!=0){
				sql+=" and sysms.comTypeId="+systemMenus.getComTypeId();
			}
			if(systemMenus.getParentMenusId()!=0){
				sql+=" and sysms.parentMenusId="+systemMenus.getParentMenusId();
			}
			if(!ComStringUtil.isEmpty(systemMenus.getStatus())){
				sql+=" and sysms.status="+systemMenus.getStatus();
			}
			if(!ComStringUtil.isEmpty(beginDate)){
				sql+=" and sysms.createTime>='"+beginDate+"'";
			}
			if(!ComStringUtil.isEmpty(endDate)){
				sql+=" and sysms.createTime<='"+endDate+"'";
			}
			if(!ComStringUtil.isEmpty(sortname) && !ComStringUtil.isEmpty(sortorder)){
				String[] sortNameArray={"sysms.id","sysms.menusCode","sysms.menusName","sysms.menusUrl","sysms.menusImg","sysms.parentMenusId"
			  ,"cmty.comTypeName","rg.rightsTypeName","sysms.createTime","atu.userName","sysms.status"};
				sql+=" order by "+sortNameArray[Integer.parseInt(sortname)]+" "+sortorder;
			}else{
				sql+=" order by sysms.id";
			}
			queryPageResult = super.executeQuery(startRowNum, Integer.parseInt(pagesize), sql, null);
		} catch (Exception e) {
			throw e;
		}
		return queryPageResult;
	}

	@Override
	public void insertSystemMenus(SystemMenus systemMenus) throws Exception {
		try {
			super.insertObject(systemMenus);
		} catch (Exception e) {
			throw e;
		}
	}

}

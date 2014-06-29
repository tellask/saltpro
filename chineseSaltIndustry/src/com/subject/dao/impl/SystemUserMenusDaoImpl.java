package com.subject.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.SystemUserMenusDao;
import com.subject.pojo.SystemUserMenus;
@Repository(value="systemUserMenusDao")
public class SystemUserMenusDaoImpl extends BaseDao implements SystemUserMenusDao {

	@Override
	public List<SystemUserMenus> getList(SystemUserMenus systemUserauth) throws Exception {
		List<SystemUserMenus> resList=null;
		try {
			String sql="select * from system_UserAuth where status=0 ";
			sql+=" and userId=?";
			sql+=" and roleId=?";
			sql+=" and authFlag=?";
			DataSet res=super.executeQuery(sql, systemUserauth.getUserId(),systemUserauth.getRoleId(),systemUserauth.getAuthFlag());
			resList=super.toObjects(res, SystemUserMenus.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

	@Override
	public List<SystemUserMenus> getListByUserId(int userId)
			throws Exception {
		List<SystemUserMenus> resList=new ArrayList<SystemUserMenus>();
		SystemUserMenus systemUserauth =new SystemUserMenus();
		systemUserauth.setUserId(userId);
		List<SystemUserMenus> UserAuthList= this.getList(systemUserauth);
		for (int i = 0; i <UserAuthList.size(); i++) {
			SystemUserMenus userAuth=UserAuthList.get(i);
			//如果是权限，则直接加入，如果是角色，则继续查找权限
			if(userAuth.getAuthFlag()==0){
				resList.add(userAuth);
			}else{
				
			}
		}
		return resList;
	}

	@Override
	public List<SystemUserMenus> getAllList() throws Exception {
		List<SystemUserMenus> resList=null;
		try {
			String sql="select * from system_UserMenus where status=0 ";
			DataSet res=super.executeQuery(sql);
			resList=super.toObjects(res, SystemUserMenus.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

	@Override
	public List<Integer> getMenusIdByUserId(Object userId) throws Exception {
		List<Integer> resList=null;
		try {
			String sql="select roleId from system_UserMenus where status=0 and authFlag=0 and userId=? ";
			DataSet res=super.executeQuery(sql,userId);
			if(res.getRowCount()>0){
				resList=new ArrayList<Integer>();
				while (res.next()) {
					resList.add(res.getInt(0));
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

	@Override
	public List<Integer> getRolesIdByUserId(Object userId) throws Exception {
		List<Integer> resList=null;
		try {
			String sql="select roleId from system_UserMenus where status=0 and authFlag=1 and userId=? ";
			DataSet res=super.executeQuery(sql,userId);
			if(res.getRowCount()>0){
				resList=new ArrayList<Integer>();
				while (res.next()) {
					resList.add(res.getInt(0));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

}

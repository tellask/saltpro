package com.subject.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.SystemRoleMenusDao;
import com.subject.pojo.SystemRolemenus;
@Repository(value="systemRoleMenusDao")
public class SystemRoleMenusDaoImpl extends BaseDao implements
		SystemRoleMenusDao {
	
	@Override
	public List<SystemRolemenus> getList(int RoleId) throws Exception {
		List<SystemRolemenus> resList=null;
		try {
			String sql="select * from system_RoleMenus where status=0 ";
			sql+=" and roleId=?";
			sql+=" order by roleId,authFlag,menusId";
			DataSet res=super.executeQuery(sql, RoleId);
			resList=super.toObjects(res, SystemRolemenus.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

	@Override
	public List<SystemRolemenus> getAllList() throws Exception {
		List<SystemRolemenus> resList=null;
		try {
			String sql="select * from system_RoleMenus where status=0 order by roleId,authFlag,menusId";
			DataSet res=super.executeQuery(sql);
			resList=super.toObjects(res, SystemRolemenus.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

}

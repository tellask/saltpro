package com.subject.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.SystemRoleDao;
import com.subject.pojo.SystemRole;
@Repository(value="systemRoleDao")
public class SystemRoleDaoImpl extends BaseDao implements SystemRoleDao {

	@Override
	public List<SystemRole> getList(SystemRole systemRole) throws Exception {
		List<SystemRole> resList=null;
		try {
			String sql="select * from system_Role where status=0 ";
			sql+=" and roleName like '%?%'";
			DataSet res=super.executeQuery(sql, systemRole.getRoleName());
			resList=super.toObjects(res, SystemRole.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

}

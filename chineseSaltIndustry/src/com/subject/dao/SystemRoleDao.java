package com.subject.dao;

import java.util.List;
import com.subject.pojo.SystemRole;

public interface SystemRoleDao {
	/**
	 * 根据对象拿到集合
	 * @param systemRole
	 * @return
	 * @throws Exception
	 */
	public List<SystemRole> getList(SystemRole systemRole) throws Exception;
}

package com.subject.dao;

import java.util.List;

import com.subject.pojo.SystemRolemenus;

public interface SystemRoleMenusDao {
	/**
	 * 根据对象拿到集合
	 * @param systemRoleMenus
	 * @return
	 * @throws Exception
	 */
	public List<SystemRolemenus> getList(int RoleId) throws Exception;
	/**
	 * 拿到所有的角色与权限
	 * @return
	 * @throws Exception
	 */
	public List<SystemRolemenus> getAllList() throws Exception;
}

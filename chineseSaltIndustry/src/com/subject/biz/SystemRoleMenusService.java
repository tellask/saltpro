package com.subject.biz;

import java.util.List;

import com.subject.pojo.SystemRolemenus;

public interface SystemRoleMenusService {
	
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

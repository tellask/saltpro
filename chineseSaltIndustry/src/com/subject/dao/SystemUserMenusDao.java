package com.subject.dao;

import java.util.List;

import com.subject.pojo.SystemUserMenus;

public interface SystemUserMenusDao {
	/**
	 * 根据对象拿到集合
	 * @param systemUserauth
	 * @return
	 * @throws Exception
	 */
	public List<SystemUserMenus>  getList(SystemUserMenus systemUserauth) throws Exception;
	/**
	 * 根据用户ID拿到相应菜单
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SystemUserMenus>  getListByUserId(int userId) throws Exception;
	/**
	 * 拿到所有用户和菜单之间的关系
	 * @return
	 * @throws Exception
	 */
	public List<SystemUserMenus>  getAllList()  throws Exception;
	/**
	 * 根据用户Id拿到菜单ID集合
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getMenusIdByUserId(Object userId)  throws Exception;
	/**
	 * 根据用户Id拿到角色ID集合
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getRolesIdByUserId(Object userId)  throws Exception;
}

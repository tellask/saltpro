package com.subject.biz;

import java.util.List;

import com.subject.pojo.SystemMenus;
import com.subject.pojo.SystemUserMenus;

public interface SystemUserMenusService {
	/**
	 * 根据对象拿到集合
	 * @param systemUserauth
	 * @return
	 * @throws Exception
	 */
	public List<SystemUserMenus> getList(SystemUserMenus systemUserauth);
	/**
	 * 根据uerId拿到相应的菜单
	 * @param userId
	 * @return
	 */
	public List<SystemMenus> getMenusListByUserId(int userId);
	/**
	 * 清理缓存中的菜单项
	 */
	public void clearMenus(int userId);
}

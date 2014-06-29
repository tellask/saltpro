package com.subject.dao;

import com.subject.pojo.AccountUser;

public interface AccountUserDao {
	/**
	 * 查询账号是否存在
	 * @param userName
	 * @return
	 */
	public AccountUser getInfoByUserName(String userName) throws Exception; 
	/**
	 * 根据账号密码查询用户对象
	 * @param accountUser
	 * @return
	 */
	public AccountUser getInfoByUser(AccountUser accountUser) throws Exception;
	
	/**
	 * 添加用户
	 * @param accountUser
	 * @return
	 * @throws Exception
	 */
	public boolean insertInfo(AccountUser accountUser) throws Exception;
}

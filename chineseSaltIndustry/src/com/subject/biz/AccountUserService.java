package com.subject.biz;

import com.subject.pojo.AccountUser;

public interface AccountUserService {
	/**
	 * 查询账号是否存在
	 * @param userName
	 * @return
	 */
	public AccountUser getInfoByUserName(String userName); 
	/**
	 * 根据账号密码查询用户对象
	 * @param accountUser
	 * @return
	 */
	public AccountUser getInfoByUser(AccountUser accountUser);
	/**
	 * 添加用户
	 * @param accountUser
	 * @return
	 * @throws Exception
	 */
	public boolean insertInfo(AccountUser accountUser);
}

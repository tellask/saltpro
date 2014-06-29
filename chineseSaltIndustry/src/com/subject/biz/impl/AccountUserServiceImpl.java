package com.subject.biz.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.AccountUserService;
import com.subject.dao.AccountUserDao;
import com.subject.pojo.AccountUser;

@Service(value="accountUserService")
@WebService(serviceName="accountUserService",endpointInterface="com.subject.biz.AccountUserService")
public class AccountUserServiceImpl implements AccountUserService  {
	
	@Autowired
	private AccountUserDao accountUserDao;
	
	public AccountUserDao getAccountUserDaoImpl() {
		return accountUserDao;
	}

	public void setAccountUserDaoImpl(AccountUserDao accountUserDaoImpl) {
		this.accountUserDao = accountUserDaoImpl;
	}

	@Override
	public AccountUser getInfoByUserName(String userName) {
		AccountUser accountuser=null;
		try {
			accountuser=accountUserDao.getInfoByUserName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountuser;
	}

	@Override
	public AccountUser getInfoByUser(AccountUser accountUser) {
		AccountUser accountuser=null;
		try {
			accountuser=accountUserDao.getInfoByUser(accountUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountuser;
	}

	@Override
	public boolean insertInfo(AccountUser accountUser)  {
		boolean res=false;
		try {
			res=accountUserDao.insertInfo(accountUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}

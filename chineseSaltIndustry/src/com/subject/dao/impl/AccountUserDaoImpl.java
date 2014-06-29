package com.subject.dao.impl;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.AccountUserDao;
import com.subject.pojo.AccountUser;

@Repository(value="accountUserDao")
public class AccountUserDaoImpl extends BaseDao implements AccountUserDao {

	@Override
	public AccountUser getInfoByUserName(String accountNum) throws Exception {
		AccountUser result=null;
		String sql="select * from account_User where status=0 and accountNum=? ";
		try {
			DataSet dataSet=super.executeQuery(sql, accountNum);
			result=super.toObject(dataSet, AccountUser.class);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	@Override
	public AccountUser getInfoByUser(AccountUser accountUser) throws Exception {
		AccountUser result=null;
		try {
			String sql="select * from account_User where status=0 and accountNum=? and passWord=?";
			DataSet dataSet=super.executeQuery(sql, accountUser.getAccountNum(),accountUser.getPassWord());
			result=super.toObject(dataSet, AccountUser.class);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	@Override
	public boolean insertInfo(AccountUser accountUser) throws Exception {
		boolean res=false;
		try {
			super.insertObject(accountUser);
			res=true;
		} catch (Exception e) {
			throw e;
		}
		return res;
	}
}

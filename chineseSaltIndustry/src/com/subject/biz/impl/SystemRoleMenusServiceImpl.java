package com.subject.biz.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.SystemRoleMenusService;
import com.subject.dao.SystemRoleMenusDao;
import com.subject.pojo.SystemRolemenus;

@Service(value="systemRoleMenusService")
@WebService(serviceName="systemRoleMenusService",endpointInterface="com.subject.biz.SystemRoleMenusService")
public class SystemRoleMenusServiceImpl implements SystemRoleMenusService {
	@Autowired
	private SystemRoleMenusDao systemRoleMenusDao;

	public SystemRoleMenusDao getSystemRoleMenusDao() {
		return systemRoleMenusDao;
	}

	public void setSystemRoleMenusDao(SystemRoleMenusDao systemRoleMenusDao) {
		this.systemRoleMenusDao = systemRoleMenusDao;
	}

	@Override
	public List<SystemRolemenus> getList(int RoleId) throws Exception {
		List<SystemRolemenus> res=null;
		try {
			res=systemRoleMenusDao.getList(RoleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public List<SystemRolemenus> getAllList() throws Exception {
		List<SystemRolemenus> res=null;
		try {
			res=systemRoleMenusDao.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
}

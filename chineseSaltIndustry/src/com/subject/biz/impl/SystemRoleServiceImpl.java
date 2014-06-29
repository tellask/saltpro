package com.subject.biz.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.SystemRoleService;
import com.subject.dao.SystemRoleDao;
import com.subject.pojo.SystemRole;

@Service(value="systemRoleService")
@WebService(serviceName="systemRoleService",endpointInterface="com.subject.biz.SystemRoleService")
public class SystemRoleServiceImpl implements SystemRoleService {
	@Autowired
	private SystemRoleDao systemRoleDao;
	
	public SystemRoleDao getSystemRoleDao() {
		return systemRoleDao;
	}

	public void setSystemRoleDao(SystemRoleDao systemRoleDao) {
		this.systemRoleDao = systemRoleDao;
	}
	
	@Override
	public List<SystemRole> getList(SystemRole systemRole) {
		List<SystemRole> res=null;
		try {
			res=systemRoleDao.getList(systemRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}

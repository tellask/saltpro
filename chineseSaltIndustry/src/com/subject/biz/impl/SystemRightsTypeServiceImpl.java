package com.subject.biz.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.SystemRightsTypeService;
import com.subject.dao.SystemRightsTypeDao;
import com.subject.pojo.SystemRightsType;

@Service(value="systemRightsTypeService")
@WebService(serviceName="systemRightsTypeService",endpointInterface="com.subject.biz.SystemRightsTypeService")
public class SystemRightsTypeServiceImpl implements SystemRightsTypeService {
	@Autowired
	private SystemRightsTypeDao systemRightsTypeDao;
	@Override
	public List<SystemRightsType> getAllList() {
		List<SystemRightsType> res=null;
		try {
			res=systemRightsTypeDao.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public SystemRightsTypeDao getSystemRightsTypeDao() {
		return systemRightsTypeDao;
	}
	public void setSystemRightsTypeDao(SystemRightsTypeDao systemRightsTypeDao) {
		this.systemRightsTypeDao = systemRightsTypeDao;
	}
	
}

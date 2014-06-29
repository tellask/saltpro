package com.subject.biz.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dbcom.dataset.QueryPageResult;
import com.subject.biz.SystemMenusService;
import com.subject.dao.SystemMenusDao;
import com.subject.pojo.SystemMenus;

@Service(value="systemMenusService")
@WebService(serviceName="systemMenusService",endpointInterface="com.subject.biz.SystemMenusService")
public class SystemMenusServiceImpl implements SystemMenusService {

	@Autowired
	private SystemMenusDao systemMenusDao;
	
	public SystemMenusDao getSystemMenusDao() {
		return systemMenusDao;
	}

	public void setSystemMenusDao(SystemMenusDao systemMenusDao) {
		this.systemMenusDao = systemMenusDao;
	}

	@Override
	public List<SystemMenus> getAllList() {
		List<SystemMenus> res=null;
		try {
			res=systemMenusDao.getAllList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public QueryPageResult getMenusListForBg(SystemMenus systemMenus, String beginDate,
			String endDate,String pageIndex,String pagesize,String sortname,String sortorder){
		QueryPageResult queryPageResult=new QueryPageResult();
		try {
			queryPageResult=systemMenusDao.getMenusListForBg(systemMenus,beginDate,endDate,pageIndex,pagesize,sortname,sortorder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return queryPageResult;
	}

	@Override
	public void insertSystemMenus(SystemMenus systemMenus) {
		try {
			systemMenusDao.insertSystemMenus(systemMenus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

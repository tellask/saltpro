package com.subject.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.SystemRightsTypeDao;
import com.subject.pojo.SystemRightsType;

@Repository(value="systemRightsTypeDao")
public class SystemRightsTypeDaoImpl extends BaseDao implements
		SystemRightsTypeDao {

	@Override
	public List<SystemRightsType> getAllList() throws Exception {
		List<SystemRightsType> resList=null;
		try {
			String sql="select * from system_rightsType where status=0 ";
			DataSet res=super.executeQuery(sql);
			resList=super.toObjects(res, SystemRightsType.class);
		} catch (Exception e) {
			throw e;
		}
		return resList;
	}

}

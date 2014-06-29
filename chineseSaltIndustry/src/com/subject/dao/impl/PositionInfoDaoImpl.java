package com.subject.dao.impl;

import org.springframework.stereotype.Repository;

import com.dbcom.DataSet;
import com.subject.dao.PositionInfoDao;
import com.subject.pojo.PositionInfo;

@Repository(value="positionInfoDao")
public class PositionInfoDaoImpl extends BaseDao implements PositionInfoDao {

	@Override
	public PositionInfo getInfoById(int positionId) throws Exception{
		PositionInfo result=null;
		String sql="select * from position_Info where id=?";
		try {
			DataSet dataSet=super.executeQuery(sql, positionId);
			result=super.toObject(dataSet, PositionInfo.class);
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public boolean insertInfo(PositionInfo positionInfo) throws Exception{
		boolean res=false;
		try {
			super.insertObject(positionInfo);
			res=true;
		} catch (Exception e) {
			throw e;
		}
		return res;
	}

}

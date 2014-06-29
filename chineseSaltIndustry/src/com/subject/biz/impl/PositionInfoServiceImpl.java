package com.subject.biz.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.PositionInfoService;
import com.subject.dao.PositionInfoDao;
import com.subject.pojo.PositionInfo;

@Service(value="positionInfoService")
@WebService(serviceName="positionInfoService",endpointInterface="com.subject.biz.PositionInfoService")
public class PositionInfoServiceImpl implements PositionInfoService {
	
	@Autowired
	private PositionInfoDao positionInfoDao;
	
	public PositionInfoDao getPositionInfoDao() {
		return positionInfoDao;
	}

	public void setPositionInfoDao(PositionInfoDao positionInfoDao) {
		this.positionInfoDao = positionInfoDao;
	}

	@Override
	public PositionInfo getInfoById(int positionId) {
		PositionInfo positionInfo=null;
		try {
			positionInfo=positionInfoDao.getInfoById(positionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return positionInfo;
	}

	@Override
	public boolean insertInfo(PositionInfo positionInfo) {
		boolean res=false;
		try {
			res=positionInfoDao.insertInfo(positionInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}

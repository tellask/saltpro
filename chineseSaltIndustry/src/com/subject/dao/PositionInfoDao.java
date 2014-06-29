package com.subject.dao;

import com.subject.pojo.PositionInfo;

public interface PositionInfoDao {
	/**
	 * 根据ID拿到对象
	 * @param positionId
	 * @return
	 * @throws Exception
	 */
	public PositionInfo getInfoById(int positionId)  throws Exception;
	/**
	 * 新增
	 * @param positionInfo
	 * @return
	 * @throws Exception
	 */
	public boolean insertInfo(PositionInfo positionInfo)  throws Exception;
}

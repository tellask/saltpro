package com.subject.biz;

import com.subject.pojo.PositionInfo;

public interface PositionInfoService {
	/**
	 * 根据ID拿到对象
	 * @param positionId
	 * @return
	 */
	public PositionInfo getInfoById(int positionId);
	/**
	 * 新增
	 * @param positionInfo
	 * @return
	 */
	public boolean insertInfo(PositionInfo positionInfo);
}

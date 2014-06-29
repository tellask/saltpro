package com.subject.biz;

import java.util.List;

import com.subject.pojo.SystemRole;

public interface SystemRoleService {
	/**
	 * 根据对象拿到集合
	 * @param systemRole
	 * @return
	 * @throws Exception
	 */
	public List<SystemRole> getList(SystemRole systemRole);
}

package com.subject.dao;

import java.util.List;

import com.dbcom.dataset.QueryPageResult;
import com.subject.pojo.SystemMenus;

public interface SystemMenusDao {
	/**
	 * 拿到所有菜单
	 * @param systemMenus
	 * @return
	 * @throws Exception
	 */
	public List<SystemMenus> getAllList() throws Exception;
	/**
	 * 根据查询条件分页查询，并返回分页对象
	 * @param systemMenus
	 * @param beginDate
	 * @param endDate
	 * @param pageIndex 页码
	 * @param pagesize 每页数
	 * @param sortname 排序字段
	 * @param sortorder 排序方向
	 * @return
	 * @throws Exception
	 */
	public QueryPageResult getMenusListForBg(SystemMenus systemMenus,String beginDate,String endDate,String pageIndex,String pagesize,String sortname,String sortorder) throws Exception;
	/**
	 * 新增
	 * @param systemMenus
	 * @throws Exception
	 */
	public void insertSystemMenus(SystemMenus systemMenus)  throws Exception;
}

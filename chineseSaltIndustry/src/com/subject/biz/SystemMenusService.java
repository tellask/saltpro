package com.subject.biz;

import java.util.List;

import com.dbcom.dataset.QueryPageResult;
import com.subject.pojo.SystemMenus;

public interface SystemMenusService {
	/**
	 * 拿到所有菜单
	 * @param systemMenus
	 * @return
	 * @throws Exception
	 */
	public List<SystemMenus> getAllList();
	/**
	 * 根据查询条件分页查询，并返回分页对象
	 * @param systemMenus
	 * @param beginDate
	 * @param endDate
	 * @param pageIndex 页码
	 * @param pagesize 每页数量
	 * @param sortname 排序字段
	 * @param sortorder 排序方式
	 * @return
	 */
	public QueryPageResult getMenusListForBg(SystemMenus systemMenus,String beginDate,String endDate,String pageIndex,String pagesize,String sortname,String sortorder);
	/**
	 * 新增
	 * @param systemMenus
	 * @throws Exception
	 */
	public void insertSystemMenus(SystemMenus systemMenus);
}

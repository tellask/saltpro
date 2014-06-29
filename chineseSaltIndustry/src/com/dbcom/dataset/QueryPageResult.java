package com.dbcom.dataset;

import java.io.Serializable;

import com.dbcom.DataSet;
/**
 * 分页查询时的返回对象
 * @author taddy
 *
 */
public class QueryPageResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//返回的本页集合
	private DataSet queryList;
	//返回的总记录数
	private int totalCount;
	
	public DataSet getQueryList() {
		return queryList;
	}
	public void setQueryList(DataSet queryList) {
		this.queryList = queryList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}

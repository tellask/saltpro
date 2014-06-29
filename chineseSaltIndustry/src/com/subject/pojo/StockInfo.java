package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("stock_info")
public class StockInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String stockCodeNo;

	@DBField
	private String stockName;

	@DBField
	private String stockAddress;

	@DBField
	private int comId;

	@DBField
	private String orderBy;

	@DBField
	private Timestamp createTime;

	@DBField
	private int createUserId;

	@DBField
	private String status="0";

	@DBField
	private String remark;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setStockCodeNo(String stockCodeNo) {
		this.stockCodeNo = stockCodeNo;
	}

	public String getStockCodeNo() {
		return stockCodeNo;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockAddress(String stockAddress) {
		this.stockAddress = stockAddress;
	}

	public String getStockAddress() {
		return stockAddress;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	public int getComId() {
		return comId;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

}

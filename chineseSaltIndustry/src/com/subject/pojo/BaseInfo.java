package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("base_info")
public class BaseInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String baseCodeNo;

	@DBField
	private String baseVal;

	@DBField
	private int baseTypeId;

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

	public void setBaseCodeNo(String baseCodeNo) {
		this.baseCodeNo = baseCodeNo;
	}

	public String getBaseCodeNo() {
		return baseCodeNo;
	}

	public void setBaseVal(String baseVal) {
		this.baseVal = baseVal;
	}

	public String getBaseVal() {
		return baseVal;
	}

	public void setBaseTypeId(int baseTypeId) {
		this.baseTypeId = baseTypeId;
	}

	public int getBaseTypeId() {
		return baseTypeId;
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

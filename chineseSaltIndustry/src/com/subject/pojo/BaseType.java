package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("base_type")
public class BaseType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String baseTypeName;

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

	public void setBaseTypeName(String baseTypeName) {
		this.baseTypeName = baseTypeName;
	}

	public String getBaseTypeName() {
		return baseTypeName;
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

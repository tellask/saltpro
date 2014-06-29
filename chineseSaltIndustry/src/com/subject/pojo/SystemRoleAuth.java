package com.subject.pojo;

import java.sql.*;
import com.dbcom.annotation.*;
import java.io.Serializable;

@DBTable("system_RoleAuth")
public class SystemRoleAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private int roleId;

	@DBField
	private int authId;

	@DBField
	private int authFlag;

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

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}

	public int getAuthId() {
		return authId;
	}

	public void setAuthFlag(int authFlag) {
		this.authFlag = authFlag;
	}

	public int getAuthFlag() {
		return authFlag;
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

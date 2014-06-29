package com.subject.pojo;

import java.sql.*;
import com.dbcom.annotation.*;
import java.io.Serializable;

@DBTable("system_Auth")
public class SystemAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String authType="0";

	@DBField
	private int menusId=0;

	@DBField
	private String authName;

	@DBField
	private String authUrl;

	@DBField
	private String authMemo;

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

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setMenusId(int menusId) {
		this.menusId = menusId;
	}

	public int getMenusId() {
		return menusId;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthMemo(String authMemo) {
		this.authMemo = authMemo;
	}

	public String getAuthMemo() {
		return authMemo;
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

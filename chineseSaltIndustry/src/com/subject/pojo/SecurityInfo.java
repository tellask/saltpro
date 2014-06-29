package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("security_info")
public class SecurityInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String securityCodeNo;

	@DBField
	private String securityName;

	@DBField
	private int securityTypeId;

	@DBField
	private int securitySpecId;

	@DBField
	private int securityMatId;

	@DBField
	private String imgUrl;

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

	public void setSecurityCodeNo(String securityCodeNo) {
		this.securityCodeNo = securityCodeNo;
	}

	public String getSecurityCodeNo() {
		return securityCodeNo;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityTypeId(int securityTypeId) {
		this.securityTypeId = securityTypeId;
	}

	public int getSecurityTypeId() {
		return securityTypeId;
	}

	public void setSecuritySpecId(int securitySpecId) {
		this.securitySpecId = securitySpecId;
	}

	public int getSecuritySpecId() {
		return securitySpecId;
	}

	public void setSecurityMatId(int securityMatId) {
		this.securityMatId = securityMatId;
	}

	public int getSecurityMatId() {
		return securityMatId;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
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

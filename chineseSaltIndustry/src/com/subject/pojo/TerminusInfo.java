package com.subject.pojo;

import java.sql.*;
import com.dbcom.annotation.*;
import java.io.Serializable;

@DBTable("terminus_info")
public class TerminusInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String terminusCodeNo;

	@DBField
	private String terminusName;

	@DBField
	private String terminusShortName;

	@DBField
	private String terminusAddress;

	@DBField
	private Timestamp createTime;

	@DBField
	private int createUserId;

	@DBField
	private String status;

	@DBField
	private String remark;

	@DBField
	private int respLocalId;

	@DBField
	private int companyInfoId;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setTerminusCodeNo(String terminusCodeNo) {
		this.terminusCodeNo = terminusCodeNo;
	}

	public String getTerminusCodeNo() {
		return terminusCodeNo;
	}

	public void setTerminusName(String terminusName) {
		this.terminusName = terminusName;
	}

	public String getTerminusName() {
		return terminusName;
	}

	public void setTerminusShortName(String terminusShortName) {
		this.terminusShortName = terminusShortName;
	}

	public String getTerminusShortName() {
		return terminusShortName;
	}

	public void setTerminusAddress(String terminusAddress) {
		this.terminusAddress = terminusAddress;
	}

	public String getTerminusAddress() {
		return terminusAddress;
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

	public void setRespLocalId(int respLocalId) {
		this.respLocalId = respLocalId;
	}

	public int getRespLocalId() {
		return respLocalId;
	}

	public void setCompanyInfoId(int companyInfoId) {
		this.companyInfoId = companyInfoId;
	}

	public int getCompanyInfoId() {
		return companyInfoId;
	}

}

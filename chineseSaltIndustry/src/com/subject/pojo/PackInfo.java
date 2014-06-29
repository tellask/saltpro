package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("pack_info")
public class PackInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String packCodeNo;

	@DBField
	private int bagInfoId;

	@DBField
	private int outBagInfoId;

	@DBField
	private int securityInfoId;

	@DBField
	private int bagNum;

	@DBField
	private int securityNum;

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

	public void setPackCodeNo(String packCodeNo) {
		this.packCodeNo = packCodeNo;
	}

	public String getPackCodeNo() {
		return packCodeNo;
	}

	public void setBagInfoId(int bagInfoId) {
		this.bagInfoId = bagInfoId;
	}

	public int getBagInfoId() {
		return bagInfoId;
	}

	public void setOutBagInfoId(int outBagInfoId) {
		this.outBagInfoId = outBagInfoId;
	}

	public int getOutBagInfoId() {
		return outBagInfoId;
	}

	public void setSecurityInfoId(int securityInfoId) {
		this.securityInfoId = securityInfoId;
	}

	public int getSecurityInfoId() {
		return securityInfoId;
	}

	public void setBagNum(int bagNum) {
		this.bagNum = bagNum;
	}

	public int getBagNum() {
		if(this.bagNum==0){
			this.bagNum=1;
		}
		return bagNum;
	}

	public void setSecurityNum(int securityNum) {
		this.securityNum = securityNum;
	}

	public int getSecurityNum() {
		if(this.securityNum==0){
			this.securityNum=1;
		}
		return securityNum;
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

package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("company_info")
public class CompanyInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String comCodeNo;

	@DBField
	private String comName;

	@DBField
	private String comShortName;

	@DBField
	private String comTelephone;

	@DBField
	private String comLinkMan;

	@DBField
	private String comAddress;

	@DBField
	private String openBank;

	@DBField
	private String openBankNum;

	@DBField
	private String taxpayerNum;

	@DBField
	private Timestamp createTime;

	@DBField
	private int createUserId;

	@DBField
	private String status="0";

	@DBField
	private String remark;

	@DBField
	private int parentComId;

	@DBField
	private int respLocalId;

	@DBField
	private int comTypeId;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setComCodeNo(String comCodeNo) {
		this.comCodeNo = comCodeNo;
	}

	public String getComCodeNo() {
		return comCodeNo;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComName() {
		return comName;
	}

	public void setComShortName(String comShortName) {
		this.comShortName = comShortName;
	}

	public String getComShortName() {
		return comShortName;
	}

	public void setComTelephone(String comTelephone) {
		this.comTelephone = comTelephone;
	}

	public String getComTelephone() {
		return comTelephone;
	}

	public void setComLinkMan(String comLinkMan) {
		this.comLinkMan = comLinkMan;
	}

	public String getComLinkMan() {
		return comLinkMan;
	}

	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}

	public String getComAddress() {
		return comAddress;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBankNum(String openBankNum) {
		this.openBankNum = openBankNum;
	}

	public String getOpenBankNum() {
		return openBankNum;
	}

	public void setTaxpayerNum(String taxpayerNum) {
		this.taxpayerNum = taxpayerNum;
	}

	public String getTaxpayerNum() {
		return taxpayerNum;
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

	public void setParentComId(int parentComId) {
		this.parentComId = parentComId;
	}

	public int getParentComId() {
		return parentComId;
	}

	public void setRespLocalId(int respLocalId) {
		this.respLocalId = respLocalId;
	}

	public int getRespLocalId() {
		return respLocalId;
	}

	public void setComTypeId(int comTypeId) {
		this.comTypeId = comTypeId;
	}

	public int getComTypeId() {
		return comTypeId;
	}

}

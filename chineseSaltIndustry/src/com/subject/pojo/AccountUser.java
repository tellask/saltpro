package com.subject.pojo;

import java.sql.*;
import com.dbcom.annotation.*;
import java.io.Serializable;

@DBTable("account_user")
public class AccountUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String accountNum;

	@DBField
	private String passWord;

	@DBField
	private String userName;

	@DBField
	private String userSex="0";

	@DBField
	private Timestamp userBirthday;

	@DBField
	private String userTelephone;

	@DBField
	private String userQQNum;

	@DBField
	private String emailUrl;

	@DBField
	private Timestamp createTime;

	@DBField
	private String status="0";

	@DBField
	private int accountType=3;

	@DBField
	private Timestamp lastTime;

	@DBField
	private String lastIp;

	@DBField
	private String remark;

	@DBField
	private int companyInfoId;

	@DBField
	private String isLookOthers="0";

	@DBField
	private String isEditOthers="0";

	@DBField
	private int positionId;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserBirthday(Timestamp userBirthday) {
		this.userBirthday = userBirthday;
	}

	public Timestamp getUserBirthday() {
		return userBirthday;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserQQNum(String userQQNum) {
		this.userQQNum = userQQNum;
	}

	public String getUserQQNum() {
		return userQQNum;
	}

	public void setEmailUrl(String emailUrl) {
		this.emailUrl = emailUrl;
	}

	public String getEmailUrl() {
		return emailUrl;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setCompanyInfoId(int companyInfoId) {
		this.companyInfoId = companyInfoId;
	}

	public int getCompanyInfoId() {
		return companyInfoId;
	}

	public void setIsLookOthers(String isLookOthers) {
		this.isLookOthers = isLookOthers;
	}

	public String getIsLookOthers() {
		return isLookOthers;
	}

	public void setIsEditOthers(String isEditOthers) {
		this.isEditOthers = isEditOthers;
	}

	public String getIsEditOthers() {
		return isEditOthers;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getPositionId() {
		return positionId;
	}

}

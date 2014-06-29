package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("salt_info")
public class SaltInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String saltCodeNo;

	@DBField
	private String saltName;

	@DBField
	private String nickName;

	@DBField
	private int saltTypeId;

	@DBField
	private String imgUrl;

	@DBField
	private String barCode;

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

	public void setSaltCodeNo(String saltCodeNo) {
		this.saltCodeNo = saltCodeNo;
	}

	public String getSaltCodeNo() {
		return saltCodeNo;
	}

	public void setSaltName(String saltName) {
		this.saltName = saltName;
	}

	public String getSaltName() {
		return saltName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setSaltTypeId(int saltTypeId) {
		this.saltTypeId = saltTypeId;
	}

	public int getSaltTypeId() {
		return saltTypeId;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCode() {
		return barCode;
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

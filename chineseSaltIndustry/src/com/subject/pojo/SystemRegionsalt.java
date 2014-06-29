package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("system_regionsalt")
public class SystemRegionsalt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private int comId;

	@DBField
	private int saltPackId;

	@DBField
	private double sendMaxSalt;

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

	public void setComId(int comId) {
		this.comId = comId;
	}

	public int getComId() {
		return comId;
	}

	public void setSaltPackId(int saltPackId) {
		this.saltPackId = saltPackId;
	}

	public int getSaltPackId() {
		return saltPackId;
	}

	public void setSendMaxSalt(double sendMaxSalt) {
		this.sendMaxSalt = sendMaxSalt;
	}

	public double getSendMaxSalt() {
		return sendMaxSalt;
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

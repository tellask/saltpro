package com.subject.pojo;

import java.io.Serializable;

import com.dbcom.annotation.*;

@DBTable("system_regionsend")
public class SystemRegionsend implements Serializable{
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
	private int regionSaltId;

	@DBField
	private int sendComId;

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

	public void setRegionSaltId(int regionSaltId) {
		this.regionSaltId = regionSaltId;
	}

	public int getRegionSaltId() {
		return regionSaltId;
	}

	public void setSendComId(int sendComId) {
		this.sendComId = sendComId;
	}

	public int getSendComId() {
		return sendComId;
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

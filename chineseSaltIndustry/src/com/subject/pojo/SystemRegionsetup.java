package com.subject.pojo;

import java.io.Serializable;

import com.dbcom.annotation.*;

@DBTable("system_regionsetup")
public class SystemRegionsetup implements Serializable{
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
	private int sendDay=30;

	@DBField
	private String isReview="0";

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

	public void setSendDay(int sendDay) {
		this.sendDay = sendDay;
	}

	public int getSendDay() {
		return sendDay;
	}

	public void setIsReview(String isReview) {
		this.isReview = isReview;
	}

	public String getIsReview() {
		return isReview;
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

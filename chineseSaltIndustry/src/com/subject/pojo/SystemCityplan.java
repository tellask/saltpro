package com.subject.pojo;

import java.io.Serializable;

import com.dbcom.annotation.*;

@DBTable("system_cityplan")
public class SystemCityplan implements Serializable{
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
	private int planDay=30;

	@DBField
	private int additionalCount=5;

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

	public void setPlanDay(int planDay) {
		this.planDay = planDay;
	}

	public int getPlanDay() {
		return planDay;
	}

	public void setAdditionalCount(int additionalCount) {
		this.additionalCount = additionalCount;
	}

	public int getAdditionalCount() {
		return additionalCount;
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

package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("region_yearplan")
public class RegionYearplan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private int planYear;

	@DBField
	private int planMonth;

	@DBField
	private int comId;

	@DBField
	private int saltId;

	@DBField
	private double planCount;

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

	public void setPlanYear(int planYear) {
		this.planYear = planYear;
	}

	public int getPlanYear() {
		return planYear;
	}

	public void setPlanMonth(int planMonth) {
		this.planMonth = planMonth;
	}

	public int getPlanMonth() {
		return planMonth;
	}

	public void setComId(int comId) {
		this.comId = comId;
	}

	public int getComId() {
		return comId;
	}

	public void setSaltId(int saltId) {
		this.saltId = saltId;
	}

	public int getSaltId() {
		return saltId;
	}

	public void setPlanCount(double planCount) {
		this.planCount = planCount;
	}

	public double getPlanCount() {
		return planCount;
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

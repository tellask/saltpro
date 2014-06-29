package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("bag_info")
public class BagInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String bagCodeNo;

	@DBField
	private String bagName;

	@DBField
	private int bagTypeId;

	@DBField
	private int specId;

	@DBField
	private int matId;

	@DBField
	private double bagSize;

	@DBField
	private String imgUrl;

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

	public void setBagCodeNo(String bagCodeNo) {
		this.bagCodeNo = bagCodeNo;
	}

	public String getBagCodeNo() {
		return bagCodeNo;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public String getBagName() {
		return bagName;
	}

	public void setBagTypeId(int bagTypeId) {
		this.bagTypeId = bagTypeId;
	}

	public int getBagTypeId() {
		return bagTypeId;
	}

	public void setSpecId(int specId) {
		this.specId = specId;
	}

	public int getSpecId() {
		return specId;
	}

	public void setMatId(int matId) {
		this.matId = matId;
	}

	public int getMatId() {
		return matId;
	}

	public void setBagSize(double bagSize) {
		this.bagSize = bagSize;
	}

	public double getBagSize() {
		return bagSize;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgUrl() {
		return imgUrl;
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

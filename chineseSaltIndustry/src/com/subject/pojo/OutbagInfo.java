package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("outbag_info")
public class OutbagInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String outBagCodeNo;

	@DBField
	private String outBagName;

	@DBField
	private int outBagTypeId;

	@DBField
	private int outSpecId;

	@DBField
	private int outMatId;

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

	public void setOutBagCodeNo(String outBagCodeNo) {
		this.outBagCodeNo = outBagCodeNo;
	}

	public String getOutBagCodeNo() {
		return outBagCodeNo;
	}

	public void setOutBagName(String outBagName) {
		this.outBagName = outBagName;
	}

	public String getOutBagName() {
		return outBagName;
	}

	public void setOutBagTypeId(int outBagTypeId) {
		this.outBagTypeId = outBagTypeId;
	}

	public int getOutBagTypeId() {
		return outBagTypeId;
	}

	public void setOutSpecId(int outSpecId) {
		this.outSpecId = outSpecId;
	}

	public int getOutSpecId() {
		return outSpecId;
	}

	public void setOutMatId(int outMatId) {
		this.outMatId = outMatId;
	}

	public int getOutMatId() {
		return outMatId;
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

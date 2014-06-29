package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("salt_pack")
public class SaltPack implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private int saltId;

	@DBField
	private int packId;

	@DBField
	private String typeVal="0";

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

	public void setSaltId(int saltId) {
		this.saltId = saltId;
	}

	public int getSaltId() {
		return saltId;
	}

	public void setPackId(int packId) {
		this.packId = packId;
	}

	public int getPackId() {
		return packId;
	}

	public void setTypeVal(String typeVal) {
		this.typeVal = typeVal;
	}

	public String getTypeVal() {
		return typeVal;
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

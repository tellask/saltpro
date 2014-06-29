package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("system_menus")
public class SystemMenus implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String menusCode;

	@DBField
	private String menusName;

	@DBField
	private String menusUrl;

	@DBField
	private String menusImg;

	@DBField
	private int parentMenusId;

	@DBField
	private int comTypeId;

	@DBField
	private int rightsType=3;

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

	public void setMenusCode(String menusCode) {
		this.menusCode = menusCode;
	}

	public String getMenusCode() {
		return menusCode;
	}

	public void setMenusName(String menusName) {
		this.menusName = menusName;
	}

	public String getMenusName() {
		return menusName;
	}

	public void setMenusUrl(String menusUrl) {
		this.menusUrl = menusUrl;
	}

	public String getMenusUrl() {
		return menusUrl;
	}

	public void setMenusImg(String menusImg) {
		this.menusImg = menusImg;
	}

	public String getMenusImg() {
		return menusImg;
	}

	public void setParentMenusId(int parentMenusId) {
		this.parentMenusId = parentMenusId;
	}

	public int getParentMenusId() {
		return parentMenusId;
	}

	public void setComTypeId(int comTypeId) {
		this.comTypeId = comTypeId;
	}

	public int getComTypeId() {
		return comTypeId;
	}

	
	public int getRightsType() {
		return rightsType;
	}

	public void setRightsType(int rightsType) {
		this.rightsType = rightsType;
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

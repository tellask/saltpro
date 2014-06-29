package com.subject.pojo;

import java.io.Serializable;

import com.dbcom.annotation.*;

@DBTable("system_setup")
public class SystemSetup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String physicalPoint="2";

	@DBField
	private String moneyPoint="3";

	@DBField
	private String unitmoneyPoint="6";
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPhysicalPoint(String physicalPoint) {
		this.physicalPoint = physicalPoint;
	}

	public String getPhysicalPoint() {
		return physicalPoint;
	}

	public void setMoneyPoint(String moneyPoint) {
		this.moneyPoint = moneyPoint;
	}

	public String getMoneyPoint() {
		return moneyPoint;
	}

	public String getUnitmoneyPoint() {
		return unitmoneyPoint;
	}

	public void setUnitmoneyPoint(String unitmoneyPoint) {
		this.unitmoneyPoint = unitmoneyPoint;
	}
	
}

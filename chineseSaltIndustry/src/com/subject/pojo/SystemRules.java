package com.subject.pojo;

import java.io.Serializable;

import com.dbcom.annotation.*;

@DBTable("system_rules")
public class SystemRules implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private String tableName;

	@DBField
	private String numberRules;


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setNumberRules(String numberRules) {
		this.numberRules = numberRules;
	}

	public String getNumberRules() {
		return numberRules;
	}

}

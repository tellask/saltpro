package com.subject.pojo;

import java.io.Serializable;
import java.sql.*;

import com.dbcom.annotation.*;

@DBTable("current_salt")
public class CurrentSalt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DBField
	@PrimaryKey
	private int id;

	@DBField
	private Timestamp makeDateTime;

	@DBField
	private double putSaltStorage;

	@DBField
	private double outSaltStorage;

	@DBField
	private double balancSaltNum;

	@DBField
	private int saltId;

	@DBField
	private double putBagStorage;

	@DBField
	private double outBagStorage;

	@DBField
	private double balancBagNum;

	@DBField
	private double putOutStorage;

	@DBField
	private double outOutStorage;

	@DBField
	private double balancOutNum;

	@DBField
	private double putSecurStorage;

	@DBField
	private double outSecurStorage;

	@DBField
	private double balancSecurNum;

	@DBField
	private int sellComId;

	@DBField
	private int recvComId;

	@DBField
	private int stockId;

	@DBField
	private String currentType="1";

	@DBField
	private String sourceTable;

	@DBField
	private int sourceId;

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

	public void setMakeDateTime(Timestamp makeDateTime) {
		this.makeDateTime = makeDateTime;
	}

	public Timestamp getMakeDateTime() {
		return makeDateTime;
	}

	public void setPutSaltStorage(double putSaltStorage) {
		this.putSaltStorage = putSaltStorage;
	}

	public double getPutSaltStorage() {
		return putSaltStorage;
	}

	public void setOutSaltStorage(double outSaltStorage) {
		this.outSaltStorage = outSaltStorage;
	}

	public double getOutSaltStorage() {
		return outSaltStorage;
	}

	public void setBalancSaltNum(double balancSaltNum) {
		this.balancSaltNum = balancSaltNum;
	}

	public double getBalancSaltNum() {
		return balancSaltNum;
	}

	public void setSaltId(int saltId) {
		this.saltId = saltId;
	}

	public int getSaltId() {
		return saltId;
	}

	public void setPutBagStorage(double putBagStorage) {
		this.putBagStorage = putBagStorage;
	}

	public double getPutBagStorage() {
		return putBagStorage;
	}

	public void setOutBagStorage(double outBagStorage) {
		this.outBagStorage = outBagStorage;
	}

	public double getOutBagStorage() {
		return outBagStorage;
	}

	public void setBalancBagNum(double balancBagNum) {
		this.balancBagNum = balancBagNum;
	}

	public double getBalancBagNum() {
		return balancBagNum;
	}

	public void setPutOutStorage(double putOutStorage) {
		this.putOutStorage = putOutStorage;
	}

	public double getPutOutStorage() {
		return putOutStorage;
	}

	public void setOutOutStorage(double outOutStorage) {
		this.outOutStorage = outOutStorage;
	}

	public double getOutOutStorage() {
		return outOutStorage;
	}

	public void setBalancOutNum(double balancOutNum) {
		this.balancOutNum = balancOutNum;
	}

	public double getBalancOutNum() {
		return balancOutNum;
	}

	public void setPutSecurStorage(double putSecurStorage) {
		this.putSecurStorage = putSecurStorage;
	}

	public double getPutSecurStorage() {
		return putSecurStorage;
	}

	public void setOutSecurStorage(double outSecurStorage) {
		this.outSecurStorage = outSecurStorage;
	}

	public double getOutSecurStorage() {
		return outSecurStorage;
	}

	public void setBalancSecurNum(double balancSecurNum) {
		this.balancSecurNum = balancSecurNum;
	}

	public double getBalancSecurNum() {
		return balancSecurNum;
	}

	public void setSellComId(int sellComId) {
		this.sellComId = sellComId;
	}

	public int getSellComId() {
		return sellComId;
	}

	public void setRecvComId(int recvComId) {
		this.recvComId = recvComId;
	}

	public int getRecvComId() {
		return recvComId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public int getStockId() {
		return stockId;
	}

	public void setCurrentType(String currentType) {
		this.currentType = currentType;
	}

	public String getCurrentType() {
		return currentType;
	}

	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}

	public String getSourceTable() {
		return sourceTable;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getSourceId() {
		return sourceId;
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

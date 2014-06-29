package com.dbcom;

public class DBOperateResult implements java.io.Serializable {
	private boolean success;		// �����Ƿ�ɹ�
	private Exception exception;	// �쳣
	private long executeTimes;		// ��ɶ����ʱ������λ����
	private Object returnObject;	// ��ݿ����ķ��ض���
	
	private static final long serialVersionUID = -4711058195221240142L;
	
	public DBOperateResult(Exception exception, long executeTimes) {
		success = false;
		this.exception = exception;
		this.executeTimes = executeTimes;
		returnObject = null;
	}
	
	public DBOperateResult(Object returnObject, long executeTimes) {
		success = true;
		exception = null;
		this.executeTimes = executeTimes;
		this.returnObject = returnObject;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public Exception getException() {
		return exception;
	}
	
	public long getExecuteTimes() {
		return executeTimes;
	}
	
	public Object getReturnObject() {
		return this.returnObject;
	}
}

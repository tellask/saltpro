package com.dbcom;

import marst.util.staticlass.GeneralException;
/**
 * 数据库连接超时
 * @author taddy
 *
 */
public class DBWaitTimeoutException extends GeneralException {
	public DBWaitTimeoutException() {
		super();
	}
	
	public DBWaitTimeoutException(String message) {
		super(message);
	}
	
	public DBWaitTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DBWaitTimeoutException(Throwable cause) {
		super(cause);
	}
	
	private static final long serialVersionUID = -1623792281291010265L;
}

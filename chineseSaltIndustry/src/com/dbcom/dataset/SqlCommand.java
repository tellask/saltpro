package com.dbcom.dataset;

import java.io.Serializable;
import java.util.List;

/***
 * SQL对象
 * @author taddy
 *
 */
public class SqlCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String commandText;
	private List<Object> comandParameters;
	public String getCommandText() {
		return commandText;
	}
	public void setCommandText(String commandText) {
		this.commandText = commandText;
	}
	public List<Object> getComandParameters() {
		return comandParameters;
	}
	public void setComandParameters(List<Object> comandParameters) {
		this.comandParameters = comandParameters;
	}
	
	
}

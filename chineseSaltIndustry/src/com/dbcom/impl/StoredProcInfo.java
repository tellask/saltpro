package com.dbcom.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import marst.util.staticlass.GeneralException;




class StoredProcInfo {
	
	
//	public StoredProcInfo(boolean isFunction, ParamInfo[] paramsInfo, boolean hasCursorParam) {
//		this.isFunction = isFunction;
//		this.paramsInfo = paramsInfo;
//		this.hasCursorParam = hasCursorParam;
//		
//		if (paramsInfo != null && paramsInfo.length > 0) {
//			declareParamCount = paramsInfo.length;
//			if (isFunction) {
//				declareParamCount--;
//			}
//		}
//	}
	
	public StoredProcInfo(Connection conn, String storedProcName) throws SQLException {
		String catalog = conn.getCatalog();
		DatabaseMetaData dbmd = conn.getMetaData();
		String schema = dbmd.getUserName();
		/*
		catalog是你的存储过程的package，如果没有传null，schemaPattern是你的schema名称，不确定的话也是可以传null，procedureNamePattern是你存储过程的名称，这个不能没有了，columnNamePattern不清楚，但是可以为null,可以传"%"表示任意字符。
		但是关键是：所有这些参数，如果有的话，一律要大写，不管你定义的时候是什么样的。否则他取不出值来的，至少在oracle上是这样的，这个浪费了我N多时间。
		联想到一般数据库的表名和字段都是大小写不敏感的，在这里突然来一个大小写敏感，实在是不合理。*/
		//ResultSet mrs = dbmd.getProcedureColumns(null, userName.toUpperCase(), storedProcName.toUpperCase(), null);
		//String catalog = "";
		String procName = storedProcName.toUpperCase();
		int iPos = procName.indexOf('.');
		if (iPos > 0) {
			catalog = procName.substring(0, iPos);
			procName = procName.substring(iPos+1);
		}
		ResultSet mrs = dbmd.getProcedureColumns(catalog, schema, procName, null);
		ArrayList<ParamInfo> paramsInfoList = new ArrayList<ParamInfo>();
		boolean hasCursorParam = false;
		int paramIndex = 1;
		while (mrs.next()) {
			int mode = mrs.getInt(5);
			int sqlType = mrs.getInt(6);
			if (sqlType == Types.OTHER) {
				String dbTypeName = mrs.getString(7);
				sqlType = dbToSqlType(dbTypeName);
				if (sqlType == ParamInfo.TYPE_CURSOR) {
					mode = ParamInfo.OUT;
					hasCursorParam = true;
				}
			}
			ParamInfo pi = new ParamInfo(paramIndex++, mode, sqlType);
			paramsInfoList.add(pi);
		}
		mrs.close();
	
		int totalParamCount = paramsInfoList.size();	// param count, including return if it is a function
		paramsInfo = new ParamInfo[totalParamCount];
		for (int i = 0; i < totalParamCount; i++) {
			paramsInfo[i] = (ParamInfo)paramsInfoList.get(i);
		}
		
		boolean isFunction = false;
		int declareParamCount = totalParamCount;	// param count, excluding return if it is a function
		if (paramsInfo.length > 0 && paramsInfo[0].mode == ParamInfo.RETURN) {
			isFunction = true;
			declareParamCount--;
		}
		
		callSql = getStoredProcSql(storedProcName, isFunction, declareParamCount);
	}
	
	private int dbToSqlType(String dbTypeName) {
		if (dbTypeName.equalsIgnoreCase("FLOAT")) {
			return java.sql.Types.FLOAT;
		} else if (dbTypeName.equalsIgnoreCase("REF CURSOR")) {
			return ParamInfo.TYPE_CURSOR;
		}
		throw new GeneralException("unsupported dbTypeName: " + dbTypeName);
	}
	
	/**
	 * generate jdbc call string for executing storedproc.
	 * @param storedProcName	The name of proceure or function
	 * @param isFunction	True if the storedproc is a funciton, false or not. 
	 * @param declareParamCount The parameter count of the storedproc, including in/inout/out, but no return.
	 * @return Return a jdbc call statement for executing the storedproc. It has the folliwng styles:
	 * 		{call procedure_name}
	 * 		{call procedure_name(?, ?, ...)}
	 * 		{? = call procedure_name}
	 * 		{? = call procedure_name(?, ?, ...)}
	 */
	private String getStoredProcSql(String storedProcName, boolean isFunction, int declareParamCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (isFunction) {
			sb.append("? = ");
		}
		sb.append("call ");
		sb.append(storedProcName);
		if (declareParamCount > 0) {
			sb.append("(");
			for (int i = 0; i < declareParamCount - 1; i++) {
				sb.append("?,");
			}
			sb.append("?)");
		}
		sb.append("}");
		return sb.toString();
	}
	
	// in, inout
	List<ParamInfo> getInParamsInfo() {
		List<ParamInfo> list = new ArrayList<ParamInfo>();
		for (ParamInfo pi : paramsInfo) {
			if (pi.mode == ParamInfo.IN || pi.mode == ParamInfo.INOUT) {
				list.add(pi);
			}
		}
		
		return list;
	}
	
	// out/inout/return, but no cursor params
	List<ParamInfo> getOutParamsInfo() {
		List<ParamInfo> list = new ArrayList<ParamInfo>();
		for (ParamInfo pi : paramsInfo) {
			if (pi.mode == ParamInfo.OUT || pi.mode == ParamInfo.INOUT
					|| pi.mode == ParamInfo.RETURN) {
				if (pi.sqlType != ParamInfo.TYPE_CURSOR) {
					list.add(pi);
				}
			}
		}
		
		return list;
	}
	
	List<ParamInfo> getCursorParamsInfo() {
		List<ParamInfo> list = new ArrayList<ParamInfo>();
		for (ParamInfo pi : paramsInfo) {
			if (pi.sqlType == ParamInfo.TYPE_CURSOR) {
				list.add(pi);
			}
		}
		
		return list;
	}
	
	//boolean isFunction;	// true-是Function，false-是Procedure
	ParamInfo[] paramsInfo;	// 所有参数信息，包括return参数
	//boolean hasCursorParam;	// 是否有Cursor 参数
	//int declareParamCount = 0;	
	String callSql;			// JDBC 调用存储过程的语句
}

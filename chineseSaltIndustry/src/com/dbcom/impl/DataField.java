package com.dbcom.impl;

import java.lang.reflect.Method;

import marst.util.staticlass.DataType;

class DataField {
	int index;				// 数据字段的序号，从0开始
	String classFieldName;	// 类中的成员变量名
	String dbFieldName;		// 数据库表中的字段名
	Method setMethod;		// 类中的set赋值方法
	// set方法的参数类型, 见 ticd.java.component.util.DataType 中的定义
	int setArgType = DataType.DT_Unknown;
	Method getMethod;		// 类中的get方法
	// get方法的返回值类型,见 ticd.java.component.util.DataType 中的定义
	int getReturnType = DataType.DT_Unknown;
	boolean isPrimaryKey; // 是否为主键

	DataField(int index, String classFieldName, String dbFieldName,
			boolean isPrimaryKey, Method getMethod, Method setMethod) {
		this.index = index;
		this.classFieldName = classFieldName;
		this.dbFieldName = dbFieldName;
		this.isPrimaryKey = isPrimaryKey;
		this.setMethod = setMethod;
		this.getMethod = getMethod;
		
		if (setMethod != null) {
			String typeName = setMethod.getParameterTypes()[0].getName();
			setArgType = DataType.getDataType(typeName);
		}
		if (getMethod != null) {
			String typeName = getMethod.getReturnType().getName();
			getReturnType = DataType.getDataType(typeName);
		}
	}
/*
	//	参数类型定义
	static final int ARG_TYPE_BYTE = 1;
	static final int ARG_TYPE_SHORT = 2;
	static final int ARG_TYPE_INT = 3;
	static final int ARG_TYPE_LONG = 4;
	static final int ARG_TYPE_FLOAT = 5;
	static final int ARG_TYPE_DOUBLE = 6;
	static final int ARG_TYPE_CHAR = 7;
	static final int ARG_TYPE_STRING = 8;
	static final int ARG_TYPE_BOOLEAN = 9;
	static final int ARG_TYPE_DATE = 10;
					
	byte toIntType(Type argumentType) {
		String typeName = argumentType.toString();
		byte argType = -1;
		if (typeName.equals("byte")) {
			argType = ARG_TYPE_BYTE;
		} else if (typeName.equals("short")) {
			argType = ARG_TYPE_SHORT;
		} else if (typeName.equals("int")) {
			argType = ARG_TYPE_INT;
		} else if (typeName.equals("long")) {
			argType = ARG_TYPE_LONG;
		} else if (typeName.equals("float")) {
			argType = ARG_TYPE_FLOAT;
		} else if (typeName.equals("double")) {
			argType = ARG_TYPE_DOUBLE;
		} else if (typeName.equals("char")) {
			argType = ARG_TYPE_CHAR;
		} else if (typeName.equals("class java.lang.String")) {
			argType = ARG_TYPE_STRING;
		} else if (typeName.equals("boolean")) {
			argType = ARG_TYPE_BOOLEAN;
		} else if (typeName.equals("class java.util.Date")) {
			argType = ARG_TYPE_DATE;
		} else {
			throw new GeneralException("unknown argument type: " + typeName);
		}
		
		return argType;
	}
	
	Object getTypeValue(Object columnValue, int argType) {
		Object result = null;
		String s = columnValue.toString();
		switch (argType) {
		case ARG_TYPE_BYTE:
			result = Integer.valueOf(s).byteValue();
			break;
		case ARG_TYPE_SHORT:
			result = Integer.valueOf(s).shortValue();
			break;
		case ARG_TYPE_INT:
			result = Integer.valueOf(s).intValue();
			break;
		case ARG_TYPE_LONG:
			result = Integer.valueOf(s).longValue();
			break;
		case ARG_TYPE_FLOAT:
			result = Float.valueOf(s).floatValue();
			break;
		case ARG_TYPE_DOUBLE:
			result = Double.valueOf(s).doubleValue();
			break;
		case ARG_TYPE_CHAR:
			result = s.charAt(0);
			break;
		case ARG_TYPE_STRING:
			result = s;
			break;
		case ARG_TYPE_BOOLEAN:
			result = Boolean.valueOf(s).booleanValue();
			break;
		case ARG_TYPE_DATE:
			result = columnValue;
			break;
		}
		
		return result;
	}
*/
}

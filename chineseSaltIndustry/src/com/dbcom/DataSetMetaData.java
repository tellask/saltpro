package com.dbcom;

public interface DataSetMetaData {
	int getColumnCount();
	int getColumnDisplaySize(int column);
	String getColumnLabel(int column);
	String getColumnName(int column);
	int getPrecision(int column);
	int getScale(int column);
	// 返回 ticd.java.component.util.DataType 中定义的类型
	int getColumnType(int column);
	// 返回数据库中定义的数据类型名称
	String getColumnDbTypeName(int column);
}

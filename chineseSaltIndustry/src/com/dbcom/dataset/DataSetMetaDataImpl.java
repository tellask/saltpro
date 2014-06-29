package com.dbcom.dataset;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

import com.dbcom.*;
import marst.util.staticlass.DataType;
import marst.util.staticlass.GeneralException;

public class DataSetMetaDataImpl implements DataSetMetaData, Serializable, Cloneable {
	public DataSetMetaDataImpl(int dbType, ResultSet rs) {
		this.dbType = dbType;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
			columnInfos = new ArrayList<ColumnMetaInfo>(columnCount);
			for (int i = 0; i < columnCount; i++) {
				int column = i + 1;
				
				int displaySize = rsmd.getColumnDisplaySize(column);
				String label = rsmd.getColumnLabel(column);
				String name = rsmd.getColumnName(column);
				int precision = rsmd.getPrecision(column);
				int scale = rsmd.getScale(column);
				scale = scale < 0 ? 0 : scale;
				//int type = rsmd.getColumnType(column);
				String dbTypeName = rsmd.getColumnTypeName(column);
				String typeName = dbToJavaType(dbType, dbTypeName, displaySize, precision, scale);
				int type = DataType.getDataType(typeName);
				columnInfos.add(new ColumnMetaInfo(i, displaySize, label, 
							name, precision, scale, type, dbTypeName));
			}
		} catch (Exception e) {
			throw new GeneralException(e);
		}
		
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public int getColumnDisplaySize(int column) {
		return columnInfos.get(column).displaySize;
	}

	public String getColumnLabel(int column) {
		return columnInfos.get(column).label;
	}

	public String getColumnName(int column) {
		return columnInfos.get(column).name;
	}

	public int getPrecision(int column) {
		return columnInfos.get(column).precision;
	}

	public int getScale(int column) {
		return columnInfos.get(column).scale;
	}

	// ���� ticd.java.component.util.DataType �ж��������
	public int getColumnType(int column) {
		return columnInfos.get(column).type;
	}
	
	public String getColumnDbTypeName(int column) {
		return columnInfos.get(column).dbTypeName;
	}

	public static String dbToJavaType(int dbType, String dbDataType, int dataLen, int precision, int scale) {
		if (dbType == DBType.DB_MYSQL) {
			return mysqlToJavaType(dbDataType, dataLen, precision, scale);
		} else if (dbType == DBType.DB_ORACLE) {
			return oracleToJavaType(dbDataType, dataLen, precision, scale);
		} else {
			return null;
		}
	}
	
	private static String mysqlToJavaType(String mysqlType, int dataLen, int precision, int scale) {
		mysqlType = mysqlType.toUpperCase();
		if (mysqlType.equals("TINYINT")) {
			return "byte";
		} else if (mysqlType.equals("BOOL") || mysqlType.equals("BOOLEAN")) {
			return "boolean";
		} else if (mysqlType.equals("SMALLINT")) {
			return "short";
		} else if (mysqlType.equals("MEDIUMINT") || mysqlType.equals("INT")
				|| mysqlType.equals("INTEGER")) {
			return "int";
		} else if (mysqlType.equals("BIGINT") || mysqlType.equals("SERIAL") || mysqlType.equals("INT UNSIGNED")) {
			return "long";
		} else if (mysqlType.equals("FLOAT")) {
			return "float";
		} else if (mysqlType.equals("DOUBLE") || mysqlType.equals("DECIMAL")
				|| mysqlType.equals("REAL")) {
			return "double";
		} else if (mysqlType.equals("DATE")) {
			return "Date";
		} else if (mysqlType.equals("TIME")) {
			return "Time";
		} else if (mysqlType.equals("DATETIME") || mysqlType.equals("TIMESTAMP")) {
			
			return "Timestamp";
		} else if (mysqlType.equals("CHAR") || mysqlType.equals("VARCHAR")
				|| mysqlType.equals("TINYTEXT") || mysqlType.equals("TEXT")
				|| mysqlType.equals("MEDIUMTEXT") || mysqlType.equals("LONGTEXT")) {
			return "String";
		} else if (mysqlType.equals("BINARY") || mysqlType.equals("VARBINARY")
				|| mysqlType.equals("TINYBLOB") || mysqlType.equals("BLOB")
				|| mysqlType.equals("MEDIUMBLOB") || mysqlType.equals("LONGBLOB")) {
			return "byte[]";
		} else {
			return "unknown";
		}
	}
	
	private static String oracleToJavaType(String oraType, int dataLen, int precision, int scale) {
		oraType = oraType.toUpperCase();
		if (oraType.equals("INTEGER") || oraType.equals("INT")
			|| oraType.equals("PLS_INTEGER") || oraType.equals("BINARY_INTEGER")) {
			return "int";
		} else if (oraType.equals("SMALLINT")) {
			return "short";
		} else if (oraType.equals("NUMBER")) {
			if (scale > 0) {
				return "double";
			}
			return "int";
		} else if (oraType.equals("DEC") || oraType.equals("DECIMAL")
			|| oraType.equals("NUMERIC") || oraType.equals("DOUBLE")
			|| oraType.equals("PRECISION") || oraType.equals("FLOAT")
			|| oraType.equals("REAL")) {
			return "double";
		} else if (oraType.equals("STRING") || oraType.equals("VARCHAR")
			|| oraType.equals("VARCHAR2") || oraType.equals("NVARCHAR2")
			|| oraType.equals("CHAR") || oraType.equals("NCHAR")
			|| oraType.equals("CHARACTER")) {
			if (dataLen > 1) {
				return "String";
			}
			return "char";
		} else if (oraType.equals("DATE") || oraType.equals("TIMESTAMP")) {
			return "Timestamp";
		} else if (oraType.equals("RAW") || oraType.equals("LONG RAW")) {
			if (dataLen > 1) {
				return "byte[]";
			}
			return "byte";
		} else if (oraType.equals("LONG") || oraType.equals("CLOB")
				|| oraType.equals("NCLOB")) {
			return "String";
		} else if (oraType.equals("BLOB")) {
			return "byte[]";
		} else {
			return "unknown";
		}
	}
	///////////////////////////////////////////////////////
	private static final long serialVersionUID = 4015941603310054184L;
	private int dbType = DBType.DB_UNKNOWN;
	private int columnCount;
	private ArrayList<ColumnMetaInfo> columnInfos;
	
	private class ColumnMetaInfo {
		int index;
		int displaySize;
		String label;
		String name;
		int precision;
		int scale;
		int type;	// DataType.DT_XXX
		String dbTypeName;
		
		public ColumnMetaInfo(int index, int displaySize, String label, String name, 
				int precision, int scale, int type, String dbTypeName) {
			this.index = index;
			this.displaySize = displaySize;
			this.label = label;
			this.name = name;
			this.precision = precision;
			this.scale = scale;
			this.type = type;
			this.dbTypeName = dbTypeName;
		}
	}
}

package com.dbcom;


public class DBType {
	public static final int DB_UNKNOWN = 0;
	public static final int DB_ORACLE = 1;
	public static final int DB_MYSQL = 2;
/*	
	public static int dbToSqlType(int dbType, String dataTypeName, int dataLen, int precision, int scale) {
		if (dbType == DB_ORACLE) {
			return oracleToSqlType(dataTypeName, dataLen, precision, scale);
		} else if (dbType == DB_MYSQL) {
			return mysqlToSqlType(dataTypeName, dataLen, precision, scale);
		} else {
			throw new GeneralException("invalid dbType!");
		}
	}
	
	private static int oracleToSqlType(String oraType, int dataLen, int precision, int scale) {
		oraType = oraType.toUpperCase();
		if ((oraType.equals("BOOL") || oraType.equals("BOOLEAN")) {
			return Types.BOOLEAN;
		} else if (oraType.equals("SMALLINT")) {
			return Types.SMALLINT;
		} else if (oraType.equals("MEDIUMINT") || oraType.equals("INT")
				|| oraType.equals("INTEGER")) {
			return Types.INTEGER;
		} else if (oraType.equals("BIGINT") || oraType.equals("SERIAL")) {
			return Types.BIGINT;
		} else if (oraType.equals("FLOAT")) {
			return Types.FLOAT;
		} else if (oraType.equals("DOUBLE")) {
			return Types.DOUBLE;
		} else if (oraType.equals("REAL")) {
			return Types.REAL;
		} else if (oraType.equals("DECIMAL")) {
			return Types.DECIMAL;
		} else if (oraType.equals("DATE")) {
			return Types.DATE;
		} else if (oraType.equals("TIME")) {
			return Types.TIME;
		} else if (oraType.equals("DATETIME") || oraType.equals("TIMESTAMP")) {
			return Types.TIMESTAMP;
		} else if (oraType.equals("CHAR")) {
			return Types.CHAR;
		} else if (oraType.equals("VARCHAR")) {
			return Types.VARCHAR;
		} else if (oraType.equals("TINYTEXT") || oraType.equals("TEXT")
				|| oraType.equals("MEDIUMTEXT") || oraType.equals("LONGTEXT")) {
			return Types.CLOB;
		} else if (oraType.equals("BINARY")) {
			return Types.BINARY;
		} else if (oraType.equals("VARBINARY")) {
			return Types.VARBINARY;
		} else if (oraType.equals("TINYBLOB") || oraType.equals("BLOB")
				|| oraType.equals("MEDIUMBLOB") || oraType.equals("LONGBLOB")) {
			return Types.BLOB;
		} else {
			return Types.OTHER;
		}
	}
	
	private static int mysqlToSqlType(String mysqlType, int dataLen, int precision, int scale) {
		mysqlType = mysqlType.toUpperCase();
		if ((mysqlType.equals("BOOL") || mysqlType.equals("BOOLEAN")) {
			return Types.BOOLEAN;
		} else if (mysqlType.equals("TINYINT")) {
			return Types.TINYINT;
		} else if (mysqlType.equals("SMALLINT")) {
			return Types.SMALLINT;
		} else if (mysqlType.equals("MEDIUMINT") || mysqlType.equals("INT")
				|| mysqlType.equals("INTEGER")) {
			return Types.INTEGER;
		} else if (mysqlType.equals("BIGINT") || mysqlType.equals("SERIAL")) {
			return Types.BIGINT;
		} else if (mysqlType.equals("FLOAT")) {
			return Types.FLOAT;
		} else if (mysqlType.equals("DOUBLE")) {
			return Types.DOUBLE;
		} else if (mysqlType.equals("REAL")) {
			return Types.REAL;
		} else if (mysqlType.equals("DECIMAL")) {
			return Types.DECIMAL;
		} else if (mysqlType.equals("DATE")) {
			return Types.DATE;
		} else if (mysqlType.equals("TIME")) {
			return Types.TIME;
		} else if (mysqlType.equals("DATETIME") || mysqlType.equals("TIMESTAMP")) {
			return Types.TIMESTAMP;
		} else if (mysqlType.equals("CHAR")) {
			return Types.CHAR;
		} else if (mysqlType.equals("VARCHAR")) {
			return Types.VARCHAR;
		} else if (mysqlType.equals("TINYTEXT") || mysqlType.equals("TEXT")
				|| mysqlType.equals("MEDIUMTEXT") || mysqlType.equals("LONGTEXT")) {
			return Types.CLOB;
		} else if (mysqlType.equals("BINARY")) {
			return Types.BINARY;
		} else if (mysqlType.equals("VARBINARY")) {
			return Types.VARBINARY;
		} else if (mysqlType.equals("TINYBLOB") || mysqlType.equals("BLOB")
				|| mysqlType.equals("MEDIUMBLOB") || mysqlType.equals("LONGBLOB")) {
			return Types.BLOB;
		} else {
			return Types.OTHER;
		}
	}
*/
}

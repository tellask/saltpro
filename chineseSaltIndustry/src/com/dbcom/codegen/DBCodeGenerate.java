package com.dbcom.codegen;

import java.util.*;
import java.io.*;

import marst.util.staticlass.GeneralException;

import com.dbcom.DBAccess;
import com.dbcom.DBType;
import com.dbcom.DataSet;
import com.dbcom.dataset.DataSetMetaDataImpl;


/**
 * 方便生成实体类
 * @author taddy
 *
 */
public class DBCodeGenerate {

	public void setDBAccess(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	public DBAccess getDBAccess() {
		return dbAccess;
	}
	
	/**
	 * generate java class code according to a database table.
	 * @param srcDir srcDir can be null, "", or "." to mean the current directory. 
	 *		It can also be relative path or absolute path.
	 * @param packageName the package name of java class to be generated.
	 * 		It can be null or "" to mean default package. 
	 * @param tableName name of the table in db. table name can have schema, 
	 * 		such as "OtherDB.TableName", "OtherUser.TableName"
	 * @param javaClassName name of the java class to be generated.
	 * 		When it is null or "", the tableName is used for java class name.
	 */
	public boolean generateClass(String srcDir, String packageName,  String tableName, String javaClassName) {
		// 生成代码
		String code = generateCode(packageName, tableName, javaClassName);
		
		// 写入文件
		if (isNull(srcDir)) {
			srcDir = "";
		} else {
			srcDir = srcDir.replace('\\', '/');
			if (srcDir.charAt(srcDir.length()-1) != '/')
				srcDir += "/";
		}
		String destDir = srcDir + packageName.replace('.', '/');
		File f = new File(destDir);
		f.mkdirs();
		
		String clsDeclare = "public class ";
		int iPos = code.indexOf(clsDeclare) + clsDeclare.length();
		int iPos2 = code.indexOf(' ', iPos);
		String className = code.substring(iPos, iPos2);
		String fileName = destDir + "/" + className + ".java";
		try {
			// 转换为 UTF-8 格式
			byte[] bytes = code.getBytes("UTF-8");
			
			FileOutputStream writer = new FileOutputStream(fileName, false);
			writer.write(bytes);
			writer.flush();
			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public String generateCode(String packageName,  String tableName, String javaClassName) {
		if (!dbAccess.isConnected()) {
			dbAccess.connect();
		}
		
		dbType = dbAccess.getDBType();
		if (dbType != DBType.DB_MYSQL && dbType != DBType.DB_ORACLE) {
			throw new GeneralException("db is not supported!");
		}
		
		// 1. 解析Schema
		parseSchema(tableName);
		
		// 2. 查询表注释
		getTableComment();
		
		// 3. 查询列信息，并获取主键信息
		getColumnInfo();
		
		// 生成java class 代码
		return getJavaClassCode(packageName, javaClassName);
	}
	
	private void parseSchema(String tableName) {
		schemaName = null;
		pureTabName = tableName;
		int iPos = tableName.indexOf('.');
		if (iPos > 0) {
			schemaName = tableName.substring(0, iPos);
			pureTabName = tableName.substring(iPos+1);
		} else {
			if (dbType == DBType.DB_MYSQL) {
				schemaName = getMysqlDb(dbAccess.getDbUrl());
			} else if (dbType == DBType.DB_ORACLE) {
				schemaName = dbAccess.getUserName().toUpperCase();
			}
		}
		
		inputTabName = pureTabName;
		if (dbType == DBType.DB_ORACLE) {
			pureTabName = pureTabName.toUpperCase();
		}
	}
	
	private String getMysqlDb(String dbUrl) {
		int iPos = dbUrl.lastIndexOf('/');
		int iPos2 = dbUrl.indexOf('?', iPos+1);
		if (iPos2 > iPos) {
			return dbUrl.substring(iPos+1, iPos2);
		} else {
			return dbUrl.substring(iPos+1);
		}
	}
	
	private void getTableComment() {
		String sql = "";
		DataSet ds;
		if (dbType == DBType.DB_MYSQL) {
			sql = "select table_name, table_comment from information_schema.tables where table_schema = ? and table_name = ?";
		} else if (dbType == DBType.DB_ORACLE) {
			sql = "select TABLE_NAME, COMMENTS from all_tab_comments where owner=? and table_name=? and table_type='TABLE'";
		} else {
			return;
		}
		ds = dbAccess.executeQuery(sql, schemaName, pureTabName);
		if (ds.getRowCount() != 1) {	// 表不存在
			throw new GeneralException("table " + schemaName + "." + pureTabName + " does not exist!");
		}
		ds.next();
		tableComment = ds.getString(1);
	}
	
	private void getColumnInfo() {
		String sql = "";
		DataSet ds = null;
		primaryKeyCount = 0;
		primaryKeys = "";
		if (dbType == DBType.DB_MYSQL) {
			// MySQL，主键和列信息可以一次查询出来
			sql = "select column_name, data_type, character_octet_length, numeric_precision,"
				+ " numeric_scale, column_comment, column_key from information_schema.columns"
				+ " where table_schema = ? and table_name = ? order by ordinal_position";
			ds = dbAccess.executeQuery(sql, schemaName, pureTabName);
		} else if (dbType == DBType.DB_ORACLE) {
			// 查询主键
			sql = "select acc.column_name from all_constraints ac, all_cons_columns acc"
					+ " where ac.owner = acc.owner and ac.constraint_name = acc.constraint_name"
					+ " and ac.table_name = acc.table_name "
					+ " and ac.owner = ? and ac.table_name = ?"
					+ " and ac.constraint_type = 'P' order by position";
			ds = dbAccess.executeQuery(sql, schemaName, pureTabName);
			ds.beforeFirst();
			while (ds.next()) {
				primaryKeyCount++;
				if (primaryKeys.length() > 0) {
					primaryKeys += ",";
				}
				primaryKeys += toVariableName(ds.getString(0));
			}
			
			// 查询列信息
			sql = "select atc.COLUMN_NAME, atc.DATA_TYPE, atc.data_length, atc.data_precision,"
				+ " atc.data_scale, acc.COMMENTS from All_Tab_Cols atc, ALL_COL_COMMENTS acc"
				+ " where atc.owner = acc.owner and atc.table_name = acc.table_name "
				+ " and atc.column_name = acc.column_name"
				+ " and atc.owner=? and atc.table_name = ?" 
				+ " order by atc.column_id";
			ds = dbAccess.executeQuery(sql, schemaName, pureTabName);
		}
		
		// 解析表结构，分解出主键，列名，列的数据类型
		importSet.clear();
		importSet.add("import com.dbcom.annotation.*;\r\n");
		importSet.add("import java.io.Serializable;\r\n");
		fieldList.clear();
		
		int dbType = dbAccess.getDBType();
		ds.beforeFirst();
		while (ds.next()) {
			// 列名
			String colName = ds.getString(0);
			String memberName = toVariableName(colName);
			
			// 数据类型
			String dbTypeName = ds.getString(1);
			int dataLen = ds.getInt(2);
			int precision = ds.getInt(3);
			int scale = ds.getInt(4);
			String javaType = DataSetMetaDataImpl.dbToJavaType(dbType, dbTypeName, dataLen, precision, scale);
			if (javaType.equals("Date") || javaType.equals("Time")
					|| javaType.equals("Timestamp")) {
				importSet.add("import java.sql.*;\r\n");
			}
			
			// 主键
			if (dbType == DBType.DB_MYSQL) {
				String pri = ds.getString(6);
				pri = pri.toUpperCase();
				if (pri.equals("PRI")) {	// primary key
					primaryKeyCount++;
					if (primaryKeys.length() > 0) {
						primaryKeys += ", ";
					}
					primaryKeys += memberName;
				}
			}
			
			// 注释
			String comment = ds.getString(5);
			if (isNull(comment)) {
				comment = null;
			}
			
			FieldInfo field = new FieldInfo(colName, memberName, javaType, comment);
			fieldList.add(field);
		}
	}
	
	private String toVariableName(String colName) {
		int iPos = colName.indexOf('_');
		// 如果存在下划线，则将下划线去掉，第一个单词首字母小写，后续每个单词首字母大写
		if (iPos > 0) {	
			int colNameLen = colName.length();
			int underlineCount = 0;	// 下划线数量
			byte[] bytes = colName.toLowerCase().getBytes();
			while (iPos > 0) {
				int moveDataLen = colNameLen - iPos - 1;
				int byteOffset = iPos - underlineCount;
				System.arraycopy(bytes, byteOffset+1, bytes, byteOffset, moveDataLen);
				// 首字母大写
				// A-Z 65-90
				// a-z 97-122
				byte b = bytes[byteOffset];
				if (b >= 97 && b <= 122) {
					bytes[byteOffset] -= 32;
				}
				underlineCount++;
				iPos = colName.indexOf('_', iPos +1);
			}
			return new String(bytes, 0, colNameLen - underlineCount);
		} else {
			if (dbType == DBType.DB_ORACLE) {
				return colName.toLowerCase();
			} else {
				return colName.substring(0,1).toLowerCase() + colName.substring(1);
			}
		}
	}
	
	private String getJavaClassCode(String packageName, String javaClassName) {
		StringBuffer sb = new StringBuffer();
		// 包声明
		if (!isNull(packageName)) {
			sb.append("package ");
			sb.append(packageName);
			sb.append(";\r\n\r\n");
		}
		// import 部分
		if (importSet.size() > 0) {
			Iterator<String> it = importSet.iterator();
			while (it.hasNext()) {
				sb.append(it.next());
			}
			sb.append("\r\n");
		}
		
		// @DBTable
		if (isNull(javaClassName)) {
			sb.append("@DBTable\r\n");
			javaClassName = toIdentifer(inputTabName, true);
		} else if (inputTabName.equalsIgnoreCase(javaClassName)) {
			sb.append("@DBTable\r\n");
		} else {
			sb.append("@DBTable(\"");
			sb.append(inputTabName);
			sb.append("\")\r\n");
		}
		
		// @PrimaryKey
		if (primaryKeyCount > 1) {
			sb.append("@PrimaryKey(\"");
			sb.append(primaryKeys);
			sb.append("\")\r\n");
		}
		
		if (tableComment.length() > 0) {
			sb.append("// ");
			sb.append(tableComment);
			sb.append("\r\n");
		}
		// class
		sb.append("public class ");
		sb.append(javaClassName);
		sb.append(" implements Serializable");
		sb.append(" {\r\n\r\n");
		
		//加入序列化
		sb.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
		
		int fieldCount = fieldList.size();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < fieldCount; i++) {
			FieldInfo field = fieldList.get(i);
			String colName = field.colName;
			String memberName = field.memberName;
			String javaType = field.javaType;
			String comment = field.comment;
			
			if (memberName.equalsIgnoreCase(colName)) {
				sb.append("\t@DBField\r\n");
			} else {
				sb.append("\t@DBField(\"");
				sb.append(colName);
				sb.append("\")\r\n");
			}
			if (primaryKeyCount == 1 && memberName.equals(primaryKeys)) {
				sb.append("\t@PrimaryKey\r\n");
				primaryKeyCount = 0;	// 后续不再进行比较了
			}
			if (comment != null) {
				sb.append("\t// ");
				sb.append(comment);
				sb.append("\r\n");
			}
			sb.append("\tprivate ");
			sb.append(javaType);
			sb.append(' ');
			sb.append(memberName);
			sb.append(";\r\n\r\n");
			
			String identifier = toIdentifer(memberName, true);
			// set method
			builder.append("\tpublic void set");
			builder.append(identifier);
			builder.append("(");
			builder.append(javaType);
			builder.append(" ");
			builder.append(memberName);
			builder.append(") {\r\n");
			
			builder.append("\t\tthis.");
			builder.append(memberName);
			builder.append(" = ");
			builder.append(memberName);
			builder.append(";\r\n");
			
			builder.append("\t}\r\n\r\n");
			
			// get method
			builder.append("\tpublic ");
			builder.append(javaType);
			builder.append(" get");
			builder.append(identifier);
			builder.append("() {\r\n");
			
			builder.append("\t\treturn ");
			builder.append(memberName);
			builder.append(";\r\n");
			
			builder.append("\t}\r\n\r\n");	
		}

		sb.append("\r\n");
		sb.append(builder);
		sb.append("}\r\n");
		
		String code = sb.toString();
		return code;
	}
	
	private String toIdentifer(String strName, boolean initialUppercase) {
		/*if (dbAccess.getDBType() == DBType.DB_ORACLE) {
			strName = strName.toLowerCase();
		}*/
		if (initialUppercase) {
			return strName.substring(0,1).toUpperCase() + strName.substring(1);
		} else {
			return strName.substring(0,1).toLowerCase() + strName.substring(1);
			//return strName.toLowerCase();
		}
	}
	
	private boolean isNull(String str) {
		if (str == null || str.length() < 1) {
			return true;
		} 
		return false;
	}

	class FieldInfo {
		String colName;
		String memberName;
		String javaType;
		String comment;
		FieldInfo(String colName, String memberName, String javaType, String comment) {
			this.colName = colName;
			this.memberName = memberName;
			this.javaType = javaType;
			this.comment = comment;
		}
	}
	
	private DBAccess dbAccess;
	private int dbType = DBType.DB_UNKNOWN;
	private String schemaName;
	private String pureTabName;
	private String inputTabName;	// 传入的表名
	private String tableComment;
	private int primaryKeyCount = 0;
	private String primaryKeys;
	private HashSet<String> importSet = new HashSet<String>();
	private List<FieldInfo> fieldList = new LinkedList<FieldInfo>();
}

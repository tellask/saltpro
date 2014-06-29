package com.dbcom.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import com.dbcom.annotation.DBField;
import com.dbcom.annotation.DBTable;
import com.dbcom.annotation.PrimaryKey;

import marst.util.staticlass.ClassReflector;


class DataClassInfo {
	String tableName;		// 数据库表名
	DataField[] dataFields;	// 各个字段描述信息
	int[] primaryKeysIndex;	// 各个主键字段在dbFieldInfos中的数组下标
	
	String insertSql;		// insert sql
	
	String updateSql;		// update sql
	int[] updateFields;	// update sql中，各字段在dataFields中的位置下标
	
	String deleteSql;		// delete sql
	int[] deleteFields;	// delete sql中，各字段在dataFields中的位置下标
	
	DataClassInfo(Class classObj) {
		// 解析类的标注
		String[] primaryKeys = null;
		List<DataField> primaryKeyList = new ArrayList<DataField>();
		ClassReflector reflector = new ClassReflector(classObj);
		Annotation[] classAnnotions = reflector.getAnnotations();
		for(Annotation a : classAnnotions) {
			if (a instanceof DBTable) {
				tableName = ((DBTable)a).value();	// 数据库中表的名称
				if (tableName == null || tableName.length() == 0) {
					tableName = classObj.getSimpleName().toUpperCase();	// 数据库中表的字段名称
				}
			} else if (a instanceof PrimaryKey) {
				primaryKeys = ((PrimaryKey)a).value().split(",");	// 主键
			}
		}
		
		// 解析各个字段
		ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
		Field[] classFields = reflector.getFields();
		for (Field classField : classFields) {
			String classFieldName = classField.getName();
			boolean isValidField = false;
			boolean isPrimaryKey = false;
			String dbFieldName = null;
			Method setMethod = null;
			Method getMethod = null;
			
			Annotation[] annotions = classField.getAnnotations();
			for(Annotation a : annotions) {
				if (a instanceof PrimaryKey) {	// 有主键标注
					isPrimaryKey = true;
				} else if (a instanceof DBField) {
					// 1. 确定数据库中的字段名
					dbFieldName = ((DBField)a).value();	// 数据库中表的字段名称
					if (dbFieldName == null || dbFieldName.length() == 0) {
//						 数据库中表的字段名称
						dbFieldName = classFieldName.substring(0,1).toUpperCase() + classFieldName.substring(1);
					}
				
					// 2. 确定类中的get/set方法
					setMethod = reflector.findSetMethod(classFieldName);
					getMethod = reflector.findGetMethod(classFieldName);
					if (setMethod != null || getMethod != null) {
						isValidField = true;
					}
				}	
			}
			
			if (!isValidField) {
				continue;
			}
			
			// 判断是否在主键列表中
			if (!isPrimaryKey && primaryKeys != null) {	
				for (int i = 0; i < primaryKeys.length; i++) {
					if (primaryKeys[i].equalsIgnoreCase(classFieldName)) {
						isPrimaryKey = true;
						break;
					}
				}
			}
			
			int index = dataFieldList.size();
			DataField dataField = new DataField(index, classFieldName, 
					dbFieldName, isPrimaryKey, getMethod, setMethod);
			dataFieldList.add(dataField);
			
			if (isPrimaryKey) {
				primaryKeyList.add(dataField);
			}
		}
		
		// 将dataField 转换为数组
		int dataFieldCount = dataFieldList.size();
		dataFields = new DataField[dataFieldCount];
		for (int i = 0; i < dataFieldCount; i++) {
			dataFields[i] = dataFieldList.get(i);
		}
		
		// 主键位置
		int iCount = primaryKeyList.size();
		primaryKeysIndex = new int[iCount];
		for (int i = 0; i < iCount; i++) {
			primaryKeysIndex[i] = primaryKeyList.get(i).index;
		}
		
		// 构造insert sql
		getInsertSql();
		
		// 构造Update/Delete sql
		getUpdateDeleteSql();
	}

	private void getInsertSql() {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb.append("insert into ");
		sb.append(tableName);
		sb.append("(");
		sb2.append(") values (");
		int dataFieldCount = dataFields.length;
		for (int i = 0; i < dataFieldCount; i++) {
			sb.append(dataFields[i].dbFieldName);
			sb2.append("?");
			if (i != dataFieldCount - 1) {
				sb.append(",");
				sb2.append(",");
			} else {
				sb2.append(")");
				sb.append(sb2);
			}
		}
		insertSql = sb.toString();
	}
	
	private void getUpdateDeleteSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		StringBuffer whereClause = new StringBuffer();
		whereClause.append(" where ");
		int dataFieldCount = dataFields.length;
		updateFields = new int[dataFieldCount];
		int updateCount = 0;
		for (int i = 0; i < dataFieldCount; i++) {
			DataField dbFieldInfo = dataFields[i];
			String dbFieldName = dbFieldInfo.dbFieldName;
			if (dbFieldInfo.isPrimaryKey) {	// 主键放在where 条件中
				whereClause.append(dbFieldName);
				whereClause.append("=? and ");
			} else {
				sb.append(dbFieldName);
				sb.append("=?, ");
				updateFields[updateCount++] = i;
			}
		}
		sb.delete(sb.length()-2, sb.length());
		whereClause.delete(whereClause.length()-5, whereClause.length());
		sb.append(whereClause);
		updateSql = sb.toString();
		int primaryKeyCount = primaryKeysIndex.length;
		System.arraycopy(primaryKeysIndex, 0, updateFields, updateCount, primaryKeyCount);
		
		// 构造delete
		sb.setLength(0);
		sb.append("delete from ");
		sb.append(tableName);
		sb.append(whereClause);
		deleteSql = sb.toString();
		deleteFields = new int[primaryKeyCount];
		System.arraycopy(primaryKeysIndex, 0, deleteFields, 0, primaryKeyCount);
	}
}

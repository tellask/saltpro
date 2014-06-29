package com.dbcom.dataset;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.math.*;


import com.dbcom.*;

import marst.util.staticlass.DataType;
import marst.util.staticlass.GeneralException;


public class DataSetImpl implements DataSet, Serializable, Cloneable {
	public DataSetImpl(int dbType, ResultSet rs) {
		this(dbType, rs, 0, -1);
	}
	
	public DataSetImpl(int dbType, ResultSet rs, int startRow) {
		this(dbType, rs, startRow, -1);
	}
	
	public DataSetImpl(int dbType, ResultSet rs, int startRow, int rowCount) {
		try {
			this.dbType = dbType;
			dsmd = new DataSetMetaDataImpl(dbType, rs);
			columnCount = dsmd.getColumnCount();
			
			// 逐行取出数据
			if (rowCount == 0) {
				return;
			}
			int maxRowNum = (rowCount == -1) ? Integer.MAX_VALUE : startRow + rowCount;
			int curRowNum = -1;		// 当前行号
			rs.beforeFirst();
			while (rs.next()) {
				curRowNum++;
				if (curRowNum < startRow) {
					continue;
				}
				if (curRowNum >= maxRowNum) {
					break;
				}
				
				Object[] values = new Object[columnCount];
				for (int k = 0; k < columnCount; k++) {
					if (dbType == DBType.DB_ORACLE && dsmd.getColumnType(k) == java.sql.Types.DATE) {
						values[k] = rs.getTimestamp(k + 1);
					} else {
						values[k] = rs.getObject(k + 1);
					}
				}
				
				Row row = new Row(columnCount, values);
				rowList.add(row);
			}
		} catch (Exception e) {
			throw new GeneralException(e);
		}
	}
	
	public DataSetImpl(DataSetImpl ds) {
		this(ds, 0, -1);
	}
	
	public DataSetImpl(DataSetImpl ds, int startRow) {
		this(ds, startRow, -1);
	}
	
	public DataSetImpl(DataSetImpl ds, int startRow, int rowCount) {
		dbType = ds.dbType;
		dsmd = ds.dsmd;
		columnCount = ds.columnCount;
		
		if (rowCount == 0) {
			return;
		}
		int stopRow = startRow + rowCount;
		if (rowCount == -1 || stopRow > ds.rowList.size()) {
			stopRow = ds.rowList.size();
		}
		
		for (int i = startRow; i < stopRow; i++) {
			rowList.add(ds.rowList.get(i));
		}
	}
	
	// 结果集行数
	public int getRowCount() {
		return rowList.size();
	}
	
	public int getColumnCount() {
		return columnCount;
	}
	
	public DataSetMetaData getMetaData() {
		return dsmd;
	}
	
	public void beforeFirst() {
		currentRowNumber = -1;
		curRow = null;
	}
	
	// 移动到下一行
	public boolean next() {
		currentRowNumber++;
		boolean bRet = currentRowNumber < rowList.size();
		curRow = bRet ? rowList.get(currentRowNumber) : null;
		return bRet;
	}
	
	//	 设置当前行号
	public void setRow(int rowNumber) {
		if (rowNumber >= -1 && rowNumber < rowList.size()) {
			currentRowNumber = rowNumber;
			curRow = rowList.get(currentRowNumber);
		}
	}
	public int getRow() {
		return currentRowNumber;
	}

////////////////////////////////////////////////////////////////////////
   
	public boolean getBoolean(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Boolean value = (Boolean)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Boolean);
		return value == null ? false : value;
	}
	
	public byte getByte(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Byte value = (Byte)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Byte);
		return value == null ? 0 : value;
	}

	public short getShort(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Short value = (Short)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Short);
		return value == null ? 0 : value;
	}

	public int getInt(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Integer value = (Integer)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Integer);
		return value == null ? 0 : value;
	}

	public long getLong(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Long value = (Long)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Long);
		return value == null ? 0 : value;
	}

	public float getFloat(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Float value = (Float)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Float);
		return value == null ? 0 : value;
	}

	public double getDouble(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		Double value = (Double)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Double);
		return value == null ? 0 : value;
	}

	public String getString(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		if (obj == null) {
			return "";
		}
		return (String)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_String);
	}

	public byte[] getBytes(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
    	return (byte[])obj;
	}

	public java.sql.Date getDate(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		return (java.sql.Date)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Date);
	}

	public java.sql.Time getTime(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		return (java.sql.Time)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_Time);
	}

	public java.sql.Timestamp getDateTime(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		return (java.sql.Timestamp)DataType.toType(obj, valueDataTypes[columnIndex], DataType.DT_DateTime);
	}

	public Object getObject(int columnIndex) {
		Object obj = getColumnObject(columnIndex);
		return obj;
	}

////////////////////////////////////////////////////////////////////	
	public void setBoolean(int col, boolean value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setByte(int col, byte value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setShort(int col, short value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setInt(int col, int value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setLong(int col, long value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setFloat(int col, float value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setDouble(int col, double value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setString(int col, String value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setBytes(int col, byte[] value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setDate(int col, java.sql.Date value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setTime(int col, java.sql.Time value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setDateTime(int col, java.sql.Timestamp value) {
		curRow.setColumnObject(col, value);
	}
	
	public void setObject(int col, Object value) {
		curRow.setColumnObject(col, value);
	}

	
	public void insert(int row) {
		if (row >= 0 && row < rowList.size()) {
			curRow = new Row(columnCount);
			rowList.add(row, curRow);
			currentRowNumber = row;
		}
	}
	
	public void insert(int row, DataSet ds) {
		insert(row, ds, 0, -1);
	}
	
	public void insert(int row, DataSet ds, int startRow) {
		insert(row, ds, startRow, -1);
	}
	
	public void insert(int row, DataSet ds, int startRow, int maxRowCount) {
		int stopRow = startRow + maxRowCount;
		if (maxRowCount == 0) {
			return;
		} else if (maxRowCount == -1 || stopRow > ds.getRowCount()) {
			stopRow = ds.getRowCount();
		}
		
		DataSetImpl dsImpl = (DataSetImpl)ds;
		int insertIndex = row;
		for (int i = startRow; i < stopRow; i++) {
			rowList.add(insertIndex++, dsImpl.rowList.get(i));
		}
		
		if (currentRowNumber >= row) {
			currentRowNumber += insertIndex - row;
		}
	}
	
	
	public void append() {
		curRow = new Row(columnCount);
		rowList.add(curRow);
		currentRowNumber = rowList.size() - 1;
	}
	
	public void append(DataSet ds) {
		append(ds, 0, -1);
	}
	
	public void append(DataSet ds, int startRow) {
		append(ds, startRow, -1);
	}
	
	public void append(DataSet ds, int startRow, int maxRowCount) {
		int stopRow = startRow + maxRowCount;
		if (maxRowCount == 0) {
			return;
		} else if (maxRowCount == -1 || stopRow > ds.getRowCount()) {
			stopRow = ds.getRowCount();
		}
		
		DataSetImpl dsImpl = (DataSetImpl)ds;
		for (int i = startRow; i < stopRow; i++) {
			rowList.add(dsImpl.rowList.get(i));
		}
	}
	
	// delete current row
	public void delete() {
		deleteRows(currentRowNumber, 1);
	}
	public void delete(int row) {
		deleteRows(row, 1);
	}
	
	public void deleteRows(int startRow) {
		deleteRows(startRow, -1);
	}
	public void deleteRows(int startRow, int rowCount) {
		int allRowCount = rowList.size();
		if (startRow < 0 || startRow >= allRowCount) {
			return;
		}
		int stopRow = startRow + rowCount;
		if (rowCount == -1 || stopRow > allRowCount) {
			stopRow = allRowCount; 
		}
		for (int i = startRow; i < stopRow; i++) {
			rowList.remove(startRow);
		}
		
		if (currentRowNumber >= startRow) {
			if (currentRowNumber >= stopRow) {
				currentRowNumber -= stopRow - startRow;
			} else {
				currentRowNumber = startRow;
			}
			
			if (currentRowNumber >= rowList.size()) {
				curRow = null;
			}
		}
	}
	
	public void clear() {
		rowList.clear();
		currentRowNumber = -1;
		curRow = null;
	}
	
	public DataSet subset(int startRow) {
		return subset(startRow, -1);
	}
	
	public DataSet subset(int startRow, int rowCount) {
		return new DataSetImpl(this, startRow, rowCount);
	}
	

	


/////////////////////////////////////////////////////////////////////
	
	private synchronized Object getColumnObject(int columnIndex) {
		if (valueDataTypes == null) {
			valueDataTypes = new int[columnCount];
			for (int i = 0; i < columnCount; i++) {
				valueDataTypes[i] = DataType.DT_Unknown;
			}
		}
		
		Object obj = curRow.getColumnObject(columnIndex);
		int iSrcType = valueDataTypes[columnIndex];
		if (iSrcType == DataType.DT_Unknown) {
			iSrcType = DataType.getDataType(obj);
			valueDataTypes[columnIndex] = iSrcType;
		}
		obj = DataType.toType(obj, iSrcType, dsmd.getColumnType(columnIndex));
		return obj;
	}
	
	private long getIntValue(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof Byte) {
			return ((Byte)obj).longValue();
		} else if (obj instanceof Short) {
			return ((Short)obj).longValue();
		} else if (obj instanceof Integer) {
			return ((Integer)obj).longValue();
		} else if (obj instanceof Long) {
			return ((Long)obj).longValue();
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal)obj).longValue();
		} else if (obj instanceof BigInteger) {
			return ((BigInteger)obj).longValue();
		} else {
			throw new GeneralException("object is not a int type: " + obj.toString());
		} 
	}
	
	private double getDoubleValue(Object obj) {
		if (obj == null) {
			return 0;
		}
		
		if (obj instanceof Float) {
			return ((Float)obj).doubleValue();
		} else if (obj instanceof Double) {
			return ((Double)obj).doubleValue();
		} else if (obj instanceof Byte) {
			return ((Byte)obj).byteValue();
		} else if (obj instanceof Short) {
			return ((Short)obj).shortValue();
		} else if (obj instanceof Integer) {
			return ((Integer)obj).intValue();
		} else if (obj instanceof Long) {
			return ((Long)obj).longValue();
		} else {
			throw new GeneralException("object is not a double type: " + obj.toString());
		}
	}
	
	private static final long serialVersionUID = -2117317834676707787L;
	private int dbType = DBType.DB_UNKNOWN;
	private DataSetMetaData dsmd = null;
	private LinkedList<Row> rowList = new LinkedList<Row>();
	private Row curRow = null;
	private int columnCount;
	private int[] valueDataTypes = null;
	//private int rowCount;
	private int currentRowNumber = -1;
}

package com.dbcom;


public interface DataSet {
	    // 行数
		public int getRowCount();
		// 列数
		public int getColumnCount();
		
		// 结果集元数据
		public DataSetMetaData getMetaData();
		
		public void beforeFirst();
		public boolean next();
		
		// 设置当前行号
		public void setRow(int row);
		// 获取当前行号
		public int getRow();
		
		// 获取列数据
		public boolean getBoolean(int col);
		public byte getByte(int col);
		public short getShort(int col);
		public int getInt(int col);
		public long getLong(int col);
		public float getFloat(int col);
		public double getDouble(int col);
		public String getString(int col);
		public byte[] getBytes(int col);
		public java.sql.Date getDate(int col);
		public java.sql.Time getTime(int col);
		public java.sql.Timestamp getDateTime(int col);
		public Object getObject(int col);
		
		
		// 设置或更新数据
		public void setBoolean(int col, boolean value);
		public void setByte(int col, byte value);
		public void setShort(int col, short value);
		public void setInt(int col, int value);
		public void setLong(int col, long value);
		public void setFloat(int col, float value);
		public void setDouble(int col, double value);
		public void setString(int col, String value);
		public void setBytes(int col, byte[] value);
		public void setDate(int col, java.sql.Date value);
		public void setTime(int col, java.sql.Time value);
		public void setDateTime(int col, java.sql.Timestamp value);
		public void setObject(int col, Object value);
		
		public void insert(int row);
		
		public void append();
		
		public void delete();	// 删除当前行
		public void delete(int row);
		public void deleteRows(int startRow);
		public void deleteRows(int startRow, int rowCount);
		public void clear();	// 清除所有行
		
		// 数据集分割
		public DataSet subset(int startRow);
		public DataSet subset(int startRow, int rowCount);
		// 数据集合并
		public void append(DataSet ds);
		public void append(DataSet ds, int startRow);
		public void append(DataSet ds, int startRow, int rowCount);
		public void insert(int row, DataSet ds);
		public void insert(int row, DataSet ds, int startRow);
		public void insert(int row, DataSet ds, int startRow, int rowCount);
		


		
		// 排序
		//public void sort();
		
		// 过滤
		//public DataSet filter();
	
}

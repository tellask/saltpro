package com.dbcom.impl;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import com.dbcom.*;
import com.dbcom.dataset.*;

import marst.util.*;
import marst.util.staticlass.DataType;
import marst.util.staticlass.GeneralException;

public class DBAccessImpl implements DBAccess, Runnable {
	public DBAccessImpl() {
	}
	
	/**
	 * Establish multiple connections with database.
	 * java.net.ConnectException
	 */
	public void connect() {
		// 1. 建立数据库连接
		try {
			Class.forName(driverName);
			connections = new Connection[connectCount];
			status = new int[connectCount];
			transactions = new int[connectCount];
			for (int i = 0; i < connectCount; i++) {
				connections[i] = DriverManager.getConnection(dbUrl, userName, password);
				currentConnectCount++;
				status[i] = 0;
				transactions[i] = INVALID_TRANS_ID;
			}
			
			// 2. 对存储过程进行预解析
			if (preparedProcNames != null && storedProcInfoMap.size() < 1) {
				try {
					prepareStoredProcs(preparedProcNames);
				} catch (Exception e) {}
			}
			
			connected = true;
			
			if (dbMonitor != null) {
				dbMonitor.onConnected();
			}
		} catch (Exception e) {
			// 3. 启动重连线程
			startReconnectThread();
			throw new GeneralException(e);
		}
	}

	// db is connected?
	public boolean isConnected() {
		return connected;
	}
	
	// which kind of db ?
	public int getDBType() {
		return dbType;
	}
	
	/**
	 * Get JDBC Driver version. connect method must be called first.
	 * @return jdbc driver version
	 */
	public String getDriverVersion() {
		checkConnect();
		
		try {
			int index = getConnection();
			String driverVersion = connections[index].getMetaData().getDriverVersion();
			freeConnection(index);
			return driverVersion;
		} catch (SQLException e) {
			throw new GeneralException(e);
		}	
	}
	
	/**
	 * Disconnect all connections from database.
	 */
	public void disconnect() {
		connected = false;
		// 停止重连线程
		stopReconnectThread();
		
		// 关闭与数据库的连接
		for (int i = 0; i < currentConnectCount; i++) {
			try {
				connections[i].close();
			} catch (SQLException e) {
				//throw new GeneralException(e);
			}
		}
		currentConnectCount = 0;
		connections = null;
		
		storedProcInfoMap.clear();
		
		if (dbMonitor != null) {
			dbMonitor.onDisconnected();
		}
	}
	
	/**
	 * begin a transaction
	 * @return transaction Id
	 */
	public int beginTransaction() {
		if (dbMonitor != null) {
			return (Integer)dbOperate("beginTransaction");
		} else {
			return _beginTransaction();
		}
	}
	private int _beginTransaction() {
		checkConnect();
		int connectionIndex = getConnection();
		try {
			connections[connectionIndex].setAutoCommit(false);
		} catch (Exception e) {
			handleException(e);
		}
		
		int transactionId = INVALID_TRANS_ID;
		synchronized(this) {
			transactionId = nextTransactionId++;
		}
		
		transactions[connectionIndex] = transactionId;
		return transactionId;
	}
	
	/**
	 * commit a transaction
	 * @param transactionId the id of transaction. It is returned by beginTransaction().
	 */
	public void commit(int transactionId) {
		if (dbMonitor != null) {
			dbOperate("commit", transactionId);
		} else {
			_commit(transactionId);
		}
	}
	private void _commit(int transactionId) {
		trans(transactionId, true);
	}
	
	/**
	 * rollback a transaction
	 * @param transactionId the id of transaction. It is returned by beginTransaction().
	 */
	public void rollback(int transactionId) {
		if (dbMonitor != null) {
			dbOperate("rollback", transactionId);
		} else {
			_rollback(transactionId);
		}
	}
	private void _rollback(int transactionId) {
		trans(transactionId, false);
	}
	
	private void trans(int transactionId, boolean bCommit) {
		checkConnect();
		if (transactionId == INVALID_TRANS_ID) {
			throw new GeneralException("transactionId " + transactionId + " is invalid!");
		}
		
		int connectionIndex = -1;
		for (int i = 0; i < currentConnectCount; i++) {
			if (transactions[i] == transactionId) {
				connectionIndex = i;
				break;
			}
		}
		if (connectionIndex == -1) {
			throw new GeneralException("transactionId " + transactionId + " is invalid!");
		}
		
		try {
			try {
				if (bCommit) {
					connections[connectionIndex].commit();
				} else {
					connections[connectionIndex].rollback();
				}
			} finally {
				try {
					connections[connectionIndex].setAutoCommit(true);
				} catch (Exception e) {}
				transactions[connectionIndex] = INVALID_TRANS_ID;
				freeConnection(connectionIndex);
			}
		} catch (Exception e) {
			handleException(e);
		}
	}
	
	/**
	 * Execute sql with parameter. select sql is invalid here.
	 * @param sql The sql to execute. Usually, it is insert, update, delete or ddl.
	 * @param parameters input parameter values. They are provided in the same order with parameters in the sql.
	 */
	public int executeSql(String sql, Object... parameters) {
		if (dbMonitor != null) {
			return (Integer)dbOperate("executeSql", sql, parameters);
		} else {
			return _executeSql(sql, parameters);
		}
	}
	private int _executeSql(String sql, Object... parameters) {
		return _executeSql(INVALID_TRANS_ID, sql, parameters);
	}
	
	public int executeSql(int transactionId, String sql, Object... parameters) {
		if (dbMonitor != null) {
			return (Integer)dbOperate("executeSql", transactionId, sql, parameters);
		} else {
			return _executeSql(transactionId, sql, parameters);
		}
	}
	private int _executeSql(int transactionId, String sql, Object... parameters) {
		// 1. 检查数据库连接是否正常
		checkConnect();
		
		// 2. 获取一个数据库连接
		int connectionIndex = getConnection(transactionId);
		Statement statement = null;
		try {
			Connection conn = connections[connectionIndex];
			if (parameters == null || parameters.length < 1) {
				statement = conn.createStatement();
				statement.executeUpdate(sql);
			} else {
				// 3. 解析Sql 语句，设置各个参数值
				PreparedStatement ps = getStatement(conn, sql, parameters);
				statement = ps;
				
				// 4. 执行Sql
				ps.executeUpdate();
			}
			return statement.getUpdateCount();
		} catch (Exception e) {
			throw handleException(e);	// 5. 处理异常
		} finally {
			closeStatement(statement);	// 6. 关闭Statement
			// 7. 释放数据库连接
			freeConnection(transactionId, connectionIndex);
		}
	}
	
	public int[] executeBatch(String... sqls) {
		if (dbMonitor != null) {
			return (int[])dbOperate("executeBatch", (Object[])sqls);
		} else {
			return _executeBatch(sqls);
		}
	}
	private int[] _executeBatch(String... sqls) {
		return _executeBatch(INVALID_TRANS_ID, sqls);
	}
	
	public int[] executeBatch(int transactionId, String... sqls) {
		if (dbMonitor != null) {
			return (int[])dbOperate("executeBatch", transactionId, sqls);
		} else {
			return _executeBatch(transactionId, sqls);
		}
	}
	private int[] _executeBatch(int transactionId, String... sqls) {
		checkConnect();
		
		int connectionIndex = getConnection(transactionId);
		Statement statement = null;
		try {
			Connection conn = connections[connectionIndex];
			statement = conn.createStatement();
			for (String s : sqls) {
				statement.addBatch(s);
			}
			return statement.executeBatch();
		} catch (Exception e) {
			throw handleException(e);
		} finally {
			closeStatement(statement);
			freeConnection(transactionId, connectionIndex);
		}
	}
	/**
	 * Execute select sql with parameters
	 * @param sql select sql to be executed.
	 * @param parameters input parameter values. They are provided in the same order with parameters in the sql.
	 * @return Return a DataSet. DataSet is a offline ResultSet.
	 */
	public DataSet executeQuery(String sql, Object... parameters) {
		if (dbMonitor != null) {
			return (DataSet)dbOperate("executeQuery", sql, parameters);
		} else {
			return _executeQuery(sql, parameters);
		}
	}
	private DataSet _executeQuery(String sql, Object... parameters) {
		return _executeQuery(0, -1, sql, parameters);
	}
	
	public DataSet executeQuery(int startRowNum, int rowCount, String sql, Object... parameters) {
		if (dbMonitor != null) {
			return (DataSet)dbOperate("executeQuery", startRowNum, rowCount, sql, parameters);
		} else {
			return _executeQuery(startRowNum, rowCount, sql, parameters);
		}
	}
	private DataSet _executeQuery(int startRowNum, int rowCount, String sql, Object... parameters) {
		// 1. 检查数据库连接是否正常
		checkConnect();
		
		// 2. 获取一个空闲的数据库连接
		int connectionIndex = getConnection();
		Statement statement = null;
		ResultSet rs = null;
		try {
			Connection conn = connections[connectionIndex];
			if (parameters == null || parameters.length < 1) {
				statement = conn.createStatement();
				rs = statement.executeQuery(sql);
			} else {
				// 3. 解析sql 语句，设置参数的值
				PreparedStatement ps = getStatement(conn, sql, parameters);
				statement = ps;
				// 4. 执行sql
				rs = ps.executeQuery();
			}
			
			// 5. 填充离线数据集
			DataSet DataSet = getDataSet(rs, startRowNum, rowCount);

			return DataSet;
		} catch (Exception e) {
			throw handleException(e);	// 6. 处理异常
		} finally {
			// 7. 关闭ResultSet和Statement
			closeResultSet(rs);
			closeStatement(statement);
			// 8. 释放数据库连接
			freeConnection(connectionIndex);
		}
	}
	
	private void prepareStoredProcs(String storedProcNames) {
		String[] procNames = storedProcNames.split(",");
		int storedProcCount = procNames.length;
		int threadCount = storedProcCount > currentConnectCount ? currentConnectCount : storedProcCount;
		PrepareThread[] threads = new PrepareThread[threadCount];
		Semaphore semaphore = new Semaphore(0, threadCount);
		for (int i = 0; i < storedProcCount; i++) {
			int index = i % threadCount;
			if (threads[index] == null) {
				threads[index] = new PrepareThread(connections[index], storedProcInfoMap, semaphore);
			}
			threads[index].addPrepareStoredProc(procNames[i]);
		}
		for (int i = 0; i < threadCount; i++) {
			new Thread(threads[i]).start();
		}

		ThreadUtil.syncWait(semaphore);
	}
	/**
	 * Execute storedproc. It supports both procedure and function. 
	 * @param storedProcName the name of procedure or function to be executed.
	 * @param inParameters in/inout parameter values. They are provided in the same order with in/inout parameters of the storedproc.
	 * @return return an object array. the objects are in order:
	 *    1) return value if the storedproc is a function
	 *    2) out/inout parameter output value. They are in the same order with the out/inout parameters of the storedproc.
	 *    3) zero or more DataSet
	 */
	public Object[] executeStoredProc(String storedProcName, Object... inParameters) {
		if (dbMonitor != null) {
			return (Object[])dbOperate("executeStoredProc", storedProcName, inParameters);
		} else {
			return _executeStoredProc(storedProcName, inParameters);
		}
	}
	private Object[] _executeStoredProc(String storedProcName, Object... inParameters) {
		return _executeStoredProc(INVALID_TRANS_ID, storedProcName, inParameters);
	}
	
	public Object[] executeStoredProc(int transactionId, String storedProcName, Object... inParameters) {
		if (dbMonitor != null) {
			return (Object[])dbOperate("executeStoredProc", transactionId, storedProcName, inParameters);
		} else {
			return _executeStoredProc(transactionId, storedProcName, inParameters);
		}
	}
	private Object[] _executeStoredProc(int transactionId, String storedProcName, Object... inParameters) {
		// 1. 检查是否与数据库建立连接
		checkConnect();
		
		// 2. 获取一个数据库连接
		int connectionIndex = getConnection(transactionId);
		CallableStatement statement = null;
		try {
			Connection conn = connections[connectionIndex];
			
			// 3. 获取存储过程参数信息
			StoredProcInfo procInfo = getStoredProcInfo(conn, storedProcName);
			ParamInfo[] paramsInfo = procInfo.paramsInfo;
			
			// 4. 创建CallableStatement
			statement = conn.prepareCall(procInfo.callSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			// 5. 设置输入参数值，注册输出参数
			int vIndex = 0;
			for (ParamInfo pi : paramsInfo) {
				switch (pi.mode) {
					case ParamInfo.IN:
					case ParamInfo.INOUT:
						setParamValue(statement, pi.index, pi.sqlType, inParameters[vIndex++]);
						if (pi.mode == ParamInfo.IN)
							break;
					case ParamInfo.OUT:
					case ParamInfo.RETURN:
						statement.registerOutParameter(pi.index, pi.sqlType);
				}
			}
			
			// 6. 执行存储过程
			statement.execute();
			
			// 7.获取结果集和输出参数
			// 7.1 先取结果集
			List<DataSet> dataSetList = new ArrayList<DataSet>();
			ResultSet rs = statement.getResultSet();
			if (rs != null) {
				dataSetList.add(getDataSet(rs));
				while (statement.getMoreResults()) {
					rs = statement.getResultSet();
					dataSetList.add(getDataSet(rs));
				}
			}
			
			// 7.2 再看看是否有Oracle Cursor 返回结果集
			List<ParamInfo> cursorParamsInfo = procInfo.getCursorParamsInfo();
			for (ParamInfo pi : cursorParamsInfo) {
				ResultSet rts = (ResultSet)statement.getObject(pi.index);
				dataSetList.add(getDataSet(rts));
			}
			
			// 7.3 取UpdateCount,这样才可以取return, out, inout 参数值
			statement.getUpdateCount();
			
			// 7.4 取 return, out, inout 参数值,  并且把这些值放在返回数组的前面
			// 放在前面的原因是:结果集的个数不定,但输出参数的个数固定.
			List<Object> resultList = new LinkedList<Object>();
			List<ParamInfo> outParamsInfo = procInfo.getOutParamsInfo();
			for (ParamInfo pi : outParamsInfo) {
				Object outVal = getOutParamValue(statement, pi.index, pi.sqlType);
				resultList.add(outVal);
			}
			
			// 7.5 把结果集写在返回数组的后面
			resultList.add(dataSetList);
			
			// 8. 返回输出参数和结果集构成的对象数组
			return resultList.toArray();
		} catch (Exception e) {
			throw handleException(e);
		} finally {
			closeStatement(statement);
			freeConnection(transactionId, connectionIndex);
		}
	}

	/**
	 * Convert DataSet records to specified class instance array. The method is usually used together 
	 * with querySql or executeStoredProc.
	 * @param DataSet The DataSet to be converted
	 * @param dataClass the target class to be created. The class member variables are annotated by @DBField.
	 *        convert rule: the value of @DBField is the same with the column name in DataSet.
	 * @return return an array of the specified class instances.
	 */
	public <T> List<T> toObjects(DataSet ds, Class<T> dataClass) {
		try {
			// 1. 解析数据类的标注
			DataClassInfo classInfo = getClassInfo(dataClass);
			
			// 2. 设置数据字段的匹配标志
			DataField[] dataFields = classInfo.dataFields;
			int dataFieldCount = dataFields.length;
			int[] dataFieldFlags = new int[dataFieldCount];
			for (int i = 0; i < dataFieldCount; i++) {
				dataFieldFlags[i] = 0;	// 还未成功匹配到列
			}
			
			// 3. 遍历数据集的各列，寻找匹配的数据字段
			DataSetMetaData dsmd = ds.getMetaData();
			int columnCount = dsmd.getColumnCount();
			int[] fieldsIndex = new int[columnCount];	// 存放列对应的数据字段下标
			for (int i = 0; i < columnCount; i++) {
				fieldsIndex[i] = -1;					// 未匹配到数据字段
				String columnName = dsmd.getColumnName(i);	// 列名
				for (int k = 0; k < dataFieldCount; k++) {
					if (dataFieldFlags[k] == 0 && dataFields[k].dbFieldName.equalsIgnoreCase(columnName)) {
						fieldsIndex[i] = k;		// 记录下数据字段的位置下标
						dataFieldFlags[k] = 1;	// 成功匹配到列,下次比较将跳过此字段
						break;
					}
				}
			}
			
			// 4. 对DataSet 每行，构造相应的对象
			LinkedList<T> resultList = new LinkedList<T>();
			ds.beforeFirst();
			while (ds.next()) {
				// 4.1 创建对象
				Object obj = dataClass.newInstance();
				resultList.add((T)obj);
				
				// 设置各字段的值
				for (int i = 0; i < columnCount; i++) {
					int index = fieldsIndex[i];
					if (index >= 0) {	// 此列有对应的数据成员
						Object value = ds.getObject(i);
						if (value != null) {
							DataField dataField = dataFields[index];	// 此列对应的数据字段
							// 将列值，转换为与数据字段匹配的类型
							Object argValue = DataType.toType(value, 
									dsmd.getColumnType(i), dataField.setArgType);
							// 设置数据字段的值
							dataField.setMethod.invoke(obj, argValue);
						}
					}
				}
			}
			
			// 5. 返回对象集合
			return resultList;
		} catch (Exception e) {
			throw new GeneralException(e);
		}
	}
	public void insertObject(Object obj) {
		if (dbMonitor != null) {
			dbOperate("insertObject", obj);
		} else {
			_insertObject(obj);
		}
	}
	private void _insertObject(Object obj) {
		_insertObject(INVALID_TRANS_ID, obj);
	}
	
	public void insertObject(int transactionId, Object obj) {
		if (dbMonitor != null) {
			dbOperate("insertObject", transactionId, obj);
		} else {
			_insertObject(transactionId, obj);
		}
	}
	private void _insertObject(int transactionId, Object obj) {
		DataClassInfo classInfo = getClassInfo(obj.getClass());
		DataField[] dataFields = classInfo.dataFields;
		int dbFieldCount = dataFields.length;
		Object[] params = new Object[dbFieldCount];
		try {
			for (int i = 0; i < dbFieldCount; i++) {
				params[i] = dataFields[i].getMethod.invoke(obj, (Object[])null);
			}
		} catch (Exception e) {
			throw new GeneralException(e);
		}
		
		_executeSql(transactionId, classInfo.insertSql, params);
	}
	
	public void updateObject(Object obj) {
		if (dbMonitor != null) {
			dbOperate("updateObject", obj);
		} else {
			_updateObject(obj);
		}
	}
	private void _updateObject(Object obj) {
		_updateObject(INVALID_TRANS_ID, obj);
	}
	
	public void updateObject(int transactionId, Object obj) {
		if (dbMonitor != null) {
			dbOperate("updateObject", transactionId, obj);
		} else {
			_updateObject(transactionId, obj);
		}
	}
	private void _updateObject(int transactionId, Object obj) {
		DataClassInfo classInfo = getClassInfo(obj.getClass());
		DataField[] dataFields = classInfo.dataFields;
		int[] updateFields = classInfo.updateFields;
		int paramCount = updateFields.length;
		Object[] params = new Object[paramCount];
		try {
			for (int i = 0; i < paramCount; i++) {
				int index = updateFields[i];
				params[i] = dataFields[index].getMethod.invoke(obj, (Object[])null);
			}
		} catch (Exception e) {
			throw new GeneralException(e);
		}
		
		_executeSql(transactionId, classInfo.updateSql, params);
	}
	
	public void deleteObject(Object obj) {
		if (dbMonitor != null) {
			dbOperate("deleteObject", obj);
		} else {
			_deleteObject(obj);
		}
	}
	private void _deleteObject(Object obj) {
		_deleteObject(INVALID_TRANS_ID, obj);
	}
	
	public void deleteObject(int transactionId, Object obj) {
		if (dbMonitor != null) {
			dbOperate("deleteObject", transactionId, obj);
		} else {
			_deleteObject(transactionId, obj);
		}
	}
	private void _deleteObject(int transactionId, Object obj) {
		DataClassInfo classInfo = getClassInfo(obj.getClass());
		DataField[] dataFields = classInfo.dataFields;
		int[] deleteFields = classInfo.deleteFields;
		int paramCount = deleteFields.length;
		Object[] params = new Object[paramCount];
		try {
			for (int i = 0; i < paramCount; i++) {
				int index = deleteFields[i];
				params[i] = dataFields[index].getMethod.invoke(obj, (Object[])null);
			}
		} catch (Exception e) {
			throw new GeneralException(e);
		}
		
		_executeSql(transactionId, classInfo.deleteSql, params);
	}
	
	// *************  the followings are private part   **********************************************************************
	private void checkConnect() {
		if (!connected) {
			throw new GeneralException("Database is not connected!");
		}
	}
	/**
	 * To obtain a free database connection. If all connections are occupied, it will wait for maxTimeout milliseconds.
	 * If still no connection is available, a GeneralException will be thrown.
	 * @return return database connection index in connection array.
	 */
	private int getConnection() {
		for (int k = 0; k < 2; k++) {
			synchronized(status) {
				for (int i = 0; i < currentConnectCount; i++) {
					if (status[i] == 0) {
						status[i] = 1;
						return i;
					}
				}
				
				if (k == 1)
					break;
				
				ThreadUtil.waitObject(status, waitTimeout);
			}
		}
			
		throw new DBWaitTimeoutException("No connection is available!");
	}
	
	private int getConnection(int transactionId) {
		if (transactionId == INVALID_TRANS_ID) {
			return getConnection();	// 获取一个空闲的数据库连接
		}
		
		// 查找 transactionId 对应的连接
		for (int i = 0; i < currentConnectCount; i++) {
			if (transactions[i] == transactionId) {
				return i;
			}
		}
		
		// 未找到 transactionId 对应的连接
		throw new GeneralException("transactionId " + transactionId + " is invalid!");
	}
	
	/**
	 * Set database connection to free state. A connection of free state can be used again.
	 * @param connectionIndex the index of database connection to free.
	 */
	private void freeConnection(int connectionIndex) {
		if (connectionIndex >= 0 && connectionIndex < currentConnectCount) {
			synchronized(status) {
				status[connectionIndex] = 0;
				status.notifyAll();
			}
		}
	}
	
	private void freeConnection(int transactionId, int connectionIndex) {
		// 非事务的时候才真正释放数据库连接
		if (transactionId == INVALID_TRANS_ID) {
			freeConnection(connectionIndex);
		}
	}
	
	/**
	 * Safely close statement.
	 * @param statement
	 */
	private void closeStatement(Statement statement) {
		try {
			if (statement != null)
				statement.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Safely close ResultSet
	 * @param rs
	 */
	private void closeResultSet(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * to generate a PreparedStatement according sql and parameters.
	 * @param conn database connection
	 * @param sql Sql to be executed. It has one or more parameters. It can be insert,delete,update or select.
	 * @param paramList input parameter values. They are provided in the same order with parameters in the sql.
	 * @return PreparedStatement. The parameters' values are already set.
	 * @throws Exception
	 */
	private PreparedStatement getStatement(Connection conn, String sql, Object[] parameters) throws Exception {
		PreparedStatement statement = conn.prepareStatement(sql);

		for (int i = 1; i <= parameters.length; i++) {
			Object value = parameters[i-1];
			setParamValue(statement, i, value);
		}
		
		return statement;
	}

	private void setParamValue(PreparedStatement statement, int paramIndex, Object value) throws SQLException {
		if (value instanceof String) {
			String str = (String)value;
			if (str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {	// DateTime
				statement.setTimestamp(paramIndex, Timestamp.valueOf(str));
				return;
			} else if (str.matches("\\d{4}-\\d{2}-\\d{2}")) {	// Date
				statement.setDate(paramIndex, java.sql.Date.valueOf(str));
				return;
			} else if (str.matches("\\d{2}:\\d{2}:\\d{2}")) {	// Time
				statement.setTime(paramIndex, Time.valueOf(str));
				return;
			}
		} else if (value instanceof java.util.Date) {
			Timestamp t = (Timestamp)DataType.toType(value, DataType.DT_DateTime);
			statement.setTimestamp(paramIndex, t);
			return;
		}
		
		statement.setObject(paramIndex, value);
	}
	
	private void setParamValue(PreparedStatement statement, int paramIndex, int targetSqlType, Object value) throws SQLException {
		switch (targetSqlType) {
		case java.sql.Types.DATE:
			value = DataType.toType(value, DataType.DT_Date);
			break;
		case java.sql.Types.TIME:
			value = DataType.toType(value, DataType.DT_Time);
			break;
		case java.sql.Types.TIMESTAMP:
			value = DataType.toType(value, DataType.DT_DateTime);
			break;
		}
		statement.setObject(paramIndex, value, targetSqlType);
	}
	
	/**
	 * Generate an offline DataSet from a online ResultSet.
	 * @param rs ResultSet
	 * @return DataSet
	 * @throws Exception
	 */
	private DataSet getDataSet(ResultSet rs) throws Exception {
		return getDataSet(rs, 0, -1);
	}
	private DataSet getDataSet(ResultSet rs, int startRowNum, int rowCount) throws Exception {
		try {
			DataSetImpl dsi = new DataSetImpl(dbType, rs, startRowNum, rowCount);
		    return dsi;
		} catch (Exception e) {	
			if (e instanceof java.net.ConnectException) {	// 断连
				throw e;
			}
			
			return null;	// 支持无效的结果集.不抛异常,作为正常返回
		}
	}
    
	private StoredProcInfo getStoredProcInfo(Connection conn, String storedProcName) throws SQLException {
		String procName = storedProcName.toUpperCase();
		StoredProcInfo procInfo = storedProcInfoMap.get(procName);
		if (procInfo != null) {
			return procInfo;
		}
		
		procInfo = new StoredProcInfo(conn, storedProcName);
		storedProcInfoMap.put(procName, procInfo);
		
		return procInfo;
	}
	private Object getOutParamValue(CallableStatement statement, int parameterIndex, int sqlType) throws Exception {
		switch(sqlType) {
		case java.sql.Types.TINYINT:
			return statement.getByte(parameterIndex);
		case java.sql.Types.SMALLINT:
			return statement.getShort(parameterIndex);
		case java.sql.Types.INTEGER:
		case java.sql.Types.DECIMAL:
		case java.sql.Types.NUMERIC:
			return statement.getInt(parameterIndex);
		case java.sql.Types.BIGINT:
			return statement.getLong(parameterIndex);
		case java.sql.Types.REAL:
			return statement.getFloat(parameterIndex);
		case java.sql.Types.FLOAT:
			return statement.getFloat(parameterIndex);
		case java.sql.Types.DOUBLE:
			return statement.getDouble(parameterIndex);
		/*case java.sql.Types.DECIMAL:
		case java.sql.Types.NUMERIC:
			return statement.getBigDecimal(parameterIndex);*/
		case java.sql.Types.BIT:
		case java.sql.Types.BOOLEAN:
			return statement.getBoolean(parameterIndex);
		case java.sql.Types.CHAR:
			return statement.getString(parameterIndex).charAt(0);
		case java.sql.Types.VARCHAR:
		case java.sql.Types.LONGVARCHAR:
			return statement.getString(parameterIndex);
		case java.sql.Types.BINARY:
		case java.sql.Types.VARBINARY:
		case java.sql.Types.LONGVARBINARY:
			return statement.getBytes(parameterIndex);
		default:
			throw new GeneralException("type is not supported! sqltype=" + sqlType + ", parameterIndex=" + parameterIndex);
		}
	}
	
	Method[] getDeclaredMethods(Class classObj, String prefix) {
		Method[] methods = classObj.getDeclaredMethods();
		int index = 0;
		for (Method m : methods) {
			if (m.getName().startsWith(prefix)) {
				methods[index++] = m;
			}
		}
		
		Method[] result = new Method[index];
		for (int i = 0; i < index; i++) {
			result[i] = methods[i];
		}
		return result;
	}
	
	private DataClassInfo getClassInfo(Class classObj) {
		String className = classObj.getName();
		DataClassInfo clsInfo = classInfoMap.get(className);
		if (clsInfo != null) {
			return clsInfo;
		}
		
		clsInfo = new DataClassInfo(classObj);
		classInfoMap.put(className, clsInfo);
		
		return clsInfo;
	}
	
	// driverName jdbc driver name for the database. 
	// driverName can be null for default jdbc drivers of oracle and mysql
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	// dbUrl the jdbc url of the target database. It can not be null.
	public String getDbUrl() {
		return dbUrl;
	}
	// oracle: jdbc:oracle:thin:@127.0.0.1:1521:orc8i
	// mysql:  jdbc:mysql://localhost/dbName?user=userName&password=userPasswd
	// db2:    jdbc:db2://192.168.1.10:50000/database
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
		if (dbUrl.indexOf(":oracle:") > 0) {
			dbType = DBType.DB_ORACLE;	// oracle
			if (driverName == null) {
				driverName = "oracle.jdbc.driver.OracleDriver";
			}
		} else if  (dbUrl.indexOf(":mysql:") > 0) {
			dbType = DBType.DB_MYSQL;	// mysql
			
			if (driverName == null) {
				driverName = "com.mysql.jdbc.Driver";
			}
//		} else if  (dbUrl.indexOf(":db2:") > 0) {
//			// jdbc:db2://192.168.1.10:50000/database
//			dbType = DBType.DB_DB2;	// db2
//			if (driverName == null) {
//				driverName = "com.ibm.db2.jcc.DB2Driver";
//			}
		}
		
		parseMethods(this, innerMethods, "_");
	}

	// username to login database. It can not be null.
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	// password of the user. It can not be null.
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	// connectCount connection count to the database
	public int getConnectCount() {
		return connectCount;
	}
	public void setConnectCount(int connectCount) {
		this.connectCount = connectCount;
	}
	
	// the interval of reconnecting db, in milliseconds.
	public int getReconnectInterval() {
		return reconnectInterval;
	}
	public void setReconnectInterval(int reconnectInterval) {
		this.reconnectInterval = reconnectInterval;
	}
	
	// waitTimeout maximal timeout to wait for a database connection available, in milliseconds.
	public int getWaitTimeout() {
		return waitTimeout;
	}
	public void setWaitTimeout(int waitTimeout) {
		this.waitTimeout = waitTimeout;
	}
	
	public DBMonitor getDBMonitor(){
		return dbMonitor;
	}
	public void setDBMonitor(DBMonitor dbMonitor){
		this.dbMonitor = dbMonitor;
		monitorMethods.clear();
		if (dbMonitor != null) {
			parseMethods(dbMonitor, monitorMethods, "on");
		}
	}
	
	public void setPreparedProcNames(String preparedProcNames) {
		this.preparedProcNames = preparedProcNames;
	}

	private GeneralException handleException(Exception e) {
		if (e instanceof java.net.ConnectException) {
			connected = false;		// 设置断连标志
			boolean bRet = startReconnectThread();	// 启动重连线程
			if (dbMonitor != null && bRet) {	// 用bRet避免回调dbMonitor多次
				dbMonitor.onDisconnected();
			}
		} 
		
		GeneralException ge = e instanceof GeneralException ? 
				(GeneralException)e : new GeneralException(e);
		return ge;
	}
	
	private synchronized boolean startReconnectThread() {
		if (reconnectThread == null) {
			reconnectThread = new Thread(this);
			reconnectRun = true;
			reconnectThread.start();
			return true;
		}
		
		return false;
	}
	
	private synchronized void stopReconnectThread() {
		if (reconnectThread != null) {
			reconnectRun = false;
			ThreadUtil.stop(reconnectThread, 3000);
			reconnectThread = null;
		}
	}
	
	public void run() {
		while (reconnectRun && !connected) {
			boolean bSleep = ThreadUtil.sleep(reconnectInterval);
			if (bSleep) {
				connect();
			}
		}
		
		// 线程结束，将标志服务，变量清除
		reconnectRun = false;
		reconnectThread = null;
	}
	
	private Object dbOperate(String methodName, Object... params) {
		java.util.Date beginTime = new java.util.Date();
		
		String monitorBeginName = "on" + methodName.substring(0, 1).toUpperCase()
									+ methodName.substring(1);
		String monitorResultName = monitorBeginName + "Result";
		String innerName = "_" + methodName;
		int paramCount = params != null ? params.length : 0;
		if (dbMonitor != null) {
			Method begin = getMethod(monitorMethods, monitorBeginName, paramCount);
			try {
				begin.invoke(dbMonitor, params);
			} catch (Exception e) {}
		}
		
		try {
			Method operate = getMethod(innerMethods, innerName, paramCount);
			Object ret = operate.invoke(this, params);
			
			if (dbMonitor != null) {
				notifyMonitorResult(beginTime, ret, monitorResultName, params);
			}
			return ret;
		} catch (Exception e) {
			if (dbMonitor != null) {
				notifyMonitorResult(beginTime, e, monitorResultName, params);
			}
			throw (GeneralException)e;
		}
	}
	
	private Method getMethod(Map<String, Method[]> table, String methodName, int paramCount) {
		Method[] operateArray = table.get(methodName);
		Method operate = operateArray[0];
		if (operateArray.length > 1) {
			for (int i = 0; i < operateArray.length; i++) {
				Method m = operateArray[i];
				if (m.getParameterTypes().length == paramCount) {
					operate = m;
					break;
				}
			}
		}
		return operate;
	}
	
	private void notifyMonitorResult(java.util.Date beginTime, Object ret,
					String monitorResultName, Object[] params) {
			long interval = (new java.util.Date()).getTime() - beginTime.getTime();
			DBOperateResult result = null;
			if (ret instanceof Exception) {
				result = new DBOperateResult((Exception)ret, interval);
			} else {
				result = new DBOperateResult((Object)ret, interval);
			}
			
			int paramCount = params != null ? params.length : 0;
			Method callback = getMethod(monitorMethods, monitorResultName, paramCount + 1);
			Object[] args = new Object[paramCount+1];
			args[0] = result;
			for (int i = 0; i < paramCount; i++) {
				args[i+1] = params[i];
			}
			try {
				callback.invoke(dbMonitor, args);
			} catch (Exception ex) {} 
	}
	
	private static void parseMethods(Object obj, Map<String, Method[]> table, String prefix) {
		table.clear();
		if (obj == null) {
			return;
		}
		
		Method[] methods = obj.getClass().getDeclaredMethods();
		for (Method m : methods) {
			String methodName = m.getName();
			if (methodName.startsWith(prefix)) {
				Method[] ms = table.get(methodName);
				if (ms == null) {
					table.put(methodName, new Method[] {m});
				} else {
					int iCount = ms.length;
					Method[] ms2 = new Method[iCount + 1];
					for (int i = 0; i < iCount; i++) {
						ms2[i] = ms[i];
					}
					ms2[iCount] = m;
					table.put(methodName, ms2);
				}
			}
		}
	}
	
	// 连接数据库的驱动名
	private String driverName = null;
	// 数据库连接串
	private String dbUrl = null;
	// 连接数据库的用户名
	private String userName = null;
	// 连接数据库的用户密码
	private String password = null;
	// 与数据库建立的连接数
	private int connectCount = 2;
	// 重连数据库的间隔，单位毫秒
	private int reconnectInterval = 30000;
	// 操作等待超时时间，单位毫秒
	private int waitTimeout = 10000;
	// 数据操作监控接口
	private DBMonitor dbMonitor = null;
	// 需要预解析的存储过程名，多个存储过程名用","分割
	private String preparedProcNames = null;
	
	private int dbType = DBType.DB_UNKNOWN;
	
	private boolean connected = false;		// 当前是否连接数据库
	private int currentConnectCount = 0;
	private Thread reconnectThread = null;	// 重连线程
	private boolean reconnectRun = false;	// 重连线程是否可以运行
	private Connection[] connections;
	private int[] status;		// 数据库连接是否被使用的状态,0-未使用，1-正在被使用
	private int[] transactions;	// 各连接的 transactionId
	private int nextTransactionId = 1;
	private final int INVALID_TRANS_ID = -1;
	
	// 缓存存储过程的参数解析结果
	private Map<String, StoredProcInfo> storedProcInfoMap = new Hashtable<String, StoredProcInfo>();
	
	// 缓冲对象的解析结果
	private Map<String, DataClassInfo> classInfoMap = new Hashtable<String, DataClassInfo>();
	// 缓冲DataSet 到对象的解析结果
	//private Map<Class, ArrayList<ColumnMap>>classInfoMap = new HashMap<Class, ArrayList<ColumnMap>>();
	private Map<String, Method[]> innerMethods = new Hashtable<String, Method[]>();
	private Map<String, Method[]> monitorMethods  = new Hashtable<String, Method[]>();

}

package com.dbcom;

import java.util.List;
/**
 * 数据库访问对象
 * @author taddy
 *
 */
public interface DBAccess {
	public String getDriverName();
	public void setDriverName(String driverName);
	
	public String getDbUrl();
	public void setDbUrl(String dbUrl);
	
	public String getUserName();
	public void setUserName(String userName);
	
	public String getPassword();
	public void setPassword(String password);
	
	public int getConnectCount();
	public void setConnectCount(int connectCount) ;
	
	public int getReconnectInterval();
	public void setReconnectInterval(int reconnectInterval);
	
	public int getWaitTimeout();
	public void setWaitTimeout(int waitTimeout);
	
	public DBMonitor getDBMonitor();
	public void setDBMonitor(DBMonitor dbMonitor);
	
	public void setPreparedProcNames(String preparedProcNames);
	
	// Establish multiple connections with database.
	public void connect();
	
	// Disconnect all connections from database.
	public void disconnect();
	
	/**
	 * query current connection status
	 * @return true-connection is established. false-connection is broken.
	 */
	public boolean isConnected();
	
	/**
	 * Query database type, such as DB_ORACLE, DB_MYSQL,...
	 * connect method must be called first.
	 * @return databaseType
	 */
	public int getDBType();
	
	/**
	 * Get JDBC Driver version. connect method must be called first.
	 * @return jdbc driver version
	 */
	public String getDriverVersion();
	
	/**
	 * begin a transaction
	 * @return transaction Id
	 */
	public int beginTransaction();
	
	/**
	 * commit a transaction
	 * @param transactionId the id of transaction. It is returned by beginTransaction().
	 */
	public void commit(int transactionId);
	
	/**
	 * rollback a transaction
	 * @param transactionId the id of transaction. It is returned by beginTransaction().
	 */
	public void rollback(int transactionId);
	
	
	/**
	 * Execute sql with parameter. select sql is invalid here.
	 * @param sql The sql to execute. Usually, it is insert, update, delete or ddl.
	 * @param parameters input parameter values. They are provided in the same order with parameters in the sql.
	 * @return return the count of affected records
	 */
	public int executeSql(String sql, Object... parameters);
	public int executeSql(int transactionId, String sql, Object... parameters);
	
	/**
	 * batch sql operations
	 * @param sqls one or more sqls
	 * @return an array of update counts containing one element for each command in the batch. 
	 *         The elements of the array are ordered according to the order in which commands were added to the batch. 
	 */
	public int[] executeBatch(String... sqls);
	public int[] executeBatch(int transactionId, String... sqls);
	
	/**
	 * Execute select sql with parameters
	 * @param sql select sql to be executed.
	 * @param parameters input parameter values. They are provided in the same order with parameters in the sql.
	 * @return Return a DataSet. DataSet is a offline ResultSet.
	 */
	public DataSet executeQuery(String sql, Object... parameters);	
	
	/**
	 * Execute select sql with parameters
	 * @param startRowNum the start row number of records to fill into dataset. from 0.
	 * @param rowCount how many records to fill into dataset. if -1, that means all records. 0 is also valid.
	 * @param sql select sql to be executed.
	 * @param parameters input parameter values. They are provided in the same order with parameters in the sql.
	 * @return Return a DataSet. DataSet is a offline ResultSet.
	 */
	public DataSet executeQuery(int startRowNum, int rowCount, String sql, Object... parameters);
	
	/**
	 * Execute storedproc. It supports both procedure and function. 
	 * @param storedProcName the name of procedure or function to be executed.
	 * @param inParameters in/inout parameter values. They are provided in the same order with in/inout parameters of the storedproc.
	 * @return return an object array. the objects are in order:
	 *    1) return value if the storedproc is a function
	 *    2) out/inout parameter output value. They are in the same order with the out/inout parameters of the storedproc.
	 *    3) zero or more DataSet
	 */
	public Object[] executeStoredProc(String storedProcName, Object... inParameters);
	public Object[] executeStoredProc(int transactionId, String storedProcName, Object... inParameters);

	/**
	 * Convert DataSet records to specified data object instance list. 
	 * The method is usually used together with querySql or executeStoredProc.
	 * @param dataSet The DataSet to be converted
	 * @param dataClass the target class to be converted. The class member variables are annotated by @DBField.
	 *        convert rule: the value of @DBField is the same with the column name in DataSet.
	 * @return return an list of the specified class instances.
	 */
	public <T> List<T> toObjects(DataSet dataSet, Class<T> dataClass);
	// direct object operation
	public void insertObject(Object dataObject);
	public void insertObject(int transactionId, Object dataObject);
	public void updateObject(Object dataObject);
	public void updateObject(int transactionId, Object dataObject);
	public void deleteObject(Object dataObject);
	public void deleteObject(int transactionId, Object dataObject);
}

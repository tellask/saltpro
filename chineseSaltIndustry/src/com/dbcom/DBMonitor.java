package com.dbcom;
/**
 * 数据库监视类
 * @author taddy
 *
 */
public interface DBMonitor {
	public void onConnected();
	//public void onConnectResult(DBOperateResult result);
	public void onDisconnected();
	//public void onDisconnectResult(DBOperateResult result);
	
	public void onBeginTransaction();
	public void onBeginTransactionResult(DBOperateResult result);
	public void onCommit(int transactionId);
	public void onCommitResult(DBOperateResult result, int transactionId);
	public void onRollback(int transactionId);
	public void onRollbackResult(DBOperateResult result, int transactionId);
	
	public void onExecuteSql(String sql, Object... parameters);
	public void onExecuteSqlResult(DBOperateResult result, String sql, Object... parameters);
	public void onExecuteSql(int transactionId, String sql, Object... parameters);
	public void onExecuteSqlResult(DBOperateResult result, int transactionId, String sql, Object... parameters);
	
	public void onExecuteBatch(String... sqls);
	public void onExecuteBatchResult(DBOperateResult result, String... sqls);
	
	public void onExecuteQuery(String sql, Object... parameters);
	public void onExecuteQueryResult(DBOperateResult result, String sql, Object... parameters);
	public void onExecuteQuery(int startRowNum, int rowCount, String sql, Object... parameters);
	public void onExecuteQueryResult(DBOperateResult result, int startRowNum, int rowCount, String sql, Object... parameters);
	
	
	public void onExecuteStoredProc(String storedProcName, Object... inParameters);
	public void onExecuteStoredProcResult(DBOperateResult result, String storedProcName, Object... inParameters);
	public void executeStoredProc(int transactionId, String storedProcName, Object... inParameters);
	public void executeStoredProcResult(DBOperateResult result, int transactionId, String storedProcName, Object... inParameters);
	
	public void onInsertObject(Object dataObject);
	public void onInsertObjectResult(DBOperateResult result, Object dataObject);
	public void onInsertObject(int transactionId, Object dataObject);
	public void onInsertObjectResult(DBOperateResult result, int transactionId, Object dataObject);
	public void onUpdateObject(Object dataObject);
	public void onUpdateObjectResult(DBOperateResult result, Object dataObject);
	public void onUpdateObject(int transactionId, Object dataObject);
	public void onUpdateObjectResult(DBOperateResult result, int transactionId, Object dataObject);
	public void onDeleteObject(Object dataObject);
	public void onDeleteObjectResult(DBOperateResult result, Object dataObject);
	public void onDeleteObject(int transactionId, Object dataObject);
	public void onDeleteObjectResult(DBOperateResult result, int transactionId, Object dataObject);
}

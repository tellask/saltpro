package com.subject.dao.impl;

import java.util.List;

import marst.component.FinalString;
import marst.util.ComStringUtil;
import marst.util.LoadProperties;

import com.dbcom.DBAccess;
import com.dbcom.DataSet;
import com.dbcom.dataset.QueryPageResult;
import com.dbcom.dataset.SqlCommand;
import com.dbcom.impl.DBAccessImpl;

public class BaseDao {
	protected DBAccess dbAccess;
	
	private void initDBAccess(){
		LoadProperties loadProperties=new LoadProperties();
		String db_url=loadProperties.getValue(FinalString.DB_URL);
		String db_usernmae=loadProperties.getValue(FinalString.DB_USERNAME);
		String db_pwd=loadProperties.getValue(FinalString.DB_PASSWORD);
		String db_count=loadProperties.getValue(FinalString.DB_CONNECT_COUNT);
		String db_reconect=loadProperties.getValue(FinalString.DB_RECONECT_INTERVAL);
		String db_waittime=loadProperties.getValue(FinalString.DB_WAIT_TIMEOUT);
		this.dbAccess=new DBAccessImpl();
		this.dbAccess.setDbUrl(db_url);
		this.dbAccess.setUserName(db_usernmae);
		this.dbAccess.setPassword(db_pwd);
		this.dbAccess.setConnectCount(Integer.parseInt(db_count));
		this.dbAccess.setReconnectInterval(Integer.parseInt(db_reconect));
		this.dbAccess.setWaitTimeout(Integer.parseInt(db_waittime));
	}
	
	private int beginTransaction(){
		initDBAccess();
		this.dbAccess.connect();
		int result= this.dbAccess.beginTransaction();
		this.dbAccess.disconnect();
		return result;
	}
	private void commit(int transactionId){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.commit(transactionId);
		this.dbAccess.disconnect();
	}
	private void rollback(int transactionId){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.rollback(transactionId);
		this.dbAccess.disconnect();
	}
	/**
	 * 批量执行SQL语句，并添加事务
	 * @param commandList
	 * @return
	 */
	protected boolean executeSqlList(List<SqlCommand> commandList){
		boolean res=false;
		if(commandList.size()>0){
			int transactionId=beginTransaction();
			try {
				for(int i=0;i<commandList.size();i++){
					SqlCommand sqlCommand=commandList.get(i);
					String sqlText=sqlCommand.getCommandText();
					List<Object> sqlParameters=sqlCommand.getComandParameters();
					if(sqlParameters.size()>0){
						Object[] arrayParem=new Object[sqlParameters.size()];
						for(int m=0;m<sqlParameters.size();m++){
							arrayParem[m]=sqlParameters.get(m);
						}
						executeSql(transactionId,sqlText,arrayParem);
					}
				}
				commit(transactionId);
				res=true;
			} catch (Exception e) {
				rollback(transactionId);
				e.printStackTrace();
			}
		}
		return res;
	}
	/**
	 * 执行SQL返回影响行数
	 * @param sql
	 * @param parameters
	 * @return
	 */
	protected int executeSql(String sql, Object... parameters){
		initDBAccess();
		this.dbAccess.connect();
		int result=this.dbAccess.executeSql(sql,parameters);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 执行SQL返回影响行数，加入事务
	 * @param transactionId
	 * @param sql
	 * @param parameters
	 * @return
	 */
	protected int executeSql(int transactionId, String sql, Object... parameters){
		initDBAccess();
		this.dbAccess.connect();
		int result=this.dbAccess.executeSql(transactionId,sql,parameters);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 批量执行SQL，返回影响行数数组
	 * @param sqls
	 * @return
	 */
	protected int[] executeBatch(String... sqls){
		initDBAccess();
		this.dbAccess.connect();
		int[] result=this.dbAccess.executeBatch(sqls);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 批量执行SQL，返回影响行数数组，加入事务
	 * @param transactionId
	 * @param sqls
	 * @return
	 */
	protected int[] executeBatch(int transactionId, String... sqls){
		initDBAccess();
		this.dbAccess.connect();
		int[] result=this.dbAccess.executeBatch(transactionId,sqls);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 查询SQL，返回集合
	 * @param sql
	 * @param parameters
	 * @return
	 */
	protected DataSet executeQuery(String sql, Object... parameters){
		initDBAccess();
		this.dbAccess.connect();
		DataSet result=this.dbAccess.executeQuery(sql,parameters);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 查询SQL，分页展示，并返回分页对象（集合，总记录数）
	 * @param startRowNum
	 * @param rowCount
	 * @param sql
	 * @param parameters
	 * @return
	 */
	protected QueryPageResult executeQuery(int startRowNum, int rowCount, String sql, Object... parameters){
		QueryPageResult queryPageResult=null;
		initDBAccess();
		this.dbAccess.connect();
		if(!ComStringUtil.isEmpty(sql) && sql.length()>0){
			queryPageResult=new QueryPageResult();
			DataSet result=this.dbAccess.executeQuery(startRowNum,rowCount,sql,parameters);
			StringBuffer newQuerySql=new StringBuffer("");
			newQuerySql.append("select count(1) ");
			sql=sql.toLowerCase();	//全部转化成小写
			String fromSql=sql.substring(sql.indexOf("from"));	//截断从第一个from开始之后的字段
			newQuerySql.append(fromSql);	//组合新的查询语句，查询总记录数
			DataSet resCount=this.dbAccess.executeQuery(newQuerySql.toString(), parameters);
			int totailCount=0;
			if(resCount.next()){
				String resString=resCount.getString(0);
				if(ComStringUtil.isInt(resString)){
					totailCount=Integer.parseInt(resString);
				}
				queryPageResult.setTotalCount(totailCount);
				queryPageResult.setQueryList(result);
			}
		}
		this.dbAccess.disconnect();
		return queryPageResult;
	}
	/**
	 * 直接存储过程，返回对象数组
	 * @param storedProcName
	 * @param inParameters
	 * @return
	 */
	protected Object[] executeStoredProc(String storedProcName, Object... inParameters){
		initDBAccess();
		this.dbAccess.connect();
		Object[] result=this.dbAccess.executeStoredProc(storedProcName,inParameters);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 执行存储过程，返回对象数组，加入事务
	 * @param transactionId
	 * @param storedProcName
	 * @param inParameters
	 * @return
	 */
	protected Object[] executeStoredProc(int transactionId, String storedProcName, Object... inParameters){
		initDBAccess();
		this.dbAccess.connect();
		Object[] result=this.dbAccess.executeStoredProc(transactionId,storedProcName,inParameters);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 查询结果集合，转化为实体对象集合（多个）
	 * @param dataSet
	 * @param dataClass
	 * @return
	 */
	protected <T> List<T> toObjects(DataSet dataSet, Class<T> dataClass){
		initDBAccess();
		this.dbAccess.connect();
		List<T> result=this.dbAccess.toObjects(dataSet,dataClass);
		this.dbAccess.disconnect();
		return result;
	}
	/**
	 * 查询结果集合，转化为实体对象（单个）
	 * @param dataSet
	 * @param dataClass
	 * @return
	 */
	protected <T> T toObject(DataSet dataSet, Class<T> dataClass){
		initDBAccess();
		this.dbAccess.connect();
		List<T> result=this.dbAccess.toObjects(dataSet,dataClass);
		if(result.size()==1){
			this.dbAccess.disconnect();
			return result.get(0);
		}else{
			this.dbAccess.disconnect();
			return null;
		}
	}
	/**
	 * 新增对象
	 * @param dataObject
	 */
	protected void insertObject(Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.insertObject(dataObject);
		this.dbAccess.disconnect();
	}
	/**
	 * 新增对象，加入事务
	 * @param transactionId
	 * @param dataObject
	 */
	protected void insertObject(int transactionId, Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.insertObject(transactionId,dataObject);
		this.dbAccess.disconnect();
	}
	/**
	 * 更新对象
	 * @param dataObject
	 */
	protected void updateObject(Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.updateObject(dataObject);
		this.dbAccess.disconnect();
	}
	/**
	 * 更新对象，加入事务
	 * @param dataObject
	 */
	protected void updateObject(int transactionId, Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.updateObject(transactionId,dataObject);
		this.dbAccess.disconnect();
	}
	/**
	 * 删除对象
	 * @param dataObject
	 */
	protected void deleteObject(Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.deleteObject(dataObject);
		this.dbAccess.disconnect();
	}
	/**
	 * 删除对象，加入事务
	 * @param dataObject
	 */
	protected void deleteObject(int transactionId, Object dataObject){
		initDBAccess();
		this.dbAccess.connect();
		this.dbAccess.deleteObject(transactionId,dataObject);
		this.dbAccess.disconnect();
	}
}

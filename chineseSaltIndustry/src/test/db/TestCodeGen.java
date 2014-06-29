
package test.db;

import marst.component.FinalString;
import marst.util.ComStringUtil;
import marst.util.LoadProperties;

import com.dbcom.DBAccess;
import com.dbcom.DataSet;
import com.dbcom.codegen.DBCodeGenerate;
import com.dbcom.impl.DBAccessImpl;

/**
 * 执行方法前，请删除所有pojo下的文件，否则会发生重复
 * 
 * @author taddy
 *
 */
public class TestCodeGen {
	public static void main(String[] args) {
		LoadProperties loadProperties=new LoadProperties();
		//String db_driver=loadProperties.getValue(FinalString.DB_DRIVER);
		String db_url=loadProperties.getValue(FinalString.DB_URL);
		//String db_database=loadProperties.getValue(FinalString.DB_DATABASE);
		String db_usernmae=loadProperties.getValue(FinalString.DB_USERNAME);
		String db_pwd=loadProperties.getValue(FinalString.DB_PASSWORD);
		String db_dataName=loadProperties.getValue(FinalString.DB_DATABASE);//数据库名称
		DBAccess dbAccess = new DBAccessImpl();
		// mysql
		//String dbUrl = "jdbc:mysql://localhost/db_salt";
		// oracle
		//String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
		dbAccess.setDbUrl(db_url);
		dbAccess.setUserName(db_usernmae);
		dbAccess.setPassword(db_pwd);
		//拿到数据库中所有表
		//getAllTable(dbAccess,db_dataName);
		//生成一个表数据
		String tableName="system_rightsType";
		getOneTable(dbAccess,tableName);
		
	}
	private static void getAllTable(DBAccess dbAccess,String dataBaseName){
		dbAccess.connect();
		//根据数据库名称拿到所有表名，针对Mysql
		String getAllTablesNameSql="select table_name from information_schema.tables where table_schema=?";
		DataSet tablesName=dbAccess.executeQuery(getAllTablesNameSql, dataBaseName);
		
		DBCodeGenerate generate = new DBCodeGenerate();
		generate.setDBAccess(dbAccess);
		//generate.generateClass("src", "com.subject.pojo", "terminus_info", "TerminusInfo");
		while(tablesName.next()){
			String tableName=tablesName.getString(0);
			String javaClassName=null;
			
			if(!ComStringUtil.isEmpty(tableName) && tableName.indexOf("_")>0){
				char[] strChar=tableName.toCharArray();
				StringBuffer className=new StringBuffer("");
				int num=0;
				for(int i=0;i<strChar.length;i++){
					String str=strChar[i]+"";
					if(i==0){
						className.append(str.toUpperCase());
					}else if(str.equals("_")){
						num=i;
						continue;
					}else if(num!=0 &&(num+1)==i){
						className.append(str.toUpperCase());
					}else{
						className.append(str);
					}
				}
				javaClassName=className.toString();
			}
			//根据数据库表，生成实体类
			//如果表内字段有默认值，则需要手动修改pojo下实体类中相应修改，否则执行时会产生错误！
			generate.generateClass("src", "com.subject.pojo", tableName, javaClassName);
			
		}
		
		dbAccess.disconnect();
		System.out.println("finished!");
	}
	private static void getOneTable(DBAccess dbAccess,String tableName){
		dbAccess.connect();
		DBCodeGenerate generate = new DBCodeGenerate();
		generate.setDBAccess(dbAccess);
		String javaClassName=null;
		if(!ComStringUtil.isEmpty(tableName) && tableName.indexOf("_")>0){
			char[] strChar=tableName.toCharArray();
			StringBuffer className=new StringBuffer("");
			int num=0;
			for(int i=0;i<strChar.length;i++){
				String str=strChar[i]+"";
				if(i==0){
					className.append(str.toUpperCase());
				}else if(str.equals("_")){
					num=i;
					continue;
				}else if(num!=0 &&(num+1)==i){
					className.append(str.toUpperCase());
				}else{
					className.append(str);
				}
			}
			javaClassName=className.toString();
			generate.generateClass("src", "com.subject.pojo", tableName, javaClassName);
			dbAccess.disconnect();
			System.out.println("finished!");
		}
	}
}

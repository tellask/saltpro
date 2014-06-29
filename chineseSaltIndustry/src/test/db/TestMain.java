package test.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.OracleDriver;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		//连接对象   
		Statement stmt = null;
		//语句对象   
		ResultSet rs = null;
		try{    
			DriverManager.registerDriver(new OracleDriver());
			//注册驱动程序  
			//建立到数据库的连接    
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCL", "saltmanager", "manage2012");
			String sql = "select TABLE_NAME, COMMENTS from all_tab_comments where table_type='TABLE' and OWNER='SALTMANAGER' and table_name='LOGIN_USER'"; 
			//String sql = "SELECT department_id,department_name,manager_id,location_id " + 
			//"FROM departments";    
			stmt = conn.createStatement();
			//通过连接对象获得语句对象   
			boolean bool = stmt.execute(sql);    
			if(bool){
				//如果布尔值为true，就意味着执行的是查询语句     
				rs = stmt.getResultSet();     
				while(rs.next()){      
					System.out.println(rs.getString("TABLE_NAME"));      
					System.out.println(rs.getString("COMMENTS"));      
					System.out.println("====================================");     
					}    
				}else{//如果布尔值为FALSE时，就意
					int count = stmt.getUpdateCount();
					System.out.println(count);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{//收尾工作.  
				if(rs != null){     
					try {      rs.close();     } 
					catch (SQLException e) 
					{      e.printStackTrace();     }    
					}    
				if(stmt != null){    
					try {      stmt.close();     
					}catch (SQLException e) {      e.printStackTrace();     } }    
				if(conn != null){     
					try {      conn.close();     } 
					catch (SQLException e) { 
						e.printStackTrace();
				}
		}
	}
	}
}

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
		//���Ӷ���   
		Statement stmt = null;
		//������   
		ResultSet rs = null;
		try{    
			DriverManager.registerDriver(new OracleDriver());
			//ע����������  
			//���������ݿ������    
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:ORCL", "saltmanager", "manage2012");
			String sql = "select TABLE_NAME, COMMENTS from all_tab_comments where table_type='TABLE' and OWNER='SALTMANAGER' and table_name='LOGIN_USER'"; 
			//String sql = "SELECT department_id,department_name,manager_id,location_id " + 
			//"FROM departments";    
			stmt = conn.createStatement();
			//ͨ�����Ӷ�����������   
			boolean bool = stmt.execute(sql);    
			if(bool){
				//�������ֵΪtrue������ζ��ִ�е��ǲ�ѯ���     
				rs = stmt.getResultSet();     
				while(rs.next()){      
					System.out.println(rs.getString("TABLE_NAME"));      
					System.out.println(rs.getString("COMMENTS"));      
					System.out.println("====================================");     
					}    
				}else{//�������ֵΪFALSEʱ������
					int count = stmt.getUpdateCount();
					System.out.println(count);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{//��β����.  
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

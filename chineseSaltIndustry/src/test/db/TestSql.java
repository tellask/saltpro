package test.db;

import com.dbcom.DBAccess;
import com.dbcom.DataSet;
import com.dbcom.impl.*;

public class TestSql {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DBAccess dbAccess = new DBAccessImpl();
		// mysql
		String dbUrl = "jdbc:mysql://localhost/test";
		// oracle
		//String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		dbAccess.setDbUrl(dbUrl);
		dbAccess.setUserName("testuser");
		dbAccess.setPassword("test2008");
		dbAccess.connect();
		
		String sql = "insert into customer"
			+ " values(?, ?, ?, ?, ?)";
		//java.sql.Timestamp t = java.sql.Timestamp.valueOf("2008-10-12 23:55:32");
		byte cid = 2;
		int iCount = dbAccess.executeSql(sql, cid, "13812345678", "����", "2008-10-12 23:55:32", 1);
		//System.out.println(iCount);
		
		sql = "select * from customer";
		DataSet ds = dbAccess.executeQuery(sql);
		System.out.println("rowCount = " + ds.getRowCount());
		//List<Customer> customers = dbAccess.toObjects(ds, Customer.class);
		//for (Customer c : customers) {
		//	System.out.println(c.getCustomerName());
		//}
		
		dbAccess.disconnect();
		
	}

}

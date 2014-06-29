/*
 * Source code of the book of Thinking in Java Component Design
 * ��������Java ������
 * ����: �׵���
 * Email: kshark2008@gmail.com
 * Date: 2008-12
 * Copyright 2008-2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test.db;

import com.dbcom.DBAccess;
import com.dbcom.impl.DBAccessImpl;


public class TestProc {

	public static void main(String[] args) {
		DBAccess dbAccess = new DBAccessImpl();
		// mysql
		//String dbUrl = "jdbc:mysql://localhost/test";
		// oracle
		String dbUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		dbAccess.setDbUrl(dbUrl);
		dbAccess.setUserName("testuser");
		dbAccess.setPassword("test2008");
		dbAccess.connect();
		
		Object[] results = dbAccess.executeStoredProc("p3", 0);
		dbAccess.disconnect();
		
		//int iRet = (Integer)results[0];
		//System.out.println(iRet);
	}

}

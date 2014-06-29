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

package test.db.spring;

import javax.sql.DataSource;
import org.springframework.jdbc.core.*;
import java.sql.*;
import java.util.*;

public class CustomerServiceDao implements RowMapper {
	
	public CustomerServiceDao() {	
	}
	
	public List getCustomerSerivces(String phone) {
		StoredProc storedProc = new StoredProc(ds, "queryCustomerServices");
		
		SqlParameter phoneParam = new SqlParameter("p_customerPhone", Types.VARCHAR);
		storedProc.declareParameter(phoneParam);
		SqlOutParameter resultParam = new SqlOutParameter("p_result", Types.INTEGER);
		storedProc.declareParameter(resultParam);
		
		SqlReturnResultSet resultSetParam = new SqlReturnResultSet("customerServices", this);
		storedProc.declareParameter(resultSetParam);
		
		HashMap inParamMap = new HashMap();
		inParamMap.put("p_customerPhone", phone);
		storedProc.setInParams(inParamMap);
		Map retMap = storedProc.execute();
		int ret = (Integer)retMap.get("p_result");
		if (ret == 0) {	// ��ѯ�ɹ�
			// ����������
			List list = (List)retMap.get("customerServices");
			return list;
		} else {	// δ�鵽��Ӧ�����
			return null;
		}
	}
	
	public Object mapRow(ResultSet rs, int rowNum) {
		try {
			CustomerSerivce cs = new CustomerSerivce();
			cs.setCustomerId(rs.getInt(1));
			cs.setServiceId(rs.getInt(2));
			cs.setOpenServiceTime(rs.getTimestamp(3));
			cs.setServiceState(rs.getInt(4));
			return cs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	private DataSource ds = null;
/*	
	public DataSet getUserInfo(int userId) {
		String sql = "select * from usertab where userId=?";
		DataSet dataset = dbAccess.executeSql(sql, userId);
		return dataset;
	}
*/	
	
}

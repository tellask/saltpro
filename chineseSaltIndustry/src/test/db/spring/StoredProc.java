/*
 * Source code of the book of Thinking in Java Component Design
 * ����������Java ������
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
import org.springframework.jdbc.object.StoredProcedure;
import java.util.Map;

public class StoredProc extends StoredProcedure {
	
	public StoredProc(DataSource ds, String procName) {
		super(ds, procName);
	}
 
	public void setInParams(Map inParams) {
		this.inParams = inParams;
	}

	/**
	 * ִ�д洢����
	 * ���ڸ���execute ������abstract, ��˱���̳и���
	 */
	public Map execute() {
	    return super.execute(inParams);
	}

	private Map inParams = null;
}

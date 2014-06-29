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

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;


public class SpringStoredProcedure extends StoredProcedure{
    /*
     * ������/�������������ֵ�Ե�Map
     */
    private Map inParams = new HashMap();
    
    public SpringStoredProcedure() {
        
    }
    public SpringStoredProcedure(DataSource ds, String procName) {
        super(ds, procName);
    }
    /*
     * ����function, ���ô˷������÷���ֵ������
     */
    
    public void addReturnParam(String paramName, int paramType) {
        setFunction(true);
        addOutParam(paramName, paramType);
    }
    /*
     * ����sqlserver, sybase ����ݿ�Ĵ洢���? ���Է��ض����?
     *�������ʵ�ʵĴ洢��̲���ǰ���ô˷���, �м���ؽ��͵��ü��������
     */
    public void addReturnResultset(String paramName, RowMapper rm) {
        declareParameter(new SqlReturnResultSet(paramName, rm)); 
    }
    /*
     * �����������?
     */
    public void addInParam(String paramName, int paramType) {
        declareParameter(new SqlParameter(paramName, paramType));
        
    }
    /*
     * ����������
     */
    public void addOutParam(String paramName, int paramType) {
        declareParameter(new SqlOutParameter(paramName, paramType));
    }
    
    public void addInOutParam(String paramName, int paramType) {
        declareParameter(new SqlInOutParameter(paramName, paramType));
    }
    
    /*
     * ���oracle cursor ���͵Ĳ���,�˲�����������
     */
    public void addOutCursorParameter(String paramName, RowMapper rm) {
    /*
     * oracle.jdbc.OracleTypes.CURSOR �� -10, �Դ˽���Ӳ����, 
     *��������oracle ���·��ʷ�oracle��ݿ�Ҳ�����oracle jdbc��jar��
     */ 
        declareParameter(new SqlOutParameter(paramName, -10, rm));
    }
    /*
     * ���index table ���͵Ĳ���
     */
    public void addOutIndexTableParameter(String paramName, RowMapper rm){
        declareParameter(new SqlOutParameter(paramName, OracleTypes.PLSQL_INDEX_TABLE , rm));
    }
    /* 
     * ����execute()�����б��봫���Map ����/������������?
     */
    public void setParamValue(String paramName, Object paramValue) {
        inParams.put(paramName, paramValue);
    }
    
    /* 
     * Ԥ����洢���
     */
    public void prepare() {
        super.compile();
    }
    
    /* 
     * ִ�д洢���?
     * ���ڸ���execute ������abstract, ��˱���̳и���
     * */
    public Map execute() {
        return super.execute(inParams);
    }


    public Map getInParams() {
        return inParams;
    }

    public void setInParams(Map inParams) {
        this.inParams = inParams;
    }

    
    
}

/*
 * Source code of the book of Thinking in Java Component Design
 * 中文书名：Java 组件设计
 * 作者: 孔德生
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

public class TestDb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "aa";
		String[] arr = new String[] {str, "3.5"};
		TestDb.varTest(1, arr);

	}


	public static void varTest(int index, Object... args) {
		for (Object obj : args) {
			System.out.println(obj.getClass().getName());
		}
	}
	
	private String driverName;
	private String dbUrl;
	private String userName;
	private String password;
	private int connectCount;
	private int reconnectInterval;
	private int maxTimeout;
	
	

	
	
	
}

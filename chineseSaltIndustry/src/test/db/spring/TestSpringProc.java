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

package test.db.spring;

import java.util.List;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.*;



public class TestSpringProc {
	public static void main(String[] args) {
		Resource res = new ClassPathResource("ticd/java/component/db/spring/TestSpringProc.xml");
		XmlBeanFactory factory=new XmlBeanFactory(res);
		CustomerServiceDao customerServiceDao = (CustomerServiceDao)factory.getBean("customerServiceDao");
		List list = customerServiceDao.getCustomerSerivces("13812345678");
		for (int i = 0; i < list.size(); i++) {
			CustomerSerivce cs = (CustomerSerivce)list.get(i);
			System.out.println(cs.getServiceId());
		}
	}
	
}

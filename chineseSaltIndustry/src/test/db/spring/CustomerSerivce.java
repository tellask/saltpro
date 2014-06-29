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

public class CustomerSerivce {
	private int customerId;
	private int serviceId;
	private java.sql.Timestamp openServiceTime;
	private int serviceState;
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public java.sql.Timestamp getOpenServiceTime() {
		return openServiceTime;
	}
	public void setOpenServiceTime(java.sql.Timestamp openServiceTime) {
		this.openServiceTime = openServiceTime;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getServiceState() {
		return serviceState;
	}
	public void setServiceState(int serviceState) {
		this.serviceState = serviceState;
	}
	
	
}

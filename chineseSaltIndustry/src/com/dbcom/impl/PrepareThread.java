package com.dbcom.impl;

import java.sql.Connection;
import java.util.*;

import marst.util.Semaphore;

class PrepareThread implements Runnable {
	public PrepareThread(Connection conn, Map storedProcInfoMap, Semaphore semaphore) {
		this.conn = conn;
		this.storedProcInfoMap = storedProcInfoMap;
		this.semaphore = semaphore;
	}
	public void addPrepareStoredProc(String storedProcName) {
		storedProcNames.add(storedProcName);
	}
	public void run() {
		// 解析存储过程
		int count = storedProcNames.size();
		for (int i = 0; i < count; i++) {
			try {
				StoredProcInfo spi = new StoredProcInfo(conn, storedProcNames.get(i));
				String procName = storedProcNames.get(i).toUpperCase();
				storedProcInfoMap.put(procName, spi);
			} catch (Exception e) {}
		}
		
		// 设置信号量
		semaphore.AddCount();
	}

	private Connection conn;
	private Map storedProcInfoMap = null;
	private Semaphore semaphore;
	private ArrayList<String> storedProcNames = new ArrayList<String>();
}

package test.Jlogonent;

import marst.Jlogonent.JLog;

public class TestLogger {
	public static void main(String[] args) {
		TestLogger.TestThread t = new TestLogger.TestThread();
		t.start();
		try {
			t.join(2000);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static class TestThread extends Thread {
		public void run() {
			JLog logger = new JLog();
			
			logger.setLogFileName("log/myapp/myapp2");
			logger.setLogLevel(JLog.DEBUG);
			logger.setMaxSizePerFile(400);
			logger.setFileCountPerDay(3);
			
			for (int i = 1; i < 20; i++) {
				logger.debug("src", JLog.DI_OUTPUT, "dest", "this is msg #" + i);
			}
		}
	}
}

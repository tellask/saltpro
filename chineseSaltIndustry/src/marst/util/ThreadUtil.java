package marst.util;
/**
 * 线程工具类
 * @author taddy
 *
 */
public class ThreadUtil {
	public static boolean sleep(long millis) {
		try {
			Thread.sleep(millis);
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	public static void interrupt(Thread t) {
		try {
			t.interrupt();
		} catch (Exception e) {}
	}
	
	public static void stop(Thread t, long millis) {
		interrupt(t);
		
		try {
			t.join(millis);
		} catch (Exception e) {}
	}
	
	public static void waitObject(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
		}
	}
	
	public static void waitObject(Object obj, long millis) {
		try {
			obj.wait(millis);
		} catch (InterruptedException e) {
		}
	}
	
	public static void syncWait(Object obj) {
		synchronized(obj) {
			try {
				obj.wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void syncWait(Object obj, long millis) {
		synchronized(obj) {
			try {
				obj.wait(millis);
			} catch (InterruptedException e) {
			}
		}
	}
}

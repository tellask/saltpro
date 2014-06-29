package marst.util;

//信号量，用于同步，针对Stocker通信
public class Semaphore {
	public Semaphore(int initialValue, int maxValue) {
		this.initialValue = initialValue;
		this.maxValue = maxValue;
	}
	
	public synchronized void AddCount() {
		initialValue++;
		if (initialValue >= maxValue) {
			notifyAll();
		}
	}
	
	private int initialValue;
	private int maxValue;
}

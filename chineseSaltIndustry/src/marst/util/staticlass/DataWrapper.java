package marst.util.staticlass;
/**
 * 数据类型声明
 * @author taddy
 *
 * @param <T>
 */
public class DataWrapper<T> {
	private T value;
	
	public DataWrapper() {
	}
	
	public DataWrapper(T value) {
		setVal(value);
	}
	
	public T getVal() {
		return value;
	}
	
	public void setVal(T value) {
		this.value = value;
	}
}

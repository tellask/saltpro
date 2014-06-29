/*
 * 日志组件
 */
package marst.Jlogonent;

public interface LogFilter {
	public boolean canLog(int level, String myName, int direction, String otherName,
			String ftm, Object... args);
}

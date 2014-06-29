/*
 * 日志记录
 */
package marst.Jlogonent;

import marst.util.staticlass.ClassReflector;
import marst.util.staticlass.GeneralException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

public class JLog {
	// 日志级别
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;
	public static final int FATAL = 5;
	public static final int OFF = 6;
	
	// 方向
	public static final int DI_OUTPUT = 1;		// 模块输出
	public static final int DI_INPUT = 2;		// 模块输入
	public static final int DI_INTERNAL = 3;	// 模块内部处理
	
	/**
	 * 设置日志文件名
	 * @param logFileName 日志文件名，可以带路径，路径可以是相对路径，也可以是绝对路径。不带.log文件后缀。
	 * 如:  CtrlApp
	 *      log/CtrlApp
	 *      /ctrlsys/log/CtrlApp
	 * 最终磁盘上的日志文件名将是CtrlApp_20080115.log
	 */
	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}
	
	public String getLogFileName() {
		return logFileName;
	}
	
	// 设置日志等级
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
	
	public int getLogLevel() {
		return logLevel;
	}
	
	// 设置文件输出方式
	public void setFileOutput(boolean bFile) {
		this.bFile = bFile;
	}
	public boolean getFileOutput() {
		return bFile;
	}
	
	// 设置控制台输出方式
	public void setConsoleOutput(boolean bConsole) {
		this.bConsole = bConsole;
	}
	public boolean getConsoleOutput() {
		return bConsole;
	}
	
	public void setFileCountPerDay(int fileCountPerDay) {
		if (fileCountPerDay > 0) {
			this.fileCountPerDay = fileCountPerDay;
		}
	}
	public int getFileCountPerDay() {
		return fileCountPerDay;
	}
	
	// 单个日志文件大小
	public void setMaxSizePerFile(long maxSizePerFile) {
		if (maxSizePerFile > 0) {
			this.maxSizePerFile = maxSizePerFile;
		}
	}
	public long getMaxSizePerFile() {
		return maxSizePerFile;
	}	
	
	public void setLogFilter(LogFilter logFilter) {
		this.logFilter = logFilter;
	}
	
	public LogFilter getLogFilter() {
		return logFilter;
	}
	
	
	public void debug(String myName, int direction, String otherName, String fmt, Object... args) {
		log(DEBUG, myName, direction, otherName, fmt, args);
	}
	
	public void info(String myName, int direction, String otherName, String fmt, Object... args) {
		log(INFO, myName, direction, otherName, fmt, args);
	}
	
	public void warn(String myName, int direction, String otherName, String fmt, Object... args) {
		log(WARN, myName, direction, otherName, fmt, args);
	}
	
	public void error(String myName, int direction, String otherName, String fmt, Object... args) {
		log(ERROR, myName, direction, otherName, fmt, args);
	}
	
	public void fatal(String myName, int direction, String otherName, String fmt, Object... args) {
		log(FATAL, myName, direction, otherName, fmt, args);
	}

	/**
	 * 记录日志
	 * @param level 此行日志的日志等级。如果没有设置LogFilter，则仅当此参数的值>=设定的LogLevel时，日志才被写入。
	 *        如果设置了LogFilter，则以LogFilter的返回值为准，决定是否写入日志。
	 * @param myName 日志记录者的名字，由应用程序定义。通常可以用进程名、模块名甚至类名。
	 * @param direction 输入输出方向，可以为DI_INPUT, DI_OUTPUT,DI_INTERNAL
	 * @param otherName 对方的名称。如果direction为DI_INTERNAL，则此参数无效
	 * @param fmt 格式化的格式串。 fmt和args，请参见JDK String.format 函数说明。
	 * @param args 一系列要格式化的参数。
	 */
	public void log(int level, String myName, int direction, String otherName, 
					String fmt, Object... args) {
		// 1. 判断是否可以记录此日志
		if (logFilter != null) {	// 如果logFilter 存在，则以LogFilter的返回值作为是否记日志的依据
			boolean bLog = logFilter.canLog(level, myName, direction, otherName, fmt, args);
			if (!bLog) {	// 不允许记录
				return;
			}
		} else {	// LogFilter 不存在时，以LogLevel作为是否记日志的依据
			if (level < this.logLevel) {
				return;
			}
		}
		
		// 2. 判断日志文件是否已经打开
		Date now = new Date();	// 当前时间
		if (bFile) {	// 写入文件
			synchronized(this) {
				if (fileWriter == null) {	// 文件还没有打开
					openLogFile(now);
				} else {					// 判断是否日期发生了改变
					String sDate = dateFormat.format(now);
					if (!sDate.equals(logFileDate)) {	// 日期不同，需更换文件
						openLogFile(now);
					}
				}
			}
		}
		
		// 3. 生成日志的文本串
		String sText = getLogText(now, level, myName, direction, otherName, fmt, args);
		
		// 4. 写入日志
		synchronized(this) {
			try {
				if (bConsole) {
					System.out.print(sText);
				}
				if (bFile) {
					fileWriter.write(sText);
					fileWriter.flush();
					curFileSize += sText.length();
					if (curFileSize > maxSizePerFile) {
						rollLogFile();	// 循环滚动文件
					}
				}
			} catch (Exception e) {
				throw new GeneralException(e);
			}
		}
	}

	// 打开日志文件
	private void openLogFile(Date now) {
		// 1. 创建日志文件的父目录
		int iPos = logFileName.lastIndexOf('/');
		if (iPos < 0) {
			iPos = logFileName.lastIndexOf('\\');
		}
		if (iPos > 0) {
			File f = new File(logFileName.substring(0, iPos));
			f.mkdirs();
		}
		
		// 2. 获取当前日志文件大小
		logFileDate = dateFormat.format(now);
		String fileName = logFileName + logFileDate + ".log";
		File f = new File(fileName);
		curFileSize = f.length();
		
		// 3. 追加方式打开文件
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			if (fileWriter != null) {
				fileWriter.close();
				fileWriter = null;
			}
			fileWriter = new FileWriter(fileName, true);
		} catch (Exception e) {
			throw new GeneralException(e);
		}
	}
	
	// 拼成日志的字符串
	// 格式为：2008-03-15 09:10:35 main INFO [src->dest] msgInfo
	private String getLogText(Date now, int level, String myName, int direction, String otherName, String fmt, Object... args) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String nowStr = sdf.format(now);
		String threadName = Thread.currentThread().getName();
		String levelName = getLevelName(level);
		String directionStr = getDirectionStr(myName, direction, otherName, sb);
		
		sb.setLength(0);
		sb.append(nowStr);
		sb.append(threadName);
		sb.append(' ');
		sb.append(levelName);
		sb.append(' ');
		sb.append(directionStr);
		sb.append(' ');
		sb.append(String.format(fmt, args));
		sb.append(lineSeparator);
		return sb.toString();
	}
	
	// 循环滚动文件
	private void rollLogFile() throws Exception {
		// 1. 关闭当前文件
		fileWriter.close();
		fileWriter = null;
		
		// 2. 滚动文件，覆盖旧的日志文件
		String fileNamePrefix = logFileName + logFileDate;
		if (fileCountPerDay > 1) {	// 多个文件循环滚动
			// 删掉最后一个文件
			File f = new File(fileNamePrefix + "_" + (fileCountPerDay-1) + ".log");
			if (f.exists()) {
				f.delete();
			}
			
			// 文件的序号依次增一
			for (int i = fileCountPerDay-2; i > 0; i--) {
				f = new File(fileNamePrefix + "_" + i + ".log");
				if (f.exists()) {
					f.renameTo(new File(fileNamePrefix + "_" + (i+1) + ".log"));
				}
			}
			f = new File(fileNamePrefix + ".log");
			f.renameTo(new File(fileNamePrefix + "_" + 1 + ".log"));
		} else {	// 每天一个文件
			File f = new File(fileNamePrefix + ".log");
			f.delete();
		}
		
		// 3. 打开新的日志文件
		curFileSize = 0;
		fileWriter = new FileWriter(fileNamePrefix + ".log");
	}
	
	private String getDirectionStr(String myName, int direction, String otherName, StringBuilder sb) {
		sb.setLength(0);
		sb.append('[');
		if (myName != null) {
			sb.append(myName);
			if (direction != DI_INTERNAL) {
				if (direction == DI_INPUT) {
					sb.append("<-");
				} else if (direction == DI_OUTPUT) {
					sb.append("->");
				}
				
				if (otherName != null) {
					sb.append(otherName);
				}
			}
		}
		sb.append(']');
		
		return sb.toString();
	}

	private String getLevelName(int logLevel) {
		switch (logLevel) {
			case DEBUG:
				return "DEBUG";
			case INFO:
				return "INFO";
			case WARN:
				return "WARN";
			case ERROR:
				return "ERROR";
			case FATAL:
				return "ERROR";
			case OFF:
				return "OFF";
			default:
				return "";
		}
	}
	
	// 获取异常的栈信息
	public static String getStackTrace(Exception e) {
		StringBuffer sb = new StringBuffer();
		sb.append(e);
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i< trace.length; i++) {
			sb.append("\n\tat " + trace[i]);
		}

		return sb.toString();
	}
	
	public static String dump(byte[] data, int offset, int length) {
		int end = offset + length;
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb.append("[HEX]  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f | 0123456789abcdef");
		sb.append("\n------------------------------------------------------------------------");
		boolean chineseCutFlag = false;
		for (int i = offset; i < end; i += 16) {
			sb.append(String.format("\n%04x: ", i - offset));
			sb2.setLength(0);
			for (int j = i; j < i + 16; j++) {
				if (j < end) {
					byte b = data[j];
					if (b >= 0) {	// Ascii
						sb.append(String.format("%02x ", b));
						if(b<32 || b>126) { // 不可见字符用"."显示
							sb2.append(".");
						} else {
							sb2.append((char)b);
						}
					} else {	// chinese
						if(j == i+15) { // 如果一行16个字节的最后字节是中文字符的前一个字节
							sb.append(String.format("%02x ", data[j]));
							chineseCutFlag = true;
							String s = new String(data, j, 2);
							sb2.append(s);
						} else if (j == i && chineseCutFlag) { // 如果一行16个字节的开始字节是中文字符的后一个字节
							sb.append(String.format("%02x ", data[j]));
							chineseCutFlag = false;
							String s = new String(data, j, 1);
							sb2.append(s);
						} else {
							sb.append(String.format("%02x %02x ", data[j], data[j+1]));
							String s = new String(data, j, 2);
							sb2.append(s);
							j++;
						}
					}
				} else {
					sb.append("   ");
				}
			}
			sb.append("| ");
			sb.append(sb2.toString());
		}
		return sb.toString();
	}
	
	public static String dump(byte[] data) {
		return dump(data, 0, data.length);
	}
	
	public static String dump(Object obj) {
		StringBuilder builder = new StringBuilder();
		ClassReflector reflector = new ClassReflector(obj);
		
		builder.append(reflector.getSimpleClassName());
		builder.append(" ==> ");
		
		int propertyCount = reflector.getPropertyCount();
		for (int i = 0; i < propertyCount; i++) {
			Object value = reflector.getPropertyValue(i);
			if (value != null && !value.getClass().getName().startsWith("java.lang.")) {
				continue;
			}
			
			if (i > 0) {
				builder.append(',');
			}
			
			builder.append(reflector.getPropertyName(i));
			builder.append('=');
			if (value != null) {
				builder.append(value.toString());
			} else {
				builder.append("null");
			}
		}
		
		return builder.toString();
	}
	
	private int logLevel = INFO;	// 日志级别
	private String logFileName = "output";	// 日志文件名
	private FileWriter fileWriter = null;
	private long curFileSize = 0;			// 当前日志文件大小
	private LogFilter logFilter = null;		// 日志过滤器
	private boolean bFile = true;			// 日志是否输出到文件
	private boolean bConsole = true;		// 日志是否输出到控制台
	
	private long maxSizePerFile = 300*1024*1024;	// 单个日志文件的大小
	private int fileCountPerDay = 3;				// 日志文件的个数，多个日志文件可以循环滚动，覆盖最旧的文件
	
	private String lineSeparator = System.getProperty("line.separator");	// 换行符
	private SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyyMMdd");
	private String logFileDate;	// log文件命名的日期，如_20080130ڣ���_20080130
}

package marst.component;
/**
 * 常量声明
 * @author raogang
 *
 */
public class FinalString {
	/**
	 * 数据库连接
	 */
	public static final String DB_DRIVER="dbdriver";	//连接驱动
	public static final String DB_URL="dburl";	//连接URL
	public static final String DB_USERNAME="dbusername";	//连接用户名
	public static final String DB_PASSWORD="dbpassword";	//密码
	public static final String DB_DATABASE="dbdatabase";	//数据库名
	public static final String DB_CONNECT_COUNT="dbconnectCount";	//连接数
	public static final String DB_RECONECT_INTERVAL="dbreconnectInterval";//重连时间
	public static final String DB_WAIT_TIMEOUT="dbwaitTimeout";	//超时时间
	
	/**
	 * 日志文件字段
	 */
	public static final String LG_FILENAME="logfilename";	//日志文件名
	public static final String LG_FILEMAXSIZE="logfilemaxsize";	//文件大小
	public static final String LG_FILECOUNT="filecountperday";	//每日生成文件个数，如多出次数，自动覆盖
	
	//分页默认
	public static final String PG_PAGEINDEX="1";	//页码
	public static final String PG_PAGESIZE="30"; //每页数量
	//男女
	public static final String MALE="0";	//男
	public static final String FEMALE="1";	//女
	
	//允许不允许
	public static final String ALLOW="0";	//允许
	public static final String NOTALLOW="1";	//不允许
	
	//权限or角色
	public static final int ACCOUNT_AUTH=0; 	//菜单
	public static final int ACCOUNT_ROLE=1; 	//角色
	
	//用户类型
	public static final int ACCOUNT_ADMIN=1; 	//超级管理员
	public static final int ACCOUNT_MANAGER=2; //管理员
	public static final int ACCOUNT_OPERATOR=3; //普通用户
}

package marst.util;
/**
 * 字符串格式化工具
 * @author taddy
 *
 */
public class ComStringUtil {
	/**
	 * 非空判断
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null || str.length()==0 || str.equals("")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断字符串是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str){
		try {  
            int num=Integer.valueOf(str);//把字符串强制转换为数字  
            return true;//如果是数字，返回True  
        } catch (Exception e) {  
            return false;//如果抛出异常，返回False  
        }  
	}
	/**
	 * 判断字符串是否为小数
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str){
		try {  
            double num=Double.valueOf(str);//把字符串强制转换为数字  
            return true;//如果是数字，返回True  
        } catch (Exception e) {  
            return false;//如果抛出异常，返回False  
        }  
	}
}

package marst.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式化工具类
 * @author taddy
 *
 */
public class ComDateUtil {
	public static final String DATE_PATTERN_LONG = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	public static Date parseDate(String str, String pattern){  
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        Date date = null;  
        if(!ComStringUtil.isEmpty(str)){  
            try {  
                date = sdf.parse(str);  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
        }else{  
            throw new RuntimeException("字符串为空，不能转换成日期");  
        }  
        return date;  
    }
	public static Date parseDate(String str){  
        return parseDate(str, ComDateUtil.DATE_PATTERN);  
    }  
      
    public static Date parseLongDate(String str){  
        return parseDate(str, ComDateUtil.DATE_PATTERN_LONG);  
    }  
      
    public static String date2String(Date date, String pattern){  
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
        return sdf.format(date);  
    }  
      
    public static String Date2String(Date date){  
        return date2String(date, ComDateUtil.DATE_PATTERN);  
    }  
      
    public static String Date2LongString(Date date){  
        return date2String(date, ComDateUtil.DATE_PATTERN_LONG);  
    }  
}

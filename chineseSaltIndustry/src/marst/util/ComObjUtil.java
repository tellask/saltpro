package marst.util;

import java.util.Collection;
import java.util.Map;

/**
 * 对象格式化工具
 * @author Administrator
 *
 */
public class ComObjUtil {
	/**
	 * 非空判断
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		if(obj==null){
			return true;
		}else{
			return false;
		}
	}
	public static int getLength(Object[] obj){  
        int length = 0;  
        length = obj == null ? 0 : obj.length;  
        return length;  
    }
	public static int getSize(Collection<?> coll){  
        int size = coll == null ? 0 : coll.size();  
        return size;  
    }  
      
    public static int getSize(Map<?,?> map){  
        int size = map == null ? 0 : map.size();  
        return size;  
    }
}

package marst.util.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dbcom.DataSet;

import marst.util.staticlass.ClassReflector;
import marst.util.staticlass.DataType;

public class JsonConverter {
	/**
	 * 将Json 串转换为Java对象
	 * @param jsonStr 待转换的Json串，可以是内置类型、数组或者对象，
	 * 				  也可以是上述三种的任意组合
	 * @param className 目标Java对象的类型名
	 * @return 返回转换后的Java对象，可能是内置类型、数组、集合类型或者对象
	 * @exception 如果转换过程出现异常，统一抛出GeneralException 异常
	 */
	public static Object json2Obj(String jsonStr, String className) {
		Class cls = ClassReflector.newClass(className);
		return json2Obj(jsonStr, cls);
	}
	
	/**
	 * 将Json 串转换为Java对象
	 * @param jsonStr 待转换的Json串，可以是内置类型、数组或者对象，
	 * 				  也可以是上述三种的任意组合
	 * @param cls 目标Java对象的类
	 * @return 返回转换后的Java对象，可能是内置类型、数组、集合类型或者对象
	 * @exception 如果转换过程出现异常，统一抛出GeneralException 异常
	 */
	public static <T> T json2Obj(String jsonStr, Class<T> cls) {
		
		// 1. 如果是内置类型，则直接进行数据类型转换即可
		String objTypeName = cls.getName();
		int objType = DataType.getDataType(objTypeName);
		if (DataType.isSimpleType(objType)) {	// 简单类型
			jsonStr = jsonStr.trim();
			jsonStr = delQuote(jsonStr);	// 去掉引号
			return (T)DataType.toType(jsonStr, DataType.DT_String, objType);
		}
		
		// 2. 解析jsonStr, 切出各个属性
		// 2.1 合并成单行
		jsonStr = jsonStr.replace("\n", "");
		//jsonStr = jsonStr.replace("\t", "");
		jsonStr = jsonStr.trim();
		// 2.2 分解出各个属性
		List<JsonProp> propList = splitProperties(jsonStr);
		
		// 3. 创建目标对象
		Object obj = null;
		switch (objType) {
		case DataType.DT_Array:	// 数组的创建需要知道数组元素的类型和数组长度，因此后续再创建
			break;
		case DataType.DT_List:
			obj = ClassReflector.newInstance("java.util.ArrayList");
			break;
		case DataType.DT_Set:
			obj = ClassReflector.newInstance("java.util.HashSet");
			break;
		case DataType.DT_Map:
			obj = ClassReflector.newInstance("java.util.Hashtable");
			break;
		default:
			obj = ClassReflector.newInstance(cls);
		}
		
		// 4. 处理对象的场景
		if (objType == DataType.DT_UserDefine) {	// 对象
			ClassReflector reflector = new ClassReflector(obj);
			// 逐个属性向obj 赋值
			for (JsonProp p : propList) {
				String propTypeName = reflector.getPropertyType(p.propName);
				Object propObj = json2Obj(p.jsonPropValue, propTypeName);
				reflector.setPropertyValue(p.propName, propObj);
			}
			return (T)obj;
		}
		
		// 5. 处理数组/集合的场景
		// 5.1 获取元素的类型
		String elementTypeName = DataType.getElementTypeName(objTypeName);
		Class elementCls = ClassReflector.newClass(elementTypeName);
		int index = 0;
		// 5.2 解析每个元素对象，放入数组或集合中
		for (JsonProp p : propList) {
			// 5.2.1 解析每个元素对象
			Object elementObj = json2Obj(p.jsonPropValue, elementCls);
			
			// 5.2.2 将元素对象放入数组或集合中
			switch(objType) {
				case DataType.DT_Array:
					if (obj == null) {
						obj = ClassReflector.newArray(elementCls, propList.size());
					}
					ClassReflector.setArrayElement(obj, index++, elementObj);
					break;
				case DataType.DT_List:
					((List)obj).add(elementObj);
					break;
				case DataType.DT_Map:
					((Map)obj).put(p.propName, elementObj);
					break;
				case DataType.DT_Set:
					((Set)obj).add(elementObj);
					break;
			}
		}
		
		// 返回转换后的集合对象
		return (T)obj;
	}

	/**
	 * 将Java对象转换为JSON字符串
	 * @param obj 待转换的Java对象
	 * @return 返回转换后的JSON字符串
	 */
	public static String obj2Json(Object obj) {
		int objDataType = DataType.getDataType(obj);
		if (DataType.isSimpleType(objDataType)) {
			switch (objDataType) {
				case DataType.DT_Character:
				case DataType.DT_String:
				case DataType.DT_Date:
				case DataType.DT_Time:
				case DataType.DT_DateTime:
					obj = DataType.toType(obj, objDataType, DataType.DT_String);
					return "\"" + obj.toString() + "\"";
				default:
					return "\"" +obj.toString()+ "\"";
			}
		}
		
		// 处理复杂类型
		StringBuilder sb = new StringBuilder();
		switch (objDataType) {
		case DataType.DT_Array:
			sb.append('[');
			int iLen = Array.getLength(obj);
			for (int i = 0; i < iLen; i++) {
				sb.append("\"");	//加入双引号
				sb.append(obj2Json(Array.get(obj, i)));
				sb.append("\"");	//加入双引号
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append(']');
			break;
		case DataType.DT_List:
			sb.append('[');
			List list = (List)obj;
			for (Object o : list) {
				sb.append(obj2Json(o));
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append(']');
			break;
		case DataType.DT_Set:
			sb.append('[');
			Set set = (Set)obj;
			Iterator it = set.iterator();
			while (it.hasNext()) {
				sb.append(obj2Json(it.next()));
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append(']');
			break;
		case DataType.DT_Map:
			sb.append("{");
			Set entrySet = ((Map)obj).entrySet();
			Iterator iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry)iterator.next();
				Object key = entry.getKey();
				sb.append("\"");	//加入双引号
				sb.append(key.toString());
				sb.append("\"");	//加入双引号
				sb.append(" : ");
				
				Object value = entry.getValue();
				sb.append(obj2Json(value));
				sb.append(", ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("}");
			break;
		case DataType.DT_UserDefine:
			sb.append("{");
			ClassReflector reflector = new ClassReflector(obj);
			int iCount = reflector.getPropertyCount();
			for (int i = 0; i < iCount; i++) {
				String propName = reflector.getPropertyName(i);
				sb.append("\"");	//加入双引号
				sb.append(propName);
				sb.append("\"");	//加入双引号
				sb.append(" : ");
				
				Object propValue = reflector.getPropertyValue(i);
				sb.append(obj2Json(propValue));
				sb.append(",\n");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("}");
			break;
		case DataType.DT_DataSet:
			sb.append("[");
			DataSet reDataSet=(DataSet)obj;
			while(reDataSet.next()){
				sb.append("{");
				for(int m=0;m<reDataSet.getColumnCount();m++){
					sb.append("\"");	//加入双引号
					sb.append(m);
					sb.append("\"");	//加入双引号
					sb.append(" : ");
					sb.append("\"");	//加入双引号
					sb.append(reDataSet.getString(m));
					sb.append("\"");	//加入双引号
					sb.append(",");
				}
				sb.delete(sb.length() - 1, sb.length());
				sb.append("}, ");
			}
			sb.delete(sb.length() - 2, sb.length());
			sb.append("]");
			break;
		default:
			if(obj==null){
				obj="";
			}
			return "\"" +obj.toString()+ "\"";
		}
		
		return sb.toString();
	}
	/**
	 * 将json 字符串分解为一系列属性结构
	 * @param jsonStr 需要解析的json 字符串
	 * @return 返回一个List，其中元素类型为JsonProp。当无任何属性时，返回List的size为0.
	 */
	public static List<JsonProp> splitProperties(String jsonStr) {
		List<JsonProp> propList = new ArrayList<JsonProp>();
		
		jsonStr = jsonStr.trim();
		char cBegin = jsonStr.charAt(0);
		char cEnd = jsonStr.charAt(jsonStr.length()-1);
		if ((cBegin == '[' && cEnd==']') || (cBegin == '{' && cEnd=='}')) {
			jsonStr = jsonStr.substring(1, jsonStr.length()-1).trim() + ",";
		} else {
			jsonStr += ",";
		}
			
		String propName = null;
		String propValue = null;
		int iBegin = 0;
		
		int strLen = jsonStr.length();
		boolean bQuote = false;
		char chQuote = '"';
		for (int i = 0; i < strLen; i++) {
			char ch = jsonStr.charAt(i);
			// 跳过引号括起来的字符串
			if (!bQuote) {
				if (ch == '"' || ch == '\'') {	// 引号开始
					bQuote = true;
					chQuote = ch;
					continue;
				}
			} else {
				if (ch == chQuote) {			// 引号结束
					if (jsonStr.charAt(i-1) != '\\') {	// 没有转义符
						bQuote = false;
					}
				}
				
				continue;
			}
			
			switch (ch) {
				case ':':	// 属性名
					propName = jsonStr.substring(iBegin, i).trim();
					iBegin = i+1;
					break;
				case ',': 
					propValue = jsonStr.substring(iBegin, i).trim();
					AddJsonProperty(propName, propValue, propList);
					propName = null;
					iBegin = i+1;
					break;
				case '[':
				case '{':
					char rightBracket = ']';
					if (ch == '{') {
						rightBracket = '}';
					}
					propValue = parseComplexProperty(jsonStr, iBegin, i, ch, rightBracket);
					AddJsonProperty(propName, propValue.trim(), propList);
					propName = null;
					i = jsonStr.indexOf(',', iBegin + propValue.length());
					if (i < 0) {	// 后面没有其它属性了
						i = jsonStr.length();
					}
					iBegin = i+1;
					break;
			}
		}
		
		return propList;
	}
	
	/**
	 * 从Json串中解析一个数组或对象属性，支持嵌套
	 * @param jsonStr 待解析的Json字符串
	 * @param iBegin 解析的开始位置
	 * @param iBracketPos 第一个括号开始位置
	 * @param leftBracket 左括号
	 * @param rightBracket 右括号
	 * @return 返回解析后的属性串，不对其进行trim().
	 */
	private static String parseComplexProperty(String jsonStr, int iBegin, int iBracketPos, 
			char leftBracket, char rightBracket) {
		int iBracketCount = 0;
		int strLen = jsonStr.length();
		boolean bQuote = false;
		char chQuote = '"';
		for (int i = iBracketPos + 1; i < strLen; i++) {
			char ch = jsonStr.charAt(i);
			// 跳过引号括起来的字符串
			if (!bQuote) {
				if (ch == '"' || ch == '\'') {	// 引号开始
					bQuote = true;
					chQuote = ch;
					i++;
					continue;
				}
			} else {
				if (ch == chQuote) {			// 引号结束
					if (jsonStr.charAt(i-1) != '\\') {	// 没有转义符
						bQuote = false;
					}
				}
				
				i++;
				continue;
			}
			
			
			if (ch == leftBracket) {	// 左括号
				iBracketCount--;
			} else if (ch == rightBracket) {	// 右括号
				iBracketCount++;
				if (iBracketCount == 1) {	// 属性结束
					String attributeStr = jsonStr.substring(iBegin, i+1);
					return attributeStr;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 将Json 属性封装成JsonProp对象，放入propList 中。
	 * 如果属性值为空，或者为null，则忽略此属性。
	 * 另外，如果属性值前后带引号，则引号将被去掉。
	 * @param propName 属性名
	 * @param propValue 属性值
	 * @param propList 目标List
	 */
	private static void AddJsonProperty(String propName, String propValue, List<JsonProp> propList) {
		if (propValue == null || propValue.length() < 1 || propValue.equalsIgnoreCase("null")) {
			return;
		}
				
		char ch = propValue.charAt(0);
		int propType = JsonProp.SIMPLE;
		if (ch == '[') {
			propType = JsonProp.ARRAY;
		} else if (ch == '{') {
			propType = JsonProp.OBJECT;
		} else {
			propValue = delQuote(propValue);
		}
		
		if (propName != null) {
			propName = delQuote(propName);
		}
		JsonProp jsonProperty = new JsonProp(propName, propType, propValue);
		propList.add(jsonProperty);
	}
	
	/**
	 * 去掉字符串的前后引号
	 * @param strValue 要处理的字符串
	 * @return 返回去掉前后引号的字符串
	 */
	public static String delQuote(String strValue) {
		char ch = strValue.charAt(0);
		if (ch == '\'' || ch == '"') {
			strValue = strValue.substring(1, strValue.length()-1);
		}
		return strValue;
	}
}

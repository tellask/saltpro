package marst.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import marst.component.FinalString;

/**
 * 加载properties文件
 * @author raogang
 *
 */
public class LoadProperties {
	private static Properties propertie;
	private FileOutputStream outputFile;
	private static String filePath="/project.properties";
	static{
		propertie = new Properties(); 
		try{
			InputStream inputFile = LoadProperties.class.getResourceAsStream(filePath); 
			propertie.load(inputFile); 
			inputFile.close();  
		}catch(FileNotFoundException ex){ 
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");  
			ex.printStackTrace(); 
		}catch(IOException ex) {System.out.println("装载文件--->失败!"); 
			ex.printStackTrace(); 
		}
	}
	/**
	 * 拿到value值
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		String valueStr="";
		if(propertie.containsKey(key)){
			valueStr=propertie.getProperty(key);
		}
		return valueStr;
	}
	/**
	 * 清楚key值
	 */
	public void clear(){
		propertie.clear();
	}
	/**
	 * 改变或添加一个key和value对，如果key存在则改变,不存在则添加
	 * @param key
	 * @param value
	 */
	public void setValue(String key,String value){
		propertie.setProperty(key, value);
	}
	/**
	 * 将更改后的文件数据保存在文件中，文件可不存在
	 * @param fileName	文件路径+文件名
	 * @param description	文件描述
	 */
	public void saveFile(String fileName,String description){
		 try {
			outputFile = new FileOutputStream(fileName);
			 propertie.store(outputFile, description); 
			 outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		LoadProperties loadProperties=new LoadProperties();
		String db_driver=loadProperties.getValue(FinalString.DB_DRIVER);
		String db_url=loadProperties.getValue(FinalString.DB_URL);
		String db_database=loadProperties.getValue(FinalString.DB_DATABASE);
		String db_usernmae=loadProperties.getValue(FinalString.DB_USERNAME);
		String db_pwd=loadProperties.getValue(FinalString.DB_PASSWORD);

		//System.out.println("result:"+result);
		System.out.println("db_driver="+db_driver+"\n"+
				"db_url="+db_url+"\n"+
				"db_database="+db_database+"\n"+
				"db_usernmae="+db_usernmae+"\n"+
				"db_pwd="+db_pwd+"\n");
		
	}
}

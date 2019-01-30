/*
 *  @(#)PropertiesUtil.java  1.0   2012-12-25
 *  Copyright (c) 2012, 北京优听信息技术有限公司
 */
package my.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 类或接口：PropertiesUtil<br>
 * 功能描述：读写属性文件的工具类
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys 
 */
public class PropertiesUtil {
	/**
	 * 成员方法：getProperty(String key,String fromFile)<br>
	 * 功能描述：获取属性文件中的指定属性值
	 * @param   key - 属性名
	 * @param   fromFile - 属性文件名
	 * @return  String - &{返回值说明}
	 * @throws Exception 
	 * @throws  无
	 */
	public static String getProperty(String key,String fromFile) {
		String result="";
		Properties properties = null;
		File file=null;
		FileInputStream fileInputStream=null;
		int[] sleepTime = {100,300,600,0};
		for (int i = 0; i < 4; i++) {
		try {
				properties = new Properties();
				file=new File(fromFile);
				fileInputStream=new FileInputStream(file);
				properties.load(fileInputStream);
				if(properties.getProperty(key)!=null){
					result=properties.getProperty(key).toString().trim();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(sleepTime[i]);
				} catch (InterruptedException e1) {
				}
				continue;
			} finally{
				try{
					if(fileInputStream!=null) fileInputStream.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	
	public static String getPropertyEncode(String key,String fromFile,String encode) {
		String result="";
		Properties properties = null;
		File file=null;
		InputStreamReader fileInputStream=null;
		int[] sleepTime = {100,300,600,0};
		for (int i = 0; i < 4; i++) {
		try {
				properties = new Properties();
				file=new File(fromFile);
				fileInputStream=new InputStreamReader(new FileInputStream(file),encode);
				properties.load(fileInputStream);
				if(properties.getProperty(key)!=null){
					result=properties.getProperty(key).toString().trim();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(sleepTime[i]);
				} catch (InterruptedException e1) {
				}
				continue;
			} finally{
				try{
					if(fileInputStream!=null) fileInputStream.close();
				}catch(Exception e){}
			}
		}
		return result;
	}
	
	/**
	 * 成员方法：getProperty(String key,String fromFile,String defaultValue)<br>
	 * 功能描述：获取属性文件中的指定属性值
	 * @param   key - 属性名
	 * @param   fromFile - 属性文件名
	 * @param	defaultValue - 默认值
	 * @return  String - &{返回值说明}
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws  无
	 */
	public static String getProperty(String key,String fromFile,String defaultValue){
		String result="";
		Properties properties = null;
		File file=null;
		FileInputStream fileInputStream=null;
		int[] sleepTime = {100,300,600,0};
		for (int i = 0; i < 4; i++) {
			try {
				properties = new Properties();
				file=new File(fromFile);
				fileInputStream=new FileInputStream(file);
				properties.load(fileInputStream);
				result=properties.getProperty(key,defaultValue).toString().trim();
				break;
			} 
			catch (Exception e) {
				mylog.Err("getProperty:"+e.getMessage());
				mylog.ErrStackTrace(e);
				try {
					Thread.sleep(sleepTime[i]);
				} catch (InterruptedException e1) {
				}
				continue;
			} finally{
				try{
					if(fileInputStream!=null) fileInputStream.close();
				}catch(Exception e){}
			}
		}
		if(result==null || result.trim().length()<=0) result = defaultValue;
		return result;
	}
	/**
	 * 成员方法：getProperties(String fromFile)<br>
	 * 功能描述：获取属性文件中的所有属性，以Map形式返回
	 * @param   fromFile - 属性文件名
	 * @return  Map - Map形式的属性集合
	 * @throws  无
	 */	
	public static Map<String,String> getProperties(String fromFile){
		Map<String,String> map=new HashMap<String,String>();
		Properties properties = null;
		File file=null;
		FileInputStream fileInputStream=null;
		try{
			properties = new Properties();
			file=new File(fromFile);
			fileInputStream=new FileInputStream(file);
			properties.load(fileInputStream);
			Enumeration<Object> keys=properties.keys();
			while(keys.hasMoreElements()) {
				String key=(String)keys.nextElement();
				map.put(key,(String)properties.get(key));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(fileInputStream!=null) fileInputStream.close();
			}catch(Exception e){}
		}
		return map;
	}
}

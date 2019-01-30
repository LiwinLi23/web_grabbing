/*
 *  @(#)JsonUtil.java  1.0   2012-12-23
 *  Copyright (c) 2012, 北京优听信息技术有限公司
 */
package my.function;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * 类或接口：JsonUtil<br>
 * 功能描述：Json工具类
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys 
 */
public class JsonUtil {

	private static ObjectMapper mapper=new ObjectMapper();

	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null.
	 * 如果JSON字符串为"[]",返回空集合.
	 * 
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
	 * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) 
	{
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	  public static <T> T fromJson2(String jsonString, Class<T> clazz) {
		    if (StringUtils.isEmpty(jsonString)) {
		      return null;
		    }
		    try
		    {
		      mapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		      return mapper.readValue(jsonString, clazz);
		    } catch (IOException e) {
		      e.printStackTrace();
		    }return null;
		  }


	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null.
	 * 如果JSON字符串为"[]",返回空集合.
	 * 
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
	 * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
	 */
	public static <T> T fromJsonFile(File jsonFile, Class<T> clazz) {
		try {
			return mapper.readValue(jsonFile, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
	 */
	public static String toJson(Object object) {

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
	
	public static boolean isRealJson(String jsonString){
		if(jsonString !=null && jsonString.length()>0){
			jsonString = jsonString.trim() ;
			if((jsonString.startsWith("{") || jsonString.startsWith("[")) && (jsonString.endsWith("}") || jsonString.endsWith("]"))){
				return true ;
			}
		}
		return false ;
		
	}
}
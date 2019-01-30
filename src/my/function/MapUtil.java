/*
 *  @(#)MapUtil.java  1.0   2016-5-27
 *  Copyright (c) 2013, 北京优听信息技术有限公司
 */
package my.function;

import java.util.Map;

/**
 * 类或接口：MapUtil<br>
 * 功能描述：&{类/接口功能说明}
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys 
 */
public class MapUtil {
	public static int getInt(Map map,Object key,int defaultValue){
		if(map==null || !map.containsKey(key)) return defaultValue;
		int result=defaultValue;
		try{
			result=Integer.parseInt(map.get(key).toString());
		}catch(Exception e){
			
		}
		return result;
	}
	@SuppressWarnings("rawtypes")
	public static String getString(Map map,Object key,String defaultValue){
		if(map==null || !map.containsKey(key)) return defaultValue;
		String result=defaultValue;
		try{
			result=map.get(key).toString();
		}catch(Exception e){}
		return result;
	}
	public static String mapToString(Map map){
		StringBuffer sb = new StringBuffer() ;
		Object[] arr = map.keySet().toArray() ;
		for(int i=0 ;i<arr.length;i++){
			sb.append(arr[i].toString()+"="+map.get(arr[i].toString())+"&") ;
		}
		if(sb.charAt(sb.length()-1)=='&'){
			sb.deleteCharAt(sb.length()-1) ;
		}
		return sb.toString() ;
	}
}

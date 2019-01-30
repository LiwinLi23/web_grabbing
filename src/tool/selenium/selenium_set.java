package tool.selenium;

import java.util.Map;

import my.function.PropertiesUtil;
import my.function.pb;

public class selenium_set {
	
	public static String chrome_driver="C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe"; //你浏览器驱动的全路径
	public static String userdatadir="C:\\Users\\taohuosheng\\AppData\\Local\\Google\\Chrome\\User Data";
	
	//public static String chrome_driver="C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe";
	//public static String userdatadir="C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data";
	
	static 
	{
		Map configs = PropertiesUtil.getProperties(pb.my_runpath()+System.getProperty("file.separator")+"Config.properties");
		if (configs.get("chrome_driver")!=null)
		chrome_driver =(String) configs.get("chrome_driver");
		if (configs.get("userdatadir")!=null)
		userdatadir=(String) configs.get("userdatadir");
	}
	
	

}

package my.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class pb {
	
	public static boolean bRun=true;
	
	public static String order_PostUrl="http://127.0.0.1:8086/Order";
	public static String sKey="WYapp-2016-QGFYDFi";
	public static String Token="ABCDEFG";
	public static String sDesKey="zfx2016#";
	
	public static String m_RunPath="";
	public static String surldb;
	public static String suser;
	public static String spassword;
	
	//社区数据库连接
	public static String surldb_mysql;
	public static String suser_mysql;
	public static String spassword_mysql;
	
	//公共函数列表
	public static Set<String> set_Public_function = new HashSet<String>();
	
	public static HashMap<String , String> mapToken = new HashMap<String , String>();  
	
	public static synchronized void mapToken_add(String sMobile,String sToken)
	{
		mapToken.put(sMobile, sToken);
	}
	
	public static synchronized String mapToken_get(String sMobile)
	{
		return mapToken.get(sMobile);
	}
	
	public static synchronized String mapToken_del(String sMobile)
	{
		return mapToken.remove(sMobile);
	}


	public static String deletehm(String stime)
	{
		if (stime==null) return "";
		if (stime.length()<=19)
		{
			return stime;			
		}
		else
		{
			return stime.substring(0, 19);
		}
	}
	
	//判断文件是否存在
	public static boolean FileExists(String sfile)
	{
		File file = new File(sfile);
		if (file.exists()) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//判断文件夹
	public static boolean DirExists(String sfile)
	{
		File file = new File(sfile);
		if (file.exists()) 
		{
			if (file.isDirectory())
			return true;
			else
				return false;	
		}
		else
		{
			return false;
		}
	}
	
	//判断文件是否存在
	public static boolean delfile(String sfile)
	{
		File file = new File(sfile);
		if (file.exists()) 
		{
			file.delete();
			return true;
		}
		else
		{
			return true;
		}
		
	}		
	
	public static String GetUUid()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	//获取唯一订单号
	public static int giOrderid=0;
	public static synchronized String GetOrderId()
	{
		giOrderid++;
		if (giOrderid>999) giOrderid=1;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String orderid=sdf.format(new Date())+String.valueOf(giOrderid);
		return orderid;
	}
	
	public  static int difftime(Date t)
	{
		if (t==null) return 2147483647;
		Date tnow=new Date();    
		//System.out.println(((tnow.getTime()-t.getTime())/1000));
    	return (int) Math.abs((tnow.getTime()-t.getTime())/1000);    
    	
	}
	
	//判断是否是有效时间(时间戳)
		public  static boolean CheakTimeStamp(String TimeStamp)
		{
			if (TimeStamp.length()!=14) return false;
			
		    try {
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
				Date date = sdf.parse(TimeStamp);
				if (difftime(date)>30*60)
				{
					return false;
				}
				else
				{
					return true;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 	
		}
	
	/*public static String utf8encode(String sMsg)
    {
   	 StringBuffer sb = new StringBuffer();  
   	 sb.append(sMsg);  
   	 String utf8str = "";  
   	 try {  
   	    utf8str = new String(sb.toString().getBytes("UTF-8"));  
   	    } catch (UnsupportedEncodingException e) {  
   	    e.printStackTrace();  
   	 }
   	 return utf8str;
    }*/
	
	public static String utf8encode(String sMsg)
    {
   	 String utf8str = "";  
   	 try { 
   		utf8str= new String (sMsg.getBytes("UTF-8"),"GBK");
   	    } catch (UnsupportedEncodingException e) {  
   	    e.printStackTrace();  
   	 }
   	 return utf8str;
    }
	
	public static String utf8decode(String utf8str)
	{
		String str="";
		try {
			//String unicode = new String(utf8str.getBytes(),"UTF-8");   
			//str = new String(unicode.getBytes("GBK"));  
			str = new String(utf8str.getBytes("GBK"),"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace(); 
		}
		return str;
	}
	
	
	public static int atoi(String s)
    {
		if (s==null) return 0;
    	String NUMBER_REGEX="[\\+\\-]??\\d+"; 
    	int idef=0;
    	
    	try
    	{
    		if (s.matches(NUMBER_REGEX))
    		{
    			int iret= Integer.valueOf(s);
    			return iret;
    		}
    	}
    	catch(NumberFormatException e)
		{
			return idef; 
		}
    	return idef;
    }
	
	public static long atol(String s)
    {
		if (s==null) return 0;
    	String NUMBER_REGEX="[\\+\\-]??\\d+"; 
    	int idef=0;
    	
    	try
    	{
    		if (s.matches(NUMBER_REGEX))
    		{
    			long iret= Long.valueOf(s);
    			return iret;
    		}
    	}
    	catch(NumberFormatException e)
		{
			return idef; 
		}
    	return idef;
    }
    
	public static double atof(String s)
    {
    	if (s==null) return 0;
		String NUMBER_REGEX="\\-??\\d+(.\\d+)??";  
    	
    	double ddef=0;
    	try
    	{
    		if (s.matches(NUMBER_REGEX))
    		{
    			double dret= Double.parseDouble(s);
    			return dret;
    		}
    	}
    	catch(NumberFormatException e)
		{
			return ddef; 
		}
    	return ddef;
    	
    }
	
	//增加多少秒
	public static Date dateaddsecond(Date dt,int isecond)
	{
		Calendar dttime = Calendar.getInstance();
		dttime.setTime(dt); 
		dttime.add(Calendar.SECOND, isecond);
		return dttime.getTime(); 	
	}
	
	//
	public static String datetostr(Date dt)
    {
		SimpleDateFormat sdf=null;
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(dt);	
    }
	
	//
	public static Date strtodatedefnow(String s)
    {
		SimpleDateFormat sdf=null;
		switch (s.length()) {
		case 10:
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			break;
		case 19:
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			break;

		default:
			return new Date();
			//break;
		}
		
		try {
			Date dts = sdf.parse(s);
			return dts;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}  	
    }
	
	public static String deldecimal(String sZero)
	{
		int iPoint=sZero.indexOf(".");
		if (iPoint<0) return sZero;
		for (int i=sZero.length();i>0;i--)
		{
			if (sZero.charAt(i-1)!='0')
			{
				if (sZero.charAt(i-1)=='.')
					return sZero.substring(0,i-1);
				else
					return sZero.substring(0,i);
			}
		}
		return sZero;
	}
	
	
	//获取当前运行路径
	public static String my_runpath()
	{
		//获取当前运行路径
    	java.net.URL url = pb.class.getProtectionDomain().getCodeSource().getLocation();  
    	String filePath = null;  
    	try {  
    	filePath = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码  
    	} catch (Exception e) {  
    	e.printStackTrace();  
    	}  
    	if (filePath.endsWith(".jar")) {// 可执行jar包运行的结果里包含".jar"  
    	// 截取路径中的jar包名  
    	 filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
    	}  
    	     
    	File file = new File(filePath);  

    	// /If this abstract pathname is already absolute, then the pathname  
    	// string is simply returned as if by the getPath method. If this  
    	// abstract pathname is the empty abstract pathname then the pathname  
    	// string of the current user directory, which is named by the system  
    	// property user.dir, is returned.  
    	filePath = file.getAbsolutePath();//得到windows下的正确路径 
    	file.delete();
    	System.out.println("filePath:"+filePath);
    	return filePath;
	}
	
	public static String CreateLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (key.equals(""))continue;
			if (key.equals("sign"))continue;

			if (prestr.equals("")) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + "&" + key + "=" + value ;
			}
		}

		return prestr;
	}
	
	public static String CreateLinkStringNotNull(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (key.equals(""))continue;
			if (key.equals("sign"))continue;
			if (value==null)continue;
			if (value.equals(""))continue;

			if (prestr.equals("")) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + "&" + key + "=" + value ;
			}
		}

		return prestr;
	}
	
	

	
	/***
	 * MD5加码 生成32位md5码
	 */
	public static String MD5Str(String inStr){
		if (inStr.equals("")) return inStr;
		
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		//这里有编码问题
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	
	public static String MD5Encoder(String s, String charset) {
        try
        {
        	byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0;i < md.length; i++) {
            	int val = ((int)md[i]) & 0xff;
                if(val < 16){
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        }
         catch(Exception e) {
            return null;
        }

    }
	
	
	public static String MD5utf8Str(String inStr){
		if (inStr.equals("")) return inStr;
		
		/*byte[] utf8Bytes = s.getBytes(Charset.forName("utf-8"));   
byte[] gbkBytes = s.getBytes(Charset.forName("gbk"));  */
		
		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		//解决编码问题
		byte[] byteArray = inStr.getBytes(Charset.forName("utf-8"));
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	
	
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            /*Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }*/
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	            result=e.getMessage();
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }

	 public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
		//public static final String ALGORITHM_DES = "DES";
		
		/**
	     * DES算法，加密
	     *
	     * @param data 待加密字符串
	     * @param key  加密私钥，长度不能够小于8位
	     * @return 加密后的字节数组，一般结合Base64编码使用
	     * @throws InvalidAlgorithmParameterException 
	     * @throws Exception 
	     */
	    public static String encode(String key,String data) {
	    	if(data == null)
	    		return null;
	    	try{
		    	DESKeySpec dks = new DESKeySpec(key.getBytes());	    	
		    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		        //key的长度不能够小于8位字节
		        Key secretKey = keyFactory.generateSecret(dks);
		        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
		        AlgorithmParameterSpec paramSpec = iv;
		        cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);           
		        byte[] bytes = cipher.doFinal(data.getBytes()); 
		        return new BASE64Encoder().encode(bytes);  
		        //return byte2hex(bytes);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return "";
	    	}
	    }

	    /**
	     * DES算法，解密
	     *
	     * @param data 待解密字符串
	     * @param key  解密私钥，长度不能够小于8位
	     * @return 解密后的字节数组
	     * @throws Exception 异常
	     */
	    public static String decode(String key,String data) {
	    	if(data == null)
	    		return null;
	        try {
		    	DESKeySpec dks = new DESKeySpec(key.getBytes());
		    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	            //key的长度不能够小于8位字节
	            Key secretKey = keyFactory.generateSecret(dks);
	            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
	            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
	            AlgorithmParameterSpec paramSpec = iv;
	            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
	            //return new String(cipher.doFinal(hex2byte(data.getBytes())));
	            return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(data)));
	        } catch (Exception e){
	    		e.printStackTrace();
	    		return "";
	        }
	    }

	    
	    public static String encodeBASE64(String str,String charset) {
			if (null == charset) {
				charset = "UTF-8";
			}
			byte[] bytes;
			String value = null;
			try {
				bytes = str.getBytes(charset);
				BASE64Encoder encode = new BASE64Encoder();
				value = encode.encode(bytes);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value;
		}
	    
	    public static String decodeBASE64(String str,String charset) {
			if (null == charset) {
				charset = "UTF-8";
			}
			byte[] bytes;
			String value = null;
			try {
				bytes = str.getBytes(charset);
				BASE64Decoder decoder = new BASE64Decoder();
				bytes = decoder.decodeBuffer(str);
				value = new String(bytes,charset);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value;
		}

	    
		/**
		 * 二行制转字符串
		 * @param b
		 * @return
		 */
	    static String byte2hex(byte[] b) {
			StringBuilder hs = new StringBuilder();
			String stmp;
			for (int n = 0; b!=null && n < b.length; n++) {
				stmp = Integer.toHexString(b[n] & 0XFF);
				if (stmp.length() == 1)
					hs.append('0');
				hs.append(stmp);
			}
			return hs.toString().toUpperCase();
		}
	    
	    private static byte[] hex2byte(byte[] b) {
	        if((b.length%2)!=0)
	            throw new IllegalArgumentException();
			byte[] b2 = new byte[b.length/2];
			for (int n = 0; n < b.length; n+=2) {
			    String item = new String(b,n,2);
			    b2[n/2] = (byte)Integer.parseInt(item,16);
			}
	        return b2;
	    }
	    
	 // 加密
		public static String getBase64(String str) {
			byte[] b = null;
			String s = null;
			try {
				b = str.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b != null) {
				s = new BASE64Encoder().encode(b);
			}
			return s;
		}

		// 解密
		public static String getFromBase64(String s) {
			byte[] b = null;
			String result = null;
			if (s != null) {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					b = decoder.decodeBuffer(s);
					result = new String(b, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}
		
		//去除两头的逗号
		public static String deldh(String sin)
		{
			//String sout="";
			if (sin==null) return "";
			
			for (int i=sin.length()-1;i>=0;i--)
			{
				if (sin.charAt(i)!=',')
				{
					sin=sin.substring(0,i+1);
					break;
				}
			}
			
			for (int i=0;i<sin.length();i++)
			{
				if (sin.charAt(i)!=',')
				{
					sin=sin.substring(i,sin.length());
					break;
				}
			}
			
			return sin;
		}
		
		//从map获取string
		public static String getStrbyMap(Map<String, String> infoMap, String skey)
		{
			String value=infoMap.get(skey);
			if (value==null) value="";
			return value;
		}
		
		//获取一个随机数
		public static int getrandom(int x)
		{
			return (int)(Math.random()*x);
		}

}

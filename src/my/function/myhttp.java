package my.function;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;




public class myhttp {
	
	 /** 
     * 向指定URL发送GET方法的请求 
     * 
     * @param url 
     *            发送请求的URL 
     * @param param 
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。 
     * @return URL所代表远程资源的响应 
     * 		url=url+"&X-Up-Calling-Line-ID="+mobile;
		
		url=url+"&X-Channel-Code=014A00H";   //渠道代码
		url=url+"&X-Timestamp="+dtnow;  //时间搓
		url=url+"&X-Signature="+signature;  //签名
		url=url+"&x-up-bear-type=UNKNOWN";  //网络类型
     */  
	public static String Get(String url,String mobile,String Channel,String dtnow,String signature ) { 
		
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlName = url ;   
            System.out.println(urlName);  
            URL realUrl = new URL(urlName);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            
            conn.setRequestProperty("X-Up-Calling-Line-ID",mobile);
            conn.setRequestProperty("X-Channel-Code",Channel);
            conn.setRequestProperty("X-Timestamp",dtnow);
            conn.setRequestProperty("X-Signature",signature);
            
            // 设置超时时间  
            conn.setConnectTimeout(10000);  
            conn.setReadTimeout(10000);  
            // 建立实际的连接  
            conn.connect();  
            // 获取所有响应头字段  
            //Map< String,List< String>> map = conn.getHeaderFields();  
            // 遍历所有的响应头字段  
            /*for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  */
            // 定义BufferedReader输入流来读取URL的响应  
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
        	mylog.Err(url);
            String sErr = "Get:服务器连接失败！"+e.getMessage();  
            mylog.Err(sErr);
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
	
public static String Get(String url) { 
		
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlName = url ;   
            System.out.println(urlName);  
            URL realUrl = new URL(urlName);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            
            // 设置超时时间  
            conn.setConnectTimeout(10000);  
            conn.setReadTimeout(10000);  
            // 建立实际的连接  
            conn.connect();  
            // 获取所有响应头字段  
            //Map< String,List< String>> map = conn.getHeaderFields();  
            // 遍历所有的响应头字段  
            /*for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  */
            // 定义BufferedReader输入流来读取URL的响应  
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) { 
        	mylog.Err(url);
            String sErr = "Get:服务器连接失败！"+e.getMessage();  
            mylog.Err(sErr);
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 


	public static String Postutf8(String url, String param) {  
	mylog.info("Post:"+url+":"+param);
    PrintWriter out = null;  
    BufferedReader in = null;  
    String result = "";  
    try {  
        URL realUrl = new URL(url);  
        // 打开和URL之间的连接  
        URLConnection conn = realUrl.openConnection();
     // 设置超时时间  
        conn.setConnectTimeout(10000);  
        conn.setReadTimeout(10000);  
        // 发送POST请求必须设置如下两行  
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        
        byte[] requestStringBytes = param.getBytes("utf-8");   
        conn.setRequestProperty("Content-length", "" + requestStringBytes.length);   
        // 设置通用的请求属性  
        conn.setRequestProperty("accept", "*/*");   
        conn.setRequestProperty("user-agent", "Mozilla/5.0");  
        conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
        
     // 建立输出流，并写入数据   
        OutputStream outputStream = conn.getOutputStream();   
        outputStream.write(requestStringBytes);   
        outputStream.close();   
     
  
        // 定义BufferedReader输入流来读取URL的响应  
        in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
        //in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"GBK"));
        String line;  
        while ((line = in.readLine()) != null) {  
            result += "\n" + line;  
        }  
    } catch (Exception e) { 
    	mylog.Err(url+":"+param);
        result = "Post:服务器连接失败！";  
        String sErr = "Post:服务器连接失败！"+e.getMessage();  
        mylog.Err(sErr);
        e.printStackTrace();  
    }  
    // 使用finally块来关闭输出流、输入流  
    finally {  
        try {  
            if (out != null) {  
                out.close();  
            }  
            if (in != null) {  
                in.close();  
            }  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
    }  
    mylog.info("Post Ret:"+result);
    return result;  
} 

	/**  
     * 向指定URL发送POST方法的请求  
     *  
     * @param url  
     *            发送请求的URL  
     * @param param  
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。  
     * @return URL所代表远程资源的响应  
     */  
    public static String Post(String url, String param) {  
    	mylog.info("Post:"+url+":"+param);
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            //conn.setRequestProperty("connection", "Keep-Alive");  
            //conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");  
            conn.setRequestProperty("user-agent", "Mozilla/5.0");  
            //conn.setRequestProperty("Content-type", "application/json;charset=utf-8");
            
            ((HttpURLConnection) conn).setRequestMethod("POST");
            // 设置超时时间  
            conn.setConnectTimeout(10000);  
            conn.setReadTimeout(10000);  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"GBK"));
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) { 
        	mylog.Err(url+":"+param);
            result = "Post:服务器连接失败！";  
            String sErr = "Post:服务器连接失败！"+e.getMessage();  
            mylog.Err(sErr);
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        mylog.info("Post Ret:"+result);
        return result;  
    }  
    
    public static String Postbyhead(String url, String param,Map<String, String> maphead) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // 打开和URL之间的连接  
            URLConnection conn = realUrl.openConnection();  
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); 
            conn.setRequestProperty("Content-type", "application/xml");  //华为要加这个

            for (Map.Entry<String, String> entry : maphead.entrySet()) {
            	            //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
            	             //entry.getKey() ;entry.getValue(); entry.setValue();
            	            //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
            		conn.setRequestProperty(entry.getKey(), entry.getValue());  
            		System.out.println(entry.getKey()+":"+ entry.getValue());
                     }
            // 设置超时时间  
            conn.setConnectTimeout(10000);  
            conn.setReadTimeout(10000);  
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // 获取URLConnection对象对应的输出流  
            out = new PrintWriter(conn.getOutputStream());  
            // 发送请求参数  
            out.print(param);  
            // flush输出流的缓冲  
            out.flush();  
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            result = "Post:服务器连接失败！";  
            mylog.Err(url+":"+param);
            String sErr = "Post:服务器连接失败！"+e.getMessage();  
            mylog.Err(sErr);
            e.printStackTrace();  
        }  
        // 使用finally块来关闭输出流、输入流  
        finally {  
            try {  
                if (out != null) {  
                    out.close();  
                }  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    
public static String GetEx(String url) { 
		
        String result = "";  
        BufferedReader in = null;  
        URL realUrl=null;
        HttpURLConnection conn =null;
        try {  
            String urlName = url ;   
            System.out.println(urlName);  
            realUrl = new URL(urlName);  
            // 打开和URL之间的连接  
            conn = (HttpURLConnection)realUrl.openConnection();  
            // 设置通用的请求属性  
            //conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");  
            /*conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent",  
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36"); 
            conn.setRequestProperty("Accept-Encoding","gzip, deflate");  
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");*/

            
            // 设置超时时间  
            conn.setConnectTimeout(30000);  
            conn.setReadTimeout(30000);  
            // 建立实际的连接  
            conn.connect();  
            // 获取所有响应头字段  
            //Map< String,List< String>> map = conn.getHeaderFields();  
            // 遍历所有的响应头字段  
            /*for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  */
            // 定义BufferedReader输入流来读取URL的响应  
            //in = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            
            //当返回不是HttpURLConnection.HTTP_OK， HttpURLConnection.HTTP_CREATED， HttpURLConnection.HTTP_ACCEPTED 时，不能用getInputStream()
            if (conn.getResponseCode()!=HttpURLConnection.HTTP_OK)
            {
	            BufferedInputStream in1 = new BufferedInputStream(conn.getErrorStream());
	            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
	            //用OutputStream来接收
	            byte bb[] = new byte[1024];
	            int length = 0;
	            while ((length = in1.read(bb, 0, bb.length)) != -1) {
	                byteout.write(bb, 0, length);
	            }
	            //用文本方式来接收
	            String ret = byteout.toString();
	            System.out.println(ret);
            }

            
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) { 
        	mylog.Err(url);
            String sErr = "Get:失败！"+e.getMessage();  
            mylog.Err(sErr);
            result=sErr;
            e.printStackTrace();  
            
            try {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
				String line;  
	            while ((line = in.readLine()) != null) {  
	                System.out.println(line);  
	            }
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            

        }  
        // 使用finally块来关闭输入流  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    } 
	
}

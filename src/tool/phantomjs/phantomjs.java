package tool.phantomjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class phantomjs {
	
	private static String projectPath = "D:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe";//System.getProperty("user.dir");
	//private static String jsPath = "D:\\phantomjs-2.1.1-windows\\huicong.js";
	private static String jsPath = "D:\\phantomjs-2.1.1-windows\\page.js";
	private static String exePath = projectPath + File.separator + "phantomjs" + File.separator + "bin" + File.separator
			+ "phantomjs.exe";
	
	
	// 调用phantomjs程序，并传入js文件，并通过流拿回需要的数据。
		public static String getParseredHtml2(String url) throws IOException
		{
			Runtime rt = Runtime.getRuntime();
			//Process p = rt.exec(projectPath + " " + "ignore-ssl-errors=yes" +" "+ jsPath + " " + url+ " ");
			Process p = rt.exec(projectPath + " " + jsPath + " " + url );
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuffer sbf = new StringBuffer();
			String tmp = "";
			while ((tmp = br.readLine()) != null)
			{
				sbf.append(tmp+"\r\n");
			}
			System.out.println(sbf);
			
			return sbf.toString();
		}
	
	
}

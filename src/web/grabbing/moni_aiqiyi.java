package web.grabbing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tool.selenium.selenium_set;

import org.openqa.selenium.JavascriptExecutor;

public class moni_aiqiyi {
	
	

	
	WebDriver driver = null;
	
	public moni_aiqiyi()
	{
		//#实例化谷歌设置选项
		//webdriverChromeOptions()
		//#添加保持登录的数据路径：安装目录一般在C:\Users\黄\AppData\Local\Google\Chrome\User Data
		//option.add_argument(r"user-data-dir=C:\Users\黄\AppData\Local\Google\Chrome\User Data")
		
		ChromeOptions Options = new ChromeOptions();
		//Options.addArguments("user-data-dir=C:\\Users\\taohuosheng\\AppData\\Local\\Google\\Chrome\\User Data");
		Options.addArguments("user-data-dir="+selenium_set.userdatadir);
		//options.addArguments("--no-sandbox");
		///options.addArguments("--disable-dev-shm-usage");
		//File file = new File ("files\\youtube.crx");//加载插件      
		//options.addExtensions(file);
		//Options.addArguments("user-data-dir=D:\\111111\\tmplate\\googletest");
		//options.addArguments("disk-cache-dir","D:\\111111\\tmplate\\googlecache");

			// 设置 ie 的路径  
//		        System.setProperty("webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe");
			// 设置 chrome 的路径  
		        System.setProperty("webdriver.chrome.driver", selenium_set.chrome_driver);
		        // 创建一个 ie 的浏览器实例  
//		        driver = new InternetExplorerDriver();
		        // 创建一个 chrome 的浏览器实例
		        driver = new ChromeDriver(Options);
		        
		        try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
			//最大化
			//driver.manage().window().maximize();
			//访问百度
			driver.get("https://mp.iqiyi.com/");
			//等待5秒，等第count条查询结果加载完
			WebDriverWait wait = new WebDriverWait(driver, 15);
			
			if(true) return;
			
			//读取Cookie
			try 
	        {
	            File file=new File("d:\\broswer.data");
	            FileReader fr=new FileReader(file);
	            BufferedReader br=new BufferedReader(fr);
	            String line;
	            while((line=br.readLine())!= null)
	            {
	                StringTokenizer str=new StringTokenizer(line,";");
	                while(str.hasMoreTokens())
	                {
	                    String name=str.nextToken();
	                    String value=str.nextToken();
	                    String domain=str.nextToken();
	                    String path=str.nextToken();
	                    Date expiry=null;
	                    String dt;
	                    if(!(dt=str.nextToken()).equals(null))
	                    {
	                        //expiry=new Date(dt);
	                        System.out.println();
	                    }
	                    boolean isSecure=new Boolean(str.nextToken()).booleanValue();
	                    Cookie ck=new Cookie(name,value,domain,path,expiry,isSecure);
	                    driver.manage().addCookie(ck);
	                }
	            }
	            
	            driver.get("https://mp.iqiyi.com/");
	            
	            try
	            {
	            	for (int i=0;i<2;i++)
	            	{
	            		Thread.sleep(3000);
	            		//跳过按钮。<span class="skip">跳过</span>
	            		WebElement submit = driver.findElement(By.xpath("//span[@class='skip']"));
	            		submit.click();
	            	}
	            }
	            catch(Exception e)
	            {
	            	
	            }
	            
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
			
			
			
			
	}
	
	public void closeIE()
	{
		
		File file = new File("d:\\broswer.data");
        try {
            // delete file if exists
            file.delete();
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Cookie ck : driver.manage().getCookies()) {
                bw.write(ck.getName() + ";" + ck.getValue() + ";"
                        + ck.getDomain() + ";" + ck.getPath() + ";"
                        + ck.getExpiry() + ";" + ck.isSecure());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("cookie write to file");
        }
        driver.quit();
		//driver.close();
	}
	
	public void do_login()
	{

		//WebElement element = driver.findElement(By.xpath("//*[@id=\"kw\"]"));
		//WebElement searchinputbox=driver.findElement(By.id("query"));
		//WebElement searchButton=driver.findElement(By.cssSelector("a[btn-base btn-base_border]"));//
		//WebElement searchButton=driver.findElement(By.className("login-btn"));
		WebElement searchButton=driver.findElement(By.linkText("登录"));
		searchButton.click();  
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		//searchinputbox.sendKeys("使用JavaScript语句进行");
		//JavaScriptClick(searchButton);	

		// 定位frame位置，并选取frame
		//WebElement frame=driver.findElement(By.xpath( "/html/body/div[2]/div[8]/div[2]/div[3]/div/div[2]/div/iframe" ));
		//driver.switchTo().frame(frame);
		driver.switchTo().frame(0);
	

		WebElement aa=driver.findElement(By.linkText("账号密码登录"));
		aa.click();  
		WebDriverWait wait1 = new WebDriverWait(driver, 5);
		
		
		//<input type="text" class="txt-info txt-account" data-pwdloginbox="name">
		WebElement login = driver.findElement(By.xpath("//input[@class='txt-info txt-account']"));
		login.sendKeys("13981919000");
		
		
		//<input type="password" class="txt-info txt-password" data-pwdloginbox="pwd" maxlength="20" data-pwdbak-name="loginpwd">
		WebElement spassword = driver.findElement(By.xpath("//input[@class='txt-info txt-password']"));
		spassword.sendKeys("textere");
		
		WebElement submit = driver.findElement(By.xpath("//input[@class='txt-info txt-password']"));
		submit.click();		
		
		
		//3.跳出iFrame
		//跳出frame,进入default content;
		//dr.switchTo().defaultContent();
		
	}
	
	
	//进入视频管理页获取数据
	public void do_spgl()
	{
		//driver.findElements(By.cssSelector(".datagrid-row-expander.datagrid-row-expand")).get(0).click();   //当我们get(0)的时候会操作当前页面的第一个，以此类推
		
		//<a href="javascript:;" data-menu-toggle="自媒体" class="leftMenu"><em class="mp-svgicon svg-selfMedia"></em>自媒体<i class="mp-svgicon svg-arrow-down"></i></a>
		
		List<WebElement> WebElements =driver.findElements(By.cssSelector("a[data-menu-toggle='自媒体']"));
		WebElements.get(0).click();
		
		
	
		/*List<WebElement> WebElements2 =driver.findElements(By.cssSelector("a[href='/wemedia/stat']"));
		
		System.out.println(WebElements.size());
		
		WebElement WebElement2=WebElements2.get(0);
		
		String aaa=WebElement2.getText();
		
		System.out.println(aaa);
		
		WebElements2.get(0).click();*/
		
		WebElement WebElement3=driver.findElement(By.linkText("视频数据"));

		WebElement3.click();
		
		
		
		
		if (true) return;
		
		/**
		 <li class="router-link-exact-active selected" data-route-name="$wemediaStat" rseat="pub_leftmenu_shipinsz" dt="b"><a href="/wemedia/stat">
                            视频数据
                            <!----> <!----></a></li>
		 */
	
		//System.out.print(driver.get);
		String stext="\r\n"
 +"                            视频数据"+"\r\n"
 +"                            ";
		
		WebElement searchButton=driver.findElement(By.xpath("//li[@class='router-link-exact-active selected']"));
		
		String aa=searchButton.getText();
		System.out.println(aa);
		
		searchButton.click();  
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
			
	}
	
	public void JavaScriptClick(WebElement element){
		try {

			if (element.isEnabled() && element.isDisplayed()) {

				//((JavascriptExecutor)driver).executeScript("arguments[0].click();",element );
				((JavascriptExecutor)driver).executeScript("arguments[0].click();",element );
				System.out.println(element.getText());

			}else {
				System.out.println("页面上的元素无法进行点击操作");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws Exception 
	 {
		Document doc = Jsoup.parse("<table class=\"w-table\"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>4.2万</span></div></td><td><div><span>3.1万</span></div></td><td><div><span>18.1万</span></div></td><td><div><span>--</span></div></td><td><div><span>23</span></div></td><td><div><span>8</span></div></td><td><div><span>9</span></div></td><td><div><span>18</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>4.2万</span></div></td><td><div><span>3.1万</span></div></td><td><div><span>18.1万</span></div></td><td><div><span>48.49%</span></div></td><td><div><span>23</span></div></td><td><div><span>8</span></div></td><td><div><span>9</span></div></td><td><div><span>18</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>4.2万</span></div></td><td><div><span>3.1万</span></div></td><td><div><span>18.1万</span></div></td><td><div><span>48.49%</span></div></td><td><div><span>23</span></div></td><td><div><span>8</span></div></td><td><div><span>9</span></div></td><td><div><span>18</span></div></td></tr> <!----></tbody></table>");
		
		Elements rows = doc.select("tr");
		
		for (Element row :rows)
		{
			System.out.println("-----------------------");	
			
			//System.out.println(row.html());	
			System.out.println(row.text());	
			List<TextNode> aa= row.textNodes();
			
			for (TextNode a:aa)
			{
				System.out.println(a.text());	
			}
		}
		
		
		if (true) return;
		moni_aiqiyi moni=new moni_aiqiyi();
		//moni.do_login();
		
		moni.do_spgl();
		
		Thread.sleep(30000);
		
		
		moni.closeIE();
	 }
	
}

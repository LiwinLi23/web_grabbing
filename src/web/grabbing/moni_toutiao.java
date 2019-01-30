package web.grabbing;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import main.PID;
import my.db.db;
import my.function.mylog;
import my.function.pb;
import tool.selenium.selenium_set;

public class moni_toutiao {
	
	WebDriver driver = null;
	
	String Main_handle="";//主要窗口
	
	public moni_toutiao()
	{
		//#实例化谷歌设置选项
		ChromeOptions Options = new ChromeOptions();
		
		Options.addArguments("user-data-dir="+selenium_set.userdatadir);

		// 设置 chrome 的路径  
        System.setProperty("webdriver.chrome.driver", selenium_set.chrome_driver);
        
        
        
        // 创建一个 chrome 的浏览器实例
        driver = new ChromeDriver(Options);
        
        
  
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        
        
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);//隐式时间等待是在查找元素的时候，设置一个最大时间值
        driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);//设置get超时时间
        driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);//在设置规定的时间内，等待异步脚本的执行结束，而不是里面抛出错误
       
        

    	//最大化
         System.out.println(driver.manage().window().getSize());
    	/*driver.manage().window().maximize();
        try {
    			Thread.sleep(3000);
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}*/
        
         long t1=System.currentTimeMillis();
    	
    	//访问百度
        try
        {
        	//driver.get("http://127.0.0.1:5051/test/test/test");
        	driver.get("https://mp.toutiao.com/profile_v3/index");
        	mylog.info("driver.get:"+String.valueOf(System.currentTimeMillis()-t1));
        	
        	Thread.sleep(6000);
        	
            Main_handle=driver.getWindowHandle();
            System.out.println("Main_handle:"+Main_handle);
            
        }
        catch(Exception e)
        {
        	mylog.info("driver.get Err:"+String.valueOf(System.currentTimeMillis()-t1));
        	Main_handle="";
        	mylog.ErrStackTrace(e);
        	
        }
    	

    	
    	//等待5秒，等第count条查询结果加载完
    	//WebDriverWait wait = new WebDriverWait(driver, 30);
				
	}
	
	
	public void closeIE()
	{
		
		/*String closename="chrome.exe";
		PID.kill(closename);
		closename="chromedriver.exe";
		PID.Fkill(closename);*/	
		
		try
        {
			 Set<String> winhandles=  driver.getWindowHandles();
			 for(java.util.Iterator<String> iter = winhandles.iterator(); iter.hasNext();){
		            String value =  iter.next();
		            
		            driver.switchTo().window(value);
		            driver.close();
		            Thread.sleep(500);
		        }
	        driver.quit();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
    		String closename="chrome.exe";
    		PID.kill(closename);
    		closename="chromedriver.exe";
    		PID.Fkill(closename);	
        }

	}
	
	public void set_vodie_by_day(String splatform,String sname,String sznum,String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>1)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				//日期                              播放次数 播放人数     播放时长(分钟) 播放完成度 评论 收藏 顶 踩
				//[2018-11-29, 3.7万, 2.7万, 15.8万, 59.94%, 21, 3, 32, 16]
				//System.out.println(sname +":"+ sline);
				dao_grabbing.set_alone_video(sday, splatform, sname, "", dao_grabbing.reset(sznum),
						 dao_grabbing.reset(arr[1]), "");
			}
		}
		
	}
	
	
	public void set_tengxun_money_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				if (pb.atof(arr[1])!=0)
				dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_toutiao,arr[1],arr[3],arr[4]);
			}
		}
	}

	
	
	public void set_tenxun_Fans_by_day(String sline,String inum,String iplaynum)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				//String inum=dao_grabbing.reset(arr[1]);
				String iaddnum=dao_grabbing.reset(arr[3]);
				if (pb.atof(inum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_toutiao,inum , "", iaddnum, "", "",iplaynum);
			}
		}
	}
	
	
	
	//
	//判断是否需要登录
	public boolean cheaklogin()
	{
		try
		{
			String stitle=driver.getTitle();
			//登录-腾讯内容开放平台
			if (stitle.indexOf("主页 - 头条号")>=0)
			{
				WebElement spButton=driver.findElement(By.xpath("/html/body/div/div[3]/div[1]/div/img[3]"));
				spButton.click();
				Thread.sleep(1000);
				
				//用户名
				WebElement logininput=driver.findElement(By.xpath("//*[@id='screens']/div[2]/div[4]/div[2]/div[4]/form/div[1]/input"));
				System.out.println(logininput.getAttribute("value"));
				if (!logininput.getAttribute("value").equals("wylvipkiki@163.com"))
				{
					logininput.clear();
					//logininput.sendKeys("wylvipkiki@163.com");
					
					logininput.sendKeys("wy");
					Thread.sleep(1000);
					logininput.sendKeys("lvip");
					Thread.sleep(1000);
					logininput.sendKeys("kiki");
					Thread.sleep(1000);
					logininput.sendKeys("@163");
					Thread.sleep(1000);
					logininput.sendKeys(".com");
					Thread.sleep(1000);

				}
				//密码
				WebElement password=driver.findElement(By.xpath("//*[@id='screens']/div[2]/div[4]/div[2]/div[4]/form/div[2]/input"));
			
				password.clear();
				password.sendKeys("34cwhs100");
				
				//登录  //*[@id="screens"]
				spButton=driver.findElement(By.xpath("//*[@id='screens']/div[2]/div[4]/div[2]/div[4]/div[1]/button[1]"));
				spButton.click();
				Thread.sleep(1000);	
			}
			
			
			WebElement spButton=driver.findElement(By.linkText("收益统计"));

			return true;
			
			
		}
		catch(Exception e)
        {
			mylog.ErrStackTrace(e);
			return false;
			
        }
	}
	
	
	
	
	//视频分析
		public int spfx(String splatform,int ibegin) //开始也页面。开始的位置
		{
			int iout=ibegin;//返回值
			
			int istart=ibegin/20+1;
			int jstart=ibegin % 20;
			
			 try {
					//
				 
				 	WebElement spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/div/span"));
					spButton.click(); 
					Thread.sleep(1000);
					
					spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/ul/li[3]/a"));
					spButton.click(); 
					
				 	//WebElement spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[1]/ul/li[3]/a"));
					//spButton.click(); 
					
					Thread.sleep(5000);
					
					//<em class="sum-page">157</em>
					//WebElement elevator=driver.findElement(By.xpath("//*[@id='article-list']/div[22]/div/div[3]"));
					WebElement elevator=driver.findElement(By.xpath("//*[@id='video-manage']/div[2]/div[21]/div[3]"));
					
					Document doc1 = Jsoup.parse(elevator.getAttribute("outerHTML"));
					
					int ipage=0;
					
					Elements pages = doc1.select("span");
					
					for (Element a1:pages)
					{
						if (pb.atoi(a1.text())>ipage)
							ipage=pb.atoi(a1.text());
						
					}
					
					System.out.println("spage:"+ipage);
					
					//不支持跳页，只有一页 一页的来
					int i=1;
					while (i<=ipage)
					{	
						System.out.println("第"+i+"页");
						//等待table可以点击<em class="cur-page">4</em>     ////*[@id="video-manage"]/div[2]
						//WebElement table=driver.findElement(By.xpath("//*[@id='article-list']"));
						WebElement table=driver.findElement(By.xpath("//*[@id='video-manage']"));
						
						 String sdoc=table.getAttribute("outerHTML");
						 
						 Document doc = Jsoup.parse(sdoc);
						
						//#article-list > div:nth-child(2) > div.article-card-wrap.has-image > div
						 //Elements rows = doc.select("div[class=article-card-bone]");
						 Elements rows = doc.select("div[class=article-card]");
						 
						 for (Element row :rows)		
							{
							 /*
							  * 之前阅读的
							  * //#article-list > div:nth-child(2) > div.article-card-wrap.has-image > div > div.title-wrap > div
							 String sname=row.selectFirst("div.title-wrap > div").text();
							 
							 //#article-list > div:nth-child(2) > div.article-card-wrap.has-image > div > ul > li:nth-child(2)
							 String inum=row.selectFirst("div > ul > li:nth-child(2)").text();
							 //阅读  3425
							 inum=inum.replaceAll("阅读", "").trim();
							 
							 mylog.info(iout+"->"+ splatform+":"+sname+" inum:"+inum);
							
							 String icollection=row.selectFirst("div > ul > li:nth-child(5)").text();
							 icollection=icollection.replaceAll("收藏 ", "").trim();
							 
							 String dtadd ="";
							 Element enum3 =row.selectFirst("div > div:nth-child(4) > span");
							 if (enum3!=null)
							 {
								 dtadd=enum3.text();
								 dtadd=dao_grabbing.dayset(dtadd);
							 }*/
							 
							 
							//#video-manage > div.m-articles.no-count > div:nth-child(2) > div.article-card-wrap > div > a
							 String sname=row.selectFirst("div.article-card-wrap > div > a").text();
							 String inum="";
							 String dtadd ="";
							 String icollection="";
							 try
							 {
							 //#video-manage > div.m-articles.no-count > div:nth-child(3) > div.article-card-wrap > div > ul > li:nth-child(2)
							 inum=row.selectFirst("div.article-card-wrap > div > ul > li:nth-child(2)").text();
							 //阅读  3425
							 inum=inum.replaceAll("播放", "").trim();
							 
							 inum=dao_grabbing.reset(inum);
							 
							 mylog.info(iout+"->"+ splatform+":"+sname+" inum:"+inum);
							
							 icollection=row.selectFirst("div.article-card-wrap > div > ul > li:nth-child(5)").text();
							 icollection=icollection.replaceAll("收藏 ", "").trim();
							 
							
							 Element enum3 =row.selectFirst("div.article-card-wrap > div > div.label.create-time > span");
							 if (enum3!=null)
							 {
								 dtadd=enum3.text();
								 dtadd=dao_grabbing.dayset(dtadd);
							 }
							 }
							 catch (Exception e)
							 {
								 
							 }
							 
							
							 
							//修正表里面的数据， 
							dao_grabbing.insert_tvideo_byname(dao_grabbing.splatform_toutiao, sname, inum,dtadd);
							
							String iaddnum=inum;
							
							//添加日志数据
							String sday=pb.datetostr(new Date()).substring(0,10);
							
							String sSql="select top 1 * from tvideolog where splatform='"+dao_grabbing.splatform_toutiao+"' and sname='"+sname+"'"
									+ "and  convert(VARCHAR(10),dtinsert,120) <>'"+sday+"' order by dtinsert desc";
							
							Map ret=db.OpenOneSql_toMap("pftfdb", sSql);
							if (ret!=null)
							{
								//计算增量
								//iaddnum=String.valueOf(pb.atoi(inum)-pb.atoi((String)ret.get("inum")));
								String iplaynumyestoday= ret.get("iplaynum").toString();
								//计算增量
								iaddnum=String.valueOf(pb.atoi(inum)-pb.atoi(iplaynumyestoday));
							}

							dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_toutiao, sname, "", inum, iaddnum, icollection);
							
							iout++;
							}
						 
						 	i++;
						 	if (i>ipage)
						 	{
						 		return 9999;
						 	}
						 	
						 	{
						 		boolean bfind=false;
						 		//翻页
						 		//*[@id="article-list"]/div[22]/div/div[3]
						 		//List<WebElement> listbtn=  driver.findElements(By.xpath("//*[@id='article-list']/div[22]/div/div[3]/span"));
						 		List<WebElement> listbtn=  driver.findElements(By.xpath("//*[@id='video-manage']/div[2]/div[21]/div[3]/span"));
						 		for (WebElement btn:listbtn)
						 		{
						 			
						 			//if (btn.getText())
						 			System.out.println(btn.getText());
						 			int thispage=pb.atoi(btn.getText());
						 			if (thispage==i)
						 			{
						 				bfind=true;
						 				btn.click();
						 				//这里要等很久数据才加载出来
						 				Thread.sleep(5000);
						 				break;
						 			}
						 		}
						 		
						 		if (!bfind)
						 		{
						 			return 9999;
						 		}
						 	}
							
						 }	
						 jstart=0; //重置
						
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mylog.ErrStackTrace(e);
					return iout;
			}

			dao_grabbing.set_scanning(splatform, 9999);
			return 9999;
		}
	
	//收益分析
	
	public boolean syfx()
	{
		
		try {
			//总收益
			
			//先获取总收益
			WebElement spButton1=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[3]/div/span"));
			spButton1.click(); 
			Thread.sleep(2000);
			spButton1=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[3]/ul/li[4]/a"));
			spButton1.click(); 
			Thread.sleep(2000);
			
			spButton1=driver.findElement(By.xpath("//*[@id='index']/div/div/div/div[1]/div/div[1]/div/div[3]/div[2]"));
			
			String dallmoney=spButton1.getText();
			
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_toutiao,"",dallmoney,"","","","");
			
			
			
			System.out.print(driver.getWindowHandle());
			Thread.sleep(1000);
			
			
			WebElement spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/div/span"));
			spButton.click(); 
			
			Thread.sleep(1000);
			
			spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/ul/li[7]/a"));
			spButton.click(); 
			
			//首页，不用跳
			 //WebElement sy=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[1]/ul/li[6]/a"));
			 //sy.click();
			 
			 Thread.sleep(2000);
			                                                 
			
			 //WebElement table=driver.findElement(By.xpath(  "//div[@class='tui-table-container']"));
			 WebElement table=driver.findElement(By.xpath(  "//div[@class='xigua-table-container undefined']"));
			 
			 String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				
				 
				Document doc = Jsoup.parse(sdoc);
				
				//Elements rows = doc.select("tr");
				Elements rows = doc.select("li");
				for (Element row :rows)		
				{
					
					sday=row.select("span").get(0).text();
					String aa=row.select("span").get(1).text();
					String a1=row.select("span").get(3).text();
					String a2=row.select("span").get(4).text();
					
					
					dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_toutiao,aa,a1,a2);
					//set_tengxun_money_by_day(row.text());
				}
				System.out.print("收益分析完毕");	
				return true;
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
	 }
	}
	
	//粉丝分析
	public boolean fsfx()
	{
		
		try {
			
			WebElement spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/div/span"));
			spButton.click(); 
			Thread.sleep(1000);
			
			spButton=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[2]/ul/li[6]/a"));
			spButton.click(); 
			
			Thread.sleep(1000);
			
			//总的播放数
			 String iplaynum=driver.findElement(By.xpath("//*[@id='xigua']/div/div/div/div[1]/div[2]/div[3]/div[1]")).getText();
			 iplaynum=dao_grabbing.reset(iplaynum);
			
			
			//个人中心
			 WebElement sy=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[3]/div/span"));
			 sy.click();
			 
			 Thread.sleep(1000);
			 
			 sy=driver.findElement(By.xpath("//*[@id='root']/div[2]/div[2]/ul/li[3]/ul/li[3]/a"));
			 sy.click();
			 
			 Thread.sleep(2000);
			 
			 String fs=driver.findElement(By.xpath("//*[@id='index']/div/div/div[2]/div[1]/div[1]/div[2]/a")).getText();
			 fs=dao_grabbing.reset(fs);
			 
			 //悬停
			
			 WebElement xt =driver.findElement(By.xpath("//*[@id='index']/div/div/div[2]/div[1]/div[1]/div[1]/div/div/i"));
			 Actions action = new Actions(driver);  
			 //action.moveToElement(xt).clickAndHold();
			 action.moveToElement(xt).perform();
			 
			 Thread.sleep(1000);
			 //<div class="pgc-hover-popup "><i type="questioncircle" class="iconfont icon-questioncircle "></i><div class="hover-content right" style="min-width: 180px; width: auto;"><div><p class="popup-inner">头条/问答: 1110513</p><p class="popup-inner">西瓜: 1617850</p><p class="popup-inner">抖音: 3987380</p></div><div class="hover-arrow"><i></i></div></div></div>
			 
			 xt =driver.findElement(By.xpath("//*[@id='index']/div/div/div[2]/div[1]/div[1]/div[1]/div/div"));
			 System.out.println(xt.getAttribute("outerHTML"));
			 
			 String smsg=xt.getAttribute("outerHTML");
			 int ipos=smsg.indexOf("抖音: ");
			 if (ipos>0) smsg=smsg.substring(ipos+4);
			 smsg=smsg.substring(0,smsg.indexOf("</p>"));
			 
			 fs=String.valueOf(pb.atoi(fs)-pb.atoi(smsg));

			 ////*[@id="index"]/div/div/div[2]/div[3]/div[2]/table
			 WebElement table=driver.findElement(By.xpath("//div[@class='tui-table-container']/table"));
			 
			 String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				 
				Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_tenxun_Fans_by_day(row.text(),fs,iplaynum);
				}
			 
				System.out.print("粉丝分析完毕");	
				return true;
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;	
	 }
	}
	
	///html/body/div[2]/div/div/div[2]/div/div[2]/div[1]
	//可能有悬浮窗口，要先关下
	public void closeguanggao()
	{
		

		try {
			
			//先判断下是否需要登录。
			
			
			WebElement body=driver.findElement(By.xpath("/html/body"));
			//WebElement body=driver.findElement(By.xpath("//*[@id='root']/div[1]"));
			System.out.print(body.getAttribute("class"));
			String sclass=body.getAttribute("class");
			
			if (sclass.equals("pgc-login"))
			{
				body=driver.findElement(By.xpath("/html/body/div/div[3]/div[1]/div/img[3]"));
				body.click();
				Thread.sleep(1500);
			}
			
			
			
			
			System.out.print(driver.getWindowHandle());
				 WebElement sy=driver.findElement(By.xpath("//html/body/div[2]/div/div/div[2]/div/div[2]/div[1]"));
				 sy.click();
				System.out.print("关闭广告");	
				Thread.sleep(1100);
		
			

			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
		 		System.out.print("没找到广告");	
			e.printStackTrace();
		
			
	 }
		
	}
	
	
	public static String Seach() throws InterruptedException
	{
		String sout=dao_grabbing.splatform_toutiao+"扫描完毕";
		int iret=0;

		//广告判断，关闭广告
		{
			moni_toutiao moni=new moni_toutiao();
			moni.closeguanggao();
			moni.closeIE();
			Thread.sleep(5000);
		}
		PID.clear();
		
		for(int i=0;i<10;i++)
		{
			moni_toutiao moni=new moni_toutiao();
			iret = moni.spfx(dao_grabbing.splatform_toutiao,1);
			moni.closeIE();
			Thread.sleep(5000);
			PID.clear();
			if (iret==9999) break;
		}
		
		if (iret!=9999) sout=sout+" 分析视频数据失败！";
		
		{
			moni_toutiao moni=new moni_toutiao();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		{
			moni_toutiao moni=new moni_toutiao();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		return sout;
		
	}
	
	
	public static void main(String[] args) throws Exception 
	 {
		
		
		//广告判断，关闭广告
		/*{
			moni_toutiao moni=new moni_toutiao();
			moni.closeguanggao();
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		/*{
			moni_toutiao moni=new moni_toutiao();
			//if (moni.cheaklogin())
			{
				moni.syfx();	
			}
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		{
			moni_toutiao moni=new moni_toutiao();
			//if (moni.cheaklogin())
			{
				moni.fsfx();	
			}
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		/*{
			moni_toutiao moni=new moni_toutiao();
			
			moni.spfx(dao_grabbing.splatform_toutiao,1);
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		
		
		



		
		
		//moni.spfx(dao_grabbing.splatform_uc,1);
		//moni.spfx(dao_grabbing.splatform_youku,1);
		//moni.spfx(dao_grabbing.splatform_tudou,1);
		//moni.syfx();
		
		

		//moni.closeIE();
	 }
}
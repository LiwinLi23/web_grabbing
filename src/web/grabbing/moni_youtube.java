package web.grabbing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import main.PID;
import my.db.db;
import my.function.mylog;
import my.function.pb;
import tool.selenium.selenium_set;

public class moni_youtube {
	
	WebDriver driver = null;
	
	String Main_handle="";//主要窗口
	
	public moni_youtube()
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
        	driver.get("https://www.youtube.com/my_videos?o=U");
        	mylog.info("driver.get:"+String.valueOf(System.currentTimeMillis()-t1));
        	
        	Thread.sleep(3000);
        	
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
	
	
	
	
	public void set_tengxun_money_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			
			if (sday.indexOf("月")==6) sday=sday.replaceAll("年", "年0");
			if (sday.indexOf("日")==9) sday=sday.replaceAll("月", "月0");
			
			sday=sday.replaceAll("年", "-");
			sday=sday.replaceAll("月", "-");
			sday=sday.replaceAll("日", "");
			if ((sday.length()>=8)&&(sday.length()<=10))
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				if (pb.atof(arr[2])!=0)
				dao_grabbing.set_alone_money_youtobe(sday, dao_grabbing.splatform_youtube,
						dao_grabbing.reset(arr[2]),
						dao_grabbing.reset(arr[5]),
						dao_grabbing.reset(arr[8]));
			}
		}
	}

	
	
	public void set_tenxun_Fans_by_day(String sline,String inum,String iplaynum)
	{
		String[] arr=sline.split(" ");
		
		inum=dao_grabbing.reset(inum);
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.indexOf("月")==6) sday=sday.replaceAll("年", "年0");
			if (sday.indexOf("日")==9) sday=sday.replaceAll("月", "月0");
			sday=sday.replaceAll("年", "-");
			sday=sday.replaceAll("月", "-");
			sday=sday.replaceAll("日", "");
			if ((sday.length()>=8)&&(sday.length()<=10))
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				//String inum=dao_grabbing.reset(arr[1]);
				String iaddnum=dao_grabbing.reset(arr[1]);
				if (pb.atof(inum)!=0)
				//dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youtube,inum , "", iaddnum, "", "",iplaynum);
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youtube,"" , "", iaddnum, "", "","");
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
			if (stitle.indexOf("登录")>=0)
			{
				WebElement spButton=driver.findElement(By.xpath("//*[@id='screens']/div[2]/div[4]/div[2]/div[1]/div/div[2]"));
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
		
		int istart=ibegin/30+1;
		int jstart=ibegin % 30;
		
		 try {

				//<em class="sum-page">157</em>
				WebElement elevator=driver.findElement(By.xpath("//*[@id='creator-subheader-item-count']"));
				
				
				
				String spage= elevator.getText();
				System.out.println("spage:"+spage);
				int ipage=pb.atoi(spage);
				ipage=(int) Math.ceil(ipage/30.0);
				
			
				
				System.out.println("spage:"+ipage);
				
				//不支持跳页，只有一页 一页的来
				int i=1;
				while (i<=ipage)
				{	
					
					System.out.println("第"+i+"页");
					Thread.sleep(5000);
					//等待table可以点击<em class="cur-page">4</em>
					WebElement table=driver.findElement(By.xpath("//*[@id='vm-playlist-video-list-ol']"));
					
					 String sdoc=table.getAttribute("outerHTML");
					 
					 
					 Document doc = Jsoup.parse(sdoc);
					
					 Elements rows = doc.select("li[class=ng-binding ng-scope]");
					 
					 for (Element row :rows)		
						{
						 //System.out.println(row.outerHtml());
						 
						//#videoList > div.videoList.searchResult > div.video_lists > ul > li:nth-child(1) > div.rightPub > div.rightPub-top > p > span.text > b
						 String sname=row.selectFirst("a[class=vm-video-title-content yt-uix-sessionlink]").text();
						
						 
						 String inum="0";
						 Element enum1 =row.selectFirst("div > div.vm-video-item-content-secondary > div.vm-video-side-content-left-container > div.vm-video-side-view-count > a");
						 if (enum1!=null)
						 inum=dao_grabbing.reset(enum1.text());
						 
						 mylog.info(iout+"->"+ splatform+":"+sname+" inum:"+inum);
						
						 String icollection ="0";
						 Element enum2 =row.selectFirst(" div > div.vm-video-item-content-secondary > div.vm-video-side-content-right-container > div > a.yt-uix-tooltip.view-stats-metric-link > span.vm-video-metric.video-likes-count > span > span.vm-video-metric-value");
						 if (enum2!=null)
						 icollection =dao_grabbing.reset(enum2.text());
						 
						 String dtadd ="";
						 //#vm-video-zj8uAqYpZms > 
						 //Element enum3 =row.selectFirst(" div > div.vm-video-item-content-primary > div.vm-video-info-container > div:nth-child(2) > span > span:nth-child(4) > span:nth-child(2)");
						 Element enum3 =row.selectFirst(" div > div.vm-video-item-content-primary > div.vm-video-info-container > div:nth-child(2) > span > span:nth-child(1)");
						 if (enum3!=null)
						 {
							 dtadd=enum3.text();
							 System.out.println(dtadd);
							 dtadd=dao_grabbing.dayset(dtadd);
							 System.out.println(dtadd);
						 }
						 
						mylog.info(this.getClass().getName(),sname+":"+inum+":"+icollection+":"+dtadd);
						 
						//修正表里面的数据， 
						dao_grabbing.insert_tvideo_byname(dao_grabbing.splatform_youtube, sname, inum,dtadd);
						
						String iaddnum=inum;
						
						//添加日志数据
						String sday=pb.datetostr(new Date()).substring(0,10);
						
						String sSql="select top 1 * from tvideolog where splatform='"+dao_grabbing.splatform_youtube+"' and sname='"+sname+"'"
								+ "and  convert(VARCHAR(10),dtinsert,120) <>'"+sday+"' order by dtinsert desc";
						
						Map ret=db.OpenOneSql_toMap("pftfdb", sSql);
						if (ret!=null)
						{
							String iplaynumyestoday= ret.get("iplaynum").toString();
							//计算增量
							iaddnum=String.valueOf(pb.atoi(inum)-pb.atoi(iplaynumyestoday));
						}

						dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_youtube, sname, "", inum, iaddnum, icollection);
						
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
					 		List<WebElement> listbtn=  driver.findElements(By.xpath("//*[@id='vm-pagination']/div/a"));
					 		for (WebElement btn:listbtn)
					 		{
					 			
					 			//if (btn.getText())
					 			
					 			int thispage=pb.atoi(btn.getText());
					 			if (thispage==i)
					 			{
					 				System.out.println(btn.getText());
					 				bfind=true;
					 				btn.click();
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

			WebElement spButton=driver.findElement(By.xpath("//*[@id='creator-sidebar-section-id-analytics']/h3/a/span[2]"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(2000);
			
			spButton=driver.findElement(By.xpath("//*[@id='creator-sidebar-item-id-earnings']/a/span"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click(); 
			
			Thread.sleep(12000);
			
			List<WebElement> WebElements=driver.findElements(By.xpath("//span[@class='C2KYWWB-g-e']"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			for (WebElement aa:WebElements)
			{
				if (aa.getText().equals("日期"))
				{
					//System.out.println(aa.getAttribute("outerHTML"));	
					aa.click();
					break;
				}
				
			}
			
			
			
			
			Thread.sleep(12000);
			
			 WebElement table=driver.findElement(By.xpath("//*[@id='gwt-debug-table']/table"));
			 
			
				String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				
				 
				Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_tengxun_money_by_day(row.text());
				}
			 
			 
				
				//先获取总收益
				WebElement spButton1=driver.findElement(By.xpath("//*[@id='gwt-debug-datePicker']/button[1]"));
				spButton1.click(); 
				Thread.sleep(2000);   
				List<WebElement> cheak=driver.findElements(By.xpath("//*[@id='body']/div[10]/div/ul/li"));
				for (WebElement aa:cheak)
				{
					if (aa.getText().equals("存在期间"))
					{
						aa.click();
						Thread.sleep(15000);
						
						spButton1=driver.findElement(By.xpath("//*[@id='gwt-debug-bragbutton-label-21']"));
						
						String dallmoney=spButton1.getText();
						
						String sday=pb.datetostr(new Date()).substring(0,10);
						dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youtube,"",dallmoney,"","","","");
						
						break;
					}
				}
				
				System.out.print("收益分析完毕");	
				return true;
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
	 }
	}
	
	//所有播放量
	//粉丝分析
	public boolean fsfx()
	{
		
		String inum="";
		String iplaynum="";
		
		
		
		long t1=System.currentTimeMillis();
		try {
			
			
			//查看总订阅数
			WebElement B1=driver.findElement(By.xpath("//*[@id='creator-sidebar-section-id-dashboard']/h3/a/span[2]"));
			B1.click();
			
			Thread.sleep(1000);
			
			
			inum=driver.findElement(By.xpath("//*[@id='dashboard-header-stats']/li[2]/div[1]")).getText();
			//总的播放数
			iplaynum=driver.findElement(By.xpath("//*[@id='dashboard-header-stats']/li[1]/div[1]")).getText();
			
			
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youtube,inum , "", "", "", "",iplaynum);
			
			
			
			WebElement spButton=driver.findElement(By.xpath("//*[@id='creator-sidebar-section-id-analytics']/h3/a/span[2]"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(2000);
			
			spButton=driver.findElement(By.xpath("//*[@id='creator-sidebar-item-id-subscribers']/a/span"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(5000);
			
			////*[@id="gwt-debug-dimension-3"]
			List<WebElement> WebElements=driver.findElements(By.xpath("//span[@class='C2KYWWB-g-e']"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			for (WebElement aa:WebElements)
			{
				if (aa.getText().equals("日期"))
				{
					System.out.println(aa.getAttribute("outerHTML"));	
					aa.click();
					break;
				}
				
			}
			
			
			//这里等的有点久
			Thread.sleep(15000);
			
			 WebElement table=driver.findElement(By.xpath("//*[@id='gwt-debug-table']/table"));
			 
			
				String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				
				 
				Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_tenxun_Fans_by_day(row.text(),inum,iplaynum);
				}
		return true;
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("driver.get Err:"+String.valueOf(System.currentTimeMillis()-t1));
		return false;
	 }
	}
	
	public static String Seach() throws InterruptedException
	{
		String sout="Youtube："+"扫描完毕";
		
		PID.clear();
		
		int iret=0;
		
		for(int i=0;i<10;i++)
		{
			moni_youtube moni=new moni_youtube();
			iret = moni.spfx(dao_grabbing.splatform_youtube,1);
			moni.closeIE();
			PID.clear();
			Thread.sleep(5000);
			if (iret==9999) break;
		}
		
		
		
		if (iret!=9999) sout=sout+" 分析视频数据失败！";
		
		{
			moni_youtube moni=new moni_youtube();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		PID.clear();
		
		{
			moni_youtube moni=new moni_youtube();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		return sout;
		
	}
	
	public static void main(String[] args) throws Exception 
	{
		
		String sday="2019年1月3日";

		
		
		String sout="Youtube：";
		int iret=0;
		
		{
			moni_youtube moni=new moni_youtube();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		/*for(int i=0;i<10;i++)
		{
			moni_youtube moni=new moni_youtube();
			iret = moni.spfx(dao_grabbing.splatform_youtube,1);
			moni.closeIE();
			Thread.sleep(5000);
			if (iret==9999) break;
		}
		
		if (iret!=9999) sout=sout+" 分析视频数据失败！";*/
		
		/*{
			moni_youtube moni=new moni_youtube();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		

		
		

	 }
}
package web.grabbing;

import java.io.IOException;
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

//秒拍模拟
public class moni_miaopai {
	
	WebDriver driver = null;
	
	String Main_handle="";//主要窗口
	
	public moni_miaopai()
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
        	driver.get("http://creator.miaopai.com/");
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
				dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_miaopai,arr[1],arr[2],arr[3]);
			}
		}
	}

	
	
	public void set_tenxun_Fans_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				String inum=dao_grabbing.reset(arr[1]);
				String iaddnum=dao_grabbing.reset(arr[2]);
				if (pb.atof(inum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_miaopai,inum , "", iaddnum, "", "","");
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
			
			int istart=ibegin/20+1;
			int jstart=ibegin % 20;
			
			 try {
					//
					
				 	WebElement spButton=driver.findElement(By.xpath("//*[@id='creator-main']/div[1]/div[2]/ul/li[3]/ul/li"));
					spButton.click(); 
					
					Thread.sleep(10000);
					
					//<em class="sum-page">157</em>
					WebElement elevator=driver.findElement(By.xpath("//*[@id='videoList']/div[3]/div[2]"));
					
					Document doc1 = Jsoup.parse(elevator.getAttribute("outerHTML"));
					
					String spage= doc1.text();
					int ipage=0;
					
					String[] page =spage.split(" ");
					for (String a1:page)
					{
						if (pb.atoi(a1)>ipage)
							ipage=pb.atoi(a1);
						
					}
					
					System.out.println("spage:"+ipage);
					
					//不支持跳页，只有一页 一页的来
					int i=1;
					while (i<=ipage)
					{	
						System.out.println("第"+i+"页");
						//等待table可以点击<em class="cur-page">4</em>
						WebElement table=driver.findElement(By.xpath("//*[@id='videoList']/div[3]/div[1]/ul"));
						
						 String sdoc=table.getAttribute("outerHTML");
						 
						 Document doc = Jsoup.parse(sdoc);
						
						 Elements rows = doc.select("li");
						 
						 for (Element row :rows)		
							{
							//#videoList > div.videoList.searchResult > div.video_lists > ul > li:nth-child(1) > div.rightPub > div.rightPub-top > p > span.text > b
							 String sname=row.selectFirst("div.rightPub > div.rightPub-top > p > span.text > b").text();
							 //#                          div.rightPub > div.rightPub-target > span:nth-child(2) > b
							 
							 String inum=row.selectFirst("div.rightPub > div.rightPub-target > span:nth-child(2) > b").text();
							 
							 inum=dao_grabbing.reset(inum);
							 
							 mylog.info(iout+"->"+ splatform+":"+sname+" inum:"+inum);
							 //#videoList > div.videoList.searchResult > div.video_lists > ul > li:nth-child(1) > div.rightPub > div.rightPub-target > span:nth-child(3) > b
							 String icollection="0";
							 
							 //#videoList > div.videoList.searchResult > div.video_lists > ul > li:nth-child(1) > div.rightPub > div.rightPub-top > p > span.time
							 String dtadd=row.selectFirst("div.rightPub > div.rightPub-top > p > span.time").text();
							 
							//修正表里面的数据， 
							dao_grabbing.insert_tvideo_byname(dao_grabbing.splatform_miaopai, sname, inum,dtadd);
							
							String iaddnum=inum;
							
							//添加日志数据
							String sday=pb.datetostr(new Date()).substring(0,10);
							
							String sSql="select top 1 * from tvideolog where splatform='"+dao_grabbing.splatform_miaopai+"' and sname='"+sname+"'"
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

							dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_miaopai, sname, "", inum, iaddnum, icollection);
							
							iout++;
							}
						 
						 	i++;
						 	if (i>ipage)
						 	{
						 		return 9999;
						 	}
						 	//翻页
						 	////*[@id="videoList"]/div[3]/div[2]/button[2]
						 	WebElement boten=driver.findElement(By.xpath("//*[@id='videoList']/div[3]/div[2]/button[2]"));
						 	boten.click();
						 	Thread.sleep(3000);
							
						 }	
						 jstart=0; //重置
						
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mylog.ErrStackTrace(e);
					return iout;
			}

			dao_grabbing.set_scanning(splatform, 9999);
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog_byday(splatform,sday);
			return 9999;
		}
	
	//所有播放量
	//粉丝分析
	public boolean fsfx()
	{
		 //前一天
		 Date date=new Date();
		//Calendar calendar =new GregorianCalendar();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);//前一天的
		date = calendar.getTime();
		SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		String sday = format.format(date);
		
		try {
			//首页，不用跳
			////*[@id="creator-home"]/div[1]/div/div/div/div[4]/span/b
			//WebElement spButton=driver.findElement(By.linkText("订阅数统计"));
			//spButton.click();  
			
			//Thread.sleep(2000);
			
			 WebElement fs=driver.findElement(By.xpath("//*[@id='creator-home']/div[1]/div/div/div/div[4]/span/b"));
			
			 
			String inum=fs.getText();
			 System.out.println(inum);
			dao_grabbing.set_tFansLog(sday,dao_grabbing.splatform_miaopai,inum,"","","","","");
			
			return true;
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
	 }
	}
	
	public static String Seach() throws InterruptedException
	{
		String sout=dao_grabbing.splatform_miaopai+"扫描完毕";
		int iret=0;

		for(int i=0;i<10;i++)
		{
			moni_miaopai moni=new moni_miaopai();
			iret = moni.spfx(dao_grabbing.splatform_miaopai,1);
			moni.closeIE();
			PID.clear();
			Thread.sleep(5000);
			if (iret==9999) break;
		}
		
		if (iret!=9999) sout=sout+" 分析视频数据失败！";
		
		/*{
			moni_miaopai moni=new moni_miaopai();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		
		
		{
			moni_miaopai moni=new moni_miaopai();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		return sout;
		
	}
	
	public static void main(String[] args) throws Exception 
	 {
		
		
		
		{
			moni_miaopai moni=new moni_miaopai();
			
			moni.fsfx();
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		/*{
			moni_miaopai moni=new moni_miaopai();
			
			moni.spfx(dao_grabbing.splatform_miaopai,1);
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		/*int iret=0;
		while (iret!=9999)
		{
			moni_tenxun moni=new moni_tenxun();
			if (moni.cheaklogin())
			{
				iret=dao_grabbing.get_scanning(dao_grabbing.splatform_tenxun);
				mylog.info(dao_grabbing.splatform_tenxun+"开始位置:"+iret);
				iret=moni.spfx(dao_grabbing.splatform_tenxun,iret);
				mylog.info("本次成功:"+iret);
			}
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		

	 }
}
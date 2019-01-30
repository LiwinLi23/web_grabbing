package web.grabbing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import main.PID;
import my.function.mylog;
import my.function.pb;
import tool.selenium.selenium_set;

public class moni_dayu {
	
	WebDriver driver = null;
	
	public moni_dayu()
	{
		//#实例化谷歌设置选项
		ChromeOptions Options = new ChromeOptions();
		
		Options.addArguments("user-data-dir="+selenium_set.userdatadir);
		// 设置 chrome 的路径  
        System.setProperty("webdriver.chrome.driver", selenium_set.chrome_driver);
        
        
        
        // 创建一个 chrome 的浏览器实例
        driver = new ChromeDriver(Options);
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);//设置超时时间
        
       
        
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

    	//最大化
         System.out.println(driver.manage().window().getSize());
    	/*driver.manage().window().maximize();
        try {
    			Thread.sleep(3000);
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}*/
        
    	
    	//访问百度
        try
        {
        	driver.get("https://mp.dayu.com/");
        }
        catch(Exception e)
        {
        	mylog.ErrStackTrace(e);
        	
        }
    	
    	
    	//等待5秒，等第count条查询结果加载完
    	//WebDriverWait wait = new WebDriverWait(driver, 30);
				
	}
	
	
	public void closeIE()
	{
		try
        {
			Thread.sleep(1000);
			driver.close();
	        driver.quit();
			/*String closename="chrome.exe";
    		PID.kill(closename);
    		closename="chromedriver.exe";
    		PID.Fkill(closename);	*/
        }
        catch(Exception e)
        {
    		String closename="chrome.exe";
    		PID.kill(closename);
    		closename="chromedriver.exe";
    		PID.Fkill(closename);	
        }

	}
	
	public void set_vodie_by_day(String splatform,String sname,String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>5)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				//日期                              播放次数 播放人数     播放时长(分钟) 播放完成度 评论 收藏 顶 踩
				//[2018-11-29, 3.7万, 2.7万, 15.8万, 59.94%, 21, 3, 32, 16]
				//System.out.println(sname +":"+ sline);
				dao_grabbing.set_alone_video(sday, splatform, sname, "",
						dao_grabbing.reset(arr[1]), dao_grabbing.reset(arr[1]), dao_grabbing.reset(arr[6]));
			}
		}
		
	}
	
	
	public void set_uc_money_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>5)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				if (pb.atof(arr[1])!=0)
				dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_uc,arr[1],arr[3],arr[5]);
			}
		}
	}
	
	public void set_youku_money1_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>2)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				if (pb.atof(arr[1])!=0)
				dao_grabbing.set_alone_money1_youku(sday, dao_grabbing.splatform_youku,arr[1]);
			}
		}
	}
	
	public void set_youku_money2_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>2)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				if (pb.atof(arr[2])!=0)
				dao_grabbing.set_alone_money2_youku(sday, dao_grabbing.splatform_youku,arr[2]);
			}
		}
	}
	
	
	public void set_uc_Fans_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>4)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				String inum=dao_grabbing.reset(arr[1]);
				String iaddnum=dao_grabbing.reset(arr[4]);
				if (pb.atof(inum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_uc,inum , "", iaddnum, "", "","");
			}
		}
	}
	
	public void set_youku_Fans_by_day(String sline,String inum,String iplaynum)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				String iaddnum=dao_grabbing.reset(arr[3]);
				if (pb.atof(iaddnum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youku, inum, "", iaddnum, "", "",iplaynum);
			}
		}
	}
	
	public void set_tudou_Fans_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06 0 20.32 0 0.74
				System.out.println(sline);
				String iaddnum=dao_grabbing.reset(arr[3]);
				if (pb.atof(iaddnum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_tudou, iaddnum, "", iaddnum, "", "","");
			}
		}
	}
	
	
	//有时候需要重新登录一次， 每次执行的时候运行下这个
	public boolean cheaklogin()
	{
		
		//<title>大鱼号官网</title>
		try
		{
			Thread.sleep(4000);//标题可能要变
			
			String stitle=driver.getTitle();
			//登录-腾讯内容开放平台
			if (stitle.equals("大鱼号官网"))
			{
				WebElement spButton=driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/a/div/div/span"));
				spButton.click();
				Thread.sleep(1000);
				
				
				
				
				String cheak=driver.findElement(By.xpath("//*[@id='loginbox']")).getAttribute("outerHTML");
				
				if (cheak.indexOf("YT-nloginSubmit")>0)
				{
					//用户名密码登录
					spButton=driver.findElement(By.xpath("//*[@id='YT-nloginSubmit']"));
					spButton.click();
					
				}
				else
				{
					//快捷登录
					spButton=driver.findElement(By.xpath("//*[@id='YT-loginFrame-container']/div/div[2]/div[4]/a"));
					spButton.click();
				}
				
				Thread.sleep(5000);
			}
			
			stitle=driver.getTitle();
			
			System.out.println(stitle);

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
		cheaklogin();
		int iout=ibegin;//返回值
		
		int istart=ibegin/10+1;
		int jstart=ibegin % 10;
		
		 try {
				//<a href="/dashboard/stat/video" id="w-menu-stat_video" class=" "><span class="w-menu_title">视频分析</span></a>
				WebElement spButton=driver.findElement(By.id("w-menu-stat_video"));
				spButton.click();  
				
				Thread.sleep(1000);
				
				//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText("视频详情"))); //显示等待调用
				//<a href="javascript:;">视频详情</a>
				
				spButton=driver.findElement(By.linkText("视频详情"));
				spButton.click(); 
				
				Thread.sleep(5000);
				
				//跳到对应的分析页面
				spButton=driver.findElement(By.linkText(splatform));
				spButton.click(); 
				Thread.sleep(5000);
				
				//<div class="elevator"><div class="f-pagination-go"><input type="text"> <span>GO</span></div>

				WebElement elevator=driver.findElement(By.xpath("//div[@class='elevator']"));
				String spage= elevator.findElement(By.xpath("//span[@class='text']")).getText();
				System.out.println("spage:"+spage);
				
				int ipage= pb.atoi(spage);
				
				for (int i=istart;i<=ipage;i++)
				{
					//跳转到第N页
					//<div class="f-pagination-go"><input type="text" data-spm-anchor-id="a2s0i.db_stat_video.content.i1.4c1e3caao0QNsW" placeholder="" data-placeholder=""> <span data-spm-anchor-id="a2s0i.db_stat_video.content.i2.4c1e3caao0QNsW">GO</span></div>
					WebElement go=driver.findElement(By.xpath("//div[@class='f-pagination-go']"));
					WebElement go_input=go.findElement(By.xpath("//input[@type='text']"));
					go_input.sendKeys(String.valueOf(i));
					
					WebElement gopage=go.findElement(By.cssSelector("span"));
					gopage.click();
					
					Thread.sleep(1000);
					
					//等待table可以点击<table class="w-table">  //
					WebElement input=driver.findElement(By.xpath("//table[@class='w-table']"));
					String stable=input.getAttribute("outerHTML");
					Document doctable = Jsoup.parse(stable);
					Elements ztables = doctable.select("tbody>tr");
					System.out.println("ztables:"+ztables.size());
					
					Thread.sleep(1000);
					System.out.println("翻页到："+i);
					
					//<a href="javascript:;">分析</a>
					 
					 List<WebElement> hreffxs=driver.findElements(By.linkText("分析"));
					 for (int j=jstart;j<hreffxs.size();j++)
					 {
						 String dtadd="";
						 
						 if (ztables.size()>j)
						 {
							 Element a1=ztables.get(j);
						 //#tableList > tbody > tr:nth-child(1) > td.text-center
						 	 dtadd=(a1.selectFirst("td:nth-child(2)").text());
						 }
						 
						 
						 WebElement fx= hreffxs.get(j);
						 fx.click();
						
						 Thread.sleep(2000);
						 
						 //<span class="title">选出最适合人鱼公主的鱼尾礼服吧!+换装游戏第三弹!+小伶玩具</span> 
						 String sname=driver.findElement(By.xpath("//span[@class='title']")).getText();
						 //System.out.println(sname);
						 
						 //<table class="w-table"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>8.6万</span></div></td><td><div><span>6.5万</span></div></td><td><div><span>36.9万</span></div></td><td><div><span>--</span></div></td><td><div><span>48</span></div></td><td><div><span>26</span></div></td><td><div><span>41</span></div></td><td><div><span>52</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>2.1万</span></div></td><td><div><span>1.6万</span></div></td><td><div><span>9.2万</span></div></td><td><div><span>49.80%</span></div></td><td><div><span>12</span></div></td><td><div><span>6.50</span></div></td><td><div><span>10.25</span></div></td><td><div><span>13</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.5万</span></div></td><td><div><span>10.0万</span></div></td><td><div><span>65.04%</span></div></td><td><div><span>7</span></div></td><td><div><span>0</span></div></td><td><div><span>2</span></div></td><td><div><span>6</span></div></td></tr><tr><td><div><span>2018-11-27</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.4万</span></div></td><td><div><span>8.9万</span></div></td><td><div><span>57.27%</span></div></td><td><div><span>7</span></div></td><td><div><span>2</span></div></td><td><div><span>3</span></div></td><td><div><span>3</span></div></td></tr><tr><td><div><span>2018-11-26</span></div></td><td><div><span>1.2万</span></div></td><td><div><span>9512</span></div></td><td><div><span>4.4万</span></div></td><td><div><span>38.92%</span></div></td><td><div><span>9</span></div></td><td><div><span>1</span></div></td><td><div><span>4</span></div></td><td><div><span>8</span></div></td></tr><tr><td><div><span>2018-11-25</span></div></td><td><div><span>3.9万</span></div></td><td><div><span>2.7万</span></div></td><td><div><span>13.6万</span></div></td><td><div><span>37.97%</span></div></td><td><div><span>25</span></div></td><td><div><span>23</span></div></td><td><div><span>32</span></div></td><td><div><span>35</span></div></td></tr> <!----></tbody></table>
						 List<WebElement> tables=driver.findElements(By.xpath("//table[@class='w-table']"));
						 
						 if (tables.size()==2)
						 {
							 String sdoc=tables.get(1).getAttribute("outerHTML");
							 
							 Document doc = Jsoup.parse(sdoc);
							
							Elements rows = doc.select("tr");
							
							System.out.println(iout+"->"+ splatform+":"+sname+" rows:"+rows.size());
							
							//修正表里面的数据， 
							dao_grabbing.insert_tvideo_byname(splatform, sname, "",dtadd);
							
							for (Element row :rows)		
							{
								//System.out.println("-----------------------");	
								
								//System.out.println(row.html());	
								//System.out.println(row.text());	
								//System.out.println(iout+"->"+ splatform+":"+sname+":"+row.text());
								set_vodie_by_day(splatform,sname,row.text());
							}
						 }
						 
						 iout++;
						 dao_grabbing.set_scanning(splatform, iout);
						 
						 //内存占用有点大，每200个重启一次IE算了
						 if ((iout % 200)==0)
							 return iout;
						 
						 //<button class="w-btn w-btn_default"><i class="w-icon w-icon-back1"></i>返回</button>
						 WebElement button=driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
						 button.click();

						 
						 //<div class="f-pagination-go"><input type="text"> <span>GO</span></div>
						
						Thread.sleep(500);
						
						
					 }	
					 jstart=0; //重置
					
				}
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
		cheaklogin();
		try {
			//<a href="/dashboard/stat/overview" id="w-menu-" class=" w-menu-active  "><span class="w-menu_title">收益分析</span></a>
			//WebElement spButton=driver.findElement(By.cssSelector("span[class='w-menu_title']"));
			WebElement spButton=driver.findElement(By.linkText("收益分析"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(3000);
			
			
			
			//总收入
			
			WebElement 
			spButton1=driver.findElement(By.xpath("/html/body/div/div/div[4]/div/div[1]/div/div/div[3]/div/div[1]/span[2]"));
			
			String dallmoney=spButton1.getText();
			
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youku,"",dallmoney,"","","","");
			
			
			
			spButton=driver.findElements(By.linkText("UC分润")).get(1);
			spButton.click(); 
			
			Thread.sleep(2000);
			
			//<table class="w-table"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>8.6万</span></div></td><td><div><span>6.5万</span></div></td><td><div><span>36.9万</span></div></td><td><div><span>--</span></div></td><td><div><span>48</span></div></td><td><div><span>26</span></div></td><td><div><span>41</span></div></td><td><div><span>52</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>2.1万</span></div></td><td><div><span>1.6万</span></div></td><td><div><span>9.2万</span></div></td><td><div><span>49.80%</span></div></td><td><div><span>12</span></div></td><td><div><span>6.50</span></div></td><td><div><span>10.25</span></div></td><td><div><span>13</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.5万</span></div></td><td><div><span>10.0万</span></div></td><td><div><span>65.04%</span></div></td><td><div><span>7</span></div></td><td><div><span>0</span></div></td><td><div><span>2</span></div></td><td><div><span>6</span></div></td></tr><tr><td><div><span>2018-11-27</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.4万</span></div></td><td><div><span>8.9万</span></div></td><td><div><span>57.27%</span></div></td><td><div><span>7</span></div></td><td><div><span>2</span></div></td><td><div><span>3</span></div></td><td><div><span>3</span></div></td></tr><tr><td><div><span>2018-11-26</span></div></td><td><div><span>1.2万</span></div></td><td><div><span>9512</span></div></td><td><div><span>4.4万</span></div></td><td><div><span>38.92%</span></div></td><td><div><span>9</span></div></td><td><div><span>1</span></div></td><td><div><span>4</span></div></td><td><div><span>8</span></div></td></tr><tr><td><div><span>2018-11-25</span></div></td><td><div><span>3.9万</span></div></td><td><div><span>2.7万</span></div></td><td><div><span>13.6万</span></div></td><td><div><span>37.97%</span></div></td><td><div><span>25</span></div></td><td><div><span>23</span></div></td><td><div><span>32</span></div></td><td><div><span>35</span></div></td></tr> <!----></tbody></table>
			 List<WebElement> tables=driver.findElements(By.xpath("//table[@class='w-table']"));
			 
			 if (tables.size()==1)
			 {
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_uc_money_by_day(row.text());
				}
			 }
			 
			 
			 //优酷流量分成
			 spButton=driver.findElement(By.linkText("优酷流量分成")); 
			 spButton.click(); 
			 Thread.sleep(5000);
			 
			 tables=driver.findElements(By.xpath("//table[@class='w-table']"));
			 
			 if (tables.size()==1)
			 {
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_youku_money1_by_day(row.text());
				}
			 }
			 
			 //优酷粉丝激励
			 spButton=driver.findElement(By.linkText("优酷粉丝激励")); 
			 spButton.click(); 
			 Thread.sleep(1000);
			 //<iframe id="mpIframe" frameborder="0" scrolling="no" width="100%" style="display: block; overflow-y: hidden; height: 600px;" src="https://mp.youku.com/income/fans/dayu_list.html?spm=a2s0i.db_stat_overview.content.4.324b3caa7woF7G&amp;el=mpIframe" data-spm-anchor-id="a2s0i.db_stat_ykFans.0.i0.4eed3caa3vtS92" data-spm-act-id="a2s0i.db_stat_ykFans.0.i0.4eed3caa3vtS92"></iframe>
			 
			 //<div class="w-contentbox">
			 WebElement div=driver.findElement(By.cssSelector("div[class='w-contentbox']"));
			 System.out.println(div.getAttribute("outerHTML"));
			 
			 
			// driver.switchTo().frame("mpIframe");//这个没有
			 driver.switchTo().frame(0);

			 //<table class="table">
			 tables=driver.findElements(By.cssSelector("table[class='table']"));
			 
			 if (tables.size()==1)
			 {
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_youku_money2_by_day(row.text());
				}
			 }
			
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
		
		
		cheaklogin();
		try {
			//<a href="/dashboard/stat/overview" id="w-menu-" class=" w-menu-active  "><span class="w-menu_title">收益分析</span></a>
			//WebElement spButton=driver.findElement(By.cssSelector("span[class='w-menu_title']"));
			WebElement spButton=driver.findElement(By.linkText("用户分析"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(3000);
			
			spButton=driver.findElements(By.linkText("UC")).get(0);
			spButton.click(); 
			
			Thread.sleep(2000);
			
			//<table class="w-table"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>8.6万</span></div></td><td><div><span>6.5万</span></div></td><td><div><span>36.9万</span></div></td><td><div><span>--</span></div></td><td><div><span>48</span></div></td><td><div><span>26</span></div></td><td><div><span>41</span></div></td><td><div><span>52</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>2.1万</span></div></td><td><div><span>1.6万</span></div></td><td><div><span>9.2万</span></div></td><td><div><span>49.80%</span></div></td><td><div><span>12</span></div></td><td><div><span>6.50</span></div></td><td><div><span>10.25</span></div></td><td><div><span>13</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.5万</span></div></td><td><div><span>10.0万</span></div></td><td><div><span>65.04%</span></div></td><td><div><span>7</span></div></td><td><div><span>0</span></div></td><td><div><span>2</span></div></td><td><div><span>6</span></div></td></tr><tr><td><div><span>2018-11-27</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.4万</span></div></td><td><div><span>8.9万</span></div></td><td><div><span>57.27%</span></div></td><td><div><span>7</span></div></td><td><div><span>2</span></div></td><td><div><span>3</span></div></td><td><div><span>3</span></div></td></tr><tr><td><div><span>2018-11-26</span></div></td><td><div><span>1.2万</span></div></td><td><div><span>9512</span></div></td><td><div><span>4.4万</span></div></td><td><div><span>38.92%</span></div></td><td><div><span>9</span></div></td><td><div><span>1</span></div></td><td><div><span>4</span></div></td><td><div><span>8</span></div></td></tr><tr><td><div><span>2018-11-25</span></div></td><td><div><span>3.9万</span></div></td><td><div><span>2.7万</span></div></td><td><div><span>13.6万</span></div></td><td><div><span>37.97%</span></div></td><td><div><span>25</span></div></td><td><div><span>23</span></div></td><td><div><span>32</span></div></td><td><div><span>35</span></div></td></tr> <!----></tbody></table>
			 List<WebElement> tables=driver.findElements(By.xpath("//table[@class='w-table']"));
			 
			 if (tables.size()==1)
			 {
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_uc_Fans_by_day(row.text());
				}
			 }
			 
			 
			 //优酷粉丝
			 spButton=driver.findElement(By.linkText("优酷")); 
			 spButton.click(); 
			 Thread.sleep(2000);
			 get_youku.get_page_fs();
			 /*tables=driver.findElements(By.xpath("//table[@class='w-table']"));
			 
			 if (tables.size()==1)
			 {
				 Map aa=get_youku.get_page_fs();
				 
				 String iplaynum=(String) aa.get("iplaynum");
				 String inum=(String) aa.get("inum");
				 
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_youku_Fans_by_day(row.text(),inum,iplaynum);
				}
			 }*/
			 
			 //土豆粉丝
			 spButton=driver.findElement(By.linkText("土豆")); 
			 spButton.click(); 
			 Thread.sleep(5000);
			 
			 tables=driver.findElements(By.xpath("//table[@class='w-table']"));
			 
			 if (tables.size()==1)
			 {
				 String sdoc=tables.get(0).getAttribute("outerHTML");
				 
				 Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_tudou_Fans_by_day(row.text());
				}
			 }
			
		return true;	
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;	
	 }
	}
	
	
	public static String Seach() throws InterruptedException
	{
		String sout=dao_grabbing.splatform_youku+"扫描完毕";
		int iret=0;
		
		//检查 一次登录状态
		{
			moni_dayu moni=new moni_dayu();
			if (!moni.cheaklogin())
				{
					sout=sout+"登录状态有问题！";
					moni.closeIE();
					return sout;
				}
			moni.closeIE();
			
			Thread.sleep(10000);
		}

		for(int i=0;i<20;i++)
		{
			moni_dayu moni=new moni_dayu();
			iret = moni.spfx(dao_grabbing.splatform_youku,iret);
			moni.closeIE();
			Thread.sleep(5000);
			PID.clear();
			if (iret==9999) break;
		}
		
		if (iret!=9999) sout=sout+" 分析视频数据失败！";
		
		{
			moni_dayu moni=new moni_dayu();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		PID.clear();
		
		{
			moni_dayu moni=new moni_dayu();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		return sout;
		
	}
	
	public static void main(String[] args) throws Exception 
	 {

		/*{
			moni_dayu moni=new moni_dayu();
			moni.cheaklogin();
			moni.closeIE();
			Thread.sleep(10000);
		}*/
		
		/*{
			moni_dayu moni=new moni_dayu();
			moni.syfx();
			moni.closeIE();
			Thread.sleep(10000);
		}*/
		

		int iret=0;
		while (iret!=9999)
		{
			moni_dayu moni=new moni_dayu();
			iret=moni.spfx(dao_grabbing.splatform_youku,iret);
			mylog.Err("本次成功:"+iret);
			moni.closeIE();
			Thread.sleep(5000);
		}

		/*iret=0;
		while (iret!=9999)
		{
			moni_dayu moni=new moni_dayu();
			iret=moni.spfx(dao_grabbing.splatform_tudou,iret);
			mylog.Err("本次成功:"+iret);
			moni.closeIE();
			Thread.sleep(5000);
		}*/

	 }

}

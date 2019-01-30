package web.grabbing;

import java.io.IOException;
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
import my.function.myhttp;
import my.function.mylog;
import my.function.pb;
import tool.selenium.selenium_set;

//美拍，主要是获取相关视频url 然后通过html 进行扫描, 这个估计暂时没用
public class moni_meipai {
	
	WebDriver driver = null;
	
	String Main_handle="";//主要窗口
	
	public moni_meipai()
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
        	driver.get("https://www.meipai.com/user/1074764441");
        	mylog.info("driver.get:"+String.valueOf(System.currentTimeMillis()-t1));
        	
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
				dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_meipai,arr[1],arr[2],arr[3]);
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
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_meipai,inum , "", iaddnum, "", "","");
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
	
	//通过http 完成所有视频的分析
	public void spfxbyhttpurl()
	{
		String sSql="select * from tvideo where splatform='"+dao_grabbing.splatform_meipai+"'";
		List<Map> urllst= db.OpenSql_toList("pftfdb", sSql);
		if (urllst==null) return;
		
		for (Map murl:  urllst)
		{
			String surl=murl.get("surl")==null? "" :  (String) murl.get("surl");
			//String sname =murl.get("sname")==null? "" :  (String) murl.get("sname");
			
			try {
				Document doc = Jsoup.connect(surl).get();
				
				Element ecnt=  doc.selectFirst("div[class=detail-info pr]"); 
				
				String inum=ecnt.selectFirst("div[class=detail-location]").text();
				
				inum=inum.replaceAll("播放", "");
				inum=dao_grabbing.reset(inum);
				
				String sname=ecnt.selectFirst("h1").text();
				String icollection=ecnt.selectFirst("span[class='detail-like dbl pr cp']").text(); //搜藏
				icollection=dao_grabbing.reset(icollection);
				//String a2=ecnt.selectFirst("span[class='detail-comment dbl pr']").text(); //评论
				
				//修正表里面的数据， 
				dao_grabbing.set_tvideo_byurl(dao_grabbing.splatform_meipai, surl, sname, inum,"");
				
				String iaddnum=inum;
				
				//添加日志数据
				String sday=pb.datetostr(new Date()).substring(0,10);
				
				sSql="select top 1 * from tvideolog where convert(VARCHAR(10),dtinsert,120) <>'"+sday+"' order by dtinsert desc";
				
				Map ret=db.OpenOneSql_toMap("pftfdb", sSql);
				if (ret!=null)
				{
					//计算增量
					iaddnum=String.valueOf(pb.atoi(inum)-pb.atoi((String)ret.get("inum")));
				}

				dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_meipai, sname, "", inum, iaddnum, icollection);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
	//视频分析
	public int spfx(String splatform,int ibegin) //开始也页面。开始的位置
	{
		int iout=ibegin;//返回值
		
		int istart=ibegin/8+1;
		int jstart=ibegin % 8;
		
		 try {
				
				//<a href="/article/videoStatistic" class="menu-sub-text">视频统计</a>
				
			 	WebElement spButton=driver.findElement(By.linkText("视频统计"));
				spButton.click(); 
				
				Thread.sleep(2000);
				
				//<em class="sum-page">157</em>
				WebElement elevator=driver.findElement(By.xpath("//em[@class='sum-page']"));
				String spage= elevator.getText();
				System.out.println("spage:"+spage);
				
				int ipage= pb.atoi(spage);
				
				for (int i=istart;i<=ipage;i++)
				{
					//跳转到第N页
					//<li class="pageInput"><input type="text" class="input-text sm" value=""><button type="button" class="btn btn-default btn-sm go">跳转</button></li>
					WebElement go=driver.findElement(By.xpath("//li[@class='pageInput']"));
					WebElement go_input=go.findElement(By.xpath("//input[@class='input-text sm']"));
					go_input.sendKeys(String.valueOf(i));
					
					WebElement gopage=go.findElement(By.cssSelector("button"));
					gopage.click();
					
					Thread.sleep(2000);
					
					//等待table可以点击<em class="cur-page">4</em>
					WebElement input=driver.findElement(By.xpath("//em[@class='cur-page']"));
					
					//翻页失败
					if (!input.getText().equals(String.valueOf(i)))
					{
						return  iout;
						
					}
					
					System.out.println(driver.getWindowHandles());
					
					
					Thread.sleep(1000);
					System.out.println("翻页到："+i);
					
					//<table id="tableList"
					
					WebElement table=driver.findElement(By.id("tableList"));
					 
					//<a href="/article/singleVideoStatistic?articleId=a07584q1q0q" target="_blank" class="link realTrendLink">详情</a>
					//主表里面的元素
					 /*String stable=table.getAttribute("outerHTML");
					 Document doctable = Jsoup.parse(stable);
					 Elements ztables = doctable.select("tr");*/

					 List<WebElement> hreffxs=table.findElements(By.linkText("详情"));
					 for (int j=jstart;j<hreffxs.size();j++)
					 {
						 //跳页面
						 WebElement fx= hreffxs.get(j);
						 fx.click();
						
						 Thread.sleep(2000);
						 
						 //跳转到其他页面操作
						 Set<String> winhandles=  driver.getWindowHandles();
						 for(java.util.Iterator<String> iter = winhandles.iterator(); iter.hasNext();){
					            String value =  iter.next();
					            
					            if (!value.equals(Main_handle))
					            {
					            	driver.switchTo().window(value);	
					            	break;
					            }
					            
					        }

						 String sname=driver.findElement(By.xpath("//*[@id='detailArticleTotalData']/div/h3/a[1]")).getText();
						 System.out.println(sname);
						 
						 //总播放量
						 String sznum=driver.findElement(By.cssSelector("#detailArticleTotalData > ul > li:nth-child(1) > span.text-number")).getText();
						 
						 //<table class="w-table"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>8.6万</span></div></td><td><div><span>6.5万</span></div></td><td><div><span>36.9万</span></div></td><td><div><span>--</span></div></td><td><div><span>48</span></div></td><td><div><span>26</span></div></td><td><div><span>41</span></div></td><td><div><span>52</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>2.1万</span></div></td><td><div><span>1.6万</span></div></td><td><div><span>9.2万</span></div></td><td><div><span>49.80%</span></div></td><td><div><span>12</span></div></td><td><div><span>6.50</span></div></td><td><div><span>10.25</span></div></td><td><div><span>13</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.5万</span></div></td><td><div><span>10.0万</span></div></td><td><div><span>65.04%</span></div></td><td><div><span>7</span></div></td><td><div><span>0</span></div></td><td><div><span>2</span></div></td><td><div><span>6</span></div></td></tr><tr><td><div><span>2018-11-27</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.4万</span></div></td><td><div><span>8.9万</span></div></td><td><div><span>57.27%</span></div></td><td><div><span>7</span></div></td><td><div><span>2</span></div></td><td><div><span>3</span></div></td><td><div><span>3</span></div></td></tr><tr><td><div><span>2018-11-26</span></div></td><td><div><span>1.2万</span></div></td><td><div><span>9512</span></div></td><td><div><span>4.4万</span></div></td><td><div><span>38.92%</span></div></td><td><div><span>9</span></div></td><td><div><span>1</span></div></td><td><div><span>4</span></div></td><td><div><span>8</span></div></td></tr><tr><td><div><span>2018-11-25</span></div></td><td><div><span>3.9万</span></div></td><td><div><span>2.7万</span></div></td><td><div><span>13.6万</span></div></td><td><div><span>37.97%</span></div></td><td><div><span>25</span></div></td><td><div><span>23</span></div></td><td><div><span>32</span></div></td><td><div><span>35</span></div></td></tr> <!----></tbody></table>
						 WebElement table1=driver.findElement(By.id("tableList"));
						 
						
							 String sdoc=table1.getAttribute("outerHTML");
							 
							 Document doc = Jsoup.parse(sdoc);
							
							Elements rows = doc.select("tr");
							
							mylog.info(iout+"->"+ splatform+":"+sname+" rows:"+rows.size());
							
							for (Element row :rows)		
							{
								//System.out.println("-----------------------");	
								
								//System.out.println(row.html());	
								//System.out.println(row.text());	
								//System.out.println(iout+"->"+ splatform+":"+sname+":"+row.text());
								set_vodie_by_day(splatform,sname,sznum,row.text());
							}
						 
						 
						 iout++;
						 dao_grabbing.set_scanning(splatform, iout);
						 
						 //内存占用有点大，每200个重启一次IE算了
						 if ((iout % 200)==0)
							 return iout;
						 
						if (!driver.getWindowHandle().equals(Main_handle))
						{
							driver.close();
						}
						
						driver.switchTo().window(Main_handle);//回到主窗口

						 
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
	
	//所有视频抓取
	public void spzq()
	{
		try {
			
			///html/body/div[3]/div[1]/h2/span/span[2]   //body > div.feed-content.clearfix > div.feed-left.fl > h2 > span > span.js-span-a.type-l-t2.dbl
			WebElement spButton=driver.findElement(By.xpath("/html/body/div[3]/div[1]/h2/span/span[2]"));
			//WebElement spButton=driver.findElement(By.linkText("收益统计"));
			//WebElement button=  driver.findElement(By.xpath("//button[@class='w-btn w-btn_default']"));
			spButton.click();  
			
			Thread.sleep(2000);
			
			while(true)
			{
			
				//获取列表
				WebElement mediasList=driver.findElement(By.xpath("//*[@id='mediasList']"));
				//System.out.println(mediasList.getAttribute("outerHTML"));
				
				////*[@id="mediasList"]/li[1]
				List<WebElement> listhelf=mediasList.findElements(By.xpath("li/div[1]/a"));
				
				for (WebElement row:listhelf)
				{
					System.out.println(row.getAttribute("href"));
				}
					
				driver.findElement(By.xpath("//*[@id='paging']")).getText();
				//下一页  //*[@id="paging"]/a
				WebElement paging=driver.findElement(By.linkText("下一页"));
				paging.click();
			}
			
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	 }
	}
	
	
	
	//粉丝分析
	public void fsfx()
	{
		try {
			///html/body/div[1]/div[2]/div/div/div[1]/ul/li[3]/ul/li[3]/a
			WebElement spButton=driver.findElement(By.linkText("订阅数统计"));
			spButton.click();  
			
			Thread.sleep(2000);
			
			//<table class="w-table"><thead><tr><th>日期</th><th>播放次数</th><th>播放人数</th><th>播放时长(分钟)</th><th>播放完成度</th><th>评论</th><th>收藏</th><th>顶</th><th>踩</th></tr></thead> <tbody><tr><td><div><span>汇总</span></div></td><td><div><span>8.6万</span></div></td><td><div><span>6.5万</span></div></td><td><div><span>36.9万</span></div></td><td><div><span>--</span></div></td><td><div><span>48</span></div></td><td><div><span>26</span></div></td><td><div><span>41</span></div></td><td><div><span>52</span></div></td></tr><tr><td><div><span>平均</span></div></td><td><div><span>2.1万</span></div></td><td><div><span>1.6万</span></div></td><td><div><span>9.2万</span></div></td><td><div><span>49.80%</span></div></td><td><div><span>12</span></div></td><td><div><span>6.50</span></div></td><td><div><span>10.25</span></div></td><td><div><span>13</span></div></td></tr><tr><td><div><span>2018-11-28</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.5万</span></div></td><td><div><span>10.0万</span></div></td><td><div><span>65.04%</span></div></td><td><div><span>7</span></div></td><td><div><span>0</span></div></td><td><div><span>2</span></div></td><td><div><span>6</span></div></td></tr><tr><td><div><span>2018-11-27</span></div></td><td><div><span>1.7万</span></div></td><td><div><span>1.4万</span></div></td><td><div><span>8.9万</span></div></td><td><div><span>57.27%</span></div></td><td><div><span>7</span></div></td><td><div><span>2</span></div></td><td><div><span>3</span></div></td><td><div><span>3</span></div></td></tr><tr><td><div><span>2018-11-26</span></div></td><td><div><span>1.2万</span></div></td><td><div><span>9512</span></div></td><td><div><span>4.4万</span></div></td><td><div><span>38.92%</span></div></td><td><div><span>9</span></div></td><td><div><span>1</span></div></td><td><div><span>4</span></div></td><td><div><span>8</span></div></td></tr><tr><td><div><span>2018-11-25</span></div></td><td><div><span>3.9万</span></div></td><td><div><span>2.7万</span></div></td><td><div><span>13.6万</span></div></td><td><div><span>37.97%</span></div></td><td><div><span>25</span></div></td><td><div><span>23</span></div></td><td><div><span>32</span></div></td><td><div><span>35</span></div></td></tr> <!----></tbody></table>
			 WebElement table=driver.findElement(By.xpath("//*[@id='tableList']"));
			 
			
				String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				 
				Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("tr");
				for (Element row :rows)		
				{
					set_tenxun_Fans_by_day(row.text());
				}
			 
			
			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	 }
	}
	
	public static void main(String[] args) throws Exception 
	 {
		
		String surl="https://www.meipai.com/media/1064060056";
		try {
			Document doc = Jsoup.connect(surl).get();
			
			//String html=myhttp.Get(surl);

			//Document doc = Jsoup.parse(html);
			
			//body > div.detail-content.clearfix > div.detail-left.fl.pr > div.detail-info.pr > div.detail-location > i
			//String sname= doc.selectFirst("h1[class=detail-description break js-convert-emoji js-detail-desc]").text();
			Element ecnt=  doc.selectFirst("div[class=detail-info pr]"); 
			
			String inum=ecnt.selectFirst("div[class=detail-location]").text();
			
			inum=inum.replaceAll("播放", "");
			inum=dao_grabbing.reset(inum);
			
			String sname=ecnt.selectFirst("h1").text();
			String icollection=ecnt.selectFirst("span[class='detail-like dbl pr cp']").text(); //搜藏
			icollection=dao_grabbing.reset(icollection);
			//String a2=ecnt.selectFirst("span[class='detail-comment dbl pr']").text(); //评论
			
			//修正表里面的数据， 
			dao_grabbing.set_tvideo_byurl(dao_grabbing.splatform_meipai, surl, sname, inum,"");
			
			String iaddnum=inum;
			
			//添加日志数据
			String sday=pb.datetostr(new Date()).substring(0,10);
			
			String sSql="select top 1 * from tvideolog where convert(VARCHAR(10),dtinsert,120) <>'"+sday+"' order by dtinsert desc";
			
			Map ret=db.OpenOneSql_toMap("pftfdb", sSql);
			if (ret!=null)
			{
				//计算增量
				iaddnum=String.valueOf(pb.atoi(inum)-pb.atoi((String)ret.get("inum")));
			}

			dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_meipai, sname, "", inum, iaddnum, icollection);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*{
			moni_meipai moni=new moni_meipai();
			
			moni.spzq();
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		/*{
			moni_tenxun moni=new moni_tenxun();
			if (moni.cheaklogin())
			{
				moni.fsfx();	
			}
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
		



		
		
		//moni.spfx(dao_grabbing.splatform_uc,1);
		//moni.spfx(dao_grabbing.splatform_youku,1);
		//moni.spfx(dao_grabbing.splatform_tudou,1);
		//moni.syfx();
		
		

		//moni.closeIE();
	 }
}
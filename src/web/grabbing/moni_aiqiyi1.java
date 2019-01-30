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


//爱奇艺的数据， 上午没有，下午才有
public class moni_aiqiyi1 {
	
	WebDriver driver = null;
	
	String Main_handle="";//主要窗口
	
	public moni_aiqiyi1()
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
        	driver.get("https://mp.iqiyi.com/finance/income");
        	mylog.info("driver.get:"+String.valueOf(System.currentTimeMillis()-t1));
        	
            Main_handle=driver.getWindowHandle();
            System.out.println("Main_handle:"+Main_handle);
            Thread.sleep(1000);
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
	
	public void set_vodie_by_day(String splatform,String sday,String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>3)
		{
			String sname=arr[0];
			if (!sname.equals("所有视频合计"))
			{
				//所有视频合计 1667637 660067 1133 0 - -
				//学习颜色！搞笑父女一起玩切蔬果游戏！ 128287 60366 115 0 3分23秒 49.5% 查看详情
				//String sday,String splatform,String sname,String snum,String iplaynum,String iaddnum,String icollection
				dao_grabbing.set_alone_video(sday, splatform, sname, "", dao_grabbing.reset(arr[2]),
						 dao_grabbing.reset(arr[2]), arr[3]);
			}
		}
		
	}
	
	
	public void set_tengxun_money_by_day(String sline)
	{
		String[] arr=sline.split(" ");
		
		if (arr.length>1)
		{
			String sday=arr[0];
			if (sday.length()==10)
			{
				////2018-11-28 21.06
				System.out.println(sline);
				if (pb.atof(arr[1])!=0)
				//dao_grabbing.set_alone_money(sday, dao_grabbing.splatform_aiqiyi1,arr[1],arr[1],arr[1]);
				dao_grabbing.set_tFansLog(sday,dao_grabbing.splatform_aiqiyi1,"","","",arr[1],"","");
			}
		}
	}

	
	//视频分析另外一个地方抓发布时间，总播放量，麻烦啊
	public int spfxex(String splatform,int ibegin) //开始也页面。开始的位置
	{
		int iout=ibegin;//返回值
		
		int istart=ibegin/8+1;
		int jstart=ibegin % 8;
		
		
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
			 	WebElement spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[2]/a"));
				spButton.click(); 
				
				Thread.sleep(1000);
				
				
				spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[2]/ul/li[3]/a"));
				spButton.click(); 
				Thread.sleep(1500);
				

				 //<div class="cs-page">                                                 //*[@id="appEntry"]/div/div[2]/section/div/div[2]/div/div[5]/div/div/div/a[6]
				//一共多少页
				//WebElement elevator= driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div/div[6]/div/div/div/a[6]"));
				WebElement elevator= driver.findElement(By.xpath("//div[@class='cs-page']/a[6]"));
				System.out.println(elevator.getAttribute("outerHTML"));
				String spage= elevator.getText();
				System.out.println("spage:"+spage);
				
				int ipage= pb.atoi(spage);
				
				for (int i=istart;i<=ipage;i++)
				{
					//                                            
					WebElement table=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div/div[5]"));
					
					
					String sdoc=table.getAttribute("outerHTML");
					 
					 Document doc = Jsoup.parse(sdoc);
					
					 //#appEntry > div > div.public-platMain.clearfix > section > div > div.platform-con-netBig.mod-myVideo > div > div.videoList-wrapper.clearfix.videoList-profession > ul > li:nth-child(3) > div.videoList-textBox
					Elements rows = doc.select("div[class=videoList-textBox]");
					
					System.out.println(iout+"->"+ splatform+" rows:"+rows.size());
					
					for (Element row :rows)		
					{
						//System.out.println("-----------------------");	
						
						String sname=row.selectFirst("h4").text();
						String inum=row.selectFirst("span").text();
						String dtadd=row.select("span").get(1).text();
						
						//System.out.println(row.html());	
						//System.out.println(row.text());	
						//System.out.println(iout+"->"+ splatform+":"+sname+":"+row.text());
						inum=dao_grabbing.reset(inum);
						dao_grabbing.insert_tvideo_byname(splatform, sname, inum,dtadd);
						iout++;
						
						
						String iaddnum=inum;
						
						//添加日志数据
						sday=pb.datetostr(new Date()).substring(0,10);
						
						String sSql="select top 1 * from tvideolog where splatform='"+dao_grabbing.splatform_aiqiyi1+"' and sname='"+sname+"'"
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

						dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_aiqiyi1, sname, "", inum, iaddnum, "");
						
						

					}
					
					
					////*[@id="appEntry"]/div/div[2]/section/div/div[2]/div[2]/div[5]/div/div/div/a[8]
					if (i!=ipage)
					{
						elevator= driver.findElement(By.linkText("下一页"));
						elevator.click();
						Thread.sleep(1000);
					}
				}
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mylog.ErrStackTrace(e);
				return iout;
		}

		//dao_grabbing.set_scanning(splatform, 9999);
		return 9999;
	}

	
	
	//视频分析
	public int spfx(String splatform,int ibegin) //开始也页面。开始的位置
	{
		int iout=ibegin;//返回值
		
		int istart=ibegin/8+1;
		int jstart=ibegin % 8;
		
		
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
			 	WebElement spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[2]/a"));
				spButton.click(); 
				
				Thread.sleep(1000);
				
				//<em class="sum-page">157</em>
				spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[2]/ul/li[5]/a"));
				spButton.click(); 
				Thread.sleep(1500);
				
				//总播放量
				String iplaynum=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[1]/ul/li[3]/p")).getText();
				dao_grabbing.set_tFansLog(sday, splatform,"" , "", "", "", "",iplaynum);
				
				//不显示粉丝
				spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[2]/div[1]/a[1]"));
				spButton.click(); 
				Thread.sleep(1400);
				
				//试试模拟点击时间(废弃)
				/*{
					spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[2]/span/div/span/i"));
					spButton.click(); 
					Thread.sleep(1500);
					
					
					spButton=driver.findElement(By.xpath("/html/body/div[4]"));
					System.out.println(spButton.getAttribute("outerHTML"));
					
					
					List<WebElement> lst= driver.findElements(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div/span"));
					
					for (WebElement aa:lst)
					{
						String aria=aa.getAttribute("aria-label");
						System.out.println(aria);
						if (aria.equals("十二月 23, 2018"))
						{
							aa.click(); 
							Thread.sleep(500);
							break;
						}
						
					}
					
					 lst= driver.findElements(By.xpath("/html/body/div[4]/div[2]/div/div[2]/div/span"));
					
					for (WebElement aa:lst)
					{
						String aria=aa.getAttribute("aria-label");
						System.out.println(aria);
						if (aria.equals("十二月 23, 2018"))
						{
							aa.click(); 
							Thread.sleep(500);
							break;
						}
						
					}
					
					Thread.sleep(1500);
					
				}*/
				
				
				
				
				spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[2]/div[2]/label"));
				spButton.click(); 
				Thread.sleep(4400);
				
				//先验证下有没有数据
				WebElement testtable=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[3]/div[2]"));
				String test=testtable.getText().replaceAll("\n", ""); //所有视频合计0000--
				if (test.indexOf("所有视频合计0000")>=0)
				{
					return -1;//数据没出来
				}
				
				
				
				
																  //*[@id="appEntry"]/div/div[2]/section/div/div[2]/div[2]/div[5]/div/div/div/a[6]
				//一共多少页
				WebElement elevator= driver.findElement(By.xpath("//div[@class='cs-page']/a[6]"));
				//WebElement elevator= driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[5]/div/div/div/a[6]"));
				String spage= elevator.getText();
				System.out.println("spage:"+spage);
				
				int ipage= pb.atoi(spage);
				
				for (int i=istart;i<=ipage;i++)
				{
					//                                            
					WebElement table=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[3]/div[2]"));
					
					
					String sdoc=table.getAttribute("outerHTML");
					 
					 Document doc = Jsoup.parse(sdoc);
					
					 //#appEntry > div > div.public-platMain.clearfix > section > div > div.platform-con-netBig.mod-videoData > div.data-detail > div.dataTableCon > div.tbody > div:nth-child(2)
					Elements rows = doc.select("div>div.row");
					
					System.out.println(iout+"->"+ splatform+" rows:"+rows.size());
					
					for (Element row :rows)		
					{
						//System.out.println("-----------------------");	
						
						//System.out.println(row.html());	
						//System.out.println(row.text());	
						//System.out.println(iout+"->"+ splatform+":"+sname+":"+row.text());
						set_vodie_by_day(splatform,sday,row.text());
					}
					
					
					////*[@id="appEntry"]/div/div[2]/section/div/div[2]/div[2]/div[5]/div/div/div/a[8]
					if (i!=ipage)
					{
						elevator= driver.findElement(By.linkText("下一页"));
						elevator.click();
						Thread.sleep(1000);
					}
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
		try {
			
			WebElement                             
			spButton1=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div/div/div[1]/ul/li/div/div[2]/div[2]/span"));
			
			String dallmoney=spButton1.getText();
			
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_aiqiyi1,"",dallmoney,"","","","");
			
			//WebElement spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[1]/ul/li[5]/a"));
			//spButton.click();  
			
			Thread.sleep(2000);
			                                            
			 WebElement table=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div/div/div[4]/div[2]/div[2]/div[1]"));
			 
			
				String sdoc=table.getAttribute("outerHTML");
				//System.out.println(sdoc);
				
				 
				Document doc = Jsoup.parse(sdoc);
				
				Elements rows = doc.select("li");
				for (Element row :rows)		
				{
					set_tengxun_money_by_day(row.text());
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
		try {
			WebElement spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/aside/ul/li[1]/ul/li[1]/a"));
			spButton.click();  
			Thread.sleep(2000);
			
			 spButton=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[1]/div/div/a[1]"));
			spButton.click();  
				
			
			
			Thread.sleep(2000);
			
			//新增粉丝
			 WebElement table=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[1]/ul/li[1]/p[1]"));
			 String iaddnum= table.getText();
			 
			//总的粉丝
			 WebElement table1=driver.findElement(By.xpath("//*[@id='appEntry']/div/div[2]/section/div/div[2]/div[2]/div[1]/ul/li[3]/p[1]"));
			 
			 String inum=table1.getText();
			
			 //前一天
			 Date date=new Date();
			//Calendar calendar =new GregorianCalendar();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE, -1);
			date = calendar.getTime();
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
			String sday = format.format(date);

			 
			if (pb.atof(inum)!=0)
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_aiqiyi1,inum , "", iaddnum, "", "","");
		
			return true;	
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
			
	 }
	}
	
	
	public static String Seach() throws InterruptedException
	{
		String sout=dao_grabbing.splatform_aiqiyi1 +"扫描完毕";
		int iret=0;
		
		for(int i=0;i<3;i++)
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			iret = moni.spfxex(dao_grabbing.splatform_aiqiyi1,1);
			moni.closeIE();
			Thread.sleep(5000);
			PID.clear();
			
			if (iret==-1) break;
			if (iret==9999) break;
		}

		//废了
		/*for(int i=0;i<10;i++)
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			iret = moni.spfx(dao_grabbing.splatform_aiqiyi1,1);
			moni.closeIE();
			Thread.sleep(5000);
			PID.clear();
			
			if (iret==-1) break;
			if (iret==9999) break;
		}*/
		
		if (iret==-1) sout=sout+" 分析视频数据失败(平台未生成)！";
		else if (iret!=9999) sout=sout+" 分析视频数据失败！";
		
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			if (!moni.syfx()) sout=sout+"收益分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		PID.clear();
		
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			
			if (!moni.fsfx()) sout=sout+"粉丝分析失败！";
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		return sout;
		
	}
	
	public static void main(String[] args) throws Exception 
	 {
		
		
		//String aa=dao_grabbing.reset("57.5亿");
		
		String closename="chrome.exe";
		PID.kill(closename);
		closename="chromedriver.exe";
		PID.Fkill(closename);
		
		/*{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			moni.syfx();
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		
		/*{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			
		
			moni.spfx(dao_grabbing.splatform_aiqiyi1,1);
				
			
			moni.closeIE();
			Thread.sleep(5000);
		}*/

		
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			
		
			moni.syfx();
				
			
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		/*{
			moni_aiqiyi1 moni=new moni_aiqiyi1();
			
			moni.fsfx();	
		
			moni.closeIE();
			Thread.sleep(5000);
		}
		
		
		int iret=0;
		while (iret!=9999)
		{
			moni_aiqiyi1 moni=new moni_aiqiyi1();

			iret=moni.spfx(dao_grabbing.splatform_aiqiyi1,iret);
			mylog.info("本次成功:"+iret);
			moni.closeIE();
			Thread.sleep(5000);
		}*/
		



	 }

}

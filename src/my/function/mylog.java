package my.function;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;




//基于log4j的log类
public class mylog {
	
	/*
	Log4j 输出格式转换字符说明 
	＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝ 
	c  category的名称，可使用｛n}限制输出的精度。例如：logger名为"a.b.c"，%c{2}将输出"b.c"。 
	C  产生log事件的java完全限定类名。可使用｛n}限制输出的精度。例如：“org.apache.xyz.SomeClass”,%C{2}将输出“SomeClass”。 
	d  时间和日期的输出格式，例如：%d{yyyy MM dd HH:mm:ss,SS}，可不带后面的日期格式字符。 
	F  产生log事件的java源文件名，带“.java”后缀及包名称。 
	l  log发生位置的详细描述，包括方法名、文件名及行号。 
	L  log发生在源文件中的位置。 
	m  log事件的消息内容。 
	M  log发生时所在的方法名称。 
	n  根据所运行的平台输出相应的行分隔字符。 
	p  log事件的级别。 
	r  自程序运行至log事件产生所经过的时间。 
	t  产生log的线程名称。 
	*/
	private static boolean ifConsole=true; //是否输出到控制台
	
	private static String filelogpath="";	 //日志路径
	private static Map<String,Logger> MyLogFactorys=new HashMap<String,Logger>();
	
	static
	{	
		String sLocalPath=pb.my_runpath()+System.getProperty("file.separator");
		//String sLocalPath=Constant.LOG_PATH;
		filelogpath=sLocalPath;
	}
	
	//获取一个数据库连接
	private static Logger getMyLog(String logname){	
		Logger mylogger= MyLogFactorys.get(logname);
		try{
			if(mylogger==null)
			{
				//初始化
				synchronized (mylog.class)
				{
					if(MyLogFactorys.get(logname)==null)
					{
						try {
							mylogger = Logger.getLogger(logname);  
							mylogger.removeAllAppenders();  
							//mylogger.setAdditivity(true);//设置继承输出root  
							mylogger.setAdditivity(false);//Logger不会在父Logger的appender里输出，默认为true
							
		  
						    PatternLayout layout = new PatternLayout();  
						    /*if (logname.equals("Err"))
						    	layout.setConversionPattern("[%p]%d{yyyy-MM-dd HH:mm:ss,SSS} [%c]-[%C %M line:%L] %m%n"); 
						    else
						    	layout.setConversionPattern("[%p]%d{yyyy-MM-dd HH:mm:ss,SSS} %m%n"); */
						    
						    layout.setConversionPattern("[%p]%d{yyyy-MM-dd HH:mm:ss,SSS} [%c] %m%n");
						    
						    String mylogname=filelogpath+"mylogs"+System.getProperty("file.separator")+logname+".log";
						    
						    if ((!logname.equals("Err"))&&(!logname.equals("Log")))
						    	mylogname=filelogpath+"mylogs"+System.getProperty("file.separator")+logname+System.getProperty("file.separator")+logname+".log";
						    	
						    if(ifConsole)  // 日志输出到控制台
						    {  
						    	ConsoleAppender consoleAppender = new ConsoleAppender();
						    	consoleAppender.setLayout(layout);
						    	consoleAppender.setThreshold(Level.INFO);  // ConsoleAppender日志级别为DEBUG
						    	consoleAppender.activateOptions();
						    	mylogger.addAppender(consoleAppender);
						   }
						    
						    //另外一种写法
						    /*if(true) 
						    {  // 日志输出到文件
						    	   FileAppender fileAppender = new FileAppender();
						    	   fileAppender.setLayout(layout);
						    	   fileAppender.setFile(logFile);
						    	   fileAppender.setEncoding("UTF-8");
						    	   fileAppender.setAppend(true);
						    	   fileAppender.setThreshold(Level.INFO);   // FileAppender日志级别为INFO
						    	   fileAppender.activateOptions();
						    	   mylogger.addAppender(fileAppender);
						    }*/
						   
						    Appender appender = null; 
						    //appender =new DailyRollingFileAppender(layout,"../logs/"+str1[0]+".log","yyyy-MM-dd");
							//配置2中使用到另一个类RollingFileAppender，这个类也继承子FileAppender，用于按照一定的size文件大小   ((RollingFileAppender)appender).setMaximumFileSize(111);
							appender =new DailyRollingFileAppender(layout,mylogname,"yyyyMMdd");
							
						    mylogger.addAppender(appender); 
						    
						    mylogger.setLevel(Level.INFO);  
						    //mylogger.setLevel(Level.DEBUG);  
						    mylogger.info("log创建成功。。。。。");  
					    } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							mylogger=null;
						}  
						
						//初始化log
						if(mylogger!=null)
						{
							MyLogFactorys.put(logname,mylogger);
						}
					}
				}
				
				mylogger= MyLogFactorys.get(logname);
				if(mylogger!=null)
					return mylogger;
				else
					return null;
			}
			else
			{
				return mylogger;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public static void Err(String msg)
	{
		Logger dolog= getMyLog("Err");
		if (dolog!=null)
		{
			dolog.info(msg);
		}
	}
	
	public static void Err(String logname,String msg)
	{
		Logger dolog= getMyLog(logname);
		if (dolog!=null)
		{
			dolog.error(msg);
			//dolog.removeAllAppenders(); 关闭日志
		}
	}
	
	public static void info(String msg)
	{
		Logger dolog= getMyLog("Log");
		if (dolog!=null)
		{
			dolog.info(msg);
		}
	}
	
	public static void info(String logname,String msg)
	{
		Logger dolog= getMyLog(logname);
		if (dolog!=null)
		{
			dolog.info(msg);
			//dolog.removeAllAppenders(); 关闭日志
		}
	}
	
	//记录报错堆栈
	public static void ErrStackTrace(Exception e)
	{
		String sStack="";
		StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);//将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();

            sStack= sw.toString();
        } catch (Exception ex) {
        	sStack=e.getMessage();
        	Err("ErrStackTrace:"+ex.getMessage());
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
		
        Err(sStack);
	}
	
	//记录报错堆栈
		public static void ErrStackTrace(String logname,Exception e)
		{
			String sStack="";
			StringWriter sw = null;
	        PrintWriter pw = null;
	        try {
	            sw = new StringWriter();
	            pw = new PrintWriter(sw);
	            e.printStackTrace(pw);//将出错的栈信息输出到printWriter中
	            pw.flush();
	            sw.flush();

	            sStack= sw.toString();
	        } catch (Exception ex) {
	        	sStack=e.getMessage();
	        	Err("ErrStackTrace:"+ex.getMessage());
	        } finally {
	            if (sw != null) {
	                try {
	                    sw.close();
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	            if (pw != null) {
	                pw.close();
	            }
	        }
			
	        Err(logname,sStack);
		}

}

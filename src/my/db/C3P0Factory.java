package my.db;

import java.util.HashMap;
import java.util.Map;
import my.function.PropertiesUtil;
import my.function.mylog;
import my.function.pb;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;


//放弃mybatis，直接用原始的数据库控件。操作更灵活，缺点是没有持久层
public class C3P0Factory {
		
		private static Map<String,ComboPooledDataSource> c3p0Factorys=new HashMap<String,ComboPooledDataSource>();
		
		//获取一个数据库连接
		public static Connection getC3P0Connection(String environmentName){	
			ComboPooledDataSource dataSource= c3p0Factorys.get(environmentName);
			try{
				if(dataSource==null)
				{
					//初始化
					synchronized (C3P0Factory.class)
					{
						if(c3p0Factorys.get(environmentName)==null)
						{
							//使用C3P0连接池
							dataSource = getC3PODataSource(environmentName);
							if(dataSource!=null)
							{
								c3p0Factorys.put(environmentName,dataSource);
							}
						}
					}
					
					dataSource= c3p0Factorys.get(environmentName);
					if(dataSource!=null)
						return dataSource.getConnection();
					else
						return null;
					
				}
				else
				{
					return dataSource.getConnection();
				}
			}catch(Exception e){
				e.printStackTrace();
				mylog.ErrStackTrace(e);
				return null;
			}
			
		}
		
		//获取一个连接池,这个基本没啥用。
		public static ComboPooledDataSource getC3P0Factory(String environmentName){	
			try{
				if(c3p0Factorys.get(environmentName)==null){
					synchronized (C3P0Factory.class){
						if(c3p0Factorys.get(environmentName)==null)
						{
							//使用C3P0连接池
							ComboPooledDataSource dataSource = getC3PODataSource(environmentName);
							if(dataSource!=null){
							c3p0Factorys.put(environmentName,dataSource);
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return c3p0Factorys.get(environmentName);
		}
		
		public static void FreeC3P0Factory(String environmentName){	
			try{
				synchronized (C3P0Factory.class)
				{
					ComboPooledDataSource dataSource= c3p0Factorys.remove(environmentName);
					if(dataSource!=null)
					{
						dataSource.close();
					}	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@SuppressWarnings("rawtypes")
		private static ComboPooledDataSource getC3PODataSource(String environmentName){
			ComboPooledDataSource dataSource=null;
			
			//String dbcpConfig=Constant.CONFIG_PATH+"Config.properties";
			String dbcpConfig=pb.my_runpath()+System.getProperty("file.separator")+"Config.properties";
			try{
				//读jdbc.properties，解析出各参数
				Map configs=PropertiesUtil.getProperties(dbcpConfig);
				
				ComboPooledDataSource bdsmysql= new ComboPooledDataSource();
				
				String sdriver=(String) configs.get("sdriver_"+environmentName);
				String surl=(String) configs.get("surl_"+environmentName);
				String suser=(String) configs.get("suser_"+environmentName);
				String spassword= (String) configs.get("spassword_"+environmentName);
				

				bdsmysql.setDriverClass(sdriver);//设置连接池连接数据库所需的驱动  
				bdsmysql.setJdbcUrl(surl);//设置连接数据库的URL  
				bdsmysql.setUser(suser);//设置连接数据库的用户名  
				bdsmysql.setPassword(spassword);//设置连接数据库的密码  
				
				int iMin=pb.atoi((String)configs.get("ConMin_"+environmentName));//最小连接数
				if (iMin==0) iMin=10;
			      
				bdsmysql.setMaxPoolSize(iMin*3);//设置连接池的最大连接数  
			      
				bdsmysql.setMinPoolSize(iMin);//设置连接池的最小连接数  
			      
				bdsmysql.setInitialPoolSize(iMin);//设置连接池的初始连接数  
			      
				bdsmysql.setMaxStatements(20);//设置连接池的缓存Statement的最大数
			   	
				bdsmysql.setMaxIdleTime(120);//3分钟后自动回收
			   	
				bdsmysql.setAcquireRetryAttempts(0); //定义在从数据库获取新连接失败后重复尝试获取的次数，默认为30；
				bdsmysql.setAcquireRetryDelay(1000); //重新连接的频率（两个配合使用）
			   	
				bdsmysql.setIdleConnectionTestPeriod(120); //隔多少秒检查所有连接池中的空闲连接，默认为0表示不检查； 
				bdsmysql.setBreakAfterAcquireFailure(false); //（重连）如果 breakAfterAcquireFailure=true ，一旦pool向数据库请求连接失败，就会标记pool block并关闭pool，这样无论数据库是否恢复正常，应用端都无法从pool拿到连接
			   	                                        //获取连接失败将会引起所有等待获取连接的线程抛出异常。但是数据源仍有效保留，并在下次调   用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试获取连接失败后该数据源将申明已断开并永久关闭。；	
			   	
				
				//bdsmysql.setTestConnectionOnCheckout(true); //因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的时候都 将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
				bdsmysql.setIdleConnectionTestPeriod(60);//单位是秒,不是毫秒 //因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的时候都 将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
			   	//datasource.c3p0.autoCommitOnClose=true连接关闭时默认将所有未提交的操作回滚。默认为false；
			   	//datasource.c3p0.maxStatementsPerConnection=100连接池内单个连接所拥有的最大缓存Statement数。默认为0；
				
				dataSource= bdsmysql;
			}catch(Exception e){
				e.printStackTrace();
			}
			return dataSource;
		}

}

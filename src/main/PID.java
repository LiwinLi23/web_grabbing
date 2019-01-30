package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PID {
	
	
	private static String getPID(String pidname) {
		String pid = null;
		String cmd = "tasklist /nh /FI \"IMAGENAME eq "+pidname+"\"";
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
			String line = null;
			while((line=br.readLine()) != null){
				System.out.println(line);
				if(line.indexOf(pidname) != -1)
				{
					while (line.indexOf("  ")>=0)
					{
						line=line.replaceAll("  ", " ");	
					}
					
					String[] lineArray = line.split(" ");
					pid = lineArray[1].trim();
					return pid;
					/*if(pid.equals(RFT_ECLIPSE_PID)){
						continue;
					} else {
						return pid;
					}*/
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pid;
	}
	
	public static void kill(String closename)
	{
		String oldpid="";
		String RBD_ECLIPSE_PID = getPID(closename); 
		while (RBD_ECLIPSE_PID!=null)
		{
		    
		    System.out.println("RBD_ECLIPSE_PID:"+RBD_ECLIPSE_PID);
		    Process process;
			try {
				//
				if (oldpid.equals(RBD_ECLIPSE_PID))
					process = Runtime.getRuntime().exec("taskkill /F /PID "+RBD_ECLIPSE_PID);// F 是强制关闭
				else
					process = Runtime.getRuntime().exec("taskkill /PID "+RBD_ECLIPSE_PID);
				 BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
					String line = null;
					while((line=br.readLine()) != null)
					{
						System.out.println(line);
					}
				oldpid=RBD_ECLIPSE_PID;
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   
			RBD_ECLIPSE_PID = getPID(closename); 
		}
		
	}
	
	
	
	//强制杀死
	public static void Fkill(String closename)
	{
		String RBD_ECLIPSE_PID = getPID(closename); 
		while (RBD_ECLIPSE_PID!=null)
		{
		    
		    System.out.println("RBD_ECLIPSE_PID:"+RBD_ECLIPSE_PID);
		    Process process;
			try {
				process = Runtime.getRuntime().exec("taskkill /F /PID "+RBD_ECLIPSE_PID);// F 是强制关闭
				//process = Runtime.getRuntime().exec("taskkill /PID "+RBD_ECLIPSE_PID);
				 BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
					String line = null;
					while((line=br.readLine()) != null)
					{
						System.out.println(line);
					}
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   
			RBD_ECLIPSE_PID = getPID(closename); 
		}
		
	}
	
	public static void clear()
	{
		String closename="chrome.exe";
		kill(closename);
		closename="chromedriver.exe";
		Fkill(closename);
	}
	
	public static void main(String[] args) throws Exception 
	 {
		//String RBD_ECLIPSE_PID = getPID("chromedriver.exe");  
		
		String closename="chrome.exe";
		kill(closename);
		closename="chromedriver.exe";
		Fkill(closename);
		
		/*String RBD_ECLIPSE_PID = getPID(closename); 
		while (RBD_ECLIPSE_PID!=null)
		{
		    
		    System.out.println("RBD_ECLIPSE_PID:"+RBD_ECLIPSE_PID);
		    Process process = Runtime.getRuntime().exec("taskkill /F /PID "+RBD_ECLIPSE_PID); 
		    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
			String line = null;
			while((line=br.readLine()) != null)
			{
				System.out.println(line);
			}
			RBD_ECLIPSE_PID = getPID(closename); 
		}*/
	 }


}

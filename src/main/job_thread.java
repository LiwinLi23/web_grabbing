package main;

import java.util.Date;

import my.function.mylog;
import my.function.pb;
import web.grabbing.dao_grabbing;
import web.grabbing.get_meipai;
import web.grabbing.moni_aiqiyi1;
import web.grabbing.moni_dayu;
import web.grabbing.moni_douyin;
import web.grabbing.moni_meipai;
import web.grabbing.moni_miaopai;
import web.grabbing.moni_tenxun;
import web.grabbing.moni_toutiao;
import web.grabbing.moni_youtube;

public class job_thread  extends Thread{
	
	private String splatform="";
	
	public job_thread(String splatform)
    {
        this.splatform = splatform;
    }
	
	public void run()
    {
		String sret="";
		try
		{
			switch (splatform)
			{
				case dao_grabbing.splatform_aiqiyi1 : 
					sret=moni_aiqiyi1.Seach(); 
					break;
				case  dao_grabbing.splatform_youku :
					sret=moni_dayu.Seach(); 
					break;
				case  dao_grabbing.splatform_tenxun :
					sret=moni_tenxun.Seach(); 
					break;
				case  dao_grabbing.splatform_meipai :
					get_meipai getse=new get_meipai();
					getse.get_main_page("https://www.meipai.com/user/1074764441?single_column=0");
					getse.spfxbyhttpurl();
					String sday=pb.datetostr(new Date()).substring(0,10);
					dao_grabbing.set_tFansLog_byday(dao_grabbing.splatform_meipai,sday);
					sret="OK";
					break;
				case  dao_grabbing.splatform_miaopai :
					sret=moni_miaopai.Seach(); 
					break;
				case  dao_grabbing.splatform_toutiao :
					sret=moni_toutiao.Seach(); 
					break;
				case  dao_grabbing.splatform_youtube :
					sret=moni_youtube.Seach(); 
					break;
					
				case  dao_grabbing.splatform_douyin :
					sret=moni_douyin.Seach(); 
					break;
					
				case  "all" :
					main_grabbing.doall(); 
					break;

			}
		}
		catch (Exception e)
		{
			mylog.ErrStackTrace(e);
		}
		finally
		{
			main_grabbing.setenable();
			
			switch (splatform)
			{
				case dao_grabbing.splatform_aiqiyi1 : 
					main_grabbing.lablel_aiqiyi.setText(sret); 
					break;
				case  dao_grabbing.splatform_youku :
					main_grabbing.lablel_dayu.setText(sret); 
					break;
				case  dao_grabbing.splatform_tenxun :
					main_grabbing.lablel_tenxun.setText(sret); 
					break;
				case  dao_grabbing.splatform_meipai :
					main_grabbing.lablel_meipai.setText(sret); 
					break;
				case  dao_grabbing.splatform_miaopai :
					main_grabbing.lablel_miaopai.setText(sret); 
					break;
				case  dao_grabbing.splatform_toutiao :
					main_grabbing.lablel_toutiao.setText(sret); 
					break;
				case  dao_grabbing.splatform_youtube :
					main_grabbing.lablel_youtube.setText(sret); 
					break;
				case  dao_grabbing.splatform_douyin :
					main_grabbing.lablel_douyin.setText(sret); 
					break;


			}
			
		}
			
    }

}

package main;

import java.util.Calendar;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import my.function.mylog;


@DisallowConcurrentExecution
public class grabbing_job implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		try
		{
			//mylog.info("job_aod_channel_info start");
			
			if (!main_grabbing.canjob) return;
			
			Calendar now = Calendar.getInstance();
		
			int ihour =  now.get(Calendar.HOUR_OF_DAY);
			int iminute= now.get(Calendar.MINUTE);
			int isecond= now.get(Calendar.SECOND);
	
			if ((ihour%12==9)&&(iminute==0))
			{
				main_grabbing.lablel_wait.setText("正在任务:"+ihour+":"+iminute);
				while (!main_grabbing.canjob)
				{
					main_grabbing.lablel_wait.setText("等待中....");
					Thread.sleep(3000);
				}
				main_grabbing.doall();
			}
			else
			{
				
				int ilefthour=0;
				if ((ihour>=8)&&(ihour<20))  ilefthour=19-ihour;
				else if  (ihour>=20)  ilefthour=23-ihour +8;
				else ilefthour=7-ihour;
				
				int ileftmin= 60-iminute;
				
				main_grabbing.lablel_wait.setText("当前时间:"+ihour+":"+iminute +" 距离下次任务还有："+ilefthour+" 小时:"+ileftmin +" 分钟");
			}
			
			
			
		}
		catch(Exception e){
			//cn.anyradio.common.log.Log.ErrStackTrace(e);
			//mylog.Err("job_radio_list_tofile");
			mylog.ErrStackTrace(e);
		}
		
	}

}

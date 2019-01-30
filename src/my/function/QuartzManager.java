package my.function;

import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
  

public class QuartzManager {
	

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME"; 
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";  
  
    /** 
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     * 
     * @param jobName 
     *            任务名 
     * @param jobClass 
     *            任务 
     * @param time 
     *            时间设置，参考quartz说明文档 
     * @throws SchedulerException 
     * @throws ParseException 
     */  
    public static void addJob(String jobName, String jobClass, String time) {  
        try { 
        	
        	Properties props = System.getProperties();
        	props.setProperty("org.quartz.threadPool.threadCount", "1"); //设置同时可以运行的线程数
        	
            Scheduler sched = gSchedulerFactory.getScheduler();  
            
            
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>)Class.forName(jobClass)) 
            		.withDescription("this is a ram job") //job的描述
                    .withIdentity(jobName, JOB_GROUP_NAME)   //job 的name和group
                    .build(); 
            
            
            
            /*SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5)
                    .withRepeatCount(3);//5秒一次，执行3次
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger_1", "tGroup1").startNow()
                    .withSchedule(builder).build();*/
            
            //任务运行的时间，SimpleSchedle类型触发器有效
            /*long lrun=  System.currentTimeMillis() + 3*1000L; //3秒后启动任务
            Date statTime = new Date(lrun);*/
            
            // cronTrigger 创建
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger()
            		.withIdentity(jobName, TRIGGER_GROUP_NAME)
                    .withSchedule(cronScheduleBuilder).build();
            

            /*CronTrigger trigger = TriggerBuilder.newTrigger()  
                    .withIdentity(jobName, jobName)  
                    .startAt(new Date(System.currentTimeMillis()+5*1000))  
                    //.endAt(new Date(System.currentTimeMillis()+5*1000+60*1000))  
                    .withSchedule(CronScheduleBuilder.cronSchedule(time))  
                    .build();*/ 
 
            sched.scheduleJob(jobDetail, trigger);  
            // 如果 sched 没启动，则启动
            /*if (!sched.isShutdown()){  
                sched.start();  
            }  */
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 添加一个定时任务 
     * 
     * @param jobName 
     *            任务名 
     * @param jobGroupName 
     *            任务组名 
     * @param triggerName 
     *            触发器名 
     * @param triggerGroupName 
     *            触发器组名 
     * @param jobClass 
     *            任务 
     * @param time 
     *            时间设置，参考quartz说明文档 
     * @throws SchedulerException 
     * @throws ParseException 
     */  
    /*public static void addJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName, String jobClass, String time){  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, Class.forName(jobClass));// 任务名，任务组，任务执行类  
            // 触发器  
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组  
            trigger.setCronExpression(time);// 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     * 
     * @param jobName 
     * @param time 
     */  
    @SuppressWarnings("rawtypes")
	/*public static void modifyJobTime(String jobName, String time) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);  
            if(trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);  
                Class objJobClass = jobDetail.getJobClass();  
                String jobClass = objJobClass.getName();  
                removeJob(jobName);  
  
                addJob(jobName, jobClass, time);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 修改一个任务的触发时间 
     * 
     * @param triggerName 
     * @param triggerGroupName 
     * @param time 
     */  
    /*public static void modifyJobTime(String triggerName,  
            String triggerGroupName, String time) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);  
            if(trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                CronTrigger ct = (CronTrigger) trigger;  
                // 修改时间  
                ct.setCronExpression(time);  
                // 重启触发器  
                sched.resumeTrigger(triggerName, triggerGroupName);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     * 
     * @param jobName 
     */  
    /*public static void removeJob(String jobName) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器  
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器  
            sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 移除一个任务 
     * 
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    /*public static void removeJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器  
            sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器  
            sched.deleteJob(jobName, jobGroupName);// 删除任务  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    } */ 
  
    /** 
     * 启动所有定时任务 
     */  
    public static void startJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 关闭所有定时任务 
     */  
    public static void shutdownJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            if(!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        }  
    } 
}  
package main;

import my.function.QuartzManager;
import my.function.mylog;
import my.function.pb;
import web.grabbing.dao_grabbing;
import web.grabbing.get_meipai;
import web.grabbing.moni_aiqiyi1;
import web.grabbing.moni_dayu;
import web.grabbing.moni_douyin;
import web.grabbing.moni_miaopai;
import web.grabbing.moni_tenxun;
import web.grabbing.moni_toutiao;
import web.grabbing.moni_youtube;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class main_grabbing {
	
	public static JLabel lablel_wait=new JLabel("等待");
	public static JButton butten_all = new JButton("手动运行全部");
	
	public static JLabel lablel_aiqiyi=new JLabel("爱奇艺");
	public static JButton butten_aiqiyi = new JButton("爱奇艺");
	
	public static JLabel lablel_meipai=new JLabel("美拍");
	public static JButton butten_meipai = new JButton("美拍");
	
	public static JLabel lablel_dayu=new JLabel("大鱼");
	public static JButton butten_dayu = new JButton("大鱼");
	
	public static JLabel lablel_miaopai=new JLabel("秒拍");
	public static JButton butten_miaopai = new JButton("秒拍");
	
	public static JLabel lablel_tenxun=new JLabel("腾讯");
	public static JButton butten_tenxun = new JButton("腾讯");
	
	public static JLabel lablel_toutiao=new JLabel("头条");
	public static JButton butten_toutiao = new JButton("头条");
	
	public static JLabel lablel_youtube=new JLabel("youtube");
	public static JButton butten_youtube = new JButton("youtube");
	
	public static JLabel lablel_douyin=new JLabel("抖音");
	public static JButton butten_douyin = new JButton("抖音");
	
	public static boolean canjob=true;
	
	
	//public static 
	
	public static void setnoenable()
	{
		canjob=false;
		butten_aiqiyi.setEnabled(false);
		butten_meipai.setEnabled(false);
		butten_dayu.setEnabled(false);
		butten_miaopai.setEnabled(false);
		butten_tenxun.setEnabled(false);
		butten_toutiao.setEnabled(false);
		butten_youtube.setEnabled(false);
		butten_douyin.setEnabled(false);
		butten_all.setEnabled(false);
	}
	
	public static void setenable()
	{
		canjob=true;
		butten_aiqiyi.setEnabled(true);
		butten_meipai.setEnabled(true);
		butten_dayu.setEnabled(true);
		butten_miaopai.setEnabled(true);
		butten_tenxun.setEnabled(true);
		butten_toutiao.setEnabled(true);
		butten_youtube.setEnabled(true);
		butten_douyin.setEnabled(true);
		butten_all.setEnabled(true);
	}
	
	public static void iniframe()
	{
		JFrame f = new JFrame("LoL");
        f.setSize(500, 500);
        f.setLocation(300, 200);
        f.setLayout(null);
        
        
        // 创建一个水平箱容器
        Box hBox1 = Box.createHorizontalBox();
        Box hBox2 = Box.createHorizontalBox();
        Box hBox3 = Box.createHorizontalBox();
        Box hBox4 = Box.createHorizontalBox();
        Box hBox5 = Box.createHorizontalBox();
        Box hBox6 = Box.createHorizontalBox();
        Box hBox7 = Box.createHorizontalBox();
        Box hBox8 = Box.createHorizontalBox();
        Box hBox9 = Box.createHorizontalBox();
        
     

      //文字颜色
        //lablel_aiqiyi.setForeground(Color.red);
        //lablel_aiqiyi.setBounds(50, 10, 110, 30);
        //lablel_aiqiyi.setHorizontalAlignment(SwingConstants.CENTER);        // 使标签文字居中
        //butten_aiqiyi.setEnabled(false);
        butten_aiqiyi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//关闭弹窗
				//System.exit(0);	
				setnoenable();
				lablel_aiqiyi.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_aiqiyi1);
				job.start();
				
			}
		});
        
        hBox1.add(butten_aiqiyi);
        hBox1.add(lablel_aiqiyi);
        
        /*JPanel p1 = new JPanel();
        // 设置面板大小
        p1.setBounds(0, 50, f.getWidth(), 50);
        // 设置面板背景颜色
        p1.setBackground(Color.RED);
        p1.setLayout(new FlowLayout());
        //
        
        p1.add(lablel_aiqiyi);
        p1.add(butten_aiqiyi);*/
        
        
        butten_meipai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_meipai.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_meipai);
				job.start();
				
			}
		});
        
        hBox2.add(butten_meipai);
        hBox2.add(lablel_meipai);
        
        
        
        butten_dayu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_dayu.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_youku);
				job.start();	
			}
		});
        hBox3.add(butten_dayu);
        hBox3.add(lablel_dayu);
        
        
        butten_miaopai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_miaopai.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_miaopai);
				job.start();	
			}
		});
        
        hBox4.add(butten_miaopai);
        hBox4.add(lablel_miaopai);
        
        
        butten_tenxun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_tenxun.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_tenxun);
				job.start();	
			}
		});
        
        hBox5.add(butten_tenxun);
        hBox5.add(lablel_tenxun);
        
        
        butten_toutiao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_toutiao.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_toutiao);
				job.start();	
			}
		});
        
        hBox6.add(butten_toutiao);
        hBox6.add(lablel_toutiao);
        
        
        butten_youtube.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_youtube.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_youtube);
				job.start();	
			}
		});
        
        hBox7.add(butten_youtube);
        hBox7.add(lablel_youtube);
        

        butten_douyin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_douyin.setText("正在执行。。");
				job_thread job = new job_thread(dao_grabbing.splatform_douyin);
				job.start();	
			}
		});
        
        hBox8.add(butten_douyin);
        hBox8.add(lablel_douyin);
        
        
        butten_all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setnoenable();
				lablel_wait.setText("正在执行。。");
				job_thread job = new job_thread("all");
				job.start();	
			}
		});
        
        hBox9.add(butten_all);
        hBox9.add(lablel_wait);
        
         
        
        // 把面板加入窗口
        //f.add(hBox1);
        
        // 创建一个垂直箱容器
        Box vBox = Box.createVerticalBox();
        
        vBox.add(hBox1);
        vBox.add(hBox2);
        vBox.add(hBox3);
        vBox.add(hBox4);
        vBox.add(hBox5);
        vBox.add(hBox6);
        vBox.add(hBox7);
        vBox.add(hBox8); 
        vBox.add(hBox9); 
       
        
        f.add(vBox);
        f.setContentPane(vBox);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setVisible(true);
		
	}
	

	
	public static void main(String[] args) throws Exception 
	{
		
		String aa=pb.datetostr(new Date());
		
		if (aa.compareTo("2019-03-01")>0) return;
		
		iniframe();
		
		//doall();

		
		//开始job
		QuartzManager.addJob("grabbing_job.java", grabbing_job.class.getName(), "0/10 * * * * ?");

	
		QuartzManager.startJobs();
		
		System.out.println(args.length);
		
		if (args.length==0)
		{
			//启动定时任务
		}
		else
		{
			//测试
			String splatform=args[0];
		}
	}
	
	public static void doall()
	{
		String sret="";
		
		setnoenable();
		
		//爱奇艺的要最后跑。 太早了没数据
		try {
			lablel_aiqiyi.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_aiqiyi1.Seach();
			mylog.info("main",sret);
			lablel_aiqiyi.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			lablel_douyin.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_douyin.Seach();
			mylog.info("main",sret);
			lablel_douyin.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			lablel_youtube.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_youtube.Seach();
			mylog.info("main",sret);
			lablel_youtube.setText(pb.datetostr(new Date())+"->"+sret);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		try {
			lablel_miaopai.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_miaopai.Seach();
			mylog.info("main",sret);
			lablel_miaopai.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			lablel_toutiao.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_toutiao.Seach();
			mylog.info("main",sret);
			lablel_toutiao.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			lablel_meipai.setText(pb.datetostr(new Date())+"->正在处理..");
			get_meipai getse=new get_meipai();
			//getse.get_sub_page("https://www.meipai.com/user/1074764441?single_column=0&p=99");
			getse.get_main_page("https://www.meipai.com/user/1074764441?single_column=0");
			getse.spfxbyhttpurl();
			String sday=pb.datetostr(new Date()).substring(0,10);
			dao_grabbing.set_tFansLog_byday(dao_grabbing.splatform_meipai,sday);
			lablel_meipai.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			lablel_tenxun.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_tenxun.Seach();
			mylog.info("main",sret);
			lablel_tenxun.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			lablel_dayu.setText(pb.datetostr(new Date())+"->正在处理..");
			sret=moni_dayu.Seach();
			mylog.info("main",sret);
			lablel_dayu.setText(pb.datetostr(new Date())+"->"+sret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		setenable();
		
	}

}

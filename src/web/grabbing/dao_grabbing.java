package web.grabbing;

import java.util.Date;
import java.util.List;
import java.util.Map;

import my.db.db;
import my.function.pb;

//数据库操作
public class dao_grabbing {
	
	static public final String splatform_youku="优酷";
	static public final String splatform_tudou="土豆";
	static public final String splatform_uc="UC";
	static public final String splatform_tenxun="腾讯";
	static public final  String splatform_aiqiyi1="爱奇艺";
	
	
	static public final String splatform_meipai="美拍";
	static public final String splatform_miaopai="秒拍";
	static public final String splatform_toutiao="今日头条";
	static public final String splatform_youtube="YouTube";
	static public final String splatform_douyin="抖音";
	
	
	public static String dayset(String svalue)
	{
		//美拍的 18-12-19 17:10  01-08 17:05
		if (svalue.length()==14)
		{
			svalue="20"+svalue+":00";
		}
		
		if ((svalue.length()==11)&&(!svalue.substring(0,2).equals("20")))
		{
			svalue="2019-"+svalue+":00";
		}
		
		
		svalue=svalue.replaceAll("年","-");
		svalue=svalue.replaceAll("月","-");
		svalue=svalue.replaceAll("日","");
		
		return svalue;
	}
	
	public static String reset(String svalue)
	{
		svalue=svalue.replace('$',' ');
		svalue=svalue.replaceAll(",","");
		svalue=svalue.replaceAll(" ","");
		svalue=svalue.replaceAll("$","");
		
        svalue=svalue.replaceAll("조회수","");
        svalue=svalue.replaceAll("회","");
		
		if (svalue.indexOf("次观看")>=0)
		{
			svalue=svalue.replaceAll("次观看","");
		}
		if (svalue.indexOf("播放量:")>=0)
		{
			svalue=svalue.replaceAll("播放量:","");
		}
		
		if (svalue.indexOf("万")>=0)
		{
			svalue=svalue.replaceAll("万","");
			long aa=(long) ((pb.atof(svalue))*10000);
			svalue=String.valueOf(aa);
		}
		
		if (svalue.indexOf("亿")>=0)
		{
			svalue=svalue.replaceAll("亿","");
			long aa=(long) ((pb.atof(svalue))*10000*10000);
			svalue=String.valueOf(aa);
		}
		
		
		//去掉小数点
		if (svalue.indexOf(".")>=0)
		{
			svalue=svalue.substring(0,svalue.indexOf("."));	
		}
		
		return String.valueOf(pb.atol(svalue));
	}
	
	//通过tvideoing 反写  tvideo 
	public static void reset_video_by_day(String splatform,String sday)
	{
		String sSql="insert into tvideo(sname,snum,splatform,iplaynum)"
					+"select  sname,snum,splatform,iplaynum from  tvideolog   where  splatform='"+splatform+"' and convert(VARCHAR(10),dtinsert,120) ='"+sday+"'"  
					+ "and sname not in (select sname from tvideo)";
		db.ExceSql("pftfdb", sSql);
	}
	
	//设置某个具体视频，某一天的相关信息
	public static void set_alone_video(String sday,String splatform,String sname,String snum,String iplaynum,String iaddnum,String icollection)
	{
		sname=sname.replaceAll("'", "''");
		
		if (iplaynum.equals("")) iplaynum="0";
		if (iaddnum.equals("")) iaddnum="0";
		if (icollection.equals("")) icollection="0";
		
		iplaynum=reset(iplaynum);
		iaddnum=reset(iaddnum);
		icollection=reset(icollection);
		
		//xiaoyan
		if (Integer.valueOf(iaddnum)<0)
		{
			if (iplaynum.equals("0"))
			{
				
			}
			iaddnum="0";
		}
		
		//如果都是0就不写数据
		if ((iplaynum.equals("0"))&&(iplaynum.equals("0")))
		{
			return;
		}
		
		String sSql="select * from tvideolog where sname='"+sname+"'and  convert(VARCHAR(10),dtinsert,120) ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
		
		List rets=db.OpenSql_toList("pftfdb", sSql);
		if (rets.size()==0)
		{
			//添加一条
			sSql="insert into tvideolog(dtinsert,splatform,sname,snum,iplaynum,iaddnum,icollection)"
					+ "values('"+sday+"','"+splatform+"','"+sname+"','"+snum+"',"+iplaynum+","+iaddnum+","+icollection+")";
			db.ExceSql("pftfdb", sSql);
		}
	}
	
	
	
	//设置UC的钱
	public static void set_alone_money(String sday,String splatform,String dallmoney,String dmoney1,String dmoney2)
	{
		if (dallmoney.equals("")) dallmoney="0";
		if (dmoney1.equals("")) dmoney1="0";
		if (dmoney2.equals("")) dmoney2="0";
		
		String sSql="select * from tplatformlog where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
		
		List rets=db.OpenSql_toList("pftfdb", sSql);
		if (rets.size()==0)
		{
			//添加一条
			sSql="insert into tplatformlog(dtinsert,splatform,sday,dallmoney,dmoney1,dmoney2)"
					+ "values('"+sday+"','"+splatform+"','"+sday+"','"+dallmoney+"',"+dmoney1+","+dmoney2+")";
			db.ExceSql("pftfdb", sSql);
			set_tFansLog(sday,splatform,"",dallmoney,"",dallmoney,"","");
		}
		
	}
	
	//设置youbetu的钱
	public static void set_alone_money_youtobe(String sday,String splatform,String dallmoney,String dmoney1,String dmoney2)
	{
		if (dallmoney.equals("")) dallmoney="0";
		if (dmoney1.equals("")) dmoney1="0";
		if (dmoney2.equals("")) dmoney2="0";
		
		String sSql="select * from tplatformlog where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
		
		List rets=db.OpenSql_toList("pftfdb", sSql);
		if (rets.size()==0)
		{
			//添加一条
			sSql="insert into tplatformlog(dtinsert,splatform,sday,dallmoney,dmoney1,dmoney2)"
					+ "values('"+sday+"','"+splatform+"','"+sday+"','"+dallmoney+"',"+dmoney1+","+dmoney2+")";
			db.ExceSql("pftfdb", sSql);
			set_tFansLog(sday,splatform,"","","",dallmoney,"","");
		}
		
	}

	//设置优酷每天的钱
	public static void set_alone_money1_youku(String sday,String splatform,String dmoney1)
	{
	
		if (dmoney1.equals("")) return;
		
		String sSql="update tplatformlog set dmoney1="+dmoney1+",dallmoney=dmoney2+"+dmoney1+" where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
		
	
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			sSql="insert into tplatformlog(dtinsert,splatform,sday,dallmoney,dmoney1)"
					+ "values('"+sday+"','"+splatform+"','"+sday+"',"+dmoney1+","+dmoney1+")";
			db.ExceSql("pftfdb", sSql);
		}
		else
		{
			//更新另外一个表
			sSql="select top 1 * from tplatformlog  where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
			Map ret= db.OpenOneSql_toMap("pftfdb", sSql);
			if (ret!=null)
			{
				String dallmoney=ret.get("dallmoney").toString();
				set_tFansLog(sday,splatform,"","","",dallmoney,"","");
			}
		}
		
	}
	
	//设置优酷每天的钱
	public static void set_alone_money2_youku(String sday,String splatform,String dmoney2)
	{
	
		if (dmoney2.equals("")) return;
		
		String sSql="update tplatformlog set dmoney2="+dmoney2+",dallmoney=dmoney1+"+dmoney2+" where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
		
	
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			sSql="insert into tplatformlog(dtinsert,splatform,sday,dallmoney,dmoney2)"
					+ "values('"+sday+"','"+splatform+"','"+sday+"',"+dmoney2+","+dmoney2+")";
			db.ExceSql("pftfdb", sSql);
		}
		else
		{
			//更新另外一个表
			sSql="select top 1 * from tplatformlog  where  sday ='"+sday+"'" 
				+" and splatform='"+splatform+"'";
			Map ret= db.OpenOneSql_toMap("pftfdb", sSql);
			if (ret!=null)
			{
				String dallmoney=ret.get("dallmoney").toString();
				set_tFansLog(sday,splatform,"",dallmoney,"",dallmoney,"","");
			}
		}
		
	}
	
	public static void set_tFansLog(String sday,String splatform,String inum,String imoney,String iaddnum,String iaddmoney,String icollection,String iplaynum)
	{
		if (inum.equals("")) inum="0";
		if (imoney.equals("")) imoney="0";
		if (iaddnum.equals("")) iaddnum="0";
		if (iaddmoney.equals("")) iaddmoney="0";
		if (icollection.equals("")) icollection="0";
		if (iplaynum.equals("")) iplaynum="0";
		
		iplaynum=reset(iplaynum);
		inum=reset(inum);
		iaddnum=reset(iaddnum);
		imoney=reset(imoney);
		iaddmoney=reset(iaddmoney);


		
		String sSql="update tFansLog set splatform='"+splatform+"' ";
		if (!inum.equals("0")) sSql=sSql+",inum="+inum;
		if (!imoney.equals("0")) sSql=sSql+",imoney="+imoney;
		if (!iaddnum.equals("0")) sSql=sSql+",iaddnum="+iaddnum;
		if (!iaddmoney.equals("0")) sSql=sSql+",iaddmoney="+iaddmoney;
		if (!icollection.equals("0")) sSql=sSql+",icollection="+icollection;
		if (!iplaynum.equals("0")) sSql=sSql+",iplaynum="+iplaynum;
		
		sSql=sSql+" where splatform='"+splatform+"' and convert(VARCHAR(10),dtinsert,120) ='"+sday+"'" ;
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			sSql="insert into tFansLog(dtinsert,splatform,inum,imoney,iaddnum,iaddmoney,icollection,iplaynum)"
					+ "values('"+sday+"','"+splatform+"',"+inum+","+imoney+","+iaddnum+","+iaddmoney+","+icollection+","+iplaynum+")";
			db.ExceSql("pftfdb", sSql);
		}
		
	}
	
	//设置扫描断点
	public static void set_scanning(String splatform,int irow)
	{
		String sSql="update tScanning set irow="+irow+",dtupdate=getdate() where  splatform ='"+splatform+"'" ;
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			sSql="insert into tScanning(dtupdate,splatform,irow)"
					+ "values(getdate(),'"+splatform+"',"+irow+")";
			db.ExceSql("pftfdb", sSql);
		}
	}
	
	//获取最后一条扫描的数据
	public static Map get_scanning()
	{
		String sSql="select top 1 * from  tScanning  order by irow, dtupdate desc" ;
		return  db.OpenOneSql_toMap("pftfdb", sSql);
	}
	
	//获取最后一条扫描的数据
	public static int get_scanning(String splatform)
	{
		String sSql="select top 1 * from  tScanning  where splatform='"+splatform+"'" ;
		
		
		Map ramp= db.OpenOneSql_toMap("pftfdb", sSql);
		if (ramp!=null)
		{
			int irow= (int) ramp.get("irow");
			Date dtupdate=(Date) ramp.get("dtupdate");
			
			if (irow<9999)
			{
				return irow;
			}
			else
			{
				String sday=pb.datetostr(dtupdate).substring(0,10);
				String snow=pb.datetostr(new Date()).substring(0,10);
				
				//当天扫描过
				if (sday.equals(snow))
				{
					return irow;
				}
				else
				{
					return 0;
				}
			}
			
		} 
		else
		{
			return 0;
		}
	}
	
	
	//写表，填入surl 和ID号
	public static void insert_tvideo_byurl(String splatform,String surl,String snum)
	{
		snum=reset(snum);
		//String sSql="select * from tvideo where splatform='"+splatform+"' and snum ='"+snum+"'" ;
		String sSql="update tvideo  set surl='"+surl+"' where splatform='"+splatform+"' and snum ='"+snum+"'" ;
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			sSql="insert into tvideo(splatform,snum,surl,sname)"
					+ "values('"+splatform+"','"+snum+"','"+surl+"','"+snum+"')";
			db.ExceSql("pftfdb", sSql);
		}
	}
	
	//写表，填 name 和ID号
	public static void insert_tvideo_byname(String splatform,String sname,String iplaynum,String dtadd)
	{
		iplaynum=reset(iplaynum);
		
		sname=sname.replaceAll("'", "''");
		//String sSql="select * from tvideo where splatform='"+splatform+"' and snum ='"+snum+"'" ;
		String sSql="update tvideo  set iplaynum='"+iplaynum+"'";
		if (!dtadd.equals(""))
			sSql=sSql	+ ",dtadd='"+dtadd+"'";
				
				sSql=sSql	+ " where splatform='"+splatform+"' and sname ='"+sname+"'" ;
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			if (!dtadd.equals(""))
			{
				sSql="insert into tvideo(splatform,iplaynum,sname,snum,dtadd)"
						+ "values('"+splatform+"','"+iplaynum+"','"+sname+"','','"+dtadd+"')";
			}
			else
			{
			sSql="insert into tvideo(splatform,iplaynum,sname,snum)"
					+ "values('"+splatform+"','"+iplaynum+"','"+sname+"','')";
			}
			db.ExceSql("pftfdb", sSql);
		}
	}
	
	//详细页面的时候，填写总播放量，并且吧名字填进去
	public static void set_tvideo_byurl(String splatform,String surl,String sname,String iplaynum,String dtadd)
	{
		
		if (iplaynum.equals("")) iplaynum="0";
		
		String sSql="update tvideo  set surl='"+surl+"' " ;
		if (!sname.equals("")) sSql=sSql+",sname='"+sname+"'";
		if (!iplaynum.equals("0")) sSql=sSql+",iplaynum="+iplaynum;
		if (!dtadd.equals("")) sSql=sSql+",dtadd='"+dtadd+"'";
				
		sSql=sSql + " where splatform='"+splatform+"' and surl='"+surl+"'";
		if (db.ExceSql("pftfdb", sSql)==0)
		{
			//添加一条
			//sSql="insert into tvideo(splatform,snum,surl)"
				//	+ "values('"+splatform+"','"+snum+"','"+surl+"')";
			//db.ExceSql("pftfdb", sSql);
		}
	}
	
	//根据时间，自己算总播放量
	public static void set_tFansLog_byday(String splatform,String sday)
	{
		String sSql="select sum(iplaynum) as iplaynum from tvideolog where splatform='"+splatform+"' and convert(VARCHAR(10),dtinsert,120) ='"+sday+"'";
		
		Map aa=db.OpenOneSql_toMap("pftfdb", sSql);
		
		String iplaynum=aa.get("iplaynum").toString();
		
		set_tFansLog( sday, splatform,"","","","","", iplaynum);
		
	}
	
	
	
	
	

}

package web.grabbing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import my.db.db;
import my.function.mylog;
import my.function.pb;

public class get_meipai{
	
	
//获取采集某一个用户的首页信息（播放数，粉丝数， 具体的视频详情）
public boolean get_main_page(String surl)
{
	
	 //前一天
	 Date date=new Date();
	//Calendar calendar =new GregorianCalendar();
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(calendar.DATE, -1);//前一天的
	date = calendar.getTime();
	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
	String sday = format.format(date);
	//System.out.print(sread);
	//计算总页数
	int ipage=0;
	try {
		Document doc = Jsoup.connect(surl).get();
		//查询总视频数量
		{
			//<span class="num">66.9万</span>
			Element row = doc.selectFirst("#rightUser > div.user-num > a.user-num-item.user-hv.first.dbl > span.user-txt.pa");
			if (row!=null)
			{
				System.out.println(row.text());
				ipage=(int) Math.ceil(  pb.atoi(row.text()) / 24.0);
				System.out.println("ipage:"+ipage);
			}
		}
		
		//查询总粉丝数
		{
			//<span class="num j_rss_count">438</span>
			Element row = doc.selectFirst("#rightUser > div.user-num > a:nth-child(4) > span.user-txt.pa");
			if (row!=null)
			{
				System.out.println(row.text());
				String inum=row.text();
				dao_grabbing.set_tFansLog(sday,dao_grabbing.splatform_meipai,inum,"","","","","");
			}
		}
		
		//获取页码信息(有问题)
		{
			for (int i=1;i<=ipage;i++)
			{
				String ssuburl="https://www.meipai.com/user/1074764441?single_column=0&p="+i;
				get_sub_page(ssuburl);
				
			}
		}
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
		
	
	return true;
}
	

public boolean get_sub_page(String surl)
{
	
	try {
		Document doc = Jsoup.connect(surl).get();
		
		//#mediasList > li:nth-child(1)
		int ideal=0;
		Elements mediasList = doc.select("#mediasList > li");
		if (mediasList!=null)
		{
			System.out.println(surl+":"+mediasList.size());
			
			for (Element row:mediasList)
			{
				//System.out.println(row.select("div.content-l-video.content-l-media-wrap.pr.cp>a").attr("href"));
				String surl1="https://www.meipai.com"+row.select("div.content-l-video.content-l-media-wrap.pr.cp>a").attr("href");
				String snum=row.select("div.content-l-video.content-l-media-wrap.pr.cp").attr("data-id");
				dao_grabbing.insert_tvideo_byurl(dao_grabbing.splatform_meipai, surl1, snum);
				ideal++;
			}
			
			System.out.println(ideal);
		}
		else
		{
			return false;
		}
		
		
		return true;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
}

	//通过http 完成所有视频的分析
	public void spfxbyhttpurl()
	{
		String sSql="select * from tvideo where splatform='"+dao_grabbing.splatform_meipai+"'";
		List<Map> urllst= db.OpenSql_toList("pftfdb", sSql);
		if (urllst==null) return;
		
		String surl="";
		
		int ifind=0;
		for (Map murl:  urllst)
		{
			ifind++;
			surl=murl.get("surl")==null? "" :  (String) murl.get("surl");
			//String sname =murl.get("sname")==null? "" :  (String) murl.get("sname");
			System.out.println(ifind+" surl:"+surl);
			if (surl.equals("")) continue;
			
			try {
				Document doc = Jsoup.connect(surl).get();
				
				Element ecnt=  doc.selectFirst("div[class=detail-info pr]"); 
				
				if (ecnt==null) continue;
				
				String inum=ecnt.selectFirst("div[class=detail-location]").text();
				
				inum=inum.replaceAll("播放", "");
				inum=dao_grabbing.reset(inum);
				
				String sname=ecnt.selectFirst("h1").text();
				String icollection=ecnt.selectFirst("span[class='detail-like dbl pr cp']").text(); //搜藏
				icollection=dao_grabbing.reset(icollection);
				//String a2=ecnt.selectFirst("span[class='detail-comment dbl pr']").text(); //评论
				
				//<div class="detail-time pa" itemprop="datePublished"><strong>18-12-19 17:10   01-08 17:05</strong></div>
				String dtadd=ecnt.selectFirst("div[class='detail-time pa']").text(); //添加日志
				dtadd=dao_grabbing.dayset(dtadd);
				
				//修正表里面的数据， 
				dao_grabbing.set_tvideo_byurl(dao_grabbing.splatform_meipai, surl, sname, inum ,dtadd);
				mylog.info(this.getClass().getName(),sname +":" +inum);
				
				String iaddnum=inum;
				
				//添加日志数据
				String sday=pb.datetostr(new Date()).substring(0,10);
				
				sSql="select top 1 * from tvideolog where splatform='"+dao_grabbing.splatform_meipai+"' and sname='"+sname+"'"
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

				dao_grabbing.set_alone_video(sday, dao_grabbing.splatform_meipai, sname, "", inum, iaddnum, icollection);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mylog.Err(surl);
			}
			
		}
	}

		
public static void main(String[] args) throws Exception 
 {
	/*get_meipai getse=new get_meipai();
	//getse.get_sub_page("https://www.meipai.com/user/1074764441?single_column=0&p=99");
	getse.get_main_page("https://www.meipai.com/user/1074764441?single_column=0");
	getse.spfxbyhttpurl();*/

 }
 }
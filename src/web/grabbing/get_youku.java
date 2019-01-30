package web.grabbing;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import my.function.myhttp;
import my.function.pb;

public class get_youku {
	
	/*
	 * Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
log(doc.title());
Elements newsHeadlines = doc.select("#mp-itn b a");
for (Element headline : newsHeadlines) {
  log("%s\n\t%s", 
    headline.attr("title"), headline.absUrl("href"));
}
	 * */
	
	
	//获取采集某一个用户的首页信息（播放数，粉丝数， 具体的视频详情）
	public boolean get_main_page(String surl)
	{
		String sread=myhttp.Get(surl);
		//System.out.print(sread);
		

		try {
			Document doc = Jsoup.parse(sread);
			
			//查询总视频播放量
			/*{
				Elements rows = doc.select("li[class=vnum]");
				if (rows.size()>0)
				{
					Element row = rows.get(0);
					System.out.println(row.attr("title").toString());
					System.out.println(row.select("em").text());
				}
			}
			
			//查询粉丝数
			{
				Elements rows = doc.select("li[class=snum]");
				if (rows.size()>0)
				{
					Element row = rows.get(0);
					System.out.println(row.attr("title").toString());
					System.out.println(row.select("em").text());
				}
			}*/
			
			//跳转到视频列表页，要求录入的时候录入视频列表页的url
			//http://i.youku.com/i/UMzM3NTIyNjg0MA==/videos
			/*{
				Elements rows = doc.select("ul[class=nav-menu]").select("li");
				Element row = rows.get(1);
				System.out.println(row.attr("href").toString());
        	
        	}*/
			
			//获取页码信息
			{
				//查询每一集的情况
				//body > div.window > div > div.s-body > div > div.layout-content > div > div > div > div.bd
				Elements rows = doc.selectFirst("ul[class=yk-pages]").select("a");
				if (rows.size()>0)
				{
					int imaxpage=0;
					for (Element row : rows) 
					{
						  // headline.attr("title"), headline.absUrl("href"));
						int i=pb.atoi(row.text());
						if (i>imaxpage) imaxpage=i;
						//System.out.println(row.attr("href"));
					}
					
					if (surl.indexOf("?")>0)
					{
						surl=surl.substring(0,surl.indexOf("?"));
					}
					
					for (int i=1;i<=imaxpage;i++)
					{
						System.out.println("第"+i+"页");
						String url=surl+"?"+"order=1&page="+i+"&last_item=&last_pn="+ imaxpage;
						get_sub_page(url);	
					}
					
				}
				
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return true;
	}
	
	
	public void get_sub_page(String surl)
	{
		String sread=myhttp.Get(surl);

		Document doc = Jsoup.parse(sread);
		
		//查询每一集的情况
		Elements rows = doc.select("div[class=v va]");
		if (rows.size()>0)
		{
			for (Element row : rows) 
			{
				  // headline.attr("title"), headline.absUrl("href"));
				
				//System.out.println(row.selectFirst("a").outerHtml());
				System.out.println("http:"+row.selectFirst("a").attr("href"));
				System.out.println(row.selectFirst("a").attr("title"));
				System.out.println(row.selectFirst("span[class=v-num]").text());
			}
		}
		
		
	}
	
	
	//获取总粉丝数
	public static boolean get_page_fs()
	{
		
		Map fs=new HashMap();
		
		String inum="";
		String iplaynum="";
		
		String sread=myhttp.Get("http://i.youku.com/u/UMzM3NTIyNjg0MA==?spm=a2h0k.11417342.soresults.dtitle");
		//System.out.print(sread);
		

		try {
			Document doc = Jsoup.parse(sread);
			
			//查询总视频播放量
			{
				Elements rows = doc.select("li[class=vnum]");
				if (rows.size()>0)
				{
					Element row = rows.get(0);
					System.out.println(row.attr("title").toString());
					System.out.println(row.select("em").text());
					//fs.put("iplaynum", row.attr("title").toString());
					iplaynum = row.attr("title").toString();
					iplaynum=dao_grabbing.reset(iplaynum);
				}
			}
			
			//查询粉丝数
			{
				Elements rows = doc.select("li[class=snum]");
				if (rows.size()>0)
				{
					Element row = rows.get(0);
					System.out.println(row.attr("title").toString());
					System.out.println(row.select("em").text());
					//fs.put("inum", row.attr("title").toString());
					
					inum = row.attr("title").toString();
					inum=dao_grabbing.reset(inum);
				}
			}
			
			String sday = pb.datetostr(new Date()).substring(0,10);
			if (pb.atof(inum)!=0)
			{
				dao_grabbing.set_tFansLog(sday, dao_grabbing.splatform_youku,inum , "", "", "", "",iplaynum);
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
			
		
		return false;
	}
	
	
	public static void main(String[] args) throws Exception 
	 {
		get_page_fs();
		
		
		//get_youku youku=new get_youku();
		//youku.get_main_page("http://i.youku.com/i/UMzM3NTIyNjg0MA==/videos?order=1&page=25&last_item=&last_pn=1&last_vid=964960290");
	 
	 }

}

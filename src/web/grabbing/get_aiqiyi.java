package web.grabbing;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import my.function.pb;
import tool.phantomjs.phantomjs;

public class get_aiqiyi {
	
	//获取采集某一个用户的首页信息（播放数，粉丝数， 具体的视频详情）
	public boolean get_main_page(String surl)
	{
		//System.out.print(sread);

		try {
			Document doc = Jsoup.connect(surl).get();
			//查询总视频播放量
			{
				//<span class="num">66.9万</span>
				Element row = doc.selectFirst("span[class=num]");
				if (row!=null)
				{
					System.out.println(row.text());
				}
			}
			
			//查询订阅数
			{
				//<span class="num j_rss_count">438</span>
				Element row = doc.selectFirst("span[class=num j_rss_count]");
				if (row!=null)
				{
					System.out.println(row.text());
				}
			}
			
			//获取页码信息(有问题)
			{
				//查询每一集的情况<span class="btn_inner">47</span>
				Elements rows = doc.select("span[class=btn_inner]");
				if (rows.size()>0)
				{
					int imaxpage=0;
					for (Element row : rows) 
					{
						  // headline.attr("title"), headline.absUrl("href"));
						int i=pb.atoi(row.text());
						if (i>imaxpage) imaxpage=i;
					}
					
					if (surl.indexOf("?")>0)
					{
						surl=surl.substring(0,surl.indexOf("?"));
					}
					
					for (int i=1;i<=imaxpage;i++)
					{
						System.out.println("第"+i+"页");
						String url=surl+"?"+"order=1&page="+i+"&last_item=&last_pn="+ imaxpage;
						System.out.println(url);
						//get_sub_page(url);	
					}
					
				}
				
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return true;
	}
			
			
	public static void main(String[] args) throws Exception 
	 {
		get_aiqiyi youku=new get_aiqiyi();
		youku.get_main_page("http://v.qq.com/vplus/a6f819e4bd039d58d8e3af8bd4c40ab9/videos");
	 
	 }

}

package web.grabbing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import my.function.pb;

public class get_Zone {
	
	//获取采集某一个用户的首页信息（播放数，粉丝数， 具体的视频详情）
		public boolean get_main_page(String surl)
		{
			//System.out.print(sread);

			try {
				Document doc = Jsoup.connect(surl).get();
			
				
				//获取页码信息(有问题)
				{
					
					Element clist = doc.getElementById("clist");
					
					//查询每一集的情况<span class="btn_inner">47</span>
					Elements dts = clist.select("dt");
					Elements dds = clist.select("dd");
					if (dts.size()>0)
					{
						int imaxpage=0;
						
						for (int i=0; i<dts.size();i++)
						{
							System.out.println(dts.get(i).text());
							
							Elements as= dds.get(i).select("a");
							for (Element row : as) 
							{
								System.out.println(row.text());
							}
							
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
			get_Zone youku=new get_Zone();
			youku.get_main_page("http://www.zuojihao.com/city/");
		 
		 }

}

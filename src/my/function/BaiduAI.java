package my.function;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.aip.ocr.AipOcr;

import sun.misc.BASE64Decoder;

public class BaiduAI {
	
	//设置APPID/AK/SK
    public static final String APP_ID = "9913483";
    public static final String API_KEY = "Sxm4a1NDtWNsQC2e7YmwxrcD";
    public static final String SECRET_KEY = "wCFyYSQvtvPiEflykQSwlDQECmgwLsTG";
	
	public static String get_image(String imgStr)
	{
		int ipos=imgStr.indexOf(",");
		
		if (ipos>0) imgStr=imgStr.substring(ipos+1);
		
		// 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        
     // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("recognize_granularity", "big");
	    options.put("detect_direction", "true");
	    
	    BASE64Decoder decoder = new BASE64Decoder();
	    
	    byte[] file;
		try {
			file = decoder.decodeBuffer(imgStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0";
		}

		/*
		 * {
  "direction": 0,
  "log_id": 1369842577911336594,
  "words_result": [{
    "location": {
      "height": 44,
      "left": 0,
      "top": 8,
      "width": 233
    },
    "words": "3858691"
  }],
  "words_result_num": 1
}*/
	    // 参数为二进制数组
        JSONObject  res = client.numbers(file, options);
	    //System.out.println(res.toString(2));
        if (res!=null)
        {
        	
        	try {
				JSONObject a0= (JSONObject) res.getJSONArray("words_result").get(0);
				return a0.getString("words");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return "0";
		
	}

}

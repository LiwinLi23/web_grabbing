package my.function;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class base64_pic {
	
	/**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     */
    public static boolean GenerateImage(String imgStr, String filepath) 
    {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
// 解密
            byte[] b = decoder.decodeBuffer(imgStr);
// 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(filepath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) 
        {
            return false;
        }
    }
    
    //图片转化成base64字符串  
    public static String GetImageStr(String imgFile)  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        //String imgFile = "F:\\tupian\\a.jpg";//待处理的图片
                         // 地址也有写成"F:/deskBG/86619-107.jpg"形式的
        InputStream in = null;  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            in = new FileInputStream(imgFile);          
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }
    
    
    //实际转换，可能前面有一节数据
    public static String GenerateImagebyHead(String base64data,String path,String filename) 
    {
        //这一步很重要很重要很重要，因为base64的数据会有data：base64img，
        //所有需要将这个截取掉之后转化，不然就是空白的打不开的文件
    	//data:image/bmp;base64,Qk22Rw...
    	//这里要判断下图片类型
    	String base64Suffix=  base64data.substring(base64data.indexOf("/")+1,base64data.indexOf(";"));
        String base64img = base64data.substring(base64data.indexOf(",")+1);
        
        String pathfile= path+filename+"."+base64Suffix;

        if (GenerateImage(base64img,pathfile))
        return pathfile;
        else
        return null;
      }

}

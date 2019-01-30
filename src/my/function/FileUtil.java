/*
 *  @(#)FileUtil.java  1.0   2013-1-9
 *  Copyright (c) 2012, 北京优听信息技术有限公司
 */
package my.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * 类或接口：FileUtil<br>
 * 功能描述：文件工具类
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys 
 */
public class FileUtil {
	
	public static boolean exists(String fileUrl){
		return new File(fileUrl).exists();
	}
	
	
	/**
	 * 成员方法：createFile()<br>
	 * 功能描述：创建文本文件
	 * @param   filePath - 文件路径（系统路径，最后应该包括/）
	 * @param   fileName - 文件名
	 * @param   fileContent - 文件内容
	 * @param   charset - 字符集
	 * @return  boolean - 是否创建成功
	 * @throws  无
	 */
	public static boolean createFile(String filePath,String fileName,String fileContent,String charset){
		mylog.info("createFile:"+filePath+fileName);
		boolean result=false;
		File file = null;
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		BufferedWriter bw = null;
		try{
			file = new File(filePath);
			//判断文件夹是否存在,如果不存在则创建文件夹
			if (!file.exists()) {
				file.mkdirs();
			}
			//将内容写入文件
			fos=new FileOutputStream(filePath+fileName);
			osw=new OutputStreamWriter(fos,charset);
			bw = new BufferedWriter(osw);
			bw.write(fileContent);
			bw.flush();
			result=true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null) bw.close();
			} catch (IOException e) {}
			try {
				if(osw!=null) osw.close();
			} catch (IOException e) {}
			try {
				if(fos!=null) fos.close();
			} catch (IOException e) {}
		}
		return result;
	}
	/**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File file) {
       boolean flag = false;
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 读取文本文件内容
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding 文本文件打开的编码方式
     * @return  返回文本文件的内容
     * @throws IOException
     */
    public static String readFile(String filePathAndName,String encoding){
    	
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String result = "";
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
        	fs = new FileInputStream(filePathAndName);
        	if(encoding.equals("")){
        		isr = new InputStreamReader(fs);
        	}else{
        		isr = new InputStreamReader(fs,encoding);
        	}
        	br = new BufferedReader(isr);
        	try{
        		String data = "";
        		while((data = br.readLine())!=null){
        			str.append(data+"\n"); 
        		}
        	}catch(Exception e){
        		str.append(e.toString());
        	}
        	result = str.toString();
        }catch(IOException es){
        	result = "";
        }finally{
			try {
				if(br!=null)
				br.close();
			} catch (IOException e) {}
			try {
				if(isr!=null) isr.close();
			} catch (IOException e) {}
			try {
				if(fs!=null) fs.close();
			} catch (IOException e) {}
        }
        return result;     
    }
}

package my.function;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CMPPUtil {
	/**
	 * 从流中读取指定长度的字节 转换成字符串
	 * @return
	 * @throws IOException 
	 */
	public static String readString(ByteBuffer din,int len) throws IOException{
//		//1.异常如何处理
//		//2.编码方式
//		byte[] b = new byte[len];
//		din.readFully(b);
//		String s = new String(b);
//		return s;
		 byte[] b = new byte[len];
	     din.get(b);
	     String s = new String(b,"GBK");
	     return s.trim();
	}

	/**
	 * 1:
	 * @param status 状态
	 * @param authsource 
	 * @param sercet
	 * @return
	 */
	public static byte[] getMd5AuthIsmg(int status,byte[] authsource,String sercet){
		try{
			java.security.MessageDigest md5 = MessageDigest.getInstance("MD5");
			String auth=new String(authsource);
			String authMd5 = status  + auth+ sercet;
			byte[] data = authMd5.getBytes();
			byte[] md5_result = md5.digest(data);
			return md5_result;
		}catch(Exception ex){ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 1:判断字节数组是否相等
	 * 
	 * @param auth1
	 *            字节数组1
	 * @param auth2
	 *            字节数组2
	 * @return
	 */
	public static boolean byteEquals(byte[] auth1, byte[] auth2) {
		debugData("SP-MD5\n", auth1);
		debugData("IMSG-MD5\n", auth2);
		int length1 = auth1.length;
		int length2 = auth2.length;
		if (length1 != length2) {
			return false;
		}
		for (int i = 0; i < length1; i++) {
			if (auth1[i] != auth2[i]) {
				return false;
			}
		}
		return true;
	}



	/**
	 * SP接收到的认证信息需要SP确认
	 * 
	 * @return
	 */
	public static byte[] getLoginMD5(String pid, String pwd, String timeStamp) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String authMd5 = pid + "\0\0\0\0\0\0\0\0\0" + pwd + timeStamp;
			byte[] data = authMd5.getBytes();
			byte[] md5_result = md5.digest(data);
			return md5_result;
		} catch (NoSuchAlgorithmException e) {
			//mylog.ErrStackTrace("Util:生成登陆串发生异常\n");
			mylog.ErrStackTrace(e);
		}
		return null;
	}

	/**
	 * 消息流水号
	 * squence
	 * @return
	 */
	public static int sque = 1;
	public static int getSequence(){
		
		if(sque >= 9999){
			sque = 1000;
		}
		Date d = new Date();
		String s = d.getTime()+"";
		s = s.substring(4, 8);//月和天
		s = s+sque;
		sque++;
		return Integer.parseInt(s);
	}

	public static long getMsg_Id(){
		return new Date().getTime();
	}

	/**
	 * 时间戳
	 * @return
	 */
	public static String getMMDDHHMMSS() {
		SimpleDateFormat fomart = new SimpleDateFormat("MMddhhmmss");
		Date date = new Date();
		return fomart.format(date);
	}
	
	//自己写的
	/**
	 * 1:未满补零
	 * 
	 * @param dos    写出流
	 * @param length 长度
	 * @param input  写出的定长字符串
	 */
	public static boolean writeFully(ByteBuffer dos, int lenwrite, String input)
	{
		//byte[] bb = new byte[length];
		byte[] in;
		try {
			in = input.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//填空补全
			in = new byte[lenwrite];
			dos.put(in);
			return false;
		}
		
		int len = in.length;
		if (len > lenwrite) 
		{
			//截断一部分
			dos.put(in, 0, lenwrite);
		}
		else
		{
			dos.put(in);
			while (len < lenwrite) {
				dos.put((byte)0);
				len++;
			}
		}
		return true;
	}
	
	
	/**
	 * 1:未满补零
	 * 
	 * @param dos    写出流
	 * @param length 长度
	 * @param input  写出的定长字符串
	 */
	public static void writeFully(DataOutputStream dos, int length, String input)throws Exception {
		
		byte[] bb = new byte[length];
		byte[] in = input.getBytes("GBK");
		int len = in.length;
		if (len > bb.length) {
			throw new Exception("写入字符串过长\n");
		}
		try {
			dos.write(in);
			while (len < bb.length) {
				dos.writeByte(0);
				len++;
			}
		} catch (Exception ex) {
			//Client.setText("Util:writeFully发生异常\n");
			mylog.ErrStackTrace(ex);
		}

	}


	/**
	 * 调试消息原始数据
	 * 
	 * @param dir
	 *            :消息发送方向说明
	 * @param data
	 *            :消息数据
	 */
	public static void debugData(String dir,byte[] data){
		StringBuffer sb = new StringBuffer();
		sb.append(dir);
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			int b = data[i];
			if (b < 0) {
				b += 256;
			}
			// 16进制如果不满2位则补零
			String hexString = Integer.toHexString(b);
			hexString = (hexString.length() == 1) ? "0" + hexString : hexString;
			sb.append(hexString);
			sb.append("  ");
			count++;
			if (count % 4 == 0) {
				sb.append(" ");
			}
			if (count % 16 == 0) {
				
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		//Client.setText("Util:"+sb.toString());
	}

	  /**
	   * 公有方法，将当前时间格式化,格式化为12/12 06:50
	   * @return String
	   */
	  public static String getFormatTime() {
	    Calendar now = Calendar.getInstance();
	    String mon = Integer.toString(now.get(Calendar.MONTH) + 1);
	    String day = Integer.toString(now.get(Calendar.DAY_OF_MONTH));
	    String hour = Integer.toString(now.get(Calendar.HOUR_OF_DAY));
	    String min = Integer.toString(now.get(Calendar.MINUTE));
	    String sec = Integer.toString(now.get(Calendar.SECOND));
	    mon = (mon.length() == 1) ? "0" + mon : mon;
	    day = (day.length() == 1) ? "0" + day : day;
	    hour = (hour.length() == 1) ? "0" + hour : hour;
	    min = (min.length() == 1) ? "0" + min : min;
	    sec = (sec.length() == 1) ? "0" + sec : sec;
	    return (mon + "-" + day + " " + hour + ":" + min + ":" + sec);
	  }
	/////////////////////////////////////////////////////////
	/**
	* byte   long   转换 代码	  
	* @throws IOException 
	*/
	   public static long readLong(DataInputStream din) throws IOException{
		   byte[] b = new byte[8];
		   din.read(b);
		   ByteBuffer buf = ByteBuffer.allocate(8);
		   buf.put(b);
	       buf.flip();
	       long l = Long.reverseBytes(buf.getLong());
	       return l;
	   }
	   
	   
	   
	   
	  public static byte[] writeLong(long l){
	      byte[] b = new byte[8]; 
	      putReverseBytesLong(b, l, 0); 
	      return b;
	  }
	  
	  public static byte[] writeInt(int i){
		  byte[] b = new byte[4]; 
	      putReverseBytesInt(b, i, 0);
	      putInt(b, i, 0);
	      debugData("",b);
	      return b;
	  }
	  
	  
	  
	 /* public static String getProperties(String key){
			InputStream in=PropHelper.guessPropFile(PropHelper.class,"cmpppara.properties");   
			String values = null;
			if(in!=null){
				Properties pro = new Properties();
				try {
					pro.load(in);
					in.close();
					values = pro.getProperty(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return values;
	  }*/
	  
	  
	    public static void putLong(byte[] bb, long x, int index) { 
	        bb[index + 0] = (byte) (x >> 56); 
	        bb[index + 1] = (byte) (x >> 48); 
	        bb[index + 2] = (byte) (x >> 40); 
	        bb[index + 3] = (byte) (x >> 32); 
	        bb[index + 4] = (byte) (x >> 24); 
	        bb[index + 5] = (byte) (x >> 16); 
	        bb[index + 6] = (byte) (x >> 8); 
	        bb[index + 7] = (byte) (x >> 0); 
	    } 
	    public static void putReverseBytesLong(byte[] bb, long x, int index) { 
	        bb[index + 7] = (byte) (x >> 56); 
	        bb[index + 6] = (byte) (x >> 48); 
	        bb[index + 5] = (byte) (x >> 40); 
	        bb[index + 4] = (byte) (x >> 32); 
	        bb[index + 3] = (byte) (x >> 24); 
	        bb[index + 2] = (byte) (x >> 16); 
	        bb[index + 1] = (byte) (x >> 8); 
	        bb[index + 0] = (byte) (x >> 0); 
	    } 
	    public static long getLong(byte[] bb, int index) { 
	        return ((((long) bb[index + 0] & 0xff) << 56) 
	                | (((long) bb[index + 1] & 0xff) << 48) 
	                | (((long) bb[index + 2] & 0xff) << 40) 
	                | (((long) bb[index + 3] & 0xff) << 32) 
	                | (((long) bb[index + 4] & 0xff) << 24) 
	                | (((long) bb[index + 5] & 0xff) << 16) 
	                | (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0)); 
	    } 
	    public static long getReverseBytesLong(byte[] bb, int index) { 
	        return ((((long) bb[index + 7] & 0xff) << 56) 
	                | (((long) bb[index + 6] & 0xff) << 48) 
	                | (((long) bb[index + 5] & 0xff) << 40) 
	                | (((long) bb[index + 4] & 0xff) << 32) 
	                | (((long) bb[index + 3] & 0xff) << 24) 
	                | (((long) bb[index + 2] & 0xff) << 16) 
	                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0)); 
	    }
	//////////////////////////////////////////////////////////////    

	    
	    
	    public static void putInt(byte[] bb, int x, int index) { 
	        bb[index + 0] = (byte) (x >> 24); 
	        bb[index + 1] = (byte) (x >> 16); 
	        bb[index + 2] = (byte) (x >> 8); 
	        bb[index + 3] = (byte) (x >> 0); 
	    } 
	    public static int getInt(byte[] bb, int index) { 
	        return (int) ((((bb[index + 0] & 0xff) << 24) 
	                | ((bb[index + 1] & 0xff) << 16) 
	                | ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0))); 
	    } 
	    
	    
	    public static int readInt(DataInputStream din) throws IOException{
			   byte[] b = new byte[4];
			   din.read(b);
			   ByteBuffer buf = ByteBuffer.allocate(4);
			   buf.put(b);
		       buf.flip();
		       int bs = getInt(b, 0);
		       return bs;
		   }
	    
	    public static int readInt(ByteBuffer din) 
	    {
			   byte[] b = new byte[4];
			   din.get(b);
		       int bs = getInt(b, 0);
		       return bs;
		   }
	    
	    public static void putReverseBytesInt(byte[] bb, int x, int index) { 
	        bb[index + 3] = (byte) (x >> 24); 
	        bb[index + 2] = (byte) (x >> 16); 
	        bb[index + 1] = (byte) (x >> 8); 
	        bb[index + 0] = (byte) (x >> 0);
	    }

	  
	    /** 
	     * 字节数组转16进制 
	     * @param bytes 需要转换的byte数组 
	     * @return  转换后的Hex字符串 
	     */  
	    public static String bytesToHexHead(byte[] bytes,int ilen) 
	    {  
	    	if (bytes.length<ilen)  ilen=bytes.length;
	    	
	        StringBuffer sb = new StringBuffer();  
	        for(int i = 0; i < ilen; i++) {  
	            String hex = Integer.toHexString(bytes[i] & 0xFF);  
	            if(hex.length() < 2){  
	                sb.append(0);  
	            }  
	            sb.append(hex);  
	        }  
	        return sb.toString();  
	    } 

	    
	   public static String getsmsUCS2msg(byte[] buf,byte TP_udhi)
	   {
		   String msg="";
		   if (TP_udhi==1)
			{
			   try {
				   byte ilen=(byte) (buf[0]+1);
				   msg=new String(buf,ilen,buf.length-ilen,"UnicodeBigUnmarked");
				   msg=bytesToHexHead(buf,ilen)+msg;
			   } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
				
			}
			else
			{
				try {
					msg=new String(buf,"UnicodeBigUnmarked");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   
		   return msg;
	   }  
	   
	   public static String getsmsmsg(byte[] buf,byte TP_udhi,byte MSG_Fmt)
	   {
		   //0：ASCII串；3：短信写卡操作；4：二进制信息；8：UCS2编码；15：含GB汉字。。。。。。
		   String msg="";
		   if (TP_udhi==1)
			{
			   try {
				   byte ilen=(byte) (buf[0]+1);
				   
				   if (MSG_Fmt==8)
				   {
					   msg=new String(buf,ilen,buf.length-ilen,"UnicodeBigUnmarked");
					   msg=bytesToHexHead(buf,ilen)+msg;
				   }
				   else if (MSG_Fmt==15)
				   {
					   msg=new String(buf,ilen,buf.length-ilen,"GBK"); 
					   msg=bytesToHexHead(buf,ilen)+msg;
				   }
					   
				   else
					   msg=pb.byte2hex(buf);
				   
				   
			   } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }
				
			}
			else
			{
				try {
					if (MSG_Fmt==8)
						msg=new String(buf,"UnicodeBigUnmarked");
					 else if (MSG_Fmt==15)
						 msg=new String(buf,"GBK");
					 else
						 msg=pb.byte2hex(buf);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		   
		   return msg;
	   } 
	   
	   public static String QuotedStrEx(String smsg)
	   {
		   smsg=smsg.replace("'","''");
		   return smsg;
	   }
	   
	 }



	


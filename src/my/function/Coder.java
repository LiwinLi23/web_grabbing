/**
 * request参数加解密
 */
package my.function;

import java.net.URLEncoder;
import java.security.MessageDigest;

public class Coder {
	public static String encode(String params){
		String result="";
		try{
			params = URLEncoder.encode(params, "utf-8");
			// Xor加密
			byte[] bytPara = params.getBytes();
			for (int i = 0; i < bytPara.length; i++) {
				bytPara[i] = (byte) (bytPara[i] ^ 0x6e);
			}
			// 将原始数据编码为base64编码
			char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
					.toCharArray();
			char[] pchars = new char[((bytPara.length + 2) / 3) * 4];
			for (int i = 0, index = 0; i < bytPara.length; i += 3, index += 4) {
				boolean quad = false;
				boolean trip = false;
				int val = (0xFF & (int) bytPara[i]);
				val <<= 8;
				if ((i + 1) < bytPara.length) {
					val |= (0xFF & (int) bytPara[i + 1]);
					trip = true;
				}
				val <<= 8;
				if ((i + 2) < bytPara.length) {
					val |= (0xFF & (int) bytPara[i + 2]);
					quad = true;
				}
				pchars[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
				val >>= 6;
				pchars[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
				val >>= 6;
				pchars[index + 1] = alphabet[val & 0x3F];
				val >>= 6;
				pchars[index + 0] = alphabet[val & 0x3F];
			}
			params = new String(pchars);
			result = URLEncoder.encode(params, "utf-8");
			//url="action="+action+"&data="+parameter+"&verf="+verf+"&format="+format+"&pv="+pv;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static String verf(String params){
		String result="";
		try{
			params=URLEncoder.encode(params,"utf-8");
			//MD5校验
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(params.getBytes());
			byte[] md=mdInst.digest();
			int j = md.length;
		    char str[] = new char[j * 2];
		    int k = 0;
		    for (int i = 0; i < j; i++) {
			    byte byte0 = md[i];
			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			    str[k++] = hexDigits[byte0 & 0xf];
		    }
		    result =new String(str);
		}catch(Exception e){
			e.printStackTrace();
		}
	    return result;
	}
	
	public static String decode(String params){
		return "";
	}
	
	public static String MD5(String params){
		String result="";
		try{
			//params=URLEncoder.encode(params,"utf-8");
			//MD5校验
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','A', 'B', 'C', 'D', 'E', 'F' };
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(params.getBytes());
			byte[] md=mdInst.digest();
			int j = md.length;
		    char str[] = new char[j * 2];
		    int k = 0;
		    for (int i = 0; i < j; i++) {
			    byte byte0 = md[i];
			    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			    str[k++] = hexDigits[byte0 & 0xf];
		    }
		    result =new String(str);
		}catch(Exception e){
			e.printStackTrace();
		}
	    return result;
	}
	
}

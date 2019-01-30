/*
 *  @(#)StringUtil.java  1.0   2012-12-24
 *  Copyright (c) 2012, 北京优听信息技术有限公司
 */
package my.function;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类或接口：StringUtil<br>
 * 功能描述：String工具类
 * 
 * @author Xu Tieqiang
 * @version 1.0
 * @librarys
 */
public class StringUtil {
	/*
	 * 成员方法：string2map()<br> 功能描述：String转Map，单String值中的值为"k=v"形式
	 * 
	 * @param source - String类型，存储"k=v"值的字符串
	 * 
	 * @param regex - String类型，分隔的字符
	 * 
	 * @return Map<String,String> - map形式
	 * 
	 * @throws 无
	 */
	@SuppressWarnings("rawtypes")
	public static Map string2map(String source, String regex) {
		Map map = new HashMap();
		return string2map(source, regex, map);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map string2map(String source, String regex, Map map) {
		String[] strData = source.split(regex);
		for (int i = 0; i < strData.length; i++) {
			int token = strData[i].indexOf("=");
			// 将等号前面的为Key,等号后面的为Value
			try {
				map.put(strData[i].substring(0, token), strData[i].substring(token + 1));
			} catch (Exception e) {
				// 产生异常outofindex一般是由于tm或chu参数值里有&特殊字符，导致该数组中某值没有=符号
				System.out.println("执行strings2map()方法时发生错误，参数String[]为：");
				System.out.println(Arrays.asList(strData).toString());
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 填充0
	 * 
	 * @param str
	 *            原始字符串
	 * @param len
	 *            最终长度
	 * @param position
	 *            l：left左侧加0；r：right右侧加0
	 * @return 字符串
	 */
	public static String addZero(String str, int len, String position) {
		if (len <= 0)
			len = 1;
		if (str.length() < len) {
			byte[] b = new byte[len];
			for (int i = 0; i < len; i++) {
				b[i] = '0';
			}
			byte[] s = str.getBytes();
			if ("l".equals(position)) {
				for (int i = 1; i <= s.length; i++) {
					b[len - i] = s[str.length() - i];
				}
				return new String(b);
			} else if ("r".equals(position)) {
				for (int i = 0; i < s.length; i++) {
					b[i] = s[i];
				}
				return new String(b);
			} else {
				return str;
			}
		} else if (str.length() == len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * 成员方法：isNumeric()<br>
	 * 功能描述：判断是否为数字，整数、小数、负数（带-号）、正数（带+号）、int类型、long类型、超长数字都返回true
	 * 
	 * @param source
	 *            - String类型，被判断的字符串源
	 * @return boolean - 是数字则返回true，不是数字则返回false
	 * @throws 无
	 */
	public static boolean isNumeric(String source) {
		return source.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 成员方法：isUnsignedInteger()<br>
	 * 功能描述：判断是否为无符号整数，即正整数和零都返回true
	 * 
	 * @param source
	 *            - String类型，被判断的字符串源
	 * @return boolean - 是数字则返回true，不是数字则返回false
	 * @throws 无
	 */
	public static boolean isUnsignedInteger(String source) {
		return source.matches("[0-9]+");
	}

	/**
	 * 
	 * @param type
	 *            类型 1纯数字；2纯字母；3混合
	 * @param length
	 * @return
	 */
	public static String getRandomStringByLength(int type, int length) {
		String base = "";
		switch (type) {
		case 1:
			base = "0123456789";
			break;
		case 2:
			base = "abcdefghijklmnopqrstuvwxyz";
			break;
		default:
			base = "abcdefghijklmnopqrstuvwxyz0123456789";
			break;
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 成员方法：toInt()<br>
	 * 功能描述：String转int，如果source=null或转化失败，则返回默认值defaultValue
	 * 
	 * @param String
	 *            source - 原字符串 int defaultValue - 默认返回值
	 * @return int - 转化后的int
	 */
	public static int toInt(String source, int defaultValue) {
		int result = defaultValue;
		if (source != null) {
			try {
				result = Integer.parseInt(source);
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static int toInt(Object source, int defaultValue) {
		int result = defaultValue;
		if (source != null) {
			try {
				result = Integer.parseInt(source.toString());
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 成员方法：toInt()<br>
	 * 功能描述：String转int，如果source=null或转化失败，则返回默认值0
	 * 
	 * @param String
	 *            source - 原字符串
	 * @return int - 转化后的int
	 */
	public static int toInt(String source) {
		return toInt(source, 0);
	}

	public static long toLong(String source, long defaultValue) {
		long result = defaultValue;
		try {
			result = Long.parseLong(source);
		} catch (Exception e) {
		}
		return result;
	}

	public static double toDouble(String source, double defaultValue) {
		double result = defaultValue;
		try {
			result = Double.parseDouble(source);
		} catch (Exception e) {
		}
		return result;
	}

	public static String toString(Object source, String defaultValue) {
		String result = defaultValue;
		if (source != null) {
			try {
				result = source.toString();
			} catch (Exception e) {
			}
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static String mergeString(List source, String splitChar) {
		String result = "";
		if (source != null && source.size() > 0) {
			try {
				for (int i = 0; i < source.size(); i++) {
					if (source.get(i) != null) {
						result += source.get(i).toString() + splitChar;
					}
				}
			} catch (Exception e) {
			}
		}
		// 去掉末尾的splitChar分隔符
		if (result.endsWith(splitChar))
			result.subSequence(0, result.length() - splitChar.length());
		return result;
	}

	// 是否是手机号
	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null || mobiles.length() < 11)
			return false;
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	//做urlencoder编码
	public static String urlencode(String source) {
		String result=source;
		try{
			result=URLEncoder.encode(source,"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}

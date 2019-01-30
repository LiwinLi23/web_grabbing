package my.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class xmlConvertMapUtil {
	
	/**  
     * xml 转 Map  
     * @param xml  
     * @return  
     */  
    public static Map<String,String> Dom2Map(String xml)  
    {  
        Map<String,String> map = new HashMap<String, String>();  
        Document doc = null;  
        try {  
            doc = DocumentHelper.parseText(xml);  
        } catch (DocumentException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        if(doc ==null)  
            return map;  
        Element root = doc.getRootElement();  
      for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {  
         Element e = (Element) iterator.next();  
         map.put(e.getName(), e.getText());  
      }  
      return map;  
    }  
  
  
    
      
    /**  
     * Map 转 XML  
     * @param map  
     * @return  
     */  
    public static byte[] callMapToXML(Map map) {  
        System.out.println("将Map转成Xml, Map：" + map.toString());  
        StringBuffer sb = new StringBuffer();  
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><MESSAGE>");  
        mapToXMLTest2(map, sb);  
        sb.append("</MESSAGE>");  
        System.out.println("将Map转成Xml, Xml：" + sb.toString());  
        try {  
            return sb.toString().getBytes("UTF-8");  
        } catch (Exception e) {  
            System.out.println(e);  
        }  
        return null;  
    }  
  
    private static void mapToXMLTest2(Map map, StringBuffer sb) {  
        Set set = map.keySet();  
        for (Iterator it = set.iterator(); it.hasNext();) {  
            String key = (String) it.next();  
            Object value = map.get(key);  
            if (null == value)  
                value = "";  
            if (value.getClass().getName().equals("java.util.ArrayList")) {  
                ArrayList list = (ArrayList) map.get(key);  
                sb.append("<" + key + ">");  
                for (int i = 0; i < list.size(); i++) {  
                    HashMap hm = (HashMap) list.get(i);  
                    mapToXMLTest2(hm, sb);  
                }  
                sb.append("</" + key + ">");  
  
            } else {  
                if (value instanceof HashMap) {  
                    sb.append("<" + key + ">");  
                    mapToXMLTest2((HashMap) value, sb);  
                    sb.append("</" + key + ">");  
                } else {  
                    sb.append("<" + key + ">" + value + "</" + key + ">");  
                }  
  
            }  
  
        }  
    } 

}

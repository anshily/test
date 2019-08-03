package io.anshily.base.utils;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * ***************************************************************************
 * 
 * @程序名: XmlUtil.java
 *
 */
@SuppressWarnings("rawtypes")
public class XmlUtil {

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> parseXMLList(String xml) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (CheckData.isNotEmpty(xml)) {
			try {
				StringReader xmlReader = new StringReader(xml);
				InputSource xmlSource = new InputSource(xmlReader);
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(xmlSource);
				Element elt = doc.getRootElement();
				List<Element> child = elt.getChildren();
				for (Element childEle : child) {
					Map<String, Object> map = new HashMap<String, Object>();
					map = parseChildXml(childEle, map);
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseXMLMap(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (CheckData.isNotEmpty(xml)) {
			try {
				StringReader xmlReader = new StringReader(xml);
				InputSource xmlSource = new InputSource(xmlReader);
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(xmlSource);
				Element elt = doc.getRootElement();
				List<Element> child = elt.getChildren();
				for (Element childEle : child) {
					map.put(childEle.getName(), childEle.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> parseChildXml(Element child, Map<String, Object> parentMap) {
		if (parentMap == null) {
			parentMap = new HashMap<String, Object>();
		}
		List<Element> child1 = (List<Element>) child.getChildren();
		if (child1.size() != 0) {
			for (Element childEle1 : child1) {
				parseChildXml(childEle1, parentMap);
			}
		} else {
			parentMap.put(child.getName(), child.getValue());
		}
		return parentMap;
	}
	
	public static String jsontoXml(List list) {  
        try {  
			@SuppressWarnings("unused")
			XMLSerializer xmlSerializer = new XMLSerializer();
            JSON jsonObject = JSONSerializer.toJSON(list);
            String jsonStr = jsonObject.toString();
            JSONArray jsonArryObject = JSONArray.fromObject(jsonStr);
            
            StringBuffer sb = new StringBuffer("<info_list>\n");
           
          
            
            for (int i = 0; i < jsonArryObject.size(); i++) {
            	 sb.append("<info>\n");
            	 JSONObject jsonObj = jsonArryObject.getJSONObject(i);
            	 sb.append("<dnbid>"+jsonObj.get("dnbid")+"</dnbid>\n");
            	 sb.append("<pooling_no>"+jsonObj.get("pooling_no")+"</pooling_no>\n");
            	 
            	 sb.append("<libraryPrePartNo>"+jsonObj.get("libraryPrePartNo")+"</libraryPrePartNo>\n");
            	 sb.append("<extractionPartNo>"+jsonObj.get("extractionPartNo")+"</extractionPartNo>\n");
            	 sb.append("<sequencingPartNo>"+jsonObj.get("sequencingPartNo")+"</sequencingPartNo>\n");
            	 sb.append("<libraryPreLotNo>"+jsonObj.get("libraryPreLotNo")+"</libraryPreLotNo>\n");
            	 sb.append("<extractionLotNo>"+jsonObj.get("extractionLotNo")+"</extractionLotNo>\n");
            	 sb.append("<sequencingLotNo>"+jsonObj.get("sequencingLotNo")+"</sequencingLotNo>\n");
            	 
            	 sb.append("<sample_list>\n");
                 JSONArray sampleArray = jsonObj.getJSONArray("sample_list");
                 if(sampleArray!=null&&sampleArray.size()>0){
                	 for (int j = 0; j < sampleArray.size(); j++) {
                		JSONObject samplejson =  (JSONObject) sampleArray.get(j);
                		 sb.append("<sample>\n");
                		 sb.append("<patientId>"+samplejson.get("patientId")+"</patientId>\n"); 
                		 sb.append("<barcode>"+samplejson.get("barcode")+"</barcode>\n"); 
                		 sb.append("</sample>\n");
					}
                	 
                 }
                
                 sb.append("</sample_list>\n");
                 sb.append("</info>\n");
                
			}
            sb.append("</info_list>\n");	
           
            return sb.toString();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
    }  
	
	public static void main(String[] args) {
//		String xml = "<scheduling>\n" + "<schema_id>序号</schema_id>\n" + "<schema_type>排班类型</schema_type>\n" + "<week>星期</week>\n" + "<remark>备注</remark>\n" + "</scheduling>\n";
//
//		XmlUtil util = new XmlUtil();
//		Map<String,Object> map = util.parseXMLMap(xml);

	}
}

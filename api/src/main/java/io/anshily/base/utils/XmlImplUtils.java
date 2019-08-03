package io.anshily.base.utils;

import io.anshily.base.core.JdbcTemplateUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/*
import java.io.FileWriter;   
import java.io.IOException;   
import java.io.Writer;   

import org.dom4j.Document;   
import org.dom4j.DocumentException;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
import org.dom4j.io.SAXReader;   
import org.dom4j.io.XMLWriter; 

 */

public class XmlImplUtils {
	private Document document;

	public void init() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	public void createXml(String fileName, String communitID) {
		try {
			Element root = this.document.createElement("root");
			this.document.appendChild(root);

			String communit_name = " SELECT  C.COMMUNITYID, C.COMMUNITY_CODE FROM  COMMUNITY C  WHERE C.STATUS=1";
			if (communitID != null && !"".equals(communitID)) {
				communit_name += " AND C.COMMUNITYID='" + communitID + "'";
			}

			List<?> communit_list = JdbcTemplateUtil.queryForList(communit_name);
			if (communit_list != null && communit_list.size() > 0) {
				for (int i = 0; i < communit_list.size(); i++) {

					Map map = (Map) communit_list.get(i);
					Element employee_i = this.document.createElement("stage");
					employee_i.setAttribute("value", map.get("COMMUNITY_CODE").toString());
					//employee_i.appendChild(this.document.createTextNode(map.get("COMMUNITY_CODE").toString()));

					String buil_name = "SELECT  DISTINCT B.BUILD_NAME,B.BUILDID,B.BUILD_CODE  FROM BUILD B WHERE B.STATUS=1 AND B.COMMUNITYID='"
							+ map.get("COMMUNITYID").toString()
							+ "' GROUP BY B.BUILD_NAME,B.BUILDID,B.BUILD_CODE  ORDER BY B.BUILD_CODE ";
					List<?> buil_list = JdbcTemplateUtil.queryForList(buil_name);
					if (buil_list != null && buil_list.size() > 0) {
						for (int j = 0; j < buil_list.size(); j++) {
							Map map_buil = (Map) buil_list.get(j);
							Element employee_j = this.document.createElement("floor");
							employee_j.setAttribute("value", map_buil.get("BUILD_NAME").toString());
							//employee_j.appendChild(this.document.createTextNode(map_buil.get("BUILD_NAME").toString()));

							String unit_name = " SELECT  DISTINCT C.UNIT_NAME,C.UNITID,C.SORT   FROM COVER_UNIT C WHERE  C.BUILDID='"
									+ map_buil.get("BUILDID").toString()
									+ "' GROUP BY C.UNIT_NAME,C.UNITID,C.SORT  ORDER BY C.SORT ";
							List<?> unit_list = JdbcTemplateUtil.queryForList(unit_name);
							if (unit_list != null && unit_list.size() > 0) {
								for (int k = 0; k < unit_list.size(); k++) {
									Map map_unit = (Map) unit_list.get(k);

									Element employee_k = this.document.createElement("unit");
									employee_k.setAttribute("value", map_unit.get("UNIT_NAME").toString());
									//employee_k.appendChild(this.document.createTextNode(map_unit.get("UNIT_NAME").toString()));

									String house_code = " SELECT DISTINCT HOUSE_CODE FROM COVER_HOUSE WHERE STATUS= 1  AND HOUSE_CODE IS NOT NULL  AND UNITID='"
											+ map_unit.get("UNITID").toString()
											+ "'  GROUP BY HOUSE_CODE ORDER BY  HOUSE_CODE ";
									List<?> house_list = JdbcTemplateUtil.queryForList(house_code);
									if (house_list != null && house_list.size() > 0) {
										for (int n = 0; n < house_list.size(); n++) {
											Map map_house = (Map) house_list.get(n);
											Element employee_n = this.document.createElement("house");
											employee_n.setAttribute("value", map_house.get("HOUSE_CODE").toString());
											//employee_n.appendChild(this.document.createTextNode(map_house.get("HOUSE_CODE").toString()));
											employee_k.appendChild(employee_n);
										}
									}
									employee_j.appendChild(employee_k);
								}
							}
							employee_i.appendChild(employee_j);
						}
					}
					root.appendChild(employee_i);
				}
			}
			/* Element name = this.document.createElement("name");
			name.appendChild(this.document.createTextNode("wangchenyang"));
			employee.appendChild(name);
			Element sex = this.document.createElement("sex");
			sex.appendChild(this.document.createTextNode("m"));
			employee.appendChild(sex);
			Element age = this.document.createElement("age");
			age.appendChild(this.document.createTextNode("26"));
			employee.appendChild(age);
			
			
			root.appendChild(employee);
			 */

			TransformerFactory tf = TransformerFactory.newInstance();

			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("生成XML文件成功!");
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		}
	}

	/*public void parserXml(String fileName) {   
		File inputXml=new File(fileName);   
		SAXReader saxReader = new SAXReader();   
		try {   
			Document document = saxReader.read(inputXml);   
			Element employees=document.getRootElement();   
			for(Iterator i = employees.elementIterator(); i.hasNext();){   
				Element employee = (Element) i.next();   
				for(Iterator j = employee.elementIterator(); j.hasNext();){   
					Element node=(Element) j.next();   
					System.out.println(node.getName()+":"+node.getText());   
				}   
			}   
		} catch (DocumentException e) {   
		 System.out.println(e.getMessage());   
		}   
		System.out.println("dom4j parserXml");   
	}*/
	/* public  staticByhang  void main(String args[]){
		XmlImplUtils dd=new XmlImplUtils();
	    String str="D:/grade.xml";
	    dd.init();
	    dd.createXml(str);    //创建xml
	    //dd.parserXml(str);    //读取xml
	}*/
}

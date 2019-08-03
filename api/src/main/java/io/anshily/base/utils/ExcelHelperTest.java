package io.anshily.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * poi包操作excel测试
 *
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 
 * @changesSum: 
 * 
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class ExcelHelperTest {
	BufferedReader reader = null;

	//	@Test
	public void excelHelperImportTest() {
		// excel列结构
		List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
		excelColumns.add(new ExcelColumn(0, "depart_id", "科别流水号"));
		excelColumns.add(new ExcelColumn(1, "depart_code", "科别编码"));
		excelColumns.add(new ExcelColumn(2, "depart_name", "科别名称"));
		excelColumns.add(new ExcelColumn(3, "depart_type", "科室分类"));

		// 需要特殊转换的单元
		Map<String, Map> excelColumnsConvertMap = new HashMap<String, Map>();
		Map<String, Integer> isReceive = new HashMap<String, Integer>();
		isReceive.put("是", 1);
		isReceive.put("否", 0);
		//excelColumnsConvertMap.put("isReceive", isReceive);

		File sourceFile = new File("D:\\cmm\\excel\\科室信息列表201410251114812.xls");

		ExcelHead head = new ExcelHead();
		head.setRowCount(1); // 头所占行数
		head.setColumns(excelColumns); // 列的定义
		//head.setColumnsConvertMap(excelColumnsConvertMap); // 列的转换

		//		List<AppDepartInfo> infoList =  ExcelHelper.getInstanse().importToObjectList(head, sourceFile, AppDepartInfo.class);
		//		
		//		for (AppDepartInfo info : infoList) {
		//			System.out.println(info.getDepartName()+"=="+info.getDepartCode());
		//		}
	}

	@SuppressWarnings("unchecked")
	public void excelHelperExportTest()
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		/*List<AppEmployeesInfo> infoList = new ArrayList<AppEmployeesInfo>();
		for (int i = 0; i < 20; i++) {
			AppEmployeesInfo info = new AppEmployeesInfo();
			info.setEmployees_code("001");
			info.setEmployees_name("王晓晓");
			
			infoList.add(info);
		}*/

		// excel结构
		List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
		excelColumns.add(new ExcelColumn(0, "employees_code", "编码", Cell.CELL_TYPE_STRING));
		excelColumns.add(new ExcelColumn(1, "Employees_name", "名称", Cell.CELL_TYPE_STRING));

		// 需要特殊转换的单元
		Map<String, Map> excelHeadConvertMap = new HashMap<String, Map>();
		Map isReceive = new HashMap();
		isReceive.put(1, "是");
		isReceive.put(0, "否");

		File modelFile = new File("_model.xlsx");
		File outputFile = new File("_export.xlsx");

		ExcelHead head = new ExcelHead();
		head.setRowCount(2); // 模板中头部所占行数
		head.setColumns(excelColumns); // 列的定义
		head.setColumnsConvertMap(excelHeadConvertMap); // 列的转换

		ExcelHelper.getInstanse().exportExcelFile(head, modelFile, outputFile, new ArrayList());
	}

	//	@Test
	public void excelHelperExcelFileConvertToList() throws Exception {
		FileInputStream fis = new FileInputStream("D:\\cmm\\excel\\科室信息列表201410251114812.xls");
		List<List> dataList = ExcelHelper.getInstanse().excelFileConvertToList(fis);
		/*	for (List list : dataList) {
				for (Object object : list) {
					System.out.print(object);
					System.out.print("\t\t\t");
				}
				System.out.println();
			}*/
		for (int i = 1; i < dataList.size(); i++) {
			//System.out.println(dataList.get(i).get(0)+"==");;
		}
	}

	/**
	  * 读取对账文件
	  */
	public String readLine(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		reader = new BufferedReader(new InputStreamReader(is));
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = reader.readLine()) != null) {
			sb.append(str);
			sb.append("@");
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		ExcelHelperTest test = new ExcelHelperTest();
		String line = test.readLine("D:\\cm\\ccc\\10002.txt");
		if (line.endsWith("@")) {
			line = line.substring(0, line.lastIndexOf("@"));
		}
		String billIds = "";
		String houseId = "";
		String wyMoney = "";
		String yeMoney = "";
		String tradeNumber = "";
		if (StringUtils.isNotEmpty(line)) {
			String[] lineArr = line.split("@");
			for (int i = 0; i < lineArr.length; i++) {
				String lineStr = lineArr[i];
				String[] dataArr = lineStr.split("\\|");
				for (int j = 0; j < dataArr.length; j++) {
					System.out.println("==" + dataArr[j]);
				}
				/*billIds = dataArr[0];//算费ID
				houseId = dataArr[1];//套户ID
				wyMoney = dataArr[2];//网银支付金额
				yeMoney = dataArr[3];//余额支付金额
				tradeNumber = dataArr[4];//订单号
				System.out.println("billIds=="+billIds+"  houseId=="+houseId+"  wyMoney=="+wyMoney+"  yeMoney=="+yeMoney+"  tradeNumber=="+tradeNumber);*/
			}
		}
	}
}

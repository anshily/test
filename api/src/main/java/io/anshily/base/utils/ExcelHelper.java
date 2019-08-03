package io.anshily.base.utils;

import jodd.bean.BeanUtil;
import jodd.datetime.JDateTime;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel助手类
 *
 * @version: 0.1
 * @lastVersion: 0.1
 * @updateTime: 
 * @changesSum: 
 * 
 */
@SuppressWarnings({"rawtypes", "unused"})
public class ExcelHelper {
	private static ExcelHelper helper = null;

	private ExcelHelper() {

	}

	public static synchronized ExcelHelper getInstanse() {
		if (helper == null) {
			helper = new ExcelHelper();
		}
		return helper;
	}

	/**
	 * 将Excel文件导入到list对象
	 * @param head  文件头信息
	 * @param file  导入的数据源
	 * @param cls   保存当前数据的对象
	 * @return
	 * @return List
	 */
	public List importToObjectList(ExcelHead head, InputStream is, Class cls) {
		List contents = null;
		FileInputStream fis;
		// 根据excel生成list类型的数据
		List<List> rows;
		try {
			// fis = new FileInputStream(file);
			rows = excelFileConvertToList(is);

			// 删除头信息
			for (int i = 0; i < head.getRowCount(); i++) {
				rows.remove(0);
			}

			// 将表结构转换成Map
			Map<Integer, String> excelHeadMap = convertExcelHeadToMap(head.getColumns());
			// 构建为对象
			contents = buildDataObject(excelHeadMap, head.getColumnsConvertMap(), rows, cls);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contents;
	}

	@SuppressWarnings("unchecked")
	public String vlHeadRows(ExcelHead head, InputStream is, List headList) {
		List contents = null;
		FileInputStream fis;
		// 根据excel生成list类型的数据
		List<List> rows;
		List<String> firstRow = new ArrayList<String>();
		String returnValue = null;
		try {
			// fis = new FileInputStream(file);
			rows = excelFileConvertToList(is);
			if (rows != null && rows.size() > 0) {
				firstRow = rows.get(0);
			}
			for (int i = 0; i < firstRow.size(); i++) {

				if (!headList.get(i).equals(firstRow.get(i))) {

					return firstRow.get(i);

				}

			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * 导出数据至Excel文件
	 * @param excelColumns  报表头信息
	 * @param excelHeadConvertMap   需要对数据进行特殊转换的列
	 * @param modelFile  模板Excel文件
	 * @param outputFile 导出文件
	 * @param dataList  导入excel报表的数据来源
	 * @return void
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void exportExcelFile(ExcelHead head, File modelFile, File outputFile, List<?> dataList)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 读取导出excel模板
		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(modelFile);
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			// 生成导出数据
			buildExcelData(sheet, head, dataList);

			// 导出到文件中
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (InvalidFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 导出数据至Excel文件
	 * @param excelColumns  报表头信息
	 * @param excelHeadConvertMap   需要对数据进行特殊转换的列
	 * @param modelFile  模板Excel文件
	 * @param outputFile 导出文件
	 * @param dataList  导入excel报表的数据来源
	 * @return void
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void exportExcelFile(ExcelHead head, File modelFile, OutputStream outputFile, List<?> dataList)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 读取导出excel模板
		InputStream inp = null;
		Workbook wb = null;
		try {
			inp = new FileInputStream(modelFile);
			wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			// 生成导出数据
			buildExcelData(sheet, head, dataList);
			wb.write(outputFile);
			outputFile.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (InvalidFormatException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 将报表结构转换成Map
	 * @param excelColumns
	 * @return void
	 */
	private Map<Integer, String> convertExcelHeadToMap(List<ExcelColumn> excelColumns) {
		Map<Integer, String> excelHeadMap = new HashMap<Integer, String>();
		for (ExcelColumn excelColumn : excelColumns) {
			if ("".equals(excelColumn.getFieldName())) {
				continue;
			} else {
				excelHeadMap.put(excelColumn.getIndex(), excelColumn.getFieldName());
			}
		}
		return excelHeadMap;
	}

	/**
	 * 生成导出至Excel文件的数据
	 * @param sheet 工作区间
	 * @param excelColumns  excel表头
	 * @param excelHeadMap  excel表头对应实体属性
	 * @param excelHeadConvertMap   需要对数据进行特殊转换的列
	 * @param dataList      导入excel报表的数据来源
	 * @return void
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void buildExcelData(Sheet sheet, ExcelHead head, List<?> dataList)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<ExcelColumn> excelColumns = head.getColumns();
		Map<String, Map> excelHeadConvertMap = head.getColumnsConvertMap();

		// 将表结构转换成Map
		Map<Integer, String> excelHeadMap = convertExcelHeadToMap(excelColumns);

		// 从第几行开始插入数据
		int startRow = head.getRowCount();
		int order = 1;
		for (Object obj : dataList) {
			Row row = sheet.createRow(startRow++);
			for (int j = 0; j < excelColumns.size(); j++) {
				Cell cell = row.createCell(j);
				cell.setCellType(excelColumns.get(j).getType());
				String fieldName = excelHeadMap.get(j);
				if (fieldName != null) {
					Object valueObject = BeanUtil.getProperty(obj, fieldName);

					// 如果存在需要转换的字段信息，则进行转换
					if (excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
						valueObject = excelHeadConvertMap.get(fieldName).get(valueObject);
					}

					if (valueObject == null) {
						cell.setCellValue("");
					} else if (valueObject instanceof Integer) {
						cell.setCellValue((Integer) valueObject);
					} else if (valueObject instanceof String) {
						cell.setCellValue((String) valueObject);
					} else if (valueObject instanceof Date) {
						cell.setCellValue(new JDateTime((Date) valueObject).toString("YYYY-MM-DD"));
					} else {
						cell.setCellValue(valueObject.toString());
					}
				} else {
					cell.setCellValue(order++);
				}
			}
		}
	}

	/**
	 * 将Excel文件内容转换为List对象
	 * @param fis   excel文件
	 * @return  List<List> list存放形式的内容
	 * @throws IOException
	 * @return List<List>
	 */
	public List<List> excelFileConvertToList(InputStream fis) throws Exception {
		Workbook wb = WorkbookFactory.create(fis);
		int lsheet = 1;//wb.getNumberOfSheets();
		Sheet sheet = null;
		List<List> rows = new ArrayList<List>();
		for (int r = 0; r < lsheet; r++) {
			sheet = wb.getSheetAt(r);
			//    	        Iterator<Row> row2 =  sheet.iterator();
			Row row = null;
			Cell cell = null;

			//    	        for (Row row : sheet) {
			for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
				int cellleaght = 0;
				List<Object> cells = new ArrayList<Object>();
				row = sheet.getRow(i);

				if (row == null) {
					continue;
				}
				int k = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() - 1;
				// for (Cell cell : row) {
				for (int j = 0; j < k; j++) {
					cell = row.getCell(j);

					Object obj = null;
					if (cell == null) {
						cells.add(obj);
						cellleaght++;
						continue;
					}
					switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							obj = cell.getRichStringCellValue().getString().trim();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								//obj = new java.util.Date(((java.sql.Timestamp) cell.getDateCellValue()).getTime());
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								double d = cell.getNumericCellValue();
								obj = sdf.format(HSSFDateUtil.getJavaDate(d)).toString();
								//obj = new JDateTime(cell.getDateCellValue());
							} else {
								obj = cell.getNumericCellValue();
								/*DecimalFormat df = new DecimalFormat("0");
								obj = df.format(obj);*/
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							obj = cell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA:
							obj = cell.getNumericCellValue();
							break;
						default:
							obj = null;
					}

					if (obj == null) {
						cellleaght++;
					}

					cells.add(obj);
				}
				if (cellleaght != k) {
					rows.add(cells);
				}
			}
		}
		return rows;
	}

	/**
	 * 根据Excel生成数据对象
	 * @param excelHeadMap 表头信息
	 * @param excelHeadConvertMap 需要特殊转换的单元
	 * @param rows
	 * @param cls 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	private List buildDataObject(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap,
			List<List> rows, Class cls) {
		List contents = new ArrayList();
		for (List list : rows) {
			// 如果当前第一列中无数据,则忽略当前行的数据
			/*if(list == null || list.get(0) == null) {
			    continue;
			}*/
			// 当前行的数据放入map中,生成<fieldName, value>的形式
			Map<String, Object> rowMap = rowListToMap(excelHeadMap, excelHeadConvertMap, list);

			// 将当前行转换成对应的对象
			Object obj = null;
			try {
				obj = cls.newInstance();
			} catch (InstantiationException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
			BeanUtil.populateBean(obj, rowMap);

			contents.add(obj);
		}
		return contents;
	}

	/**
	 * 将行转行成map,生成<fieldName, value>的形式
	 * @param excelHeadMap 表头信息
	 * @param excelHeadConvertMap excelHeadConvertMap
	 * @param list
	 * @return
	 * @return Map<String,Object>
	 */
	private Map<String, Object> rowListToMap(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap,
			List list) {
		Map<String, Object> rowMap = new HashMap<String, Object>();
		for (int i = 0; i < list.size(); i++) {
			String fieldName = excelHeadMap.get(i);
			// 存在所定义的列
			if (fieldName != null) {
				Object value = list.get(i);
				if (excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
					value = excelHeadConvertMap.get(fieldName).get(value);
				}
				rowMap.put(fieldName, value);
			}
		}
		return rowMap;
	}
}

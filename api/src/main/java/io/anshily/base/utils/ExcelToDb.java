package io.anshily.base.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExcelToDb {

	public static List<String> readExcelFile(String path) throws Exception {
		
		// 创建工作簿对象
		File excelFile = new File(path);
		Workbook workBook = Workbook.getWorkbook(excelFile);
		List<String> list = new ArrayList<String>();
		// 得到工作簿所有的工作表对象
		Sheet[] sheets = workBook.getSheets();
		// 遍历所有行
		for (int i = 1; i < sheets[0].getRows(); i++)
		{
			String strVal ="";
		// 得到所有列，在输出列中的内容
		Cell[] cells = sheets[0].getRow(i);
		for (int j = 0; j < cells.length; j++)
		{
		    strVal+= cells[j].getContents()+",";
		 }
		//strVal =strVal+",";
          list.add(strVal);
		}
		return list;
	}
	
	
}

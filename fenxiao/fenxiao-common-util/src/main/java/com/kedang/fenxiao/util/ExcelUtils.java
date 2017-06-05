package com.kedang.fenxiao.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * Excel工具类 lf1402271953
 */
/**
 * @author Administrator
 *
 */
public class ExcelUtils {
	/**
	 * 导出Excel lf1402280934
	 * 
	 * @param excelName
	 * @param headers
	 * @param mapList
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void exportExcel(String excelName,String title, String[] headers,
			List mapList, HttpServletResponse response) {
		if (headers != null && headers.length > 0) {
			int headerLength = headers.length;
			// 声明一个工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(excelName);
			// 设置表格默认列宽度为15个字符
			sheet.setDefaultColumnWidth(30);
			// 创建字体样式  
		    HSSFFont font = workbook.createFont();  
		    font.setFontName("Verdana");
		    font.setBoldweight((short) 100);  
		    font.setFontHeight((short) 300);  
			// 创建单元格样式  
		    HSSFCellStyle style = workbook.createCellStyle();
		    style.setAlignment(CellStyle.ALIGN_CENTER);
		    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		    style.setFont(font);//设置单元格字体样式
		    style.setWrapText(true);//自动换行
			// 在表格的第一和第二行用来存放表格的标题
			HSSFRow titleRow=sheet.createRow(0);
			// 合并A1单元格占据2行6列(开始行，结束行，开始列，结束列)
			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, headerLength-1));
			// 创建一个存放标题的单元格  
			HSSFCell titleCell=titleRow.createCell(0);
			titleCell.setCellValue(title);
			titleCell.setCellStyle(style);
			// 在excel表格的第三行存储表格每一列的列名
			HSSFRow row = sheet.createRow(2);
			for (int i = 0; i < headerLength; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}

			if (mapList != null && mapList.size() > 0) {
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setWrapText(true);//自动换行
				for (int i = 0; i < mapList.size(); i++) {
					Map<String, Object> map = (Map<String, Object>) mapList
							.get(i);
					row = sheet.createRow(i + 3);
					for (int j = 0; j < headerLength; j++) {
						Object object = map.get(Constant.EXCEL_ATTRIBUTE + j);
						String cellValue = (object != null ? String
								.valueOf(object) : "");
						HSSFCell cell=row.createCell(j);
						cell.setCellValue(cellValue);
						cell.setCellStyle(cellStyle);
					}
				}
			}

			try {
				response.setContentType("application/vnd.ms-excel; charset=UTF-8");
				response.setHeader(
						"Content-disposition",
						"attachment;filename="
								+ new String(excelName.getBytes("UTF-8"),
										"ISO8859-1") + ".xls");
				response.setCharacterEncoding("UTF-8");
				OutputStream ouputStream = response.getOutputStream();
				workbook.write(ouputStream);
				ouputStream.flush();
				ouputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导入excel,封装成固定格式的集合
	 * @param is
	 * @return mapList
	 */
	public static List<Map<String, Object>> importExcel(InputStream is) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		try {
			Workbook book = null;
			book = new HSSFWorkbook(is);
			Sheet sheet = book.getSheetAt(0);
			// 获取到Excel文件中的所有行数
			int rows = sheet.getLastRowNum();
			// 遍历行
			for (int i = 0; i <=rows; i++) {
				Map<String, Object> mapCell = new HashMap<String, Object>();
				// 读取左上端单元格
				Row row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 获取到Excel文件中的所有的列
					//int cells = row.getPhysicalNumberOfCells();
					int cells=row.getLastCellNum();
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值
						Cell cell = row.getCell(j);
						if (cell != null) {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								mapCell.put(Constant.EXCEL_ATTRIBUTE + j,
										cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								mapCell.put(Constant.EXCEL_ATTRIBUTE + j,
										cell.getStringCellValue());
								break;
							default:
								mapCell.put(Constant.EXCEL_ATTRIBUTE + j, "");
								break;
							}
						}else{
							mapCell.put(Constant.EXCEL_ATTRIBUTE + j, "");
						}
					}
					mapList.add(mapCell);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(is!=null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mapList;
	}
}

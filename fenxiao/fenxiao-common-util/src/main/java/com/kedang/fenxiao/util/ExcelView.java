package com.kedang.fenxiao.util;

import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelView extends AbstractExcelView{

	@Override
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
        String fileName=model.get("title")+".xls";
        fileName=new String(fileName.getBytes("gb2312"), "iso8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
	}
	
	public void buildExcelDocumentXSSF(Map<String, Object> model,
			XSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
        String fileName=model.get("title")+"";
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        fileName=new String(fileName.getBytes("gb2312"), "iso8859-1");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();
	}
}
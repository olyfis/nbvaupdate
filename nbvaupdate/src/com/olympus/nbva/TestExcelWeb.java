package com.olympus.nbva;

 

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.olympus.nbva.olyexcel.OlyExcel;
import com.olympus.olyutil.Olyutil;

@WebServlet("/testexcel")
public class TestExcelWeb extends HttpServlet {
	
	// Service method
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				HttpSession session = req.getSession();
				
				String headerFile = "C:\\Java_Dev\\props\\headers\\NBVA_AssetHrd.txt";
				XSSFWorkbook workbook = null;
				XSSFSheet sheet = null;
				Date date = Olyutil.getCurrentDate();
				String dateStamp = date.toString();
				ArrayList<String> assetHeaderArr = new ArrayList<String>();
				assetHeaderArr = Olyutil.readInputFile(headerFile);
				String FILE_NAME = "Test_Report_" + dateStamp + ".xlsx";

				workbook = OlyExcel.newWorkbook();

				sheet = OlyExcel.newWorkSheet(workbook, "Asset List Report");
				Map<String, CellStyle> styles = OlyExcel.createStyles(workbook);
				Row row = sheet.createRow(0);
				Cell cell = row.createCell(0);
				 
					cell.setCellValue((String) "Hello!");
				 
				// Write the output to a file
		       
		       // if(workbook instanceof XSSFWorkbook) FILE_NAME += "x";
		        //FileOutputStream out = new FileOutputStream(FILE_NAME);
		        
		    
                		
		       // res.setContentType("application/vnd.ms-excel"); // Set up mime type
		        
		        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // Set up mime type
				res.addHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
				OutputStream out = res.getOutputStream();
		        workbook.write(out);
		        out.close();
				
	
	
			}
}


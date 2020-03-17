package com.olympus.nbva.olyexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.olympus.nbva.assets.AssetData;
import com.olympus.nbva.contracts.ContractData;
import com.olympus.olyutil.Olyutil;


@WebServlet("/nbvatemplate")
public class ReadExcelTemplate extends HttpServlet {

	
	
	
	
	/****************************************************************************************************************************************************/

	public static void readExcelTemplate(String dateStamp, String templateFile, String excelTemplateNew, String tab, List<Pair<ContractData, List<AssetData> >> rtnPair ) throws IOException {
		//int listArrSZ = rtnPair.size();
		//ContractData contractData = new ContractData();

		System.out.println("*** in readExcelTemplate...");
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(templateFile));
		FileOutputStream fileOut = new FileOutputStream(excelTemplateNew);
		XSSFSheet sheet1 = wb.getSheet(tab);
		// Sheet mySheet = wb.getSheetAt(0);
		String contractID = "";
		
		String tab1 = "Asset List";
		XSSFRow row = sheet1.getRow(9);
		XSSFCell cell = row.getCell(5);
		cell.setCellValue(dateStamp); 
		XSSFSheet sheet2 = wb.createSheet(tab1);
		/*
		if (listArrSZ > 0) {
			// System.out.println("*** listArrSZ=" + listArrSZ);
			for (int i = 0; i < listArrSZ; i++) {
				contractData = rtnPair.get(i).getLeft();
				contractID = contractData.getContractID();
				

			}
		}
		*/
		
		
		
		/*
		  row = sheet1.getRow(16);
		  cell = row.getCell(5);
		cell.setCellValue(contractID);
*/
		wb.write(fileOut);
		// log.info("Written xls file");
		fileOut.close();
		
		
	}
	
	/****************************************************************************************************************************************************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String excelTemplate = "C:\\Java_Dev\\props\\nbvaupdate\\excelTemplates\\letter.xlsx";
		String excelTemplateNew = "C:\\Java_Dev\\props\\nbvaupdate\\excelTemplates\\letterNew.xlsx"; 
		String tab1 = "Buyout_Letter";
		Date date = Olyutil.getCurrentDate();
		String dateStamp = date.toString();
		//String excelTemplateNew = "NBVA_BuyOut_Letter_" + dateStamp + ".xlsx"; 
		List<Pair<ContractData, List<AssetData> >> list = (List<Pair<ContractData, List<AssetData> >>) session.getAttribute("rtnPair");

		 
		 
		readExcelTemplate(dateStamp, excelTemplate, excelTemplateNew, tab1, list);
		
	}
	

	
	/****************************************************************************************************************************************************/

	
}

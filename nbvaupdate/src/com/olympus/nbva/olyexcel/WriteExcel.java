package com.olympus.nbva.olyexcel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import com.olympus.nbva.assets.AssetData;
import com.olympus.nbva.contracts.ContractData;
import com.olympus.olyutil.Olyutil;


@WebServlet("/nbvaexcel2")
public class WriteExcel extends HttpServlet {

	/***********************************************************************************************************************************/
	//   Map<String, CellStyle> styles = createStyles(workbook); // return styles to Hash
	// Ex. --> titleCell.setCellStyle(styles.get("title")); // deref title in hash and set cell
	public static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap();
        
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());  
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
        styles.put("title", style); // assign to Map
        
        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style); // assign to Map

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style); // assign to Map

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style); // assign to Map

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style); // assign to Map

        return styles;
    }
	
	/***********************************************************************************************************************************/
	public static XSSFSheet newWorkSheet(XSSFWorkbook workbook, String label) {

		XSSFSheet sheet = workbook.createSheet(label);
		return sheet;
	}
	/***********************************************************************************************************************************/
	public static XSSFWorkbook newWorkbook() {

		XSSFWorkbook workbook = new XSSFWorkbook();
		return workbook;
	}
	/****************************************************************************************************************************************************/
	public static void contractHeader(XSSFWorkbook workbook, XSSFSheet sheet, ArrayList<String> headerArr) {

		int rowNum = 4;
		int colNum = 0;
		
		
		Map<String, CellStyle> styles = createStyles(workbook);
		
		
		Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("NBVA Asset List");
        
        
        titleCell.setCellStyle(styles.get("cell"));
        
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$K$1"));
       
        
        
		 Font font = workbook.createFont();
         font.setFontHeightInPoints((short) 12);
         font.setFontName("Times New Roman");
         font.setColor(IndexedColors.BLACK.getIndex());
         font.setBold(true);
         CellStyle style = workbook.createCellStyle();
         
         
         
         
         
         style.setFont(font);
         
         style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());  
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
         style.setBorderRight(BorderStyle.THIN);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderBottom(BorderStyle.THIN);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderLeft(BorderStyle.THIN);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style.setBorderTop(BorderStyle.THIN);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	 
			 
			 
			
			
         
		for (Object field : headerArr) {
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum);
			if (field instanceof String) {
				cell.setCellStyle(style);
				cell.setCellValue((String) field);
			}
		}	
		sheet.autoSizeColumn(0); 
	}
	/****************************************************************************************************************************************************/
	public static void assetHeader(XSSFWorkbook workbook, XSSFSheet sheet, ArrayList<String> headerArr) {
			
		Row row = sheet.createRow(14);
		int colNum = 0;
		 Font font = workbook.createFont();
         font.setFontHeightInPoints((short) 12);
         font.setFontName("Times New Roman");
         font.setColor(IndexedColors.BLACK.getIndex());
         font.setBold(true);
         CellStyle style = workbook.createCellStyle();
         style.setFont(font);
         
         
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	 
         
         style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());  
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		
		for (Object field : headerArr) {
			Cell cell = row.createCell(colNum++);
			if (field instanceof String) {
				cell.setCellStyle(style);
				cell.setCellValue((String) field);
			}
		}
	}
	
	/****************************************************************************************************************************************************/
	public static void loadWorkSheetContracts(XSSFWorkbook workbook, XSSFSheet sheet, List<Pair<ContractData, List<AssetData> >> rtnPair ) {
		int listArrSZ = rtnPair.size();
		 ContractData contractData = new ContractData();
		 
		 
		 		
		if (listArrSZ > 0) {	
			//System.out.println("*** listArrSZ=" + listArrSZ);
			for (int i = 0; i < listArrSZ; i++ ) {
				contractData = rtnPair.get(i).getLeft();
		 
				CellStyle style = workbook.createCellStyle();
				style.setBorderRight(BorderStyle.THIN);
				style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderBottom(BorderStyle.THIN);
				style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderLeft(BorderStyle.THIN);
				style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				style.setBorderTop(BorderStyle.THIN);
				style.setTopBorderColor(IndexedColors.BLACK.getIndex());	
				
		            
				Row row = sheet.getRow(4);
				Cell cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getContractID());
				
				row = sheet.getRow(5); // AgreementNum
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getCustomerID());		
				//cell.setCellValue((String) "TBD");
				
				
				row = sheet.getRow(6);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getCustomerName());		
		
				row = sheet.getRow(7);
				sheet.autoSizeColumn(1); 
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getCommenceDate());	
				
				row = sheet.getRow(8);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getTermDate());
				
				
				row = sheet.getRow(9);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((long) contractData.getTerm());
				style.setAlignment(HorizontalAlignment.LEFT);
				
				row = sheet.getRow(10);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((double) contractData.getEquipPayment());
				style.setAlignment(HorizontalAlignment.LEFT);
				
				row = sheet.getRow(11);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((double) contractData.getServicePayment());
				style.setAlignment(HorizontalAlignment.LEFT);
			}
		}
	}

	
	/****************************************************************************************************************************************************/
	public static void loadWorkSheetAssets(XSSFWorkbook workbook, XSSFSheet sheet, List<Pair<ContractData, List<AssetData> >> rtnPair ) {
		AssetData assetData = new AssetData();
		int listArrSZ = rtnPair.size();
		
		if (listArrSZ > 0) {	
			for (int i = 0; i < listArrSZ; i++ ) {
				int rtnArrSZ = rtnPair.get(i).getRight().size();
				List<AssetData> assetList = new ArrayList<AssetData>();
				assetList	= rtnPair.get(i).getRight();
				//System.out.println("<h5> listArrSZ =" + listArrSZ + " -- rtnArrSZ=" +  rtnArrSZ + "--</h5>");
				for (int n = 0; n < rtnArrSZ; n++ ) {
					AssetData asset = new AssetData();
					asset = assetList.get(n);
					//System.out.println("*** AssetReturn: EquipmentType=" + asset.getEquipType() + "--");
					//System.out.println("*** AssetReturn: N=" + n + " -- CustomerID=" + asset.getCustomerID() + "--");
					
					
					CellStyle style = workbook.createCellStyle();
					style.setBorderRight(BorderStyle.THIN);
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderBottom(BorderStyle.THIN);
					style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderLeft(BorderStyle.THIN);
					style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderTop(BorderStyle.THIN);
					style.setTopBorderColor(IndexedColors.BLACK.getIndex());	
					
					
					
					Row row = sheet.createRow(n + 15);
					Cell cell = row.createCell(0);
					cell.setCellValue((long) asset.getAssetId());
					
					cell.setCellStyle(style);
					cell = row.createCell(1);
					cell.setCellValue((String) asset.getEquipType());
					//sheet.autoSizeColumn(1); 
					cell.setCellStyle(style);
					/*
					cell = row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getCustomerID());
					sheet.autoSizeColumn(2); 
					
					*/
					
					cell = row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipDesc());					
					//sheet.autoSizeColumn(2); 
					cell = row.createCell(3);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getModel());
					//sheet.autoSizeColumn(3); 
					cell = row.createCell(4);
					cell.setCellStyle(style);
					cell.setCellValue( asset.getSerNum().replaceAll("null", ""));
					//sheet.autoSizeColumn(4); 
					cell = row.createCell(5);
					cell.setCellStyle(style);
					cell.setCellValue((int) asset.getQty());
					//sheet.autoSizeColumn(5); 
					cell = row.createCell(6);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipAddr1());
					//sheet.autoSizeColumn(6); 
					cell = row.createCell(7);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipCity());
					//sheet.autoSizeColumn(7); 
					
					cell = row.createCell(8);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipState());
					//sheet.autoSizeColumn(8); 
					
					cell = row.createCell(9);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipZip());
					//sheet.autoSizeColumn(9); 
					
					cell = row.createCell(10);
					cell.setCellStyle(style);
					cell.setCellValue((int) asset.getDispCode());
					//sheet.autoSizeColumn(10); 
					
					
				/*	
					cell = row.createCell(11);
					cell.setCellStyle(style);
					cell.setCellValue((double) asset.getResidAmt());
					sheet.autoSizeColumn(11); 
					
					cell = row.createCell(12);
					cell.setCellStyle(style);
					cell.setCellValue((double) asset.getEquipCost());
					sheet.autoSizeColumn(12); 
					
					cell = row.createCell(13);
					cell.setCellStyle(style);
					cell.setCellValue((double) asset.getaRentalAmt());
					sheet.autoSizeColumn(13); 
					cell = row.createCell(14);
					cell.setCellStyle(style);
					cell.setCellValue((int) asset.getDispCode());
					sheet.autoSizeColumn(14);
					
					
				*/
				} // end for n
				//System.out.println("*** End n loop");
			} //end for i
			//System.out.println("*** End i loop");
		} // end if	
		//System.out.println("*** End if");
	}
	/****************************************************************************************************************************************************/
	public static void doBuyoutInvoice(XSSFWorkbook workbook, String tab, List<Pair<ContractData, List<AssetData> >> rtnPair, String dateStamp ) throws IOException {

	
		int listArrSZ = rtnPair.size();
		 ContractData contractData = new ContractData();
		 XSSFSheet sheet1 = workbook.getSheet(tab);
		// Sheet mySheet = wb.getSheetAt(0);
		String contractID = "";
		String agreementNum = "";
		String custName = "";
		String custAddr1 = "";
		String custAddr2 = "";
		String custCity = "";
		String custState = "";
		String custZip = "";
		String boDate = "";
		String buyOutAmt = "";
		String effDate = "";
		double buy = 0.00;
		String dFmt = Olyutil.formatDate(dateStamp, "yyyy-MM-dd", "MMMM dd, yyyy");
		
		
		if (listArrSZ > 0) {	
			//System.out.println("*** listArrSZ=" + listArrSZ);
		 
			 
			
			for (int i = 0; i < listArrSZ; i++ ) {
				contractData = rtnPair.get(i).getLeft();
				agreementNum = contractData.getCustomerID();
				effDate = contractData.getEffectiveDate();
				custName = contractData.getCustomerName();
				custAddr1 = contractData.getCustomerAddr1();
				custAddr2 = contractData.getCustomerAddr2();
				custCity = contractData.getCustomerCity();
				custState = contractData.getCustomerState();
				custZip = contractData.getCustomerZip();
				buy = contractData.getBuyTotal();			
			}
			
			String dFmt2 = Olyutil.formatDate(effDate, "yyyy-MM-dd", "MMMM dd, yyyy");
			 
			XSSFRow row = sheet1.getRow(5);
			XSSFCell cell = row.getCell(4);
			cell.setCellValue(dFmt); 
			
			row = sheet1.getRow(4);
			cell = row.getCell(4);
			cell.setCellValue(contractData.getInvoice());
			
			
			row = sheet1.getRow(10);
			cell = row.getCell(1);
			cell.setCellValue(custName);
		
			
			row = sheet1.getRow(11);
			cell = row.getCell(1);
			cell.setCellValue(custAddr1);
			
			if (! Olyutil.isNullStr(custAddr2)) {
				row = sheet1.getRow(12);
				cell = row.getCell(1);
				cell.setCellValue(custAddr2);
				row = sheet1.getRow(13);
				cell = row.getCell(1);
				cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
				
			} else {
				row = sheet1.getRow(12);
				cell = row.getCell(1);
				cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
			}
			
			row = sheet1.getRow(15);
			cell = row.getCell(1);
			cell.setCellValue(dFmt2);
			
			row = sheet1.getRow(15);
			cell = row.getCell(4);
			cell.setCellValue(agreementNum);
			
			row = sheet1.getRow(21);
			cell = row.getCell(4);
			cell.setCellValue(Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00"));
			

			row = sheet1.getRow(40);
			cell = row.getCell(4);
			
			
			cell.setCellValue(Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00"));
			//sheet1.addMergedRegion(new CellRangeAddress(40, 41, 4, 4));
		}
		
	}
	
	
	/****************************************************************************************************************************************************/

	public static void doBuyoutLetter(XSSFWorkbook wb, String excelTemplateNew, String tab,  String templateFile,   String dateStamp, List<Pair<ContractData, List<AssetData> >> rtnPair ) throws IOException {
		int listArrSZ = rtnPair.size();
		 ContractData contractData = new ContractData();



		FileOutputStream fileOut = new FileOutputStream(excelTemplateNew);
		XSSFSheet sheet1 = wb.getSheet(tab);
		// Sheet mySheet = wb.getSheetAt(0);
		String contractID = "";
		String agreementNum = "";
		String custName = "";
		String custAddr1 = "";
		String custAddr2 = "";
		String custCity = "";
		String custState = "";
		String custZip = "";
		String boDate = "";
		String buyOutAmt = "";
		String dFmt = Olyutil.formatDate(dateStamp, "yyyy-MM-dd", "MMMM dd, yyyy");
		
		XSSFRow row = sheet1.getRow(9);
		XSSFCell cell = row.getCell(5);
		cell.setCellValue(dFmt); 
		 
		

		if (listArrSZ > 0) {

			//System.out.println("*** listArrSZ=" + listArrSZ);
			 for (int i = 0; i < listArrSZ; i++) {
				contractData = rtnPair.get(i).getLeft();
				contractID = contractData.getContractID();
				agreementNum = contractData.getCustomerID();
				custName = contractData.getCustomerName();
				custAddr1 = contractData.getCustomerAddr1();
				custAddr2 = contractData.getCustomerAddr2();
				custCity = contractData.getCustomerCity();
				custState = contractData.getCustomerState();
				custZip = contractData.getCustomerZip();
				 
				boDate = contractData.getBuyoutDate();
				buyOutAmt = Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00");
				//System.out.println("*** contractID=" + contractID + "-- AgreementNum=" + agreementNum);

			}  
		}
		 
		String dFmt2 = Olyutil.formatDate(boDate, "yyyy-MM-dd", "MMMM dd, yyyy");
		 String line1 = "this purchase.   Failure to fax the countersigned letter by "   +dFmt2 +  " and remit payment no later";
		 String line2 = " than " + dFmt2 + " shall be deemed a withdrawal of your intention to purchase the Equipment, and invoices";
		 
		row = sheet1.getRow(11);
		cell = row.getCell(5);
		cell.setCellValue(custName);
		
		row = sheet1.getRow(50);
		cell = row.getCell(8);
		cell.setCellValue(custName);
		
		row = sheet1.getRow(12);
		cell = row.getCell(5);
		cell.setCellValue(custAddr1);
		
		if (! Olyutil.isNullStr(custAddr2)) {
			row = sheet1.getRow(13);
			cell = row.getCell(5);
			cell.setCellValue(custAddr2);
			row = sheet1.getRow(14);
			cell = row.getCell(5);
			cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
			
		} else {
			row = sheet1.getRow(13);
			cell = row.getCell(5);
			cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
		}
		/*row = sheet1.getRow(14);
		cell = row.getCell(5);
		cell.setCellValue(custState+ " " + custZip);*/	

		
		
		row = sheet1.getRow(16);
		cell = row.getCell(5);
		cell.setCellValue(agreementNum);

		row = sheet1.getRow(18);
		cell = row.getCell(5);
		cell.setCellValue(contractID);

		row = sheet1.getRow(22);
		cell = row.getCell(5);
		
		cell.setCellValue(buyOutAmt);

		row = sheet1.getRow(40);
		cell = row.getCell(1);
		cell.setCellValue(line1);
		
		row = sheet1.getRow(41);
		cell = row.getCell(1);
		cell.setCellValue(line1);
		
		wb.write(fileOut);
		// log.info("Written xls file");
		//fileOut.close();
		
		
	}
	/****************************************************************************************************************************************************/
	public static void doInvoiceStatement(XSSFWorkbook workbook, String tab, List<Pair<ContractData, List<AssetData> >> rtnPair, String dateStamp, ArrayList<String> ageArr ) throws IOException {

		
		int listArrSZ = rtnPair.size();
		ContractData contractData = new ContractData();
		AssetData assets = new AssetData();
		XSSFSheet sheet1 = workbook.getSheet(tab);
		// Sheet mySheet = wb.getSheetAt(0);
		String contractID = "";
		String agreementNum = "";
		String custName = "";
		String custAddr1 = "";
		String custAddr2 = "";
		String custCity = "";
		String custState = "";
		String custZip = "";
		String boDate = "";
		String buyOutAmt = "";
		String effDate = "";
		double buy = 0.00;
		String dFmt = Olyutil.formatDate(dateStamp, "yyyy-MM-dd", "MMMM dd, yyyy");
		String[] lineArr = null;
		
		if (listArrSZ > 0) {	
			//System.out.println("*** listArrSZ=" + listArrSZ);
		 
			 
			
			for (int i = 0; i < listArrSZ; i++ ) {
				contractData = rtnPair.get(i).getLeft();
				contractID = contractData.getContractID();
				agreementNum = contractData.getCustomerID();
				effDate = contractData.getEffectiveDate();
				custName = contractData.getCustomerName();
				custAddr1 = contractData.getCustomerAddr1();
				custAddr2 = contractData.getCustomerAddr2();
				custCity = contractData.getCustomerCity();
				custState = contractData.getCustomerState();
				custZip = contractData.getCustomerZip();
				buy = contractData.getBuyTotal();			
			}
			
			String dFmt2 = Olyutil.formatDate(effDate, "yyyy-MM-dd", "MMMM dd, yyyy");
			 
			XSSFRow row = sheet1.getRow(3);
			XSSFCell cell = row.getCell(4);
			cell.setCellValue(dFmt); 
		
			 
			row = sheet1.getRow(18);
			cell = row.getCell(1);
			cell.setCellValue(contractData.getInvoice());
			 
			row = sheet1.getRow(18);
			cell = row.getCell(2);
			cell.setCellValue(effDate);
			row = sheet1.getRow(18);
			cell = row.getCell(3);
			cell.setCellValue("Buyout Payment");
			
			row = sheet1.getRow(18);
			cell = row.getCell(4);
			cell.setCellValue(Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00"));
			
			row = sheet1.getRow(8);
			cell = row.getCell(1);
			cell.setCellValue(custName);
		
			
			row = sheet1.getRow(9);
			cell = row.getCell(1);
			cell.setCellValue(custAddr1);
			
			if (! Olyutil.isNullStr(custAddr2)) {
				row = sheet1.getRow(10);
				cell = row.getCell(1);
				cell.setCellValue(custAddr2);
				row = sheet1.getRow(11);
				cell = row.getCell(1);
				cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
				
			} else {
				row = sheet1.getRow(10);
				cell = row.getCell(1);
				cell.setCellValue(custCity + ", " + custState+ " " + custZip);	
			}
			
	
			
			row = sheet1.getRow(11);
			cell = row.getCell(4);
			cell.setCellValue(agreementNum);
			
			/*row = sheet1.getRow(21);
			cell = row.getCell(4);
			cell.setCellValue(Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00"));
			*/

			row = sheet1.getRow(43);
			cell = row.getCell(4);
			
			
			cell.setCellValue(Olyutil.decimalfmt(contractData.getBuyOut(), "$###,##0.00"));
			//sheet1.addMergedRegion(new CellRangeAddress(40, 41, 4, 4));
			int k = 19;
			int zz = 0;
			double amt = 0.00;
			int ageArrSZ = ageArr.size();
			// ********************* Begin display assets
			for (int m = 0; m < ageArrSZ; m++ ) {
				zz = m;
				

					lineArr = Olyutil.splitStr(ageArr.get(m), ";");
					if (contractID.equals(lineArr[0])) {
						/* System.out.println("***^^^*** LineArr:" + 
								"M=" + m
								+ " -- ID="     + contractID 
								+ " -- invoice=" + lineArr[6]
								+ " -- Date="     + lineArr[5]
								+ " -- amt="     + lineArr[4]							 
								+ "--");  */
						amt = Olyutil.strToDouble(lineArr[4]);
						row = sheet1.getRow(k);
						cell = row.getCell(1);
						cell.setCellValue(lineArr[6]);
						
						cell = row.getCell(2);
						cell.setCellValue( lineArr[5]);
						
						cell = row.getCell(3);
						cell.setCellValue( "");
						
						cell = row.getCell(4);
						cell.setCellValue( Olyutil.decimalfmt(amt, "$###,##0.00"));				
						k++;		
					}
					
			}	
			//System.out.println("***^^^*** End Invoice code  M=" + zz + " -- SZ=" + ageArrSZ);			
		} // end if SZ	
	}
	
	
	/****************************************************************************************************************************************************/

	
	
	// Service method
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			HttpSession session = req.getSession();
			Date date = Olyutil.getCurrentDate();
			String dateStamp = date.toString();
			ArrayList<String> assetHeaderArr = new ArrayList<String>();
			ArrayList<String> ageArr = new ArrayList<String>();
			String contractHeaderFile = "C:\\Java_Dev\\props\\headers\\NBVA_ContractHrd.txt";
			String headerFile = "C:\\Java_Dev\\props\\headers\\NBVA_AssetHrdExcel.txt";
			String ageFile = "C:\\Java_Dev\\props\\nbvaupdate\\dailyAge.csv";
			String FILE_NAME = "NBVA_Asset_List_Report_" + dateStamp + ".xlsx";
			String excelTemplate = "C:\\Java_Dev\\props\\nbvaupdate\\excelTemplates\\letter.xlsx";		
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			
			
		assetHeaderArr = Olyutil.readInputFile(headerFile);

		String tab1 = "Buyout_Letter";
		workbook = new XSSFWorkbook(new FileInputStream(excelTemplate));
		FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
		// String excelTemplateNew = "NBVA_BuyOut_Letter_" + dateStamp + ".xlsx";

		ageArr = Olyutil.readInputFile(ageFile);
		// Olyutil.printStrArray(ageArr);

		ArrayList<String> contractHeaderArr = new ArrayList<String>();
		contractHeaderArr = Olyutil.readInputFile(contractHeaderFile);
		List<Pair<ContractData, List<AssetData>>> list = (List<Pair<ContractData, List<AssetData>>>) session
				.getAttribute("rtnPair");

		// strArr = (ArrayList<String>) session.getAttribute("strArr");
		doBuyoutLetter(workbook, FILE_NAME, tab1, excelTemplate, dateStamp, list);

		// XSSFSheet sheet2 = getWorkSheet(workbook, "Buyout_Invoice");
		doBuyoutInvoice(workbook, "Buyout_Invoice", list, dateStamp);
		doInvoiceStatement(workbook, "Buyout_Statement", list, dateStamp, ageArr);
		//System.out.println("** Call contractHeader");
		// workbook = newWorkbook();
		sheet = newWorkSheet(workbook, "Asset List Report");
		contractHeader(workbook, sheet, contractHeaderArr);

		assetHeader(workbook, sheet, assetHeaderArr);
		//System.out.println("** Call loadWorkSheetContracts");
		loadWorkSheetContracts(workbook, sheet, list);
		//System.out.println("** Call loadWorkSheetAssets");
		loadWorkSheetAssets(workbook, sheet, list);
		//System.out.println("** Call Write Excel");
		// System.out.println("** Call loadWorkSheet");
		// WriteExcel.loadWorkSheet(workbook, sheet, strArr, 1, ";");
		// BufferedInputStream in = null;

		try {
			// HttpServletResponse response = getResponse(); // get ServletResponse
			res.setContentType("application/vnd.ms-excel"); // Set up mime type
			res.addHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
			OutputStream out2 = res.getOutputStream();
			workbook.write(out2);
			out2.flush();

			// ********************************************************************************************************************************

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
	}

}

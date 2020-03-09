package com.olympus.nbva;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.olympus.nbva.assets.AssetData;
import com.olympus.nbva.contracts.ContractData;
import com.olympus.nbva.olyexcel.OlyExcel;
import com.olympus.olyutil.Olyutil;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;


@WebServlet("/nbvaexcel")
public class NBVAexcel extends HttpServlet {
	
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
		
		
		Map<String, CellStyle> styles = OlyExcel.createStyles(workbook);
		
		
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
				
				row = sheet.getRow(5);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getCustomerName());				
				row = sheet.getRow(6);
				sheet.autoSizeColumn(1); 
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getCommenceDate());				
				row = sheet.getRow(7);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((long) contractData.getTerm());
				row = sheet.getRow(8);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((String) contractData.getTermDate());
				row = sheet.getRow(9);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((double) contractData.getEquipPayment());
				
				row = sheet.getRow(10);
				cell = row.createCell(1);
				cell.setCellStyle(style);
				cell.setCellValue((double) contractData.getServicePayment());
	 
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
				for (int j = 0; j < rtnArrSZ; j++ ) {
					AssetData asset = new AssetData();
					asset = assetList.get(j);
					//System.out.println("*** AssetReturn: EquipmentType=" + asset.getEquipType() + "--");
					//System.out.println("*** AssetReturn: CustomerID=" + asset.getCustomerID() + "--");
					
					
					CellStyle style = workbook.createCellStyle();
					style.setBorderRight(BorderStyle.THIN);
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderBottom(BorderStyle.THIN);
					style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderLeft(BorderStyle.THIN);
					style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderTop(BorderStyle.THIN);
					style.setTopBorderColor(IndexedColors.BLACK.getIndex());	
					
					
					
					Row row = sheet.createRow(j + 15);
					Cell cell = row.createCell(0);
					cell.setCellValue((long) asset.getAssetId());
					
					cell.setCellStyle(style);
					cell = row.createCell(1);
					cell.setCellValue((String) asset.getEquipType());
					sheet.autoSizeColumn(1); 
					cell.setCellStyle(style);
					cell = row.createCell(2);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getCustomerID());
					sheet.autoSizeColumn(2); 
					cell = row.createCell(3);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipDesc());					
					sheet.autoSizeColumn(3); 
					cell = row.createCell(4);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getModel());
					sheet.autoSizeColumn(4); 
					cell = row.createCell(5);
					cell.setCellStyle(style);
					cell.setCellValue( asset.getSerNum());
					sheet.autoSizeColumn(5); 
					cell = row.createCell(6);
					cell.setCellStyle(style);
					cell.setCellValue((int) asset.getQty());
					sheet.autoSizeColumn(6); 
					cell = row.createCell(7);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipAddr1());
					sheet.autoSizeColumn(7); 
					cell = row.createCell(8);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipCity());
					sheet.autoSizeColumn(8); 
					
					cell = row.createCell(9);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipState());
					sheet.autoSizeColumn(9); 
					cell = row.createCell(10);
					cell.setCellStyle(style);
					cell.setCellValue((String) asset.getEquipZip());
					sheet.autoSizeColumn(10); 
					
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
					
					
					
				}
			}
		
		}
		
		
	}
	
	/****************************************************************************************************************************************************/
	// Service method
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			HttpSession session = req.getSession();
			String contractHeaderFile = "C:\\Java_Dev\\props\\headers\\NBVA_ContractHrd.txt";
			String headerFile = "C:\\Java_Dev\\props\\headers\\NBVA_AssetHrd.txt";
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			Date date = Olyutil.getCurrentDate();
			String dateStamp = date.toString();
			ArrayList<String> assetHeaderArr = new ArrayList<String>();
			assetHeaderArr = Olyutil.readInputFile(headerFile);
			String FILE_NAME = "NBVA_Asset_List_Report_" + dateStamp + ".xlsx";
			
			ArrayList<String> contractHeaderArr = new ArrayList<String>();
			contractHeaderArr = Olyutil.readInputFile(contractHeaderFile);
			 List<Pair<ContractData, List<AssetData> >> list = (List<Pair<ContractData, List<AssetData> >>) session.getAttribute("rtnPair");
			
			//strArr = (ArrayList<String>) session.getAttribute("strArr");
			
			workbook = newWorkbook();
			sheet = newWorkSheet(workbook, "Asset List Report");
			contractHeader(workbook, sheet, contractHeaderArr);
			
			assetHeader(workbook, sheet, assetHeaderArr);
			loadWorkSheetContracts(workbook, sheet, list);
			loadWorkSheetAssets(workbook, sheet, list);
			//System.out.println("** Call loadWorkSheet");
			//WriteExcel.loadWorkSheet(workbook, sheet, strArr, 1, ";");
			//BufferedInputStream in = null; 
			try {
				// HttpServletResponse response = getResponse(); // get ServletResponse
				res.setContentType("application/vnd.ms-excel"); // Set up mime type
				res.addHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
				OutputStream out2 = res.getOutputStream();
				workbook.write(out2);
				out2.flush();

			//********************************************************************************************************************************
			
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

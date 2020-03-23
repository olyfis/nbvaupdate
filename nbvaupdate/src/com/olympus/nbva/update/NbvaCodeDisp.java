package com.olympus.nbva.update;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.NodeList;

import com.olympus.nbva.DateUtil;
import com.olympus.nbva.assets.AssetData;
import com.olympus.nbva.contracts.ContractData;
import com.olympus.nbva.kits.GetKitData;
import com.olympus.olyutil.Olyutil;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.formula.functions.FinanceLib;

// Run: http://localhost:8181/nbva/nbvaupdate?id=101-0007328-056
//http://cvyhj3a27/:8181/nbva/nbvaupdate?id=101-0015003-034
// http://localhost:8181/nbvaupdate/nbvaupdate?id=101-0013771-035&eDate=2020-03-10   // Kit Data
@WebServlet("/nbvacode")
public class NbvaCodeDisp extends HttpServlet {

	
		static Statement stmt = null;
		static Connection con = null;
		static ResultSet res  = null;
		static NodeList  node  = null;
		static String s = null;
		static private PreparedStatement statement;
		static String propFile = "C:\\Java_Dev\\props\\unidata.prop";
		static String sqlFile = "C:\\Java_Dev\\props\\sql\\NBVAassetList.sql";
		static String kitFileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
		static boolean contractStat = false;
		static boolean invoiceCodeStat = false;
		static String purchOption = "";
		static int mthRem = 0;
		/****************************************************************************************************************************************************/
		
		/****************************************************************************************************************************************************/

		public static ArrayList<String> getDbData(String id, String sqlQueryFile, String booked, String qType) throws IOException {
			FileInputStream fis = null;
			FileReader fr = null;
			String s = new String();
			String sep = "";
	        StringBuffer sb = new StringBuffer();
	        ArrayList<String> strArr = new ArrayList<String>();
			try {
				fis = new FileInputStream(propFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties connectionProps = new Properties();
			connectionProps.load(fis);
			fr = new FileReader(new File(sqlQueryFile));	
			// be sure to not have line starting with "--" or "/*" or any other non alphabetical character
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null){
			      sb.append(s);       
			}
			br.close();
			//displayProps(connectionProps);
			String query = new String();
			query = sb.toString();	
			//System.out.println( query);	 
			try {
				con = Olyutil.getConnection(connectionProps);
				if (con != null) {
					//System.out.println("Connected to the database");
					statement = con.prepareStatement(query);
					//System.out.println("***^^^*** contractID=" + contractID);
					statement.setString(1, id);
					sep = ";";	 
					res = Olyutil.getResultSetPS(statement);		 	 
					strArr = Olyutil.resultSetArray(res, sep);			
				}		
			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				try {
					if (stmt != null) {
						stmt.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			return strArr;
		}
		/*****************************************************************************************************************************************************/

		/*****************************************************************************************************************************************************/

		
		/*****************************************************************************************************************************************************/
		public static AssetData loadAssetObj(String[] line) {
			AssetData asset = new AssetData();
			double equipCost = Olyutil.strToDouble(line[20]);
			double residual = Olyutil.strToDouble(line[19]);
			String desc = line[10];
			
			if (desc.equals("EUA") || desc.equals("B/O")) {
				//System.out.println("***^^^*** AssetData: Desc="  +  desc );
				if (residual == 0.00 || equipCost == 0.00) {
					//System.out.println("***^^^*** AssetData: residual="  +  residual  + " -- EC=" + equipCost);
					asset = null;
					return(asset);
				}	
			}
			
			 //System.out.println("*** AssetData:" + line.toString() );
			//System.out.println("***^^^*** AssetData: L22="  +  line[22] + "-- Fmt" + Olyutil.strToInteger(line[22] ) );
			//System.out.println("***^^^*** AssetData: L11="  +  line[11] + "--"   );
			//System.out.println("***^^^*** AssetData: L12="  +  line[12] + "--"   );
			
			 asset.setAssetId(Olyutil.strToLong(line[7]));
			 asset.setEquipType(line[8]); 
			 //asset.setCustomerID(line[9]); 
			 asset.setEquipDesc(desc); 
			 asset.setModel(line[11]); 
			 asset.setSerNum(line[12]); 
			 asset.setQty(Olyutil.strToInteger(line[13])); 
			 asset.setEquipAddr1(line[14]); 
			 asset.setEquipAddr2(line[15]); 
			 asset.setEquipCity(line[16]); 
			 asset.setEquipState(line[17]);
			 asset.setEquipZip(line[18]); 
			 
			 asset.setResidAmt(residual);
			 
			 asset.setEquipCost(equipCost);
			 
			 asset.setaRentalAmt(Olyutil.strToDouble(line[21]));
			 asset.setDispCode(Olyutil.strToInteger(line[22])); 
			 //asset.setTermDate( line[23]); 
			 return(asset);
		}
		/****************************************************************************************************************************************************/
		public static ContractData loadContractObj(String[] strSplitArr, String effectiveDate) {
		 
			ContractData contract = new ContractData();
			//double servicePay = 0.0;
			//double equipPay = 0.0; 
			contract.setContractID(strSplitArr[0]); 
			contract.setCustomerName(strSplitArr[1]); 
			contract.setCommenceDate(strSplitArr[2]);
			contract.setTerm(Olyutil.strToLong(strSplitArr[3])); 
			contract.setTermDate(strSplitArr[23]); 
			contract.setEquipPayment(Olyutil.strToDouble(strSplitArr[5])); 
			contract.setServicePayment(Olyutil.strToDouble(strSplitArr[6]));; 
			contract.setContractStatus(strSplitArr[24]); 
			contract.setInvoiceCode(strSplitArr[25]); 
			contract.setPurOption(strSplitArr[26]); 
			contract.setEffectiveDate(effectiveDate);
			 
			contract.setFinalInvDueDate(strSplitArr[4]);
			contract.setCustomerID(strSplitArr[9]);
			contract.setCustomerAddr1(strSplitArr[14]); 
			contract.setCustomerAddr2(strSplitArr[15]); 
			contract.setCustomerCity(strSplitArr[16]); 
			contract.setCustomerState(strSplitArr[17]);
			contract.setCustomerZip(strSplitArr[18]); 
			 
			
			//System.out.println("*** ContractData:" + strSplitArr.toString() );
			return(contract);
		}
		/****************************************************************************************************************************************************/
		public static  List<Pair<ContractData, List<AssetData> >> parseData(ArrayList<String> strArr, int sz, String effDate ) {
			String[] strSplitArr = null;
			ContractData contract = null;
			AssetData asset = null;
			List<AssetData> assets = new ArrayList<AssetData>();
			List<Pair<ContractData, List<AssetData> >> listRtn = new ArrayList<>();
			
			int i = 0;
			 //System.out.println("*** SZ=" + sz );
			for (i = 0; i < sz; i++) {
				//System.out.println("*** Data:" + strArr.get(i) );
				strSplitArr = Olyutil.splitStr(strArr.get(i), ";");
				purchOption = strSplitArr[26];	
				 //System.out.println("i=" + i + " -- Value=" + strSplitArr[i]);  
				if (i == 0) { // get Contract data
					contract = loadContractObj(strSplitArr, effDate);
					
						asset = loadAssetObj(strSplitArr);
					 
					if (strSplitArr[24].equals("03")) { 
						contractStat = true;
						//System.out.println("*** SC" + strSplitArr[24] + "--");
					}
					if (strSplitArr[25].equals("N")) {
						//System.out.println("*** IC=" + strSplitArr[25] + "--");
						invoiceCodeStat = true;
					}			
				} else { // get Asset data && run checks	
					asset = loadAssetObj(strSplitArr);
				}
				// Calculate floorPrice
				if (asset != null) {
					
					assets.add(asset);	
				}
			}
			//org.apache.commons.lang3.tuple.MutablePair<ContractData, List<AssetData>> p = org.apache.commons.lang3.tuple.MutablePair.of(contract, assets);
			//
			//listRtn.add(p);	
			//listRtn.add(Pair.of(contract, assets));   
			listRtn.add(Pair.of(contract, assets));   
			//System.out.println("*** ContractReturn: ID=" + contract.getContractID() + "--");
			//System.out.println("*** ContractReturn: EquipCost=" + contract.getEquipPayment() + "--");
			//System.out.println("*** AssetReturn: SerNum=" + asset.getSerNum() + "--");
			return(listRtn); 
		}
		/****************************************************************************************************************************************************/
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HashMap<String, ArrayList<Integer>> sqlErrMap = new HashMap<String, ArrayList<Integer>>();
			ArrayList<Integer> errIDArrayRtn = new ArrayList<>();
			ArrayList<String> ageArr = new ArrayList<String>();
			double sumTotal = 0.0;
			ContractData contractData = new ContractData();
			AssetData assetData = new AssetData();
			List<Pair<ContractData, List<AssetData>>> rtnPair = new ArrayList<>();
			int rtnArrSZ = 0;
			ArrayList<String> strArr = new ArrayList<String>();
			ArrayList<String> kitArr = new ArrayList<String>();
			String idVal = "";
			String dispatchJSP = "/nbvacodedetail_update.jsp";
			String dispatchJSP_Error = "/nbvaerror.jsp";
			//String ageFile = "Y:\\GROUPS\\Global\\BI Reporting\\Finance\\FIS_Bobj\\unappsuspense\\dailyAge.csv";
			String ageFile = "C:\\Java_Dev\\props\\nbvaupdate\\dailyAge.csv";
			String tag = "csvData: ";
			DecimalFormat format = new DecimalFormat("0.00");
			String paramName = "id";
			String paramValue = request.getParameter(paramName);

			String roParamName = "rotype";
			String roParamValue = request.getParameter(roParamName);

			String eDateParamName = "eDate";
			String eDateParamValue = request.getParameter(eDateParamName);
			String effDate = eDateParamValue;
		/*	
			String boDateParamName = "boDate";
			String boDateParamValue = request.getParameter(boDateParamName);
			String boDate = boDateParamValue;
			
			String invoiceParamName = "invoice";
			String invoiceParamValue = request.getParameter(invoiceParamName);
			String invoice = invoiceParamValue;
	*/
			// System.out.println("*** eDate=" + eDateParamValue + "-- RO=" + roParamValue +
			// "--");
			String formUrl = "formUrl";
			String formUrlValue = "/nbvaupdate/nbvacodeexcel";
			request.getSession().setAttribute(formUrl, formUrlValue);
			String sep = ";";
			//String termPlusSpan = "";
			int mthSpan = 9;
			//int rtn = 0;
			//int dayChkRtn = 0;
			int arrSZ = 0;
			//int mthRem = 0;
			
			ageArr = Olyutil.readInputFile(ageFile);
			// Olyutil.printStrArray(ageArr);

			if ((paramValue != null && !paramValue.isEmpty())) {
				idVal = paramValue.trim();
				// System.out.println("*** idVal:" + idVal + "--");
			}
			strArr = getDbData(idVal, sqlFile, "", "Asset");
			arrSZ = strArr.size();
			  //System.out.println("*** arrSz:" + arrSZ + "--");
			if (arrSZ > 0) {
				 //Olyutil.printStrArray(strArr);
				kitArr = GetKitData.getKitData(kitFileName);
				// Olyutil.printStrArray(kitArr);
				rtnPair = parseData(strArr, arrSZ, effDate );
				contractData = rtnPair.get(0).getLeft();
				rtnArrSZ = rtnPair.get(0).getRight().size(); 
				// System.out.println("*** RTN Arr SZ=" + rtnArrSZ + "--");
				// System.out.println("*** ContractReturn: ID=" + contractData.getContractID() +
				// "--");
				// System.out.println("*** ContractReturn: EquipCost=" +
				// contractData.getEquipPayment() + "--");
				// request.getSession().setAttribute("contract", contractData);
				request.getSession().setAttribute("rtnPair", rtnPair);
				 //System.out.println("*** Get Contract Totals");
				//sumTotal = getContractTotals(idVal, ageArr, ";");
				//errIDArrayRtn = doCheckDates(rtnPair, effDate, mthSpan);
				//System.out.println("----- dateErrors=" + errIDArrayRtn.size());
				String termDate = rtnPair.get(0).getLeft().getTermDate();
				String commDate = rtnPair.get(0).getLeft().getCommenceDate();
				//System.out.println("*** SumTotal=" + sumTotal );
				String termPlusSpan = DateUtil.addMonthsToDate(termDate, mthSpan);	
				rtnPair.get(0).getLeft().setTermPlusSpan(termPlusSpan);
				request.getSession().setAttribute("commDate", commDate);
				request.getSession().setAttribute("termDate", termDate);
				 
				request.getSession().setAttribute("effDate", effDate);
				request.getSession().setAttribute("mthRem", mthRem);
				request.getSession().setAttribute("idVal", idVal);
				request.getSession().setAttribute("sumTotal", sumTotal);
				request.getSession().setAttribute("sqlErrMap", sqlErrMap);
				request.getSession().setAttribute("termPlusSpan", termPlusSpan);
				
				String opt = "";
				if (errIDArrayRtn.size() > 0) {
					sqlErrMap.put(idVal, errIDArrayRtn);
					dispatchJSP = "/nbvaerror.jsp";	
				} else {			
					 //opt = contractCalcs( effDate, termDate, termPlusSpan, rtnPair);
				}	
				request.getSession().setAttribute("opt", opt);
				request.getRequestDispatcher(dispatchJSP).forward(request, response);	
			} else {
				request.getRequestDispatcher(dispatchJSP_Error).forward(request, response);
			}
		} // End doGet()
	} // End Class

 

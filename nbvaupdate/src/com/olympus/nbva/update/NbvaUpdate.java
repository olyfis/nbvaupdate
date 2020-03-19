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
@WebServlet("/nbvaupdate")
public class NbvaUpdate extends HttpServlet {
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
	/****************************************************************************************************************************************************/
	public static void displayObj(Object obj) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {	
		for (Method m : obj.getClass().getMethods())
		    if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
		      final Object r = m.invoke(obj);
		      // do your thing with r
		      System.out.println("Method:" + m.getName() + "Value:" + r.toString() );
		    }
	}	
	/****************************************************************************************************************************************************/
	/****************************************************************************************************************************************************/
	public static AssetData loadAssetObj(String[] line) {
		AssetData asset = new AssetData();
		double equipCost = Olyutil.strToDouble(line[20]);
		double residual = Olyutil.strToDouble(line[19]);
		String desc = line[10];
		
		if (desc.equals("EUA") || desc.equals("B/O")) {
			System.out.println("***^^^*** AssetData: Desc="  +  desc );
			if (residual == 0.00 || equipCost == 0.00) {
				System.out.println("***^^^*** AssetData: residual="  +  residual  + " -- EC=" + equipCost);
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
	public static ContractData loadContractObj(String[] strSplitArr, String effectiveDate, String buyoutDate, String invoice) {
	 
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
		contract.setBuyoutDate(buyoutDate);
		contract.setFinalInvDueDate(strSplitArr[4]);
		contract.setCustomerID(strSplitArr[9]);
		contract.setCustomerAddr1(strSplitArr[14]); 
		contract.setCustomerAddr2(strSplitArr[15]); 
		contract.setCustomerCity(strSplitArr[16]); 
		contract.setCustomerState(strSplitArr[17]);
		contract.setCustomerZip(strSplitArr[18]); 
		contract.setInvoice(invoice);
		
		//System.out.println("*** ContractData:" + strSplitArr.toString() );
		return(contract);
	}
	/****************************************************************************************************************************************************/
	public static  List<Pair<ContractData, List<AssetData> >> parseData(ArrayList<String> strArr, int sz, String effDate, String boDate, String invoice ) {
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
				contract = loadContractObj(strSplitArr, effDate, boDate, invoice);
				
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
	public static double getContractTotals(String id, ArrayList<String> strArr, String sep) {
		double sum = 0.0;
		//Olyutil.printStrArray(strArr, "A: ");
		for (String s : strArr) {	
			String[] items = s.split(sep);	
			//System.out.println("*** SZ=" + items.length + "-- " + s );
			String contractID = items[0];
			double val = 0.0;
			val = Olyutil.strToDouble(items[4]);	
			 //System.out.println("*** ID=" + id + "-- ContractID=" + contractID +"--");
			if (id.equals(contractID)) {
				// System.out.println("*** Match:" + id + " -- Value:" + val );
				sum += val;
			}	
		}	
		return(sum);
	}
	/**
	 * @throws ParseException **************************************************************************************************************************************************/
	
	public static void doAssetCheck(String termDate, String effDate, String termSpanDate) throws ParseException {
		int rtn = 0;
		 rtn = DateUtil.compareDates(effDate, termDate); // d1 < d2 returns -1 ; d1 > d2 returns 1; d1 == d2 returns 0
		 //System.out.println("*** RTN=" + rtn);	
	}
	/****************************************************************************************************************************************************/
	/****************************************************************************************************************************************************/
	/****************************************************************************************************************************************************/
	/****************************************************************************************************************************************************/
	public static ArrayList<Integer> doCheckDates(List<Pair<ContractData, List<AssetData> >> rtnPair, String effDate, int mthSpan ) {
		ArrayList<Integer> errIDArray = new ArrayList<>();
		int rtn = 0;
		int dayChkRtn = 0;
		//int mthRem = 0;	
		int rtnDate = -15;
		String termDate = rtnPair.get(0).getLeft().getTermDate();
		String commDate = rtnPair.get(0).getLeft().getCommenceDate();
		String  termPlusSpan = DateUtil.addMonthsToDate(termDate, mthSpan);
		//System.out.println("^^^^ termPlusSpan=" + termPlusSpan);
		//System.out.println("***^^^^^*** mthSpan=" + mthSpan + "-- TermDate=" + termDate + "-- eDate=" + effDate + "-- CommDate=" + commDate + "-- spanDatePlus9=" + termPlusSpan);
		// Check dates
		
		if (effDate.equals("Click for Calendar") || Olyutil.isNullStr(effDate)   ) {
			errIDArray.add(rtnDate);
			return(errIDArray);
		}
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			//Date d1 = f.parse(effDate);
			//Date d2 = f.parse(termDate);		
			mthRem = DateUtil.differenceInMonths(effDate, termDate);
			 //System.out.println("***^^ dateDiff=" + mthRem + "--");
			// p1 = effDate -- p2 = commDate -- effDate cannot be less than termDate
			rtn = DateUtil.compareDates(effDate, commDate);
			// System.out.println("***^^^^^*** tDate=" + termDate + "-- eDate=" +
			// eDateParamValue + "-- commDate=" + commDate);
			//System.out.println("*** RTN=" + rtn);
			if (rtn < 0) {
				  System.out.println("**^^** Error occured with date compares. -- Error:" + rtn);
				// System.out.println("***^^^^^*** tDate=" + termDate + "-- eDate=" +
				// eDateParamValue + "-- CommDate="
				// + commDate + "-- spanDatePlus9=" + termPlusSpan);
				errIDArray.add(rtn);
			}
			dayChkRtn = DateUtil.compareDateDays(effDate, commDate);
			if (dayChkRtn < 0) {
				errIDArray.add(dayChkRtn);
				  System.out.println("!!!**^^**!!! Error occurred with day match error yyyy-MM-dd. -- Error:" +  dayChkRtn);
			}
			if (invoiceCodeStat == true) {
				errIDArray.add(-5);
				// System.out.println("----- IC error");
			}
			if (invoiceCodeStat == true) {
				errIDArray.add(-10);
				// System.out.println("----- IC error");
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return(errIDArray);
	}
	/****************************************************************************************************************************************************/
	/****************************************************************************************************************************************************/

	// type - type (true=pmt at beginning of period, false=pmt at end of period)
	
		/*
		 *  pv(double r, double n, double y, double f, boolean t)
		 	r - rate
			term - num of periods(term)
			y - pmt per period
			f - future value
			t - type
			***************************************************************************************
			Rate = Yield
			Term = Months Remaining
			Payperiod = 0
			FV = Residual
			Type = False

		 */	
	public static  double getPV(double rate, double term, double numPymts, double residual, boolean type) {
		Double dRtn = 0.0;
		Double dVal = FinanceLib.pv(rate, term, numPymts, residual, type);
		 dRtn = Olyutil.roundDouble(dVal, "UP", "0.00");
		//dRtn = roundDouble(dVal, "DOWN", "0.00");	
		return (dRtn);	 
		}	 
	/****************************************************************************************************************************************************/
	public static String contractCalcs(String effDate, String termDate, String termPlusSpan, List<Pair<ContractData, List<AssetData> >> dataObj) {	
		// String effDate = "2020-08-01";
		// String termDate = "2020-01-01";
		// String termPlusSpan = "2020-09-01";
		String opt = "";
		int rtn = DateUtil.compareDates(effDate, termDate);
		int rtnSpan = DateUtil.compareDates(effDate, termPlusSpan);
		int rtn_eff_gt = DateUtil.compareDates(effDate, termDate); // rtn 1
		int rtn_eff_lt_t9 = DateUtil.compareDates(effDate, termPlusSpan); // rtn -1
		//System.out.println("***^^^^^*** tDate=" + termDate + "-- eDate=" + effDate + "-- spanDatePlus9=" + termPlusSpan);
		//System.out.println("***^^^^^*** rtn=" + rtn + "-- rtnSpan=" + rtnSpan + "--");
		if (rtn == -1) { // effDate < termDate)
			opt = "opt1";
			doCalcData(dataObj, "opt_1");
			//System.out.println("*** Opt 1 -- R=" + rtn + " Effective Date < Term Date");
		}
		//System.out.println("***^ rtn_eff_gt=" + rtn_eff_gt + "-- rtn_eff_lt_t9=" + rtn_eff_lt_t9 + "--");	
		// if (effdate > termDate and effDate < (Term Date + 9 Months) termPlusSpan = "2020-09-01"; effDate = "2020-08-01" termDate = "2020-01-01";
		if (rtn_eff_gt == 1 && rtn_eff_lt_t9 == -1) { 
			opt = "opt2";
			doCalcData(dataObj, "opt_2");
			//System.out.println("***^^^ Opt 2 ^^*** rtn_eff_gt=" + rtn_eff_gt + "-- rtn_eff_lt_t9=" + rtn_eff_lt_t9 + "--");
			//System.out.println("*** (effdate > termDate and effDate < (Term Date + 9 Months)");
		}
		// effDate > (Term Date + 9 Months)); effDate = "2021-08-01"; termDate = "2020-01-01"; termPlusSpan = "2020-09-01";
		if (rtnSpan == 1) { 
			opt = "opt3";
			doCalcData(dataObj, "opt_3");
			//System.out.println("^^^^ Opt 3 -- R=" + rtn + " effDate  > (Term Date + 9 Months)");
		}
		return(opt);
	}
	/****************************************************************************************************************************************************/
	// Option 1 -> Effective Date < Term Date
	// Option 2 -> Effective Date Is Between Term Date and Term Date + 9 Months
	// Option 3 -> Effective Date > (Term Date + 9 Months) 
	public static void  doCalcData(List<Pair<ContractData, List<AssetData> >> dataObj, String option) {
		List<AssetData> assets = new ArrayList<AssetData>();
		String purchOpt = "";
		long assetID = 0;
		double price = 0.00;
		double rentalAmt = 0.00; // payment per month
		double rate = 0.0725;
		double residual = 0.00;
		double pv = 0.00;
		double equipCost = 0.00;
		double buyOutTotal = 0.00;
		double rollTotal = 0.00;
		double rtnTotal = 0.00;
		
		int dispCode = 0;
		int k = 0;
		int rArrSZ = dataObj.get(0).getRight().size();
		//System.out.println("*** rArrSZ=" + rArrSZ + "--");
		 
		ContractData contract =  dataObj.get(0).getLeft();
		purchOpt = contract.getPurOption();
		assets = dataObj.get(0).getRight();
		for (k = 0; k < rArrSZ; k++) {	
			price = 0.00;
			rentalAmt = assets.get(k).getaRentalAmt();
			assetID = assets.get(k).getAssetId();
			residual = assets.get(k).getResidAmt();
			dispCode = assets.get(k).getDispCode();
			equipCost = assets.get(k).getEquipCost();
			pv = getPV(rate, mthRem, rentalAmt, residual, false) ;
			double rollPrice = 0.00;
			double buyPrice = 0.00;
			double rtnPrice = 0.00;
			
			
				if (purchOpt.equals("01"))  { //  ($1.00 Buyout)
					rollPrice = (mthRem * rentalAmt); // rollOver
					buyPrice = (mthRem * rentalAmt);
					rtnPrice = (mthRem * rentalAmt);	
				} else {
					if (option.equals("opt_1")) { // within contractual term
					//System.out.println("*** OPT="  +  option + " -- ID="  +  assetID + " -- PO=" + purchOpt + "-- RA=" + rentalAmt + "-- dispCode=" + dispCode +   "-- PV=" + pv + "--");	
						if (residual > 0) { // Option 1	
							// rollover
							rollPrice = (mthRem * rentalAmt) + pv;
							// buyout
							buyPrice = (mthRem * rentalAmt) + (residual * 1.20);
							// return
							rtnPrice = (mthRem * rentalAmt);
					    } else if (residual == 0)  {					        
					    	rollPrice = (mthRem * rentalAmt) + 1.01;
						    buyPrice = (mthRem * rentalAmt) + 1.01;
						    rtnPrice = (mthRem * rentalAmt) + 1.01;			 
					    }
					} else if (option.equals("opt_2")) {	// less than 9 months in evergreen			
							if (residual > 0) { // Option 3
										rollPrice = (residual * 1.15);
										buyPrice = (residual * 1.20);
							  			rtnPrice = 0.00; 
							} else if (residual == 0)  {	
								rollPrice = equipCost * 0.10;
								buyPrice = equipCost * 0.10;	
								rtnPrice = 0.00;		
							}
					} else if (option.equals("opt_3")) { // in evergreen >= 9 months
						if (residual > 0) { // Option 3
							 
								rollPrice =  residual * ( 1.15 - (0.05 * mthRem));	
								buyPrice =  residual * ( 1.20 - (0.05 * mthRem));
								rtnPrice = 0.00;
							 	
					    } else if (residual == 0)  { 
					    	   rollPrice =  1.01;
					    	   buyPrice =  1.01;
							   rtnPrice = 0.00;	 		       
						}	 
					} // End opt_
			} // end else
			//dataObj.get(0).getRight().get(k).setFloorPrice(price);
			dataObj.get(0).getRight().get(k).setBuyPrice(buyPrice);;
			buyOutTotal += buyPrice;
			rollTotal += rollPrice;
			rtnTotal += rtnPrice;
			dataObj.get(0).getRight().get(k).setRollPrice(rollPrice);;
			dataObj.get(0).getRight().get(k).setRtnPrice(rtnPrice);;
			//assets.add(k, element);
			//System.out.println("*** OPT="  +  option + " -- floorPrice=" +  price + "-- ID="  +  assetID + " -- PV=" + pv  + " -- PO=" + purchOpt + "-- RA=" + rentalAmt + "-- dispCode=" + dispCode   + "--");

		} // End for
		dataObj.get(0).getLeft().setBuyOut(buyOutTotal);
		dataObj.get(0).getLeft().setRollTotal(rollTotal);
		dataObj.get(0).getLeft().setRtnTotal(rtnTotal);
		
	}
	/****************************************************************************************************************************************************/
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
		String dispatchJSP = "/nbvadetail_update.jsp";
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
		
		String boDateParamName = "boDate";
		String boDateParamValue = request.getParameter(boDateParamName);
		String boDate = boDateParamValue;
		
		String invoiceParamName = "invoice";
		String invoiceParamValue = request.getParameter(invoiceParamName);
		String invoice = invoiceParamValue;

		// System.out.println("*** eDate=" + eDateParamValue + "-- RO=" + roParamValue +
		// "--");
		String formUrl = "formUrl";
		String formUrlValue = "/nbvaupdate/nbvaexcel2";
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
			rtnPair = parseData(strArr, arrSZ, effDate, boDate, invoice );
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
			sumTotal = getContractTotals(idVal, ageArr, ";");
			errIDArrayRtn = doCheckDates(rtnPair, effDate, mthSpan);
			//System.out.println("----- dateErrors=" + errIDArrayRtn.size());
			String termDate = rtnPair.get(0).getLeft().getTermDate();
			String commDate = rtnPair.get(0).getLeft().getCommenceDate();
			//System.out.println("*** SumTotal=" + sumTotal );
			String termPlusSpan = DateUtil.addMonthsToDate(termDate, mthSpan);	
			rtnPair.get(0).getLeft().setTermPlusSpan(termPlusSpan);
			request.getSession().setAttribute("commDate", commDate);
			request.getSession().setAttribute("termDate", termDate);
			request.getSession().setAttribute("boDate", boDate);
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
				 opt = contractCalcs( effDate, termDate, termPlusSpan, rtnPair);
			}	
			request.getSession().setAttribute("opt", opt);
			request.getRequestDispatcher(dispatchJSP).forward(request, response);	
		} else {
			request.getRequestDispatcher(dispatchJSP_Error).forward(request, response);
		}
	} // End doGet()
	/****************************************************************************************************************************************************/
// http://localhost:8181/nbva/nbvaupdate?id=101-0009442-019&rotype=0&eDate=2020-02-28
	// No Errs  http://localhost:8181/nbva/nbvaupdate?id=101-0009442-019&rotype=0&eDate=2020-03-17


} // End Class

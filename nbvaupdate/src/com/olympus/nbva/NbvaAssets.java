package com.olympus.nbva;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.NodeList;

import com.olympus.nbva.assets.AssetData;
import com.olympus.nbva.contracts.ContractData;
import com.olympus.nbva.kits.GetKitData;
import com.olympus.olyutil.Olyutil;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

// Run: http://localhost:8181/nbva/nbvalist?id=101-0007328-056
// http://localhost:8181/nbva/nbvalist?id=101-0015003-034

@WebServlet("/nbvalist")
public class NbvaAssets extends HttpServlet {
	static Statement stmt = null;
	static Connection con = null;
	static ResultSet res  = null;
	static NodeList  node  = null;
	static String s = null;
	static private PreparedStatement statement;
	static String propFile = "C:\\Java_Dev\\props\\unidata.prop";
	static String sqlFile = "C:\\Java_Dev\\props\\sql\\NBVAassetList.sql";
	static String kitFileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
	
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

	public static double strToDouble(String str) {
		double rtn = 0.0;
		if (! Olyutil.isNullStr(str)) {
			rtn = Double.parseDouble(str.replaceAll(",", ""));
		}		
		return(rtn);
	}
	/****************************************************************************************************************************************************/
	public static int strToInt(String str) {
		int rtn = 0;
		if (! Olyutil.isNullStr(str)) {
			rtn = Integer.parseInt(str.replaceAll(",", ""));
		}		
		return(rtn);
	}
	
	
	/****************************************************************************************************************************************************/
	public static long strToLong(String str) {
		long rtn = 0;
		//System.out.println("*** strToLong():" + str );
		if (! Olyutil.isNullStr(str)) {
			rtn = Long.parseLong(str.replaceAll(",", ""));
		}		
		return(rtn);
	}
	/****************************************************************************************************************************************************/
	public static AssetData loadAssetObj(String[] line) {
		AssetData asset = new AssetData();
		 //System.out.println("*** AssetData:" + line.toString() );
		 
		 asset.setAssetId(strToLong(line[7]));
		 asset.setEquipType(line[8]); 
		 asset.setCustomerID(line[9]); 
		 asset.setEquipDesc(line[10]); 
		 asset.setModel(line[11]); 
		 asset.setSerNum(line[12]); 
		 asset.setQty(strToInt(line[13])); 
		 asset.setEquipAddr1(line[14]); 
		 asset.setEquipAddr2(line[15]); 
		 asset.setEquipCity(line[16]); 
		 asset.setEquipState(line[17]);
		 asset.setEquipZip(line[18]); 
		 asset.setResidAmt(strToDouble(line[19]));
		 asset.setEquipCost(strToDouble(line[20]));
		 asset.setaRentalAmt(strToDouble(line[21]));
		 asset.setDispCode(strToInt(line[22])); 	 
		 asset.setTermDate( line[23]); 
		 return(asset);
	}
	
	/****************************************************************************************************************************************************/
	public static ContractData loadContractObj(String[] strSplitArr) {
	 
		ContractData contract = new ContractData();
		//double servicePay = 0.0;
		//double equipPay = 0.0;
		
		 
		contract.setContractID(strSplitArr[0]); 
		contract.setCustomerName(strSplitArr[1]); 
		contract.setCommenceDate(strSplitArr[2]);
		contract.setTerm(strToLong(strSplitArr[3])); 
		contract.setTermDate(strSplitArr[4]); 
		contract.setEquipPayment(strToDouble(strSplitArr[5])); 
		contract.setServicePayment(strToDouble(strSplitArr[6]));; 

		//System.out.println("*** ContractData:" + strSplitArr.toString() );
		
		return(contract);
	}
	
	/****************************************************************************************************************************************************/
	public static  List<Pair<ContractData, List<AssetData> >> parseData(ArrayList<String> strArr, int sz) {
		String[] strSplitArr = null;
		ContractData contract = null;
		AssetData asset = null;
		List<AssetData> assets = new ArrayList<AssetData>();
		List<Pair<ContractData, List<AssetData> >> listRtn = new ArrayList<>();
		
	
		int i = 0;
		
		for (i = 0; i < sz; i++) {
			//System.out.println("*** Data:" + strArr.get(i) );
			
			strSplitArr = Olyutil.splitStr(strArr.get(i), ";");
			if (i == 0) { // get Contract data
				contract = loadContractObj(strSplitArr);
				asset = loadAssetObj(strSplitArr);
			} else { // get Asset data
				asset = loadAssetObj(strSplitArr);
			}
			assets.add(asset);
			
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
		for (String s : strArr) {
			String[] items = s.split(sep);
			String contractID = items[0];
			double val = 0.0;
			val = Olyutil.strToDouble(items[1]);		
			if (id.equals(contractID)) {
				//System.out.println("*** Match:" + id + " -- Value:" + val );
				sum += val;
			}	
		}
		
		return(sum);
	}
	/****************************************************************************************************************************************************/
	public static void doRolloverCheck(String roType, String eDate, String termDate) {
		
		if (roType.equals("10")) { // 
			System.out.println("***^^*** R=" + roType + "-- Term="  + termDate  + "-- eDate=" + eDate + "--");
		} else {
			System.out.println("***^^*** No R=" + roType + "-- Term="  + termDate  + "-- eDate=" + eDate + "--");
		}
		
		
	}
	/****************************************************************************************************************************************************/

/****************************************************************************************************************************************************/

	
	/****************************************************************************************************************************************************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		double sumTotal = 0.0;
		ContractData contractData = new ContractData();
		AssetData assetData = new AssetData();
		List<Pair<ContractData, List<AssetData> >> rtnPair = new ArrayList<>();
		int rtnArrSZ = 0;
		ArrayList<String> strArr = new ArrayList<String>();
		ArrayList<String> kitArr = new ArrayList<String>();
		String idVal = "";
		String dispatchJSP = "/nbvadetail_flex.jsp";
		String paramName = "id";
		String paramValue = request.getParameter(paramName);
		
		String roParamName = "rotype";
		String  roParamValue = request.getParameter(roParamName);
		
		String eDateParamName = "eDate";
		String eDateParamValue = request.getParameter(eDateParamName);
		
		System.out.println("*** eDate=" + eDateParamValue + "-- RO=" + roParamValue + "--");
		String formUrl = "formUrl";
		String formUrlValue = "/nbva/nbvaexcel " ;
		request.getSession().setAttribute(formUrl, formUrlValue);
		String sep = ";";
		int arrSZ = 0;
		
		ArrayList<String> ageArr = new ArrayList<String>();	
		//ArrayList<SerNumSAP> sapObjArrRtn = new ArrayList<SerNumSAP>();
		 
		String ageFile = "C:\\Java_Dev\\props\\nbvacalcs\\nbva_aging.csv";
        String tag = "csvData: ";
        
         //strArr = readFile(snFile);
        ageArr = Olyutil.readInputFile(ageFile);
     	//Olyutil.printStrArray(ageArr);
        
        
		
		if ((paramValue != null && !paramValue.isEmpty())) {	
			 idVal= paramValue.trim();
			// System.out.println("*** idVal:" + idVal + "--");			 
		}
		
		strArr = getDbData(idVal, sqlFile, "", "Asset");
		arrSZ = strArr.size();
		//System.out.println("*** arrSz:" + arrSZ + "--");	
		
		if (arrSZ > 0) {
			 //Olyutil.printStrArray(strArr);
			
			kitArr = GetKitData.getKitData(kitFileName);
			//Olyutil.printStrArray(kitArr);	
			rtnPair = parseData(strArr, arrSZ);
			
			contractData = rtnPair.get(0).getLeft();
			
			rtnArrSZ = rtnPair.get(0).getRight().size();
			//System.out.println("*** RTN Arr SZ=" + rtnArrSZ + "--");
			//System.out.println("*** ContractReturn: ID=" + contractData.getContractID() + "--");
			//System.out.println("*** ContractReturn: EquipCost=" + contractData.getEquipPayment() + "--");
			//request.getSession().setAttribute("contract", contractData);
			request.getSession().setAttribute("rtnPair", rtnPair);
			
			//System.out.println("Begin forward to JSP");
			
			sumTotal = getContractTotals(idVal, ageArr, ",");
			  String termDate = rtnPair.get(0).getLeft().getTermDate();
			//doRolloverCheck(roParamValue, eDateParamValue, termDate);
			//System.out.println("*** SumTotal=" + sumTotal );
			request.getSession().setAttribute("sumTotal", sumTotal);
			request.getRequestDispatcher(dispatchJSP).forward(request, response);
	
		}
	} // End doGet()
	/****************************************************************************************************************************************************/



} // End Class

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
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@WebServlet("/nbvamod")
public class NbvaCodeModify extends HttpServlet {
	/****************************************************************************************************************************************************/
	public static void setAssetObj(List<Pair<ContractData, List<AssetData> >> dataObj, HttpServletRequest request ) {
		List<AssetData> assets = new ArrayList<AssetData>();
		int rArrSZ = dataObj.get(0).getRight().size();
		 //System.out.println("*** rArrSZ=" + rArrSZ + "--");
		String dispParam = "dispCodeArr_";
		assets = dataObj.get(0).getRight();
		for (int k = 0; k < rArrSZ; k++) {	
			long assetId = dataObj.get(0).getRight().get(k).getAssetId();
			String dispParamTag = dispParam + Long.toString(assetId);
			String dispCodeVal = request.getParameter(dispParamTag);
			dataObj.get(0).getRight().get(k).setDispCode(Olyutil.strToInt(dispCodeVal));
			
			//System.out.println("*** AssetID=" + assetId + "-- Tag=" + dispParamTag + "-- Value=" + dispCodeVal);
			
		}
		
	}
	
	
	/****************************************************************************************************************************************************/
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(  request,   response);	
	} // End doGet()

	/****************************************************************************************************************************************************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dispatchJSP = "/nbvadetail_code_result.jsp";
		HttpSession session = request.getSession(true);
		List<Pair<ContractData, List<AssetData>>> list = (List<Pair<ContractData, List<AssetData>>>) session.getAttribute("rtnPairList");
		
		HashMap<String, ArrayList<Integer>> sqlErrMap = new HashMap<String, ArrayList<Integer>>();
		ArrayList<Integer> errIDArrayRtn = new ArrayList<>();
		String s1 = (String) session.getAttribute("JB");
		String dispCoveVals = request.getParameter("dispCodeArr_0");
		 
		
		 setAssetObj(list, request);
		
		
		
		
		
		
		
		
		
		 /*
		String paramName = "dispCodeArr_335595";
		String paramValue = request.getParameter(paramName);
		System.out.println("*** P=" + paramValue);
		
		//String num = request.getParameter("num");
		int sz = Olyutil.strToInt(num);
		int i = 0;
		if (!Olyutil.isNullStr(dispCoveVals)) {
			for (i = 0; i < sz; i++) {
				// System.out.println("*** P=" + dispCoveVals.toString() + " -- Num=" + num +
				// "-- S1=" + s1 + "--");
				String param = "dispCodeArr_" + i;
				String paramCode = "dispCodeArr_";
				//
				// System.out.println("*** Param=" + param + "-- Value=" +
				// request.getParameter(param));
			}

		} else {
			System.out.println("*** P=Null -- Num=" + num);
		}

		// System.out.println("*** doGet() -- P=" + paramValue);
*/
		  request.getRequestDispatcher(dispatchJSP).forward(request, response);

	} // End doGet()
	
	
} // End Class

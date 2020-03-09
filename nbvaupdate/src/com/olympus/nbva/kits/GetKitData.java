package com.olympus.nbva.kits;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.olympus.olyutil.Olyutil;
@WebServlet("/kitdata")
public class GetKitData extends HttpServlet {
	static String kitFileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
	
	
	/******************************************************************************************************************************/
	/******************************************************************************************************************************/
	
	/*******************************************************************************************************************************/

	/******************************************************************************************************************************/


	/******************************************************************************************************************************/
	// Invoke: strArr = GetKitData.getKitData(fileName);
	public static ArrayList<String> getKitData(String fileName) throws IOException {

		ArrayList<String> strArr = new ArrayList<String>();
		strArr = Olyutil.readInputFile(fileName);
		return (strArr);
	}
	/******************************************************************************************************************************/
	public static ArrayList<String> displayKits(String key, List<KitData> kits) throws IOException {
		ArrayList<String> strArr = new ArrayList<String>();
		for(int i = 0; i < kits.size(); i++) {
			String data = "";
			 if (kits.get(i).getComponent().equals(key)) {
				 //System.out.println("Key:" + key + "-- ProdName:" +  kits.get(i).getProductName()   +  "  *** Components: " + kits.get(i).getComponent());
				 data = kits.get(i).getProductName()  + ";" + kits.get(i).getComponent() +  ";" + kits.get(i).getQty()  +  ";" + kits.get(i).getProductType();
				 strArr.add(data);
			 }
        }
		return (strArr);
	}
	/******************************************************************************************************************************/
	
	/******************************************************************************************************************************/
	public static HashMap<String, String> readKitFile(ArrayList<String> kitDataArr) throws IOException {
		String key = "";
		String strVal = "";
		String component = "";
		HashMap<String, String> map = new HashMap<String, String>();
		int idx  = 0;
		//ArrayList<KitData> kitArr = new ArrayList<KitData>();
		for (String str : kitDataArr) {
			String[] items = str.split(",");
			key = items[0];
			component = items[1];
			//System.out.println("** Processing Key:" + key + "--");
			if (!Olyutil.isNullStr(key)) {
				if (map.isEmpty()) {
					strVal = key + "," + component + "," + items[2] + "," + items[3] + ";";
					map.put(key, strVal);
					// System.out.println("%%% MT Map -- put strVal:" + strVal);
				} else {
					if (map.get(key) != null) {

						strVal = key + "," + component + "," + items[2] + "," + items[3] + ";" + map.get(key).toString();
						map.put(key, strVal);
						// System.out.println("%%% else if added strVal:" + strVal);
					} else {
						strVal = key + "," + component + "," + items[2] + "," + items[3] + ";";
						// System.out.println("*** put strVal else if else MK=" + strVal );
						map.put(key, strVal);
					}
				}
			}
		}	// End for
		return(map);
	}
	/****************************************************************************************************************************************************/
	public static ArrayList<String> getHashData(HashMap<String, String> kitMap, String key) throws IOException {
		String valStr = "";;
		ArrayList<String> strArr = new ArrayList<String>();
		String mapVal = "";
		
		Iterator it = kitMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry pair = (Map.Entry)it.next();
	    	
	    	if ( pair.getKey().equals(key)) {
	    		mapVal = (String) pair.getValue();
	    		//System.out.println("**** mapData:" + pair.getKey() + " = " +mapVal);
	    		String[] items = mapVal.split(";");
	    		for (int i = 0; i < items.length; i++) {
	    			strArr.add(items[i]);
	    		}		
	    	}
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		return(strArr);
	}

		
		
	
	/****************************************************************************************************************************************************/
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> rtnMap = new HashMap<String, String>();

		String fileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
		ArrayList<String> strArr = new ArrayList<String>();
		ArrayList<String> rtnArr = new ArrayList<String>();
		HashMap<String, List<KitData>> map = new HashMap<String, List<KitData>>();
		List<KitData> kits = new ArrayList<KitData>();
		String dispatchJSP = "/nbvakits.jsp";
		//String key = "MAJ-1820";
		String key = "PCF-H180AL";
		
		String paramName = "key";
		String paramValue = request.getParameter(paramName);
		if ((paramValue != null && !paramValue.isEmpty())) {	
			 key= paramValue.trim();
			 System.out.println("****** key:" + key + "--");			 
		}
		
		try {
			strArr = GetKitData.getKitData(fileName);
		} catch (IOException e) {
			  
			e.printStackTrace();
		
		}
		rtnMap = readKitFile(strArr);
		
		
		//key = "TDSBSTARTER";
		 
		rtnArr = getHashData(rtnMap, key);
		//Olyutil.printStrArray(rtnArr);
	  	
		request.getSession().setAttribute("strArr", rtnArr);
		
		//System.out.println("Begin forward to JSP");
		 request.getRequestDispatcher(dispatchJSP).forward(request, response);

		
	}
}

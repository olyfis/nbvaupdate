package com.olympus.nbva.kits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.olympus.olyutil.Olyutil;
import com.olympus.nbva.kits.*;
import org.json.JSONArray;
import org.json.JSONObject;
public class TestMain {
	/******************************************************************************************************************************/
	public static HashMap<String, List<KitData>> loadKitObj(ArrayList<String> kitDataArr) throws IOException {	
		List<KitData> kits = new ArrayList<KitData>();
		
		String key = "";
		Map<String,KitData> myKitMap = new HashMap<String, KitData>();
	    //List<Map<String , KitData>> myKits  = new ArrayList<Map<String,KitData>>();
	    HashMap<String, List<KitData>> map = new HashMap<String, List<KitData>>();
	    	
		//Olyutil.printStrArray(kitDataArr);
		for (String str : kitDataArr) {
			String[] items = str.split(",");
			key = items[1];
			if (! Olyutil.isNullStr(key)) {
				//System.out.println("** Key:" + key + "--");
				KitData kit = new KitData();
				kit.setProductName(items[0]);
				kit.setComponent(items[1]);
				if (! Olyutil.isNullStr(items[2])) {
					kit.setQty(Olyutil.strToInt(items[2]));
				}
				
				kit.setProductType(items[3]);
				if (map.get(key) != null) {
					//System.out.println("** " + key + " -- Key Exists");
					kits = map.get(key);
					kits.add(kit);
					map.put(key, kits);
				} else {
					//System.out.println("** " + key + " -- Key Does NOT Exists");
					kits.add(kit);
					map.put(key, kits);
				}	
			}		
		}	
		return(map);
	}

	/*******************************************************************************************************************************/

	public static void main(String[] args) throws IOException {
		String fileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
		ArrayList<String> strArr = new ArrayList<String>();
		 
		HashMap<String, List<KitData>> map = new HashMap<String, List<KitData>>();
		List<KitData> kits = new ArrayList<KitData>();
		
		
		
		//String key = "MAJ-1820";
		String key = "PCF-H180AL";
		
		try {
			strArr = GetKitData.getKitData(fileName);
		} catch (IOException e) {
			  
			e.printStackTrace();
		}
		//Olyutil.printStrArray(strArr);
		map = loadKitObj(strArr) ;
		System.out.println("**** Done");
		kits = map.get(key);
		 
	 
		 System.out.println("*** KitSZ=" + kits.size() );
		 for(int i = 0; i < kits.size(); i++) {
			 
			 if (kits.get(i).getComponent().equals(key)) {
				 System.out.println("Key:" + key + "-- ProdName:" +  kits.get(i).getProductName()   +  "  *** Components: " + kits.get(i).getComponent());
			 }
			 /*
			 if (map.containsKey(key)){
		            if ((map.get(key)).equals(key)){
		            	 System.out.println("Key:" + key + "-- *** Components: " + kits.get(i).getComponent());
		            }
		        }
			 */
			 
          
         }
		 System.out.println("**********************************************************************************************************");
	}
	/******************************************************************************************************************************/

	
	
	/******************************************************************************************************************************/

}

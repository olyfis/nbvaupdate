 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.OutputStream"%>   
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="com.olympus.nbva.assets.AssetData"%>
<%@ page import="com.olympus.nbva.contracts.ContractData"%>
<%@ page import="org.apache.commons.lang3.tuple.*"%>
<%@ page import="com.olympus.olyutil.*"%>
<%@ page import="java.lang.reflect.*"%>

<% 
	String title =  "Olympus NBVA Asset List Report"; 
	 
	ArrayList<String> tokens = new ArrayList<String>();
	ArrayList<String> kitArr = new ArrayList<String>();
	String formUrl =  (String) session.getAttribute("formUrl");
 	String kitFileName = "C:\\Java_Dev\\props\\kitdata\\kitdata.csv";
	HashMap<String, String> map = new HashMap<String, String>();

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><%=title%></title>
<!--  	101-0015003-034 -->
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery.tablesorter/2.9.1/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="includes/js/tableFilter.js"></script>
<style><%@include file="includes/css/reports.css"%></style>
<style><%@include file="includes/css/table.css"%></style>
<style><%@include file="includes/css/header.css"%></style>
<link rel="stylesheet" href="includes/css/calendar.css" />

<!-- ********************************************************************************************************************************************************* -->


<script>

$(function() {

  // call the tablesorter plugin
  $("table").tablesorter({
    theme: 'blue',
    // initialize zebra striping of the table
    widgets: ["zebra"],
    // change the default striping class names
    // updated in v2.1 to use widgetOptions.zebra = ["even", "odd"]
    // widgetZebra: { css: [ "normal-row", "alt-row" ] } still works
    widgetOptions : {
      zebra : [ "normal-row", "alt-row" ]
    }
  });

});	
			
    </script>
    
   <script>
  function openWin(myID) {
  
  
   myID2 = document.getElementById(b_app).value;

  alert("ID" + myID2);
  //window.open("http://cvyhj3a27:8181/fisAssetServlet/readxml?appID=" + myID2);
	}
	
	
	var call = function(id){
		var myID = document.getElementById(id).value;
		//alert("****** myID=" + myID + " ID=" + id);		 
			//window.open("http://cvyhj3a27:8181/nbva/kitdata?key=" + myID);
		window.open("http://localhost:8181/nbva/kitdata?key=" + myID);
				
				
	}



	var getExcel = function(urlValue){
		var formUrl = document.getElementById(urlValue).value;
		//alert("SD=" + startDate + "****** formUrl=" + formUrl + " \n***** urlValue=" + urlValue);
		//alert("in Quote" + myID + " --- id=" +id);
		window.open( formUrl, 'popUpWindow','height=500,width=800,left=100,top=100,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no, status=yes' );
	
	}
	
	
	
</script> 
    
</head>
<body>

<%!  

String formUrl = null;
/*************************************************************************************************************************************************************/
public ArrayList<String> readHeader(String filePath) throws IOException {
	
	ArrayList<String> strArr = new ArrayList<String>();
	String header = null;
	BufferedReader reader = null;
	StringBuilder sb = null;
	String line = null;
	try {
	 	reader = new BufferedReader(new FileReader(filePath));
    	 sb = new StringBuilder();
    
	} catch (FileNotFoundException fex) {
		fex.printStackTrace();	
	}
	try { 
	    while((line = reader.readLine())!= null){
	    	strArr.add(line);
	    }	   
		reader.close();
	
	} catch (IOException ioe) {
		ioe.printStackTrace();
	}
	
	return strArr;	
}
/*************************************************************************************************************************************************************/
public String  buildHeader( JspWriter out2, ArrayList<String> dataArr   ) throws IOException {
	
	String header = "";
	String style = "b3";
	if (dataArr.size() > 0) {
		for (int k = 0; k < dataArr.size(); k++) {
			
			if (k == 1) {
				style = "b3a";
			} else {
				style = "b3";
			}
			//header += "<th class=\"b3\" >" + dataArr.get(k) + " </th>";
			header += "<th class=\" " + style + "  \" >" + dataArr.get(k) + " </th>";
			
		}
	}
	return header;
	
}
/*************************************************************************************************************************************************************/
public String  buildCells( JspWriter out, ArrayList<String> dataArr  ) throws IOException {
	String cells = "";
	String xDataItem = null;
	String color1 = "plum";
	String style1 = "font-family: sans-serif; color: white;";
	String rowEven = "#D7DBDD";
	String rowOdd = "AEB6BF";
	String excel = null;
	String rowColor = null;
	
	//t.println("<tr>");
	//cells = "<tr>";	
	if (dataArr.size() > 0) {
		for (int k = 0; k < dataArr.size(); k++) {
			rowColor = (k % 2 == 0) ? rowEven : rowOdd;
			cells +="<tr bgcolor=" + rowColor + ">";
			xDataItem = dataArr.get(k);
			String token_list[] = xDataItem.split(";");
			for (int x = 0; x < token_list.length; x++) {
				cells += "<td class=\"odd\">" + token_list[x] + "</td>";
			}
			cells += "</tr >";
			
			//cells += "<td class=\"a\" >" + dataArr.get(k) + " </td>";
		}
	}
	
	
	return cells;
}

/*************************************************************************************************************************************************************/
public void  buildCellsContract( JspWriter out, ContractData contract, String formUrl  ) throws IOException {
	String cells = "";
	String xDataItem = null;
	String color1 = "plum";
	String style1 = "font-family: sans-serif; color: white;";
	String rowEven = "#D7DBDD";
	String rowOdd = "AEB6BF";
	String excel = null;
	String rowColor = null;
	String style = "b3c";
 	
	out.println("<tr>"); 
	out.println("<th class=\" " + style + "  \" >Contract Number</th>");
	out.println( "<td class=\"a\">" + contract.getContractID() + "</td></tr>");
	 
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Customer Name</th>");
	out.println( "<td  width=\"70%\"  >" + contract.getCustomerName() + "</td></tr>");
	
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Commencement Date</th>");
	out.println( "<td class=\"a\">" + contract.getCommenceDate() + "</td></tr>");
	
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Term</th>");
	out.println( "<td class=\"a\">" + contract.getTerm() + "</td></tr>");
	
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Term Date</th>");
	out.println( "<td class=\"a\">" + contract.getTermDate() + "</td></tr>");
	
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Equipment Payment</th>");
	out.println( "<td class=\"a\">" + contract.getEquipPayment() + "</td></tr>");
	
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Service Payment</th>");	
	out.println( "<td class=\"a\">" + contract.getServicePayment() + "</td></tr>");
	
 
	out.println("<tr>");
	out.println("<th class=\" " + style + "  \" >Save as Excel File</th>");	
	out.println( "<td class=\"a\"> ");
	out.println(" <form name=\"excelForm\"    enctype=\"multipart/form-data\"   method=\"get\" action=" +   formUrl  +  " >    ");
	out.println("<input type=\"submit\" value=\"Save Excel File\" class=\"btn\" /> ");
	out.println("</form> </td></tr></table>");
	
	out.println( "  </td></tr>");
	
}

/*************************************************************************************************************************************************************/

public static void displayObj(Object obj) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	
	for (Method m : obj.getClass().getMethods())
	    if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {
	      final Object r = m.invoke(obj);
	      // do your thing with r
	      //System.out.println("Method:" + m.getName() + " : " + r.toString() );
	    }

}
/*************************************************************************************************************************************************************/
public String  buildCellsAsset( HashMap<String, String> hm, JspWriter out,  List<Pair<ContractData, List<AssetData> >> rtnPair  ) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	String cells = "";
	String xDataItem = null;
	String color1 = "plum";
	String style1 = "font-family: sans-serif; color: white;";
	String rowEven = "#D7DBDD";
	String rowOdd = "AEB6BF";
	String excel = null;
	String rowColor = null;
	String model = "";
	
	
	int listArrSZ = rtnPair.size();
	if (listArrSZ > 0) {	
		for (int i = 0; i < listArrSZ; i++ ) {
			int rtnArrSZ = rtnPair.get(i).getRight().size();
			List<AssetData> assetList = new ArrayList<AssetData>();
			assetList	= rtnPair.get(i).getRight();
			//out.println("<h5> listArrSZ =" + listArrSZ + " -- rtnArrSZ=" +  rtnArrSZ + "--</h5>");
			for (int j = 0; j < rtnArrSZ; j++ ) {
				AssetData asset = new AssetData();
				asset = assetList.get(j);
				//System.out.println("*** AssetReturn: EquipmentType=" + asset.getEquipType() + "--");
				//System.out.println("*** AssetReturn: CustomerID=" + asset.getCustomerID() + "--");
				model = asset.getModel();
				//System.out.println("*** AssetReturn: Model ->" + model + "--");
				
				rowColor = (j % 2 == 0) ? rowEven : rowOdd;
				cells +="<tr bgcolor=" + rowColor + ">";
				cells +="<TD>" + asset.getAssetId() + "</td> ";
				cells +="<TD>" + asset.getEquipType() + "</td> ";
				cells +="<TD>" + asset.getCustomerID() + "</td> ";
				cells +="<TD>" + asset.getEquipDesc() + "</td> ";
				if(hm.containsKey(model)){
		           // System.out.println("The hashmap contains value:" + model);
		            cells +="<TD>"  ;
		            cells +="  <form enctype=\"multipart/form-data\" method=\"post\" > ";
		            cells +="<input type=\"hidden\" id=\"" + model + "\"  value=\"" + model + "\" />";
		            //cells +="<input type=\"button\" onclick=\"call(document.getElementById(" + model + ").id)  value="+ model + "/></form>";
		            cells +="<input class=\"btn2\" type=\"button\"  value=\""+ model + "\" onclick=\" call(document.getElementById('" + model+ "').id)       \"  ></form>";
		            
		        /*
		          
		 		   <input type="hidden" id="{APP_CONTRACT_NUMBER}"     value="{APP_CONTRACT_NUMBER}"       /> 
		 		   <input type="button" onclick="call(document.getElementById('{APP_CONTRACT_NUMBER}').id)" value="Get Data"/> 
		 	    </form>
		 	    */
		 	    
		            cells +="  </td> ";
		        } else {
					cells +="<TD>" + asset.getModel() + "</td> ";
		        }
				
				cells +="<TD>" + asset.getSerNum() + "</td> ";
				cells +="<TD>" + asset.getQty() + "</td> ";
				cells +="<TD>" + asset.getEquipAddr1() + "</td> ";
				cells +="<TD>" + asset.getEquipCity() + "</td> ";
				cells +="<TD>" + asset.getEquipState() + "</td> ";
				cells +="<TD>" + asset.getEquipZip() + "</td>  ";
				cells +="<TD>" + asset.getResidAmt() + "</td>  ";
				cells +="<TD>" + asset.getEquipCost() + "</td>  ";
				cells +="<TD>" + asset.getaRentalAmt()+ "</td>  ";
				cells +="<TD>" + asset.getDispCode() + "</td></tr> ";
				
				/*
				for (int k = 0; k < rtnArrSZ; k++ ) {
					displayObj(asset);
				}
				*/
				
 
 			}
		}
	
 	}
		
	return(cells);
}
/*************************************************************************************************************************************************************/
// Invoke: strArr = GetKitData.getKitData(fileName);
	public static ArrayList<String> getKitData(String fileName) throws IOException {

		ArrayList<String> strArr = new ArrayList<String>();
		strArr = Olyutil.readInputFile(fileName);
		return (strArr);
	}
/*************************************************************************************************************************************************************/

	public static HashMap<String, String> getKitHash(ArrayList<String> kitDataArr  ) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		String key = "";
		
		for (String str : kitDataArr) {
			String[] items = str.split(",");
			key = items[0];
			map.put(key, "true");
		}
		return (map);
	}

/*************************************************************************************************************************************************************/

%>

<div style="padding-left:20px">
<%@include  file="includes/header.html" %>

<h3><%=title%></h3>
<h5>This page will provide an on-demand NBVA Asset List Report.</h5>
<!-- <BR> <font color="red"> Note: Some queries take more time to run. Please be patient.</font>   -->

<%
	String filePath = "C:\\Java_Dev\\props\\headers\\NBVA_ContractHrd.txt";
	ArrayList<String> headerArr = readHeader(filePath);
  
	String filePath2 = "C:\\Java_Dev\\props\\headers\\NBVA_AssetHrd.txt";
	ArrayList<String> headerArr2 = readHeader(filePath2);
	kitArr = getKitData(kitFileName);
	map = getKitHash(kitArr);
	
	//ArrayList<String> list2 = new ArrayList<String>();
	 List<Pair<ContractData, List<AssetData> >> list = (List<Pair<ContractData, List<AssetData> >>) session.getAttribute("rtnPair");

	 int lsz = list.size();
	 int rtnArrSZ = 0;
	 ContractData contractData = new ContractData();
	 AssetData assetData = new AssetData();		
	 		

	 if (lsz > 0) {	
	 	
	 	contractData = list.get(0).getLeft();
	 	rtnArrSZ = list.get(0).getRight().size();
	 	//out.println("<h5> List Size:" + lsz + " -- rtnArrSZ=" +  rtnArrSZ + "--</h5>");

	 	if (rtnArrSZ > 0) {
	 		
	 		list.get(0).getRight().get(0);
	 		
	 	}
	 	
	 	
	 	
	 }
	
	
	
	
	 
	//list2.add("xx");
	
	//out.println("listSize=" + list.size());
	if (list.size() > 0) {
		/**********************************************************************************************************************************************************/
		// Output Table 
	%>	
	
	<table border="0"  ><tr>
	
	<!--  
	<td><input id="search" type="text" placeholder="Enter Text to Filter..."></td>
-->
<td>
<!-- 
 <form name="excelForm" enctype="multipart/form-data" method="get" action=" <%=formUrl%> " \>
 --> 
  
<%
/*
out.println(" <form name=\"excelForm\"    enctype=\"multipart/form-data\"   method=\"get\" action=" +   formUrl  +  " >    ");
out.println("<input type=\"submit\" value=\"Save Excel File\" class=\"btn\" /> ");
out.println("</form> </td></tr></table>");
*/




		out.println("<table class=\"tablesorter\" border=\"1\"  width=\"600\"> <thead> <tr>");
		//String header = buildHeader(out, headerArr); // build header from file
		//out.println(header);
		//out.println("</tr></thead>");
		out.println("<tbody id=\"report\">");
		  buildCellsContract(out, contractData, formUrl); // build data cells from file
		//out.println(cells);
		out.println("</tbody></table><BR>"); // Close Table
		/**********************************************************************************************************************************************************/
	
	out.println("<table class=\"tablesorter\" border=\"1\"> <thead> <tr>");
		String header2 = buildHeader(out, headerArr2); // build header from file
		out.println(header2);
		out.println("</tr></thead>");
		out.println("<tbody id=\"report\">");
		out.println(buildCellsAsset(map, out, list)); // build data cells from file
		
		out.println("</tbody></table><BR>"); // Close Table
	
	
	
		/**********************************************************************************************************************************************************/

	
	} else {
		out.println("No Asset data to display." + "<br>");

	}
	 
%>

</body>
</html>
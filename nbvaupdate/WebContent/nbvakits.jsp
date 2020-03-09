<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.OutputStream"%>   
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<% 
  	String title =  "FIS NBVA Kit Data Page"; 
	ArrayList<String> list = new ArrayList<String>();
	ArrayList<String> tokens = new ArrayList<String>();
	String formUrl =  (String) session.getAttribute("formUrl");
	String filePath = "C:\\Java_Dev\\props\\headers\\nbvakitHdr.txt";
	 ArrayList<String> headerArr = readHeader(filePath);
 
	ArrayList<String> list2 = new ArrayList<String>();
	list = (ArrayList<String>) session.getAttribute("strArr");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title><%=title%></title>
<style><%@include file="includes/css/header.css"%></style>
<style><%@include file="includes/css/table2.css"%></style>

<!-- ********************************************************************************************************************************************************* -->
</head>

<%!  
/*****************************************************************************************************************************************************************/
//String formUrl = null;
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
			header += "<th class=\" " + style + "  \" >" + dataArr.get(k) + " </th>";	
		}
	}
	return header;
}
/*************************************************************************************************************************************************************/

/*************************************************************************************************************************************************************/
public String  buildCells(JspWriter out, ArrayList<String> dataArr  ) throws IOException {
	String cells = "";
	String xDataItem = null;
	String color1 = "plum";
	String style1 = "font-family: sans-serif; color: white;";
	String rowEven = "#D7DBDD";
	String rowOdd = "AEB6BF";
	String excel = null;
	String rowColor = null;
	

	if (dataArr.size() > 0) {
		for (int k = 0; k < dataArr.size(); k++) {
			rowColor = (k % 2 == 0) ? rowEven : rowOdd;
			cells +="<tr bgcolor=" + rowColor + ">";
			xDataItem = dataArr.get(k);
			String token_list[] = xDataItem.split(",");
			for (int x = 0; x < token_list.length; x++) {
				cells += "<td class=\"b3\">" + token_list[x] + "</td>";
			}
			cells += "</tr >";
		}
	}
	return cells;
}
%>

<!-- ********************************************************************************************************************************************************* -->



<body>
    
    
 <%@include  file="includes/header.html" %>
 
  <h3><%=title%></h3>
 
<BR>
<!--   <h5>This page will provide an on-demand...  </h5> -->

<%
 		
%>
<BR>
<!--  action = servlet to call: http://localhost:8181/reports/olrent?id=2019-08-31   -->	 
 <!-- ************************* Inner Table ****************************************************************************** -->
 <!-- 
    <table  border="0"  width="10%"    >
  <tr>
 
    <td  valign="bottom"  >
    <form name="excelForm" enctype="multipart/form-data" method="get" action="<%=formUrl%> " \> 
    <input type="submit" value="Save Excel File" class="btn" /> 
    </form> 
	</td> 
   </tr></table>
    <!-- ****************************************************************************************************************************** -->
   <!--    
    </td>
  </tr>
</table> -->
	<div style="height: 500px; overflow: auto;">
<%
out.println("<table  border=\"1\"> <thead> <tr>");
String header = buildHeader(out, headerArr); // build header from file
out.println(header);
out.println("</tr></thead>");
out.println("<tbody id=\"report\">");
String cells =  buildCells(out, list); // build data cells from file
out.println(cells);
out.println("</tbody></table>"); // Close Table
      %>

 
</div>
</body>
</html>
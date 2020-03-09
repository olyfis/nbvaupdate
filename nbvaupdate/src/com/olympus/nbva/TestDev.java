package com.olympus.nbva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.olympus.olyutil.Olyutil;
import com.olympus.olyutil.log.OlyLog;
 

@WebServlet("/testdev")
public class TestDev extends HttpServlet {
	
	private final Logger logger = Logger.getLogger(TestDev.class.getName()); // define logger
	static Statement stmt = null;
	static Connection con = null;
	static ResultSet res  = null;
	static String s = null;
	static private PreparedStatement statement;
	static String propFile = "C:\\Java_Dev\\props\\unidata_Dev9F.prop";
	
	static String sqlFile = "C:\\Java_Dev\\props\\sql\\testDev.sql";
	static String sqlFileUpdate = "C:\\Java_Dev\\props\\sql\\testDevUpdate.sql";
	
	/****************************************************************************************************************************************************/
	
	/****************************************************************************************************************************************************/

	/****************************************************************************************************************************************************/

	/****************************************************************************************************************************************************/
	public static ArrayList<String> getDbData(String  dateParam ) throws IOException {
		FileInputStream fis = null;
		FileReader fr = null;
		String s = new String();
        StringBuffer sb = new StringBuffer();
        ArrayList<String> strArr = new ArrayList<String>();
		try {
			fis = new FileInputStream(propFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties connectionProps = new Properties();
		connectionProps.load(fis);
		 
		//fr = new FileReader(new File(sqlFileUpdate));
		fr = new FileReader(new File(sqlFile));
		// be sure to not have line starting with "--" or "/*" or any other non alphabetical character
		BufferedReader br = new BufferedReader(fr);
		while((s = br.readLine()) != null){
		      sb.append(s);
		       
		}
		br.close();
		//displayProps(connectionProps);
		String query = new String();
		query = sb.toString();	
		//System.out.println("Query=" + query);	
		//System.out.println( query);	
		try {
			con = Olyutil.getConnection(connectionProps);
			if (con != null) {
				//System.out.println("Connected to the database");
				
				//System.out.println("***^^^*** dateParam=" + dateParam);
				String newQuery = query.replaceFirst("!", dateParam);
				//System.out.println( newQuery);	
				
				statement = con.prepareStatement(newQuery);
				
				
				
				//statement.setString(1, dateParam);
				res = Olyutil.getResultSetPS(statement);		 	 
				strArr = Olyutil.resultSetArray(res, ";");			
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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> strArr = new ArrayList<String>();
		
		String logFileName = "testDev.log";
		String directoryName = "D:/Kettle/logfiles/testDev";
		Handler fileHandler =  OlyLog.setAppendLog(directoryName, logFileName, logger );
		String dateFmt = "";
	 
		String connProp = null;
		String sqlFile = null;
		String sep = null;
		
		Date date = Olyutil.getCurrentDate();
		String dateStamp = date.toString();
		// System.out.println("Date=" + dateStamp);
		 
		sep = ";";
		strArr = getDbData("");
		Olyutil.printStrArray(strArr);

		request.getSession().setAttribute("strArr", strArr);
		 // req.getSession().setAttribute(paramName, paramValue);
		dateFmt = Olyutil.formatDate("yyyy-MM-dd hh:mm:ss.SSS");
		//logger.info(dateFmt + ": " + "------------------Begin forward to: " );
		fileHandler.flush();
		fileHandler.close();
		System.out.println("*** strArr Size:" + strArr.size());
		//request.getRequestDispatcher(dispatchJSP).forward(request, response);
		// System.out.println("Exit Servlet " + this.getServletName() + " in doGet() ");
		
	}
	

}

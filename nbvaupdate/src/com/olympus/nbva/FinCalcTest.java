package com.olympus.nbva;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.olympus.olyutil.Olyutil;

/***************************************************************************************************************************************/
/*
    URL: https://poi.apache.org/apidocs/dev/org/apache/poi/ss/formula/functions/FinanceLib.html
	Parameters:
	rate - rate
	term - num of periods
	payPeriod - pmt per period
	fv - future value
	type - type (true=pmt at end of period, false=pmt at beginning of period)
	
	Symbols used in the formulae that follow:
	p: present value
	f: future value
	n: number of periods
	y: payment (in each period)
	r: rate
*/
/***************************************************************************************************************************************/

public class FinCalcTest {
	// usage: dRtn = roundDouble(dVal, "UP", "0.00"); // Default rounds up
	public static double roundDouble(double value, String direction, String fmt) {
		
		DecimalFormat df = new DecimalFormat(fmt);
		double dVal = 0.0;
		
		if (direction.toUpperCase().equals("DOWN")) {
			df.setRoundingMode(RoundingMode.DOWN);
		} else {
			df.setRoundingMode(RoundingMode.UP);
		}
		String dStr = df.format(value);
		dVal = Double.parseDouble(dStr);
		
		return(dVal);
	}
	
/***************************************************************************************************************************************/
	// type - type (true=pmt at beginning of period, false=pmt at end of period)
	
	/*
	 *  pv(double r, double n, double y, double f, boolean t)
	 	r - rate
		n - num of periods(term)
		y - pmt per period
		f - future value
		t - type
	 */
	
public static  double getPV(double rate, double term, double y, double fv, boolean type) {
	Double dRtn = 0.0;
	Double dVal = FinanceLib.pv(rate, term, y, fv, type);
	 dRtn = roundDouble(dVal, "UP", "0.00");
	//dRtn = roundDouble(dVal, "DOWN", "0.00");
	
	return (dRtn);	 
	}	 
	
/***************************************************************************************************************************************/
/*
 * Parameters:
	r - rate
	n - num of periods
	y - pmt per period
	p - future value
	t - type (true=pmt at beginning of period, false=pmt at end of period)
 * 
 */

//type - type (true=pmt at beginning of period, false=pmt at end of period)
public static  double getFV(double rate, double term, double payPeriod, double fv, boolean type) {
	Double dRtn = 0.0;
	Double dVal = FinanceLib.fv(rate, term, payPeriod, fv, type);
	 dRtn = roundDouble(dVal, "UP", "0.00");
	//dRtn = roundDouble(dVal, "DOWN", "0.00");
	
	return (dRtn);	 
	}	 
/***************************************************************************************************************************************/
//type - type (true=pmt at beginning of period, false=pmt at end of period)
public static  double getPmt(double rate, double term, double pv, double fv, boolean type) {
	Double dRtn = 0.0;
	Double dVal = FinanceLib.pmt(rate, term, pv, fv, type);
	 dRtn = roundDouble(dVal, "UP", "0.00");
	 
	
	return (dRtn);	 
	}	 
/***************************************************************************************************************************************/


public static void main(String[] args) {
		
	double pvRtn = 0.0;		 
	double rate = 0.0725;
    double term = 36.0;
   // double y = -26700.74;
    double y = 26700.74;
    double fv = 460943.00;
    boolean type = false;
    double fvRtn = 0.0;	
    double pmtRtn = 0.0;
    String effectiveDate = "";
    
    pvRtn =  getPV( rate, term, y, fv, type);
    System.out.println("pvRtn=" + String.format( "%.2f", pvRtn ) + "--");
    
    fvRtn =  getFV( rate, term, y, fv, type);
    System.out.println("fvRtn=" + String.format( "%.2f", fvRtn ) + "--");
    
   // pmtRtn =  getPmt( rate, term, payPeriod, fv, type);
    pmtRtn =  getPmt( rate, term, pvRtn, fvRtn, type);
    System.out.println("pmtRtn=" + String.format( "%.2f", pmtRtn ) + "--");
    
    effectiveDate = DateUtil.calculateMonthsFromDate(13);
    System.out.println("Effective Date=" +  effectiveDate);
     
    
    DateUtil.calculateMonthsBetweenDates( "2020-02-01",   "2020-09-01", 9 );
 

 
    
}
/***************************************************************************************************************************************/

}

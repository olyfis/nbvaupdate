package com.olympus.nbva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import com.olympus.olyutil.Olyutil;

public class DateUtil {
	
	/********************************************************************************************************************************************************/

	// Call: 	effectiveDate = DateUtil.calculateMonthsFromDate(9);
    //			System.out.println("Effective Date=" +  effectiveDate);
	
	public static String calculateMonthsFromDate(int range){
		LocalDate specialOfferExpiryDate = LocalDate.now();
         
        if(range > 0 && range <=12) {
        	LocalDate today = LocalDate.now(); 
            specialOfferExpiryDate = today.plusMonths(range) ; 
            //System.out.println("Month=" + specialOfferExpiryDate.toString() );
         }
        return specialOfferExpiryDate.toString();

   }
	/********************************************************************************************************************************************************/
	//Default pattern is yyyy-MM-dd
	public static int calculateMonthsBetweenDates(String termDate, String effDate, int monthsToAdd) {
	
		int months = 0;
		LocalDate tDate = LocalDate.parse(termDate);
		LocalDate eDate = LocalDate.parse(effDate);
		LocalDate futureTermDate = tDate.plusMonths(monthsToAdd);

		//System.out.println("T=" + tDate.toString());
		//System.out.println("FT=" + futureTermDate.toString());
		//System.out.println("E=" + eDate.toString());

		Period diff = Period.between(LocalDate.parse("2016-08-22").withDayOfMonth(1),
				LocalDate.parse("2016-09-10").withDayOfMonth(1));
		//System.out.println("Diff=" + diff); // P3M

		return months;
	}
	/*********************************************************************************************************************************************************/
	public static int compareDateDays(String effDate, String commDate) throws ParseException {
		int rtnVal = 0;
		String cDay = "";
		String eDay = "";

		String[] cdateArr = commDate.split("-");
		cDay = cdateArr[2];
		String[] edateArr = effDate.split("-");
		;
		eDay = edateArr[2];
		if (!eDay.equals(cDay)) {
			//System.out.println("E=" + eDay + "--    C=" + cDay);
			rtnVal = -2;
		}
		return (rtnVal);
	}
	
	/*********************************************************************************************************************************************************/
	// int rtn = DateUtil.compareDates("2020-01-01", "2020-02-01");
	// rtn = -1 -- Effective date occurs before Commencement date
	public static int compareDates(String effDate, String commDate) {
		int rtnVal = 0;
		Date edate = null;
		
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		Date cdate = null;
		try {
			cdate = sdformat.parse(commDate);
			edate = sdformat.parse(effDate); 
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("The Effective date is: " + sdformat.format(edate)  + "-- Param=" + effDate );
		//System.out.println("The Commencement date is: " + sdformat.format(cdate)  + "-- Param=" + commDate);
		
		if (cdate.compareTo(edate) > 0) {
			rtnVal = -1;
			 //System.out.println("Effective date occurs before Commencement date -- RTN=" + rtnVal);
			
		} else if (cdate.compareTo(edate) < 0) {
			rtnVal = 1;
			  //System.out.println("Effective date occurs after Commencement date -- RTN=" + rtnVal);
		} else if (cdate.compareTo(edate) == 0) {
			rtnVal = 0;
			//System.out.println("Both dates are equal");
		}	
		return(rtnVal);
	}
	/********************************************************************************************************************************************************/
	public static String addMonthsToDate(String origDate, int mths) {
		String newDate = "";
		LocalDate date   = LocalDate.parse(origDate); 
		LocalDate returnvalue  = date.plusMonths(mths); 
		
		//System.out.println("^^^!!!*** OrigDate=" +  origDate + " -- LocalDate after " + " adding months:" + mths   +  " ReturnedDate="  + returnvalue); 
		 newDate = returnvalue.toString();
		 //System.out.println("***** LocalDate after " + " adding months: " + newDate); 
		return(newDate);
	}
	
	
	/********************************************************************************************************************************************************/
	// Returns years, months and days

	public static int differenceInMonths(String effDate, String termDate) {
		int diff = 0;
		Period period = Period.between(
            LocalDate.parse(effDate).withDayOfMonth(1),
            LocalDate.parse(termDate).withDayOfMonth(1));
		//System.out.println(period); //P3M
		int mths = period.getYears() * 12;
		diff  = mths + period.getMonths();
		
		return diff;
}
	/********************************************************************************************************************************************************/
	// Returns months only
	
	
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// Date d1 = sdf.parse("2014-03-22");
	//Date d1 = sdf.parse(effDate);
		//Date d2 = sdf.parse(termDate);
	
	
	// d1 = effDate -- d2 = termDate
	public static int differenceInMonths2(Date d1, Date d2) {
	    Calendar c1 = Calendar.getInstance();
	    c1.setTime(d1);
	    Calendar c2 = Calendar.getInstance();
	    c2.setTime(d2);
	    int diff = 0;
	    if (c2.after(c1)) {
	        while (c2.after(c1)) {
	            c1.add(Calendar.MONTH, 1);
	            if (c2.after(c1)) {
	                diff++;
	            }
	        }
	    } else if (c2.before(c1)) {
	        while (c2.before(c1)) {
	            c1.add(Calendar.MONTH, -1);
	            if (c1.before(c2)) {
	                diff--;
	            }
	        }
	    }
	    return diff;
	}
	/********************************************************************************************************************************************************/

}

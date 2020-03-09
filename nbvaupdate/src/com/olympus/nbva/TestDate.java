package com.olympus.nbva;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		// p1 = effDate -- p2 = commDate
		// effDate cannot be less than termDate
		int rtn = DateUtil.compareDates("2021-04-01", "2020-02-03");
		System.out.println("Val=" + rtn);
		
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		
		// di = effDate -- d2 = termDate
	    Date d1 = f.parse("2020-02-01");
	    Date d2 = f.parse("2017-03-31");
	    int n = DateUtil.differenceInMonths2(d1, d2);
	    System.out.println("mthDiff=" + n);
	    
	    
	    
	    
	    
	    
	  /*  
	    
	    
	    
	    // create a LocalDate object 
        LocalDate date 
            = LocalDate.parse("2020-01-15"); 
  
        // print instance 
        System.out.println("LocalDate before"
                           + " adding months: " + date); 
  
        // add 5 months 
        LocalDate returnvalue 
            = date.plusMonths(9); 
  
        // print result 
        System.out.println("LocalDate after "
                           + " adding months: " + returnvalue); 
        
        */
	}

}

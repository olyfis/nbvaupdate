package com.olympus.nbva;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Calendar; 
import java.util.Date; 
import java.util.GregorianCalendar; 
 

 
public class TestMonthDiff {

	public static void main(String[] args) {
	 
		Period period = Period.between(
	            LocalDate.parse("2020-02-01").withDayOfMonth(1),
	            LocalDate.parse("2017-03-01").withDayOfMonth(1));
	System.out.println(period); //P3M
	
	System.out.print(period.getYears() + " years,");
	int mths = period.getYears() * 12;
    System.out.print(period.getMonths() + " months,");
    System.out.println(period.getDays() + " days");
    int totMths = mths + period.getMonths();
    System.out.println("total months=" + totMths);
    
	}

}

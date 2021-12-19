// Filename DateUtilityDemo.java.
// Demonstration client for the DateUtility class.
//
// Written for JI book chapter 3.
// Fintan Culwin, v 0.2, August 1997. 

package DatePanel;

import DatePanel.DateUtility;

public class DateUtilityDemo { 

   public static void main(String args[]) {

      System.out.println( "\t Date Utility Demonstration \n");

      System.out.println( "\nDays in Jan 1900 is " + 
                          DateUtility.daysThisMonthIs( 1900, 1) + 
                          ".");
      System.out.println( "First day of Jan 1900 is " + 
                          DateUtility.firstDayOfMonthIs( 1900, 1) + 
                          ".");                          

      System.out.println( "\nDays in Jly 1996 is " + 
                          DateUtility.daysThisMonthIs( 1996, 7) + 
                          ".");
      System.out.println( "First day of Jly 1996 is " + 
                          DateUtility.firstDayOfMonthIs( 1996, 7) + 
                          "."); 
                          
      System.out.println( "\nDays in Jan 1996 is " + 
                          DateUtility.daysThisMonthIs( 1996, 1) + 
                          ".");
      System.out.println( "First day of Jan 1996 is " + 
                          DateUtility.firstDayOfMonthIs( 1996, 1) + 
                          "."); 
                          
      System.out.println( "\nDays in Feb 1996 is " + 
                          DateUtility.daysThisMonthIs( 1996, 2) + 
                          ".");
      System.out.println( "First day of Feb 1996 is " + 
                          DateUtility.firstDayOfMonthIs( 1996, 2) + 
                          ".");  
                          
      System.out.println( "\nDays in Feb 1995 is " + 
                          DateUtility.daysThisMonthIs( 1995, 2) + 
                          ".");
      System.out.println( "First day of Feb 1995 is " + 
                          DateUtility.firstDayOfMonthIs( 1995, 2) + 
                          ".");  
                          
     System.out.println( "\nDays in Jan 1901 is " + 
                          DateUtility.daysThisMonthIs( 1901, 1) + 
                          ".");
     System.out.println( "First day of Jan 1901 is " + 
                          DateUtility.firstDayOfMonthIs( 1901, 1) + 
                          ".");                             

     System.out.println( "\nThis year is " + 
                          DateUtility.yearIs() + ".");                          
     System.out.println( "\nThis month is " + 
                          DateUtility.monthIs() + ".");  
     System.out.println( "\nThis date is " + 
                          DateUtility.dayOfMonthIs() + ".");                                                                                                                                                             
   } // End main.
} // End class DateUtilityDemo;

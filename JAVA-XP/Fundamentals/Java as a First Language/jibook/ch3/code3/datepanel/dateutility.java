// Filename DateUtility.java.
// Utility class containing static actions to return
// the number of days in a month and the day of the week. 
//
// Written for JI book chapter 3.
// Fintan Culwin, v 0.2, August 1997. 

package DatePanel;


import java.util.Calendar;
import java.util.GregorianCalendar;


final class DateUtility extends Object {

   // Return the number of days in the month supplied.
   public static int daysThisMonthIs( int thisYear, int thisMonth) { 
   
   int localDays;
      
      switch ( thisMonth) { 
         case 4:
         case 6:
         case 9:
         case 11:
           localDays = 30;
           break; 
         case 2:
           if ( isLeapYear( thisYear)) { 
              localDays = 29;
           } else { 
              localDays = 28;
           } // end if
           break;
      default:
           localDays = 31;
      } // end switch
      return localDays;
   } // End daysPerMonthIs.
    

   private static boolean isLeapYear( int thisYear) { 
      return ( ((thisYear % 4) == 0) && (thisYear != 1900));
   } // End isLeapYear.


   // Return the day of the week for the first day of the date
   // given, with 0 indicating Sunday, using Zeller's congruence. 
   public static int dayOfWeekIs( int thisYear, 
                                  int thisMonth,
                                  int thisDay) {
      
   int thisCentury;
   int zellers;   
       
      if ( thisMonth < 3) { 
         thisYear--;
         thisMonth += 10;
      } else { 
         thisMonth -= 2;
      } // End if.
      thisCentury =  thisYear/100;
      thisYear    %= 100;   
      
      zellers = (( (26 * thisMonth -2)/10  + thisDay +
                   thisYear + thisYear/4 + 
                   thisCentury /4 - 2*thisCentury )%7);       
      if ( zellers < 0) { 
        zellers += 7;
      } 
      return  zellers %7;
   } // End dayOfWeekIs.


   public static int firstDayOfMonthIs( int thisYear, 
                                        int thisMonth) {
      return dayOfWeekIs( thisYear, thisMonth, 1);                           
   } // End firstDayOfMonthIs.                                 


   public static int yearIs(){ 
      return new GregorianCalendar().get( Calendar.YEAR);
   } // End yearIs.
   
   public static int monthIs(){ 
      return new GregorianCalendar().get( Calendar.MONTH) +1;
   } // End monthIs.
   
   public static int dayOfMonthIs() { 
      return new GregorianCalendar().get( Calendar.DATE);
   } // End dayOfMonthIs.   

} // End class DateUtility.

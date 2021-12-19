// Filename BasicJulianDate.java.
// Base of the JulianDate hierarchy providing the 
// essential functionality.
//
// Written for JFL Book Chapter 10.
// Fintan Culwin, V 0.1, Jan 1997. 

package JulianDates;

import java.util.Date;
import JulianDates.JulianDateException;

class BasicJulianDate { 

protected int dayNumber;

private static final int DAYS_PER_YEAR      = 365;
private static final int DAYS_PER_LEAP_YEAR = 366;

private static final int MINIMUM_YEAR       = 1900;
private static final int MAXIMUM_YEAR       = 2199;
private static final int MINIMUM_MONTH      = 1;
private static final int MAXIMUM_MONTH      = 12;
private static final int MINIMUM_DAY        = 1;

private static final String DAY_NAMES[]   = { 
                                      "Sat", "Sun", "Mon", "Tue",  
                                      "Wed", "Thu", "Fri"  };

private static final String MONTH_NAMES[] = { "" ,
                                      "Jan", "Feb", "Mar", "Apr",
                                      "May", "Jun", "July", "Aug",
                                      "Sep", "Oct", "Nov", "Dec" };
                                      
protected static final int NULL_JULIAN_DATE     = 0;
protected static final int MINIMIUM_JULIAN_DATE = 1;                                      
protected static final int MAXIMIUM_JULIAN_DATE = 109573;

   // Default constructor - set to todays date!
   public BasicJulianDate(){ 
      super();
   } // end JulianDate default constructor


   // Alternative constructor - to construct specific date.
   // Will throw exception if invalid date requested. 
   public BasicJulianDate( int Year, int Month, int Day )throws JulianDateException { 
       makeJulianDate( Year, Month, Day);
   } // end JulianDate alternative default constructor


   // Make a JulianDate value from yyyy/mm/dd value. 
   // 1900 <= yyyy <= 2199, 1<= mm <= 12, 1<= dd <= 31.
   protected void makeJulianDate( int year, int month, int day ) { 

  
   int localDayNumber   = 0;
   int thisMonth; 

      if ( ! isValidDate( year, month, day )) { 
        throw new JulianDateException( 
                        JulianDateException.DATE_CONSTRUCTION_ERROR); 
      } // End if. 

      
      // Start with the day of the month
      localDayNumber = day;

      // Add the number of days for the month.
      for ( thisMonth = 1; thisMonth < month; thisMonth++) { 
         localDayNumber += daysThisMonth( year, thisMonth);
      } 

       // Add the number of days for the year (ignoring leap years).
      localDayNumber += (year - MINIMUM_YEAR)* DAYS_PER_YEAR; 
   
      // Correct for the number of leap years passed. 
      localDayNumber = localDayNumber + 
                       ((year - MINIMUM_YEAR) /4) - 
                       ((year - MINIMUM_YEAR) /100);
                       
      if ( year > 2000) { 
         localDayNumber++;
      } 
              
      this.dayNumber = localDayNumber;
   } // end fun MakeJulianDate


   // Set the date to the current system date. 
   public void today(){ 

   Date theSystemDate = new java.util.Date();
   int  theYear       = theSystemDate.getYear()  + 1900;
   int  theMonth      = theSystemDate.getMonth() +1;
   int  theDay        = theSystemDate.getDate();
   
      makeJulianDate( theYear, theMonth, theDay);
   }  // end fun today.



  // Enquiry functions

   public int yearIs() throws JulianDateException { 
   
   int daysThisYear = DAYS_PER_YEAR; // 1900 is not a leap year!
   int daysRemining = this.dayNumber;
   int localYear    = MINIMUM_YEAR;

      if ( this.dayNumber == NULL_JULIAN_DATE) { 
         throw new JulianDateException( JulianDateException.NULL_DATE);
      } // End if.
      
      while ( daysRemining > daysThisYear ) {
         daysRemining -= daysThisYear; 
         localYear++;
         if ( isLeapYear( localYear)){ 
            daysThisYear = DAYS_PER_LEAP_YEAR;
         } else { 
            daysThisYear = DAYS_PER_YEAR;
         } // End if.      
      } // End while;     
      return localYear;
   } // End YearIs.


   public int monthIs() throws JulianDateException { 

   int  daysRemining = this.dayNumber - daysToThisYear();
   int  localYear    = yearIs();
   int  localMonth   = 1;
   
      if ( this.dayNumber == NULL_JULIAN_DATE) { 
         throw new JulianDateException( JulianDateException.NULL_DATE);
      } // End if.  
      
      while ( daysRemining > daysThisMonth( localYear, localMonth)) {       
         daysRemining -= daysThisMonth( localYear, localMonth);
         localMonth++;
      }               
      return localMonth;
   } // End MonthIs.


   public int dayIs() throws JulianDateException { 
      if ( this.dayNumber == NULL_JULIAN_DATE) { 
         throw new JulianDateException( JulianDateException.NULL_DATE);
      } // End if.   
      return this.dayNumber - daysToThisYearAndMonth();
   } // End dayIs.


   private String dayNameIs(){ 
      return DAY_NAMES[ this.dayNumber % 7];
   } // End DayNameIs.
   
   protected int dayNumberIs() { 
     return this.dayNumber;
   } // End dayNumberIs.
   

///////////////////////////////////////////////////////////
// Private utility functions.                            //
///////////////////////////////////////////////////////////


   private int daysToThisYearAndMonth(){ 
   
   int daysElapsed  = daysToThisYear();
   int daysRemining = this.dayNumber - daysElapsed;
   int localYear    = yearIs();
   int localMonth   = 1;

      while ( daysRemining > daysThisMonth( localYear, localMonth)) { 
         daysRemining -= daysThisMonth( localYear, localMonth);
         daysElapsed  += daysThisMonth( localYear, localMonth);
         localMonth++;
      }
      return daysElapsed;
   } // End daysToThisYearAndMonth.


   private int daysToThisYear(){ 

   int localYears   = yearIs() - MINIMUM_YEAR ;
   int daysElapsed  = 0;
 
      daysElapsed =  ( (localYears  * DAYS_PER_YEAR) + 
                       (localYears  /4)              - 
                       (localYears /100)             );                    
      if ( localYears > 100) { 
        daysElapsed++;
      } // 

      return daysElapsed;                 
   } // End daysToThisYear.


   private int daysThisMonth( int thisYear, int thisMonth ) { 

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
   } // End daysThisMonth.


   private boolean isLeapYear( int thisYear) { 
      return ( ((thisYear % 4) == 0) && (thisYear != 1900));
   } // End isLeapYear.


   protected boolean isValidDate( int year, int month, int day ) { 
   
   boolean isGoodYear  = year  >= MINIMUM_YEAR &&
                         year  <= MAXIMUM_YEAR;
   boolean isGoodMonth = month >= MINIMUM_MONTH && 
                         month <= MAXIMUM_MONTH;
   boolean isGoodDay   = false; 

      if ( isGoodYear && isGoodMonth) {     
         isGoodDay = day >= MINIMUM_DAY && 
                     day <= daysThisMonth( year, month); 
      } // End if. 
      return isGoodYear && isGoodMonth && isGoodDay; 
   } // End isValidDate.


   public String toString(){ 
     if ( this.dayNumber == NULL_JULIAN_DATE ) { 
        return "Date not yet set.";
     } else { 
         return   this.dayNameIs() + " " + this.MONTH_NAMES[ monthIs()] + 
                  "  " + this.dayIs()   + " " + this.yearIs();
     } // End if.
   } // End toString.


} // End class BasicJulianDate. 




// Filename ArithmeticJulianDate.java.
// First extension of the JulianDate hierarchy 
// to provide (pseudo)-arithmetic operations.
//
// Written for JFL Book Chapter 10.
// Fintan Culwin, V 0.1, Jan 1997. 

package JulianDates;

import JulianDates.JulianDateException;

public class ArithmeticJulianDate extends BasicJulianDate {

 
   public ArithmeticJulianDate(){ 
      super();
   } // end JulianDate default constructor
   
   public ArithmeticJulianDate( int year, int month, int day) 
                                    throws JulianDateException {
       super( year, month, day);
   } // end JulianDate alternative default constructor

   
   public void tomorrow() throws JulianDateException { 
      this.add( 1);
   } // End tomorrow. 

   public void yesterday() throws JulianDateException { 
      this.add( -1);
   } // End yesterday. 

   public void daysHence( int toElapse) throws JulianDateException { 
      this.add( toElapse);   
   } // End daysHence.
   
   public void daysPast( int hasPassed) throws JulianDateException { 
      this.add( -hasPassed);   
   } // End daysPast.
   
   public long daysBetween( ArithmeticJulianDate since) { 
      return this.dayNumberIs() - since.dayNumberIs();
   } // End equals.
   
   public boolean equals( ArithmeticJulianDate toCompare) { 
      return this.dayNumberIs() == toCompare.dayNumberIs();
   } // End equals.
   
   public boolean isLaterThan( ArithmeticJulianDate toCompare) { 
      return this.dayNumberIs() > toCompare.dayNumberIs();
   } // End isLaterThan.   

   public boolean isEarlierThan( ArithmeticJulianDate toCompare) { 
      return this.dayNumberIs() < toCompare.dayNumberIs();
   } // End isEarierThan.
   
   private void add( int toAdd) { 
      if ( (this.dayNumberIs() + toAdd >  MAXIMIUM_JULIAN_DATE) ||
           (this.dayNumberIs() + toAdd <  MINIMIUM_JULIAN_DATE)   ) { 
        throw new JulianDateException( 
                        JulianDateException.ARITHMETIC_ERROR);          
      } // End if.
      this.dayNumber += toAdd; 
   } // end add. 

} // End AritmeticJulianDate.

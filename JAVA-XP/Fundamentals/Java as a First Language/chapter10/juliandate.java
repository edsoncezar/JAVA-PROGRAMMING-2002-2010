// Filename JulianDate.java.
// Second extension of the JulianDate hierarchy 
// to provide internationalised i/o operations.
//
// Written for JFL Book Chapter 10.
// Fintan Culwin, V 0.1, Jan 1997. 

package JulianDates;

import java.io.DataInputStream;
import java.io.IOException;
import JulianDates.JulianDateException;
import OutputFormatter;
import OutputFormatException;


public class JulianDate extends ArithmeticJulianDate { 

public static final int EUROPEAN  = 0;
public static final int AMERICAN  = 1;

private int theLocale = EUROPEAN;

private static java.io.DataInputStream keyboard = 
                       new java.io.DataInputStream( System.in);
                       
   public JulianDate(){ 
      super();
   } // end JulianDate default constructor.
   
   public JulianDate( int locale){ 
      super();
      theLocale = locale;
   } // end JulianDate default constructor.   

   public JulianDate( int year, int month, int day, 
                      int locale) throws JulianDateException {
       super( year, month, day);
       theLocale = locale;
   } // end JulianDate alternative default constructor


   public void readDate() throws JulianDateException { 
   
   String fromKeyboard;
   int    firstField;
   int    secondField;
   int    yearField;
   int    startOfField;
   int    endOfField;
   char   separator = '/';
   
   
      System.out.flush();
      try { 
         fromKeyboard = keyboard.readLine().trim();
         endOfField = fromKeyboard.indexOf( separator);
         if ( endOfField != -1) { 
           firstField = Integer.valueOf( fromKeyboard.
                                  substring( 0, endOfField)).intValue(); 
         } else { 
           throw new java.lang.Exception();
         }
          
         startOfField = endOfField + 1;
         endOfField   = fromKeyboard.lastIndexOf( separator);
         if ( endOfField != -1) { 
           secondField = Integer.valueOf( fromKeyboard.
                                  substring( startOfField, 
                                             endOfField)).intValue(); 
         } else { 
           throw new java.lang.Exception();
         } 
         
         startOfField = endOfField + 1;
         yearField   = Integer.valueOf( fromKeyboard.
                                  substring( startOfField, 
                                             fromKeyboard.length())).intValue(); 

         if ( theLocale == EUROPEAN) { 
            makeJulianDate( yearField, secondField, firstField );
         } else { 
            makeJulianDate( yearField, firstField, secondField );
         }                
      } catch ( java.lang.Exception exception ) { 
          throw new  JulianDateException( 
                        JulianDateException.DATE_INPUT_ERROR);      
      }        
      
   
   } // End readDate.   

   public String sparseString() { 
   
   String theYear  = OutputFormatter.formatLong( 
                                this.yearIs(), 4, true,
                                OutputFormatter.DECIMAL );
   String theMonth = OutputFormatter.formatLong( 
                                this.monthIs(), 2, true,
                                OutputFormatter.DECIMAL );
   String theDay   = OutputFormatter.formatLong( 
                                this.dayIs(), 2, true,
                                OutputFormatter.DECIMAL );                                                                
     
      if ( theLocale == EUROPEAN){
         return theDay + "/" + theMonth  + "/" +  theYear;
      } else { 
         return theMonth + "/" + theDay  + "/" +  theYear;
      }   
   } // End sparseString.

   
} // End JulianDate class.


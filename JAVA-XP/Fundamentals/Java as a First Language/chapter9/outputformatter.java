// Filename OutputFormatter.java.
// Provides facilities for the formatting of numbers.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, January 1997.

import OutputFormatException;


public class  OutputFormatter { 

public  static final int  BINARY  = 2;
public  static final int  OCTAL   = 8;
public  static final int  DECIMAL = 10;
public  static final int  HEX     = 16;

private static final char SPACE = ' ';
private static final char ZERO  = '0';

   public static String formatLong( long    aNumber,
                                    int     toWidth,
                                    boolean withZeros,
                                    int     inBase) { 
   
   StringBuffer formatString;
   char         padding;
   
      if ( (inBase < Character.MIN_RADIX) ||
           (inBase > Character.MAX_RADIX) ){ 
        throw new OutputFormatException( OutputFormatException.INVALID_BASE);   
      } // End if.     
   
      if ( withZeros) { 
         padding = ZERO;
      } else { 
         padding = SPACE;
      } // End if.                                
       
      formatString = new StringBuffer( Long.toString( aNumber, inBase));
      
      while ( formatString.length() <  toWidth) { 
         formatString.insert( 0, padding);
      } // End while.
    
      return formatString.toString();                          
   } // End formatLong.                             



   public static String formatFloat( float  aNumber,
                                     int     foreWidth,
                                     int     aftWidth,
                                     boolean withZeros){ 
   String foreString;
   StringBuffer aftString;
   double wholePart   = Math.floor( aNumber);
   double decimalPart = aNumber - wholePart;
   
       if ( ( ((long) aNumber) < Float.MIN_VALUE) ||
            ( ((long) aNumber) > Float.MAX_VALUE) ){ 
          throw new OutputFormatException( OutputFormatException.FP_TOO_LARGE);  
       } // End if.
               
        if ( aftWidth < 1) { 
           aftWidth =1;
        } // End if. 
  
       foreString = formatLong( (long) wholePart, foreWidth, withZeros, DECIMAL); 
   
       decimalPart *= Math.pow( 10,aftWidth);
       decimalPart =  Math.round( decimalPart);
       
       aftString = new StringBuffer( Long.toString( (long) decimalPart));
       if ( aftString.length() > aftWidth) { 
          aftString.setLength( aftWidth);
       } else { 
          while ( aftString.length() <  aftWidth) { 
            aftString.append( ZERO);
          } // End while.
       } // End if.
                           
      return foreString + "." + aftString;                          
   } // End formatDouble.       
} // End NumberFormatter.

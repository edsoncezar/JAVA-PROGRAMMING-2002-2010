// Filename QuarterlySales.java.
// Illustrates the construction and use of a 
// two dimensional array.
//
// Written for JFL Book Chapter 13.
// Fintan Culwin, V 0.1, Jan 1997.

import java.util.Random;
import java.util.Date;

class QuarterlySales {  

private static int NUMBER_OF_OFFICES  = 3;
private static int NUMBER_OF_QUARTERS = 4;

private int sales[][] = 
            new int [ NUMBER_OF_OFFICES] [ NUMBER_OF_QUARTERS];

private  Random generator = new Random( new Date().getTime()); 

   public QuarterlySales() { 
   
   int thisOffice, thisQuarter;
   
      for( thisOffice =0; 
           thisOffice < NUMBER_OF_OFFICES; 
           thisOffice ++) { 
         for( thisQuarter =0; 
              thisQuarter < NUMBER_OF_QUARTERS; 
              thisQuarter ++) { 
            sales[ thisOffice] [ thisQuarter] = 2000 + 
                                (int) (generator.nextDouble() * 5000.0);
         } // End for thisQuarter.
      } // End for thisOffice.
   } // End QuarterlySales default constructor.


   public String toString() {  
   
   StringBuffer    theBuffer = new StringBuffer();
   int thisOffice, thisQuarter;
   
      for( thisOffice =0; 
           thisOffice < NUMBER_OF_OFFICES; 
           thisOffice ++) { 
         for( thisQuarter =0; 
              thisQuarter < NUMBER_OF_QUARTERS; 
              thisQuarter ++) {
            theBuffer.append( Integer.toString( 
                                 sales[ thisOffice] [ thisQuarter]) + 
                                 "    "); 
         } // End for thisQuarter.
         theBuffer.append("\n");
      } // End for thisOffice.   
      return theBuffer.toString();
   } // End toString.   
   
} // End class QuarterlySales;

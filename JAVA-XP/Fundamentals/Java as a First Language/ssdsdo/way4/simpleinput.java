// Filename SimpleInput.java.
// Provides simple input routines for primitive values.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, Jan 1997.
// This version January 1999.

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

abstract class SimpleInput extends Object {
 
private static java.io.BufferedReader keyboard 
                       = new java.io.BufferedReader( 
                                  new InputStreamReader(System.in));


   protected static long getLong() throws IOException { 

   
   long   localLong =0;
   String  tempString;
   Long    tempLong;
     
      System.out.flush();
      try { 
         tempString = keyboard.readLine();
         tempString.trim();
         tempLong = Long.valueOf( tempString);
         localLong = tempLong.longValue();
 
//         The four lines abouve could be replaced with:
//         localLong =  Long.valueOf( keyboard.readLine().trim()
//                                                 ).longValue(); 
      } catch ( java.lang.Exception exception){ 
         throw new java.io.IOException();
      } // End try/ catch;
      return localLong;
   } // end getLong.


   protected static double getDouble() throws IOException { 
   
   double localDouble = 0.0;
     
      System.out.flush();
      try {  
         localDouble =  Double.valueOf( keyboard.readLine().trim()
                                                   ).doubleValue(); 
      } catch ( java.lang.Exception exception){ 
         throw new java.io.IOException();
      } // End try/ catch;
      return localDouble;
   } // end getDouble.                                 

} // End SimpleInput.


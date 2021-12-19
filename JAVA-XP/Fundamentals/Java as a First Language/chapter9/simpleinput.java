// Filename SimpleInput.java.
// Provides simple input routines for primitive values.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, Jan 1997.

import java.io.DataInputStream;
import java.io.IOException;

abstract class SimpleInput extends Object {
 
private static java.io.DataInputStream keyboard = new java.io.DataInputStream( System.in);
   
//   protected SimpleInput()  {
//      super(); 
//      keyboard = new java.io.DataInputStream( System.in);
//   } // Enc constructor. 


   protected static long readLong() throws IOException { 

   
   long   localLong =0;
   String  tempString;
   Long    tempLong;
     
      System.out.flush();
      try { 
         tempString = keyboard.readLine();
         tempString.trim();
         tempLong = Long.valueOf( tempString);
         localLong = tempLong.longValue();
 
//         localLong =  Long.valueOf( keyboard.readLine().trim()
//                                                 ).longValue(); 
      } catch ( java.lang.Exception exception){ 
         throw new java.io.IOException();
      } // End try/ catch;
      return localLong;
   } // end readLong.


   protected double readDouble() throws IOException { 
   
   double localDouble = 0.0;
     
      System.out.flush();
      try {  
         localDouble =  Double.valueOf( keyboard.readLine().trim()
                                                   ).doubleValue(); 
      } catch ( java.lang.Exception exception){ 
         throw new java.io.IOException();
      } // End try/ catch;
      return localDouble;
   } // end readDouble.                                 

} // End SimpleInput.


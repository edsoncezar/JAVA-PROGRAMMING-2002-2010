// Filename ValidatedInput.java.
// Provides validated input routines for primitive values.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, Jan 1997.
// This version January 1999.

import SimpleInput;

public class ValidatedInput extends SimpleInput {

   public static long readLong( String prompt,
                                long   minimumAcceptable,
                                long   maximumAcceptable){
   
   long    localLong  = 0;
   boolean inputNotOK = true;
   
      if ( prompt == null ) { 
         prompt = new String( "Please enter an integer :");
      } // End if. 
         
      while ( inputNotOK ) { 
         System.out.print( prompt);
         try {  
            localLong  = getLong();
            if ( (localLong < minimumAcceptable) ||
                 (localLong > maximumAcceptable) ){
              throw new  java.io.IOException();
            } else {      
              inputNotOK = false;
            } // End if. 
         } catch ( java.io.IOException exception) {
              System.out.println( "Please enter a value between " + 
                                  minimumAcceptable + " and "     + 
                                  maximumAcceptable + ".");              
         } // End try/ catch;
      } // End while;
      return localLong;
   } // End readLong.


   public static double readDouble( String prompt,
                                    double minimumAcceptable,
                                    double maximumAcceptable){
   
   double  localDouble = 0;
   boolean inputNotOK  = true;
   
      if ( prompt == null ) { 
         prompt = new String( "Please enter an floating point value :");
      } // End if. 
         
      while ( inputNotOK ) { 
         System.out.print( prompt);
         try {  
            localDouble  = getDouble();
            if ( (localDouble < minimumAcceptable) ||
                 (localDouble > maximumAcceptable) ){
              throw new  java.io.IOException();
            } else {      
              inputNotOK = false;
            } // End if. 
         } catch ( java.io.IOException exception) {
              System.out.println( "Please enter a value between " + 
                                  minimumAcceptable + " and "     + 
                                  maximumAcceptable + ".");              
         } // End try/ catch;
      } // End while;
      return localDouble;
   } // End readDouble.

} // End ValidatedInput.

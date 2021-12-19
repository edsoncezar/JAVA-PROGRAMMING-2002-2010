// Filename ValidatedInput.java.
// Provides validated input routines for primitive values.
//
// Produced for JFL book Chapter 9.
// Fintan Culwin, v0.1, Jan 1997.

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
            localLong  = readLong();
            if ( (localLong < minimumAcceptable) ||
                 (localLong > maximumAcceptable) ){
              throw new  java.io.IOException();
            } else {      
              inputNotOK = false;
            } 
         } catch ( java.io.IOException exception) {
              System.out.println( "Please enter a value between " + 
                                  minimumAcceptable + " and "     + 
                                  maximumAcceptable + ".");              
         } // End try/ catch;
      } // End while;
      return localLong;
   } // End readLong.


} // End ValidatedInput.

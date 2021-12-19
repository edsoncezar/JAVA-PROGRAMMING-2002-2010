// Filename PlayTimeTerminalInput.java.
// Input details of a PlayTime from the terminal.
//
// Produced for ssd/sdo 99/00 
// Fintan Culwin, v0.1, January 2000.

package playtime;

import ValidatedInput;

public class PlayTimeTerminalInput extends Object { 

   public static PlayTime readPlayTime( String prompt) { 

   int numberOfMins    = 0;
   int numberOfSeconds = 0; 
   int numberOfTenths  = 0;

   int totalNumberOfTenths = 0;

      if ( prompt.length() > 0 ) { 
         System.out.println( prompt);
      } // End if

      numberOfMins = (int) ValidatedInput.readLong( 
                             "Please enterThe number of mins   : ",
                             0, Integer.MAX_VALUE);  
      numberOfSeconds = (int) ValidatedInput.readLong( 
                             "Please enterThe number of secs   : ",
                             0, 59);      
      
      numberOfTenths = (int) ValidatedInput.readLong( 
                             "Please enterThe number of tenths : ",
                             0, 9);     
      totalNumberOfTenths = numberOfTenths + 
                            (numberOfSeconds * 10) + 
                            (numberOfMins    * 600); 
      return new PlayTime( totalNumberOfTenths);
   } // End readPlayTime

} // End PlayTimeTerminalInput.

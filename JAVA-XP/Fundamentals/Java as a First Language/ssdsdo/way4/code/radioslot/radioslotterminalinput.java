// Filename RadioSlotTerminalInput.java.
// Input details of a eadioSlot from the terminal.
//
// Produced for ssd/sdo 99/00 
// Fintan Culwin, v0.1, January 2000.

package radioslot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import playtime.*;
import ManifestInput;

public class RadioSlotTerminalInput extends Object { 


private static final String types[] = { "unknown", "music",
                                "advert", "news", "weather"};

private static ManifestInput typeInput = new ManifestInput( types); 

private static java.io.BufferedReader keyboard 
                       = new java.io.BufferedReader( 
                                  new InputStreamReader(System.in));


   public static RadioSlot readRadioSlot( String prompt) { 

   int      slotType        = RadioSlot.UNKNOWN;
   String   slotDescription = "";
   PlayTime slotStartTime   = null;
   PlayTime slotDuration    = null;

   RadioSlot obtained       = null;
   
      if ( prompt.length() > 0 ) { 
         System.out.println( prompt);
      } // End if

      slotType = typeInput.readManifest( 
                                 "Please enter the slot type  : ");
      try { 
         System.out.print( "Please enter the slot description  : ");
         slotDescription = keyboard.readLine().trim();
      } catch ( IOException exception) { 
         slotDescription = "????";
      } // End try/catch.

      slotDuration  = PlayTimeTerminalInput.readPlayTime( 
                                 "Please enter the duration : ");


      slotStartTime = PlayTimeTerminalInput.readPlayTime( 
                                 "Please enter the start time   : ");


      obtained = new RadioSlot( slotType, slotDescription, 
                                slotDuration);
      obtained.setSlotStartTime( slotStartTime); 

      return obtained;
   } // End readRadioSlot

} // End class RadioSlotTerminalInput





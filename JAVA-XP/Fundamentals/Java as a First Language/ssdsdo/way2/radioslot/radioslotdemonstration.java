// Filename playtime\BasicTimeDemonstration.java
//
// Initial demo of the basicTime class 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package radioslot;

import playtime.PlayTime;

public class RadioSlotDemonstration extends Object { 

   public static void main( String[] args) { 

   RadioSlot demoSlot      = null;
   PlayTime  thirtySeconds = new PlayTime( 300);   
   PlayTime  fourMinutes   = new PlayTime( 2400);
  

      System.out.println( "\n\nRadio Slot Demonstration\n\n");


      System.out.println( "Constructing an Advert for 'Sid's Used Car Lot' " + 
                          "\nwith a duration of 30.0 seconds starting at   " + 
                          "\n4 min 00.0 seconds");

      demoSlot = new RadioSlot( RadioSlot.ADVERT,
                                "Sid's Used Car Lot", 
                                thirtySeconds);
      demoSlot.setSlotStartTime( fourMinutes);
      
      System.out.println( "\n... constructed showing ...\n"); 

      System.out.println( demoSlot);  

      System.out.println( "Constructing an instance which should" + 
                          "\nthrow an exception!");

      demoSlot = new RadioSlot( -1, null, null);

      System.out.println( "If this message appears" + 
                          "\nthe class is broken!!!!");   
   } // End main. 

} // End class PlayTimeDemonstration.



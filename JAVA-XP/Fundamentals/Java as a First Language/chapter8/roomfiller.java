// Filename RoomFiller.java.
// Providing a ??????.
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997

import Counters.RoomMonitor;
import java.util.Random;

public class RoomFiller extends Thread { 

RoomMonitor  roomToFill;
Random       generator = new Random();

   public RoomFiller( RoomMonitor theMonitor ) { 
      roomToFill = theMonitor; 
   } // End RoomFiller onstructor;

   public void run() {
   
   int delay;
   
       while ( true ) { 
          try {
             delay = (int) (generator.nextDouble() * 200.0);
             this.sleep( delay);
          } catch ( InterruptedException exception ){ 
            // Do nothing.
          }    
          roomToFill.enterRoom();
          System.out.println( "Entered ...");
       }    
   } 

} 

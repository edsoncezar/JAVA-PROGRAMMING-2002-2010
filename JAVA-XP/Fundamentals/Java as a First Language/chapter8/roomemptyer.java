// Filename RoomEmptyer.java.
// Providing a ??????.
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997

import Counters.RoomMonitor;
import java.util.Random;

public class RoomEmptyer extends Thread { 

RoomMonitor  roomToEmpty;
Random       generator = new Random();

   public RoomEmptyer( RoomMonitor theMonitor ) { 
      roomToEmpty = theMonitor; 
   } // End RoomFiller onstructor;

   public void run() {
   
   int    delay;
   double leaveRoom; 
   

       while ( true) { 
          try {
             delay = (int) (generator.nextDouble() * 200.0);
             this.sleep( delay);
          } catch ( InterruptedException exception ){ 
            // Do nothing.
          }    
          leaveRoom = generator.nextDouble() * 
                       (double)roomToEmpty.numberCurrentlyInRoomIs(); 
          if ( leaveRoom > 0.33 ) { 
             roomToEmpty.exitRoom();
             System.out.println( "Exited ...");
          } 
       }    
   } 

} 

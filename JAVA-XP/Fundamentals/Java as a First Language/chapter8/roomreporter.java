// Filename RoomReporter.java.
// Providing a ??????.
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997

import Counters.RoomMonitor;
import java.util.Random;

public class RoomReporter extends Thread { 

RoomMonitor  roomToReportOn;
Random       generator = new Random();

   public RoomReporter( RoomMonitor theMonitor ) { 
      roomToReportOn = theMonitor; 
   } // End RoomFiller onstructor;

   public void run() {
   

   int delay;
   
       while ( true)  { 
          try {
             delay = 800 + (int) (generator.nextDouble() * 800.0);
             this.sleep( delay);
          } catch ( InterruptedException exception ){ 
            // Do nothing.
          }    
          
          System.out.println( roomToReportOn);
       }    
   } 

} 

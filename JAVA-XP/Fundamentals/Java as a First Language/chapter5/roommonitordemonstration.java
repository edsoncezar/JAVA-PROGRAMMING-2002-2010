// Filename Counters/RoomMonitorDemonstration.java.
// Demonstration client for the RoomMonitor class.
//
// Written for JFL book Chapter 5 see text.
// Fintan Culwin, v0.1, January 1997.
// Solution to Exercise 5.1 provided by Payman Rezania.


package Counters;

import Counters.RoomMonitor;


public class RoomMonitorDemonstration { 


   public static void main( String argv[]) { 

   RoomMonitor  demoMonitor = new RoomMonitor(); 

	  System.out.println( "\n\t\t Room Monitor demonstration \n");

	  System.out.println( "The monitor has been created ...");

	  System.out.println( "\n Four people enter the room ... ");
	  demoMonitor.enterRoom();
	  demoMonitor.enterRoom(); 
	  demoMonitor.enterRoom();
	  demoMonitor.enterRoom();

	  System.out.println( "\n Two people leave the room ... ");
	  demoMonitor.exitRoom();    
	  demoMonitor.exitRoom();    

	  System.out.println( "\n One person enters the room ... ");
	  demoMonitor.enterRoom();

	  System.out.print( "The value of numberCurrentlyInRoomIs() should be 3 ..");
	  System.out.print( demoMonitor.numberCurrentlyInRoomIs());
	  System.out.println( "." );

	  System.out.print( "The value of maxEverInRoomIs() should be 4 ... ");
	  System.out.print( demoMonitor.maxEverInRoomIs());
	  System.out.println( "." );

	  System.out.print( "The value of totalNumberEnteredIs() should be 5 ... ");
	  System.out.print( demoMonitor.totalNumberEnteredIs());
	  System.out.println( "." );            

	  System.out.println( "\n Demonstrating the toString action ...");
	  System.out.println( demoMonitor);
	  
	  System.out.println( "\n Demonstrating the exception via the exitRoom() action");
	  demoMonitor.exitRoom();
	  demoMonitor.exitRoom();
	  demoMonitor.exitRoom();
	  System.out.println( "\n Demonstrating the toString action again...");
	  System.out.println( "Number of people in room should be 0...");
	  System.out.println( demoMonitor);	  
	  System.out.println( "Using the exitRoom() action one more time " + 
                              "will throw an exception...");
          try { 
	     demoMonitor.exitRoom();
             System.out.print( "No exception thrown ... which is wrong!!");
          } catch ( Counters.CounterException exception) { 
             System.out.print( "Exception thrown ... as expected!!");
          } // End try catch.
   } // End main               
} // End RoomMonitorDemonstration

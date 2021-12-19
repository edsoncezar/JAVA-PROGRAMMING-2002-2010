// Filename TrafficDemo.java.
// 
//
// Written for JFL book Chapter 8 see text.
// Fintan Culwin, v0.1, January 1997.

import TrafficLight;

public class TrafficDemo  { 

   public static void main( String argv[]) { 
   
   TrafficLight northSouth = new TrafficLight( "north south ");
   
   TrafficLight eastWest   = new TrafficLight( "east west ");   

      northSouth.setPeer( eastWest);
      eastWest.setPeer( northSouth);

 
      northSouth.start();  
      eastWest.start();

   
   } // End main.
   
} // End TrafficDemo.


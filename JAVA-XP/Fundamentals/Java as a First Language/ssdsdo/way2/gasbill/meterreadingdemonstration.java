// Filename gasbill\MeterReadingDemonstration.java
//
// Class to represent a single meter reading, 
// as an address and a reading value. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package gasbill;

public class MeterReadingDemonstration extends Object { 

   public static void main( String[] args) { 

   MeterReading thisReading = null;
   MeterReading thatReading = null;  
  

      System.out.println( "\n\nMeterReading Demonstration\n\n");

      System.out.println( "This demonstration will construct and display");
      System.out.println( "Two instances of the MeterReading class."); 

      System.out.println( "\nConstrucing ... \n");

      thisReading = new MeterReading( "10 Downing Street", 18203);
      thatReading = new MeterReading( "34A Smith Square", 92378);   


      System.out.println( "\nConstructed, demonstrating ... \n");

      System.out.println( "This address is " + thisReading.getAddress());
      System.out.println( "That address is " + thatReading.getAddress());

      System.out.println( "\n... demonstrating toString ... \n");
      System.out.println( "This is ..." + thisReading);
      System.out.println( "That is ..." + thatReading);
   } // End main. 

} // End class MeterReadingDemonstration.



// Filename gasbill\MeterReading.java
//
// Class to represent a single meter reading, 
// as an address and a reading value. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package gasbill;

public class MeterReading extends Object { 

private String address = "Unknown";
private int    reading = 0;

   public MeterReading( String theAddress, 
                        int    theReading) { 
      super();
      address = new String( theAddress);
      reading = theReading; 
   } // End MeterReading constructor.


   public String getAddress() { 
      return address;
   } // End The getAddress.

   public int getReading() { 
      return reading;
   } // End The getReading.  


   public String toString() { 
      return this.getAddress() + " reading " +
             this.getReading();
   } // End toString.

} // End MeterReading.

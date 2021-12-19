// Filename gasbill\GasBill.java
//
// An extension of the MeterReading class 
// adding a newreading attribute which allows 
// the number of units and cost of gas to 
// be calculated. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package gasbill;

public class GasBill extends MeterReading { 


private static final double UNIT_COST  = 0.07;
private                 int newReading = 0; 


   public GasBill( String theAddress, 
                   int    theReading) { 
      super( theAddress, theReading);
      newReading = theReading;
   } // End GasBill constructor.


   public void setNewReading( int theNewReading) 
                        throws RuntimeException { 
      if ( theNewReading < this.getReading()) { 
         throw new RuntimeException( "GasBill:" + 
                              "\nNewReading must be more than" + 
                              "\nor equal to original reading!");
      } else { 
         newReading = theNewReading;
      } // End if. 
   } // End setNewReading.

   public int getNewReading() { 
      return newReading;
   } // End getNewReading.  


   public int getUnitsUsed() { 
      return this.getNewReading() - this.getReading();
   } // End getUnitsUsed


   public double getCostOfGas() { 
      return this.getUnitsUsed() * this.getUnitCost();
   } // End getCostOfGas


   static public double getUnitCost() { 
      return UNIT_COST;
   } // End getUnitCost


   public String toString() { 
      return super.toString() + 
             " new reading " + this.getNewReading() + 
             "\nunits used  " + this.getUnitsUsed()  + 
             " : at " + this.getUnitCost() + " is : "   +
             this.getCostOfGas();
   } // End toString().

} // End class GasBill.

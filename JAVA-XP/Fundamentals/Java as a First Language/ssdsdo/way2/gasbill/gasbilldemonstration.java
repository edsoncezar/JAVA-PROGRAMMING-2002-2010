// Filename gasbill\GasBillDemonstration.java
//
// Class to demonstrate two instances of the GasBill 
// class, one of which throws an exception.
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

package gasbill;

public class GasBillDemonstration extends Object { 

   public static void main( String[] args) { 

   GasBill thisBill = null;
   GasBill thatBill = null;  
  

      System.out.println( "\n\nGasBill Demonstration\n\n");

      System.out.println( "This demonstration will construct and display"    +
                          "\nTwo instances of the GasBill class, the second" + 
                          "\ninstance will deliberately throw an exception!"); 

      System.out.println( "\nConstrucing first instance ... \n");
      thisBill = new GasBill( "10 Downing Street", 18203);

      System.out.println( "\nSetting new reading ... \n");
      thisBill.setNewReading( 19203);

      System.out.println( "\nDemonstrating inquiry methods ... \n");
      System.out.println( "Address is ... "     + thisBill.getAddress());
      System.out.println( "Reading is ... "     + thisBill.getReading());
      System.out.println( "New reading is ... " + thisBill.getNewReading());
      System.out.println( "Units used is ... "  + thisBill.getUnitsUsed());
      System.out.println( "Units cost is ... "  + thisBill.getUnitCost());
      System.out.println( "Cost of gas is ... " + thisBill.getCostOfGas());

      System.out.println( "\nShowing toString() ... \n");
      System.out.println( thisBill);

      System.out.println( "\n\nConstructing second instance ... \n");
      thatBill = new GasBill( "34A Smith Square", 92378);

      System.out.println( "\nSetting new reading with a value which should cause " + 
                          "\nan exception to be thrown, crashing the program!\n");
      thatBill.setNewReading( 19202);
      System.out.println( "\nIf you see this message the implementation of the\n" + 
                          "\nGasBill class if faulty!");

   } // End main. 

} // End class nGasBillDemonstration.



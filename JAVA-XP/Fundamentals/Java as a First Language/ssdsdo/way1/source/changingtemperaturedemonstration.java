// Filename ChangingTemperatureDemonstration.java
//
// Demonstration client for the ChangingTemperature
// class. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

public class ChangingTemperatureDemonstration extends Object { 

   public static void main( String[] args) { 

   ChangingTemperature freezing  = null;
   ChangingTemperature boiling   = null;  
  
      System.out.println( "\n\nChanging Temperature Demonstration\n\n");

      System.out.println( "\nConstrucing & showing two instances ... \n");
      freezing = new ChangingTemperature();
      boiling  = new ChangingTemperature( 100.0);
      System.out.println( "Freezing  is " + freezing);
      System.out.println( "Boiling   is " + boiling);  

      System.out.println( "\nWarming freezing & Cooling Boiling ... \n");
      freezing.warmer();
      boiling.cooler();
      System.out.println( "Freezing  is now " + freezing);
      System.out.println( "Boiling   is now " + boiling);  

   } // End main. 

} // End ChangingTemperatureDemonstration.

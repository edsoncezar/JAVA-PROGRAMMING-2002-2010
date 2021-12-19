// Filename TemperatureDemonstration.java
//
// Demonstration client for the Temperature
// class. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

public class TemperatureDemonstration extends Object { 

   public static void main( String[] args) { 

   Temperature freezing  = null;
   Temperature boiling   = null;  
   Temperature bodyHeat  = null;    

      System.out.println( "\n\nTemperature Demonstration\n\n");

      System.out.println( "This demonstration will construct and display");
      System.out.println( "Three different instances of the Temperature class."); 

      System.out.println( "\nConstrucing ... \n");

      freezing = new Temperature( 0.0);
      boiling  = new Temperature( 100.0);
      bodyHeat = new Temperature( 37.8);    

      System.out.println( "\nConstructed, showing ... \n");

      System.out.println( "Freezing  is " + freezing);
      System.out.println( "Boiling   is " + boiling);
      System.out.println( "Body Heat is " + bodyHeat);
   } // End main. 

} // End TemperatureDemonstration.

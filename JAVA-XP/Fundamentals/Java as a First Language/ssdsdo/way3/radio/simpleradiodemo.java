// Filename radio/SimpleRadioDemo.java.
//
// Demonstration of the SimpleRadio class. 
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class SimpleRadioDemo extends Object { 

   public static void main( String[] args) { 

   SimpleRadio aRadio = null;

      System.out.println( "\nSimple Radio Demonstration");

      System.out.println( "\nConstructing a radio .... ");
      aRadio = new SimpleRadio();

      System.out.println( "\nConstructed it should be off .... ");
      if ( aRadio.isSwitchedOff()) { 
         System.out.println( "Yes it is!");
      } else { 
         System.out.println( "ERROR - no it isn't!!");
      } // End if. 

      System.out.println( "\nSwitching on  .... ");
      aRadio.switchOn();

      System.out.println( "\nDemonstrating toString() " + 
                          "it should show it is switched on ...\n");
      System.out.println( aRadio);

      System.out.println( "\nEnd of Simple Radio Demonstration");
 
   } // End main. 

} // End SimpleRadioDemo

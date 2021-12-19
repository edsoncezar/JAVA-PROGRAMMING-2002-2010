// Filename radio/TuningRadioDemo.java.
//
// Demonstration of the TuningRadio class. 
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class TuningRadioDemo extends Object { 

   public static void main( String[] args) { 

   TuningRadio aRadio = null;

      System.out.println( "\nTuning Radio Demonstration");

      System.out.println( "\nConstructing a Radio & switching on .... ");
      aRadio = new TuningRadio();
      aRadio.switchOn();      

      System.out.println( "\nShowing its state ... ");
      System.out.println( aRadio);



      System.out.println( "\n\nEnd of Tuning Radio Demonstration");
 
   } // End main. 

} // End TuningRadioDemo

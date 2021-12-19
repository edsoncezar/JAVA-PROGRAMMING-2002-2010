// Filename radio/VolumeRadioDemo.java.
//
// Demonstration of the VolumeRadio class. 
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan Feb 2000


package radio;

public class VolumeRadioDemo extends Object { 

   public static void main( String[] args) { 

   VolumeRadio aRadio = null;

      System.out.println( "\nVolume Radio Demonstration");

      System.out.println( "\nConstructing a Radio & switching on .... ");
      aRadio = new VolumeRadio();
      aRadio.switchOn();      

      System.out.println( "\nConstructed the Volume should be 0 .... ");
      System.out.println( "The Volume is " + aRadio.getVolume());

      System.out.println( "\ncalling louder twice, the Volume should be 2");
      aRadio.louder(); 
      aRadio.louder(); 
      System.out.println( "The Volume is " + aRadio.getVolume());      

      System.out.println( "\nMuting the Volume and calling is silent");
      aRadio.mute();
      if ( aRadio.isSilent()) { 
         System.out.println( "The radio is silent, which is correct");
      } else { 
         System.out.println( "The radio is silent, which is NOT correct");
      } // End if.

      System.out.println( "\nMore demonstrations are needed!!\n");

      System.out.println( "\nEnd of Volume Radio Demonstration");
 
   } // End main. 

} // End VolumeRadioDemo

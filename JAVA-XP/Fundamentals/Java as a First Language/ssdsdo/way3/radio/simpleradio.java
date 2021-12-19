// Filename radio/SimpleRadio.java.
//
// A GasBoiler with an on/off & volume control.
//
// Produced for ssd way 3 sem 2 99/00
//
// Version 0.1 Fintan reb 2000


package radio;

public class SimpleRadio extends Object { 

private boolean onOrOff = false; 

   public SimpleRadio() { 
      super(); 
      onOrOff = false; // Switched off by default. 
   } // End GasBoiler
   
   public void switchOn() { 
      onOrOff = true;
   } // End switchOn

   public void switchOff() { 
      onOrOff = false;
   } // End switchOff

   public boolean isSwitchedOn() { 
      return onOrOff;
   } // End isSwitchedOn

   public boolean isSwitchedOff() { 
      return ! onOrOff;
   } // End isSwitchedOff

   public String toString() { 
      if ( this.isSwitchedOn()) { 
         return "The radio is switched on.";
      } else { 
         return "The radio is switched off.";
      } // End if. 
   } // End toString

} // End class SimpleRadio

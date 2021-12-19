// Filename ChangingTemperature.java
//
// Extends the temperature class to add
// additional methods. 
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

public class ChangingTemperature extends Temperature { 

   public ChangingTemperature() { 
      this( 0.0); 
   } // End ChangingTemperature default constructor.

   public ChangingTemperature( double setTo) { 
      super( setTo); 
   } // End ChangingTemperature alternative constructor.


   public void warmer() { 

   double currentTemperature = this.getTemp();

      currentTemperature += 0.1; 
      this.setTemp( currentTemperature); 
   } // End warmer. 

   public void cooler() { 
      this.setTemp( this.getTemp() - 0.1); 
   } // End cooler. 

} // End ChangingTemperature.


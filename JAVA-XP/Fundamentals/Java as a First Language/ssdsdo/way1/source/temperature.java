// Filename Temperature.java
//
// Class to represent a temperature in degrees 
// centigrade, accurate to 1/10 of a degree.
//
// Produced for waypoint 2 99/00.
//
// Fintan Culwin, v0.1, sept 99.

public class Temperature extends Object { 

private double theTemp = 0.0;

   public Temperature() { 
      this( 0.0); 
   } // End Temperature default constructor.

   public Temperature( double setTo) { 
      super(); 
      theTemp = setTo;
   } // End Temperature alternative constructor.


   public double getTemp() { 
      return theTemp;
   } // End The getTemp.

   protected void setTemp( double setTo) { 
      theTemp = setTo;
   } // End The setTemp.  


   public String toString() { 
      return theTemp + " degrees centigrade";
   } // End toString.

} // End Temperature.

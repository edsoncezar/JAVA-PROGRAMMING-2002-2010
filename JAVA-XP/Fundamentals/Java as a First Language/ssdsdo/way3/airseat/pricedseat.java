// filname PricededSeat.java
//
// adds destination and pricing capability 
// to the airseat hierarchy, 
//
// Produced for ssd waypoint 1 01/01 session 
//
// Fintan Culwin v 0.1 Nov 2001

package airseat;

public class PricedSeat extends BookedSeat { 

public final static int UNKNOWN = 0; 
public final static int PARIS   = 1;
public final static int BERLIN  = 2; 
public final static int PRAGUE  = 3; 
public final static int MOSCOW  = 4; 

private int toOrFrom = UNKNOWN;

   public PricedSeat( String rowAndSeat, int city) 
				 throws Exception {       
       super( rowAndSeat);
       toOrFrom = city;
   } // End PricedSeat constructor

   
   public double getBasePrice() { 
      return 100.0;
   } // End getBasePrice
   
   public double getAgePrice() { 
      return 100.0;
   } // End getAgePrice
   
   public double getTaxes() { 
      return 100.0;
   } // End getTaxes
      
   public double getPrice() { 
      return 100.0;
   } // End getPrice
   
   public String toString() { 
       return super.toString();     
   } // End toString    

} // End class PricedSeat


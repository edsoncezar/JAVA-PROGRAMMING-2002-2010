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

public final static double PARIS_PRICE  = 50.0; 
public final static double BERLIN_PRICE = 70.0; 
public final static double PRAGUE_PRICE = 80.0; 
public final static double MOSCOW_PRICE = 100.0; 

private int    toOrFrom = UNKNOWN;
private double basePrice = 0.0;

   public PricedSeat( String rowAndSeat, int city) 
				 throws Exception {  
       super( rowAndSeat);                                 
       
       if ( (city <  PARIS) ||
	    (city >  MOSCOW) ){ 
	  throw new Exception( "Priced Seat - unknown city!"); 
       } // End if. 

       toOrFrom = city;
       switch ( city) { 
       
       case PARIS  : basePrice = PARIS_PRICE;
		     break;
       case BERLIN : basePrice = BERLIN_PRICE;
		     break;
       case PRAGUE : basePrice = PRAGUE_PRICE;
		     break;
       case MOSCOW : basePrice = MOSCOW_PRICE;
		     break;                    
       } // End switch 
       
   } // End PricedSeat constructor

   
   public double getBasePrice() { 
      return basePrice;
   } // End getBasePrice
   
   public double getAgePrice() { 
      if ( this.isInfant() ) { 
	 return 0.0;
      } else if ( this.isChild() ) { 
	 return this.getBasePrice() * 0.50;
      } else if ( this.isYouth() ) { 
	 return this.getBasePrice() * 0.75;
      } else {  
	 return this.getBasePrice();
      } // end if. 
   } // End getAgePrice
   
   public double getTaxes() { 
      if ( this.isInfant() ) { 
	 return 0.0;
      } else if ( this.isChild() ) { 
	 return 10.00;
      } else {  
	 return 25.00;
      } // end if. 
   } // End getTaxes
      
   public double getPrice() { 
      return this.getAgePrice() + 
	     this.getTaxes();
   } // End getPrice
   
   public String toString() { 
       return super.toString() + "\n" + 
	      "base "    + this.getBasePrice() + 
	      " age "    + this.getAgePrice()  + 
	      " tax "    + this.getTaxes()     + 
	      " price "  + this.getPrice() ;   
   } // End toString    

} // End class PricedSeat


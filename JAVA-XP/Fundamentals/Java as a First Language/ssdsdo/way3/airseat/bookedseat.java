// filname BookedSeat.java
//
// frist extension of the airseat 
// hierarchy, contains booking, age & 
// vegetarian status of a seat.
//
// Produced for ssd waypoint 1 01/01 session 
//
// Fintan Culwin v 0.1 Nov 2001

package airseat;

public class BookedSeat extends AirlineSeat { 

// VArious manifest values. 
private final static int INFANT_LIMIT = 2; 
private final static int CHILD_LIMIT  = 16; 
private final static int YOUTH_LIMIT  = 26; 

private final static int UNKNOWN = 0;
private final static int INFANT  = 0;
private final static int CHILD   = 0;
private final static int YOUTH   = 0;
private final static int ADULT   = 0;
 
// Attributes. 
private boolean booked      = false; 
private int     ageType     = UNKNOWN; 
private boolean vegetarian  = false;

   public BookedSeat( String rowAndSeat) 
		       throws Exception {    
      super( rowAndSeat);                      
   } // End BookedSeat constructor.

   
   public void bookSeat( int age, boolean veg) 
			    throws Exception { 
      if ( age <  INFANT_LIMIT ) { 
	 ageType = INFANT; 
      } else { 
	 // ????????????????
      } // End if.    
      booked = true;                    
   } // end makeBooking  
   

    public void cancelBooking() { 
       booked = true;     
    } // End cancelBooking      
   
    
    public boolean isBooked() { 
       return true;     
    } // End isBooked    
    
    public boolean isInfant() { 
       return true;     
    } // End isInfant 
    
    public boolean isChild() { 
       return true;     
    } // End isChild 
    
    public boolean isYouth() { 
       return true;     
    } // End isYouth 
    
    public boolean isAdult() { 
       return true;     
    } // End isAdult
    
    public boolean isVegetarian() { 
       return true;     
    } // End isVegetarian
    
    public String toString() { 
       return super.toString();     
    } // End toString 
   
} // End class BookedSeat.

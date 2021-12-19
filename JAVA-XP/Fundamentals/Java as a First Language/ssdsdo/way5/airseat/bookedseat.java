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
private final static int INFANT  = 1;
private final static int CHILD   = 2;
private final static int YOUTH   = 3;
private final static int ADULT   = 4;
 
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
			    
      if ( this.isBooked()) { 
	 throw new Exception( "Seat already booked!");
      } // End if.   

      if ( age <  INFANT_LIMIT ) { 
	 ageType = INFANT; 
      } else if ( age <  CHILD_LIMIT ) { 
	ageType = CHILD;
      } else if ( age <  YOUTH_LIMIT ) { 
	ageType = YOUTH;
      } else { 
	ageType = ADULT;
      } // End if.    
      booked     = true;  
      vegetarian =  veg;           
   } // end bookSeat  
   

    public void cancelBooking() { 
       booked = false;     
    } // End cancelBooking      
   
    
    public boolean isBooked() { 
       return booked;     
    } // End isBooked    
    
    public boolean isInfant() { 
       return ageType == INFANT;
    } // End isInfant 
    
    public boolean isChild() { 
       return ageType == CHILD;        
    } // End isChild 
    
    public boolean isYouth() { 
       return ageType == YOUTH;
    } // End isYouth 
    
    public boolean isAdult() { 
       return ageType == ADULT;       
    } // End isAdult
    
    public boolean isVegetarian() { 
       return vegetarian;     
    } // End isVegetarian
    
    public String toString() { 
    StringBuffer buffer = new StringBuffer( super.toString());  
       if ( this.isBooked() ) { 
	  if ( this.isInfant() ) { 
	     buffer.append( " I ");
	  } else if ( this.isChild() ) { 
	     buffer.append( " C ");
	  } else if ( this.isYouth() ) { 
	     buffer.append( " Y ");
	  } else {  
	     buffer.append( " A ");
	  } // end if. 
	  
	  if ( this.isVegetarian() ) { 
	     buffer.append( " VEG ");
	  } // End if 
       
       } else { 
	  buffer.append( "VACANT");
       } // End if. 
       
       return buffer.toString();
    } // End toString 
   
} // End class BookedSeat.

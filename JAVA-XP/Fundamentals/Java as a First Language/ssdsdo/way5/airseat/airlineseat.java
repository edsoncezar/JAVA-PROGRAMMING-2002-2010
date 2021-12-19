// filname AirlineSeat.java
//
// Base of the airseat hierarchy, 
// contains essential details 
// of a seat - its row number 
// 1 to 26 and its seat letter 
// A to K 
//
// Produced for ssd waypoint 1 01/02 session 
// ADapted for sdo waypoint 5 01/02 session
//
// Fintan Culwin v 0.2 Feb 2002

package airseat; 

public class AirlineSeat extends Object { 

private int row   = 0; 
private char seat = ' '; 

private final static int MIN_ROW_NUMBER = 1; 
private final static int MAX_ROW_NUMBER = 26; 
private final static int MIN_SEAT_LETTER = 'A'; 
private final static int MAX_SEAT_LETTER = 'J'; 



   // This is a complex constructor which may throw an 
   // exception if the argument does not represent a valid 
   // row and seat number e.g. 5G or 37A. 
   // There is no default constructir as there is no obvious
   // or suitable default value. 
   public AirlineSeat( String rowAndSeat) 
		       throws Exception { 
   
      super();                      
   
      boolean seemsOk  = true; 
      int     rowPart  = 0; 
      char    seatPart = ' ' ; 
      
	 // Make sure it is 'A' to 'J' not 'a' to 'j'
	 rowAndSeat = rowAndSeat.toUpperCase();      
	  
	 // Valid arguments are 2 (e.g. 5G) or 3 (e.g. 37A)
	 // characters long. 
	 if ( (rowAndSeat.length() > 3) || 
	      (rowAndSeat.length() < 2) ){ 
	    seemsOk = false; 
	 } else { 
	    // The last character should be 'A' to 'J'
	    seatPart = rowAndSeat.charAt( rowAndSeat.length()-1); 
	    if ( (seatPart < MIN_SEAT_LETTER) || 
		 (seatPart > MAX_SEAT_LETTER) ){   
	       seemsOk = false; 
	    } // End if. 
	    
	    try {      
	       // Everything but the last character should be an integer
	       // this will throw a Exception if it isn't.
	       rowPart = (new Integer( rowAndSeat.substring( 0, 
						  rowAndSeat.length()-1))).intValue();
					
	       // It is an integer - but is it in the range 1 to 42?                                                  
	       if( (rowPart < MIN_ROW_NUMBER) ||
		   (rowPart > MAX_ROW_NUMBER) ){ 
		      seemsOk = false;  
	       } // End if. 
	    } catch ( Exception exception) { 
		seemsOk = false;      
	    } // End try/catch    
	 } // End if.    

	 // At this point if seemsOk is true the argument was valid 
	 // otherwise it wasn't so throw the exception.
	 if ( seemsOk) { 
	    row  = rowPart;
	    seat = seatPart; 
	 } else { 
	    throw new Exception( "Invalid row or seat"); 
	 } // End if.          
   } // End AirlineSeat constructor.

   
   public int getRowNumber() { 
      return row;
   } // End getRowNumber

   public char getSeatLetter() { 
      return seat;
   } // End getRowNumber

   
   public boolean isEmergencyExit() { 
      if ( (this.getRowNumber() == 5)  ||
	   (this.getRowNumber() == 16) ||
	   (this.getRowNumber() == 26) ){
	 return true;
      } else { 
	 return false;
      } // End if.    
   } // End isEmergencyExit
      
   
   public boolean isWindowSeat() { 
      if ( (this.getSeatLetter() == 'A') ||
	   (this.getSeatLetter() == 'J') ){
	 return true;
      } else { 
	 return false;
      } // End if.    
   } // End isWindowSeat
   
   
    public boolean isAisleSeat() { 
      if ( (this.getSeatLetter() == 'C') ||
	   (this.getSeatLetter() == 'D') ||
	   (this.getSeatLetter() == 'G') ||
	   (this.getSeatLetter() == 'H') ){
	 return true;
      } else { 
	 return false;
      } // End if.    
   } // End isWindowSeat  
   
   
   public String toString() { 
   
   StringBuffer toReturn = new StringBuffer();
      toReturn.append( "Seat " + this.getRowNumber() + 
				 this.getSeatLetter()); 
      if ( this.isEmergencyExit()) { 
	 toReturn.append( " EE ");
      } // End if.                      
 
      if ( this.isWindowSeat()) { 
	 toReturn.append( " WI ");
      } else if ( this.isAisleSeat()) {            
	 toReturn.append( " AI ");
      } // End if.
      
      return toReturn.toString();
   } // End toString; 
   
} // end class AirlineSeat

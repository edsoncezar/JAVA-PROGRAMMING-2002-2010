

package airseat;


import java.util.*;


public class FlightDemo extends Object {

    public static void main (String[] args) {
    
    Vector flightDetails = null;

       System.out.println( "\n\n\tFlight Demo\n\n");

       flightDetails = clearTheFlight( PricedSeat.MOSCOW);
       
       frugTheFlight( flightDetails);
       showVacancyPlot( flightDetails);
       
       
       //System.out.println( flightDetails.elementAt( 212));
       
    } // End main 
    
    
    private static Vector clearTheFlight( int destination) { 
    
    Vector     toReturn = new Vector();
    PricedSeat aSeat    = null;
       
	 for ( int row = 1; row <= 26; row++ ) { 
	    for (char seat = 'A'; seat <= 'J'; seat++) {  
		try { 
		   aSeat = new PricedSeat( String.valueOf( row) + 
					   String.valueOf( seat), 
					   destination);
		} catch ( Exception exception) { 
		   System.out.println( "Exception clearing the flight!" );
		} // End try/catch 
		toReturn.add( aSeat);
	    } // End for seat.
	 } // end for row.
	 return toReturn;
    } // End clearTheFlight
    
    private static void frugTheFlight( Vector toShow) { 
    
    PricedSeat aSeat    = null;   
    
    try { 
	 aSeat =  ((PricedSeat) toShow.elementAt( 12));          
	 aSeat.bookSeat( 40, false);
	 
	 aSeat =  ((PricedSeat) toShow.elementAt( 112)); 
	 aSeat.bookSeat( 40, false);        
	 
	 aSeat =  ((PricedSeat) toShow.elementAt( 212)); 
	 aSeat.bookSeat( 40, true);
    } catch ( Exception exception) { 
	System.out.println( "Unexpected exception frugging the flight!");
    } // End try/catch 
	    
    } // End frugTheFlight
    
    private static void showVacancyPlot( Vector toShow) { 
    
    PricedSeat aSeat    = null;
    int        location = 0; 
	 for ( int row = 1; row <= 26; row++ ) { 
	    for (char seat = 'A'; seat <= 'J'; seat++) {  
	       aSeat = ((PricedSeat) toShow.elementAt( location++)); 
	       if ( aSeat.isBooked()) { 
		  System.out.print( "X");
	       } else { 
		  System.out.print( ".");
	       } // End if. 
	 
	    } // End for seat 
	    System.out.println();   
	 } // End for row         
    } // End showVacancyPlot
} // PricedSeatDemo class

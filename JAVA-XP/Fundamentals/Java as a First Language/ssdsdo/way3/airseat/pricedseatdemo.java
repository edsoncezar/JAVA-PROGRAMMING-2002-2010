
package airseat;

public class PricedSeatDemo extends Object {

    public static void main (String[] args) {
    
    PricedSeat demoSeat = null;
	
       System.out.println( "\n\n\tPriced Seat Demo\n\n");
       
       System.out.println( "Constructing seat 16D to Paris");
       try { 
	  demoSeat = new PricedSeat( "16d", PricedSeat.PARIS);
	  System.out.println( "\n\nSeat 16D constructed . . . showing \n\n");
	  System.out.println( demoSeat + "\n\n");
	  System.out.println( "The row number should be 16 . . . " + 
			      demoSeat.getRowNumber());         
	  System.out.println( "The seat letter should be D . . . " + 
			      demoSeat.getSeatLetter());                            
	  System.out.println( "Is Emergency Exit should be true . . . " + 
			      demoSeat.isEmergencyExit());
       } catch (Exception exception) { 
	  System.out.println( "Exception thrown construction 16D - *ERROR*");
       } // End try/catch
      
    } // End main 
    
} // PricedSeatDemo class

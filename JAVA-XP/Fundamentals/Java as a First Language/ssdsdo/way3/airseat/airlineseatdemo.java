
package airseat;

public class AirlineSeatDemo extends Object {

    public static void main (String[] args) {
    
    AirlineSeat demoSeat = null;
	
       System.out.println( "\n\n\tAirline Seat Demo\n\n");
       
       System.out.println( "Constructing seat 16D");
       try { 
	  demoSeat = new AirlineSeat( "16D" );
	  System.out.println( "\n\nSeat 16D constructed . . . showing \n\n");
	  System.out.println( demoSeat + "\n\n");
       } catch (Exception exception) { 
	  System.out.println( "Exception thrown construction 16D - *ERROR*");
       } // End try/catch
       

    } // End main 
} // AirllineSeatDemo class

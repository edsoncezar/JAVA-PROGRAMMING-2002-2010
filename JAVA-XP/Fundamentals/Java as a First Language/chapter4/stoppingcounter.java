//

import Counters.StoppingCounter;

public class StoppingDemonstration { 


public static void main( String argv[]) { 

   StoppingCounter  demoCounter = new StoppingCounter( 10, 12); 

      System.out.println( "\n\t\tStopping Counter demonstration \n");
    
      System.out.println( "The counter has been created with a range of ");
      System.out.println( "10 to 12 and an initial value of 10." );
    
      System.out.println( "\nDemonstrating numberCountedIs()");
      System.out.print( "The value should be 10 ... ");
      System.out.print( demoCounter.numberCountedIs());
      System.out.println( "." );
    
      System.out.println( "\nCounting two ocurrences with count()");
      demoCounter.count();
      demoCounter.count();
      System.out.print( "Its value should now be 12 ... ");
      System.out.print( demoCounter.numberCountedIs());
      System.out.println( "." );    
    
      System.out.println( "\nCounting another ocurrence with count()");
      demoCounter.count();
      System.out.print( "Its value should still be 12 ... ");
      System.out.print( demoCounter.numberCountedIs());
      System.out.println( "." );      
    
   } // End main

} // End StoppingDemonstration

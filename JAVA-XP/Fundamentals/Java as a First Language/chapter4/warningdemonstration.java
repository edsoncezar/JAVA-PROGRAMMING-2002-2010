//

import Counters.WarningCounter;

public class WarningDemonstration { 


public static void main( String argv[]) { 

   WarningCounter  demoCounter = new WarningCounter( 10, 12); 

      System.out.println( "\n\t\t Warning Counter demonstration \n");
    
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
      System.out.println( " This should throw an exception ... ");
      demoCounter.count();
      System.out.print( "This message should never appear ... ");

    
   } // End main

} // End WarningDemonstration

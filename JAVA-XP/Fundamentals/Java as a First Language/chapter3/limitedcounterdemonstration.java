// Filename LimitedCounterDemonstration.java.
// Demonstration harness for the (temporarily
// amended LimitedCounter class.
//
// Written for JFL book Chapter 3.
// Fintan Culwin, v0.1, January 1997.

import Counters.LimitedCounter;

class LimitedCounterDemonstration { 

   public static void main( String argv[]) { 

   LimitedCounter  aCounter;

      System.out.println( "\n\n\t Limited Counter Demonstration");

      System.out.print( "\n\nConstructing an instance with the ");
      System.out.println( "range 10 to 12.");
      aCounter = new LimitedCounter( 10, 12);      
      System.out.print( "Instance created ...");

      System.out.print( "\n\nDemonstrating minimumIs() ");
      System.out.print( "it should be 10 ... ");
      System.out.println(  aCounter.minimumIs());     

      System.out.print( "\n\nDemonstrating numberCountedIs ");
      System.out.print( "it should be 10 ... ");
      System.out.println(  aCounter.numberCountedIs());

      System.out.print( "\n\nDemonstrating isAtMinimum ");
      System.out.print( "it should be true ... ");
      System.out.println(  aCounter.isAtMinimum());
      System.out.print( "\n\mSetting to maximum ... " );
      aCounter.setToMaximum();
      System.out.println( "set to maximum." );

      System.out.print( "\n\nDemonstrating isAtMaximum ");
      System.out.print( "it should be true ..");
      System.out.println(  aCounter.isAtMaximum());       

      System.out.println( "\n\nDemonstration finished.\n\n");       
   } // end fun main()
} // end class LimitedCounterDemonstration.    




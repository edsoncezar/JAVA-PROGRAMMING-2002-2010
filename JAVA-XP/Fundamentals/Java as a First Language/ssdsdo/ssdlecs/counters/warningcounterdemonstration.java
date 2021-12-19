// Filename counters\RollOverCounterDemonstration.java.
// Demonstration harness for the (temporarily
// amended LimitedCounter class.
//
// Written for JFL book Chapter 3.
// Fintan Culwin, v0.1, January 1997.
// This version Oct 1999

package counters;

 public class WarningCounterDemonstration extends Object { 
 
    public static void main( String argv[]) { 
 
    WarningCounter  aCounter = null; 
 
        System.out.println( "\n\n\t Warning Counter Demonstration");
        

        System.out.print( "\n\nConstructing an instance with the ");
        System.out.println( "range 10 to 12.");
        aCounter = new WarningCounter( 10, 12);      
        System.out.print( "Instance created ...");

        System.out.print( "\n\nDemonstrating numberCountedIs(), ");
        System.out.print( "it should be 10 ... ");
        System.out.println(  aCounter.numberCountedIs());

        System.out.print( "\n\nCounting two occurrences and ");
        System.out.print( "demonstrating numberCountedIs() again,\n ");
        System.out.print( "it should be 12 ... ");
        aCounter.count();
        aCounter.count();
        System.out.println(  aCounter.numberCountedIs());     

        System.out.print( "\n\nCounting another occurrence and ");
        System.out.print( "demonstrating numberCountedIs() again,\n ");
        System.out.print( "it should throw an CounterException ... ");
        aCounter.count();
  
  
        System.out.println( "\n\nThis message should never appear!!!\n\n");

        System.out.println( "\n\nEnd of Stopping Counter Demonstration");
   } // End main.

} // end class WarningCounterDemonstration.    




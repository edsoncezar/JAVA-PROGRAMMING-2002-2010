// Filename counters\LimitedCounterDemonstration.java.
// Demonstration harness for the (temporarily
// amended LimitedCounter class.
//
// Written for JFL book Chapter 3.
// Fintan Culwin, v0.1, January 1997.
// This version Oct 1999

package counters;

 public class LimitedCounterDemonstration extends Object { 
 
    public static void main( String argv[]) { 
 
    LimitedCounter  aCounter = null; 
 
        System.out.println( "\n\n\t Limited Counter Demonstration");
        

        System.out.print( "\n\nConstructing an instance with the ");
        System.out.println( "range 10 to 12.");
        aCounter = new LimitedCounter( 10, 12);      
        System.out.print( "Instance created ...");

        System.out.print( "\nDemonstrating minimumIs(), ");
        System.out.print( "it should be 10 ... ");
        System.out.println(  aCounter.minimumIs());
        System.out.print( "\n\nDemonstrating numberCountedIs(), ");
        System.out.print( "it should be 10 ... ");
        System.out.println(  aCounter.numberCountedIs());

        
        System.out.print( "\n\nDemonstrating isAtMinimum(), ");
        System.out.print( "it should be true ... ");
        System.out.println(  aCounter.isAtMinimum());

        System.out.println( "\n\nmore Demonstrations Needed ...\n\n");

        System.out.println( "\n\nEnd of Limited Counter Demonstration");
   } // End main.

} // end class LimitedCounterDemonstration.    




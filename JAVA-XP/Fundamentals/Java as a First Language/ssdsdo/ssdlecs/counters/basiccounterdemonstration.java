// Filename counters\BasicCounterDemonstration.java.
// Demonstration harness for the (temporarily
// amended LimitedCounter class.
//
// Written for JFL book Chapter 3.
// Fintan Culwin, v0.1, January 1997.
// This version Oct 1999

package counters;

 public class BasicCounterDemonstration extends Object { 
 
    public static void main( String argv[]) { 
 
    BasicCounter  aCounter = null; 
 
        System.out.println( "\n\n\t Basic Counter Demonstration");
        
        System.out.print( "\n\nConstructing an instance with ");
        System.out.println( "the initial value 4.");
        aCounter = new BasicCounter( 4);      
        System.out.print( "Instance created ... ");

        System.out.print( "\n\nDemonstrating numberCountedIs() "+
                          ", it should be 4 ... ");
        System.out.println(  aCounter.numberCountedIs());

        System.out.println( "\n\nDemonstrating count().");
        aCounter.count();
        System.out.print( "Showing the changed value, it should be 5 ... ");
        System.out.println( aCounter.numberCountedIs());       
        
        System.out.println( "\n\nDemonstrating unCount(). ");
        aCounter.unCount();
        System.out.print( "Showing the changed value, it should be 4 ... ");
        System.out.println( aCounter.numberCountedIs());       

        System.out.println( "\n\nDemonstrating setCountTo()" +
                            " setting to 10.");
        aCounter.setCountTo( 10);
        System.out.print( "Showing the changed value, " + 
                          "it should be 10 ... ");
        System.out.println(  aCounter.numberCountedIs());

        System.out.println( "\n\nDemonstrating reset().");
        aCounter.reset();
        System.out.print( "Showing the reset value, it should be 4 ... ");
        System.out.println( aCounter.numberCountedIs());

        System.out.println( "\n\nEnd of Basic Counter Demonstration");
   } // End main.

} // end class BasicCounterDemonstration.    




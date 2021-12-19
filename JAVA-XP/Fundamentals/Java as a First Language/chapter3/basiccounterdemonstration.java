// Filename BasicCounterDemonstration.java.
// Demonstration harness for the (temporarily)
// amended BasicCounter class.
//
// Written for JFL book Chapter 3.
// Fintan Culwin, v0.1, January 1997.

import Counters.BasicCounter;

class BasicCounterDemonstration { 

   public static void main( String argv[]) { 

   BasicCounter  aCounter; 

      System.out.println( "\n\n\t Basic Counter Demonstration");

      System.out.print( "\n\nConstructing an instance with ");
      System.out.println( "the inital value 4.");
      aCounter = new BasicCounter( 4);      
      System.out.print( "Instance created ... ");

      System.out.print( "\n\nDemonstrating numberCountedIs, it should be 4 .. ");
      System.out.println(  aCounter.numberCountedIs());


      System.out.println( "\n\nDemonstrating count()  ... ");
      aCounter.count();
      System.out.print( "Showing the changed value ... it should be 5 ... ");
      System.out.println( aCounter.numberCountedIs());       

      System.out.println( "\n\nDemonstrating unCount()  ... ");
      aCounter.unCount();
      System.out.print( "Showing the changed value ... it should be 4 ... ");
      System.out.println( aCounter.numberCountedIs());       

      System.out.println( "\n\nDemonstrating setCountTo(), setting to 10 ... ");
      aCounter.setCountTo( 10);
      System.out.print( "Showing the changed value ... it should be 10 ... ");
      System.out.println(  aCounter.numberCountedIs());

      System.out.print( "\n\nConstructing a new instance ");
      System.out.println( "with the default value 0.");
      aCounter = new BasicCounter();      
      System.out.println( "New instance created ..."); 
      System.out.print( "\n\nShowing the value of the new instance, ");
      System.out.print( "it should be 0 ... ");
      System.out.println(  aCounter.numberCountedIs());       

      System.out.println( "\n\nDemonstration finished.\n\n");       
   } // End main.
} // end class BasicCounterDemonstration    

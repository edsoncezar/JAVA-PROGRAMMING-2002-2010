// Filename ClickCounterDemo.java.
// Demonstration harness for the ClickCounter class.
//
// Fintan Culwin, V0.2, August 1997.

import ClickCounter;

public class ClickCounterDemo{ 

   public static void main( String args[] ) { 
   
   ClickCounter demoCounter  = new ClickCounter ( 10, 12);

      System.out.println( "\t Click Counter Demonstration \n");

      System.out.println( "Count is as String ... " + 
                          demoCounter.countIsAsString());

      System.out.println( "counting two occurences ...");
      demoCounter.count();
      demoCounter.count();

      System.out.println( "Count is now ...       " + 
                          demoCounter.countIsAsString());
   } // End main.

} // End ClickCounterDemo


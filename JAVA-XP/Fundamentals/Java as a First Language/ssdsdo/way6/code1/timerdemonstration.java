// TimerDemonstration.java.
// Initial demonstration of a threaded class.
// Produced for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

public class TimerDemonstration extends Object { 


   public static void main( String argv[]) { 

   Timer aTimer = new Timer();

      System.out.println( "Timer demo ... starting the timer.");
      aTimer.start();
      System.out.println( "\nStarted ... it has already counted " + 
                          aTimer.getTime() + 
                          " tenths of a second.");   

      System.out.println( "\nApparently doing nothing for a bit ...");
      for ( int index = 0; index < 1000000; index++) { 
         // do nothing.
      } // End for. 
      System.out.println( "The timer must have been executing as " +
                          "\nit has now counted ... " + 
                          aTimer.getTime() + 
                          " tenths of a second."); 
  

      System.out.println( "\nSuspending the timer for a bit.");
      aTimer.suspend();
      for ( int index = 0; index < 1000000; index++) { 
         // do nothing.
      } // End for. 
      System.out.println( "\nThe timer is not doing anything as " +
                          "\nit has now counted ... " + 
                          aTimer.getTime() + 
                          " tenths of a second."); 
  
      System.out.println( "\nResuming the timer.");
      aTimer.resume();
      for ( int index = 0; index < 1000000; index++) { 
         // do nothing.
      } // End for. 
      System.out.println( "The timer must have been executing  " +
                          "again \nas it has now counted ... " + 
                          aTimer.getTime() + 
                          " tenths of a second."); 

      System.out.println( "\nResetting the timer ...");
      aTimer.resetTime();
      System.out.println( "The timer must have been reset " +
                          "\nas it has now counted ... " + 
                          aTimer.getTime() + 
                          " tenths of a second."); 

      System.out.println( "\nStopping the timer and finishing ...");
      aTimer.stop();
   } // end main.

} // End TimerDemonstration.

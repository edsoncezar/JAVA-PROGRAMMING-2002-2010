// Timer.java.
// Initial example of a threaded class.
// Produced for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.


public class Timer extends Thread { 

private int theTime =0;

   public Timer() { 
      super();
      theTime =0;
   } // End Timer constructor.


   public void run() { 
      while ( true) { 
         try { 
            this.sleep( 100);
         } catch (InterruptedException exception) { 
            // Do nothing. 
         } // End try/ catch.
         theTime++;        
      } // end while.
   } // End run.


   public int getTime() { 
      return theTime;
   } // End getTime;


   public synchronized void resetTime() { 
      theTime =0;
   } // End getTime;
} // End Timer.


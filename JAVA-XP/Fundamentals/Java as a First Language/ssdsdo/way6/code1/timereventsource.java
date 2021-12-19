// TimerEventSource.java
// Timer class which implements an ActionEvent
// unicaster protocol.
// Bridge between threading and GUIs.
// Written for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

import java.awt.event.*;


public class TimerEventSource extends Thread { 

private int theTime =0;
private ActionListener itsListener;

   public TimerEventSource() { 
      super();
      theTime =0;
   } // End Timer constructor.


   public void run() { 
      while ( true) { 
         try { 
            this.sleep( 100);
         } catch (InterruptedException exception) { 
            // Do nothing. 
         } // End try/ catch
         theTime++;

         if ( itsListener != null) { 

         String timeString = new String( 
                         new Integer( theTime).toString());

         ActionEvent theEvent = new ActionEvent( this,
                                      ActionEvent.ACTION_PERFORMED,
                                      timeString);
            itsListener.actionPerformed( theEvent);
         } // End if.        
      } // end while.
   } // End run.


   public int getTime() { 
      return theTime;
   } // End getTime;

   public synchronized void resetTime() { 
      theTime =0;
   } // End getTime;

   public void addActionListener( ActionListener listener) 
                           throws java.util.TooManyListenersException { 
      if ( itsListener == null) { 
         itsListener = listener;
      } else { 
         throw new java.util.TooManyListenersException();
      } // End if.
   } // End addActionListener.


   public void removeActionListener( ActionListener listener) { 
      if ( itsListener == listener) { 
         itsListener = null;
      } // End if.
   } // End removeActionListener.


} // End Timer.


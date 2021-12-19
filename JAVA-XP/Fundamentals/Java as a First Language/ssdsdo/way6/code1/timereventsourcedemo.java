// TimerEventSourceDemo.java
// Timer class which demonstrates recieving 
// ActionEvents from a unicaster.
// Bridge between threading and GUIs.
// Written for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

import java.awt.event.*;

public class TimerEventSourceDemo extends    Object
                                  implements ActionListener { 

private TimerEventSource          aTimer;

   public TimerEventSourceDemo() { 

      super();
      aTimer      = new TimerEventSource();
        
      try { 
         aTimer.addActionListener( this);
      } catch ( java.util.TooManyListenersException exception) { 
         // do nothing
      } // End try/catch. 
      aTimer.start();
   } // End init.


   public void actionPerformed( ActionEvent event) { 
      System.out.println( event.getActionCommand());
   } // End actionPerformed.


   public static void main( String argv[]) { 

   TimerEventSourceDemo theDemo = new TimerEventSourceDemo();
       
   } // End main.

} // End class TimerEventSourceDemo.

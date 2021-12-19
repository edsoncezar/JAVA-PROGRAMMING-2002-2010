// GraphicalTimerEventSourceDemo.java
// Timer class which demonstrates receiving 
// ActionEvents from a unicaster and showing
// them in a graphical window.
// Bridge between threading and GUIs.
// Written for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class GraphicalTimerEventSourceDemo extends Applet
                                        implements ActionListener { 

private TimerEventSource     aTimer;
private SimpleTimerInterface anInterface;

   public void init() { 

      aTimer      = new TimerEventSource();
      anInterface = new SimpleTimerInterface( this);
      try { 
         aTimer.addActionListener( this);
      } catch ( java.util.TooManyListenersException exception) { 
         // do nothing
      } // End try/catch
      aTimer.start();
   } // End init.


   public void actionPerformed( ActionEvent event) { 
      anInterface.setTime( event.getActionCommand());
   } // End actionPerformed.


   public static void main( String argv[]) { 

   Frame        frame      = new Frame("Timer");
   GraphicalTimerEventSourceDemo theDemo    
          = new GraphicalTimerEventSourceDemo();
  
      theDemo.init();  
      frame.add("Center", theDemo);
  
      frame.show();
      frame.setSize( frame.getPreferredSize());     
   } // End main.

} // End class GraphicalTimerEventSourceDemo.

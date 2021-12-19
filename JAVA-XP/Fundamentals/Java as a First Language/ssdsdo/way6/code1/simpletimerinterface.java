// SimpleTimerInterface.java
// Supplies a window within which the 
// state of a Timer can be shown.
// Bridge between threading and GUIs.
// Written for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class SimpleTimerInterface extends Object { 

private Label theTime;

   public SimpleTimerInterface( Applet screenWindow) { 
      theTime = new Label( "******");
      screenWindow.add( theTime);
   } // End init. 

   public void setTime( String timeNow) { 
      theTime.setText( timeNow);
   } // End setTime;


//   public void setTime( String timeNow) { 
//
//   int tenthsOfSecond = Integer.parseInt( timeNow, 10);
//   int tenths   = tenthsOfSecond % 10;
//   int seconds  = (tenthsOfSecond /10)  % 60;
//   int minuites = (tenthsOfSecond /600) % 60;
//   int hours    = (tenthsOfSecond /3600);
//   String timeString = new String( hours    +":" + 
//                                   minuites +":" + 
//                                   seconds  +":" + 
//                                   tenths);
//      theTime.setText( timeString);
//   } // End setTime.


} // End class SimpleTimerInterface.

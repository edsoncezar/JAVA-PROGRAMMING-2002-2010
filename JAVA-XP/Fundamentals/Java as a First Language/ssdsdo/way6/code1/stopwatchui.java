// StopwatchUI.java
// Supples a stopwatch GUI using 
// the Timer unicaster.
// Written for waypoint 5.
//
// Fintan Culwin, v0.1, March 1998.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class StopwatchUI extends Object { 

private Label  theTime     = null;
private Button startButton = null;
private Button stopButton  = null;
private Button resetButton = null;
private Panel  upperPanel  = null;
private Panel  lowerPanel  = null;


   public StopwatchUI( Applet         screenWindow,
                       ActionListener itsListener) { 

      super();
      upperPanel = new Panel();
      lowerPanel = new Panel();

      theTime = new Label( "*****");
      upperPanel.add( theTime);

      startButton = new Button( "start");
      startButton.setActionCommand( "start");
      startButton.addActionListener( itsListener);

      resetButton = new Button( "reset");   
      resetButton.setActionCommand( "reset");
      resetButton.addActionListener( itsListener);  

      stopButton  = new Button( "stop");
      stopButton.setActionCommand( "stop");
      stopButton.addActionListener( itsListener);


      lowerPanel.add( startButton); 
      lowerPanel.add( stopButton);
      lowerPanel.add( resetButton); 

      screenWindow.setLayout( new BorderLayout());
      screenWindow.add( upperPanel, "North");
      screenWindow.add( lowerPanel, "South");     
   } // End StopwatchUI constructor. 

   public void setTime( String timeNow) { 
      theTime.setText( timeNow);
   } // End setTime;


/*
   public void setTime( String timeNow) { 

   int tenthsOfSecond = Integer.parseInt( timeNow, 10);
   int tenths   = tenthsOfSecond % 10;
   int seconds  = (tenthsOfSecond /10)  % 60;
   int minuites = (tenthsOfSecond /600) % 60;
   int hours    = (tenthsOfSecond /3600);
   String timeString = new String( hours    +":" + 
                                   minuites +":" + 
                                   seconds  +":" + 
                                   tenths);
      theTime.setText( timeString);
   } // End setTime.
*/

   public void setResetState(){ 
      startButton.setEnabled(  true);
      stopButton.setEnabled(   false);
      resetButton.setEnabled(  false);
      theTime.setText( "*****");
   } // End setResetState.

   public void setRunningState(){ 
      startButton.setEnabled(  false);
      stopButton.setEnabled(   true);
      resetButton.setEnabled(  false);
   } // End setRunningState;

   public void setStoppedState(){ 
      startButton.setEnabled(  false);
      stopButton.setEnabled(   false);
      resetButton.setEnabled(  true);
   } // End setStoppedState.

} // End class StopwatchUI.

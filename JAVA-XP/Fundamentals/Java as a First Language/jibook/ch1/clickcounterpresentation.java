// Filename ClickCounterPresentation.java.
// Provides an interactive interface for the ClickCounter
// class. Written for the Java Interface book Chapter 1.
//
// Fintan Culwin, v 2.0, August 1997.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;


public class ClickCounterPresentation extends Object {

private Button         incrementButton;
private Button         resetButton;
private Button         decrementButton;
private Label          valueDisplay;

private ActionListener theListner;


   public ClickCounterPresentation( ClickCounterTranslation itsApplet) {

   Panel  valuePanel;
   Panel  controlPanel;

      itsApplet.setLayout( new GridLayout(2, 1, 10, 10));

      valueDisplay = new Label();
      valuePanel   = new Panel();
      valuePanel.add( valueDisplay);
      itsApplet.add( valuePanel);

      incrementButton = new Button( "+");
      incrementButton.setActionCommand( "increment");
      incrementButton.addActionListener( itsApplet);
      
      resetButton = new Button( "0");
      resetButton.setActionCommand( "reset");
      resetButton.addActionListener( itsApplet);
      
      decrementButton = new Button( "-");
      decrementButton.setActionCommand( "decrement");
      decrementButton.addActionListener( itsApplet);
      
      controlPanel = new Panel();
      controlPanel.add( incrementButton);
      controlPanel.add( resetButton);
      controlPanel.add( decrementButton);
      itsApplet.add( controlPanel);      
   } // end ClickCounterPresentation constructor.


   public void setValueDisplay( String updateTo) { 
      valueDisplay.setText( updateTo);
   } // End setValueDisplay.


   public void setMinimumState() {
      incrementButton.setEnabled( true); 
      resetButton.setEnabled( false);
      decrementButton.setEnabled( false);
   } // End setMinimumState.

   public void setCountingState() {
      incrementButton.setEnabled( true); 
      resetButton.setEnabled( true); 
      decrementButton.setEnabled( true); 
   } // End setCountingState.

   public void setMaximumState() {
      incrementButton.setEnabled( false); 
      resetButton.setEnabled( true); 
      decrementButton.setEnabled( true); 
   } // End setMaximumState.

} // end class ClickCounterPresentation.






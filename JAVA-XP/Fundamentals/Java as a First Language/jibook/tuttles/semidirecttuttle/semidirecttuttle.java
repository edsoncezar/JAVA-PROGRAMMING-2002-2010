// Filename SemiDirectTuttleInterface.java.
// Supplied a semi-direct interface the Tuttle class
// using TuttleButtons.
//
// Written for Java Interface book chapter 5.
// Fintan Culwin, v 0.2, August 1997.

package SemiDirectTuttle;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

import Tuttles.Tuttle;
import SemiDirectTuttle.SemiDirectTuttleInterface;


public class SemiDirectTuttle extends    Applet 
                              implements ActionListener {


private Label  feedbackLabel;
private Tuttle theTuttle;
private Panel  feedbackPanel;
 
   public void init() { 
   
   Panel     tuttlePanel = new Panel(); 
   SemiDirectTuttleInterface theInterface;
     
      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));
      feedbackPanel = new Panel();
      feedbackLabel = new Label();
      feedbackPanel.add( feedbackLabel);
      
      theTuttle     = new Tuttle( this, 400, 400);            
      tuttlePanel.add( theTuttle); 
      
      theInterface  = new SemiDirectTuttleInterface( this);

      this.setLayout( new BorderLayout());
      this.add( feedbackPanel, "North");
      this.add( tuttlePanel,   "Center");     
      this.add( theInterface,  "South");
      
      this.feedback();
   } // End init.


   public  void actionPerformed( ActionEvent event) { 

   String theCommand = event.getActionCommand();

      if ( theCommand.equals( "Forwards")) { 
         theTuttle.forward( 25);
      } else if ( theCommand.equals( "Backwards")) { 
         theTuttle.backward( 25);
      } else if ( theCommand.equals( "Turn right")) { 
         theTuttle.turnRight( 15);
      } else if ( theCommand.equals( "Turn left")) {  
         theTuttle.turnLeft( 15);
      } else if ( theCommand.equals( "Clear")) {   
         theTuttle.clearTuttleArea();
      } else if ( theCommand.equals( "Reset")) {   
         theTuttle.resetTuttle();
      } else if ( theCommand.equals( "Clear Reset")) {   
         theTuttle.clearAndReset();
      } else if ( theCommand.equals( "Penup")) {   
         theTuttle.setPenUp();
      } else if ( theCommand.equals( "Pendown")) {   
         theTuttle.setPenDown();
      } else if ( theCommand.equals( "fg green")) {   
         theTuttle.setForeground( Color.green);
      } else if ( theCommand.equals( "fg red")) {   
         theTuttle.setForeground( Color.red);
      } else if ( theCommand.equals( "fg yellow")) {   
         theTuttle.setForeground( Color.yellow);
      } else if ( theCommand.equals( "fg blue")) {   
         theTuttle.setForeground( Color.blue);
      } else if ( theCommand.equals( "fg white")) {   
         theTuttle.setForeground( Color.white);
      } else if ( theCommand.equals( "fg black")) {   
         theTuttle.setForeground( Color.black);
      } else if ( theCommand.equals( "bg green")) {   
         theTuttle.setBackground( Color.green);
      } else if ( theCommand.equals( "bg red")) {   
         theTuttle.setBackground( Color.red);
      } else if ( theCommand.equals( "bg yellow")) {   
         theTuttle.setBackground( Color.yellow);
      } else if ( theCommand.equals( "bg blue")) { 
         theTuttle.setBackground( Color.blue);
      } else if ( theCommand.equals( "bg white")) {   
         theTuttle.setBackground( Color.white);
      } else if ( theCommand.equals( "bg black")) {   
         theTuttle.setBackground( Color.black);       
      } // End if.
      
      this.feedback();       
   } // End actionPerformed.


   private void feedback() {    
      feedbackLabel.setText(  theTuttle.getDetails());
      feedbackPanel.doLayout();
   } // End feedback.
} // End SemiDirectTuttle.




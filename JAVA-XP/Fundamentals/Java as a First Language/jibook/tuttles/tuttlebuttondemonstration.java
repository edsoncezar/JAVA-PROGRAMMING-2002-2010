// Filename TuttleButtonDemonstration.java.
// A demonstration test harness for the TuttleButton class.
// 
// Written for the Java Interface book, Chapter 5.
// Fintan Culwin, v 0.1, April 1996


import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import Tuttles.TuttleButton;


public class TuttleButtonDemonstration extends    Applet 
                                       implements ActionListener {
                                                            
  public void init() {

  TuttleButton leftTuttleButton;
  TuttleButton rightTuttleButton;

     this.setBackground( Color.white);
     
     leftTuttleButton  = new TuttleButton( "greyltutt.gif", this);
     leftTuttleButton.setActionCommand( "Left  button");
     leftTuttleButton.addActionListener( this);
     this.add( leftTuttleButton);
     
     rightTuttleButton = new TuttleButton( "greyrtutt.gif", this, Color.red);
     rightTuttleButton.setActionCommand( "Right button");
     rightTuttleButton.addActionListener( this);     
     this.add( rightTuttleButton);         
  } // end init()


  public  void actionPerformed( ActionEvent event) { 
     System.out.println( event.getActionCommand() + " pressed.");                     
  } // End actionPerformed.                          
} // end class TuttleButtonDemonstration.











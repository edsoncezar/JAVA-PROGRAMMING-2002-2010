// Filename SemiDirectTuttleInterface.java.
// Supplied a semi-direct interface the Tuttle class
// using TuttleButtons.
//
// Written for Java Interface book chapter 5.
// Fintan Culwin, v 0.2, August 1997.

package SemiDirectTuttle;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import Tuttles.TuttleButton;

public class SemiDirectTuttleInterface extends Panel {


      public SemiDirectTuttleInterface( Applet itsApplet) { 
      
      ActionListener sendToHere = (ActionListener) itsApplet; 

      Panel foreGroundPanel    = new Panel();
      Panel backGroundPanel    = new Panel();
      Panel screenPanel        = new Panel();
      Panel penPanel           = new Panel();
      Panel movementPanel      = new Panel();
   
      GridBagConstraints constraints    = new GridBagConstraints();
      GridBagLayout      movementLayout = new GridBagLayout();
      GridBagLayout      tuttleLayout   = new GridBagLayout();
   

      TuttleButton goForward;
      TuttleButton goBackward;
      TuttleButton turnRight;
      TuttleButton turnLeft;

      TuttleButton clearButton;
      TuttleButton resetButton;
      TuttleButton clearAndResetButton;

      TuttleButton penUpButton;
      TuttleButton penDownButton;

      TuttleButton greenButton;
      TuttleButton redButton;
      TuttleButton yellowButton;
      TuttleButton blueButton;
      TuttleButton whiteButton;
      TuttleButton blackButton;

      TuttleButton greenBackButton;
      TuttleButton redBackButton;
      TuttleButton yellowBackButton;
      TuttleButton blueBackButton;
      TuttleButton whiteBackButton;
      TuttleButton blackBackButton;

       movementPanel.setLayout( movementLayout);
                             
       turnLeft   = new TuttleButton( "greyltutt.gif", itsApplet);
       turnLeft.setActionCommand( "Turn left");
       turnLeft.addActionListener( sendToHere);
       constraints.gridx = 0;
       constraints.gridy = 1;       
       constraints.gridwidth  = 1;
       constraints.gridheight = 2;     
       movementLayout.setConstraints( turnLeft, constraints);       
       movementPanel.add( turnLeft);

       goForward  = new TuttleButton( "greyuptutt.gif", itsApplet);
       goForward.setActionCommand( "Forwards");
       goForward.addActionListener( sendToHere);       
       constraints.gridx = 1;
       constraints.gridy = 0; 
       constraints.gridwidth  = 1;
       constraints.gridheight = 2;     
       movementLayout.setConstraints( goForward, constraints);  
       movementPanel.add( goForward);
       
       goBackward = new TuttleButton( "greydowntutt.gif", itsApplet);
       goBackward.setActionCommand( "Backwards");
       goBackward.addActionListener( sendToHere);       
       constraints.gridx = 1;
       constraints.gridy = 2;       
       constraints.gridwidth  = 1;
       constraints.gridheight = 2;     
       movementLayout.setConstraints( goBackward, constraints);
       movementPanel.add( goBackward);
       
       turnRight = new TuttleButton( "greyrtutt.gif", itsApplet);
       turnRight.setActionCommand( "Turn right");
       turnRight.addActionListener( sendToHere);       
       constraints.gridx = 2;
       constraints.gridy = 1;       
       constraints.gridwidth  = 1;
       constraints.gridheight = 2;     
       movementLayout.setConstraints( turnRight, constraints);
       movementPanel.add( turnRight);
              

       clearButton = new TuttleButton( "cleartutt.gif", itsApplet);
       clearButton.setActionCommand( "Clear");
       clearButton.addActionListener( sendToHere);       
       screenPanel.add( clearButton);

       resetButton = new TuttleButton( "resettutt.gif", itsApplet);
       resetButton.setActionCommand( "Reset");
       resetButton.addActionListener( sendToHere);
       screenPanel.add( resetButton);
       
       clearAndResetButton = new TuttleButton( "clrreset.gif", itsApplet);
       clearAndResetButton.setActionCommand( "Clear Reset");
       clearAndResetButton.addActionListener( sendToHere);
       screenPanel.add( clearAndResetButton);


       penUpButton= new TuttleButton( "penup.gif", itsApplet);
       penUpButton.setActionCommand( "Penup");
       penUpButton.addActionListener( sendToHere);
       penPanel.add( penUpButton);
       
       penDownButton= new TuttleButton( "pendown.gif", itsApplet);
       penDownButton.setActionCommand( "Pendown");
       penDownButton.addActionListener( sendToHere);
       penPanel.add( penDownButton);


       greenButton  = new TuttleButton( "greentutt.gif", itsApplet,
                                        Color.green);
       greenButton.setActionCommand( "fg green");
       greenButton.addActionListener( sendToHere);
       foreGroundPanel.add( greenButton);
       
       redButton    = new TuttleButton( "redtutt.gif", itsApplet,
                                        Color.red);
       redButton.setActionCommand( "fg red");
       redButton.addActionListener( sendToHere);
       foreGroundPanel.add( redButton);
       
       yellowButton = new TuttleButton( "yellowtutt.gif", itsApplet,
                                        Color.yellow);
       yellowButton.setActionCommand( "fg yellow");
       yellowButton.addActionListener( sendToHere);
       foreGroundPanel.add( yellowButton);
       
       blueButton   = new TuttleButton( "bluetutt.gif", itsApplet,
                                        Color.blue);
       blueButton.setActionCommand( "fg blue");
       blueButton.addActionListener( sendToHere);
       foreGroundPanel.add( blueButton);
       
       whiteButton  = new TuttleButton( "whitetutt.gif", itsApplet);
       whiteButton.setActionCommand( "fg white");
       whiteButton.addActionListener( sendToHere);
       foreGroundPanel.add( whiteButton);
       
       blackButton  = new TuttleButton( "blacktutt.gif", itsApplet);
       blackButton.setActionCommand( "fg black");
       blackButton.addActionListener( sendToHere);
       foreGroundPanel.add( blackButton);


       greenBackButton  = new TuttleButton( "greenbacktutt.gif", itsApplet,
                                            Color.green);
       greenBackButton.setActionCommand( "bg green");
       greenBackButton.addActionListener( sendToHere);
       backGroundPanel.add( greenBackButton);
       
       redBackButton    = new TuttleButton( "redbacktutt.gif", itsApplet,
                                            Color.red);
       redBackButton.setActionCommand( "bg red");
       redBackButton.addActionListener( sendToHere);
       backGroundPanel.add( redBackButton);
       
       yellowBackButton = new TuttleButton( "yellowbacktutt.gif", itsApplet,
                                            Color.yellow);
       yellowBackButton.setActionCommand( "bg yellow");
       yellowBackButton.addActionListener( sendToHere);
       backGroundPanel.add( yellowBackButton);
       
       blueBackButton   = new TuttleButton( "bluebacktutt.gif", itsApplet,
                                            Color.blue);
       blueBackButton.setActionCommand( "bg blue");
       blueBackButton.addActionListener( sendToHere);
       backGroundPanel.add( blueBackButton);
       
       whiteBackButton  = new TuttleButton( "whitebacktutt.gif", itsApplet);
       whiteBackButton.setActionCommand( "bg white");
       whiteBackButton.addActionListener( sendToHere);
       backGroundPanel.add( whiteBackButton);
       
       blackBackButton  = new TuttleButton( "blackbacktutt.gif", itsApplet);       
       blackBackButton.setActionCommand( "bg black");
       blackBackButton.addActionListener( sendToHere);
       backGroundPanel.add( blackBackButton);

   
       this.setLayout( tuttleLayout);    
       this.setBackground( Color.white);

       constraints.gridx = 0;
       constraints.gridy = 0;       
       constraints.gridwidth  = 12;
       constraints.gridheight = 1; 
       constraints.anchor     = GridBagConstraints.NORTH;
       tuttleLayout.setConstraints( foreGroundPanel, constraints);         
       this.add( foreGroundPanel);
       
       constraints.gridx = 0;
       constraints.gridy = 2;       
       constraints.gridwidth  = 12;
       constraints.gridheight = 1; 
       constraints.anchor     = GridBagConstraints.SOUTH;
       tuttleLayout.setConstraints( backGroundPanel, constraints);         
       this.add( backGroundPanel);                   

       constraints.gridx = 12;
       constraints.gridy = 0;       
       constraints.gridwidth  = 9;
       constraints.gridheight = 3; 
       tuttleLayout.setConstraints( movementPanel, constraints);         
       this.add( movementPanel);
       
       constraints.gridx = 21;
       constraints.gridy = 0;       
       constraints.gridwidth  = 6;
       constraints.gridheight = 1;
       constraints.anchor     = GridBagConstraints.NORTH;
       tuttleLayout.setConstraints( screenPanel, constraints);
       this.add( screenPanel);
       
       constraints.gridx = 21;
       constraints.gridy = 2;       
       constraints.gridwidth  = 6;
       constraints.gridheight = 1;
       constraints.anchor     = GridBagConstraints.SOUTH;
       tuttleLayout.setConstraints( penPanel, constraints);
       this.add( penPanel);        


      } // End SemiDirectTuttleInterface constructor.
} // End SemiDirectTuttleInterface.




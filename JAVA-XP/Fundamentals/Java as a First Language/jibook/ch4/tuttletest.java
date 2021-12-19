// Filename Tuttle.java.
// First attempt at a Tuttle Interface,
// interim varsion only.
//
// Written for the Java Interface Book Chapter 4.
// Fintan Culwin, v 0.2, August 1997. 


import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import Tuttles.Tuttle;


public class TuttleTest extends    Applet
                        implements ActionListener {

Tuttle theTuttle; 
Panel  feedbackPanel;
Panel  tuttlePanel;
Label  feedbackLabel;
Panel  exitButtonPanel;
Panel  tuttleButtonsPanel;


   public void init() { 


   Button fwdButton, bwdButton, rtnButton, ltnButton, rstButton,
          clrButton, cstButton, fgrButton, fglButton, fgbButton, 
          bgyButton, bgwButton, pdnButton, pupButton;

      this.setLayout( new BorderLayout());

      tuttlePanel = new Panel();
      tuttlePanel.setBackground( Color.white);
      theTuttle = new Tuttle( this, 400, 400);
      tuttlePanel.add( theTuttle);
      
      tuttleButtonsPanel = new Panel();
      tuttleButtonsPanel.setLayout(new GridLayout( 2, 8));

      fwdButton = new Button("Fwd");
      fwdButton.setActionCommand( "Fwd");
      fwdButton.addActionListener( this);
      tuttleButtonsPanel.add( fwdButton);
      
      bwdButton = new Button("Bwd");
      bwdButton.setActionCommand( "Bwd");
      bwdButton.addActionListener( this);
      tuttleButtonsPanel.add( bwdButton);      
      
      ltnButton = new Button("Ltn");
      ltnButton.setActionCommand( "Ltn");
      ltnButton.addActionListener( this);
      tuttleButtonsPanel.add( ltnButton);      
      
      rtnButton = new Button("Rtn");
      rtnButton.setActionCommand( "Rtn");
      rtnButton.addActionListener( this);
      tuttleButtonsPanel.add( rtnButton);            
      
      rstButton = new Button("Rst");
      rstButton.setActionCommand( "Rst");
      rstButton.addActionListener( this);
      tuttleButtonsPanel.add( rstButton);            
      
      clrButton = new Button("Clr");
      clrButton.setActionCommand( "Clr");
      clrButton.addActionListener( this);
      tuttleButtonsPanel.add( clrButton);            
      
      cstButton = new Button("Cst");
      cstButton.setActionCommand( "Cst");
      cstButton.addActionListener( this);
      tuttleButtonsPanel.add( cstButton);                 
      
      fgrButton = new Button("FgR");
      fgrButton.setActionCommand( "FgR");
      fgrButton.addActionListener( this);
      tuttleButtonsPanel.add( fgrButton);    
                   
      fglButton = new Button("FgL");
      fglButton.setActionCommand( "FgL");
      fglButton.addActionListener( this);
      tuttleButtonsPanel.add( fglButton);                       

      fgbButton = new Button("FgB");
      fgbButton.setActionCommand( "FgB");
      fgbButton.addActionListener( this);
      tuttleButtonsPanel.add( fgbButton);                 

      bgyButton = new Button("BgY");
      bgyButton.setActionCommand( "BgY");
      bgyButton.addActionListener( this);
      tuttleButtonsPanel.add( bgyButton);              

      bgwButton = new Button("BgW");
      bgwButton.setActionCommand( "BgW");
      bgwButton.addActionListener( this);
      tuttleButtonsPanel.add( bgwButton);       

      pdnButton = new Button("Pdn");
      pdnButton.setActionCommand( "Pdn");
      pdnButton.addActionListener( this);
      tuttleButtonsPanel.add( pdnButton); 
      
      pupButton = new Button("Pup");
      pupButton.setActionCommand( "Pup");
      pupButton.addActionListener( this);
      tuttleButtonsPanel.add( pupButton);       
      
      feedbackPanel = new Panel();
      feedbackPanel.setBackground( Color.white);
      feedbackLabel = new Label();
      feedbackPanel.add( feedbackLabel);
      
      this.add( tuttleButtonsPanel, "South");  
      this.add( tuttlePanel,        "Center");
      this.add( feedbackPanel,      "North");
            
      this.feedback();
   } // end init.
   
   

   public void actionPerformed( ActionEvent event) { 
   
      if ( event.getSource() instanceof Button) { 
      String itsCommand = event.getActionCommand();
      
         if ( itsCommand.equals("Exit")) { 
            System.exit( 0);
         } 
         if ( itsCommand.equals("Fwd")) { 
           theTuttle.forward( 25);
         } 
         if ( itsCommand.equals("Bwd")) { 
           theTuttle.backward( 25);
         } 
         if ( itsCommand.equals("Rtn")) { 
           theTuttle.turnRight( 30);
         } 
         if ( itsCommand.equals("Ltn")) { 
           theTuttle.turnLeft( 30);
         } 
         if ( itsCommand.equals("Rst")) { 
           theTuttle.resetTuttle();
         }        
         if ( itsCommand.equals("Clr")) { 
           theTuttle.clearTuttleArea();
         }        
         if ( itsCommand.equals("Cst")) { 
           theTuttle.clearAndReset();
         }        
         if ( itsCommand.equals("FgR")) { 
           theTuttle.setForeground( Color.red);
         }        
         if ( itsCommand.equals("FgL")) { 
           theTuttle.setForeground( Color.blue);
         }        
         if ( itsCommand.equals("FgB")) { 
           theTuttle.setForeground( Color.black);
         }        
         if ( itsCommand.equals("BgY")) { 
           theTuttle.setBackground( Color.yellow);
         }                  
         if ( itsCommand.equals("BgW")) { 
           theTuttle.setBackground( Color.white);
         }  
         if ( itsCommand.equals("Pdn")) { 
           theTuttle.setPenDown();
         }  
         if ( itsCommand.equals("Pup")) { 
           theTuttle.setPenUp();
         } // End if. 
           
         this.feedback();         
      } // End if.
   } // End actionPerformed.


   private void feedback(){    
      feedbackLabel.setText(  theTuttle.getDetails());
      feedbackPanel.doLayout();
   } // End feedback.

} // End TuttleTest.




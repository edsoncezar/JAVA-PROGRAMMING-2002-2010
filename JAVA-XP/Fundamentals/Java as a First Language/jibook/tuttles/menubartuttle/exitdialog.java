// Filename ExitDialog.java.
// Supplies a Dialog containing an exit/ yes/ no  
// question and sends ActionEvent if yes replied.
//
// Written for Java Interface book chapter 6.
// Fintan Culwin, v 0.2, August 1997.

package MenuBarTuttle;


import java.awt.*;
import java.awt.event.*;

import MessageCanvas;


class ExitDialog extends    Dialog 
                 implements ActionListener { 


private Window          itsParentWindow;
private ActionListener  itsListener;

private Panel            buttonPanel;
private MessageCanvas    message;                      
private Button           yesButton;
private Button           noButton; 

   protected ExitDialog( Frame itsParentFrame,
                         ActionListener listener) { 
                                     
      super( itsParentFrame, "Exit", true);
      this.setFont( itsParentFrame.getFont());
      this.setBackground( itsParentFrame.getBackground());
      itsParentWindow = (Window) itsParentFrame;
      itsListener = listener;

      message = new MessageCanvas( "Are you sure\nyou want to exit?");
      message.setBackground( Color.white);
      this.add( message, "Center");


      buttonPanel     = new Panel();
      buttonPanel.setBackground( Color.white);
      
      yesButton = new Button( "yes");      
      yesButton.setActionCommand("yes"); 
      yesButton.addActionListener( this); 
      buttonPanel.add( yesButton);

      noButton = new Button( "no"); 
      noButton.setActionCommand("no"); 
      noButton.addActionListener( this);            
      buttonPanel.add( noButton);
      

      this.add( buttonPanel, "South");
      this.pack();
   } // End ExitDialog constructor.
    
    
   public void setVisible( boolean showIt) { 
   
   Point         itsParentsLocation;
   Dimension     itsParentsSize;
   Point         itsLocation;
   Dimension     itsSize;

      if ( showIt) { 
         itsParentsLocation = itsParentWindow.getLocationOnScreen();
         itsParentsSize     = itsParentWindow.getSize();
         itsSize            = this.getSize();
         itsLocation        = new Point();
      
         itsLocation.x = itsParentsLocation.x + 
                         itsParentsSize.width/2 - 
                         itsSize.width/2;
         itsLocation.y = itsParentsLocation.y + 
                         itsParentsSize.height/2 - 
                         itsSize.height/2;                          
         this.setLocation( itsLocation);    
      } // End if.         
      super.setVisible( showIt);            
   } // End setVisible.

   public  void actionPerformed( ActionEvent event) {
      this.setVisible( false);
      if ( event.getActionCommand().equals( "yes")) { 
         itsListener.actionPerformed( new ActionEvent( this, 
                                      ActionEvent.ACTION_PERFORMED,
                                      "exit please"));        
      } // End if.   
   } // End actionPerformed.       
} // End exitPanel.



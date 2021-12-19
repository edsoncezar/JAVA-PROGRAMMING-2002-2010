// Filename NoteWindow.java.
// Provides the Note Windows for the StikNote applet.
// 
// Written for the JI book, Chapter 3.
// Fintan Culwin, v 0.2, August 1997.

package StikNote;

import java.awt.*;
import java.awt.event.*;

import MessageCanvas;


class NoteWindow extends Frame
                         implements ActionListener { 
                        
                        
   protected NoteWindow( String    message,
                         Component itsParent) { 
   
   Button        dismissButton;
   Panel         buttonPanel   = new Panel();
   MessageCanvas postedMessage;

   Point         itsParentsLocation;
   Dimension     itsParentsSize;
   Point         itsLocation;
   Dimension     itsSize;

      this.setTitle( "Stik Note");
      this.setFont( itsParent.getFont()); 
      this.setBackground( itsParent.getBackground()); 
      this.setForeground( itsParent.getForeground()); 
         
      postedMessage = new MessageCanvas( message);
      this.add( postedMessage, "Center");
     
      dismissButton = new Button( "OK");
      dismissButton.addActionListener( this);          
      buttonPanel.add( dismissButton); 
      this.add( buttonPanel, "South");
      this.pack();
      
      itsParentsLocation = itsParent.getLocationOnScreen();
      itsParentsSize     = itsParent.getSize();
      itsSize            = this.getSize();
      itsLocation        = new Point();
      
      itsLocation.x = itsParentsLocation.x + 
                      itsParentsSize.width/2 - 
                      itsSize.width/2;
      itsLocation.y = itsParentsLocation.y + 
                      itsParentsSize.height/2 - 
                      itsSize.height/2;                          

      this.setLocation( itsLocation);     
      this.show();      
   } // End NoteWindow constructor.
                                                              

   public void actionPerformed( ActionEvent event) {     
      this.dispose(); 
   } // end actionPerformed.
} // End NoteWindow.





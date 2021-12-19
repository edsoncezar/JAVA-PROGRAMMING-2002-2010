// Filename StikNoteWindow.java.
// Provides StikNote main window whose events 
// are listened to by the StikNote translation application.
//     
// Written for the JI book, Chapter 3.
// Fintan Culwin, v 0.1, August 1997.

package StikNote;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


class StikNoteWindow extends Object {

private TextArea  theMessage = new TextArea( 10, 20);


   protected StikNoteWindow( StikNote itsApplet) {

   Panel  buttonPanel    = new Panel();
   Button stikNoteButton = new Button( "Stik Note");
     
     theMessage = new TextArea( 10, 20);
            
     stikNoteButton.addActionListener( itsApplet);
     buttonPanel.add( stikNoteButton);

     itsApplet.setLayout( new BorderLayout());
     itsApplet.add( theMessage,  "Center");
     itsApplet.add( buttonPanel, "South");
   } // End init.


   protected String getMessage() { 
      return theMessage.getText().trim();         
   } // End getMessage.  

   protected void clearMessage() {
      theMessage.setText( "");   
   } // End clearMessage.          
} // end class PostItWindow.





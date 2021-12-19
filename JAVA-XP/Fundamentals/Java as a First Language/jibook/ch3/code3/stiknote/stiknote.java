// Filename StikNote.java.
// Contains the main action of the PostIt applet, responsible 
// for creating and posting the main window and creating and 
// posting PostItNote windows when it is activated.
// 
// Written for the JI book, Chapter 3.
// Fintan Culwin, v 0.2, August 1997.


package StikNote;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import StikNote.NoteWindow;
import StikNote.StikNoteWindow;


public class StikNote extends    Applet
                      implements ActionListener {

private StikNoteWindow mainWindow; 
                    
   public void init() { 
      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));
      this.setBackground( Color.yellow); 
      this.setForeground( Color.blue);            
      mainWindow = new StikNoteWindow( this);   
   } // End init.                
                    

   public void actionPerformed( ActionEvent event) {  
   
   NoteWindow theNote;
   String     itsContents = mainWindow.getMessage();

      if ( itsContents.length() > 0) { 
         theNote = new NoteWindow( itsContents, this);
         mainWindow.clearMessage();                                    
      } // End if.   
   } // End actionPerformed.
                       
                    
   public static void main( String args[]) { 

      Frame    frame        = new Frame("Stik Note");
      StikNote theInterface = new StikNote();

      theInterface.init();
      frame.add( theInterface, "Center");
      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // end main.
} // End Postit.                    

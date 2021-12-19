// Filename CommandLineTuttle.java.
// Supplies a command line interface for  
// the Tuttle class.
//
// Written for Java Interface book chapter 7.
// Fintan Culwin, v0.1, August 1997.

package CommandLineTuttle;

import java.awt.*;
import java.awt.event.*;

import Tuttles.TextTuttle;

class CommandLineTuttleInterface extends Panel {   

private TextArea     commandFeedback;
private TextField    commandArea;

   protected CommandLineTuttleInterface( ActionListener itsListener) { 
                  
      this.setLayout( new BorderLayout());

      commandFeedback = new TextArea( 6, 60);
      commandFeedback.setEditable( false);
      
      commandArea = new TextField( 60); 
      commandArea.addActionListener( itsListener);
       
      this.add( commandFeedback, "Center");
      this.add( commandArea,      "South");                
   } // end CommandLineTuttleInterface constructor.   

   protected void clearCommandArea() { 
       commandArea.setText( "");   
   } // End clearCommandArea.

   protected void appendFeedback( String toAppend) { 
       commandFeedback.append( toAppend);   
   } // End appendFeedback.

} // End class CommandLineTuttleInterface class.




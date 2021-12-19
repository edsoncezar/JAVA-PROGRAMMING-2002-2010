// Filename ClickCounterTranslation.java.
// Provides the interface behaviour for the interactive ClickCounter.
// Written for the Java Interface book Chapter 1.
//
// Fintan Culwin, v 2.0, August 1997.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

import ClickCounter;
import ClickCounterPresentation;


public class ClickCounterTranslation extends    Applet
                                     implements ActionListener { 
       
private final int INITIAL_STATE  = 0;
private final int MINIMUM_STATE  = 1;
private final int COUNTING_STATE = 2;
private final int MAXIMUM_STATE  = 3;
private       int theState       = INITIAL_STATE;
 
private ClickCounter             theCounter       = new ClickCounter( 0, 5);
private ClickCounterPresentation visibleInterface;

       
   public void init() { 
      this.setFont( new Font( "Serif", Font.PLAIN, 20));
      visibleInterface = new ClickCounterPresentation( this);
      visibleInterface.setValueDisplay( theCounter.countIsAsString()); 
      visibleInterface.setMinimumState();
      theState = MINIMUM_STATE;   
   } // end init.         


   public void actionPerformed( ActionEvent event) {   
 
   String buttonPressed = event.getActionCommand();
 
      if ( buttonPressed.equals( "increment")) { 
         if ( theState == MINIMUM_STATE) { 
            visibleInterface.setCountingState();
            theState = COUNTING_STATE;
         } // End if.
         theCounter.count();
         if ( theCounter.isAtMaximum()) { 
             visibleInterface.setMaximumState();
             theState = MAXIMUM_STATE;
         } // End if. 
                   
      } else if ( buttonPressed.equals( "reset")) { 
      
         theCounter.reset();
         visibleInterface.setMinimumState();      
         theState = MINIMUM_STATE;
      
      } else if ( buttonPressed.equals( "decrement")) { 
      
         if ( theCounter.isAtMaximum()) { 
            visibleInterface.setCountingState();
            theState = COUNTING_STATE;
         } // End if.      
         theCounter.unCount();
         if ( theCounter.isAtMinimum()) { 
            visibleInterface.setMinimumState();
            theState = MINIMUM_STATE;
         } // End if.          
      } // End if.
      
      visibleInterface.setValueDisplay( theCounter.countIsAsString() ); 
   } // End actionPerformed. 


     public static void main( String args[]) { 
  
        Frame        frame      = new Frame("Click Counter Demo");
        ClickCounterTranslation theInterface    
                                = new ClickCounterTranslation();
  
        theInterface.init();
        frame.add("Center", theInterface);
  
        frame.show();
        frame.setSize( frame.getPreferredSize());
     } // end main.  
} // end class ClickCounterTranslation.


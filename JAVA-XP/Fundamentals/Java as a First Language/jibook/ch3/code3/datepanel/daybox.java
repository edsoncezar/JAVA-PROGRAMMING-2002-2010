// Filename DayBox.java.
// Provides the individual boxes which contain the 
// day numbers in a MonthPanel.
//
// Written for JI book, Chapter 3 see text.
// Fintan Culwin, v 0.2, August 1997.

package DatePanel;

import java.awt.*;
import java.awt.event.*;


class DayBox extends Canvas {

private int            ordinal       = -1;
private int            theDayNumber  = 0;
private boolean        isHighlighted = false;
private boolean        isBordered    = false;
private boolean        isPrimed      = false;
private ActionListener sendToHere;


   protected DayBox( int            itsLocation,
                     ActionListener listener) { 
      super();
      ordinal = itsLocation;  
      this.enableEvents( AWTEvent.MOUSE_EVENT_MASK);
      sendToHere = listener;
   } // End DayBox constructor.


   protected int getOrdinal() { 
     return ordinal;   
   } // End ordinalIs.
    

   protected void setHighlight() {
      isHighlighted = true;
      repaint();
   } // End setHighlight.

   protected void clearHighlight() { 
      isHighlighted = false;
      repaint();      
   } // End clearHighlight.
   
   
   protected void setDayNumber( int dayNumber) { 
      theDayNumber = dayNumber;
   } // End setDayNumber
      
   protected int getDayNumber() {
      return  theDayNumber;
   } // End getDayNumber
   

   public void paint( Graphics context) { 
   
   Dimension   location;
   String      numString;
   FontMetrics metrics;
   int         stringHeight;
   int         stringWidth; 
           
      location = this.getSize();
      context.setColor( this.getBackground());    
      context.fillRect( 0, 0, 
                        location.width-1,
                        location.height-1); 
      context.setColor( this.getForeground()); 
       
      if ( theDayNumber != 0 ) {
         numString    = Integer.toString( theDayNumber);         
         metrics      = this.getFontMetrics( this.getFont());
         stringHeight = metrics.getHeight();
         stringWidth  = metrics.stringWidth( numString); 
         context.drawString( numString, 
                             (location.width  - stringWidth)/2, 
                             (stringHeight + 2));
                             
         if ( isHighlighted ) { 
            context.drawRect( 2, 2, 
                        location.width  - 4, 
                        location.height - 4);
            context.drawRect( 3, 3, 
                        location.width  - 6, 
                        location.height - 6);
         } // End if. 
         
         if ( isBordered ) { 
            context.drawRect( 1,1, 
                           location.width  - 2, 
                           location.height - 2);
         } // End if.                                    
      } // End if                                                                                                             
   } // End paint.


   protected void processMouseEvent( MouseEvent event) {   
      if ( this.getDayNumber() != 0) { 
         switch ( event.getID()) { 
          
            case MouseEvent.MOUSE_ENTERED:        
               isBordered = true;
               repaint();
               break;
                   
            case MouseEvent.MOUSE_EXITED: 
               isBordered = false;
               isPrimed   = false;
               repaint();
               break;
         
            case MouseEvent.MOUSE_PRESSED: 
               isPrimed = true;
               repaint();        
               break;
         
            case MouseEvent.MOUSE_RELEASED: 
               if ( this.isPrimed){
                  sendToHere.actionPerformed( 
                                   new ActionEvent(this, 
                                   ActionEvent.ACTION_PERFORMED,
                                   "DateSelected"));  
               }  // End if.
               break;                      
         } // End switch.   
      } // End if   
   } // End processMouseEvent.

} // End class DayBox


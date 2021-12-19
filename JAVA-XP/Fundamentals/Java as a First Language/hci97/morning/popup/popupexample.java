// Filename PopUpExample.java.
// Provides an intital example of the AWT canvas class.  
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class PopupExample extends Applet {

private PoppingDoodle   aDrawingArea;
private int      lastX = 0;
private int      lastY = 0;

   public void init() {
      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));     
      aDrawingArea = new PoppingDoodle( 200, 150);     
      this.add( aDrawingArea);
   } // End init.


   public static void main(String args[]) {

   Frame        frame      = new Frame("Pop Up example");
   PopupExample theExample = new PopupExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.
} // End class PopupExample.



class PoppingDoodle extends    Canvas 
                    implements ActionListener { 

private int       lastX = 0;
private int       lastY = 0;
private Graphics  context; 
private PopupMenu popup;
private MenuItem  first;
private MenuItem  second;

   protected PoppingDoodle( int width, int height) { 
      super();
      this.setSize( width, height);
      this.setBackground( Color.yellow);
      this.setForeground( Color.blue);
      this. enableEvents( AWTEvent.MOUSE_MOTION_EVENT_MASK |
                          AWTEvent.MOUSE_EVENT_MASK        |
                          AWTEvent.ACTION_EVENT_MASK       ); 
                          
      popup = new PopupMenu();         
      this.add( popup);  
       
      first = new MenuItem( "Clear");
      first.setActionCommand( "Clear");
      first.addActionListener( this);   
      popup.add( first); 
      
      second = new MenuItem( "Invert");
      second.setActionCommand( "Invert");
      second.addActionListener( this);   
      popup.add( second);                                                 
   } // End PoppingDoodle constructor.


   public void addNotify() { 
      super.addNotify();   
      context = this.getGraphics().create();      
   } // End addNotify.



   public void actionPerformed( ActionEvent event) {
      
   String command = new String( event.getActionCommand());
   Color hold;
   
      if ( command.equals( "Clear")) { 
         this.repaint();      
      } else if ( command.equals( "Invert")) { 
         hold = this.getBackground();
         this.setBackground( this.getForeground());
         this.setForeground( hold);
         context.setColor( hold);
         this.repaint();     
      } // End if.      
      
   } // End actionPerformed.
   
   
   protected void processMouseEvent(MouseEvent event) {

      if ( event.getID() == MouseEvent.MOUSE_PRESSED) { 
         if (event.getClickCount() == 2) {  
            popup.show( this.getParent(), event.getX(), event.getY());       
         } else {  
            lastX = event.getX();
            lastY = event.getY();               
         } // End if.   
      } // End if.         
   } // End class PoppingDoodle.
 
   
   protected void processMouseMotionEvent(MouseEvent event) { 
   
      if ( event.getID() == MouseEvent.MOUSE_DRAGGED) { 
      int currentX = event.getX();
      int currentY = event.getY();
      
         context.drawLine( lastX, lastY, currentX, currentY); 
         lastX = currentX;
         lastY = currentY;          
      } // End if.            
   } // End processMouseMotionEvent.
 
} // End class Doodle











// Filename CanvasExample.java.
// Provides an initial example of extending the AWT canvas class.  
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class CanvasExample extends Applet {

private Doodle aDoodlingArea;

   public void init() { 
      aDoodlingArea = new Doodle( 200, 150);     
      this.add( aDoodlingArea);
   } // End init.


   public static void main(String args[]) {

   Frame   frame      = new Frame("Doodle");
   CanvasExample  theExample = new CanvasExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.
} // End class CanvasExample.



class Doodle extends Canvas { 

private  int      lastX;
private  int      lastY;
private  Graphics context; 

   protected Doodle ( int width, int height) { 
      super();
      this.setSize( width, height);
      this.setBackground( Color.yellow);
      this.setForeground( Color.blue);
      this. enableEvents( AWTEvent.MOUSE_MOTION_EVENT_MASK | 
                          AWTEvent.MOUSE_EVENT_MASK);   
   } // End Doodle constructor.


   public void addNotify() { 
      super.addNotify();   
      context = this.getGraphics().create();
   } // End addNotify.


   protected void processMouseEvent(MouseEvent event) {
      if ( event.getID() == MouseEvent.MOUSE_PRESSED) { 
         lastX = event.getX();
         lastY = event.getY();                      
      } // End if.          
   } // End processMouseEvent.
 
   
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











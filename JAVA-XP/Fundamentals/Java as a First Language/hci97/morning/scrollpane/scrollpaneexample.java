// Filename ScrollPaneExample.java.
// Provides an initial example of the AWT ScrollPane class.    
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class ScrollPaneExample extends Applet {

private CrossDoodle aCrossDoodle;
private ScrollPane  aScrollPane;

   public void init() {
      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));     
      aCrossDoodle = new CrossDoodle( 500, 500);   
      aScrollPane = new ScrollPane( ScrollPane.SCROLLBARS_ALWAYS);
      aScrollPane.add( aCrossDoodle); 
      this.add( aScrollPane);
   } // End init.


   public static void main(String args[]) {

   Frame        frame      = new Frame("Scroll Pane example");
   ScrollPaneExample theExample = new ScrollPaneExample();

      theExample.init();
      frame.add(theExample, "Center");

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // End main.
} // End class ScrollPaneExample.



class CrossDoodle extends Canvas { 

   protected CrossDoodle ( int width, int height) { 
      super();
      this.setBackground( Color.yellow);
      this.setForeground( Color.blue);
      this.setSize( width, height);                          
   } // End CrossDoodle constructor.


   public void paint( Graphics context) { 
   
   int width  = this.getBounds().width;   
   int height = this.getBounds().height; 
   
      context.drawLine( 0, 0, width, height);
      context.drawLine( 0, height, width, 0);
      context.drawLine( width/2, 0, width/2, height); 
      context.drawLine( 0, height/2, width, height/2);
   } // end paint 
} // End class CrossDoodle.











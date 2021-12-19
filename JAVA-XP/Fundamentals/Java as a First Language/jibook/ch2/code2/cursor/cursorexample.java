// Filename LabelExample.java.
// Provides an example of the AWT Label class,  
// illustrating the three possible alignments. 
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, April 1996

import java.awt.*;
import java.applet.*;

public class CursorExample extends Applet {


  public void init() {

  int    maxCursors   = Cursor.MOVE_CURSOR +1;


  Label  cursorLabels[] = new Label[ maxCursors];
  String cursorNames[]  = { "Default",    "Cross Hair",  "Text", 
                            "Wait",       "South West", "South East",
                            "North West", "North East", "North",
                            "South",      "West",       "East", 
                            "Hand",       "Move"};

  Font   theFont      = new Font( "TimesRoman", Font.PLAIN, 20);

     this.setFont( theFont);
     this.setLayout( new GridLayout( 7, 2, 0, 10));
 
     for ( int index =0; index < maxCursors; index++ ) { 
        cursorLabels[ index] = new Label( cursorNames[ index]);
        cursorLabels[ index].setCursor( new Cursor( index));
        this.add( cursorLabels[ index]);
     } // End for.    
  } // end init.



   public static void main(String args[]) {

   Frame         frame      = new Frame("Cursor Example");
   CursorExample theExample = new CursorExample();

      theExample.init();
      frame.add( theExample, "Center");

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // end fun main

} // end class CursorExample.



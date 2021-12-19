// Filename LabelExample.java.
// Provides an example of the AWT Label class,  
// illustrating the three possible alignments. 
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, April 1996

import java.awt.*;
import java.applet.*;

public class LabelExample extends Applet {


  public void init() {


  Label  leftLabel    = new Label( "Alignment left",   Label.LEFT );
  Label  centerLabel  = new Label( "Alignment center", Label.CENTER );
  Label  rightLabel   = new Label( "Alignment right",  Label.RIGHT );
  Font   theFont      = new Font( "TimesRoman", Font.PLAIN, 20);

     this.setFont( theFont);
     this.setLayout( new GridLayout( 3, 1, 0, 10));
     
     this.add( leftLabel);
     this.add( centerLabel);
     this.add( rightLabel);     
  } // end init()



   public static void main(String args[]) {

   Frame        frame      = new Frame("Label Example demo");
   LabelExample theExample = new LabelExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // end fun main

} // end class LabelExample











// Filename CheckExample.java.
// Provides an example of the AWT CheckBox class,  
// configured as a check box buttons. 
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.applet.*;
import java.awt.event.*;


public class CheckExample extends Applet
                          implements ItemListener {

private Checkbox  boldButton;
private Checkbox  italicButton;
private Checkbox  underlineButton;
private Checkbox  smallcapsButton;

   public void init() {
      this.setLayout( new GridLayout( 2, 2, 5, 5));

      boldButton      = new Checkbox( "Bold");  
      boldButton.addItemListener( this); 
      this.add( boldButton);
      
      italicButton    = new Checkbox( "Italic");
      italicButton.addItemListener( this); 
      this.add( italicButton);
      
      underlineButton = new Checkbox( "Underline");
      underlineButton.addItemListener( this); 
      this.add( underlineButton);
      
      smallcapsButton = new Checkbox( "Small Capitals");      
      smallcapsButton.addItemListener( this); 
      this.add( smallcapsButton);     
   } // End init.


   public void itemStateChanged(ItemEvent event) {   
      System.out.print( "Item Selectable is "); 
      if ( event.getItemSelectable() == boldButton) { 
         System.out.println( "bold Button");
      } else if ( event.getItemSelectable() == italicButton) { 
         System.out.println( "italic Button");   
      } else if ( event.getItemSelectable() == underlineButton) { 
         System.out.println( "undesline Button"); 
      } else if ( event.getItemSelectable() == smallcapsButton) { 
         System.out.println( "small caps Button");                       
      } // End if.
      
      
      System.out.println( "Item is " + event.getItem());
      
      System.out.print( "State Change is ");
      if ( event.getStateChange() == ItemEvent.SELECTED) { 
         System.out.println( "Selected");
      } else { 
         System.out.println( "Deselected");
      } // End if. 
      if ( event.getID() == ItemEvent.ITEM_STATE_CHANGED ) {       
         System.out.println( "ID is ITEM_STATE_CHANGED."); 
      } // End if.         
      System.out.println( "\n"); 
   } // End itemStateChanged.


   public static void main(String args[]) {

   Frame        frame      = new Frame("Check Box demo");
   CheckExample theExample = new CheckExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.
} // End class CheckExample











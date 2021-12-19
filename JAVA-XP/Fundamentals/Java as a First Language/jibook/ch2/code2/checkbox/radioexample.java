// Filename RadioExample.java.
// Provides an example of the AWT CheckBox class,  
// configured as radio buttons. 
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class RadioExample extends    Applet 
                          implements ItemListener { 
                                                    
private Checkbox  leftButton;   
private Checkbox  rightButton;
private Checkbox  justifyButton;
private Checkbox  centerButton;

   public void init() {

   CheckboxGroup theGroup  = new CheckboxGroup();

      this.setLayout( new GridLayout( 2, 2, 5, 5));
      
      leftButton    = new Checkbox( "Left", false, theGroup);  
      leftButton.addItemListener( this);
      this.add( leftButton);
      
      rightButton   = new Checkbox( "Right", false, theGroup);
      rightButton.addItemListener( this);
      this.add( rightButton);
      
      justifyButton = new Checkbox( "Justify", true,  theGroup);
      justifyButton.addItemListener( this);
      this.add( justifyButton);
      
      centerButton  = new Checkbox( "Centre", false, theGroup);
      centerButton.addItemListener( this);
      this.add( centerButton);       
   } // End init.


   public  void itemStateChanged(ItemEvent event) {   
      System.out.print( "Item Selectable is "); 
      if ( event.getItemSelectable() == leftButton) { 
         System.out.println( "left Button");
      } else if ( event.getItemSelectable() == rightButton) { 
         System.out.println( "right Button");   
      } else if ( event.getItemSelectable() == justifyButton) { 
         System.out.println( "justify Button"); 
      } else if ( event.getItemSelectable() == centerButton) { 
         System.out.println( "center Button");                       
      } // End if.
      
      
      System.out.println( "Item is " + event.getItem());
      
      System.out.print( "State Change is ");
      if ( event.getStateChange() == ItemEvent.SELECTED) { 
         System.out.println( "Selected");
      } else { 
         System.out.println( "Deselected");
      } // End if. 
      if ( event.getID() == ItemEvent.ITEM_STATE_CHANGED ) {       
         System.out.println( "Id is ITEM_STATE_CHANGED."); 
      } // End if.         
      System.out.println( "\n"); 
   } // End itemStateChanged.

          
   public static void main(String args[]) {

   Frame        frame      = new Frame("Radio Button demo");
   RadioExample theExample = new RadioExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // End main.
} // End class RadioExample.











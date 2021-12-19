// Filename ChoiceExample.java.
// Provides an example of the AWT Choice class.  
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.


import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class ChoiceExample extends    Applet 
                           implements ItemListener { 

private Choice dayChoice;


   public void init() {

   Label  promptLabel;


     dayChoice   = new Choice();     
     dayChoice.addItemListener( this); 
     
     dayChoice.addItem( "Sunday");
     dayChoice.addItem( "Monday");
     dayChoice.addItem( "Tuesday");
     dayChoice.addItem( "Wednesday");
     dayChoice.addItem( "Thursday");
     dayChoice.addItem( "Friday");
     dayChoice.addItem( "Saturday");
     
     promptLabel = new Label( "Today is ", Label.RIGHT );
     
     this.add( promptLabel);     
     this.add( dayChoice);
   } // End init.


   public  void itemStateChanged(ItemEvent event) {      

      if ( event.getItemSelectable() == dayChoice) { 
         System.out.println( "Item Selectable is dayChoice.");           
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

   Frame        frame      = new Frame("Choice Example demo");
   ChoiceExample theExample = new ChoiceExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.

} // end class ChoiceExample.











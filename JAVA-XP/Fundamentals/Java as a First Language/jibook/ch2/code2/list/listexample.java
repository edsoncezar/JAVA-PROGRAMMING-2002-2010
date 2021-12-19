// Filename ListExample.java.
// Provides an example of the AWT List class.  
// Written for the Java interface book Chapter 2 - see text.
// This version with a scroll bar.
//
// Fintan Culwin, v 0.2, August 1997.


import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class ListExample extends    Applet 
                         implements ItemListener { 

   public void init() {

   List  cityList;   
   Label promptLabel;

      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));         
      this.setLayout( new BorderLayout());
     
      cityList = new List( 7, false);
      cityList.addItemListener( this);
      
      cityList.addItem( "London");
      cityList.addItem( "Paris");
      cityList.addItem( "Barcelona");
      cityList.addItem( "Athens");
      cityList.addItem( "Rome");
      cityList.addItem( "Istanbul");
      cityList.addItem( "Berlin");

      promptLabel = new Label( "Which city have you visited?"); 
     
      this.add( promptLabel, "North");     
      this.add( cityList,    "Center");     
  } // End init.


  public void itemStateChanged(ItemEvent event) {  
  
  List theList = (List) event.getItemSelectable();  
  
      if ( theList == cityList) { 
         System.out.println( "Item Selectable is cityList.");           
      } // End if.
            
      System.out.println( "Item index  is " + theList.getSelectedIndex());
      System.out.println( "Item string is " + theList.getSelectedItem());
      
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

   Frame        frame     = new Frame("List Example demo");
   ListExample theExample = new ListExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // end fun main

} // End ListExample.











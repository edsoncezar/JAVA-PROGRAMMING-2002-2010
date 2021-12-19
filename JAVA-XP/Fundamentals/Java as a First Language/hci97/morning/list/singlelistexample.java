// Filename SingleListExample.java.
// Provides an example of the AWT List class.  
// Written for the Java interface book Chapter 2 - see text.
// This version with a scroll bar.
//
// Fintan Culwin, v 0.2, August 1997.


import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class SingleListExample extends    Applet 
                               implements ItemListener { 

private List  cityList;   
private Label promptLabel;
private Panel    listPanel;
private Panel    topPanel;
private TextArea feedback;

   public void init() {

     this.setBackground( Color.yellow);
     this.setFont( new Font( "Times", Font.BOLD, 24)); 
     this.setLayout( new BorderLayout());
     
     listPanel = new Panel( new BorderLayout());

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
     
      listPanel.add( promptLabel, "North");     
      listPanel.add( cityList,    "Center");     

      topPanel = new Panel();
      topPanel.add( listPanel);
      this.add( topPanel, "Center");

      feedback = new TextArea( 5, 60);
      this.add( feedback, "South");  
  } // End init.


  public void itemStateChanged(ItemEvent event) {  
  
   StringBuffer message = new StringBuffer("");

      feedback.setText("");
      List theList = (List) event.getItemSelectable();  
  
      if ( theList == cityList) { 
         message.append( "   Item Selectable is cityList.");           
      } // End if.
            
      message.append( "\n   Item index  is " + theList.getSelectedIndex());
      message.append( "\n   Item string is " + theList.getSelectedItem());
      
      message.append( "\n   State Change is ");
      if ( event.getStateChange() == ItemEvent.SELECTED) { 
         message.append( "Selected");
      } else { 
         message.append( "Deselected");
      } // End if. 
      if ( event.getID() == ItemEvent.ITEM_STATE_CHANGED ) {       
         message.append( "\n   ID is ITEM_STATE_CHANGED."); 
      } // End if.         
      feedback.setText( message.toString());      
  } // End itemStateChanged.
  


   public static void main(String args[]) {

   Frame        frame     = new Frame("List Example demo");
   SingleListExample theExample = new SingleListExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // end fun main

} // End ListExample.











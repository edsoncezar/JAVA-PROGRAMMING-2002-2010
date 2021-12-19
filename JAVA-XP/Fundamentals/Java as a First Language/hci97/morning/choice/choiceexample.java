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

private Panel    dayPanel;
private Panel    topPanel;
private TextArea feedback;

   public void init() {

   Label  promptLabel;

     this.setBackground( Color.yellow);
     this.setFont( new Font( "Times", Font.BOLD, 24)); 
     this.setLayout( new BorderLayout());

     dayPanel = new Panel();

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
     
     dayPanel.add( promptLabel);     
     dayPanel.add( dayChoice);

      topPanel = new Panel();
      topPanel.add( dayPanel);
      this.add( topPanel, "Center");

      feedback = new TextArea( 4, 60);
      this.add( feedback, "South");  
   } // End init.


   public  void itemStateChanged(ItemEvent event) {      


   StringBuffer message = new StringBuffer("");

      feedback.setText("");

      
      if ( event.getItemSelectable() == dayChoice) { 
         message.append( "   Item Selectable is dayChoice.");           
      } // End if.
      
      
      message.append( "\n   Item is " + event.getItem());
      
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

   Frame        frame      = new Frame("Choice Example demo");
   ChoiceExample theExample = new ChoiceExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.

} // end class ChoiceExample.











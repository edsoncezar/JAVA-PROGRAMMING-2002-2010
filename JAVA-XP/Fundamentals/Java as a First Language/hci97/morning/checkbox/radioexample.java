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
 

private Panel    radioPanel;
private Panel    topPanel;
private TextArea feedback;
                                                   
private Checkbox  leftButton;   
private Checkbox  rightButton;
private Checkbox  justifyButton;
private Checkbox  centerButton;

   public void init() {

   CheckboxGroup theGroup  = new CheckboxGroup();

      this.setBackground( Color.yellow);
      this.setFont( new Font( "Times", Font.BOLD, 24)); 
      this.setLayout( new BorderLayout());
 
      radioPanel = new Panel( new GridLayout( 2, 2, 5, 5));
     
      leftButton    = new Checkbox( "Left", false, theGroup);  
      leftButton.addItemListener( this);
      radioPanel.add( leftButton);
      
      rightButton   = new Checkbox( "Right", false, theGroup);
      rightButton.addItemListener( this);
      radioPanel.add( rightButton);
      
      justifyButton = new Checkbox( "Justify", true,  theGroup);
      justifyButton.addItemListener( this);
      radioPanel.add( justifyButton);
      
      centerButton  = new Checkbox( "Centre", false, theGroup);
      centerButton.addItemListener( this);
      radioPanel.add( centerButton); 

      topPanel = new Panel();
      topPanel.add( radioPanel);
      this.add( topPanel, "Center");

      feedback = new TextArea( 4, 60);
      this.add( feedback, "South");  
      
   } // End init.


   public  void itemStateChanged(ItemEvent event) {   

   StringBuffer message = new StringBuffer("");

      feedback.setText("");

      message.append( "   Item Selectable is "); 
      if ( event.getItemSelectable() == leftButton) { 
         message.append( "left Button");
      } else if ( event.getItemSelectable() == rightButton) { 
         message.append( "right Button");   
      } else if ( event.getItemSelectable() == justifyButton) { 
         message.append( "justify Button"); 
      } else if ( event.getItemSelectable() == centerButton) { 
         message.append( "center Button");                       
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

   Frame        frame      = new Frame("Radio Button demo");
   RadioExample theExample = new RadioExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // End main.
} // End class RadioExample.











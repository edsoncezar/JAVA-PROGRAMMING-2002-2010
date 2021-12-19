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


private Panel    checkPanel;
private Panel    topPanel;
private TextArea feedback;

private Checkbox  boldButton;
private Checkbox  italicButton;
private Checkbox  underlineButton;
private Checkbox  smallcapsButton;

   public void init() {
      this.setBackground( Color.yellow);
      this.setFont( new Font( "Times", Font.BOLD, 24)); 
      this.setLayout( new BorderLayout());

      checkPanel = new Panel( new GridLayout( 2, 2, 5, 5));

      boldButton      = new Checkbox( "Bold");  
      boldButton.addItemListener( this); 
      checkPanel.add( boldButton);
      
      italicButton    = new Checkbox( "Italic");
      italicButton.addItemListener( this); 
      checkPanel.add( italicButton);
      
      underlineButton = new Checkbox( "Underline");
      underlineButton.addItemListener( this); 
      checkPanel.add( underlineButton);
      
      smallcapsButton = new Checkbox( "Small Capitals");      
      smallcapsButton.addItemListener( this); 
      checkPanel.add( smallcapsButton);   

      topPanel = new Panel();
      topPanel.add( checkPanel);
      this.add( topPanel, "Center");

      feedback = new TextArea( 4, 60);
      this.add( feedback, "South");  
   } // End init.


   public void itemStateChanged(ItemEvent event) {   

   StringBuffer message = new StringBuffer("");

      feedback.setText("");

      message.append( "   Item Selectable is "); 
      if ( event.getItemSelectable() == boldButton) { 
         message.append( "bold Button");
      } else if ( event.getItemSelectable() == italicButton) { 
         message.append( "italic Button");   
      } else if ( event.getItemSelectable() == underlineButton) { 
         message.append( "underline Button"); 
      } else if ( event.getItemSelectable() == smallcapsButton) { 
         message.append( "small caps Button");                       
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

   Frame        frame      = new Frame("Check Box demo");
   CheckExample theExample = new CheckExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.
} // End class CheckExample











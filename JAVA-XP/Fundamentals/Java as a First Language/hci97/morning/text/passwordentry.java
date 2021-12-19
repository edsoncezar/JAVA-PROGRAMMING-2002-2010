// Filename PasswordEntry.java.
// Provides an initial example of the AWT TextField class.  
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;


public class PasswordEntry extends    Applet 
                           implements ActionListener {

private Panel     passPanel; 
private TextField passwordField;
private TextField promptField;

   public void init() {
      this.setFont( new Font( "Times", Font.PLAIN, 24));
      this.setBackground( Color.yellow);
      passwordField  = new TextField( 8);
      passwordField.setEchoChar( '*');
      passwordField.addActionListener( this);
      
      promptField = new TextField( "Please enter the magic word ");
      promptField.setEditable( false);

      passPanel = new Panel();     
      passPanel.add( promptField); 
      passPanel.add( passwordField);  

      this.add( passPanel);  
   } // End init.


   public  void actionPerformed( ActionEvent event){ 
   
   String attempt = new String( passwordField.getText());
     
        if ( attempt.equals( "fintan")) { 
           promptField.setText( "Welcome to the magic garden.");
           passwordField.setVisible( false);;
           promptField.getParent().doLayout();
        } else { 
           passwordField.setText(""); 
        } // End if. 
   } // End actionPerformed.
  

   public static void main(String args[]) {

   Frame         frame      = new Frame("Password Entry");
   PasswordEntry theExample = new PasswordEntry();

      theExample.init();
      frame.add( theExample, "Center");

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.
} // end class PasswordEntry.











// Filename DecimalToHex.java.
// Provides an example of the AWT Scrollbar class.  
// Written for the Java interface book Chapter 2 - see text.
//
// Fintan Culwin, v 0.2, August 1997.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class DecimalToHex extends    Applet 
                          implements AdjustmentListener { 

private Scrollbar scroller;
private Label     decimalLabel;
private Label     hexLabel;

   public void init() {
      this.setFont( new Font( "TimesRoman", Font.PLAIN, 20));
      this.setLayout( new GridLayout( 3, 1, 5, 5));
     
      scroller = new Scrollbar( Scrollbar.HORIZONTAL, 0, 1, 0, 255);
      scroller.setBlockIncrement( 10);                                            
      scroller.addAdjustmentListener( this);
                                                   
      decimalLabel = new Label();
      hexLabel     = new Label();
          
      this.add( decimalLabel);     
      this.add( scroller);
      this.add( hexLabel);     

      this.update();
   } // End init.


   public void adjustmentValueChanged( AdjustmentEvent event) { 
      this.update();  
   } // End adjustmentValueChanged.


   protected void update() { 
   
   int     theValue     = scroller.getValue();
   String  decimalValue = new String( Integer.toString( theValue, 10));
   String  hexValue     = new String( Integer.toString( theValue, 16));
    
   String  decimalString = new String( "Decimal : " +  decimalValue);
   String  hexString     = new String( "Hex : " +  hexValue);

        decimalLabel.setText(  decimalString);
        hexLabel.setText(      hexString);
   } // End update.


   public static void main(String args[]) {

   Frame        frame      = new Frame("Decimal To Hex");
   DecimalToHex theExample = new DecimalToHex();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // End main.

} // end class DecimalToHex.




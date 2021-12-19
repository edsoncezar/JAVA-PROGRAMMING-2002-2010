// Filename DatePanelDemonstration.java.
// Demonstration harness for the  DatePanel.
//
// Written for the Java Interface book Chapter 3.
// Fintan Culwin, v 0.2, August 1997.

package DatePanel;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

import DatePanel.DatePanel;


public class DatePanelDemonstration extends    Applet 
                                    implements ActionListener {

DatePanel  aDatePanel;

   public void init() {    
      this.setFont( new Font( "Times", Font.BOLD, 20));


      aDatePanel = new DatePanel();
      aDatePanel.setActionCommand( "date panel demo");
      aDatePanel.addActionListener( this);
      this.add( aDatePanel);          
   } // end init()

   public  void actionPerformed( ActionEvent event)  { 
      System.out.println( "Action Event detected by demonstration harness.");
      System.out.println( aDatePanel.yearIs()  + "/" +
                          aDatePanel.monthIs() + "/" +
                          aDatePanel.dayIs());                     
   } // End action.                        


   public static void main(String args[]) {

   Frame        frame      = new Frame("Date Panel Demonstration");
   DatePanelDemonstration theTest   = new DatePanelDemonstration();
   
      theTest.init();
      frame.add("Center", theTest);

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // end fun main

} // end class DatePanelDemonstration











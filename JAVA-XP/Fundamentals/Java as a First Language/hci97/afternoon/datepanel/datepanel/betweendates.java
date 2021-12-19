// Filename BetweenDates.java.
// Demonstration artefact for the DatePanel.
//
// Written for the Java Interface book Chapter 3.
// Fintan Culwin, v 0.2, August 1997.

package DatePanel;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

import DatePanel.DatePanel;
import JulianDates.ArithmeticJulianDate;


public class BetweenDates extends Applet 
                                  implements ActionListener {

DatePanel  fromDatePanel;
DatePanel  toDatePanel;
Label      between;

   public void init() {    

   Color fromColour = new Color( 255, 200, 255);
   Color toColour   = new Color( 200, 255, 255);

   Panel topPanel    = new Panel();
   Panel fromPanel   = new Panel( new BorderLayout());
   Panel toPanel     = new Panel( new BorderLayout());
   Panel bottomPanel = new Panel();

   Label fromLabel;
   Label toLabel;

      this.setLayout ( new BorderLayout());

      fromPanel.setBackground( fromColour);
      fromLabel = new Label( "from");
      fromLabel.setFont( new Font( "Times", Font.BOLD, 20));
      fromLabel.setAlignment( Label.CENTER);

      fromDatePanel = new DatePanel();
      fromDatePanel.setActionCommand( "from");
      fromDatePanel.addActionListener( this);
      fromDatePanel.setBackground( fromColour);

      fromPanel.add( fromLabel, "North");
      fromPanel.add( fromDatePanel, "Center");

      toPanel.setBackground( toColour);
      toLabel = new Label( "to");
      toLabel.setFont( new Font( "Times", Font.BOLD, 20));
      toLabel.setAlignment( Label.CENTER);

      toDatePanel = new DatePanel();
      toDatePanel.setActionCommand( "to");
      toDatePanel.addActionListener( this);
      toDatePanel.setBackground( toColour);

      toPanel.add( toLabel, "North");
      toPanel.add( toDatePanel, "Center");

      between = new Label( "xxxxxxxxxxxxxxxxxxx"); 
      between.setAlignment( Label.CENTER); 
      between.setFont( new Font( "Times", Font.BOLD, 20));

      topPanel.add( fromPanel);
      topPanel.add( toPanel);

      bottomPanel.add( between);

      this.add( topPanel, "Center"); 
      this.add( bottomPanel, "South");  
      this.actionPerformed( new  ActionEvent( this, 
                                              ActionEvent.ACTION_PERFORMED,
                                              "Date Selected"));       
   } // end init()



   public  void actionPerformed( ActionEvent event)  { 

   ArithmeticJulianDate fromDate = 
      new ArithmeticJulianDate( fromDatePanel.yearIs(),
                                fromDatePanel.monthIs(),
                                fromDatePanel.dayIs());
   ArithmeticJulianDate toDate = 
      new ArithmeticJulianDate( toDatePanel.yearIs(),
                                toDatePanel.monthIs(),
                                toDatePanel.dayIs());

   Long betweenDays = new Long( toDate.daysBetween( fromDate));

   String toShow = new String( betweenDays + " days.");

      between.setText( toShow);
               
   } // End action.                        


   public static void main(String args[]) {

   Frame        frame     = new Frame("Date Panel Demonstration");
   BetweenDates theTest   = new BetweenDates();
   
      theTest.init();
      frame.add("Center", theTest);

      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // end fun main

} // end class DatePanelDemonstration











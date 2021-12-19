// Filename DatePanel.java.
// Provides an interactive calendar panel allowing
// safe an unambiguous input of a calendar date.
//
// Written for JI book, Chapter 3 see text.
// Fintan Culwin, v 0.2, August 1997.

package DatePanel;

import java.awt.*;
import java.awt.event.*;

import DatePanel.DateUtility;
import DatePanel.MonthPanel;

public class DatePanel extends    Panel 
                       implements ActionListener, ItemListener { 

private MonthPanel monthPanel;
private Choice     centuryChoice;
private Choice     decadeChoice;
private Choice     yearChoice;
private Choice     monthChoice;

String         actionCommand = null;
ActionListener itsListener;

private static String monthNames[] = { "Jan", "Feb", "Mar", "Apr",
                                       "May", "Jne", "Jly", "Aug", 
                                       "Sep", "Oct", "Nov", "Dec" };

   public DatePanel() {
   
      this( DateUtility.yearIs(),
            DateUtility.monthIs(),
            DateUtility.dayOfMonthIs());
   } // End DatePanel default constructor.

                                                                           
   public DatePanel( int year, int month, int day) {
  
   int    thisOne;
   Panel  topPanel        = new Panel();
   Panel  topLeftPanel    = new Panel();
   Panel  topRightPanel   = new Panel();   
   
     this.setLayout(          new BorderLayout( 4, 4));
     topPanel.setLayout(      new GridLayout( 1, 2, 4, 4));
     topLeftPanel.setLayout(  new FlowLayout( FlowLayout.CENTER, 4, 4));
     topRightPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 4, 4));
     
     monthPanel = new MonthPanel( this);
     
     centuryChoice  = new Choice();
     centuryChoice.addItemListener( this);
     centuryChoice.addItem( "19");
     centuryChoice.addItem( "20");
     centuryChoice.addItem( "21");
     
     decadeChoice = new Choice();
     decadeChoice.addItemListener( this);

     yearChoice   = new Choice();
     yearChoice.addItemListener( this);

     for ( thisOne = 0; thisOne < 10; thisOne++){ 
         decadeChoice.addItem( Integer.toString( thisOne));
         yearChoice.addItem( Integer.toString( thisOne));
     } // End for.

     monthChoice   = new Choice();
     monthChoice.addItemListener( this);
     for ( thisOne = 0; thisOne < 12; thisOne++){ 
         monthChoice.addItem( monthNames[ thisOne]);
     } // End for.
     
     topLeftPanel.add( centuryChoice);
     topLeftPanel.add( decadeChoice);
     topLeftPanel.add( yearChoice);

     topRightPanel.add( monthChoice);

     topPanel.add( topLeftPanel);
     topPanel.add( topRightPanel);    
     this.add( topPanel,   "North");
     this.add( monthPanel, "Center");
     
     this.setDate( year, month, day);
   } // End DatePanel constructor.



   public void setDate( int year, 
                        int month,
                        int dayOfMonth) { 
                                                  
       centuryChoice.select( (year / 100) - 19);
       decadeChoice.select(  (year % 100) / 10);
       yearChoice.select( year % 10);
       monthChoice.select( month -1);
       monthPanel.reConfigure( year, month, dayOfMonth);
       dateSelected();
   } // end setDate.


   public int yearIs() {    
      return Integer.valueOf( centuryChoice.getSelectedItem() +   
                              decadeChoice.getSelectedItem() +
                              yearChoice.getSelectedItem()
                            ).intValue();
   } // End yearIs.


   public int monthIs(){ 
      return monthChoice.getSelectedIndex() +1;
   } // End monthIs.


   public int dayIs(){ 
      return monthPanel.dayIs();
   } // End dayIs.


   public void actionPerformed( ActionEvent event) { 
      dateSelected();
   } // end actionPerformed.    
   
   
   public void itemStateChanged( ItemEvent event) { 
      monthPanel.reConfigure( this.yearIs(), this.monthIs(), 
                              this.dayIs()); 
      //this.updatePanel();
      dateSelected();   
   } // end itemStateChanged.






    public void setActionCommand( String command) {
        actionCommand = command;
    } // End setActionCommand.
    
    public String getActionCommand() {
       if ( actionCommand == null) { 
          return "Date Panel";
       } else { 
          return actionCommand;
       } // End if.   
    } // End getActionCommand.


    public void addActionListener(ActionListener listener) {
       itsListener = AWTEventMulticaster.add( itsListener, listener);
    } // End addActionListener.


    public void removeActionListener(ActionListener listener) {
       itsListener = AWTEventMulticaster.remove( 
                                                 itsListener, listener);
    } // End removeActionListener.


    private void dateSelected() { 
       if ( itsListener != null) {   
          itsListener.actionPerformed( new ActionEvent(this, 
                                           ActionEvent.ACTION_PERFORMED,
                                           this.getActionCommand())); 
      } // End if.                                              
   } // End dateSelected.  
} // End class DatePanel.








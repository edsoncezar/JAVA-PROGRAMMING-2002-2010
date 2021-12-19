// Filename MonthPanel.java.
// Provides an Panel which can be configured to show
// the pattern of days in any particular month.
//
// Written for JI book, Chapter 3 see text.
// Fintan Culwin, v 0.2, August 1997.

package DatePanel;

import java.awt.*;
import java.awt.event.*;

import DatePanel.DateUtility;
import DatePanel.DayBox;


class MonthPanel extends    Panel 
                 implements ActionListener {

private static final int MAX_BOXES = 37;

private DayBox           dayBoxes[]  = new DayBox[ MAX_BOXES];
private int              highlighted;
private int              theFirstBox;
private ActionListener   passUpToHere;

private static String dayNames[] = { "Sun", "Mon", "Tue", "Wed", 
                                     "Thu", "Fri", "Sat"  };


   protected MonthPanel( ActionListener listener) {

   int   thisOne; 
   Label dayLabels[]  = new Label[ 7];
   
      this.setLayout( new GridLayout( 7, 7, 0, 0));
      for ( thisOne = 0; thisOne < 7; thisOne++) { 
         dayLabels[ thisOne] = new Label( dayNames[ thisOne],
                                          Label.CENTER);
         this.add( dayLabels[ thisOne]);                                 
      } // End for.       

      for ( thisOne = 0; thisOne < MAX_BOXES; thisOne++) { 
         dayBoxes[ thisOne] = new DayBox( thisOne, this);
         this.add( dayBoxes[ thisOne] );
      } // End for.
       
      passUpToHere = listener;
   } // End MonthPanel constructor.
   
   
   public void reConfigure( int year, int month, int day) { 
   
   int maxDay   = DateUtility.daysThisMonthIs(   year, month);
   int startDay = DateUtility.firstDayOfMonthIs( year, month);

   int thisOne;
System.out.println( "setting " + year + "/" + month + "/" +  day);  
      theFirstBox = startDay;
      if ( day > maxDay) { 
         day = maxDay;
      } //End if.

      dayBoxes[ highlighted].clearHighlight();

      for ( thisOne = 0; thisOne < MAX_BOXES; thisOne++) { 
         if ( (thisOne <  startDay) ||
              (thisOne >= (startDay + maxDay)) ){     
            dayBoxes[ thisOne].setDayNumber( 0);  
         } else {      
            dayBoxes[ thisOne].setDayNumber( thisOne - startDay +1);
         } // End if    
         dayBoxes[ thisOne].repaint();
      } // End for.
      
      dayBoxes[ theFirstBox + day -1].setHighlight();
      highlighted = theFirstBox + day -1;       
   } // End reConfigure. 


   public void actionPerformed( ActionEvent event)  { 
      ((DayBox) event.getSource()).setHighlight();
      dayBoxes[ highlighted].clearHighlight();
      highlighted = ((DayBox) event.getSource()).getOrdinal(); 
      passUpToHere.actionPerformed( event);
   } // End actionPerformed.    


   public int dayIs(){ 
      return highlighted - theFirstBox +1;
   } // End dayIs;
} // End class MonthPanel.



// Filename RoomMonitorGUI.java.
// Provides an interactive interface for the RoomMonitor
// class. 
//
// Written for the Java book Chapter 16 - see text.
// Fintan Culwin, v 0.1, September 1996.


import java.awt.*;
import java.applet.*;

import Counters.OutputFormatter;
import Counters.RoomMonitor;


 public class RoomMonitorGUI extends Applet {

 private RoomMonitor theMonitor  = new RoomMonitor();


 private static final int INITIAL_STATE  = 0;
 private static final int RESET_STATE    = 1;
 private static final int COUNTING_STATE = 2;
 private static final int MINIMAL_STATE  = 3;
 private static final int MAXIMAL_STATE  = 4;
 private static int       currentState   = INITIAL_STATE;
 
 private Button enterButton  = new Button( "+");
 private Button resetButton  = new Button( "0");
 private Button leaveButton  = new Button( "-");
 
 private Label  countDisplay = new Label();  
 private Label  maxDisplay   = new Label();  
 private Label  totalDisplay = new Label();  
 
   public void init() {

   Panel  displayPanel = new Panel();
   Panel  controlPanel = new Panel();
   Label  countLabel   = new Label( "Current ");
   Label  maxLabel     = new Label( "Max ");
   Label  totalLabel   = new Label( "Total ");

   Font   theFont    = new Font( "TimesRoman", Font.PLAIN, 20);
     this.setFont( theFont);
     this.setBackground( Color.white); 
       
      this.setLayout( new BorderLayout( 0, 0));
      displayPanel.setLayout( new GridLayout(3, 2, 0, 1));
 
      displayPanel.add( countLabel);
      displayPanel.add( countDisplay);
      displayPanel.add( maxLabel);
      displayPanel.add( maxDisplay);
      displayPanel.add( totalLabel);
      displayPanel.add( totalDisplay);
      this.add( "Center", displayPanel);

      controlPanel.add( enterButton);
      controlPanel.add( resetButton);
      controlPanel.add( leaveButton);
      this.add( "South", controlPanel);
        
      currentState = resetState();  
      this.updateDisplays();
   } // End init.


   private void updateDisplays(){

   int theCount   = theMonitor.numberCurrentlyInRoomIs();
   int maxCount   = theMonitor.maxEverInRoomIs();
   int totalCount = theMonitor.totalNumberEnteredIs();
   
      countDisplay.setText( 
                   OutputFormatter.formatLong( theCount,
                   4, true, OutputFormatter.DECIMAL));
      maxDisplay.setText( 
                   OutputFormatter.formatLong( maxCount,
                   4, true, OutputFormatter.DECIMAL));
      totalDisplay.setText( 
                   OutputFormatter.formatLong( totalCount,
                   4, true, OutputFormatter.DECIMAL));
    } // End updateDisplays.


    public boolean action( Event event, Object object) {
       if (event.target instanceof Button) {
          this.transition( (Button) event.target);
          return true;
       } else {   
          return false;
       } // End if.   
    } // End action.


    private void transition( Button pressed){ 
    
      if ( pressed == enterButton) {           
          if ( currentState == RESET_STATE) {
             currentState = countingState();
          } else if ( currentState == MINIMAL_STATE) {
             currentState = countingState();
          } else if ( isPreMaximal()) { 
                currentState = maximalState();
          } // End if.
          theMonitor.enterRoom();
       
       } else  if ( pressed == leaveButton) { 
          if ( currentState == MAXIMAL_STATE) {
             currentState = countingState();
          } else if ( isPreMinimal()) { 
             currentState = minimalState();
          } // End if.
          theMonitor.leaveRoom();      
                 
       } else { 
           currentState = resetState();       
           theMonitor.reset();       
       } // End if.
       updateDisplays();    
    } // End transition.


    private int resetState(){
       enterButton.enable();
       resetButton.disable();
       leaveButton.disable();
       return RESET_STATE;
    } // End resetState.

    private int countingState(){
       enterButton.enable();
       resetButton.enable();
       leaveButton.enable();
       return COUNTING_STATE;
    } // End resetState.

    private int minimalState(){
       enterButton.enable();
       resetButton.enable();
       leaveButton.disable();
       return MINIMAL_STATE;
    } // End resetState.

    private int maximalState(){
       enterButton.disable();
       resetButton.enable();
       leaveButton.enable();
       return MAXIMAL_STATE;
    } // End resetState.


    private boolean isPreMinimal() { 
       return ( theMonitor.numberCurrentlyInRoomIs() - 
                theMonitor.minimumIs()) == 1;
    } // End isPreMinimal.

    private boolean isPreMaximal() { 
       return( theMonitor.maximumIs() - 
               theMonitor.numberCurrentlyInRoomIs() ) == 1;
    } // End isPreMaximal.


   public static void main(String args[]) {

   Frame        frame          = new Frame("Room Monitor demo");
   RoomMonitorGUI theInterface = new RoomMonitorGUI();

         theInterface.init();
         frame.add("Center", theInterface);

         frame.show();
         frame.resize( frame.preferredSize());
   } // end fun main

} // end class RoomMonitorGUI.






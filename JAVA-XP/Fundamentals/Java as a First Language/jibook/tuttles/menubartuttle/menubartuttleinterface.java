// Filename MenuBarTuttleInterface.java.
// Supplies a Main application pull down menu  
// interface to the Tuttle class.
//
// Written for Java Interface book chapter 5.
// Fintan Culwin, v0.1, August 1997.

package MenuBarTuttle;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class MenuBarTuttleInterface extends Object {

private CheckboxMenuItem greenTuttle;
private CheckboxMenuItem redTuttle;
private CheckboxMenuItem blueTuttle;
private CheckboxMenuItem yellowTuttle;
private CheckboxMenuItem whiteTuttle;
private CheckboxMenuItem blackTuttle;

private CheckboxMenuItem greenBack;
private CheckboxMenuItem redBack;
private CheckboxMenuItem blueBack;
private CheckboxMenuItem yellowBack;
private CheckboxMenuItem whiteBack;
private CheckboxMenuItem blackBack;

private CheckboxMenuItem penUpCheck;
private CheckboxMenuItem penDownCheck;

private MenuItem         undoButton;


   protected MenuBarTuttleInterface( Frame          itsFrame,
                                     ActionListener itsListener) { 
   

   MenuBar   mainMenuBar;

   Menu      fileMenu;   
   MenuItem  openButton;
   MenuItem  saveButton;
   MenuItem  saveAsButton;    

   MenuItem  exitButton;

   Menu      editMenu;

   Menu      moveMenu;
   
   Menu      moveForwardMenu;
   MenuItem  forward5;
   MenuItem  forward10;
   MenuItem  forward25;

   Menu      moveBackwardMenu;
   MenuItem  backward5;
   MenuItem  backward10;
   MenuItem  backward25;

   Menu      turnMenu;
   
   Menu      turnRightMenu;
   MenuItem  turnRight5;
   MenuItem  turnRight15;
   MenuItem  turnRight45;

   Menu      turnLeftMenu;
   MenuItem  turnLeft5;
   MenuItem  turnLeft15;
   MenuItem  turnLeft45;

   Menu      colorMenu;
   Menu      foregroundMenu;
   Menu      backgroundMenu;

   Menu      screenMenu;
   MenuItem  clearScreen;
   MenuItem  resetScreen;
   MenuItem  clsetScreen;

   Menu      helpMenu;
   MenuItem  versionItem;
   MenuItem  helpItem;
      
      mainMenuBar = new MenuBar(); 
              
         fileMenu    = new Menu( "File"); 
         
            openButton = new MenuItem( "Open ...");  
            openButton.setActionCommand( "open");
            openButton.addActionListener( itsListener);     
            fileMenu.add( openButton);
         
            fileMenu.addSeparator();
            
            saveButton = new MenuItem( "Save ...");  
            saveButton.setActionCommand( "save");
            saveButton.addActionListener( itsListener);     
            fileMenu.add( saveButton);
            
            
            saveAsButton= new MenuItem( "Save As ...");  
            saveAsButton.setActionCommand( "saveas");
            saveAsButton.addActionListener( itsListener);     
            fileMenu.add( saveAsButton);
            
            fileMenu.addSeparator(); 
                    
            exitButton  = new MenuItem( "Exit ...");  
            exitButton.setActionCommand( "exit show");
            exitButton.addActionListener( itsListener);  
            exitButton.setShortcut( new MenuShortcut( KeyEvent.VK_E, true));  
            fileMenu.add( exitButton);
            
      mainMenuBar.add( fileMenu);
      
      
         editMenu = new Menu( "Edit");
             undoButton = new MenuItem( "can't undo!");
             undoButton.setEnabled( false);
             undoButton.setActionCommand( "undo");            
             undoButton.addActionListener( itsListener); 
             editMenu.add( undoButton);
             
      mainMenuBar.add( editMenu);        
             
         moveMenu = new Menu( "Move");

            moveForwardMenu = new Menu( "Forward", true);
            
               forward5 = new MenuItem(  "5 Steps");
               forward5.setActionCommand( "fd 5");
               forward5.addActionListener( itsListener);
               moveForwardMenu.add( forward5); 
               
               forward10 = new MenuItem(  "10 Steps");
               forward10.setActionCommand( "fd 10");
               forward10.addActionListener( itsListener);
               moveForwardMenu.add( forward10);   
                    
               forward25 = new MenuItem(  "25 Steps");
               forward25.setActionCommand( "fd 25");
               forward25.addActionListener( itsListener);               
               moveForwardMenu.add( forward25);
                  
            moveBackwardMenu = new Menu( "Backward", true);
               backward5 = new MenuItem(  "5 Steps");
               backward5.setActionCommand( "bd 5");
               backward5.addActionListener( itsListener);
               moveBackwardMenu.add( backward5); 
               
               backward10 = new MenuItem(  "10 Steps");
               backward10.setActionCommand( "bd 10");
               backward10.addActionListener( itsListener);
               moveBackwardMenu.add( backward10); 
                      
               backward25 = new MenuItem(  "25 Steps");
               backward25.setActionCommand( "bd 25");
               backward25.addActionListener( itsListener);
               moveBackwardMenu.add( backward25);
           
            moveMenu.add( moveForwardMenu);
            moveMenu.add( moveBackwardMenu);
      mainMenuBar.add( moveMenu);
                 
         turnMenu = new Menu( "Turn");  
      
            turnRightMenu = new Menu( "Turn Right", true);
               turnRight5 = new MenuItem(  "5 Degrees");
               turnRight5.setActionCommand( "tr 5");
               turnRight5.addActionListener( itsListener);
               turnRightMenu.add( turnRight5); 
               
               turnRight15 = new MenuItem(  "15 Degrees");
               turnRight15.setActionCommand( "tr 15");
               turnRight15.addActionListener( itsListener);
               turnRightMenu.add( turnRight15);  
                     
               turnRight45 = new MenuItem(  "45 Degrees");
               turnRight45.setActionCommand( "tr 45");
               turnRight45.addActionListener( itsListener);
               turnRightMenu.add( turnRight45);            
       
            turnLeftMenu = new Menu( "Turn Left", true);
               turnLeft5 = new MenuItem(  "5 Degrees");
               turnLeft5.setActionCommand( "tl 5");
               turnLeft5.addActionListener( itsListener);               
               turnLeftMenu.add( turnLeft5); 
               
               turnLeft15 = new MenuItem(  "15 Degrees");
               turnLeft15.setActionCommand( "tl 15");
               turnLeft15.addActionListener( itsListener);               
               turnLeftMenu.add( turnLeft15);        
               
               turnLeft45 = new MenuItem(  "45 Degrees");
               turnLeft45.setActionCommand( "tl 45");
               turnLeft45.addActionListener( itsListener);
               turnLeftMenu.add( turnLeft45);
                              
            turnMenu.add( turnRightMenu);   
            turnMenu.add( turnLeftMenu);        
       mainMenuBar.add( turnMenu);
       
          colorMenu = new Menu( "Colors");
       
             foregroundMenu  = new Menu( "Foreground");
                greenTuttle  = new CheckboxMenuItem ( "Green");
                greenTuttle.setActionCommand( "fg green");
                greenTuttle.addActionListener( itsListener);
                foregroundMenu.add( greenTuttle);
                
                redTuttle    = new CheckboxMenuItem ( "Red");
                redTuttle.setActionCommand( "fg red");
                redTuttle.addActionListener( itsListener);
                foregroundMenu.add( redTuttle);

                blueTuttle   = new CheckboxMenuItem ( "Blue");
                blueTuttle.setActionCommand( "fg blue");
                blueTuttle.addActionListener( itsListener);
                blueTuttle.setState( true);
                foregroundMenu.add( blueTuttle);

                yellowTuttle = new CheckboxMenuItem ( "Yellow");
                yellowTuttle.setActionCommand( "fg yellow");
                yellowTuttle.addActionListener( itsListener);
                foregroundMenu.add( yellowTuttle);

                whiteTuttle  = new CheckboxMenuItem ( "White");
                whiteTuttle.setActionCommand( "fg white");
                whiteTuttle.addActionListener( itsListener);
                foregroundMenu.add( whiteTuttle);

                blackTuttle  = new CheckboxMenuItem ( "Black");
                blackTuttle.setActionCommand( "fg black");
                blackTuttle.addActionListener( itsListener);
                foregroundMenu.add( blackTuttle);
                
                

             backgroundMenu  = new Menu( "Background");
                greenBack  = new CheckboxMenuItem ( "Green");
                greenTuttle.setActionCommand( "bg green");
                greenTuttle.addActionListener( itsListener);                
                backgroundMenu.add( greenBack);
                
                redBack    = new CheckboxMenuItem ( "Red");
                redBack.setActionCommand( "bg red");
                redBack.addActionListener( itsListener);                
                backgroundMenu.add( redBack);

                blueBack   = new CheckboxMenuItem ( "Blue");
                blueBack.setActionCommand( "bg blue");
                blueBack.addActionListener( itsListener);                
                backgroundMenu.add( blueBack);

                yellowBack = new CheckboxMenuItem ( "Yellow");
                yellowBack.setActionCommand( "bg yellow");
                yellowBack.addActionListener( itsListener);                
                yellowBack.setState( true);
                backgroundMenu.add( yellowBack);

                whiteBack = new CheckboxMenuItem ( "White");
                whiteBack.setActionCommand( "bg white");
                whiteBack.addActionListener( itsListener);                                
                backgroundMenu.add( whiteBack);

                blackBack  = new CheckboxMenuItem ( "Black");
                blackBack.setActionCommand( "bg black");
                blackBack.addActionListener( itsListener);                
                backgroundMenu.add( blackBack);
          
          colorMenu.add( foregroundMenu);
          colorMenu.add( backgroundMenu);
       mainMenuBar.add( colorMenu);
       
       
          screenMenu  = new Menu( "Screen"); 
             clearScreen = new MenuItem( "Clear "); 
             clearScreen.setActionCommand( "cs");
             clearScreen.addActionListener( itsListener);
             screenMenu.add( clearScreen);          
             
             resetScreen = new MenuItem( "Reset Tuttle"); 
             resetScreen.setActionCommand( "rs");
             resetScreen.addActionListener( itsListener);
             screenMenu.add( resetScreen);
             
             screenMenu.addSeparator();
             
             clsetScreen = new MenuItem( "Clear and Reset"); 
             clsetScreen.setActionCommand( "cr");
             clsetScreen.addActionListener( itsListener);             
             screenMenu.add( clsetScreen); 
             
             screenMenu.addSeparator();
             
             penUpCheck = new CheckboxMenuItem( "Pen up"); 
             penUpCheck.setActionCommand( "pu");
             penUpCheck.addActionListener( itsListener);             
             screenMenu.add( penUpCheck); 
             
             penDownCheck = new CheckboxMenuItem( "Pen down"); 
             penDownCheck.setActionCommand( "pd");
             penDownCheck.addActionListener( itsListener);  
             penDownCheck.setState( true);           
             screenMenu.add( penDownCheck);              
                      
       mainMenuBar.add( screenMenu);

          helpMenu   = new Menu( "Help"); 
             versionItem = new MenuItem( "Version ..."); 
             versionItem.setActionCommand( "version");
             versionItem.addActionListener( itsListener);             
             helpMenu.add( versionItem); 
             
             helpItem = new MenuItem( "Help ..."); 
             helpItem.setActionCommand( "help");
             helpItem.addActionListener( itsListener);             
             helpMenu.add( helpItem);
             
       mainMenuBar.add( helpMenu);    
       mainMenuBar.setHelpMenu( helpMenu);                  

       itsFrame.setMenuBar( mainMenuBar);
   } // End MenuBarTuttleInterface constructor.   



   protected void setForegroundCheckmark( String toSet) { 

System.out.println( "setForegroundCheckmark " + toSet);
      greenTuttle.setState( false);
      redTuttle.setState( false);
      blueTuttle.setState( false);
      yellowTuttle.setState( false);
      whiteTuttle.setState( false);
      blackTuttle.setState( false);
      
      if ( toSet.equals( "green")) { 
         greenTuttle.setState( true);
      } else if ( toSet.equals( "red")) {
         redTuttle.setState( true);
      } else if ( toSet.equals( "blue")) {
         blueTuttle.setState( true);
      } else if ( toSet.equals( "yellow")) {
         yellowTuttle.setState( true);
      } else if ( toSet.equals( "white")) {
         whiteTuttle.setState( true);
      } else if ( toSet.equals( "black")) {
         blackTuttle.setState( true);
      } // End if.   
   } // End setForegroundCheckmark.

   protected void setBackgroundCheckmark( String toSet) { 
      greenBack.setState( false);
      redBack.setState( false);
      blueBack.setState( false);
      yellowBack.setState( false);
      whiteBack.setState( false);
      blackBack.setState( false);
      
      if ( toSet.equals( "green")) { 
         greenBack.setState( true);
      } else if ( toSet.equals( "red")) {
         redBack.setState( true);
      } else if ( toSet.equals( "blue")) {
         blueBack.setState( true);
      } else if ( toSet.equals( "yellow")) {
         yellowBack.setState( true);
      } else if ( toSet.equals( "white")) {
         whiteBack.setState( true);
      } else if ( toSet.equals( "black")) {
         blackBack.setState( true);
      } // End if.   
   } // End setbackgroundCheckmark.

   protected void setPenUpCheckmark( boolean upOrDown) { 
      if ( upOrDown) { 
         penUpCheck.setState( true);
         penDownCheck.setState( false);
      } else { 
         penUpCheck.setState( false);
         penDownCheck.setState( true);
      } // End if.      
   } // End if.
   
   
   protected void setUndoCommand( String theCommand) { 
      if ( theCommand.length() == 0) { 
         undoButton.setLabel( "can't undo!");
         undoButton.setEnabled( false);
      } else {          
         undoButton.setLabel( "Undo " + theCommand);
         undoButton.setEnabled( true);   
      } // End if.
   } // End setUndoCommand.
   
} // End MenuBarTuttleInterface.







// Filename TextMenuTuttle.java.
// Supplies a main application text menu  
// interface the TextTuttle class.
//
// Written for Java Interface book chapter 7.
// This version for C8 incorporating undo.
// Fintan Culwin, v 0.2, August 1997.

package TextMenuTuttle;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

import Tuttles.BufferedTuttle;
import TextMenuTuttle.TextMenuTuttleInterface;


public class TextMenuTuttle extends    Applet
                            implements KeyListener {   

private BufferedTuttle               theTuttle; 
private TextMenuTuttleInterface  theInterface;
private Panel                    feedbackPanel;
private Label                    feedbackLabel;



   public void init() { 
      
   Panel tuttlePanel;   
   Font  tuttleFont;
         
      this.setLayout( new BorderLayout());
      this.setFont( new Font( "TimesRoman", Font.BOLD, 14));
      this.setBackground( Color.white);

      tuttlePanel = new Panel();   
      theTuttle   = new BufferedTuttle( this, 400, 400);
      tuttlePanel.add( theTuttle);
              
      theInterface  = new TextMenuTuttleInterface( this);
       
      feedbackPanel = new Panel();     
      feedbackLabel = new Label();
      feedbackPanel.add( feedbackLabel);
      
      this.add( feedbackPanel, "North");
      this.add( tuttlePanel,   "Center");
      this.add( theInterface,   "South");
      
      theInterface.setMenuState( TextMenuTuttleInterface.TOP_LEVEL_MENU);
      this.feedback();
   } // end init.   



   public  void keyTyped( KeyEvent event) {   

   char pressed  = event.getKeyChar();
   int  newState = TextMenuTuttleInterface.TOP_LEVEL_MENU;
   
      switch ( theInterface.menuStateIs() ) {
      
      case TextMenuTuttleInterface.TOP_LEVEL_MENU:  
         newState = topLevelMenu( pressed);
         break;
            
      case TextMenuTuttleInterface.MOVE_MENU:     
         newState = moveMenu( pressed);
         break;
     
      case TextMenuTuttleInterface.MOVE_FORWARD_MENU: 
         newState = moveForwardMenu( pressed);
         break;  
            
      case TextMenuTuttleInterface.MOVE_BACKWARD_MENU:
         newState = moveBackwardMenu( pressed);
         break; 
            
      case TextMenuTuttleInterface.TURN_MENU:
         newState = turnMenu( pressed);
         break;  
            
      case TextMenuTuttleInterface.TURN_LEFT_MENU:
         newState = turnLeftMenu( pressed);
         break;
                          
      case TextMenuTuttleInterface.TURN_RIGHT_MENU:
         newState = turnRightMenu( pressed);
         break;  
            
      case TextMenuTuttleInterface.COLOR_MENU:
         newState = colorMenu( pressed);                     
         break;                              
                        
      case TextMenuTuttleInterface.FOREGROUND_COLOR_MENU:
         newState = foregroundMenu( pressed);                     
         break;                           

      case TextMenuTuttleInterface.BACKGROUND_COLOR_MENU:
         newState = backgroundMenu( pressed);                     
         break; 

      case TextMenuTuttleInterface.PEN_MENU:
         newState = penMenu( pressed);                     
         break; 
         
      case TextMenuTuttleInterface.UNDO_MENU:
         newState = undoMenu( pressed);                     
         break; 
                     
      case TextMenuTuttleInterface.SCREEN_MENU:
         newState = screenMenu( pressed);                     
         break;

      case TextMenuTuttleInterface.HELP_MENU:
         newState = helpMenu( pressed);                     
         break;   
                                                   
      case TextMenuTuttleInterface.EXIT_MENU:
         newState = exitMenu( pressed);                     
         break; 
                                                                      
      } // End switch menuState.
         
     this.feedback();
     theInterface.setMenuState( newState); 

     if ( theTuttle.isUndoAvailable()) { 
         theInterface.setUndoCommand( theTuttle.whatUndoIsAvailable());
     } else { 
         theInterface.setUndoCommand( "");
     } // End if.
     
   } // End keyTyped.

   public  void keyPressed(  KeyEvent event ) {}  // End keyPressed.
   public  void keyReleased( KeyEvent event ) {}  // End keyReleased.



   private int topLevelMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU; 
   
      switch( pressed) { 
      case 'M':
      case 'm':         
         newMenuState = TextMenuTuttleInterface.MOVE_MENU; 
         break;

      case 'T':
      case 't':
         newMenuState = TextMenuTuttleInterface.TURN_MENU;
         break;           

      case 'C':
      case 'c':
         newMenuState = TextMenuTuttleInterface.COLOR_MENU;
         break;
            
      case 'P':
      case 'p':
         newMenuState = TextMenuTuttleInterface.PEN_MENU;
         break;
                    
      case 'S':
      case 's':
         newMenuState = TextMenuTuttleInterface.SCREEN_MENU;
         break;           

      case 'U':
      case 'u':
         newMenuState = TextMenuTuttleInterface.UNDO_MENU;
         break; 

      case 'H':
      case 'h':
         newMenuState = TextMenuTuttleInterface.HELP_MENU;
         break;      

      case 'E':
      case 'e':
         newMenuState = TextMenuTuttleInterface.EXIT_MENU;
         break;           
      } // End switch.
      return newMenuState;
   } // end topLevelMenu
   

   private int moveMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU; 
         break;

      case 'F':
      case 'f': 
         newMenuState = TextMenuTuttleInterface.MOVE_FORWARD_MENU; 
         break;      
      
      case 'B':
      case 'b': 
         newMenuState = TextMenuTuttleInterface.MOVE_BACKWARD_MENU; 
         break;
      } // End switch.   
      return newMenuState;
   } // end moveMenu.
   
      
   private int moveForwardMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.MOVE_FORWARD_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.MOVE_MENU; 
         break;

      case '5':               
         theTuttle.doCommand("fd 5");
         break;
            
      case '1':
         theTuttle.doCommand("fd 10"); 
         break;
            
      case '2':
         theTuttle.doCommand("fd 25");  
         break;            
      } // End switch.
      return newMenuState;
   } // end moveMenu.   
   

   private int moveBackwardMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.MOVE_BACKWARD_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.MOVE_MENU; 
         break;

      case '5':               
         theTuttle.doCommand("bd 5");
         break;
            
      case '1':
         theTuttle.doCommand("bd 10"); 
         break;
            
      case '2':
         theTuttle.doCommand("bd 25");  
         break;            
      } // End switch.            
      return newMenuState;
   } // end moveMenu.   



   private int turnMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU; 
         break;

      case 'L':
      case 'l': 
         newMenuState = TextMenuTuttleInterface.TURN_LEFT_MENU; 
         break;      
      
      case 'R':
      case 'r': 
         newMenuState = TextMenuTuttleInterface.TURN_RIGHT_MENU; 
         break;
      } // End switch.   
      return newMenuState;
   } // end turnMenu.


   private int turnLeftMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.TURN_LEFT_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.TURN_MENU; 
         break;

      case '5':               
         theTuttle.doCommand("tl 5");
         break;
            
      case '1':
         theTuttle.doCommand("tl 10"); 
         break;
            
      case '3':
         theTuttle.doCommand("tl 30");  
         break;   
         
      case '4':
         theTuttle.doCommand("tl 45");  
         break;                       
      } // End switch.            
      return newMenuState;
   } // end turnLeftMenu.   


   private int turnRightMenu( char pressed) { 
   
   int newMenuState = TextMenuTuttleInterface.TURN_RIGHT_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = TextMenuTuttleInterface.TURN_MENU; 
         break;

      case '5':               
         theTuttle.doCommand("tr 5");
         break;
            
      case '1':
         theTuttle.doCommand("tr 10"); 
         break;
            
      case '3':
         theTuttle.doCommand("tr 30");  
         break;   
         
      case '4':
         theTuttle.doCommand("tr 45");  
         break;                       
      } // End switch.            
      return newMenuState;
   } // end turnRightMenu.   


   private int colorMenu( char pressed) { 
   
   int newMenuState = theInterface.COLOR_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.TOP_LEVEL_MENU; 
         break;

      case 'F':
      case 'f': 
         newMenuState = theInterface.FOREGROUND_COLOR_MENU; 
         break;      
      
      case 'B':
      case 'b': 
         newMenuState = theInterface.BACKGROUND_COLOR_MENU; 
         break;
      } // End switch.   
      return newMenuState;
   } // end colorMenu.


   private int foregroundMenu( char pressed) { 
   
   int newMenuState = theInterface.FOREGROUND_COLOR_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.COLOR_MENU;
         break;

      case 'B':
      case 'b': 
         theTuttle.doCommand( "fg black"); 
         break;      
      
      case 'W':
      case 'w': 
         theTuttle.doCommand( "fg white"); 
         break;
         
      case 'L':
      case 'l': 
         theTuttle.doCommand( "fg blue");  
         break; 
                 
      case 'R':
      case 'r': 
         theTuttle.doCommand( "fg red");  
         break;
                  
      case 'G':
      case 'g': 
         theTuttle.doCommand( "fg green");  
         break;                
         
      case 'Y':
      case 'y': 
         theTuttle.doCommand( "fg yellow");  
         break;                
      } // End switch.
                
      return newMenuState;
   } // end foregroundMenu.


   private int backgroundMenu( char pressed) { 
   
   int newMenuState = theInterface.BACKGROUND_COLOR_MENU;

      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.COLOR_MENU;
         break;

      case 'B':
      case 'b': 
         theTuttle.doCommand( "bg black"); 
         break;      
      
      case 'W':
      case 'w': 
         theTuttle.doCommand( "bg white"); 
         break;
         
      case 'L':
      case 'l': 
         theTuttle.doCommand( "bg blue");  
         break; 
                 
      case 'R':
      case 'r': 
         theTuttle.doCommand( "bg red");  
         break;
                  
      case 'G':
      case 'g': 
         theTuttle.doCommand( "bg green");  
         break;                
         
      case 'Y':
      case 'y': 
         theTuttle.doCommand( "bg yellow");  
         break;                
      } // End switch.
      
      return newMenuState;
   } // end foregroundMenu.


   private int penMenu( char pressed) { 
   
   int newMenuState = theInterface.PEN_MENU;
   
      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;

      case 'U':
      case 'u':
         theTuttle.doCommand( "pu");  
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;                 
      
      case 'D':
      case 'd':
         theTuttle.doCommand( "pd");  
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;                 
      } // End switch.  
       
      return newMenuState;
   } // End penMenu.


   private int screenMenu( char pressed) { 
   
   int newMenuState = theInterface.SCREEN_MENU;
   
      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;

      case 'C':
      case 'c':
         theTuttle.doCommand( "cl");  
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;                 
      
      case 'R':
      case 'r':
         theTuttle.doCommand( "rs");  
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break; 
         
      case 'B':
      case 'b':
         theTuttle.doCommand( "cr");  
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;          
      } // End switch. 
      
      return newMenuState;
   } // End screenMenu.


           private int undoMenu( char pressed) { 
           
           int newMenuState = TextMenuTuttleInterface.UNDO_MENU;

              switch( pressed) { 
              case KeyEvent.VK_ESCAPE:
              case 'N':
              case 'n':
                 newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU; 
                 break;

              case 'Y':
              case 'y': 
                 if ( theTuttle.isUndoAvailable()) { 
                    theTuttle.doCommand( "undo");
                 } 
                 newMenuState = TextMenuTuttleInterface.TOP_LEVEL_MENU; 
                 break;      
      
              } // End switch.   
              return newMenuState;
           } // End undoMenu.



   private int helpMenu( char pressed) { 
   
   int newMenuState = theInterface.HELP_MENU;
   
      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.TOP_LEVEL_MENU; 
         break;
      } // End switch.
      return newMenuState;
   } // End helpMenu.


   private int exitMenu( char pressed) { 
   
   int newMenuState = theInterface.HELP_MENU;
   
      switch( pressed) { 
      case KeyEvent.VK_ESCAPE:
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;

      case 'Y':
      case 'y':         
         System.exit( 0);
         break;                 
      
      case 'N':
      case 'n':
         newMenuState = theInterface.TOP_LEVEL_MENU;
         break;          
      } // End switch. 
          
      return newMenuState;
   } // End exitMenu.


   private void feedback(){    
      feedbackLabel.setText(  theTuttle.getDetails());
      feedbackPanel.doLayout();
   } // End feedback.
                                                          
} // End class TextMenuTuttle.




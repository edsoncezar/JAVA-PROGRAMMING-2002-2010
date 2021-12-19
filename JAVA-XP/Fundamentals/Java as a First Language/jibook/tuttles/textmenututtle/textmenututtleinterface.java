// Filename TextMenuTuttleInterface.java.
// Supplies text menu interface for a Tuttle.
//
// Written for Java Interface book chapter 7.
// This version for C8 incorporating undo.
// Fintan Culwin, v 0.2, August 1997.

package TextMenuTuttle;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;


public class TextMenuTuttleInterface extends    Panel {   

protected static final int TOP_LEVEL_MENU        = 0;
protected static final int MOVE_MENU             = 1; 
protected static final int MOVE_FORWARD_MENU     = 2; 
protected static final int MOVE_BACKWARD_MENU    = 3; 
protected static final int TURN_MENU             = 4; 
protected static final int TURN_LEFT_MENU        = 5;
protected static final int TURN_RIGHT_MENU       = 6;  
protected static final int COLOR_MENU            = 7;
protected static final int FOREGROUND_COLOR_MENU = 8;
protected static final int BACKGROUND_COLOR_MENU = 9;
protected static final int PEN_MENU              = 10;
protected static final int SCREEN_MENU           = 11;
protected static final int UNDO_MENU             = 12;
protected static final int HELP_MENU             = 13;
protected static final int EXIT_MENU             = 14;

private int              menuState               = TOP_LEVEL_MENU;

private TextArea     menuArea;
private KeyListener  itsListener;

private String       undoCommand = ""; 

   public TextMenuTuttleInterface( KeyListener listener) { 
                             
      menuArea  = new TextArea( 5, 60);
      menuArea.setEditable( false);
      menuArea.addKeyListener( listener);
       
      this.add( menuArea);      
   } // end init.   


   protected void setMenuState( int newState) { 

      menuState = newState;
      
      switch( menuState) { 
         case TOP_LEVEL_MENU:
            menuArea.setText( topLevelMenu);
            break;
            
         case MOVE_MENU:             
            menuArea.setText( topLevelMenu + moveMenu);                    
            break;
            
         case MOVE_FORWARD_MENU:             
            menuArea.setText( topLevelMenu + moveMenu + movefMenu);                               
            break;
                        
         case MOVE_BACKWARD_MENU:             
            menuArea.setText( topLevelMenu + moveMenu + movebMenu);                               
            break; 
                         
         case TURN_MENU:      
            menuArea.setText( topLevelMenu + turnMenu);                                           
            break;            
            
         case TURN_LEFT_MENU:      
            menuArea.setText( topLevelMenu + turnMenu + turnlMenu);                                           
            break;
                     
         case TURN_RIGHT_MENU:      
            menuArea.setText( topLevelMenu + turnMenu + turnrMenu);                                           
            break;  
            
         case COLOR_MENU:      
            menuArea.setText( topLevelMenu + colorMenu);                                           
            break;  
            
         case FOREGROUND_COLOR_MENU:      
            menuArea.setText( topLevelMenu + colorMenu + foregroundMenu);                                           
            break;            
                                       
         case BACKGROUND_COLOR_MENU:      
            menuArea.setText( topLevelMenu + colorMenu + backgroundMenu);                                           
            break;  
            
         case PEN_MENU:      
            menuArea.setText( topLevelMenu + penMenu);                                           
            break;
                          
         case SCREEN_MENU:      
            menuArea.setText( topLevelMenu + screenMenu);                                           
            break;

         case UNDO_MENU:
            if ( undoCommand.length() == 0) { 
               menuArea.setText( topLevelMenu + 
                                "\n    Undo is not available.");
            } else { 
               menuArea.setText( topLevelMenu + 
                                 "\n    Undo " + undoCommand + 
                                 " Yes No" );
            } // End if.
            break;
                        
         case HELP_MENU:      
            menuArea.setText( topLevelMenu + helpMenu);                                           
            break;   

         case EXIT_MENU:      
            menuArea.setText( topLevelMenu + exitMenu);                                           
            break;                                   
         } // End switch.            
   } // End setMenuState.


   protected int menuStateIs() {    
      return menuState;   
   } // End menuStateIs.


   protected void setUndoCommand( String theCommand) { 
      undoCommand = theCommand;   
   } // End setUndoCommand.


private static final String topLevelMenu       =  
               "  Move Turn Colors Pen Screen Undo Help Exit";
private static final String moveMenu       =  
               "\n    Move: Forwards Backwards";
private static final String movefMenu      =  
               "\n        Move Forwards: 5 10 25 ";
private static final String movebMenu      =  
               "\n        Move Forwards: 5 10 25 "; 
private static final String turnMenu       =  
               "\n    Turn: Left Right";
private static final String turnlMenu      =  
               "\n        Turn Left: 5 10 30 45";
private static final String turnrMenu      =  
               "\n        Turn Left: 5 10 30 45";
private static final String colorMenu      =  
               "\n    Color: Foreground Background";
private static final String foregroundMenu =  
               "\n        Color Foreground: White Black Red bLue Green Yellow";
private static final String backgroundMenu =  
               "\n        Color Foreground: White Black Red bLue Green Yellow";  
private static final String penMenu        =  
               "\n    Pen: Up Down";
private static final String screenMenu     =  
               "\n    Screen: Clear Reset Both"; 
private static final String helpMenu       =  
               "\n    Help: Commands Move Turn Colors Pen Screen Help Exit";
private static final String exitMenu       =  
               "\n    Exit: Yes No";                                                             

} // End class TextMenuTuttleInterface.




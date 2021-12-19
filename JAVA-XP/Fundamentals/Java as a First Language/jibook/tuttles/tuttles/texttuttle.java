// Filename TextTuttle.java.
// Extends the Tuttle class by providing a text 
// interface to supply commands.
//
// Written for the Java Interface Book Chapter 6.
// Fintan Culwin, v 0.2, August 1997.

package Tuttles;

import java.awt.*;
import java.applet.*;
import java.util.StringTokenizer;

import Tuttles.Tuttle;

public class TextTuttle extends Tuttle {

public static final int UNKNOWN         = -1;
public static final int FORWARD         = 0;
public static final int BACKWARD        = 1;
public static final int TURN_RIGHT      = 2;
public static final int TURN_LEFT       = 3;
public static final int FOREGROUND      = 4;
public static final int BACKGROUND      = 5;
public static final int PEN_UP          = 6;
public static final int PEN_DOWN        = 7;
public static final int CLEAR           = 8;
public static final int RESET           = 9;
public static final int CLEAR_AND_RESET = 10;
public static final int EXIT            = 11;
public static int       MAX_COMMANDS    = 11;

private static final String[] commands = 
                       { "fd", "bd", "tr", "tl",
                         "fg", "bg", "pu", "pd", 
                         "cl", "rs", "cr", 
                         "exit" };


   public TextTuttle(  Applet applet, int width, int height) { 
      super( applet, width, height);      
   } // End Tuttle constructor.


   public String doCommand( String theCommand) { 
   
   StringTokenizer tokenizer = new StringTokenizer( theCommand);
   String          firstTerm = null;
   String          theReply;   

   int     thisCommand = UNKNOWN;
   
      if ( tokenizer.hasMoreTokens()) { 
         firstTerm   = tokenizer.nextToken().toLowerCase();
         thisCommand = identifyCommand( firstTerm);
         
         if ( thisCommand == UNKNOWN ) { 
            theReply = new String( "The command " + firstTerm + 
                                   " is not known!");
         } else { 
            theReply = dispatchCommand( thisCommand, tokenizer);       
         } // End if.
      } else { 
         theReply = new String( "There does not seem to be a command given!");
      } // End if.
      return theReply;
   } // End doCommand.


   public int identifyCommand( String toIdentify) { 
   
   int thisCommand = MAX_COMMANDS;
   int identified  = UNKNOWN;
   
     while ( (identified  == UNKNOWN) &&
             (thisCommand != UNKNOWN) ){
        if ( toIdentify.equals( commands[ thisCommand])) { 
           identified = thisCommand;
        } else { 
           thisCommand--;
        } // end if.            
     } // End while.
     return identified;
   } // End identifyCommand.


   private String dispatchCommand( int             theCommand, 
                                   StringTokenizer arguments){ 
                                     
   StringBuffer theResponse = new StringBuffer( "");
   boolean      processed  = false;
                                        
     switch( theCommand) { 
     
     case FORWARD:
     case BACKWARD:
     case TURN_RIGHT:
     case TURN_LEFT:
        if (arguments.countTokens() == 1) { 
        int toStepOrTurn;        
            try { 
               toStepOrTurn = Integer.parseInt( arguments.nextToken());
               switch ( theCommand) {
               case FORWARD: 
                  this.forward( toStepOrTurn);
                  break;
               case BACKWARD:
                  this.backward( toStepOrTurn);
                  break; 
               case TURN_RIGHT:
                  this.turnRight( toStepOrTurn);
                  break;                                      
               case TURN_LEFT:
                  this.turnLeft( toStepOrTurn);
                  break;                  
               } // End switch.
               processed = true;          
            } catch ( NumberFormatException exception) { 
               // Do nothing.            
            } // End try/ catch.
        } // End if.
        if ( !processed) { 
           theResponse.append( commands[ theCommand] +  
                        " should be followed by a single number.");
        } // End if. 
        break;
        
     case PEN_UP:
     case PEN_DOWN:
     case RESET:
     case CLEAR:
     case CLEAR_AND_RESET:
        if ( arguments.countTokens() == 0) { 
           switch ( theCommand) {
           case PEN_UP:
              this.setPenUp();
              break;
           case PEN_DOWN:
              this.setPenDown();
              break;
           case RESET:        
              this.resetTuttle();
              break; 
           case CLEAR:        
              this.clearTuttleArea();
              break;                   
           case CLEAR_AND_RESET:        
              this.clearAndReset();
              break; 
           } // End switch.
           processed = true;   
        } // End if.
        if ( !processed) { 
            theResponse.append( commands[ theCommand] + 
                         " should not  be followed by anything.");         
        } // End if.                                                         
        break;
     
     case FOREGROUND:
     case BACKGROUND:
        if (arguments.countTokens() == 1) { 
        
        Color theColor; 
           theColor = identifyColor( arguments.nextToken().toLowerCase());
           if ( theColor != null) { 
              if ( theCommand == FOREGROUND) { 
                 this.setForeground( theColor);
              } else { 
                 this.setBackground( theColor);
              } // End if.
              processed = true;
           } 
        } // End if.
        if ( !processed) { 
           theResponse.append( commands[ theCommand] + 
                        " should only be followed by white, black, red, " + 
                        "blue, green or yellow.");                        
        } // End if.     
        break;
     } // End switch.  
     return theResponse.toString();                                
   } // End dispatchCommand                                     


   protected Color identifyColor( String possibleColor) { 

   Color theColor = null;
      if ( possibleColor.equals( "black")) { 
         theColor = Color.black;
      } else if ( possibleColor.equals( "white")) {       
         theColor = Color.white;
      } else if ( possibleColor.equals( "yellow")) {  
         theColor = Color.yellow;  
      } else if ( possibleColor.equals( "green")) {  
         theColor = Color.green;
      } else if ( possibleColor.equals( "red")) {  
         theColor = Color.red;         
      } else if ( possibleColor.equals( "blue")) {  
         theColor = Color.blue;         
      } // End if.        
      return theColor;
   } // end IdentifyColor.

} // End TextTuttle


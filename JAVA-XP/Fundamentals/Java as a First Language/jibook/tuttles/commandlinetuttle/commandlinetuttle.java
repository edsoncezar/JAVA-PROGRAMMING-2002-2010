// Filename CommandLineTuttle.java.
// Supplies a command line translation for  
// the Tuttle class.
//
// Written for Java Interface book chapter 6.
// Fintan Culwin, v0.1, August 1997.

package CommandLineTuttle;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import java.util.StringTokenizer;

import Tuttles.BufferedTuttle;
import CommandLineTuttle.CommandLineTuttleInterface;


public class CommandLineTuttle extends    Applet 
                               implements ActionListener {   

private BufferedTuttle                 theTuttle; 
private CommandLineTuttleInterface theInterface;

private Panel                      feedbackPanel;
private Label                      feedbackLabel;

   public void init() { 
   
   Panel tuttlePanel   = new Panel();
              
      this.setLayout( new BorderLayout());
      this.setFont( new Font( "TimesRoman", Font.BOLD, 14));
      this.setBackground( Color.white);

      theTuttle = new BufferedTuttle( this, 400, 400);
      tuttlePanel.add( theTuttle);
      
      theInterface = new CommandLineTuttleInterface( this);        
                 
      feedbackPanel = new Panel();     
      feedbackPanel.setBackground( Color.white);
      feedbackLabel = new Label();
      feedbackPanel.add( feedbackLabel);
      
      this.add( feedbackPanel, "North");
      this.add( tuttlePanel,   "Center");
      this.add( theInterface,  "South");
            
      this.feedback();
   } // end init.   


   public void actionPerformed( ActionEvent event) { 

   String          theCommand = event.getActionCommand();
   StringTokenizer tokenizer  = new StringTokenizer( theCommand);
   String          firstTerm  = tokenizer.nextToken().toLowerCase();
   String          theResponse;

       if ( firstTerm.equals( "help")) { 
         theResponse = obtainHelp( tokenizer);
       } else if ( firstTerm.equals( "exit")) {   
         theResponse = checkExit( tokenizer);
       } else {        
         theResponse = theTuttle.doCommand( theCommand); 
       } // End if           
       theInterface.appendFeedback( "\n> " + theCommand);
       if ( theResponse.length() > 0 ) { 
          theInterface.appendFeedback("\n" + theResponse);    
       } // End if.   
       theInterface.clearCommandArea();
       this.feedback();
   } // end processCommand.


   private String checkExit( StringTokenizer tokenizer) {    
      if ( (tokenizer.countTokens() == 1)  &&
           (tokenizer.nextToken().toLowerCase().equals( "please")) ){ 
         System.exit( 0);
         return "";  
      } else {               
         return new String( "To exit from this application you have to " +
                            "type 'exit', followed by 'please'!");         
      } // End if.  
   } // End checkExit.
   

   protected String obtainHelp( StringTokenizer tokenizer) { 
   
   StringBuffer theHelp = new StringBuffer( "");
   String       secondTerm;
   int          helpFor;   

      if ( !tokenizer.hasMoreTokens()) { 
         theHelp.append( "help is available for fd, bd, tr, tl " + 
                         "fg bg pu pd cl rs cr and exit");
      } else {    
          secondTerm = tokenizer.nextToken().toLowerCase();
          helpFor = theTuttle.identifyCommand( secondTerm);
          
          switch ( helpFor) { 
          case theTuttle.FORWARD:
             theHelp.append("fd is ForwarD, it must be followed by a number, " + 
                            "\nthe tuttle will move that many steps in its " + 
                            "current direction.");
             break;
          case theTuttle.UNKNOWN:
             theHelp.append("Sorry! The command '" + secondTerm + 
                            "' is not known \n Try 'help' by itself for " +
                            "a list of commands which are known.");
             break;                                 
          
          } // End switch.
      } // End if.    
      return theHelp.toString();
   } // End obtainHelp.

   
   private void feedback(){    
      feedbackLabel.setText(  theTuttle.getDetails());
      feedbackPanel.doLayout();
   } // End feedback.
} // End class CommandLineTuttle.




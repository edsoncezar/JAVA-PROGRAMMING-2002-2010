// Filename MenuBarTuttle.java.
// Supplied a MainMenu interface for the Tuttle class.
//
// Written for Java Interface book chapter 8.
// This version for Ch8, including undo, load and save.
// Fintan Culwin, v 0.2, August 1997.

package MenuBarTuttle;


import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.StringTokenizer;

import Tuttles.BufferedTuttle;
import MenuBarTuttle.MenuBarTuttleInterface;

import MenuBarTuttle.TuttleOpenDialog;
import MenuBarTuttle.TuttleOpenErrorDialog;
import MenuBarTuttle.TuttleSaveDialog;
import MenuBarTuttle.TuttleSaveErrorDialog;
import MenuBarTuttle.ExitDialog;
import MenuBarTuttle.VersionDialog;
import MenuBarTuttle.MenuBarTuttleHelpDialog;


public class MenuBarTuttle extends    Applet 
                           implements ActionListener, 
                                      WindowListener {
                                      
private Label  feedbackLabel;
private Panel  feedbackPanel;

private BufferedTuttle          theTuttle;
private MenuBarTuttleInterface  theInterface;

private TuttleOpenDialog        openDialog;
private TuttleOpenErrorDialog   openErrorDialog;
private TuttleSaveDialog        saveDialog;
private TuttleSaveErrorDialog   saveErrorDialog;
private ExitDialog              exitDialog;
private VersionDialog           versionDialog;
private MenuBarTuttleHelpDialog helpDialog;

private Frame                   tuttleFrame;
 
   public void init() { 
   
   Panel tuttlePanel = new Panel(); 
   
      tuttleFrame = new Frame();
      tuttleFrame.addWindowListener( this);
      tuttleFrame.setTitle( "Menu Bar Tuttle Interface");
      tuttleFrame.setBackground( Color.white);
      tuttleFrame.setFont( new Font( "TimesRoman", Font.PLAIN, 20));
      this.setBackground( Color.white);
                       
      feedbackPanel = new Panel();
      feedbackLabel = new Label();
      feedbackPanel.add( feedbackLabel);
      
      theTuttle     = new BufferedTuttle( this, 500, 500);            
      tuttlePanel.add( theTuttle); 
      
      theInterface  = new MenuBarTuttleInterface( tuttleFrame, 
                                                  (ActionListener) this);

      tuttleFrame.add( tuttlePanel,   "Center");
      tuttleFrame.add( feedbackPanel, "South");
      
      tuttleFrame.setSize( tuttleFrame.getPreferredSize());      
      tuttleFrame.setVisible( true);    
      this.feedback();

      openDialog      = new TuttleOpenDialog(      tuttleFrame, this);   
      openErrorDialog = new TuttleOpenErrorDialog( tuttleFrame, this);  
      saveDialog      = new TuttleSaveDialog(      tuttleFrame, this);   
      saveErrorDialog = new TuttleSaveErrorDialog( tuttleFrame, this);                  
      helpDialog      = new MenuBarTuttleHelpDialog( tuttleFrame);
      exitDialog      = new ExitDialog(    tuttleFrame, this); 
      versionDialog   = new VersionDialog( tuttleFrame, this.getAppletInfo());                
   } // End init.


   public  void actionPerformed( ActionEvent event) { 
   
   StringTokenizer tokenizer = new StringTokenizer( 
                                         event.getActionCommand());
   String theCommand  = tokenizer.nextToken();
   String theArgument = "";
   String tuttlesReply;
   int    executed;

      if ( tokenizer.hasMoreTokens()) { 
         theArgument = tokenizer.nextToken();      
      } // End if.

      if ( theCommand.equals( "open")) {  
         openDialog.setVisible( true);
      } else if ( theCommand.equals( "loadit")) {         
         if ( openDialog.isFilenameAvailable()) { 
            tuttlesReply = theTuttle.doCommand( "load " +  
                                     openDialog.fullFilenameIs());                                   
            if ( tuttlesReply.length() > 0 ) { 
               openErrorDialog.setReason( "The file " + 
                                     openDialog.filenameIs() + 
                                     "\nin the directory\n"  + 
                                     openDialog.dirnameIs()  +
                                     "\ncould not be opened");  
               openErrorDialog.setVisible( true);
               saveDialog.clearFullFilename();                                     
            } else { 
               saveDialog.setFullFilename( openDialog.filenameIs(),
                                           openDialog.dirnameIs());                                                                    
            } // End if.                                     
         } // End if.   

      } else if ( theCommand.equals( "saveas")) {  
         saveDialog.setVisible( true);
      } else if ( theCommand.equals( "save")) {
         if ( saveDialog.isFilenameAvailable()) { 
            this.actionPerformed( new ActionEvent( this, 
                                  ActionEvent.ACTION_PERFORMED,
                                  "saveit"));  
         } else { 
            saveDialog.setVisible( true);
         } // End if.
      } else if ( theCommand.equals( "saveit")) {   
         tuttlesReply = theTuttle.doCommand( "save " +  
                                     saveDialog.fullFilenameIs());   
            if ( tuttlesReply.length() > 0 ) { 
               saveErrorDialog.setReason( 
                                  "The drawing could not be saved to " + 
                                  "\nthe file " + saveDialog.filenameIs() + 
                                  "\nin the directory\n"   + 
                                  saveDialog.dirnameIs()  + ".");
               saveDialog.setVisible( true);
               saveDialog.clearFullFilename();
            } else { 
               saveDialog.setFullFilename( saveDialog.filenameIs(),
                                           saveDialog.dirnameIs());                                                                    
            } // End if.              
        
      } else if ( theCommand.equals( "exit")) { 
         if ( theArgument.equals( "show")) {    
            exitDialog.setVisible( true); 
         } else if ( theArgument.equals( "please")) { 
            System.exit( 0);
         } // End if.      
      } else if ( theCommand.equals( "version")) {   
         versionDialog.setVisible( true);        
      } else if ( theCommand.equals( "help")) {   
         helpDialog.setVisible( true);                 
      } else { 
         theTuttle.doCommand( event.getActionCommand());
      } // End if.   
            
      this.feedback(); 


      executed = theTuttle.identifyCommand( theCommand);
      switch ( executed) { 
      case BufferedTuttle.FOREGROUND:
         theInterface.setForegroundCheckmark( theArgument);  
         
      case BufferedTuttle.BACKGROUND:
         theInterface.setBackgroundCheckmark( theArgument);
         
      case BufferedTuttle.PEN_UP:
         theInterface.setPenUpCheckmark( true);
         
      case BufferedTuttle.PEN_DOWN:
         theInterface.setPenUpCheckmark( false);         
         
      } // End switch.
      
      if ( theTuttle.isUndoAvailable()) { 
         theInterface.setUndoCommand( theTuttle.whatUndoIsAvailable());       
      } else { 
         theInterface.setUndoCommand( ""); 
      } // End if.            
   } // End actionPerformed.


   private void feedback() {    
      feedbackLabel.setText(  theTuttle.getDetails());
      feedbackPanel.doLayout();
   } // End feedback.
   
   
   public String getAppletInfo() { 
   
     return "Menu Bar Tuttle\nVersion 2.0\n " + 
            "Fintan Culwin, August 1997\n"    + 
            "fintan@sbu.ac.uk";   
   } // End getAppletInfo.
   

   public void windowClosing( WindowEvent event) {  
      exitDialog.setVisible( true);     
   } // End windowClosing .
   
   public void windowOpened( WindowEvent event)      {} // End windowOpened.    
   public void windowClosed( WindowEvent event)      {} // End windowClosed
   public void windowIconified( WindowEvent event)   {} // End windowIconified
   public void windowDeiconified(WindowEvent event)  {} // End windowDeiconified   
   public void windowActivated( WindowEvent event)   {} // End windowActivated
   public void windowDeactivated( WindowEvent event) {} // End windowDeactivated   
   
   
   
} // End SemiDirectTuttle.




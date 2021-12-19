// Filename MenuBarTuttleHelpDialog.java.
// Supplies Help dialog for the MenuBarTuttle application,
// illustrating the use of the CardLayout management policy.
//
// Written for Java Interface book chapter 5.
// Fintan Culwin, v0.1, August 1997.

package MenuBarTuttle;


import java.awt.*;
import java.awt.event.*;

import MessageCanvas;



class MenuBarTuttleHelpDialog extends    Dialog 
                              implements ActionListener, 
                                         ItemListener    { 

private static final String FILE   = "File";
private static final String MOVE   = "Move";
private static final String TURN   = "Turn";
private static final String COLOR  = "Color";
private static final String SCREEN = "Screen";

private Panel         helpControl;
private CheckboxGroup theGroup;
private Checkbox      fileHelp;
private Checkbox      moveHelp;
private Checkbox      turnHelp;
private Checkbox      colorHelp;
private Checkbox      screenHelp;

private Panel         helpPanel;
private CardLayout    manager;
private MessageCanvas fileHelpMessage;
private MessageCanvas moveHelpMessage;
private MessageCanvas turnHelpMessage;
private MessageCanvas colorHelpMessage;
private MessageCanvas screenHelpMessage;

private Panel         buttonPanel;
private Button        dismiss;

private Window        itsParentWindow;


   protected MenuBarTuttleHelpDialog( Frame  itsParentFrame) { 
   
     super( itsParentFrame, "Help", false);
     itsParentWindow = (Window) itsParentFrame;
     this.setFont( itsParentFrame.getFont());


     helpControl = new Panel();
     helpControl.setBackground( Color.white);

     theGroup = new CheckboxGroup();
     fileHelp = new Checkbox( FILE,   theGroup, true);
     fileHelp.addItemListener( this);
     helpControl.add( fileHelp);     
     
     moveHelp = new Checkbox( MOVE,   theGroup, false);
     moveHelp.addItemListener( this);
     helpControl.add( moveHelp);
     
     turnHelp = new Checkbox( TURN,   theGroup, false);
     turnHelp.addItemListener( this);
     helpControl.add( turnHelp); 
     
     colorHelp = new Checkbox( COLOR,  theGroup, false);
     colorHelp.addItemListener( this);     
     helpControl.add( colorHelp); 
     
     screenHelp = new Checkbox( SCREEN, theGroup, false); 
     screenHelp.addItemListener( this);      
     helpControl.add( screenHelp);           
 
     manager = new CardLayout();
     helpPanel = new Panel();
     helpPanel.setBackground( Color.white);
     helpPanel.setLayout( manager);
     
     fileHelpMessage =   new MessageCanvas( FILE_MESSAGE);
     helpPanel.add( fileHelpMessage, FILE);
     
     moveHelpMessage =   new MessageCanvas( MOVE_MESSAGE);
     helpPanel.add( moveHelpMessage, MOVE);
     
     turnHelpMessage =   new MessageCanvas( TURN_MESSAGE);
     helpPanel.add( turnHelpMessage, TURN);
     
     colorHelpMessage =  new MessageCanvas( COLOR_MESSAGE);
     helpPanel.add( colorHelpMessage, COLOR);
     
     screenHelpMessage = new MessageCanvas( SCREEN_MESSAGE);
     helpPanel.add( screenHelpMessage, SCREEN);
                    
     
     dismiss     = new Button( "OK");
     dismiss.setActionCommand( "OK");
     dismiss.addActionListener( this);
     
     buttonPanel = new Panel();
     buttonPanel.setBackground( Color.white);
     buttonPanel.add( dismiss);      

     this.add( helpControl, "North");
     this.add( helpPanel,   "Center");
     this.add( buttonPanel, "South");
     this.pack();     
   } // End HelpDialog.
                               

   public void setVisible( boolean showIt) { 
   
   Point         itsParentsLocation;
   Dimension     itsParentsSize;
   Point         itsLocation;
   Dimension     itsSize;

      if ( showIt) { 
         itsParentsLocation = itsParentWindow.getLocationOnScreen();
         itsParentsSize     = itsParentWindow.getSize();
         itsSize            = this.getSize();
         itsLocation        = new Point();
      
         itsLocation.x = itsParentsLocation.x + 
                         itsParentsSize.width/2 - 
                         itsSize.width/2;
         itsLocation.y = itsParentsLocation.y + 
                         itsParentsSize.height/2 - 
                         itsSize.height/2;                          
         this.setLocation( itsLocation);   
      } // End if.          
      super.setVisible( showIt);            
   } // End setVisible.

                               
   public  void actionPerformed( ActionEvent event) {
      this.setVisible( false);   
   } // End actionPerformed. 
      
      
   public  void itemStateChanged(ItemEvent event) {   


         manager.show( helpPanel, 
                       (String) event.getItem());

   } // End itemStateChanged.
   


private static final String FILE_MESSAGE = 
                 "The File menu contains a single option Exit ...\n" +
                 "Which will post a dialog to the screen when \n" +
                 "it is selected.\n  \n"                               +
                 "The dialog asks if you are sure? If you press no\n" +
                 "the dialog will disappear. If you press yes the \n" + 
                 "program will terminate.";
                 
private static final String MOVE_MESSAGE = 
                 "The Move menu contains two options Forward and\n" +
                 "Backward. Each of these has its own cascading \n" + 
                 "menu which allows you to move the tuttle 5, 10\n" +
                 "or 15 steps, using the direction in which the \n" + 
                 "tuttle is facing. \n  \n"                         + 
                 "If the movement would take the tuttle outside \n" + 
                 "the limits of its area it will not be moved."; 

private static final String TURN_MESSAGE = 
                 "The Turn menu ";
                 
private static final String COLOR_MESSAGE = 
                 "The Color menu \n" + 
                 "The Color menu ";
                 
private static final String SCREEN_MESSAGE = 
                 "The Screen menu \n" + 
                 "The Screen menu \n" + 
                 "The Screen menu";                                  

} // End MenuBarTuttleHelpDialog class.


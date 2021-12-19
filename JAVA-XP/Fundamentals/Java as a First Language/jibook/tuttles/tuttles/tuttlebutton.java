// Filename TuttleButton.java.
// Contains an extended Canvas component to supply
// button behaviour with an image as its label.
//
// Written for the Java Interface book, Chapter 5.
// Fintan Culwin, v 0.2, August 1997.

package Tuttles;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;


public class TuttleButton extends Canvas { 

private static final int     BORDER_WIDTH         = 2;
private static final Color   DEFAULT_BORDER_COLOR = 
                                      new Color( 0x80, 0x80, 0x80); 

private Image           buttonImage   = null;
private String          imageSource   = null;
private int             buttonWidth   = -1;
private int             buttonHeight  = -1;
private boolean         pressed       = false;
private Color           borderColour; 
private String          actionCommand = null;
private ActionListener  itsListener;
private Applet          itsApplet;
  
  
   public TuttleButton( String theSource, 
                        Applet applet) { 
      this( theSource, applet,  DEFAULT_BORDER_COLOR); 
   } // End TuttleButton constructor.
  

   public TuttleButton( String theSource,
                        Applet applet,
                        Color  colorForBorder) { 
      super();
      imageSource = new String( "Tuttles/Images/" + theSource);       
      itsApplet = applet;
      this.setForeground( colorForBorder);       
      this.enableEvents( AWTEvent.MOUSE_EVENT_MASK); 
   } // End TuttleButton constructor.

   
   public void addNotify() { 
   
   MediaTracker aTracker;
   Component    possible = this.getParent();
   Applet       theApplet; 

      super.addNotify();     
       
      while ( !( possible instanceof Applet)) { 
         possible = possible.getParent();
      } // End while.
      theApplet = (Applet) possible;

      buttonImage = ( theApplet.getImage( 
                      theApplet.getCodeBase(), imageSource));         
      aTracker = new MediaTracker( this);
      aTracker.addImage( buttonImage, 0);
      try { 
          aTracker.waitForID( 0);
      } catch ( InterruptedException exception) {       
         // Do nothing!                                
      } // End try/ catch. 
 
      if ( buttonImage == null              ||
           buttonImage.getWidth( this)  < 1 ||
           buttonImage.getHeight( this) < 1 ){ 
         System.err.println( "The image " + imageSource + 
                             "\nCould not be loaded, abending ..."); 
         System.exit( -1);  
      } // End if.          
      buttonWidth  = buttonImage.getWidth( this) + BORDER_WIDTH *2;
      buttonHeight = buttonImage.getHeight(this) + BORDER_WIDTH *2; 
      this.setSize( buttonWidth, buttonHeight);    
   } // End addNotify;   


   public Dimension getMinimumSize() {
      return(new Dimension( buttonWidth, buttonHeight));
   } // End getMinimumSize.

   public Dimension getPreferredSize() {
     return this.getMinimumSize();
   } // End getPreferredSize.

     
   public void update(Graphics systemContext) {
      this.paint( systemContext);
   } // End update. 
  
   public void paint( Graphics systemContext) { 
   
   int index;

      systemContext.drawImage( buttonImage, BORDER_WIDTH, BORDER_WIDTH, this);
      for ( index=0; index < BORDER_WIDTH; index++) { 
         systemContext.draw3DRect( index, index,
                                   buttonWidth  - index -1,
                                   buttonHeight - index -1, !pressed);                                  
      } // End for.
   } // End paint;


   public void setActionCommand( String command) {
       actionCommand = command;
   } // End setActionCommand.
    
   public String getActionCommand() {
      if ( actionCommand == null) { 
         return "Tuttle Button";
      } else { 
         return actionCommand;
      } // End if.   
   } // End getActionCommand.


   public void addActionListener(ActionListener listener) {
      itsListener = AWTEventMulticaster.add( itsListener, listener);
   } // End addActionListener.

   public void removeActionListener(ActionListener listener) {
      itsListener = AWTEventMulticaster.remove( itsListener, listener);
   } // End removeActionListener.
   
        
   protected void processMouseEvent( MouseEvent event) {        
      switch ( event.getID()) { 
                  
      case MouseEvent.MOUSE_EXITED: 
         pressed = false;
         repaint();
         break;
         
      case MouseEvent.MOUSE_PRESSED:
         pressed = true;
         repaint();        
         break;
         
      case MouseEvent.MOUSE_RELEASED: 
         if ( (pressed)             && 
              (itsListener != null) ){
           itsListener.actionPerformed( new ActionEvent(this, 
                                            ActionEvent.ACTION_PERFORMED,
                                            this.getActionCommand()));                                                   
         }  // End if.
         pressed = false;
         repaint();       
         break;                      
      } // End switch.     
   } // End processMouseEvent.
} // End TuttleButton class.

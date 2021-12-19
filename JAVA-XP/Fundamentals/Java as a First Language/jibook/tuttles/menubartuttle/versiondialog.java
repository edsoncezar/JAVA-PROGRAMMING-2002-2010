// Filename VersionDialog.java.
// Supplies a Dialog containing showing the 
// version information for an applet.
//
// Written for Java Interface book chapter 6.
// Fintan Culwin, v 0.2, August 1997.

package MenuBarTuttle;

import java.awt.*;
import java.awt.event.*;

import MessageCanvas;


class VersionDialog extends    Dialog 
                    implements ActionListener { 

private MessageCanvas  versionMessage; 
private Panel          buttonPanel;
private Button         dismiss;
private Window         itsParentWindow;

    
   protected VersionDialog( Frame  itsParentFrame,                             
                            String itsMessage ){ 
   
     super( itsParentFrame, "Version", false);
     itsParentWindow = (Window) itsParentFrame;
     this.setFont( itsParentFrame.getFont());
     
     versionMessage = new MessageCanvas( itsMessage);
     versionMessage.setBackground( Color.white);     

     buttonPanel = new Panel();     
     buttonPanel.setBackground( Color.white);
     
     dismiss = new Button( "OK");
     dismiss.addActionListener( this);
     buttonPanel.add( dismiss);  
         
     this.add( versionMessage, "Center");    
     this.add( buttonPanel, "South");
     this.pack();
     this.doLayout();
   } // End VersionDialog constructor.
   
                               
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


   public void actionPerformed( ActionEvent event) {
      this.setVisible( false);   
   } // End actionPerformed. 
      

   
                                        
} // End VersionDialog.




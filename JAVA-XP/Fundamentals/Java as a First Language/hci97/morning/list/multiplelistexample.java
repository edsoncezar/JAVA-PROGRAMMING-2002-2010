// Filename MultipleListExample.java.
// Provides an example of the AWT List class, illustrating 
// scrollbar and multiple selction possibilities.  
// Written for the Java interface book Chapter 2 - see text.
// This version with a scroll bar.
//
// Fintan Culwin, v 0.2, August 1997.


import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class MultipleListExample extends    Applet 
                                 implements ItemListener { 
                                 
private TextArea feedback;
                                 
   public void init() {

   List     cityList    = new List( 4, true);
   Label    promptLabel = new Label( "Which cities have you visited?");
   Panel    listPanel   = new Panel( new BorderLayout());
   Panel    topPanel    = new Panel();



      this.setBackground( Color.yellow);
      this.setFont( new Font( "Times", Font.BOLD, 24)); 
      this.setLayout( new BorderLayout());
     
      cityList.addItem( "London");
      cityList.addItem( "Paris");
      cityList.addItem( "Barcelona");
      cityList.addItem( "Athens");
      cityList.addItem( "Rome");
      cityList.addItem( "Istanbul");
      cityList.addItem( "Berlin");

      cityList.addItemListener( this); 
     
      listPanel.add( promptLabel, "North");     
      listPanel.add( cityList,    "Center");     

      topPanel = new Panel();
      topPanel.add( listPanel);
      this.add( topPanel, "Center");

      feedback = new TextArea( 5, 60);
      this.add( feedback, "South");  
   } // End init.


  public  void itemStateChanged(ItemEvent event) {  
  
  List   originator = (List) event.getItemSelectable();
  String visited[]  = originator.getSelectedItems();
  StringBuffer message = new StringBuffer(""); 

      if ( visited.length == 0) { 
         message.append( "    No items are now selected!\n");
      } else {      
         for ( int index = 0; index < visited.length; index++ ) {     
            message.append( visited[ index] + " ");
         } // End for.
         message.append( "\n");          
      } // End if.
      feedback.append( message.toString());
  } // End itemStateChanged.
  


   public static void main(String args[]) {

   Frame        frame     = new Frame("Mutiple List Example demo");
   MultipleListExample theExample = new MultipleListExample();

      theExample.init();
      frame.add("Center", theExample);

      frame.show();
      frame.setSize( frame.getPreferredSize());

   } // End main.

} // end class MultipleListExample.











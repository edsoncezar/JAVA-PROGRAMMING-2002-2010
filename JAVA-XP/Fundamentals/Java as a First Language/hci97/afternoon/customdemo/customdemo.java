// Filename CustomDemo.java.
// Program to illustrate customisation of applets and applications.
//
// Written for JI book, Chapter 9 see text.
// Fintan Culwin, v0.2, August 1997.

import java.applet.*;
import java.awt.*;
import java.util.*;

public class CustomDemo extends Applet { 

String fontName             = new String( "Times");
String fontStyle            = new String( "PLAIN");
String fontSize             = new String( "14");
String foregroundColorName  = new String( "black");
String backgroundColorName  = new String( "white");
   
   public void init() {    

   Properties    preset   = System.getProperties();
//   String        userName = new String( preset.getProperty( "user.name"));
   String        userName = new String( "fintan");   
   AppletContext itsContext;
   Label         demoLabel = new Label();

      try { 
         itsContext = this.getAppletContext();
         this.setFontName(  this.getParameter("FONTNAME"));
         this.setFontStyle( this.getParameter("FONTSTYLE"));
         this.setFontSize(  this.getParameter("FONTSIZE"));
         this.setBackgroundColorName( this.getParameter("BACKGROUND"));
         this.setForegroundColorName( this.getParameter("FOREGROUND")); 
      } catch ( NullPointerException  exception) {    
         // Do nothing.
      } // End try/ catch.
      this.setResources();
     
      demoLabel.setText( "hello " + userName);
      this.add( demoLabel);
   } // End init.


   private void setFontName( String setTo) { 
      fontName = new String( setTo);
   } // End setFontName.
   
   private void setFontStyle( String setTo) { 
      fontStyle = new String( setTo);
   } // End setFontStyle.   

   private void setFontSize( String setTo) { 
      fontSize = new String( setTo);
   } // End setFontSize.

   private void setForegroundColorName( String setTo) { 
      foregroundColorName = new String( setTo);
   } // End setForegroundColorName.
      
   private void setBackgroundColorName( String setTo) { 
      backgroundColorName = new String( setTo);
   } // End setBackgroundColorName.

   
   private void setResources() { 

   Font  theFont;
   int   theFontStyle = Font.PLAIN;
   int   theFontSize;
   Color theBackgroundColor;
   Color theForegroundColor;   

      if ( fontStyle.equalsIgnoreCase( "ITALIC")) { 
         theFontStyle = Font.ITALIC;
      } else if ( fontStyle.equalsIgnoreCase( "BOLD")) { 
         theFontStyle = Font.BOLD;
      } // End if.
      
      try { 
         theFontSize = Integer.parseInt( fontSize);
      } catch ( NumberFormatException exception) { 
         theFontSize = 12;
      } // End try/ catch.
   
      theFont = new Font( fontName, theFontStyle, theFontSize);
      if ( theFont != null) { 
         this.setFont( theFont);      
      } // End if.
      
      theBackgroundColor =  this.identifyColor( backgroundColorName);     
      if ( theBackgroundColor != null) { 
         this.setBackground( theBackgroundColor);               
      } // End if.
      
      theForegroundColor =  this.identifyColor( foregroundColorName);
      if ( theForegroundColor != null) { 
         this.setForeground( theForegroundColor);      
      } // End if.   
   } // End setResources.
   
   
   private Color identifyColor( String toIdentify){ 
   
   Color identifiedColor = null;
   
      if ( toIdentify.equalsIgnoreCase( "red")) { 
         identifiedColor = Color.red;
      } else if ( toIdentify.equalsIgnoreCase( "green")) { 
         identifiedColor = Color.green;
      } else if ( toIdentify.equalsIgnoreCase( "blue")) { 
         identifiedColor = Color.blue;
      } else if ( toIdentify.equalsIgnoreCase( "black")) { 
         identifiedColor = Color.black;
      } else if ( toIdentify.equalsIgnoreCase( "white")) { 
         identifiedColor = Color.white;
      } else if ( toIdentify.equalsIgnoreCase( "gray")) { 
         identifiedColor = Color.gray;
      } else if ( toIdentify.equalsIgnoreCase( "lightGray")) { 
         identifiedColor = Color.lightGray;
      } else if ( toIdentify.equalsIgnoreCase( "darkGray")) { 
         identifiedColor = Color.darkGray;
      } else if ( toIdentify.equalsIgnoreCase( "magenta")) { 
         identifiedColor = Color.magenta;
      } else if ( toIdentify.equalsIgnoreCase( "orange")) { 
         identifiedColor = Color.orange;
      } else if ( toIdentify.equalsIgnoreCase( "pink")) { 
         identifiedColor = Color.pink;
      } else if ( toIdentify.equalsIgnoreCase( "yellow")) { 
         identifiedColor = Color.yellow;
      } else if ( toIdentify.equalsIgnoreCase( "cyan")) { 
         identifiedColor = Color.cyan;
      } // End if.
      
      return identifiedColor;
   } // End identifyColor.
   


   public static void main( String args[]) { 

   Frame      frame   = new Frame("Custom Demo");
   CustomDemo theDemo = new CustomDemo();
   String     parameter;
   int        argIndex; 


      for (argIndex =0; argIndex < args.length; argIndex++) {     
      
          parameter = args[ argIndex].substring( 
                                       args[ argIndex].indexOf( "=") +1,
                                       args[ argIndex].length());  
        
          if ( args[ argIndex].toLowerCase().startsWith( "fontname")) { 
             theDemo.setFontName( parameter);   
          } else if ( args[ argIndex].toLowerCase().startsWith( "fontstyle")) {  
             theDemo.setFontStyle( parameter);            
          } else if ( args[ argIndex].toLowerCase().startsWith( "fontsize")) {
             theDemo.setFontSize( parameter);   
          } else if ( args[ argIndex].toLowerCase().startsWith( "foreground")) {  
             theDemo.setForegroundColorName( parameter);
          } else if ( args[ argIndex].toLowerCase().startsWith( "background")) {
              theDemo.setBackgroundColorName( parameter);
          } // End if.     
      } // End for.
           
      theDemo.init();
      frame.add(theDemo, "Center");
      frame.show();
      frame.setSize( frame.getPreferredSize());
   } // end main.
   
} // End CustomDemo


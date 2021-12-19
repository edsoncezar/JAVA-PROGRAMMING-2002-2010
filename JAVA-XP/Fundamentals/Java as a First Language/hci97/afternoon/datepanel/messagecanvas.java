// Filename MessageCanvas.java.
// Provides a reusable centred multi-line label component.
// 
// Written for the JI book, first used in Chapter 3.
// Fintan Culwin, v 0.2, August 1997.


import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;


public class MessageCanvas extends Canvas { 

private int    maximumWidth   = 0;
private int    characterHeight;
private String theMessage[];


   public MessageCanvas( String message) { 
                            
   StringTokenizer tokenizer     = new StringTokenizer(message, "\n");
   int             numberOfLines = tokenizer.countTokens();
   int             index; 
   
      theMessage = new String[ numberOfLines]; 
      for ( index =0; index < numberOfLines; index++){ 
         theMessage[ index] = ((String) tokenizer.nextToken()).trim();
      } // End for.      
   } // End MessageCanvas constructor.


   public void addNotify() { 
      super.addNotify();
      this.setSizes();       
   } // End addNotify.


   private void setSizes(){ 
   
   FontMetrics     theMetrics = this.getFontMetrics( this.getFont()); 
   int             thisWidth;
   int             index; 
   
      characterHeight = theMetrics.getHeight();
      for ( index =0; index < theMessage.length; index++){
         thisWidth = theMetrics.stringWidth( theMessage[ index]);
         if ( thisWidth > maximumWidth) { 
            maximumWidth = thisWidth;
         } // End if. 
      } // End for.
   } // End setSizes.
   

   public void paint( Graphics graphics) { 
   
   int         index;
   int         leftOffset;
   int         totalHeight = theMessage.length * characterHeight;
   int         itsWidth    = this.getSize().width;
   int         fromTop     = ((this.getSize().height - totalHeight) /2) +
                             characterHeight/2; 
   FontMetrics theMetrics  = this.getFontMetrics( this.getFont());    

      for ( index =0; index < theMessage.length; index++){  
         leftOffset = ( itsWidth - theMetrics.stringWidth( theMessage[ index]))/2;      
         graphics.drawString( theMessage[ index], 
                              leftOffset, fromTop);
         fromTop+= characterHeight;                     
      } // End for.                                     
   } // End paint.


   public  Dimension getPreferredSize(){ 
      return new Dimension( maximumWidth+20, 
                           (characterHeight * theMessage.length) +20);   
   } // End preferredSize.
   
   public  Dimension getMinimumSize(){ 
      return new Dimension( maximumWidth, 
                           (characterHeight * theMessage.length));   
   } // End minimumSize.
} // End MessageCanvas.
   

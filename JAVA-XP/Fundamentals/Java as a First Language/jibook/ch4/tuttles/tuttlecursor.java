// Filename TuttleCursor.java.
// Provides a rotating cursor capability for the 
// Tuttle class.
//
// Written for the Java Interface Book Chapter 4.
// Fintan Culwin, v 0.2, August 1997.


package Tuttles;


import java.awt.*;
import java.awt.image.*;
import java.lang.Math;


class TuttleCursor extends Object { 

private int       imageWidth;  
private int       imageHeight;  
private int       pixels[];
private int       rotatedPixels[];  
private Component component;   
private Image     rotatedImage;       
   

   protected TuttleCursor( Image     toRotate,
                           Component observer) { 
   
   PixelGrabber grabber;
   boolean      status;
   
      component     =  observer;
      imageWidth    =  toRotate.getWidth( observer);
      imageHeight   =  toRotate.getHeight( observer);
      pixels        =  new int[ imageWidth * imageHeight];
      rotatedPixels =  new int[ imageWidth * imageHeight];

      grabber       =  new PixelGrabber( toRotate, 0, 0,
                                         imageWidth,imageHeight,  
                                         pixels, 0, imageWidth);
     try {     
         status = grabber.grabPixels(); 
         if ( !status) { 
            throw new InterruptedException();
         } // end if.            
      } catch (InterruptedException e) {
         System.err.println("Exception grabbing pixels ... abending");
         System.exit( -1);            
      } // End try/catch.
   } // End TuttleCursor constructor.



   

   protected Image rotate( int angle) { 
   
   int    x, y;
   int    fromX, fromY;
   int    toX,   toY;
   int    transparent = 0x00000000;
   double radians  = (((double) (-(angle -180) ) %360) / 180.0) * Math.PI;
   double cosAngle = Math.cos( radians);
   double sinAngle = Math.sin( radians); 


      for ( y = 0; y < imageHeight; y++) { 
         for ( x = 0; x < imageWidth; x++) {
            // Rotate around the center of the image.
            toX   = ( imageWidth  /2) - x;
            toY   = ( imageHeight /2) - y;
            fromX = (int)( ( toX * cosAngle) - ( toY * sinAngle));
            fromY = (int)( ( toX * sinAngle) + ( toY * cosAngle)); 
            fromX += imageWidth  /2;
            fromY += imageHeight /2;          
                              
            if ( (fromX < 0) || (fromX >= imageWidth)  ||
                 (fromY < 0) || (fromY >= imageHeight) ){
               // Rotated point is outside the image
               rotatedPixels[ (y * imageWidth) + x] = transparent;
            } else {                            
               rotatedPixels[ (y * imageWidth) + x] =
                      pixels[ (fromY * imageWidth) + fromX];
            } // End if.          
         } // End x loop.
      } // End y loop.         
      


      rotatedImage =  component.createImage( 
                           new MemoryImageSource(imageWidth, imageHeight,  
                                                 rotatedPixels, 0, imageWidth));
      return rotatedImage;         
   } // end rotate;
   

   
   protected void setCursorColor( Color newColor) { 
   
   int x, y;
   int newColorMask    = 0;
   int transparentMask = 0xFF000000;
      
      newColorMask = 0xFF000000                  |
                     (newColor.getRed()   << 16) |
                     (newColor.getGreen() << 8)  |      
                      newColor.getBlue();
     
      for ( y = 0; y < imageHeight; y++) { 
         for ( x = 0; x < imageWidth; x++) {            
              if ( (pixels[ (y * imageWidth) + x] & transparentMask) 
                                                 == transparentMask ) { 
                   
                 pixels[ (y * imageWidth) + x] = newColorMask;              
              } // End if.                     
         } // End x loop.
      } // End y loop.                    
   } // End setCursorColor.

} // end TuttleCursor.



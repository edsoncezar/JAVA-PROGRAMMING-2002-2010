// Filename Tuttle.java.
// Providing tuttle (turtle) graphics capabilities by
// extending the Canvas class.
//
// Written for the Java Interface Book Chapter 4.
// Some facilities not explained until Chapter 8.
// Fintan Culwin, v 0.2, August 1997.


package Tuttles;

import java.awt.*;
import java.applet.*;
import java.awt.image.*;
import java.lang.Math;

import Tuttles.TuttleCursor;


public class Tuttle extends Canvas {


private static final int SCREEN_STEPS = 500;

private Image        tuttleImage;
private Image        theCursor;
private Graphics     tuttleGraphics;
private TuttleCursor rotatingCursor;
private Applet       itsApplet;

private int     xLocation = 0;
private int     yLocation = 0;
private int     direction = 0;       // The tuttles virtual location.

private int     screenX   = 0;
private int     screenY   = 0;       // The tuttles screen location.

private int     screenWidth;
private int     screenHeight;        // The physical dimensions of the screen area.

private double  horizontalScale;
private double  verticalScale;       // Virtual to physical conversion factors.

private boolean penDown         = true;
private boolean showTuttle    = true;

private Color   currentForeground = Color.blue;
private Color   currentBackground = Color.yellow;


   public Tuttle( Applet applet, int width, int height) { 
      this.setSize( width, height);
      itsApplet = applet;     
   } // End Tuttle constructor.


   public void addNotify() { 
      super.addNotify();
      this.initialiseTuttle();
      this.initialiseCursor();
   } // End addNotify.


   private void initialiseTuttle() { 
        screenWidth    = this.getSize().width;
        screenHeight   = this.getSize().height;
        tuttleImage    = this.createImage( screenWidth, screenHeight);
        tuttleGraphics = tuttleImage.getGraphics();
        tuttleGraphics.setColor( currentBackground);
        tuttleGraphics.fillRect( 0, 0, screenWidth, screenHeight);
        tuttleGraphics.setColor( currentForeground);
        tuttleGraphics.translate( screenWidth  /2, screenHeight /2);
        horizontalScale = ((double) screenWidth  / (double) (SCREEN_STEPS * 2));
        verticalScale   = ((double) screenHeight / (double) (SCREEN_STEPS * 2));
   } // End initialiseTuttle.
   
   


   private void initialiseCursor() { 
   
   MediaTracker tuttleTracker;
   
      theCursor = ( itsApplet.getImage( 
                       itsApplet.getCodeBase(), "Tuttles/tuttle.gif"));           
      tuttleTracker = new MediaTracker( this);
      tuttleTracker.addImage( theCursor, 0);
      try { 
          tuttleTracker.waitForID( 0);
      } catch ( InterruptedException exception) {       
        // Do nothing
      } // End try/ catch.  

      if ( (theCursor == null)               || 
           (theCursor.getWidth(  this) < 1)  ||
           (theCursor.getHeight( this) < 1)  ){
         System.out.println( "Empty cursor image ... abending");
         System.exit( -1);         
      } // End if.
      rotatingCursor = new TuttleCursor( theCursor, this);
      rotatingCursor.setCursorColor( currentForeground); 
      theCursor = rotatingCursor.rotate( direction);      
   } // End initialiseCursor.
   


   public void update(Graphics systemContext) { 
      if ( showTuttle) { 
         paint( systemContext);
      } // End if. 
   } // End update.


   public void paint( Graphics systemContext ) { 
   
   int cursorCenterX;
   int cursorCenterY;

      systemContext.drawImage( tuttleImage, 0, 0, this);
      cursorCenterX = ((screenWidth/2) + screenX) - 
                      (theCursor.getWidth( this) /2);
      cursorCenterY = ((screenHeight/2) + screenY) - 
                      (theCursor.getHeight( this) /2);
      systemContext.drawImage( theCursor,
                               cursorCenterX, 
                               cursorCenterY,
                               this);
   } // End paint.
   
   
   
   public void forward( int steps) {

      int    possibleNewX;
      int    possibleNewY;
      int    localDegrees = (direction + 270 ) % 360;
      double radians      = (((double) localDegrees) / 180.0) * Math.PI;
      
      possibleNewX = xLocation +
               (int) (Math.cos( radians) * (double) steps);
      possibleNewY = yLocation +
               (int) (Math.sin( radians) * (double) steps);

      // Ensure that if steps is non-zero then the tuttle moves at 
      // least one unit in some direction!!    
      if ( ( steps != 0)                 && 
           ( possibleNewX == xLocation ) &&
           ( possibleNewY == yLocation ) ){
            
         double deltaX = ( Math.cos( radians) * (double) steps);
         double deltaY = ( Math.sin( radians) * (double) steps);; 

         if ( Math.abs( deltaX) > Math.abs( deltaY) ){ 
            if ( deltaX > 0.0) { 
               possibleNewX++;
            } else { 
               possibleNewX--;
            } // End if.   
         } else { 
            if ( deltaY > 0.0) { 
               possibleNewY++;
            } else { 
               possibleNewY--;
            } // End if                                 
         } // End if.                                                           
      } // End if.  
      
      if ( (possibleNewX >= -SCREEN_STEPS) && (possibleNewX <= SCREEN_STEPS) &&
           (possibleNewY >= -SCREEN_STEPS) && (possibleNewY <= SCREEN_STEPS) ) {
           
           int newX = (int) ( ((double) possibleNewX) * horizontalScale);               
           int newY = (int) ( ((double) possibleNewY) * verticalScale);
                        
           if ( penDown) { 
              tuttleGraphics.drawLine( screenX, screenY, newX, newY);
           } // End if.
           
           xLocation = possibleNewX;
           yLocation = possibleNewY;
           screenX   = newX;
           screenY   = newY;
           repaint();
      } // end if
   } // End forward.

   public void backward( int steps) {
      this.forward( -steps);
   } // End backward.


   public void setPenUp() {    
       penDown = false;
   } // End setPenUp. 

   public void setPenDown() {    
       penDown = true;
   } // End setPenUp. 
   

  
   public void turnRight( int turn ) {
      direction += turn;
      while ( direction < 0) { 
         direction += 360;
      } // End while.
      direction %= 360;
      theCursor = rotatingCursor.rotate( direction);
      this.repaint();
   } // End turnRight.

   public void turnLeft( int turn ) {
      turnRight( -turn);
   } // End turnLeft.
   

   
   public void clearTuttleArea() { 
        tuttleGraphics.setColor( currentBackground);
        tuttleGraphics.fillRect( -( screenWidth/2),
                                 -( screenHeight/2), 
                                 screenWidth, screenHeight);
        tuttleGraphics.setColor( currentForeground); 
        this.repaint();     
   } // End clearArea.
   
   
   public void resetTuttle() { 
      xLocation = 0;
      yLocation = 0;   
      screenX   = 0;
      screenY   = 0;
      direction = 0;
      theCursor = rotatingCursor.rotate( direction);
      this.repaint();
   } // End resetTuttle.

   public void clearAndReset() { 
      this.resetTuttle();
      this.clearTuttleArea();
   } // End clearAndReset;   
   
   public void clearAndReset( Color newColor) { 
      currentBackground = newColor;
      this.resetTuttle();
      this.clearTuttleArea();
   } // End clearAndReset;      
   
   public void setForeground( Color newColor) { 
      currentForeground = newColor;
      tuttleGraphics.setColor( currentForeground);
      rotatingCursor.setCursorColor( newColor);
      theCursor = rotatingCursor.rotate( direction);
      this.repaint();  
   } // End setForeground.
   
   
   public void setBackground( Color newColor) { 
   
   int    x, y;
   int    pixels[];
   Image  newImage;
   int    newColorMask;
   int    oldColorMask;
   Color  oldColor;  

   PixelGrabber grabber;
   boolean      status;   
   
      pixels            =  new int[ screenWidth * screenHeight];
      newColorMask      =  0xFF000000                  |
                           (newColor.getRed()   << 16) |
                           (newColor.getGreen() << 8)  |      
                           newColor.getBlue();
      oldColorMask      =  0xFF000000                           |
                           (currentBackground.getRed()   << 16) |
                           (currentBackground.getGreen() << 8)  |      
                           currentBackground.getBlue(); 
                           
      newImage = createImage( tuttleImage.getSource());                    
      grabber =  new PixelGrabber( newImage, 
                                   0, 0,
                                   screenWidth,screenHeight,  
                                   pixels, 0, screenWidth);                          


      try {    
         status = grabber.grabPixels();           
         if ( !status) { 
            throw new InterruptedException();
         } // end if.            
      } catch (InterruptedException e) {
         System.err.println("Exception grabbing pixels ... abending");
         System.exit( -1);            
      } // End try/catch.
      
      for ( y = 0; y < screenHeight; y++) { 
         for ( x = 0; x < screenWidth; x++) {            
              if ( pixels[ (y * screenWidth) + x] == oldColorMask) { 
                 pixels[ (y * screenWidth) + x] = newColorMask;              
              } // End if.
                               
         } // End x loop.
      } // End y loop.                                                                       
      

      newImage = this.createImage( new MemoryImageSource( 
                                             screenWidth, screenHeight,
                                             pixels, 0, screenWidth));
      tuttleGraphics.drawImage( newImage, -(screenWidth /2), 
                                -(screenHeight /2), this);                                                        

      currentBackground = newColor;                                                        
      this.repaint();   
   } // End setBackground.


   public String getDetails() { 

   StringBuffer buffer = new StringBuffer();
   
       buffer.append( "x : " + xLocation + 
                      " y : " + (yLocation * -1) + 
                      " d : " + direction);
       if ( penDown) { 
          buffer.append( " pen down");
       } else { 
          buffer.append( " pen up");
       } // End if.                      
       return buffer.toString();
   } // End showDetails.
   
   protected Point tuttleLocationIs(){ 
      return new Point( xLocation, (yLocation * -1));   
   } // End getLocation.
   
   protected int tuttleDirectionIs(){    
      return direction;
   } // End getDirection.   
   
   protected boolean penStatusIs(){ 
      return penDown;   
   } // End getPenStatus. 

   protected Color tuttleForegroundIs() { 
      return currentForeground;   
   } // End getForeground.
   
   protected Color tuttleBackgroundIs() { 
      return currentBackground;   
   } // End getBackground.
      
   protected void showTuttle(){ 
      showTuttle = true;
   } // End showTuttle

   protected void hideTuttle(){ 
      showTuttle = false;
   } //  End hideTuttle
   
} // End Tuttle.





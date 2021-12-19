/**
 * ---- general information ----
 *
 * Starfield.java: A Starfield applet.
 * 
 * @version 1.0, 14 Mar 2002
 * @author  Jan Keller
 * @mail    post@jan-keller.de
 * @url     www.jan-keller.de
 * @place   Leipzig, Germany
 *
 * ---- Description ----
 *
 * JAVA programm seeing a simulation of a fligth through a starfield on the internet.
 *
 * This program is postcard-ware.
 * If you like this program, send a postcard to:
 *
 *   Jan Keller
 *   Grassistraﬂe 31/315
 *   04107 Leipzig
 *   Germany
 *
 * Thanks in advance!
 * You are free to do anything you want with this program.
 * However, I am not responsible for any bugs in this program and
 * possible damage to hard- or software when using this program.
 * Please refer to this program when you are using it in one of your programs
 * Feel free to e-mail me at any time at post@jan-keller.de
 **/

import java.awt.*;
import java.applet.*;

class Star {

 int	H, V;      // middle of the screen
 int	x, y;   // position of the Star
 double z;

  Star( int width, int height, int depth) {
    H = width/2;
    V = height/2;
    x = (int)(Math.random()*width) - H;
    y = (int)(Math.random()*height) - V;
    if( (x == 0) && (y == 0 ) ) x = 10;  // we won't fly into a star ;-)
    z = (int)(Math.random()*depth);
  }

  public void Draw( Graphics g, double rot) {
    double X, Y;
    int    h, v, hh, vv;
    int    d;

    z  -= 0.5;               // we get closer
    if (z < -63) z = 100;

    hh = (int)((x*64)/(64+z));
    vv = (int)((y*64)/(64+z));
    X = (hh*Math.cos(rot))-(vv*Math.sin(rot));
    Y = (hh*Math.sin(rot))+(vv*Math.cos(rot));
    h = (int)X+H;
    v = (int)Y+V;
    if( (h < 0) || (h > (2*H))) z = 100;
    if( (v < 0) || (v > (2*H))) z = 100;
    GrayMe(g);

    d=(100-(int)z)/50;
    if( d == 0 ) d = 1;
    g.fillRect( h, v, d, d );
  }

  public void GrayMe(Graphics g) {
    if ( z > 50 ) { g.setColor( Color.gray ); }
    else if ( z > 25 ) { g.setColor( Color.lightGray ); }
         else { g.setColor( Color.white ); }
  }
}

public class StarField extends Applet implements Runnable {

  Dimension d;
  Thread    me = null;
  Image	im;
  Graphics  offscreen;
  Star	pol[];     // Points of light
  int       speed, stars, spin, move;
  double    rot;
  boolean   suspend = false;
  // the background
  MediaTracker tracker = new MediaTracker(this);  
  Image     bk;

  public void init() {
    d = getSize();
    
    // get parameters
    String theSpeed = getParameter("speed");
    if(theSpeed == null) speed = 10; else speed = Integer.valueOf(theSpeed).intValue();
    String theStars = getParameter("stars");
    if(theStars == null) stars = 100; else stars = Integer.valueOf(theStars).intValue();
    String theSpin = getParameter("spin");
    if(theSpin == null) spin = 2; else spin = Integer.valueOf(theSpin).intValue();
    System.out.println(theSpeed+" "+theStars+" "+theSpin);

    try {
      im = createImage(d.width, d.height);
      offscreen = im.getGraphics();
    }
    catch(Exception _ex) { offscreen = null; }

    pol = new Star[stars];
    for(int i = 0; i < stars; i++) pol[i] = new Star(d.width, d.height, 100);
    // maxdepth=100

    try {
      bk = getImage(getCodeBase(), "whirlpool.jpg");
      tracker.addImage(bk, 0);
      tracker.waitForID(0);
    }
    catch(Exception _ex) { _ex.printStackTrace(); }     

  }

  public void paint(Graphics g) {
    if(offscreen != null) {
      paintMe(offscreen);
      g.drawImage(im, 0, 0, this);
      return;
    } else {
      paintMe(g);
      return;
    }
  }

  public void paintMe(Graphics g) {
    g.setColor(Color.black);
    if (!tracker.isErrorID(0)) g.drawImage(bk,0,0,this);
      else g.fillRect(0, 0, d.width, d.height);
    for(int i = 0; i < stars; i++) pol[i].Draw(g, rot);
  }

  public void start() {
    if(me == null) {
      me = new Thread(this);
      me.start();
    }
  }

  public void stop() {
    if( me != null ) { me.stop(); me = null; }
  }

  public void run() {
    while( me != null ) { 
      rot += (double) spin / (double) 3600 * 6.3; 		// about 2 Pi
      try { Thread.sleep(speed); }
      catch(InterruptedException _ex) { }
      repaint();
    }
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void Toggle( ) {
    if( me != null ) {
      if( suspend ) { me.resume(); }
      else { me.suspend(); }
      suspend = !suspend;
    }
  }

}
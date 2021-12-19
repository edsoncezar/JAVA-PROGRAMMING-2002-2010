// this implementation of an image loop is better than sun's
// so i switched to this!	-LynX@java.pages.de

import java.awt.*;
import java.net.*;

/*****************************************************************************\
*                                                                             *
*  FHlogo v1.1 - Mein ZWEITES Java-Applet (beta)                              *
*                                                                             *
*  Dezember 1995 von Thorsten Ludewig                                         *
*  e-mail: th@rz.fh-wolfenbuettel.de                                          *
*  home:   http://www.fh-wolfenbuettel.de/~th/                                *
*                                                                             *
\*****************************************************************************/


public class ImageLoop extends java.applet.Applet implements Runnable
{
  int counter,
      nimages,
      width,
      height,
      sleep;

  Image simages = null;
  Image startup = null;

  Thread timer = null;

  boolean loaded = false;

  public String getAppletInfo() {
    return "ImageLoop v1.1\nDezember 1995 von Thorsten Ludewig\nhttp://www.fh-wolfenbuettel.de/~th/";
  }


  public String[][] getParameterInfo() {
    String[][] info = {
      {"startup\t\t", 	"url\t", "small startup image"},
      {"simages\t", 	"url\t", "LR images stream - a big picture :-)"},
      {"nimages\t", 	"int\t", "number of images in stream"},
      {"sleep\t\t", 	"int\t", "pause in milliseconds"}
    };
    return info;
  }


  public synchronized boolean imageUpdate(
        Image image, int flags, int x, int y, int w, int h ) {

    notifyAll();
    return true;
  }


  synchronized void WaitWhileImageIsLoading(Image image) {
    while ( image.getWidth(this) < 0 && image.getHeight(this) < 0 ) {
      try {
        wait();
      } catch (InterruptedException e) { }
    }
  }


  public void update(Graphics g) {

    if ( loaded ) {
      g.clipRect( 0, 0, width, height );
      g.drawImage(simages, -counter * width, 0, this);
    }
    else {
      if ( startup != null ) {
        g.drawImage(startup, 0, 0, this);
      } 
    }
  }


  public void paint( Graphics g ) {

    if ( ! loaded && startup == null ) {
      String pw = "PLEASE WAIT!";
      Font font = new Font( "Times", Font.PLAIN, 8 );
      FontMetrics fm = g.getFontMetrics();
      g.setColor( Color.red );
      g.drawRect( 0, 0, width-1, height-1 );
      g.setColor( Color.black );
      g.drawString( pw, ( width - fm.stringWidth( pw )) / 2, 
		(( height - font.getSize()) / 2 ) + font.getSize() );
    }

    update( g );
  }


  public void run() {

    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

    String startupURL = getParameter( "startup" );

    if ( loaded == false ) {
      try {
 
        if ( startupURL != null ) {
          showStatus( "ImageLoop: Loading startup image" );
          startup = getImage( getCodeBase(), startupURL );
          WaitWhileImageIsLoading( startup );
          repaint();
          Thread.sleep(250);
          repaint();
          Thread.sleep(250);
        }

        showStatus( "ImageLoop: Loading image stream" );
        simages = getImage(getCodeBase(), getParameter("simages"));
        WaitWhileImageIsLoading( simages );

        loaded = true;

        showStatus( "ImageLoop: finish" );

      } catch (InterruptedException e) { }
    }

    if ( simages != null ) {
      showStatus( "" );
      while ( timer != null ) {
        repaint();
        try {
          Thread.sleep(sleep);
        } catch (InterruptedException e) { }
	counter += 1;
	if ( counter == nimages ) counter = 0;
      }
    }


  }


  public void init() {
    String attr;

    loaded  = false;
    height  = size().height;
    width   = size().width;
    counter = 0;
    attr    = getParameter("nimages");
    nimages = (attr == null) ? 1 : Integer.valueOf(attr).intValue();
    attr    = getParameter("sleep");
    sleep   = (attr == null) ? 50 : Integer.valueOf(attr).intValue();
  }


  public void start() {
    if ( timer == null ) {
      timer = new Thread(this);
      timer.start();
    }
  }


  public void stop() {
    if ( timer != null && timer.isAlive()) {
      timer.stop();
    }
    timer = null;
  }
}


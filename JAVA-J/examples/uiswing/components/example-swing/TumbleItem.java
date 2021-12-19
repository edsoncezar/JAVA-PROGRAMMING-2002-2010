import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

/**
 * @author jag
 * @author mem
 * @author kwalrath
 */

public class TumbleItem extends JApplet
                        implements ActionListener {
    int loopslot = -1;  //the current frame number

    String dir;         //the directory relative to the codebase 
                        //from which the images are loaded

    Timer timer;        //the timer animating the images

    int pause;          //the length of the pause between revs

    int offset;         //how much to offset between loops
    int off;            //the current offset
    int speed;          //animation speed
    int nimgs;          //number of images to animate
    int width;          //width of the applet's content pane
    JComponent contentPane; //the applet's content pane

    ImageIcon imgs[];   //the images
    int maxWidth;       //width of widest image
    boolean finishedLoading = false;
    JLabel statusLabel;
    static Color[] labelColor = { Color.black, Color.black,
                                  Color.black, Color.black,
                                  Color.black, Color.white,
                                  Color.white, Color.white,
                                  Color.white, Color.white };

    public void init() {
        //Get the applet parameters.
        String at = getParameter("img");
        dir = (at != null) ? at : "images/tumble";
        at = getParameter("pause");
        pause = (at != null) ? Integer.valueOf(at).intValue() : 1900;
        at = getParameter("offset");
        offset = (at != null) ? Integer.valueOf(at).intValue() : 0;
        at = getParameter("speed");
        speed = (at != null) ? (1000 / Integer.valueOf(at).intValue()) : 100;
        at = getParameter("nimgs");
        nimgs = (at != null) ? Integer.valueOf(at).intValue() : 16;
        at = getParameter("maxwidth");
        maxWidth = (at != null) ? Integer.valueOf(at).intValue() : 0;

        //Animate from right to left if offset is negative.
        width = getSize().width;
        if (offset < 0) {
            off = width - maxWidth;
        }

        //Custom component to draw the current image
        //at a particular offset.
        contentPane = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (finishedLoading &&
                    (loopslot > -1) && (loopslot < nimgs)) {
                    imgs[loopslot].paintIcon(this, g, off, 0);
                } 
            }
        };
        contentPane.setBackground(Color.white);
        setContentPane(contentPane);

        //Put a "Loading Images..." label in the middle of
        //the content pane.  To center the label's text in
        //the applet, put it in the center part of a
        //BorderLayout-controlled container, and center-align
        //the label's text.
        statusLabel = new JLabel("Loading Images...",
                                 JLabel.CENTER);
        statusLabel.setForeground(labelColor[0]);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(statusLabel, BorderLayout.CENTER);

        //Set up the timer that will perform the animation.
        //Don't start it until all the images are loaded.
        timer = new Timer(speed, this);
        timer.setInitialDelay(pause);
        timer.setCoalesce(false);

        //Loading the images can take quite a while, so to
        //avoid staying in init() (and thus not being able
        //to show the "Loading Images..." label, we'll
        //load the images in a SwingWorker thread.
        imgs = new ImageIcon[nimgs];
        timer.start(); //Start the animation.
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                URL baseURL = getCodeBase();
                String prefix = dir + "/T";
                //Images are numbered 1 to nimgs,
                //but fill array from 0 to nimgs-1.
                for (int i = 0; i < nimgs; i++) {
                    imgs[i] = new ImageIcon(getURL(baseURL,
                                     prefix + (i+1) + ".gif"));
                }
                finishedLoading = true;
                return imgs;
            }
            public void finished() {
                //Remove the "Loading images" label.
                contentPane.removeAll();
                contentPane.repaint();
                loopslot = -1;
            }
        };
        worker.start();
    }

    //Update the the loopslot (frame number) and the offset.
    //If it's the last frame, restart the timer to get a long
    //pause between loops.
    public void actionPerformed(ActionEvent e) {
        loopslot++;

        if (!finishedLoading) {
            int colorIndex = loopslot % labelColor.length;
            try {
                statusLabel.setForeground(labelColor[colorIndex]);
            } catch (NullPointerException exc) {}
            return;
        }

        if (loopslot >= nimgs) {
            loopslot = 0;
            off += offset;

            if (off < 0) {
                off = width - maxWidth;
            } else if (off + maxWidth > width) {
                off = 0;
            }
        }

        contentPane.repaint();

        if (loopslot == nimgs - 1) {
            timer.restart();
        }
    }

    public void start() {
        if (finishedLoading && (nimgs > 1)) {
            timer.restart();
        }
    }

    public void stop() {
        timer.stop();
    }

    protected URL getURL(URL codeBase, String filename) {
        URL url = null;

        try {
            url = new URL(codeBase, filename);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Couldn't create image: badly specified URL");
            return null;
        }

        return url;
    }

    public String getAppletInfo() {
        return "Title: TumbleItem v1.2, 23 Jul 1997\n"
               + "Author: James Gosling\n"
               + "A simple Item class to play an image loop.";
    }
  
    public String[][] getParameterInfo() {
        String[][] info = {
          {"img", "string", "the directory containing the images to loop"},
          {"pause", "int", "pause between complete loops; default is 3900"},
          {"offset", "int", "offset of each image to simulate left (-) or "
                            + "right (+) motion; default is 0 (no motion)"},
          {"speed", "int", "the speed at which the frames are looped; "
                           + "default is 100"},
          {"nimgs", "int", "the number of images to be looped; default is 16"},
          {"maxwidth", "int", "the maximum width of any image in the loop; "
                              + "default is 0"}
        };
        return info;
    }
}

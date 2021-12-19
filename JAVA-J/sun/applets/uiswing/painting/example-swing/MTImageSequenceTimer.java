/*
 * Swing version.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * This applet displays several images in a row.  It preloads
 * the images using MediaTracker, which uses multiple background
 * threads to download the images.  The program displays a
 * "Please wait" message until all the images are fully loaded.  
 * Note that the Swing ImageIcon class uses MediaTracker to
 * preload images, so you can often use it instead of using
 * Images and MediaTracker directly.
 */

public class MTImageSequenceTimer extends JApplet 
                                  implements ActionListener {
    MTPanel mtPanel;
    static int frameNumber = -1;
    int delay;
    static boolean frozen = false;
    Timer timer;
    boolean error;
    MediaTracker tracker;

    //Invoked only when run as an applet.
    public void init() {
        //Load the images.
        Image images[] = new Image[10];
        for (int i = 1; i <= 10; i++) {
            images[i-1] = getImage(getCodeBase(), "images/T"+i+".gif");
        }
        buildUI(getContentPane(), images);
        startAnimation();
    }

    void buildUI(Container container, Image[] dukes) {
        tracker = new MediaTracker(this);
        for (int i = 1; i <= 10; i++) {
            tracker.addImage(dukes[i-1], 0);
            error = tracker.isErrorAny();
        }

        int fps = 10;

        //How many milliseconds between frames?
        delay = (fps > 0) ? (1000 / fps) : 100;

        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        mtPanel = new MTPanel(dukes);
        container.add(mtPanel, BorderLayout.CENTER);

        mtPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (frozen) {
                    frozen = false;
                    startAnimation();
                } else {
                    frozen = true;
                    stopAnimation();
                }
            }
       });
    }

    public void start() {
        startAnimation();
    }

    public void stop() {
        stopAnimation();
    }

    public synchronized void startAnimation() {
        if (frozen) { 
            //Do nothing.  The user has requested that we 
            //stop changing the image.
        } else {
            //Start animating!
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    public synchronized void stopAnimation() {
        //Stop the animating thread.
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {
        //Start downloading the images. Wait until they're
        //loaded before requesting repaints.
        try {
            tracker.waitForAll();
        } catch (InterruptedException exc) {}

        //Advance the frame.
        frameNumber++;

        //Display it.
        mtPanel.repaint();
    }

    class MTPanel extends JPanel {
        Image dukesWave[];

        public MTPanel(Image[] dukesWave) {
            this.dukesWave = dukesWave;
        }

        //Draw the current frame of animation.
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //paint the background
            int width = getWidth();
            int height = getHeight();

            //If not all the images are loaded,
            //just display a status string.
            if (!tracker.checkAll()) {
                g.drawString("Please wait...", 0, height/2);
                return;
            }

            //Paint the frame into the image.
            g.drawImage(dukesWave[MTImageSequenceTimer.frameNumber%10],
                        0, 0, this);
        }
    }

    //Invoked only when run as an application.
    public static void main(String[] args) {  
        Image[] waving = new Image[10];
        for (int i = 1; i <= 10; i++) {
            waving[i-1] =
                Toolkit.getDefaultToolkit().getImage("images/T"+i+".gif");
        }

        JFrame f = new JFrame("MTImageSequenceTimer");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        MTImageSequenceTimer controller = new MTImageSequenceTimer();
        controller.buildUI(f.getContentPane(), waving);
        controller.startAnimation();
        f.setSize(new Dimension(75, 100));
        f.setVisible(true);
    }       
}

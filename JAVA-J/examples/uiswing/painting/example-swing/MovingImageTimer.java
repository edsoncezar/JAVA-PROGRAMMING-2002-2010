/*
 * Swing version.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * Moves a foreground image in front of a background image.
 * See MovingLabels.java for an alternative implementation
 * that uses two labels instead of doing its own painting.
 */
public class MovingImageTimer extends JApplet 
                              implements ActionListener {
    int frameNumber = -1;
    boolean frozen = false;
    Timer timer;
    AnimationPane animationPane;

    static String fgFile = "images/rocketship.gif";
    static String bgFile = "images/starfield.gif";

    //Invoked only when run as an applet.
    public void init() {
        //Get the images.
        Image bgImage = getImage(getCodeBase(), bgFile);
        Image fgImage = getImage(getCodeBase(), fgFile);
        buildUI(getContentPane(), bgImage, fgImage);
    }
        
    void buildUI(Container container, Image bgImage, Image fgImage) {
        int fps = 10;

        //How many milliseconds between frames?
        int delay = (fps > 0) ? (1000 / fps) : 100;

        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        animationPane = new AnimationPane(bgImage, fgImage);
        container.add(animationPane, BorderLayout.CENTER);

        animationPane.addMouseListener(new MouseAdapter() {
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

    //Invoked by a browser only.
    public void start() {
        startAnimation();
    }

    //Invoked by a browser only.
    public void stop() {
        stopAnimation();
    }

    //Can be invoked from any thread.
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

    //Can be invoked from any thread.
    public synchronized void stopAnimation() {
        //Stop the animating thread.
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent e) {
        //Advance animation frame.
        frameNumber++;

        //Display it.
        animationPane.repaint();
    }

    class AnimationPane extends JPanel {
        Image background, foreground;
    
        public AnimationPane(Image background, Image foreground) {
            this.background = background;
            this.foreground = foreground;
        }
    
        //Draw the current frame of animation.
        public void paintComponent(Graphics g) {
            super.paintComponent(g);  //paint any space not covered
                                      //by the background image
            int compWidth = getWidth();
            int compHeight = getHeight();
            int imageWidth, imageHeight;
    
            //If we have a valid width and height for the 
            //background image, draw it.
            imageWidth = background.getWidth(this);
            imageHeight = background.getHeight(this);
            if ((imageWidth > 0) && (imageHeight > 0)) {
                g.drawImage(background, 
                            (compWidth - imageWidth)/2,
                            (compHeight - imageHeight)/2, this);
            } 
    
            //If we have a valid width and height for the 
            //foreground image, draw it.
            imageWidth = foreground.getWidth(this);
            imageHeight = foreground.getHeight(this);
            if ((imageWidth > 0) && (imageHeight > 0)) {
                g.drawImage(foreground, 
                            ((frameNumber*5)
                              % (imageWidth + compWidth))
                              - imageWidth,
                            (compHeight - imageHeight)/2,
                            this);
            }
        }
    }

    //Invoked only when run as an application.
    public static void main(String[] args) {
        Image bgImage = Toolkit.getDefaultToolkit().getImage(
                                MovingImageTimer.bgFile);
        Image fgImage = Toolkit.getDefaultToolkit().getImage(
                                MovingImageTimer.fgFile);

        JFrame f = new JFrame("MovingImageTimer");
        final MovingImageTimer controller = new MovingImageTimer();
        controller.buildUI(f.getContentPane(), bgImage, fgImage);

        f.addWindowListener(new WindowAdapter() {
            public void windowIconified(WindowEvent e) {
                controller.stopAnimation();
            }
            public void windowDeiconified(WindowEvent e) {
                controller.startAnimation();
            }
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(new Dimension(500, 125));  
        f.setVisible(true);
        controller.startAnimation();
    }
}

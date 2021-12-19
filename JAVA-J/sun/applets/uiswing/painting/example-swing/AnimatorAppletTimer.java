/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* 
 * A template for animation applets.
 */

public class AnimatorAppletTimer extends JApplet 
                                 implements ActionListener {
    int frameNumber = -1;
    Timer timer;
    boolean frozen = false;
    JLabel label;

    public void init() {
        String str;
        int fps = 0;

        //How many milliseconds between frames?  
        str = getParameter("fps");
        try { 
            if (str != null) {
                fps = Integer.parseInt(str);
            }
        } catch (Exception e) {}

        int delay = (fps > 0) ? (1000 / fps) : 100;

        //Set up a timer that calls this object's action handler.
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        label = new JLabel("Frame   ", JLabel.CENTER);

        label.addMouseListener(new MouseAdapter() {
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

        getContentPane().add(label, BorderLayout.CENTER);
    }

    //Invoked by the browser only.  invokeLater not needed
    //because startAnimation can be called from any thread.
    public void start() {
        startAnimation();
    }

    //Invoked by the browser only.  invokeLater not needed
    //because stopAnimation can be called from any thread.
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
        //Advance the animation frame.
        frameNumber++;

        //Request that the frame be painted.
        label.setText("Frame " + frameNumber);
    }
}

/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.*;

/* 
 * Based on Arthur van Hoff's animation examples, this application
 * can serve as a template for all animation applications.
 */
public class AnimatorApplication extends Frame 
                                 implements Runnable {
    int frameNumber = -1;
    int delay;
    Thread animatorThread;
    boolean frozen = false;

    AnimatorApplication(int fps, String windowTitle) {
        super(windowTitle);
        delay = (fps > 0) ? (1000 / fps) : 100;

        addMouseListener(new MouseAdapter() {
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

        addWindowListener(new WindowAdapter() {
            public void windowIconified(WindowEvent e) {
                stopAnimation();
            }
            public void windowDeiconified(WindowEvent e) {
                startAnimation();
            }
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }  
        });
    }

    public void startAnimation() {
        if (frozen) {
            //Do nothing.  The user has requested that we
            //stop changing the image.
        } else {
            //Start animating!
            if (animatorThread == null) {
                animatorThread = new Thread(this);
            }
            animatorThread.start();
        }
    }

    public void stopAnimation() {
        //Stop the animating thread.
        animatorThread = null;
    }

    public void run() {
        //Just to be nice, lower this thread's priority
        //so it can't interfere with other processing going on.
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        //Remember the starting time.
        long startTime = System.currentTimeMillis();

        //Remember which thread we are.
        Thread currentThread = Thread.currentThread();

        //This is the animation loop.
        while (currentThread == animatorThread) {
            //Advance the animation frame.
            frameNumber++;

            //Display it.
            repaint();

            //Delay depending on how far we are behind.
            try {
                startTime += delay;
                Thread.sleep(Math.max(0, 
                             startTime-System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    //Draw the current frame of animation.
    public void paint(Graphics g) {
        g.drawString("Frame " + frameNumber, 5, 50);
    }

    public static void main(String args[]) {
        AnimatorApplication animator = null;
        int fps = 10;

        // Get frames per second from the command line argument
        if (args.length > 0) {
            try {
                fps = Integer.parseInt(args[0]);
            } catch (Exception e) {}
        }
        animator = new AnimatorApplication(fps, "Animator");
        animator.setSize(200, 60);
        animator.setVisible(true);
        animator.startAnimation();
    }
}

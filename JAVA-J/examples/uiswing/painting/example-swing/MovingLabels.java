/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MovingLabels extends JApplet
                          implements ActionListener {
    int frameNumber = -1;
    Timer timer;
    boolean frozen = false;

    JLayeredPane layeredPane;
    JLabel bgLabel, fgLabel;
    int fgHeight, fgWidth;
    int bgHeight, bgWidth;
    static String fgFile = "images/rocketship.gif";  
    static String bgFile = "images/starfield.gif";

    //Invoked only when run as an applet.
    public void init() {
        Image bgImage = getImage(getCodeBase(), bgFile);
        Image fgImage = getImage(getCodeBase(), fgFile);
        buildUI(getContentPane(), bgImage, fgImage);
    }

    void buildUI(Container container, Image bgImage, Image fgImage) {
        final ImageIcon bgIcon = new ImageIcon(bgImage);
        final ImageIcon fgIcon = new ImageIcon(fgImage);
        bgWidth = bgIcon.getIconWidth();
        bgHeight = bgIcon.getIconHeight();
        fgWidth = fgIcon.getIconWidth();
        fgHeight = fgIcon.getIconHeight();
        
        //Set up a timer that calls this object's action handler
        timer = new Timer(100, this); //delay = 100 ms
        timer.setInitialDelay(0);
        timer.setCoalesce(true);

        //Create a label to display the background image.
        bgLabel = new JLabel(bgIcon);
        bgLabel.setOpaque(true);
        bgLabel.setBounds(0, 0, bgWidth, bgHeight);

        //Create a label to display the foreground image.
        fgLabel = new JLabel(fgIcon);
        fgLabel.setBounds(-fgWidth, -fgHeight, fgWidth, fgHeight);

        //Create the layered pane to hold the labels.
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(
                       new Dimension(bgWidth, bgHeight));
        layeredPane.addMouseListener(new MouseAdapter() {
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

        layeredPane.add(bgLabel, new Integer(0));  //low layer
        layeredPane.add(fgLabel, new Integer(1));  //high layer
        container.add(layeredPane, BorderLayout.CENTER);
    }
                
    //Invoked by the applet browser only.
    public void start() {
        startAnimation();
    }

    //Invoked by the applet browser only.
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
        //Advance animation frame.
        frameNumber++;

        //Display it.
        fgLabel.setLocation(
            ((frameNumber*5)
             % (fgWidth + bgWidth))
            - fgWidth,
            (bgHeight - fgHeight)/2);
    }   

    //Invoked only when run as an application.
    public static void main(String[] args) {
        Image bgImage = Toolkit.getDefaultToolkit().getImage(
                                MovingLabels.bgFile);
        Image fgImage = Toolkit.getDefaultToolkit().getImage(
                                MovingLabels.fgFile);
        final MovingLabels movingLabels = new MovingLabels();
        JFrame f = new JFrame("MovingLabels");
        f.addWindowListener(new WindowAdapter() {
            public void windowIconified(WindowEvent e) {
                movingLabels.stopAnimation();
            }
            public void windowDeiconified(WindowEvent e) {
                movingLabels.startAnimation();
            }
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        movingLabels.buildUI(f.getContentPane(), bgImage, fgImage);
        f.setSize(500, 125);
        f.setVisible(true);
        movingLabels.startAnimation();
    }
}

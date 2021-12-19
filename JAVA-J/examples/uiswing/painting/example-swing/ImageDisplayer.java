/*
 * Swing.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* 
 * This applet displays a single image twice,
 * once at its normal size and once much wider.
 */

public class ImageDisplayer extends JApplet {
    static String imageFile = "images/rocketship.gif";

    public void init() {
        Image image = getImage(getCodeBase(), imageFile);
        ImagePanel imagePanel = new ImagePanel(image);
        getContentPane().add(imagePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        Image image = Toolkit.getDefaultToolkit().getImage(
                                        ImageDisplayer.imageFile);
        ImagePanel imagePanel = new ImagePanel(image);

        JFrame f = new JFrame("ImageDisplayer");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.getContentPane().add(imagePanel, BorderLayout.CENTER);
        f.setSize(new Dimension(550,100));
        f.setVisible(true);
    }
}

class ImagePanel extends JPanel {
    Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background

        //Draw image at its natural size first.
        g.drawImage(image, 0, 0, this); //85x62 image

        //Now draw the image scaled.
        g.drawImage(image, 90, 0, 300, 62, this);
    }
}

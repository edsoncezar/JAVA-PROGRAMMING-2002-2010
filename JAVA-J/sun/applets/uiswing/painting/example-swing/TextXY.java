/* 
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* 
 * Very simple applet that illustrates parameters to text-drawing methods.
 */
public class TextXY extends JApplet {
    //Invoked only when executed as applet.
    public void init() {
        buildUI(getContentPane());
    }

    public void buildUI(Container container) {
        TextPanel textPanel = new TextPanel();
        container.add(textPanel, BorderLayout.CENTER);
    }
    
    class TextPanel extends JPanel {
        public TextPanel() {
            setPreferredSize(new Dimension(400, 100));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("drawString() at (2, 5)", 2, 5);
            g.drawString("drawString() at (2, 30)", 2, 30);
            g.drawString("drawString() at (2, height)", 2, getHeight());
        }
    }

    //Invoked only when executed as application.
    public static void main(String[] args) {
        JFrame f = new JFrame("TextXY");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        TextXY controller = new TextXY();
        controller.buildUI(f.getContentPane());
        f.pack();
        f.setVisible(true);
    }
}

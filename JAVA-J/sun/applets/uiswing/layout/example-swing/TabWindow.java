/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TabWindow extends JFrame {
    boolean inAnApplet = true;
     
    final static String BUTTONPANEL = "JPanel with JButtons";
    final static String TEXTPANEL = "JPanel with JTextField";

    public TabWindow() {
        Container contentPane = getContentPane();

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel p1 = new JPanel() {
            //Force the window to be 400+ pixels wide.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width = 400;
                return size;
            }
        };
        p1.add(new JButton("Button 1"));
        p1.add(new JButton("Button 2"));
        p1.add(new JButton("Button 3"));
        tabbedPane.addTab(BUTTONPANEL, p1);

        JPanel p2 = new JPanel();
        p2.add(new JTextField("TextField", 20));
        tabbedPane.addTab(TEXTPANEL, p2);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (inAnApplet) {
                    dispose();
                } else {
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String args[]) {
        TabWindow window = new TabWindow();
        window.inAnApplet = false;

        window.setTitle("TabWindow");
        window.pack();
        window.setVisible(true);
    }
}

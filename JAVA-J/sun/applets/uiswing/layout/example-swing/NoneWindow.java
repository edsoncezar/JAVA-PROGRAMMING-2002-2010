/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NoneWindow extends JFrame {
    boolean inAnApplet = true;
    private boolean laidOut = false;
    private JButton b1, b2, b3;

    public NoneWindow() {
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        b1 = new JButton("one");
        contentPane.add(b1);
        b2 = new JButton("two");
        contentPane.add(b2);
        b3 = new JButton("three");
        contentPane.add(b3);

        Insets insets = contentPane.getInsets();
        b1.setBounds(25 + insets.left, 5 + insets.top, 75, 20);
        b2.setBounds(55 + insets.left, 35 + insets.top, 75, 20);
        b3.setBounds(150 + insets.left, 15 + insets.top, 75, 30);

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
        NoneWindow window = new NoneWindow();
        Insets insets = window.getInsets();
        window.inAnApplet = false;

        window.setTitle("Absolute Positioning");
        window.setSize(250 + insets.left + insets.right,
                       90 + insets.top + insets.bottom);
        window.setVisible(true);
    }
}

/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlowWindow extends JFrame {
    boolean inAnApplet = true;
     
    public FlowWindow() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
   
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("2"));
        contentPane.add(new JButton("Button 3"));
        contentPane.add(new JButton("Long-Named Button 4"));
        contentPane.add(new JButton("Button 5"));

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
        FlowWindow window = new FlowWindow();
        window.inAnApplet = false;

        window.setTitle("FlowLayout");
        window.pack();
        window.setVisible(true);
    }
}

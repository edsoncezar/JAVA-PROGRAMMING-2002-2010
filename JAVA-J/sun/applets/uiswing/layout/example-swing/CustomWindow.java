/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomWindow extends JFrame {
    boolean inAnApplet = true;
     
    public CustomWindow() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new DiagonalLayout());
   
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("Button 2"));
        contentPane.add(new JButton("Button 3"));
        contentPane.add(new JButton("Button 4"));
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
        CustomWindow window = new CustomWindow();
        window.inAnApplet = false;

        window.setTitle("Custom Layout Manager");
        window.pack();
        window.setVisible(true);
    }
}

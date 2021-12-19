/*
 * FlowLayout converted to be SpringLayout.
 *
 * XXX This program does bad layout!  SpringWindow2 improves
 * XXX the layout, and SpringWindow3 further improves it.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpringWindow1 extends JFrame {
     
    public SpringWindow1() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());
   
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("2"));
        contentPane.add(new JButton("Button 3"));
        contentPane.add(new JButton("Long-Named Button 4"));
        contentPane.add(new JButton("Button 5"));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        SpringWindow1 window = new SpringWindow1();

        window.setTitle("SpringLayout");
        window.pack();
        window.setVisible(true);
    }
}

/*
 * FlowLayout converted to be SpringLayout.
 *
 * XXX This program brings up a window that's too small!  
 * XXX SpringWindow3 corrects the window size (by setting
 * XXX the SpringLayout-using container's size springs).
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpringWindow2 extends JFrame {
     
    public SpringWindow2() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());
   
        contentPane.add(new JButton("Button 1"));
        contentPane.add(new JButton("2"));
        contentPane.add(new JButton("Button 3"));
        contentPane.add(new JButton("Long-Named Button 4"));
        contentPane.add(new JButton("Button 5"));

        doLayout(contentPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void doLayout(Container parent) {
        //assert parent.getLayout() instanceof SpringLayout

        Component[] components = parent.getComponents();
        SpringLayout layout = (SpringLayout)parent.getLayout();
        Spring xPad = Spring.constant(5);
        Spring ySpring = Spring.constant(5);
        Spring xSpring = xPad;

        // Make every component 5 pixels away from the component to its left.
        for (int i = 0; i < components.length; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(components[i]);
            cons.setX(xSpring);
            xSpring = Spring.sum(xPad, cons.getConstraint("East")); //use proxy instead?

            cons.setY(ySpring);
        }
    }

    public static void main(String args[]) {
        SpringWindow2 window = new SpringWindow2();

        window.setTitle("SpringLayout");
        window.pack();
        window.setVisible(true);
    }
}

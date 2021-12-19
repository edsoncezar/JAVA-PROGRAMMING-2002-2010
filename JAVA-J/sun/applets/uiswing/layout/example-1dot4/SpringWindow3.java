/*
 * FlowLayout converted to be SpringLayout.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpringWindow3 extends JFrame {
     
    public SpringWindow3() {
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
        Spring maxHeightSpring = Spring.constant(0);

        // Make every component 5 pixels away from the component to its left.
        for (int i = 0; i < components.length; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(components[i]);

            cons.setX(xSpring);
            xSpring = Spring.sum(xPad, cons.getConstraint("East")); //use proxy instead?

            cons.setY(ySpring);
            maxHeightSpring = Spring.max(maxHeightSpring,
                                         cons.getConstraint("South"));
        }

        // Make the window's preferred size depend on its components.
        SpringLayout.Constraints pCons = layout.getConstraints(parent);

        // This doesn't work.  Why not?
        //pCons.setWidth(xSpring);
        //pCons.setHeight(Spring.sum(maxHeightSpring, ySpring));

        // Neither does this (just checking).
        // pCons.setConstraint("Width", xSpring);
        // pCons.setConstraint("Height", Spring.sum(maxHeightSpring, ySpring));

        // This works.  Why?
        pCons.setConstraint("East", xSpring);
        pCons.setConstraint("South", Spring.sum(maxHeightSpring, ySpring));

    }

    public static void main(String args[]) {
        SpringWindow3 window = new SpringWindow3();

        window.setTitle("SpringLayout");
        window.pack();
        window.setVisible(true);
    }
}

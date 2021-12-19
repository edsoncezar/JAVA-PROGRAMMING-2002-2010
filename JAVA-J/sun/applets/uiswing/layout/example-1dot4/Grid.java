/* XXX: Not exactly like GridLayout because everything's left aligned. */

import java.awt.*;
import javax.swing.*;

/**
 * An example of using SpringLayout to layout a grid. This behaves exactly
 * like <code>GridLayout</code>, with the exception that it only adjusts
 * the size to the min/pref. If given more/less space it doesn't adjust the
 * sizes.
 */
public class Grid {
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        SpringLayout layout = new SpringLayout();

        panel.setLayout(layout);
        for (int counter = 0; counter < 9; counter++) {
            JLabel label = new JLabel(Integer.toString(counter));

            if (counter == 4) {
                label.setFont(label.getFont().deriveFont(Font.PLAIN, 22f));
            }
            panel.add(label);
        }
        alignGrid(panel, 3, 3, 5, 5, 5, 5);

        // The preferred size is dictated by the east/south edges of
        // Constraint associated with the Container. Setting the
        // east/south constraint will effectively dicate what the preferred
        // size is. If you do not install constraints for the east/south
        // side, the preferred size will be 0x0.
        Component lastComponent = panel.getComponent(8);
        SpringLayout.Constraints pCons = layout.getConstraints(panel);
        pCons.setConstraint("South", layout.getConstraint("South",
                                                          lastComponent));
        pCons.setConstraint("East", layout.getConstraint("East",
                                                           lastComponent));

        JFrame frame = new JFrame("Test");
        frame.getContentPane().add(panel);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.show();
    }

    /**
     * <code>alignGrid</code> aligns the first <code>rows</code> *
     * <code>cols</code> <code>Components</code> of <code>parent</code> in
     * a grid. Each <code>Component</code> will be sized to the max
     * of the preferred width/height of the <code>Component</code>s.
     *
     * @param rows Number of rows
     * @param cols Number of columns
     * @param initialX X location to start the grid at
     * @param initialY Y location to start the grid at
     * @param xPad x padding between cells
     * @param yPad y padding between cells
     */
    private static void alignGrid(Container parent, int rows, int cols,
                                  int initialX, int initialY, int xPad,
                                  int yPad) {
        SpringLayout layout = (SpringLayout)parent.getLayout();
        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        // Calc Springs that are the max of the width/height so that all
        // cells have the same size.
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).
                                    getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).
                                    getWidth();
        for (int counter = 1; counter < max; counter++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(counter));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        // Apply the new width/height Spring. This will force all the
        // components to have the same size. Alternatively if we didn't
        // want all the Components to have the same size, but still use
        // the max to calc the grid, we could use the
        // maxWidthSpring/maxHeightSpring below instead of the 'East' or
        // 'South' constraint.
        for (int counter = 0; counter < max; counter++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(counter));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        // Then adjust the x/y constraints of all the cells so that they
        // are aligned in a grid.
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int counter = 0; counter < max; counter++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                                            parent.getComponent(counter));

            if (counter % cols > 0) {
                cons.setX(Spring.sum(lastCons.getConstraint("East"),
                                     xPadSpring));
            }
            else {
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            }
            if (counter / cols > 0) {
                cons.setY(Spring.sum(lastRowCons.getConstraint("South"),
                                     yPadSpring));
            }
            else {
                cons.setY(initialYSpring);
            }
            lastCons = cons;
        }
    }
}

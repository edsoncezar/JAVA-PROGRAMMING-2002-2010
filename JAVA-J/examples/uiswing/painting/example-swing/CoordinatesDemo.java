/*
 * Swing version.
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * This displays a framed area.  When the user clicks within
 * the area, this program displays a dot and a string indicating
 * the coordinates where the click occurred.
 */
public class CoordinatesDemo extends JApplet {
    JLabel label;

    //Called only when this is run as an applet.
    public void init() {
        buildUI(getContentPane());
    }

    void buildUI(Container container) {
        container.setLayout(new BoxLayout(container,
                                          BoxLayout.Y_AXIS));

        CoordinateArea coordinateArea = new CoordinateArea(this);
        container.add(coordinateArea);

        label = new JLabel("Click within the framed area.");
        container.add(label);

        //Align the left edges of the components.
        coordinateArea.setAlignmentX(LEFT_ALIGNMENT);
        label.setAlignmentX(LEFT_ALIGNMENT); //redundant
    }

    public void updateLabel(Point point) {
        label.setText("Click occurred at coordinate ("
                      + point.x + ", " + point.y + ").");
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("CoordinatesDemo");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        CoordinatesDemo controller = new CoordinatesDemo();
        controller.buildUI(f.getContentPane());
        f.pack();
        f.setVisible(true);
    }
}

class CoordinateArea extends JPanel {
    Point point = null;
    CoordinatesDemo controller;
    Dimension preferredSize = new Dimension(400,150);

    public CoordinateArea(CoordinatesDemo controller) {
        this.controller = controller;

        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder
                              (raisedBevel, loweredBevel);
        setBorder(compound);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (point == null) {
                    point = new Point(x, y);
                } else {
                    point.x = x;
                    point.y = y;
                }
                repaint();
            }
        });
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  //paint background

        //If user has chosen a point, paint a tiny rectangle on top.
        if (point != null) {
            controller.updateLabel(point);
            g.fillRect(point.x - 1, point.y - 1, 2, 2);
        }
    }
}

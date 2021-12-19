/*
 * 1.1 version.
 */

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

/* 
 * This displays a framed area.  When the user clicks within
 * the area, this program displays a dot and a string indicating
 * the coordinates where the click occurred.
 */

public class CoordinatesDemo extends Applet {
    FramedArea framedArea;
    Label label;

    public void init() {
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(gridBag);

        framedArea = new FramedArea(this);
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridBag.setConstraints(framedArea, c);
        add(framedArea);

        label = new Label("Click within the framed area.");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridBag.setConstraints(label, c);
        add(label);
    }

    public void updateLabel(Point point) {
        label.setText("Click occurred at coordinate ("
                      + point.x + ", " + point.y + ").");
    }
}

/* This class exists solely to put a frame around the coordinate area. */
class FramedArea extends Panel {
    public FramedArea(CoordinatesDemo controller) {
        super();

        //Set layout to one that makes its contents as big as possible.
        setLayout(new GridLayout(1,0));

        add(new CoordinateArea(controller));
    }

    public Insets getInsets() {
        return new Insets(4,4,5,5);
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        Color bg = getBackground();
 
        g.setColor(bg);
        g.draw3DRect(0, 0, d.width - 1, d.height - 1, true);
        g.draw3DRect(3, 3, d.width - 7, d.height - 7, false);
    }
}

class CoordinateArea extends Canvas {
    Point point = null;
    CoordinatesDemo controller;

    public CoordinateArea(CoordinatesDemo controller) {
        super();
        this.controller = controller;

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

    public void paint(Graphics g) {
        //If user has chosen a point, paint a tiny rectangle on top.
        if (point != null) {
            controller.updateLabel(point);
            g.fillRect(point.x - 1, point.y - 1, 2, 2);
        }
    }
}

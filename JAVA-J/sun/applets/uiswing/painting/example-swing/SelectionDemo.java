/*
 * 1.1 Swing version.
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * This displays a framed area.  When the user drags within
 * the area, this program displays a rectangle and a string
 * indicating the bounds of the rectangle.
 */
public class SelectionDemo extends JApplet {
    JLabel label;
    static String starFile = "images/starfield.gif";

    //Called only when this is run as an applet.
    public void init() {
        ImageIcon image = new ImageIcon(getImage(getCodeBase(),
                                                 starFile));
        buildUI(getContentPane(), image);
    }

    void buildUI(Container container, ImageIcon image) {
        container.setLayout(new BoxLayout(container,
                                          BoxLayout.Y_AXIS));

        JPanel framedArea = frameItUp(new SelectionArea(image, this));
        container.add(framedArea);

        label = new JLabel("Drag within the framed area.");
        container.add(label);

        //Align the left edges of the components.
        framedArea.setAlignmentX(LEFT_ALIGNMENT);
        label.setAlignmentX(LEFT_ALIGNMENT); //redundant
    }

    JPanel frameItUp(Component insides) {
        Border raisedBevel, loweredBevel, compound;
        raisedBevel = BorderFactory.createRaisedBevelBorder();
        loweredBevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder
                        (raisedBevel, loweredBevel);

        JPanel framedArea = new JPanel();
        framedArea.setBorder(compound);
        framedArea.setLayout(new GridLayout(1,0));
        framedArea.add(insides);

        return framedArea;
    }

    public void updateLabel(Rectangle rect) {
        int width = rect.width;
        int height = rect.height;

        //Make the coordinates look OK if a dimension is 0.
        if (width == 0) {
            width = 1;
        }
        if (height == 0) {
            height = 1;
        }

        label.setText("Rectangle goes from ("
                      + rect.x + ", " + rect.y + ") to ("
                      + (rect.x + width - 1) + ", "
                      + (rect.y + height - 1) + ").");
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("SelectionDemo");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        SelectionDemo controller = new SelectionDemo();
        controller.buildUI(f.getContentPane(),
                           new ImageIcon(starFile));
        f.pack();
        f.setVisible(true);
    }
}

class SelectionArea extends JLabel {
    Rectangle currentRect = null;
    Rectangle rectToDraw = null;
    Rectangle previousRectDrawn = new Rectangle();
    SelectionDemo controller;

    public SelectionArea(ImageIcon image, SelectionDemo controller) {
        super(image); //This component displays an image.
        this.controller = controller;
        setOpaque(true);
        setMinimumSize(new Dimension(10,10)); //don't hog space

        MyListener myListener = new MyListener();
        addMouseListener(myListener);
        addMouseMotionListener(myListener);
    }

    class MyListener extends MouseInputAdapter {
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            currentRect = new Rectangle(x, y, 0, 0);
            updateDrawableRect(getWidth(), getHeight());
            repaint();
        }

        public void mouseDragged(MouseEvent e) {
            updateSize(e);
        }

        public void mouseReleased(MouseEvent e) {
            updateSize(e);
        }

        /* 
         * Update the size of the current rectangle
         * and call repaint.  Because currentRect
         * always has the same origin, translate it
         * if the width or height is negative.
         * 
         * For efficiency (though
         * that isn't an issue for this program),
         * specify the painting region using arguments
         * to the repaint() call.
         * 
         */
        void updateSize(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            currentRect.setSize(x - currentRect.x,
                                y - currentRect.y);
            updateDrawableRect(getWidth(), getHeight());
            Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
            repaint(totalRepaint.x, totalRepaint.y,
                    totalRepaint.width, totalRepaint.height);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //paints the background and image

        //If currentRect exists, paint a box on top.
        if (currentRect != null) {
            //Draw a rectangle on top of the image.
// XXX: We used to use XOR mode, but in 1.2, due to bugs 4188795
// XXX: and 4219548, that causes an off-by-one error.
            //g.setXORMode(Color.white); //Color of line varies
                                         //depending on image colors
	    g.setColor(Color.white);
            g.drawRect(rectToDraw.x, rectToDraw.y, 
                       rectToDraw.width - 1, rectToDraw.height - 1);

            controller.updateLabel(rectToDraw);
        }
    }

    void updateDrawableRect(int compWidth, int compHeight) {
        int x = currentRect.x;
        int y = currentRect.y;
        int width = currentRect.width;
        int height = currentRect.height;

        //Make the width and height positive, if necessary.
        if (width < 0) {
            width = 0 - width;
            x = x - width + 1; 
            if (x < 0) {
                width += x; 
                x = 0;
            }
        }
        if (height < 0) {
            height = 0 - height;
            y = y - height + 1; 
            if (y < 0) {
                height += y; 
                y = 0;
            }
        }

        //The rectangle shouldn't extend past the drawing area.
        if ((x + width) > compWidth) {
            width = compWidth - x;
        }
        if ((y + height) > compHeight) {
            height = compHeight - y;
        }
      
        //Update rectToDraw after saving old value.
        if (rectToDraw != null) {
            previousRectDrawn.setBounds(
                        rectToDraw.x, rectToDraw.y, 
                        rectToDraw.width, rectToDraw.height);
            rectToDraw.setBounds(x, y, width, height);
        } else {
            rectToDraw = new Rectangle(x, y, width, height);
        }
    }
}

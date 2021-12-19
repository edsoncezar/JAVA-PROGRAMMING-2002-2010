/*
 * Swing version.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* 
 * This is like the ShapesDemo applet, except that it 
 * handles fonts more carefully.
 */
public class FontDemo extends JApplet {
    /** Executed only in applet. */
    public void init() {
        FontPanel fontPanel = new FontPanel();
        getContentPane().add(fontPanel, BorderLayout.CENTER);
    }

    /** Executed only in application.*/
    public static void main(String[] args) {
        JFrame f = new JFrame("FontDemo");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        FontPanel fontPanel = new FontPanel();
        f.getContentPane().add(fontPanel, BorderLayout.CENTER);
        f.setSize(550, 200);
        f.setVisible(true);
    }
}

class FontPanel extends JPanel {
    final int maxCharHeight = 17;
    final int minFontSize = 6;
    final int maxFontSize = 14;
    final Color bg = Color.lightGray;
    final Color fg = Color.black;

    Dimension totalSize;
    FontMetrics fontMetrics;
    Font biggestFont = new Font("SansSerif", Font.PLAIN, maxFontSize);

    public FontPanel() {
        //Initialize drawing colors, border, opacity.
        setBackground(bg);
        setForeground(fg);
        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createRaisedBevelBorder(),
                        BorderFactory.createLoweredBevelBorder()));
    }

    FontMetrics pickFont(Graphics g,
                         String longString,
                         int xSpace) {
        boolean fontFits = false;
        Font currentFont = biggestFont;
        FontMetrics currentMetrics = getFontMetrics(currentFont);
        int size = currentFont.getSize();
        String name = currentFont.getName();
        int style = currentFont.getStyle();

        while (!fontFits) {
            if ( (currentMetrics.getHeight() <= maxCharHeight)
              && (currentMetrics.stringWidth(longString) 
                  <= xSpace)) {
                fontFits = true;
            } else {
                if (size <= minFontSize) {
                    fontFits = true;
                } else {
                    currentFont = new Font(name, style, --size);
                    currentMetrics = getFontMetrics(currentFont);
                }
            }
        }

        //Must set font in both component and Graphics object.
        g.setFont(currentFont);
        setFont(currentFont); 
        return currentMetrics;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);      //clears the background

        Dimension d = getSize();
        Insets insets = getInsets();
        int currentWidth = d.width - insets.left - insets.right;
        int currentHeight = d.height - insets.top - insets.bottom;
        int gridWidth = currentWidth / 7;
        int gridHeight = currentHeight / 2;

        if ( (totalSize == null)
          || (totalSize.width != currentWidth)
          || (totalSize.height != currentHeight) ) {
            totalSize = new Dimension(currentWidth, currentHeight);
            fontMetrics = pickFont(g, "drawRoundRect",
                                   gridWidth);
        }

        Color fg3D = Color.lightGray;
        int firstX = insets.left + 3;
        int firstY = insets.top + 3;
        int x = firstX;
        int y = firstY;
        int rectWidth = gridWidth - 2*x;
        int stringY = gridHeight - 5 - 
                      fontMetrics.getDescent();
        int rectHeight = stringY - fontMetrics.getMaxAscent()
                         - y - 2;

        // drawLine(x1, y1, x2, y2) 
        g.drawLine(x, y+rectHeight-1, x + rectWidth, y);
        g.drawString("drawLine", x, stringY);
        x += gridWidth;

        // drawRect(x, y, w, h) 
        g.drawRect(x, y, rectWidth, rectHeight);
        g.drawString("drawRect", x, stringY);
        x += gridWidth;

        // draw3DRect(x, y, w, h, raised) 
        g.setColor(fg3D);
        g.draw3DRect(x, y, rectWidth, rectHeight, true);
        g.setColor(fg);
        g.drawString("draw3DRect", x, stringY);
        x += gridWidth;

        // drawRoundRect(x, y, w, h, arcw, arch) 
        g.drawRoundRect(x, y, rectWidth, rectHeight, 10, 10);
        g.drawString("drawRoundRect", x, stringY);
        x += gridWidth;

        // drawOval(x, y, w, h) 
        g.drawOval(x, y, rectWidth, rectHeight);
        g.drawString("drawOval", x, stringY);
        x += gridWidth;

        // drawArc(x, y, w, h) 
        g.drawArc(x, y, rectWidth, rectHeight, 90, 135);
        g.drawString("drawArc", x, stringY);
        x += gridWidth;

        // drawPolygon(xPoints, yPoints, numPoints) 
        int x1Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y1Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.drawPolygon(x1Points, y1Points, x1Points.length); 
        g.drawString("drawPolygon", x, stringY);

        // NEW ROW
        x = firstX;
        y += gridHeight;
        stringY += gridHeight;

        // drawPolyline(xPoints, yPoints, numPoints) 
        // Note: drawPolygon would close the polygon.
        int x2Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y2Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.drawPolyline(x2Points, y2Points, x2Points.length); 
        g.drawString("drawPolyline", x, stringY);
        x += gridWidth;

        // fillRect(x, y, w, h)
        g.fillRect(x, y, rectWidth, rectHeight);
        g.drawString("fillRect", x, stringY);
        x += gridWidth;

        // fill3DRect(x, y, w, h, raised) 
        g.setColor(fg3D);
        g.fill3DRect(x, y, rectWidth, rectHeight, true);
        g.setColor(fg);
        g.drawString("fill3DRect", x, stringY);
        x += gridWidth;

        // fillRoundRect(x, y, w, h, arcw, arch)
        g.fillRoundRect(x, y, rectWidth, rectHeight, 10, 10);
        g.drawString("fillRoundRect", x, stringY);
        x += gridWidth;

        // fillOval(x, y, w, h)
        g.fillOval(x, y, rectWidth, rectHeight);
        g.drawString("fillOval", x, stringY);
        x += gridWidth;

        // fillArc(x, y, w, h)
        g.fillArc(x, y, rectWidth, rectHeight, 90, 135);
        g.drawString("fillArc", x, stringY);
        x += gridWidth;

        // fillPolygon(xPoints, yPoints, numPoints) 
        int x3Points[] = {x, x+rectWidth, x, x+rectWidth};
        int y3Points[] = {y, y+rectHeight, y+rectHeight, y};
        g.fillPolygon(x3Points, y3Points, x3Points.length); 
        g.drawString("fillPolygon", x, stringY);
    }
}

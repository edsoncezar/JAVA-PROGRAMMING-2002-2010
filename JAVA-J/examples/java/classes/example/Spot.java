import java.applet.Applet;
import java.awt.*;

public class Spot extends Applet {
    private java.awt.Point clickPoint = null;
    private static final int RADIUS = 7;

    public void paint(Graphics g) {
	g.drawRect(0, 0, size().width - 1,
			 size().height - 1);
	if (clickPoint != null)
	    g.fillOval(clickPoint.x - RADIUS,
		       clickPoint.y - RADIUS,
		       RADIUS * 2, RADIUS * 2);
    }

    public boolean mouseDown(java.awt.Event evt, int x, int y) {
	if (clickPoint == null)
	    clickPoint = new java.awt.Point(x, y);
	else {
	    clickPoint.x = x;
	    clickPoint.y = y;
	}
        repaint();
        return true;
    }
}

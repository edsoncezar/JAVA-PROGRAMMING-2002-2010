import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class AnonymousSpot extends Applet {
    private java.awt.Point clickPoint = null;
    private static final int RADIUS = 7;

    public void init() {
	addMouseListener(
	    new MouseAdapter() {
		public void mousePressed(MouseEvent event) {
		    clickPoint = event.getPoint();
		    repaint();
		}
	    }
	);
    }
    public void paint(Graphics g) {
        g.drawRect(0, 0, getSize().width - 1,
			 getSize().height - 1);
        if (clickPoint != null)
	    g.fillOval(clickPoint.x-RADIUS,
		       clickPoint.y-RADIUS,
		       RADIUS*2, RADIUS*2);
    }
}

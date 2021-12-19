import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Spot extends Applet implements MouseListener {
    private java.awt.Point clickPoint = null;
    private static final int RADIUS = 7;

    public void init() {
	addMouseListener(this);
    }
    public void paint(Graphics g) {
	g.drawRect(0, 0, getSize().width - 1,
			 getSize().height - 1);
	if (clickPoint != null)
	    g.fillOval(clickPoint.x - RADIUS,
		       clickPoint.y - RADIUS,
		       RADIUS * 2, RADIUS * 2);
    }
    public void mousePressed(MouseEvent event) {	
	clickPoint = event.getPoint();
	repaint();
    }
    public void mouseClicked(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
}

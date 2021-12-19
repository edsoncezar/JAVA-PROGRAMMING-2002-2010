import java.awt.Graphics;
import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TimingIsEverything extends java.applet.Applet {

    public long firstClickTime = 0;
    public String displayStr;

    public void init() {
        displayStr = "Double Click Me";
        addMouseListener(new MyAdapter());
    }
    public void paint(Graphics g) {
        g.drawRect(0, 0, getSize().width-1, getSize().height-1);
        g.drawString(displayStr, 40, 30);
    }
    class MyAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent evt) {
            long clickTime = System.currentTimeMillis();
            long clickInterval = clickTime - firstClickTime;
            if (clickInterval < 300) {
                displayStr = "Double Click!! (Interval = "
			     + clickInterval + ")";
                firstClickTime = 0;
            } else {
                displayStr = "Single Click!!";
                firstClickTime = clickTime;
            }
            repaint();
	}
    }
}

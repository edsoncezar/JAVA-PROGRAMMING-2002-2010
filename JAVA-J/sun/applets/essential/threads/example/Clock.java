import java.awt.Graphics;
import java.awt.Color;
import java.util.Date;

public class Clock extends java.applet.Applet implements Runnable {
    private Thread clockThread = null;

    public void init() {
        setBackground(Color.white);
    }
    public void start() {
        if (clockThread == null) {
            clockThread = new Thread(this, "Clock");
            clockThread.start();
        }
    }
    public void run() {
	Thread myThread = Thread.currentThread();
        while (clockThread == myThread) {
            repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){ }
        }
    }
    public void paint(Graphics g) {
        Date now = new Date();
        g.drawString(now.getHours() + ":" + 
		     now.getMinutes() + ":" + 
		     now.getSeconds(), 5, 10);
    }
    public void stop() {
        clockThread = null;
    }
}

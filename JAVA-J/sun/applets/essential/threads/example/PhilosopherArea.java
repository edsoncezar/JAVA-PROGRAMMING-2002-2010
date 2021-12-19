import java.awt.*;

public class PhilosopherArea extends Canvas {
    PhilAnimator controller;
    Image[] imgs = new Image[3];
    Chopstick[] chopsticks = new Chopstick[NUMPHILS];
    String[] names = { "Arisduktle" ,
		       "Dukrates",
		       "Pythagorduke",
		       "Duko",
		       "Dukimedes" };
    static final int NUMPHILS = 5;

    private static final double MARGIN = 10.0f;
    private double spacing;
    private Philosopher[] philosophers = new Philosopher[NUMPHILS];
    private boolean[] redraw = new boolean[NUMPHILS];

    private boolean running = false;

    public PhilosopherArea(PhilAnimator controller) {
        super();
        this.controller = controller;

        MediaTracker mt;
        mt = new MediaTracker(this);

        imgs[0] = controller.getImage(controller.getCodeBase(),
				      "hungryduke.gif");
        mt.addImage(imgs[0], 0);
        imgs[1] = controller.getImage(controller.getCodeBase(),
				      "rightspoonduke.gif");
        mt.addImage(imgs[1], 1);
        imgs[2] = controller.getImage(controller.getCodeBase(),
				      "bothspoonsduke.gif");
        mt.addImage(imgs[2], 2);

        try {
            mt.waitForID(0);
            mt.waitForID(1);
            mt.waitForID(2);
        } catch (java.lang.InterruptedException e) {
            System.out.println("Couldn't load one of the images");
        }

        spacing = imgs[0].getWidth(this) + MARGIN;
        createPhilosophersAndChopsticks();
    }

    public void paint(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, size().width, size().height);
        for (int i = 0; i < NUMPHILS; i++) {
            redraw[i] = true;
        }
        update(g);
    }

    public void update(Graphics g) {
        for (int i = 0; i < NUMPHILS; i++) {
            if (redraw[i]) {
                philosophers[i].paint(g);
                redraw[i] = false;
            }
        }
    }

    public boolean mouseDown(java.awt.Event evt, int x, int y) {
        if (running) {
            for (int i = 0; i < NUMPHILS; i++)
                philosophers[i].suspend();
        } else {
            for (int i = 0; i < NUMPHILS; i++)
                philosophers[i].resume();
        }

        running = !running;
        return true;
    }

    public synchronized void repaintPhil(int id) {
        redraw[id] = true;
        repaint();
    }

    public void startPhilosophers() {
        for (int i = 0; i < NUMPHILS; i++)
            philosophers[i].start();
        running = true;
    }

    public void stopPhilosophers() {
        for (int i = 0; i < NUMPHILS; i++)
            philosophers[i].stopRequested();
    }

    public void createPhilosophersAndChopsticks() {
        double x, y;
        double radius = 80.0;
        double centerAdj = 85.0;
        double radians;

/* for a straight line
        y = MARGIN;
*/
        for (int i = 0; i < NUMPHILS; i++)
            chopsticks[i] = new Chopstick();

        for (int i = 0; i < NUMPHILS; i++) {
/* for a straight line
            x = i * spacing;
*/
            radians = i*(2.0 * Math.PI /(double)NUMPHILS);
            x = Math.sin(radians) * radius + centerAdj; 
            y = Math.cos(radians) * radius + centerAdj; 
            philosophers[i] = new Philosopher(this, x, y, i);
            repaintPhil(i);   
        }
    }
}

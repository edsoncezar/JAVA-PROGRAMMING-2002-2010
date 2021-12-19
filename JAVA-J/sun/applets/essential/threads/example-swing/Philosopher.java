/* 
 * This class requires no changes from the 1.0 version. 
 * It's kept here so the rest of the example can compile.
 */
import java.util.Random;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Philosopher extends JLabel implements Runnable {
    private Chopstick leftStick, rightStick;
    private boolean sated;
    private DiningPhilosophers parent;
    private int position;
    Thread philThread = null;

    public Philosopher(DiningPhilosophers parent, int position, ImageIcon img) {
        super(parent.names[position], img, JLabel.CENTER);

        this.parent = parent;
        this.position = position;

        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);

        // identify the chopsticks to my right and left
        this.rightStick = parent.chopsticks[position];
        if (position == 0) {
            this.leftStick = parent.chopsticks[parent.NUMPHILS-1];
        } else {
            this.leftStick = parent.chopsticks[position-1];
        }

        // I'm hungry
        this.sated = false;
        philThread = new Thread(this);
    }

    public void run() {
        try {
            while (true) {
	        Thread.sleep((int)(Math.random() * parent.grabDelay));
                setText("     ");
                rightStick.grab();
                setIcon(parent.imgs[parent.RIGHTSPOONDUKE]);

		Thread.sleep((int)(Math.random() * parent.grabDelay));
                leftStick.grab();
                setIcon(parent.imgs[parent.BOTHSPOONSDUKE]);

		Thread.sleep((int)(Math.random() * parent.grabDelay));
                rightStick.release();
                leftStick.release();
                setIcon(parent.imgs[parent.HUNGRYDUKE]);
                setText("Mmmm!");
                sated = true;

		Thread.sleep((int)(Math.random() * parent.grabDelay * 4));
                sated = false;
            }
        } catch (java.lang.InterruptedException e) {
        }
        leftStick.releaseIfMine();
        rightStick.releaseIfMine();
        setIcon(parent.imgs[parent.HUNGRYDUKE]);
	setText(parent.names[position]);
        sated = false;
        philThread = new Thread(this);
    }
}

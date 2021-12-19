import java.awt.*;

public class Chopstick {
    Philosopher owner = null;

    public synchronized void release(Philosopher phil) {
        if (phil == owner)
            owner = null;
        notify();
    }

    public synchronized void grab(Philosopher phil)
					throws InterruptedException {
        while (owner != null)
            wait();
        owner = phil;
    }
}

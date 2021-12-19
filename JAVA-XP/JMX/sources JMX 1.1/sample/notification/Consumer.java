package sample.notification;

import sample.standard.WorkUnit;

/**
 * This class pulls WorkUnit instances from a Queue
 * and performs a certain amount of "work", specified
 * by workFactor.
 */
public class Consumer extends Worker {

    public static final String ROLE = "Consumer";
    private static final int DEFAULT_WORK_FACTOR = 100;

    public Consumer (Queue queue) {
        this(queue, DEFAULT_WORK_FACTOR);
    }

    public Consumer (Queue inputQueue, int workFactor) {
        super(inputQueue, workFactor);
    }

    public void run () {
        _queue.addConsumer();
        //**********
        // This is where the "work" takes place. In a real-world
        /// application that uses this pattern, this logic would
        /// be replaced by the real application logic.
        //**********
        while (!(_stopCalled || _queue.isEndOfInput())) {
            while (_suspended) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
            WorkUnit unit = (WorkUnit)_queue.remove();
            // Burn some cycles...
            calculatePrimes(_workFactor);
            _numberOfUnitsProcessed++;
        }
        _queue.removeConsumer();
    }
}

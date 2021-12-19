/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.openmbean;


/**
 * This class pulls WorkUnit instances from a Queue
 * and performs a certain amount of "work", specified
 * by workFactor.
 */
public class Consumer extends Worker {
    public static final String ROLE = "Consumer";
    private static final int DEFAULT_WORK_FACTOR = 100;

    /**
     * put your documentation comment here
     * @param   Queue queue
     */
    public Consumer (Queue queue) {
        this(queue, DEFAULT_WORK_FACTOR);
    }

    /**
     * put your documentation comment here
     * @param   Queue inputQueue
     * @param   int workFactor
     */
    public Consumer (Queue inputQueue, int workFactor) {
        super(inputQueue, workFactor);
    }

    /**
     * put your documentation comment here
     */
    public void run () {
        //**********
        // This is where the "work" takes place. In a real-world
        /// application that uses this pattern, this logic would
        /// be replaced by the real application logic.
        //**********
        _queue.addConsumer();
        while (!_stopCalled) {
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




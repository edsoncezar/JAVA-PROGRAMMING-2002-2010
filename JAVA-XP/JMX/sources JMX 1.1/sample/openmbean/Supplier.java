/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.openmbean;


/**
 * put your documentation comment here
 */
public class Supplier extends Worker {
    public static final String ROLE = "Supplier";
    private static final int DEFAULT_WORK_FACTOR = 100;

    /**
     * put your documentation comment here
     * @param   Queue queue
     */
    public Supplier (Queue queue) {
        this(queue, DEFAULT_WORK_FACTOR);
    }

    /**
     * put your documentation comment here
     * @param   Queue queue
     * @param   int workFactor
     */
    public Supplier (Queue queue, int workFactor) {
        super(queue, workFactor);
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
        _queue.addSupplier();
        while (!_stopCalled) {
            while (_suspended) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
            // Burn some cycles...
            calculatePrimes(_workFactor);
            // Now place a WorkUnit in the Queue
            _queue.add(new WorkUnit());
            _numberOfUnitsProcessed++;
        }
        _queue.removeSupplier();
    }
}




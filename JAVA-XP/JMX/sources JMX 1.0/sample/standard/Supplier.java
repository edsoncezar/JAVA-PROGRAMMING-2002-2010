package  sample.standard;


/**
 * Represents the supplier component of the
 * sample application. Does some work, and
 * places the results into a queue.
 * Implemented as a standard MBean.
 */
public class Supplier extends Worker {
    public static final String ROLE = "Supplier";
    private static final int DEFAULT_WORK_FACTOR = 100;

    public Supplier (Queue queue) {
        this(queue, DEFAULT_WORK_FACTOR);
    }
    public Supplier (Queue queue, int workFactor) {
        super(queue, workFactor);
    }

    public void run () {
        _queue.addSupplier();
        //**********
        // This is where the "work" takes place. In a real-world
        /// application that uses this pattern, this logic would
        /// be replaced by the real application logic.
        //**********
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




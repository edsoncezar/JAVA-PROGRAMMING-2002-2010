package sample.notification;

import sample.standard.Basic;

/**
 * Base class for the workers in the sample application.
 */
public abstract class Worker extends Basic implements WorkerMBean, Runnable {
    
    public static final String OBJECT_NAME = "UserDomain:name=Worker";
    protected Queue _queue;

    protected long _totalProcessingTime;
    protected boolean _stopCalled;

    public Worker (Queue queue, int workFactor) {
        _queue = queue;
        _workFactor = workFactor;
    }

    public float getAverageUnitProcessingTime () {
        return  (_numberOfUnitsProcessed > 0) ? (float)_totalProcessingTime/(float)_numberOfUnitsProcessed :
                0.0f;
    }

    public abstract void run ();

    protected int _workFactor;
    public int getWorkFactor () {
        return  _workFactor;
    }
    public void setWorkFactor (int workFactor) {
        _workFactor = workFactor;
    }

    protected long _numberOfUnitsProcessed;
    public long getNumberOfUnitsProcessed () {
        return  _numberOfUnitsProcessed;
    }
    public void setNumberOfUnitsProcessed (long value) {
        _numberOfUnitsProcessed = value;
    }

    protected boolean _suspended;
    public boolean isSuspended () {
        return  _suspended;
    }

    public synchronized void stop () {
        _stopCalled = true;
    }

    public synchronized void suspend () {
        _suspended = true;
    }

    public synchronized void resume () {
        _suspended = false;
        notifyAll();
    }

    public void reset () {
        _numberOfUnitsProcessed = 0;
        _totalProcessingTime = 0;
    }

    /**
     * In this method is where the "work" takes place. We need
     * a way to simulate the application doing something, so we
     * calculate the first workFactor primes, starting with zero.
     * So, the work factor is equal to the number of primes that
     * are calculated.
     */
    void calculatePrimes (int numberOfPrimesToCalculate) {
        // There is probably an easier way to do this,
        /// but it seemed a good way to chew up some CPU
        long startTime = System.currentTimeMillis();
        long number = 1;
        int numberOfPrimesCalculated = 0;
        while (numberOfPrimesCalculated < numberOfPrimesToCalculate) {
            long currentNumber = 1;             // start with 1
            long numberOfFactors = 0;
            while (currentNumber <= number) {
                if ((number%currentNumber) == 0) {
                    // This is *definitely* the long way around, but
                    /// remember, we *want* to eat up lots of CPU...
                    numberOfFactors++;
                }
                currentNumber++;
            }
            // The number is prime if it only has two factors
            /// (i.e., itself and 1)
            if (numberOfFactors == 2) {
                numberOfPrimesCalculated++;
                //                System.out.println("Supplier.calculatePrimes(): INFO: " +
                //                    "Prime number found - " + number);
            }
            number++;
        }
        _totalProcessingTime += (System.currentTimeMillis() - startTime);
    }
}




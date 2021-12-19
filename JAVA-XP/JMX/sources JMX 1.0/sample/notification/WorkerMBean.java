package sample.notification;

import sample.standard.BasicMBean;

/**
 * Management interface definition for Worker as
 * a standard MBean. A worker represents a thread of
 * execution in the application that is performing some
 * work.
 *
 * There are four attributes:
 *
 * 1. WorkFactor (int) - Read/Write
 * 2. NumberOfUnitsProcessed (long) - READ-ONLY
 * 3. AverageUnitProcessingTime (float) - READ-ONLY
 * 4. Suspended (boolean) - READ-ONLY
 *
 * and three operations:
 *
 * 1. stop() - shuts down the worker thread
 * 2. suspend() - suspends processing (i.e., the "work")
 *    of the worker thread.
 * 3. resume() - resumes processing of the worker thread.
 */
public interface WorkerMBean extends BasicMBean
{
    // Attributes
    public int getWorkFactor ();
    public void setWorkFactor (int workFactor);
    public long getNumberOfUnitsProcessed ();
    public float getAverageUnitProcessingTime ();
    public boolean isSuspended ();
    // Operations
    public void stop ();
    public void suspend ();
    public void resume ();
}




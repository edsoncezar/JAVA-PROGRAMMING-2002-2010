package  sample.notification;


import sample.standard.BasicMBean;
/**
 * Defines the management interface for Queue
 * using the standard MBean design patterns.
 * There are ten attributes:
 *
 * 1. QueueSize (int) - Read/Write
 * 2. NumberOfItemsProcessed (long) - READ-ONLY
 * 3. AddWaitTime (long) - READ-ONLY
 * 4. RemoveWaitTime (long) - READ-ONLY
 * 5. QueueFull (boolean) - READ-ONLY
 * 6. QueueEmpty (boolean) - READ-ONLY
 * 7. Suspended (boolean) - READ-ONLY
 * 8. EndOfInput (boolean) - READ-ONLY
 * 9. NumberOfSuppliers (boolean) - READ-ONLY
 * 10. NumberOfConsumers (boolean) - READ-ONLY
 *
 * and three operations:
 *
 * 1. suspend() - suspends the activity in the queue. Allows
 *    no items to be removed from, or added to, the queue.
 * 2. resume() - resumes processing in the queue.
 * 3. reset() - resets the state of the queue. This method is
 *    inherited from BasicMBean.
 */
public interface QueueMBean extends BasicMBean
{
    // Attributes
    public void setQueueSize (int queueSize);
    public int getQueueSize ();
    public long getNumberOfItemsProcessed ();
    public long getAddWaitTime ();
    public long getRemoveWaitTime ();
    public boolean isQueueFull ();
    public boolean isQueueEmpty ();
    public boolean isSuspended ();
    public boolean isEndOfInput ();
    public int getNumberOfSuppliers ();
    public int getNumberOfConsumers ();

    // Operations
    public void suspend ();
    public void resume ();
}




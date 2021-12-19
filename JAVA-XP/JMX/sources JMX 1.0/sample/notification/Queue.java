package  sample.notification;

import sample.standard.*;
import  javax.management.*;
import  java.util.ArrayList;


/**
 * A very simple queue.
 * Implemented as a standard MBean
 */
public class Queue extends Basic
        implements QueueMBean, NotificationBroadcaster, MBeanRegistration {
    private static final int DEFAULT_QUEUE_SIZE = 10;
    // Queue guts
    private int _head;
    private int _tail;
    private Object[] _backingStore;
    private boolean _suspended;
    private boolean _queueFull;
    private boolean _queueEmpty;
    private ArrayList _suppliers = new ArrayList();
    private ArrayList _consumers = new ArrayList();
    private boolean _endOfInput;
    // Metrics
    private long _addWaitTime;
    private long _removeWaitTime;
    private long _numberOfItemsProcessed;
    private GenericBroadcaster _broadcaster = new GenericBroadcaster();
    private StalledQueueWatcher _queueWatcher = new StalledQueueWatcher();
    private static final String STALLED_QUEUE_FULL = "sample.Queue.stalled.queueFull";
    private static final String STALLED_QUEUE_EMPTY = "sample.Queue.stalled.queueEmpty";

    //
    // Implement NotificationBroadcaster interface via containment
    //
    /**
     * Adds a listener to a registered MBean.
     *
     * @param listener The listener object which will handle the notifications emitted by the registered MBean.
     * @param filter The filter object. If filter is null, no filtering will be performed before handling notifications.
     * @param handback An opaque object to be sent back to the listener when a notification is emitted. This object
     * cannot be used by the Notification broadcaster object. It should be resent unchanged with the notification
     * to the listener.
     *
     * @exception IllegalArgumentException Listener parameter is null.
     */
    public void addNotificationListener (NotificationListener listener, NotificationFilter filter, 
            Object handback) throws java.lang.IllegalArgumentException {
        _broadcaster.addNotificationListener(listener, filter, handback);
    }

    /**
     * Removes a listener from a registered MBean.
     *
     * @param name The name of the MBean on which the listener should be removed.
     * @param listener The listener object which will handle the notifications emitted by the registered MBean.
     * This method will remove all the information related to this listener.
     *
     * @exception ListenerNotFoundException The listener is not registered in the MBean.
     */
    public void removeNotificationListener (NotificationListener listener) throws ListenerNotFoundException {
        _broadcaster.removeNotificationListener(listener);
    }

    /**
     * Returns an MBeanNotificationInfo object contaning the name of the Java 
     * class of the notification and the notification types sent.  
     */
    public MBeanNotificationInfo[] getNotificationInfo () {
        return  _broadcaster.getNotificationInfo();
    }

    public Queue () {
        this(DEFAULT_QUEUE_SIZE);
    }
    public Queue (int queueSize) {
        // Create the backing store. If the specified queue size is
        /// bogus, use the default queue size
        _backingStore = new Object[(queueSize > 0) ? queueSize : DEFAULT_QUEUE_SIZE];
        _queueEmpty = true;
        //
        // Create a timer to monitor potential queue
        /// stalled conditions.
        //
        (new Thread(_queueWatcher)).start();
    }

    public synchronized void addSupplier () {
        String supplierName = Thread.currentThread().getName();
        trace("Queue.addSupplier(): INFO: " + "Adding supplier \'" + supplierName
                + "\'.");
        _suppliers.add(supplierName);
    }

    public synchronized void removeSupplier () {
        String supplier = Thread.currentThread().getName();
        trace("Queue.removeSupplier(): INFO: " + "Looking for supplier \'" + 
                supplier + "\' to remove it.");
        for (int aa = 0; aa < _suppliers.size(); aa++) {
            String s = (String)_suppliers.get(aa);
            if (supplier.equals(s)) {
                trace("Queue.removeSupplier(): INFO: " + "Removing supplier \'"
                        + s + "\'.");
                _suppliers.remove(aa);
                break;
            }
        }
        if (_suppliers.size() == 0)
            _endOfInput = true;
        notifyAll();
    }

    public synchronized void addConsumer () {
        String consumerName = Thread.currentThread().getName();
        trace("Queue.addConsumer(): INFO: " + "Adding consumer \'" + consumerName
                + "\'.");
        _consumers.add(consumerName);
    }

    public synchronized void removeConsumer () {
        String consumer = Thread.currentThread().getName();
        trace("Queue.removeConsumer(): INFO: " + "Looking for consumer \'" + 
                consumer + "\' to remove it.");
        for (int aa = 0; aa < _consumers.size(); aa++) {
            String s = (String)_consumers.get(aa);
            if (consumer.equals(s)) {
                trace("Queue.removeConsumer(): INFO: " + "Removing consumer \'"
                        + s + "\'.");
                _consumers.remove(aa);
                break;
            }
        }
        notifyAll();
    }

    public synchronized void add (Object item) {
        long addWaitTime = 0;
        while (_suspended || _tail == -1) {
            long waitStart = System.currentTimeMillis();
            try {
                wait();
            } catch (InterruptedException e) {}
            addWaitTime += (System.currentTimeMillis() - waitStart);
        }
        _addWaitTime += addWaitTime;
        // add the item to the queue's backing store
        _backingStore[_tail] = item;
        _queueWatcher.reset();
        _queueEmpty = false;
        _tail++;
        if (_tail >= _backingStore.length)
            _tail = 0;          // wrap
        if (_tail == _head) {
            _tail = -1;         // special case, we're full
        }
        _queueFull = (_tail == -1) ? true : false;
        //    if (_queueFull)
        //      trace("Queue is now full.");
        notifyAll();
    }

    public synchronized Object remove () {
        Object ret = null;
        long removeWaitTime = 0;
        if (!(_queueEmpty && _endOfInput)) {
            while (_suspended || _queueEmpty) {
                long waitStart = System.currentTimeMillis();
                try {
                    wait();
                } catch (InterruptedException e) {}
                if (_endOfInput) {
                    break;
                }
                removeWaitTime += System.currentTimeMillis() - waitStart;
            }
            if (!_queueEmpty) {
                _removeWaitTime += removeWaitTime;
                ret = _backingStore[_head];
                _queueWatcher.reset();
                _queueFull = false;
                _numberOfItemsProcessed++;
                _backingStore[_head] = null;
                if (_tail == -1)
                    _tail = _head;              // point tail to new free spot
                _head++;
                if (_head >= _backingStore.length)
                    _head = 0;                  // wrap head pointer
                _queueEmpty = (_head == _tail) ? true : false;
                //        if (_queueEmpty)
                //          trace("Queue is now empty.");
            }
        }
        notifyAll();
        return  ret;
    }
    
    //
    // MBeanRegistration Interface implementation
    //
    public ObjectName preRegister (MBeanServer server, ObjectName name) throws java.lang.Exception {
        ObjectName ret = name;
        if (name == null)
            ret = new ObjectName(server.getDefaultDomain() + ":" + "name=Queue,selfRegister=true");
        trace("Queue.preRegister(): INFO: Invocation complete");
        return  ret;
    }


    public void postRegister (Boolean registrationDone) {
        trace("Queue.postRegister(): INFO: Invocation complete");
    }

    public void preDeregister () throws java.lang.Exception {
        trace("Queue.preDeregister(): INFO: Invocation complete");
    }

    public void postDeregister () {
        trace("Queue.postDeregister(): INFO: Invocation complete");
    }


    public int getQueueSize () {
        return  _backingStore.length;
    }
    public synchronized void setQueueSize (int queueSize) {
        // Can't set queue size on a suspended queue
        if (!_suspended) {
            // Only allow the queue to grow, not shrink...
            if (queueSize > _backingStore.length) {
                // allocate new array
                Object[] newStore = new Object[queueSize];
                System.arraycopy(_backingStore, 0, newStore, 0, _backingStore.length);
            }
        }
        notifyAll();
    }

    public long getNumberOfItemsProcessed () {
        return  _numberOfItemsProcessed;
    }

    public void setNumberOfItemsProcessed (long value) {
        _numberOfItemsProcessed = value;
    }

    public long getAddWaitTime () {
        return  _addWaitTime;
    }

    public void setAddWaitTime (long value) {
        _addWaitTime = value;
    }

    public long getRemoveWaitTime () {
        return  _removeWaitTime;
    }

    public void setRemoveWaitTime (long value) {
        _removeWaitTime = value;
    }

    public Object[] getBackingStore () {
        return  _backingStore;
    }

    public boolean isQueueFull () {
        //    trace("Queue.isQueueFull(): INFO: returning " + _queueFull);
        return  _queueFull;
    }

    public boolean isQueueEmpty () {
        //    trace("Queue.isQueueEmpty(): INFO: returning " + _queueEmpty);
        return  _queueEmpty;
    }

    public boolean isSuspended () {
        return  _suspended;
    }

    public boolean isEndOfInput () {
        return  _endOfInput;
    }

    public int getNumberOfSuppliers () {
        return  _suppliers.size();
    }

    public int getNumberOfConsumers () {
        return  _consumers.size();
    }

    public synchronized void suspend () {
        _suspended = true;
    }

    public synchronized void resume () {
        _suspended = false;
        notifyAll();
    }

    public void reset () {
        setNumberOfItemsProcessed(0);
        setAddWaitTime(0);
        setRemoveWaitTime(0);
        setNumberOfResets(getNumberOfResets() + 1);
    }

    /**
     * This class provides a mechanism to watch the queue and
     * if it is stalled, send a notification.
     */
    private class StalledQueueWatcher
            implements Runnable {
        private int _sleepInterval = 0;
        private long _threshold = 0;
        private long _fullSequenceNumber = 1;
        private long _emptySequenceNumber = 1;
        private boolean _stopSignalled = false;
        private long _totalFullElapsedTime = 0;
        private long _totalEmptyElapsedTime = 0;
        private static final int DEFAULT_SLEEP_INTERVAL = 1000;
        private static final long DEFAULT_THRESHOLD = 10000;

        public StalledQueueWatcher () {
            this(DEFAULT_SLEEP_INTERVAL, DEFAULT_THRESHOLD);
        }

        public StalledQueueWatcher (int sleepInterval, long threshold) {
            trace("StalledQueueWatcher: INFO: sleepInterval=" + sleepInterval
                    + ", threshold=" + threshold + ".");
            _sleepInterval = (sleepInterval > DEFAULT_SLEEP_INTERVAL) ? sleepInterval :
                    DEFAULT_SLEEP_INTERVAL;
            _threshold = (threshold > DEFAULT_THRESHOLD) ? threshold : DEFAULT_THRESHOLD;
        }

        public void stop () {
            _stopSignalled = true;
        }

        public void reset () {
            _totalFullElapsedTime = 0;
            _totalEmptyElapsedTime = 0;
        }

        public void run () {
            while (!_stopSignalled) {
                try {
                    Thread.sleep(_sleepInterval);
                    if (isQueueFull())
                        _totalFullElapsedTime += _sleepInterval; 
                    else 
                        _totalFullElapsedTime = 0;              //reset
                    if (isQueueEmpty())
                        _totalEmptyElapsedTime += _sleepInterval; 
                    else 
                        _totalEmptyElapsedTime = 0;             //reset
                    if (_totalFullElapsedTime > (_threshold*_fullSequenceNumber)) {
                        trace("StalledQueueWatcher.run(): INFO: " + "About to emit notification: \'"
                                + STALLED_QUEUE_FULL + "\'.");
                        emitNotification(STALLED_QUEUE_FULL, _fullSequenceNumber);
                        _fullSequenceNumber++;
                    }
                    if (_totalEmptyElapsedTime > (_threshold*_emptySequenceNumber)) {
                        trace("StalledQueueWatcher.run(): INFO: " + "About to emit notification: \'"
                                + STALLED_QUEUE_EMPTY + "\'.");
                        emitNotification(STALLED_QUEUE_EMPTY, _emptySequenceNumber);
                        _emptySequenceNumber++;
                    }
                } catch (InterruptedException e) {}
            }
        }

        private void emitNotification (String notifType, long seqNumber) {
            Notification notification = new Notification(notifType, this, seqNumber, 
                    System.currentTimeMillis());
            _broadcaster.sendNotification(notification);
        }
    }

    private void trace (String message) {
        if (isTraceOn()) {
            System.out.println(Thread.currentThread().getName() + ":" + message);
        }
    }
}




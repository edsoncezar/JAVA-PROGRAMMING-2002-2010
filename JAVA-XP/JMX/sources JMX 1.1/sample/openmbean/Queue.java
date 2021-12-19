/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.openmbean;

import  java.util.ArrayList;
import  javax.management.*;
import  java.lang.reflect.*;


/**
 * A very simple queue.
 *
 * @author Steve Perry
 */
public class Queue extends Basic
        /*implements DynamicMBean */{
    private static final int DEFAULT_QUEUE_SIZE = 10;
    private static final int WAIT_FOREVER = -1;
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
    private MBeanInfo _MBeanInfo;

    /**
     * put your documentation comment here
     */
    public Queue () {
        this(DEFAULT_QUEUE_SIZE);
    }

    /**
     * put your documentation comment here
     * @param   int queueSize
     */
    public Queue (int queueSize) {
        super();
        // Create the backing store. If the specified queue size is
        /// bogus, use the default queue size
        _backingStore = new Object[(queueSize > 0) ? queueSize : DEFAULT_QUEUE_SIZE];
        _queueEmpty = true;
//        exposeManagementInterface();
    }

    /**
     * put your documentation comment here
     */
    public synchronized void addSupplier () {
        String supplierName = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.addSupplier(): INFO: " + "Adding supplier \'"
                    + supplierName + "\'.");
        _suppliers.add(supplierName);
    }

    /**
     * put your documentation comment here
     */
    public synchronized void removeSupplier () {
        String supplier = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.removeSupplier(): INFO: " + "Looking for supplier \'"
                    + supplier + "\' to remove it.");
        for (int aa = 0; aa < _suppliers.size(); aa++) {
            String s = (String)_suppliers.get(aa);
            if (supplier.equals(s)) {
                if (isTraceOn())
                    System.out.println("Queue.removeSupplier(): INFO: " + "Removing supplier \'"
                            + s + "\'.");
                _suppliers.remove(aa);
                notifyAll();
                break;
            }
        }
        if (_suppliers.size() == 0)
            _endOfInput = true;
    }

    /**
     * put your documentation comment here
     */
    public synchronized void addConsumer () {
        String consumerName = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.addConsumer(): INFO: " + "Adding consumer \'"
                    + consumerName + "\'.");
        _consumers.add(consumerName);
    }

    /**
     * put your documentation comment here
     */
    public synchronized void removeConsumer () {
        String consumer = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.removeConsumer(): INFO: " + "Looking for consumer \'"
                    + consumer + "\' to remove it.");
        for (int aa = 0; aa < _consumers.size(); aa++) {
            String s = (String)_consumers.get(aa);
            if (consumer.equals(s)) {
                if (isTraceOn())
                    System.out.println("Queue.removeConsumer(): INFO: " + "Removing consumer \'"
                            + s + "\'.");
                _consumers.remove(aa);
                notifyAll();
                break;
            }
        }
        if (_consumers.size() == 0)
            _endOfInput = true;
    }

    /**
     * put your documentation comment here
     * @param item
     */
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
        _queueEmpty = false;
        _tail++;
        if (_tail >= _backingStore.length)
            _tail = 0;          // wrap
        if (_tail == _head) {
            _tail = -1;         // special case, we're full
        }
        _queueFull = (_tail == -1) ? true : false;
        notifyAll();
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public synchronized Object remove () {
        return  (remove(WAIT_FOREVER));
    }

    /**
     * put your documentation comment here
     * @param timeout
     * @return 
     */
    public synchronized Object remove (long timeout) {
        boolean timedOut = false;
        Object ret = null;
        long removeWaitTime = 0;
        if (!_endOfInput) {
            while (_suspended || _tail == _head) {
                long waitStart = System.currentTimeMillis();
                try {
                    if (timeout == WAIT_FOREVER)
                        wait(); 
                    else 
                        wait(timeout/1000);
                } catch (InterruptedException e) {}
                if (_endOfInput)
                    break;
                if (timeout != -1 && (System.currentTimeMillis() - waitStart) > timeout) {
                    timedOut = true;
                    break;
                }
                removeWaitTime += System.currentTimeMillis() - waitStart;
            }
            if (!timedOut) {
                _removeWaitTime += removeWaitTime;
                ret = _backingStore[_head];
                _queueFull = false;
                _numberOfItemsProcessed++;
                _backingStore[_head] = null;
                if (_tail == -1)
                    _tail = _head;              // point tail to new free spot
                _head++;
                if (_head >= _backingStore.length)
                    _head = 0;                  // wrap head pointer
                _queueEmpty = (_head == _tail) ? true : false;
            }
        }
        notifyAll();
        return  ret;
    }
    ;
    // Management Interface
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

    /**
     * put your documentation comment here
     * @return 
     */
    public int getQueueSize () {
        return  _backingStore.length;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public long getNumberOfItemsProcessed () {
        return  _numberOfItemsProcessed;
    }

    /**
     * put your documentation comment here
     * @param value
     */
    public void setNumberOfItemsProcessed (long value) {
        _numberOfItemsProcessed = value;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public long getAddWaitTime () {
        return  _addWaitTime;
    }

    /**
     * put your documentation comment here
     * @param value
     */
    public void setAddWaitTime (long value) {
        _addWaitTime = value;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public long getRemoveWaitTime () {
        return  _removeWaitTime;
    }

    /**
     * put your documentation comment here
     * @param value
     */
    public void setRemoveWaitTime (long value) {
        _removeWaitTime = value;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public Object[] getBackingStore () {
        return  _backingStore;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public boolean isQueueFull () {
        return  _queueFull;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public boolean isQueueEmpty () {
        return  _queueEmpty;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public boolean isSuspended () {
        return  _suspended;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public boolean isEndOfInput () {
        return  _endOfInput;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public int getNumberOfSuppliers () {
        return  _suppliers.size();
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public int getNumberOfConsumers () {
        return  _consumers.size();
    }

    /**
     * put your documentation comment here
     */
    public synchronized void suspend () {
        _suspended = true;
    }

    /**
     * put your documentation comment here
     */
    public synchronized void resume () {
        _suspended = false;
        notifyAll();
    }

    /**
     * put your documentation comment here
     */
    public void reset () {
        setNumberOfItemsProcessed(0);
        setAddWaitTime(0);
        setRemoveWaitTime(0);
        setNumberOfResets(getNumberOfResets() + 1);
    }
    private String[] _stateAsStringArray = new String[10];

    /*    public String[] toStringArray() {
     StringBuffer buf = new StringBuffer();
     buf.append("AddWaitTime : ");
     buf.append(_addWaitTime);
     buf.append("ms");
     _stateAsStringArray[0] = buf.toString();
     buf.setLength(0);
     buf.append("RemoveWaitTime : ");
     buf.append(_removeWaitTime);
     buf.append("ms");
     _stateAsStringArray[1] = buf.toString();
     buf.setLength(0);
     buf.append("EndOfInput : ");
     buf.append(_endOfInput);
     _stateAsStringArray[2] = buf.toString();
     buf.setLength(0);
     buf.append("NumberOfItemsProcessed : ");
     buf.append(_numberOfItemsProcessed);
     _stateAsStringArray[3] = buf.toString();
     buf.setLength(0);
     buf.append("NumberOfSuppliers : ");
     buf.append(getNumberOfSuppliers());
     _stateAsStringArray[4] = buf.toString();
     buf.setLength(0);
     buf.append("QueueEmpty : ");
     buf.append(_queueEmpty);
     _stateAsStringArray[5] = buf.toString();
     buf.setLength(0);
     buf.append("QueueFull : ");
     buf.append(_queueFull);
     _stateAsStringArray[6] = buf.toString();
     buf.setLength(0);
     buf.append("QueueSize : ");
     buf.append(getQueueSize());
     buf.append(" items");
     _stateAsStringArray[7] = buf.toString();
     buf.setLength(0);
     buf.append("Suspended : ");
     buf.append(_suspended);
     _stateAsStringArray[8] = buf.toString();
     buf.setLength(0);
     buf.append("TraceOn : ");
     buf.append(isTraceOn());
     _stateAsStringArray[9] = buf.toString();
     buf.setLength(0);
     return _stateAsStringArray;
     }
     */
    private void exposeManagementInterface () {
        MBeanInfo parentInfo = super.getMBeanInfo();
        //
        // Attributes
        //
        MBeanAttributeInfo[] parentAttributes = parentInfo.getAttributes();
        int numberOfParentAttributes = parentAttributes.length;
        MBeanAttributeInfo[] attributeInfo = new MBeanAttributeInfo[numberOfParentAttributes
                + 10];
        System.arraycopy(parentAttributes, 0, attributeInfo, 0, numberOfParentAttributes);
        attributeInfo[numberOfParentAttributes + 0] = new MBeanAttributeInfo("QueueSize", 
                Integer.TYPE.getName(), "Maximum number of items the queue may contain at one time.", 
                true, true, false);
        attributeInfo[numberOfParentAttributes + 1] = new MBeanAttributeInfo("NumberOfConsumers", 
                Integer.TYPE.getName(), "The number of consumers pulling from this Queue.", 
                true, false, false);
        attributeInfo[numberOfParentAttributes + 2] = new MBeanAttributeInfo("NumberOfSuppliers", 
                Integer.TYPE.getName(), "The number of suppliers supplying to this Queue.", 
                true, false, false);
        attributeInfo[numberOfParentAttributes + 3] = new MBeanAttributeInfo("QueueFull", 
                Boolean.TYPE.getName(), "Indicates whether or not the Queue is full.", 
                true, false, true);
        attributeInfo[numberOfParentAttributes + 4] = new MBeanAttributeInfo("QueueEmpty", 
                Boolean.TYPE.getName(), "Indicates whether or not the Queue is empty.", 
                true, false, true);
        attributeInfo[numberOfParentAttributes + 5] = new MBeanAttributeInfo("Suspended", 
                Boolean.TYPE.getName(), "Indicates whether or not the Queue is currently suspended.", 
                true, false, true);
        attributeInfo[numberOfParentAttributes + 6] = new MBeanAttributeInfo("EndOfInput", 
                Boolean.TYPE.getName(), "Indicates whether or not the end-of-input has been signalled by all suppliers.", 
                true, false, true);
        attributeInfo[numberOfParentAttributes + 7] = new MBeanAttributeInfo("NumberOfItemsProcessed", 
                Long.TYPE.getName(), "The number of items that have been removed from the Queue.", 
                true, false, false);
        attributeInfo[numberOfParentAttributes + 8] = new MBeanAttributeInfo("AddWaitTime", 
                Long.TYPE.getName(), "The number of milliseconds spent waiting to add because the Queue was full.", 
                true, false, false);
        attributeInfo[numberOfParentAttributes + 9] = new MBeanAttributeInfo("RemoveWaitTime", 
                Long.TYPE.getName(), "The number of milliseconds spent waiting to remove because the Queue was empty.", 
                true, false, false);
        //
        // Constructors
        //
        Class[] signature =  {
            Integer.TYPE
        };
        Constructor constructor = null;
        MBeanConstructorInfo[] constructorInfo = new MBeanConstructorInfo[1];
        try {
            constructor = this.getClass().getConstructor(signature);
            constructorInfo[0] = new MBeanConstructorInfo("Custom constructor", 
                    constructor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        // Operations
        //
        MBeanOperationInfo[] operationInfo = new MBeanOperationInfo[2];
        MBeanParameterInfo[] parms = new MBeanParameterInfo[0];
        operationInfo[0] = new MBeanOperationInfo("suspend", "Suspends processing of the Queue.", 
                parms, Void.TYPE.getName(), MBeanOperationInfo.ACTION);
        operationInfo[1] = new MBeanOperationInfo("resume", "Resumes processing of the Queue.", 
                parms, Void.TYPE.getName(), MBeanOperationInfo.ACTION);
        //
        // Notifications
        //
        MBeanNotificationInfo[] notificationInfo = new MBeanNotificationInfo[0];
        //
        // MBeanInfo
        //
        _MBeanInfo = new MBeanInfo("Queue", "Queue MBean", attributeInfo, constructorInfo, 
                operationInfo, notificationInfo);
    }

    // DynamicMBean Implementation
    public Object getAttribute (String attributeName) throws AttributeNotFoundException, 
            MBeanException, ReflectionException {
        Object ret = null;
        // Nope, not on parent class, must be on this one
        if (attributeName.equals("QueueSize")) {
            ret = new Integer(getQueueSize());
        } 
        else if (attributeName.equals("NumberOfSuppliers")) {
            ret = new Integer(getNumberOfSuppliers());
        } 
        else if (attributeName.equals("NumberOfConsumers")) {
            ret = new Integer(getNumberOfConsumers());
        } 
        else if (attributeName.equals("QueueFull")) {
            ret = new Boolean(isQueueFull());
        } 
        else if (attributeName.equals("QueueEmpty")) {
            ret = new Boolean(isQueueEmpty());
        } 
        else if (attributeName.equals("Suspended")) {
            ret = new Boolean(isSuspended());
        } 
        else if (attributeName.equals("EndOfInput")) {
            ret = new Boolean(isEndOfInput());
        } 
        else if (attributeName.equals("NumberOfItemsProcessed")) {
            ret = new Long(getNumberOfItemsProcessed());
        } 
        else if (attributeName.equals("AddWaitTime")) {
            ret = new Long(getAddWaitTime());
        } 
        else if (attributeName.equals("RemoveWaitTime")) {
            ret = new Long(getRemoveWaitTime());
        } 
        else {
            ret = super.getAttribute(attributeName);
        }
        return  ret;
    }

    /**
     * put your documentation comment here
     * @param attribute
     * @exception AttributeNotFoundException, InvalidAttributeValueException, MBeanException, 
     ReflectionException
     */
    public void setAttribute (Attribute attribute) throws AttributeNotFoundException, 
            InvalidAttributeValueException, MBeanException, ReflectionException {
        String name = attribute.getName();
        Object value = attribute.getValue();
        // See if attribute is on parent class
        if (name.equals("QueueSize")) {
            setQueueSize(((Integer)value).intValue());
        }
        // parent will throw exception if attribute not found
        super.setAttribute(attribute);
    }

    /**
     * put your documentation comment here
     * @param attributeNames
     * @return 
     */
    public AttributeList getAttributes (String[] attributeNames) {
        //
        // Make sure the attribute names String array is not null, or all of
        /// the .equals() calls will toss a NullPointerException
        //
        AttributeList resultList = new AttributeList();
        for (int aa = 0; aa < attributeNames.length; aa++) {
            try {
                Object value = getAttribute((String)attributeNames[aa]);
                resultList.add(new Attribute(attributeNames[aa], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  (resultList);
    }

    /**
     * put your documentation comment here
     * @param attributes
     * @return 
     */
    public AttributeList setAttributes (AttributeList attributes) {
        for (int aa = 0; aa < attributes.size(); aa++) {
            try {
                setAttribute((Attribute)attributes.get(aa));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] attributeNames = new String[attributes.size()];
        for (int aa = 0; aa < attributeNames.length; aa++) {
            attributeNames[aa] = ((Attribute)attributes.get(aa)).getName();
        }
        return  getAttributes(attributeNames);
    }

    /**
     * put your documentation comment here
     * @param operationName
     * @param params[]
     * @param signature[]
     * @return 
     * @exception MBeanException, ReflectionException
     */
    public Object invoke (String operationName, Object params[], String signature[]) throws MBeanException, 
            ReflectionException {
        Object ret = Void.TYPE;
        //
        // Make sure the operation name String is not null, or all of
        /// the .equals() calls will toss a NullPointerException
        //
        ret = Void.TYPE;
        // Not on parent class, must be here
        if (operationName.equals("suspend")) {
            suspend();
        } 
        else if (operationName.equals("resume")) {
            resume();
        } 
        else {
            super.invoke(operationName, params, signature);
        }
        return  ret;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public MBeanInfo getMBeanInfo () {
        return  (_MBeanInfo);
    }
}




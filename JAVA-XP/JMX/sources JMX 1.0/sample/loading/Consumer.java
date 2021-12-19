package  sample.loading;

import javax.management.*;

import sample.standard.Queue;
import sample.standard.WorkUnit;
/**
 * This class pulls WorkUnit instances from a Queue
 * and performs a certain amount of "work", specified
 * by workFactor.
 */
public class Consumer extends Worker implements MBeanRegistration {

    public static final String ROLE = "Consumer";
    private static final int DEFAULT_WORK_FACTOR = 100;
    
    public Consumer() {
    }

    /**
     * Allows the MBean to perform any operations it needs before being registered
     * in the MBean server. If the name of the MBean is not specified, the
     * MBean can provide a name for its registration. If any exception is
     * raised, the MBean will not be registered in the MBean server.
     *
     * @param server The MBean server in which the MBean will be registered.     
     * @param name The object name of the MBean.
     *
     * @return  The name of the MBean registered.
     *
     * @exception java.lang.Exception This exception should be caught by the MBean server and re-thrown
     * as an <CODE>MBeanRegistrationException</CODE>.
     */
    public ObjectName preRegister (MBeanServer server, ObjectName name) throws java.lang.Exception {
        ObjectName objName = new ObjectName(OBJECT_NAME + ",role=" + ROLE);
        return objName;
    }
    
    /**
     * Allows the MBean to perform any operations needed after having been
     * registered in the MBean server or after the registration has failed.
     *
     * @param registrationDone Indicates whether or not the MBean has been successfully registered in
     * the MBean server. The value false means that the registration phase has failed.     
     */
    public void postRegister (Boolean registrationDone) {
        // do nothing
    }
       
    /**
     * Allows the MBean to perform any operations it needs before being de-registered
     * by the MBean server.
     *
     * @exception java.langException  This exception should be caught by the MBean server and re-thrown
     * as an <CODE>MBeanRegistrationException</CODE>.
     */   
    public void preDeregister() throws java.lang.Exception {
        // do nothing
    }

    /**
     * Allows the MBean to perform any operations needed after having been
     * de-registered in the MBean server.
     */   
    public void postDeregister() {
        // do nothing
    }

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

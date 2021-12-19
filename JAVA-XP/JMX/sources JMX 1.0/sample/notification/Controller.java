package  sample.notification;

import sample.standard.*;
import  sample.utility.*;
import  com.sun.jdmk.comm.*;
import  javax.management.*;
import  java.util.*;


/**
 * Controls and coordinates all activities in the
 * sample application. Implemented as a standard MBean.
 *
 * This class is also a notification listener (i.e., it implements
 * the NotificationListener interface).
 *
 * @author Steve Perry
 */
public class Controller extends Basic
        implements ControllerMBean, NotificationListener {
    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private HtmlAdaptorServer _html;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;
    /**
     * List of the registered MBeans. Used so that all MBeans can
     * be deregistered when the application terminates.
     */
    private ArrayList _mbeans = new ArrayList();

    /**
     * Invoked when a JMX notification occurs.
     * The implementation of this method should return as soon as possible, to avoid
     * blocking its notification broadcaster.
     *
     * @param notification The notification.    
     * @param handback An opaque object which helps the listener to associate information
     * regarding the MBean emitter. This object is passed to the MBean during the
     * addListener call and resent, without modification, to the listener. The MBean object 
     * should not use or modify the object. 
     *
     */
    public void handleNotification (Notification notification, Object handback) {
        String notificationType = notification.getType();
        Object source = notification.getSource();
        long sequenceNumber = notification.getSequenceNumber();
        Date timeStamp = new Date(notification.getTimeStamp());
        try {
            if (handback instanceof Properties) {
                Properties props = (Properties)handback;
                String response = (String)props.get("response");
                if (response.equals("email")) {
                // send an email, using the properties set in the
                /// handback object. . .
                } 
                else if (response.equals("consoleMessage")) {
                    trace("Controller: NOTIFICATION: response=consoleMessage: "
                            + "Received from: " + source.getClass().getName()
                            + " at " + timeStamp);
                    trace("\tof type \'" + notificationType + "\': " + "sequence no. "
                            + sequenceNumber);
                } 
                else {
                    System.out.println("handleNotification: ERROR: " + "Unexpected response type \'"
                            + response + "\'.");
                }
            } 
            else {
                trace("Controller: NOTIFICATION: Received from: " + source.getClass().getName()
                        + " at " + timeStamp);
                trace("\tof type \'" + notificationType + "\': " + "sequence no. "
                        + sequenceNumber);
            }
        } catch (Exception e) {
        // handle possible exceptions . . .
        }
    }

    /**
     * The main method.
     *
     * @param args String array containing arguments
     */
    public static void main (String[] args) {
        // Must contain supplier work factor, followed by consumer
        /// work factor.
        MessageLog log = new MessageLog();
        if (args.length == 2) {
            int supplierWorkFactor = Integer.parseInt(args[0]);
            int consumerWorkFactor = Integer.parseInt(args[1]);
            System.out.println("Controller.main(): INFO: " + "supplierWorkFactor="
                    + supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            log.write("Controller.main(): INFO: " + "supplierWorkFactor=" + 
                    supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            // Create and initialize the controller
            Controller controller = new Controller();
            controller.init();
            // obtain a reference to the MBean server
            MBeanServer server = controller.getMBeanServer();
            // Register the controller as an MBean
            ObjectName objName = null;
            try {
                objName = new ObjectName(Controller.CONTROLLER_OBJECT_NAME);
                System.out.println("\tOBJECT NAME = " + objName);
                log.write("\tOBJECT NAME = " + objName);
                server.registerMBean(controller, objName);
                controller.enableTracing();
                controller._mbeans.add(objName);
                controller.createWorker(Supplier.ROLE, supplierWorkFactor);
                controller.createWorker(Consumer.ROLE, consumerWorkFactor);
                while (!(controller._queue.isEndOfInput() && controller._queue.isQueueEmpty())) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                log.write("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                e.printStackTrace();
                log.write(e);
                return;
            }
            // We're done, unregister all MBeans
            controller._html.stop();
            Iterator iter = controller._mbeans.iterator();
            while (iter.hasNext()) {
                objName = (ObjectName)iter.next();
                System.out.println("Controller.main(): INFO: " + "Unregistering MBean \'"
                        + objName.toString() + "\'.");
                log.write("Controller.main(): INFO: " + "Unregistering MBean \'"
                        + objName.toString() + "\'.");
                try {
                    server.unregisterMBean(objName);
                } catch (Exception e) {
                    System.out.println("Controller.main(): ERROR: " + "Error unregistering MBean!");
                    log.write("Controller.main(): ERROR: " + "Error unregistering MBean!");
                    e.printStackTrace();
                    log.write(e);
                }
            }
        } 
        else {
            System.out.println("Controller.main(): ERROR: Usage: " + "Controller supplier-weight consumer-weight");
            log.write("Controller.main(): ERROR: Usage: " + "Controller supplier-weight consumer-weight");
        }
    }

    /**
     * Initialization. Creates and registers the Queues and performs
     * initialization to get the controller up and running.
     */
    private void init () {
        // find reference to (or create) MBean server
        MBeanServer mbs = this.getMBeanServer();
        // Create the queue
        Object[] params = new Object[] {
            new Integer(5)
        };
        String[] signature = new String[] {
            Integer.TYPE.getName()
        };
        String queueClassName = "sample.notification.Queue";
        // Register the queue as an MBean
        ObjectName objName = null;
        try {
            Object queue = mbs.instantiate(queueClassName);
            _queue = (Queue)queue;
            Properties props = new Properties();
            props.put("response", "consoleMessage");
            _queue.addNotificationListener(this, null, props);
            _queue.enableTracing();
            objName = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            System.out.println("\tOBJECT NAME = " + objName);
            try {
                _server.registerMBean(queue, objName);
            } catch (Exception e) {
                trace(e);
            }
            _mbeans.add(objName);
        } catch (Exception e) {
            trace(e);
            e.printStackTrace();
            return;
        }
        // Create the HTML adapter
        this.createHTMLAdapter();
    }

    /**
     * Obtains a reference to the MBean server. If at least one
     * MBean server already exists, then a reference to that MBean
     * server is returned. Otherwise a new instance is created.
     */
    MBeanServer getMBeanServer () {
        if (_server == null) {
            ArrayList mbeanServers = MBeanServerFactory.findMBeanServer(null);
            Iterator iter = mbeanServers.iterator();
            int aa = 1;
            while (iter.hasNext()) {
                MBeanServer mbs = (MBeanServer)iter.next();
                trace("QueueAgent.main(): INFO: " + "MBean server no. " + aa
                        + ":");
                Set set = mbs.queryMBeans(null, null);
                Iterator iter2 = set.iterator();
                while (iter2.hasNext()) {
                    ObjectInstance obji = (ObjectInstance)iter2.next();
                    trace("\tCLASSNAME={" + obji.getClassName() + "}, OBJECTNAME={"
                            + obji.getObjectName() + "}");
                }
                aa++;
            }
            _server = (mbeanServers.size() > 0) ? (MBeanServer)mbeanServers.get(0) :
                    MBeanServerFactory.createMBeanServer();
        }
        return  _server;
    }

    /**
     * Creates the HTML adapter server and starts it running
     * on its own thread of execution.
     */
    private void createHTMLAdapter () {
        int portNumber = 8090;
        _html = new HtmlAdaptorServer(portNumber);
        ObjectName html_name = null;
        try {
            html_name = new ObjectName("Adaptor:name=html,port=" + portNumber);
            System.out.println("\tOBJECT NAME = " + html_name);
            _server.registerMBean(_html, html_name);
            _mbeans.add(html_name);
        } catch (Exception e) {
            trace("\t!!! Could not create the HTML adaptor !!!");
            e.printStackTrace();
            trace(e);
            return;
        }
        _html.start();
    }

    /**
     * Returns the number of workers of each type
     */
    protected int getNumberOfWorkers (String role) {
        int ret = 0;
        if (role.equalsIgnoreCase(Supplier.ROLE)) {
            ret = _numberOfSuppliers;
        } 
        else if (role.equalsIgnoreCase(Consumer.ROLE)) {
            ret = _numberOfConsumers;
        } 
        else {
            throw  new RuntimeMBeanException(new IllegalArgumentException("Controller.getNumberOfWorkers(): ERROR: "
                    + "Unknown role name \'" + role + "\'."));
        }
        return  ret;
    }

    /**
     * Builds the worker key from the role name
     */
    protected String buildWorkerKey (String role) {
        StringBuffer buf = new StringBuffer();
        buf.append(Worker.OBJECT_NAME);
        buf.append(",role=");
        buf.append(role);
        return  buf.toString();
    }

    /**
     * Creates and registers a worker MBean of the specified
     * type.
     */
    protected void createNewWorker (String role, int workFactor, int instanceId) {
        // Create the Worker and register it with the MBean server.
        Worker worker = null;
        ObjectName objName = null;
        StringBuffer buf = new StringBuffer();
        try {
            buf.append(buildWorkerKey(role));
            buf.append(",instanceId=");
            buf.append(instanceId);
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                worker = new Supplier(_queue, workFactor);
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                worker = new Consumer(_queue, workFactor);
            }
            objName = new ObjectName(buf.toString());
            System.out.println("\tOBJECT NAME = " + objName);
            _server.registerMBean(worker, objName);
            _mbeans.add(objName);
            Thread t = new Thread(worker);
            t.start();
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                _numberOfSuppliers++;
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                _numberOfConsumers++;
            }
            //        this.addWorkerThread(t);
        } catch (Exception e) {
            trace("Controller.main(): ERROR: " + "Could not register the worker MBean! Stack trace follows...");
            e.printStackTrace();
            trace(e);
            return;
        }
    }

    /**
     * Creates and starts a worker thread
     */
    public void createWorker (String role, int workFactor) {
        int index = getNumberOfWorkers(role);
        createNewWorker(role, workFactor, index + 1);
    }

    /**
     */
    public void reset () {
    // nothing to do
    }

    /**
     * Provides an abstraction mechanism for tracing.
     * @param message String containing the message to be written
     *        to the trace facility.
     */
    private void trace (String message) {
        if (isTraceOn()) {
            System.out.println(message);
        }
        traceLog(message);
    }

    private void trace (Throwable t) {
        traceLog(t);
    }

    private void traceLog (Throwable t) {
        if (isTraceOn()) {
            _logger.write(t);
        }
    }
    MessageLog _logger = new MessageLog();

    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
}




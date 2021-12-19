package  sample.loading;

import  com.sun.jdmk.comm.*;
import  com.sun.management.jmx.*;
import  javax.management.*;
import  javax.management.loading.*;
import  java.util.*;
import  sample.standard.Basic;
import sample.standard.Queue;
import  sample.utility.MessageLog;

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
    public static final String BASE_URL = "file:c:\\jmxbook\\";
//    public static final String BASE_URL = "http://localhost/jmxbook/";
    private MBeanServer _server;
    private ObjectName _queue;
    private Queue _theQueue;
    private HtmlAdaptorServer _html;
    private MLet _mlet;
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
                if (notification instanceof TraceNotification) {
                    TraceNotification notif = (TraceNotification)notification;
                    trace("Controller.handleNotification: TRACE NOTIFICATION: "
                            + "info: " + notif.info + ", class: " + notif.className
                            + ", methodName: " + notif.methodName + ", sequenceNumber: "
                            + notif.sequenceNumber + ", globalSequenceNumber: "
                            + notif.globalSequenceNumber + ", type: " + notif.type
                            + ", level: " + notif.level + ", exception: \'"
                            + notif.exception + "\'.");
                } 
                else if (notificationType.equals(MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
                    ObjectName objName = ((MBeanServerNotification)notification).getMBeanName();
                    trace("Controller.handleNotification: REGISTRATION NOTIFICATION: "
                            + "OBJECT_NAME = " + objName);
                    _mbeans.add(objName);
                } 
                else {
                    trace("Controller: NOTIFICATION: \'" + notification.getMessage()
                            + "', received from: " + source.getClass().getName()
                            + " at " + timeStamp + " of type \'" + notificationType
                            + "\': " + "sequence no. " + sequenceNumber);
                }
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
        if (args.length == 2) {
            //
            // This m-let stuff gets tricky. It's nice to see
            /// what's going on under the hood sometimes...
            try {
                Trace.parseTraceProperties();
            } catch (java.io.IOException e) {
                MessageLog log = new MessageLog();
                log.write(e);
            }
            int supplierWorkFactor = Integer.parseInt(args[0]);
            int consumerWorkFactor = Integer.parseInt(args[1]);
            System.out.println("Controller.main(): INFO: " + "supplierWorkFactor="
                    + supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            // Create and initialize the controller
            Controller controller = new Controller();
            controller.enableTracing();
            Trace.addNotificationListener(controller, null, null);
            // Register the controller as an MBean
            ObjectName objName = null;
            // obtain a reference to the MBean server
            MBeanServer server = controller.getMBeanServer();
            try {
                ObjectName delegateObjName = new ObjectName("JMImplementation:type=MBeanServerDelegate");
                server.addNotificationListener(delegateObjName, controller, 
                        null, null);
                controller.init();
                objName = new ObjectName(Controller.CONTROLLER_OBJECT_NAME);
                server.registerMBean(controller, objName);
                // Load the worker MBeans
                controller.createWorker("Supplier", supplierWorkFactor);
                controller.createWorker("Consumer", consumerWorkFactor);
                boolean queueEOF = ((Boolean)server.getAttribute(controller._queue, 
                        "EndOfInput")).booleanValue();
                boolean queueEmpty = ((Boolean)server.getAttribute(controller._queue, 
                        "QueueEmpty")).booleanValue();
                while (!(queueEOF && queueEmpty)) {
                    Thread.sleep(5000);
                    queueEOF = ((Boolean)server.getAttribute(controller._queue, 
                            "EndOfInput")).booleanValue();
                    queueEmpty = ((Boolean)server.getAttribute(controller._queue, 
                            "QueueEmpty")).booleanValue();
                }
            } catch (Exception e) {
                System.out.println("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                MessageLog log = new MessageLog();
                log.write(e);
                return;
            }
            // We're done, unregister all MBeans
            controller._html.stop();
            Iterator iter = controller._mbeans.iterator();
            while (iter.hasNext()) {
                objName = (ObjectName)iter.next();
                String message = "Controller.main(): INFO: " + "Unregistering MBean \'"
                        + objName.toString() + "\'.";
                System.out.println(message);
                MessageLog log = new MessageLog();
                log.write(message);
                try {
                    server.unregisterMBean(objName);
                } catch (Exception e) {
                    System.out.println("Controller.main(): ERROR: " + "Error unregistering MBean!");
                    log.write(e);
                }
            }
        } 
        else {
            System.out.println("Controller.main(): ERROR: Usage: " + "Controller supplier-weight consumer-weight");
        }
    }

    /**
     * Initialization. Creates and registers the Queues and performs
     * initialization to get the controller up and running.
     */
    private void init () {
        // find reference to (or create) MBean server
        MBeanServer mbs = this.getMBeanServer();
        try {
            // Create and register the mlet service
            ObjectName mletObjName = new ObjectName("DynamicLoader:type=m-let");
            _mlet = new MLet();
            mbs.registerMBean(_mlet, mletObjName);
            _theQueue = new Queue();
            _queue = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            mbs.registerMBean(_theQueue, _queue);
        } catch (Exception e) {
            trace(e);
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
            _server.registerMBean(_html, html_name);
        } catch (Exception e) {
            trace("\t!!! Could not create the HTML adaptor !!!");
            e.printStackTrace();
            return;
        }
        _html.start();
    }

    public Object getMe () {
        return  this;
    }

    public void createWorker (String role, int workFactor) {
        // Create the Worker and register it with the MBean server.
        ObjectName objName = null;
        try {
            // use the role name to build out the mlet file name
            /// located at the baseUrl...
            Set loadedBeans = loadMBeans(BASE_URL + role + ".txt");
            if (loadedBeans.size() > 1)
                trace("Controller.init(): ERROR: " + "Too many items (" + loadedBeans.size()
                        + ") in the returned set from loadMBeans()!");
            // Grab the ObjectName from the returned set of ObjectInstances
            Iterator iter = loadedBeans.iterator();
            while (iter.hasNext()) {
                Object obj = iter.next();
                if (obj instanceof Throwable) {
                    Throwable e = (Throwable)obj;
                    trace("Controller.createWorker(): ERROR: " + "Error occurred attempting to load the Worker MBeans.");
                    trace("Controller.createWorker(): EXCEPTION INFORMATION: " + e.toString());
                    trace(e);
                    e.printStackTrace();
                } 
                else {
                    objName = ((ObjectInstance)obj).getObjectName();
                    trace("Controller.createWorker(): INFO: Loaded MBean \'" + objName
                            + "\'");
                    //
                    // Set the queue and work factor
                    //
                    try {
                        Attribute attribute = new Attribute("Queue", _theQueue);
                        _server.setAttribute(objName, attribute);
                        attribute = new Attribute("WorkFactor", new Integer(workFactor));
                        _server.setAttribute(objName, attribute);
                    } catch (Exception e) {
                        trace(e);
                        e.printStackTrace();
                    }
                    // Get a reference to the MBean, cast it to a Runnable and
                    /// create a new Thread object. This will start the worker
                    /// thread...
                    Object worker = _server.getAttribute(objName, "Me");
                    Thread t = new Thread((Runnable)worker);
                    t.start();
                }
            }
        } catch (Exception e) {
            trace("Controller.createWorker(): ERROR: " + "Could not register the MBean! Stack trace follows...");
            trace(e);
            return;
        }
    }

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

    private Set loadMBeans (String url) {
        Set ret = null;
        trace("Controller.loadMBeans(): INFO: Loading MBeans from URL \'" + 
                url + "\'.");
        try {
            //        ret = _mlet.getMBeansFromURL(url);
            ret = _mlet.getMBeansFromURL(new java.net.URL(url));
        } catch (Exception e) {
            trace("Controller.loadMBeans(): ERROR: " + "Error getting MBeans from URL \'"
                    + url + "\'.");
            trace(e);
        }
        return  ret;
    }
}




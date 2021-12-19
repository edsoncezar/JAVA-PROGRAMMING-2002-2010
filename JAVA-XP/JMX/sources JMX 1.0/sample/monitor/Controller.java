package  sample.monitor;

import  sample.standard.*;
import  sample.utility.*;
import  com.sun.jdmk.comm.*;
import  javax.management.*;
import  javax.management.monitor.*;
import  java.util.*;


/**
 * Controls and coordinates all activities in the
 * sample application. Implemented as a standard MBean.
 */
public class Controller extends Basic
        implements ControllerMBean, NotificationListener {
    /** The object name that will be used by the controller.
     */
    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private HtmlAdaptorServer _html;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;
    private ArrayList _mbeans = new ArrayList();
    private ArrayList _monitors = new ArrayList();
    private Listener _listener;

    public void handleNotification (Notification notification, Object handback) {
        if (!(notification instanceof MonitorNotification)) {
            String type = notification.getType();
            String message = notification.getMessage();
            Object source = notification.getSource();
            trace("Controller.handleNotification(): INFO: " + "Received \'"
                    + type + "\' notification from: " + source + ", message: "
                    + message + ".");
        } 
        else {
            MonitorNotification monNotif = (MonitorNotification)notification;
            trace("Controller.handleNotification(): MONITOR NOTIFICATION: "
                    + monNotif.getType() + ": " + "Object: " + monNotif.getObservedObject()
                    + ", attribute: " + monNotif.getObservedAttribute() + " = "
                    + monNotif.getDerivedGauge());
        }
    }

    /**
     * The main method.
     *
     * @param args String array containing arguments
     */
    public static void main (String[] args) {
        MessageLog log = new MessageLog(MessageLog.INCLUDE_THREAD_INFO);
        if (args.length == 2) {
            int supplierWorkFactor = Integer.parseInt(args[0]);
            int consumerWorkFactor = Integer.parseInt(args[1]);
            System.out.println("Controller.main(): INFO: " + "supplierWorkFactor="
                    + supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            log.write("Controller.main(): INFO: " + "supplierWorkFactor=" + 
                    supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            Controller controller = new Controller();
            controller.enableTracing();
            controller.init();
            MBeanServer server = controller.getMBeanServer();
            // Register the controller as an MBean
            ObjectName objName = null;
            try {
                objName = new ObjectName(Controller.CONTROLLER_OBJECT_NAME);
                System.out.println("\tOBJECT NAME = " + objName);
                log.write("\tOBJECT NAME = " + objName);
                server.registerMBean(controller, objName);
                controller._mbeans.add(objName);
                controller.createWorker(Supplier.ROLE, supplierWorkFactor);
                controller.createWorker(Consumer.ROLE, consumerWorkFactor);
                Iterator iter = controller._monitors.iterator();
                while (iter.hasNext()) {
                    Monitor monitor = (Monitor)iter.next();
                    System.out.println("Controller.main(): INFO: Starting monitor: "
                            + "Observed object: " + monitor.getObservedObject()
                            + ", observed attribute: " + monitor.getObservedAttribute());
                    log.write("Controller.main(): INFO: Starting monitor: "
                            + "Observed object: " + monitor.getObservedObject()
                            + ", observed attribute: " + monitor.getObservedAttribute());
                    ObjectName monObjName = new ObjectName("Monitor:" + monitor.getObservedObject().getKeyPropertyListString()
                            + ",attribute=" + monitor.getObservedAttribute());
                    System.out.println("\tOBJECT NAME = " + monObjName);
                    log.write("\tOBJECT NAME = " + monObjName);
                    server.registerMBean(monitor, monObjName);
                    monitor.start();
                }
                while (!(controller._queue.isEndOfInput() && controller._queue.isQueueEmpty())) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                System.out.println("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                e.printStackTrace();
                log.write("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
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
            iter = controller._monitors.iterator();
            while (iter.hasNext()) {
                Monitor monitor = (Monitor)iter.next();
                System.out.println("Controller.main(): INFO: Stopping monitor: "
                        + "Observed object: " + monitor.getObservedObject()
                        + ", observed attribute: " + monitor.getObservedAttribute());
                log.write("Controller.main(): INFO: Stopping monitor: " + "Observed object: "
                        + monitor.getObservedObject() + ", observed attribute: "
                        + monitor.getObservedAttribute());
                monitor.stop();
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
        String queueClassName = "sample.standard.Queue";
        // Register the queue as an MBean
        ObjectName objName = null;
        try {
            _listener = new Listener();
            Object queue = mbs.instantiate(queueClassName, params, signature);
            //      Object queue = mbs.instantiate(queueClassName);
            _queue = (Queue)queue;
            _queue.enableTracing();
            objName = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            trace("\tOBJECT NAME = " + objName);
            //        _server.registerMBean(_queue, objName);
            _server.registerMBean(queue, objName);
            _mbeans.add(objName);
            // Start a monitor to observe the number of items
            /// processed by the queue. Every 500 items should
            /// trigger a notification
            CounterMonitor monitor = new CounterMonitor();
            monitor.setObservedObject(objName);
            monitor.setObservedAttribute("NumberOfItemsProcessed");
            monitor.setNotify(true);
            monitor.setThreshold(new Long(500));
            monitor.setOffset(new Long(500));
            monitor.setGranularityPeriod(5000);
            monitor.addNotificationListener(_listener, null, null);
            _monitors.add(monitor);
            //
            // Start a StringMonitor to send notifications whenever the
            /// operator name gets assigned.
            //
            StringMonitor smonitor = new StringMonitor();
            smonitor.setObservedObject(new ObjectName(this.CONTROLLER_OBJECT_NAME));
            smonitor.setObservedAttribute("OperatorName");
            smonitor.setNotifyMatch(true);
            smonitor.setNotifyDiffer(true);
            smonitor.setStringToCompare("Unassigned");
            smonitor.setGranularityPeriod(5000);
            smonitor.addNotificationListener(_listener, null, null);
            _monitors.add(smonitor);
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
            trace("\tOBJECT NAME = " + html_name);
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
            trace("\tOBJECT NAME = " + objName);
            _server.registerMBean(worker, objName);
            _mbeans.add(objName);
            // Start a gauge monitor to monitor the wait times
            /// in the queue.
            GaugeMonitor gmonitor = new GaugeMonitor();
            gmonitor.setObservedObject(objName);
            gmonitor.setObservedAttribute("AverageUnitProcessingTime");
            gmonitor.setNotifyHigh(true);
            gmonitor.setNotifyLow(true);
            gmonitor.setThresholds(new Float(500), new Float(500));
            gmonitor.setGranularityPeriod(5000);
            gmonitor.addNotificationListener(_listener, null, null);
            _monitors.add(gmonitor);
            gmonitor.start();
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

    public void reset () {
    // nothing to do
    }

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
    MessageLog _logger = new MessageLog(MessageLog.INCLUDE_THREAD_INFO);

    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
    private String _operatorName = "Unassigned";

    public String getOperatorName () {
        return  _operatorName;
    }

    public void setOperatorName (String operatorName) {
        if (operatorName != null && !operatorName.equals(""))
            _operatorName = operatorName;
    }
}




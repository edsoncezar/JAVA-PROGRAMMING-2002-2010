/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.model;

import  sample.utility.*;
import  com.sun.jdmk.comm.*;
import  com.sun.jdmk.Trace;
import  javax.management.*;
import  javax.management.modelmbean.*;
import  java.util.*;
import  java.io.IOException;

//
// These classes represent the existing resource that we
/// are going to instrument through RequiredModelMBean in this
/// class, which represents the JMX agent for the sample application.
//
import sample.standard.Basic;
import sample.standard.Worker;
import sample.standard.Consumer;
import sample.standard.Supplier;
import sample.standard.Queue;

/**
 * Controls and coordinates all activities in the
 * sample application.
 */
public class Controller extends Basic implements ControllerMBean {
    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;

    /**
     * The main method.
     *
     * @param args String array containing arguments
     */
    public static void main (String[] args) {
        try {
            Trace.parseTraceProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageLog log = new MessageLog();
        if (args.length == 2) {
            int supplierWorkFactor = Integer.parseInt(args[0]);
            int consumerWorkFactor = Integer.parseInt(args[1]);
            System.out.println("Controller.main(): INFO: " + "supplierWorkFactor="
                    + supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            log.write("Controller.main(): INFO: " + "supplierWorkFactor=" + 
                    supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
            Controller controller = new Controller();
            controller.enableTracing();
            MBeanServer server = controller.getMBeanServer();
            // Register the controller as an MBean
            ObjectName objName = null;
            try {
                // expose Controller as a model MBean
                objName = new ObjectName(Controller.CONTROLLER_OBJECT_NAME);
                System.out.println("\tOBJECT NAME = " + objName);
                log.write("\tOBJECT NAME = " + objName);
                server.registerMBean(controller, objName);
            } catch (Exception e) {
                System.out.println("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                log.write("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
                e.printStackTrace();
                log.write(e);
                return;
            }
            controller.createWorker(Supplier.ROLE, supplierWorkFactor);
            controller.createWorker(Consumer.ROLE, consumerWorkFactor);
        } 
        else {
            System.out.println("Controller.main(): ERROR: Usage: " + "Controller supplier-weight consumer-weight");
            log.write("Controller.main(): ERROR: Usage: " + "Controller supplier-weight consumer-weight");
        }
    }

    /**
     * Constructor. Creates and registers the Queues and performs
     * initialization to get the controller up and running.
     */
    public Controller () {
        // find reference to (or create) MBean server
        this.getMBeanServer();
        // Create the queue
        _queue = new Queue();
        // Register the queue as an MBean
        ObjectName objName = null;
        try {
            objName = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            trace("\tOBJECT NAME = " + objName);
            // Create queue as model MBean
            RequiredModelMBean queue = new RequiredModelMBean();
            // There are 10 attributes, create an array accordingly
            ModelMBeanAttributeInfo[] attributeInfo = new ModelMBeanAttributeInfo[10];
            String[] fields = new String[] {
                "name=QueueSize", "descriptorType=attribute", "displayName=QueueSize", 
                        "getMethod=getQueueSize", "setMethod=setQueueSize", 
                        "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1", "value=0"
            };
            DescriptorSupport desc = new DescriptorSupport(fields);
            attributeInfo[0] = new ModelMBeanAttributeInfo("QueueSize", "java.lang.Integer", 
                    "The maximum size of the queue.", true, true, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=NumberOfItemsProcessed", "descriptorType=attribute", 
                        "displayName=NumberOfItemsProcessed", "getMethod=getNumberOfItemsProcessed", 
                        "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[1] = new ModelMBeanAttributeInfo("NumberOfItemsProcessed", 
                    "java.lang.Long", "The number of work units processed.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=AddWaitTime", "descriptorType=attribute", "displayName=AddWaitTime", 
                        "getMethod=getAddWaitTime", "persistPolicy=Never", 
                        //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[2] = new ModelMBeanAttributeInfo("AddWaitTime", "java.lang.Long", 
                    "No. ms. spent waiting to add a work unit to the queue.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=RemoveWaitTime", "descriptorType=attribute", "displayName=RemoveWaitTime", 
                        "getMethod=getRemoveWaitTime", "persistPolicy=Never", 
                        //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[3] = new ModelMBeanAttributeInfo("RemoveWaitTime", 
                    "java.lang.Long", "No. ms. spent waiting to remove a work unit from the queue.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=QueueFull", "descriptorType=attribute", "displayName=QueueFull", 
                        "getMethod=isQueueFull", "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[4] = new ModelMBeanAttributeInfo("QueueFull", "java.lang.Boolean", 
                    "true if queue is full, false otherwise.", true, false, 
                    true, desc);
            desc = new DescriptorSupport(new String[] {
                "name=QueueEmpty", "descriptorType=attribute", "displayName=QueueEmpty", 
                        "getMethod=isQueueEmpty", "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[5] = new ModelMBeanAttributeInfo("QueueEmpty", "java.lang.Boolean", 
                    "true if queue is empty, false otherwise.", true, false, 
                    true, desc);
            desc = new DescriptorSupport(new String[] {
                "name=Suspended", "descriptorType=attribute", "displayName=Suspended", 
                        "getMethod=isSuspended", "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[6] = new ModelMBeanAttributeInfo("Suspended", "java.lang.Boolean", 
                    "true if queue is suspended, false otherwise.", true, false, 
                    true, desc);
            desc = new DescriptorSupport(new String[] {
                "name=EndOfInput", "descriptorType=attribute", "displayName=EndOfInput", 
                        "getMethod=isEndOfInput", "persistPolicy=Never", 
                //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[7] = new ModelMBeanAttributeInfo("EndOfInput", "java.lang.Boolean", 
                    "true if end of input has been signalled, false otherwise.", 
                    true, false, true, desc);
            desc = new DescriptorSupport(new String[] {
                "name=NumberOfSuppliers", "descriptorType=attribute", "displayName=NumberOfSuppliers", 
                        "getMethod=getNumberOfSuppliers", "persistPolicy=Never", 
                        //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[8] = new ModelMBeanAttributeInfo("NumberOfSuppliers", 
                    "java.lang.Integer", "No. of supplier threads currently feeding the queue.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=NumberOfConsumers", "descriptorType=attribute", "displayName=NumberOfConsumers", 
                        "getMethod=getNumberOfConsumers", "persistPolicy=Never", 
                        //          "currencyTimeLimit=5",
                "iterable=F", "visibility=1"
            });
            attributeInfo[9] = new ModelMBeanAttributeInfo("NumberOfConsumers", 
                    "java.lang.Integer", "No. of consumer threads currently feeding the queue.", 
                    true, false, false, desc);
            // operations (must also include getters/setters)
            ModelMBeanOperationInfo[] operationInfo = new ModelMBeanOperationInfo[13];
            desc = new DescriptorSupport(new String[] {
                "name=getQueueSize", "descriptorType=operation", "role=getter"
            });
            operationInfo[0] = new ModelMBeanOperationInfo("getQueueSize", 
                    "Getter for QueueSize", new MBeanParameterInfo[0], Integer.TYPE.getName(), 
                    MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=setQueueSize", "descriptorType=operation", "role=setter"
            });
            MBeanParameterInfo[] parms = new MBeanParameterInfo[1];
            parms[0] = new MBeanParameterInfo("value", "java.lang.Integer", 
                    "value");
            operationInfo[1] = new ModelMBeanOperationInfo("setQueueSize", 
                    "Setter for QueueSize", parms, Void.TYPE.getName(), MBeanOperationInfo.ACTION, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=getNumberOfItemsProcessed", "descriptorType=operation", 
                        "role=getter"
            });
            operationInfo[2] = new ModelMBeanOperationInfo("getNumberOfItemsProcessed", 
                    "Getter for NumberOfItemsProcessed", new MBeanParameterInfo[0], 
                    Long.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getAddWaitTime", "descriptorType=operation", "role=getter"
            });
            operationInfo[3] = new ModelMBeanOperationInfo("getAddWaitTime", 
                    "Getter for AddWaitTime", new MBeanParameterInfo[0], Long.TYPE.getName(), 
                    MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getRemoveWaitTime", "descriptorType=operation", "role=getter"
            });
            operationInfo[4] = new ModelMBeanOperationInfo("getRemoveWaitTime", 
                    "Getter for RemoveWaitTime", new MBeanParameterInfo[0], 
                    Long.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=isQueueFull", "descriptorType=operation", "role=getter"
            });
            operationInfo[5] = new ModelMBeanOperationInfo("isQueueFull", "Getter for QueueFull", 
                    new MBeanParameterInfo[0], Boolean.TYPE.getName(), MBeanOperationInfo.INFO, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=isQueueEmpty", "descriptorType=operation", "role=getter"
            });
            operationInfo[6] = new ModelMBeanOperationInfo("isQueueEmpty", 
                    "Getter for QueueEmpty", new MBeanParameterInfo[0], Boolean.TYPE.getName(), 
                    MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=isSuspended", "descriptorType=operation", "role=getter"
            });
            operationInfo[7] = new ModelMBeanOperationInfo("isSuspended", "Getter for Suspended", 
                    new MBeanParameterInfo[0], Boolean.TYPE.getName(), MBeanOperationInfo.INFO, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=isEndOfInput", "descriptorType=operation", "role=getter"
            });
            operationInfo[8] = new ModelMBeanOperationInfo("isEndOfInput", 
                    "Getter for EndOfInput", new MBeanParameterInfo[0], Boolean.TYPE.getName(), 
                    MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getNumberOfConsumers", "descriptorType=operation", "role=getter"
            });
            operationInfo[9] = new ModelMBeanOperationInfo("getNumberOfConsumers", 
                    "Getter for NumberOfConsumers", new MBeanParameterInfo[0], 
                    Integer.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getNumberOfSuppliers", "descriptorType=operation", "role=getter"
            });
            operationInfo[10] = new ModelMBeanOperationInfo("getNumberOfSuppliers", 
                    "Getter for NumberOfSuppliers", new MBeanParameterInfo[0], 
                    Integer.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            //      desc = new DescriptorSupport(
            //        new String[] {
            //          "name=suspend",
            //          "descriptorType=operation",
            //          "displayName=suspend",
            //          "role=operation",
            //          "visibility=1"
            //        }
            //      );
            operationInfo[11] = new ModelMBeanOperationInfo("suspend", "Suspends activity in the queue.", 
                    new MBeanParameterInfo[0], Void.TYPE.getName(), MBeanOperationInfo.ACTION
            //        desc
            );
            desc = new DescriptorSupport(new String[] {
                "name=resume", "descriptorType=operation", "displayName=resume", 
                        "role=operation", "visibility=1"
            });
            operationInfo[12] = new ModelMBeanOperationInfo("resume", "Resumes activity in the queue.", 
                    new MBeanParameterInfo[0], Void.TYPE.getName(), MBeanOperationInfo.ACTION, 
                    desc);
            ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport(queue.getClass().getName(), 
                    "Queue Model MBean", attributeInfo, null, operationInfo, 
                    null);
            queue.setModelMBeanInfo(mbeanInfo);
            queue.setManagedResource(_queue, "ObjectReference");
            _server.registerMBean(queue, objName);
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
        HtmlAdaptorServer html = new HtmlAdaptorServer(portNumber);
        ObjectName html_name = null;
        try {
            html_name = new ObjectName("Adaptor:name=html,port=" + portNumber);
            trace("\tOBJECT NAME = " + html_name);
            _server.registerMBean(html, html_name);
        } catch (Exception e) {
            trace("\t!!! Could not create the HTML adaptor !!!");
            e.printStackTrace();
            trace(e);
            return;
        }
        html.start();
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
            else
                throw new IllegalArgumentException(
                    "Controller.createNewWorker(): ERROR: " +
                    "Unknown worker type \'" + role + "\'");
            objName = new ObjectName(buf.toString());
            trace("\tOBJECT NAME = " + objName);
            RequiredModelMBean requiredModelMBean = new RequiredModelMBean();
            // expose management interface for the new worker as
            /// a model MBean
            ModelMBeanAttributeInfo[] attributeInfo = new ModelMBeanAttributeInfo[4];
            // THIS IS LAME. The XML String I have to pass in to get it
            /// to parse correctly is not well-formed XML.
            //        String xmlString =
            //          "<descriptor>" +
            //            "<field name=name value=WorkFactor></field>" +
            //            "<field name=descriptorType value=attribute></field>" +
            //            "<field name=getMethod value=getWorkFactor></field>" +
            //          "</descriptor>";
            Descriptor desc = new DescriptorSupport(
            //          xmlString
            new String[] {
                "name=WorkFactor", "descriptorType=attribute", "getMethod=getWorkFactor", 
        
            });
            //        desc.setField("value", new Integer(-100));
            //        desc.setField("default", new Integer(-123));
            //        desc.setField("persistPeriod", new Integer(2));
            //        desc.setField("persistPolicy", "OnTimer");
            attributeInfo[0] = new ModelMBeanAttributeInfo("WorkFactor", 
            //          Integer.TYPE.getName(),
            "java.lang.Integer", "Amount of work performed per work unit.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=NumberOfUnitsProcessed", "descriptorType=attribute", 
                        "displayName=NumberOfUnitsProcessed", "getMethod=getNumberOfUnitsProcessed", 
                        "persistPolicy=Never", "iterable=F", "visibility=1"
            });
            attributeInfo[1] = new ModelMBeanAttributeInfo("NumberOfUnitsProcessed", 
                    //          Long.TYPE.getName(),
            "java.lang.Long", "The total number of work units processed by this thread.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=AverageUnitProcessingTime", "descriptorType=attribute", 
                        "displayName=AverageUnitProcessingTime", "getMethod=getAverageUnitProcessingTime", 
                        "persistPolicy=Never", "iterable=F", "visibility=1"
            });
            attributeInfo[2] = new ModelMBeanAttributeInfo("AverageUnitProcessingTime", 
                    //          Float.TYPE.getName(),
            "java.lang.Float", "The average time (in ms.) to process each work unit.", 
                    true, false, false, desc);
            desc = new DescriptorSupport(new String[] {
                "name=Suspended", "descriptorType=attribute", "displayName=Suspended", 
                        "getMethod=isSuspended", "persistPolicy=Never", "iterable=F", 
                        "visibility=1"
            });
            attributeInfo[3] = new ModelMBeanAttributeInfo("Suspended", 
            //          Boolean.TYPE.getName(),
            "java.lang.Boolean", "true if this thread is suspended, false otherwise.", 
                    true, false, true, desc);
            // operations
            ModelMBeanOperationInfo[] operationInfo = new ModelMBeanOperationInfo[7];
            desc = new DescriptorSupport(new String[] {
                "name=getworkfactor",           // not case-sensitive! conflicts with spec!
                "descriptorType=operation", 
            });
            operationInfo[0] = new ModelMBeanOperationInfo("getWorkFactor", 
                    "Getter for WorkFactor", new MBeanParameterInfo[0], Integer.TYPE.getName(), 
                    MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getNumberOfUnitsProcessed", "descriptorType=operation", 
                        "role=getter"
            });
            operationInfo[1] = new ModelMBeanOperationInfo("getNumberOfUnitsProcessed", 
                    "Getter for NumberOfUnitsProcessed", new MBeanParameterInfo[0], 
                    Long.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=getAverageUnitProcessingTime", "descriptorType=operation", 
                        "role=getter"
            });
            operationInfo[2] = new ModelMBeanOperationInfo("getAverageUnitProcessingTime", 
                    "Getter for AverageUnitProcessingTime", new MBeanParameterInfo[0], 
                    Float.TYPE.getName(), MBeanOperationInfo.INFO, desc);
            desc = new DescriptorSupport(new String[] {
                "name=isSuspended", "descriptorType=operation", "role=getter"
            });
            operationInfo[3] = new ModelMBeanOperationInfo("isSuspended", "Getter for Suspended", 
                    new MBeanParameterInfo[0], Boolean.TYPE.getName(), MBeanOperationInfo.INFO, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=suspend", "descriptorType=operation", "displayName=suspend", 
                        "role=operation", "visibility=1"
            });
            operationInfo[4] = new ModelMBeanOperationInfo("suspend", "Suspends activity in the queue.", 
                    new MBeanParameterInfo[0], Void.TYPE.getName(), MBeanOperationInfo.ACTION, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=resume", "descriptorType=operation", "displayName=resume", 
                        "role=operation", "visibility=1"
            });
            operationInfo[5] = new ModelMBeanOperationInfo("resume", "Resumes activity in the queue.", 
                    new MBeanParameterInfo[0], Void.TYPE.getName(), MBeanOperationInfo.ACTION, 
                    desc);
            desc = new DescriptorSupport(new String[] {
                "name=stop", "descriptorType=operation", "displayName=stop", 
                        "role=operation", "visibility=1"
            });
            operationInfo[6] = new ModelMBeanOperationInfo("stop", "Stops this worker thread.", 
                    new MBeanParameterInfo[0], Void.TYPE.getName(), MBeanOperationInfo.ACTION, 
                    desc);
            // notifications
            ModelMBeanNotificationInfo[] notificationInfo = new ModelMBeanNotificationInfo[1];
            desc = new DescriptorSupport(new String[] {
                "name=MyNotification", "descriptorType=notification"
                //            "log=T"
            });
            desc.setField("log", new Boolean(true));
            notificationInfo[0] = new ModelMBeanNotificationInfo(new String[] {
                "sample.model.mynotification"
            }, "MyNotification",                // this must match name in descriptor!
            "My, er, um, Notifications", desc);
            desc = new DescriptorSupport(new String[] {
                "name=WorkerMBean", "descriptorType=mbean", "log=T", "logFile=jmxmain.log"
            });
            desc.setField("persistPolicy", "OnTimer");
            ModelMBeanInfo modelMBeanInfo = new ModelMBeanInfoSupport(requiredModelMBean.getClass().getName(), 
                    ("Worker ModelMBean of type " + role), attributeInfo, null, 
                    operationInfo, notificationInfo);
            requiredModelMBean.setModelMBeanInfo(modelMBeanInfo);
            requiredModelMBean.setManagedResource(worker, "ObjectReference");
            _server.registerMBean(requiredModelMBean, objName);
            Thread t = new Thread(worker);
            t.start();
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                _numberOfSuppliers++;
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                _numberOfConsumers++;
            }
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
    MessageLog _logger = new MessageLog();

    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
}




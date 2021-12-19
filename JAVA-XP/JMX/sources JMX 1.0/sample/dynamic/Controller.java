/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.dynamic;

import  sample.utility.*;
import  java.lang.reflect.*;
import  com.sun.jdmk.comm.*;
import  javax.management.*;
import  java.util.*;

/**
 * Controls and coordinates all activities in the
 * sample application. Implemented as a dynamic MBean.
 */
public class Controller extends Basic implements DynamicMBean {
    
    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;

    /**
     * This block of code acts as the JMX agent for the sample
     * application.
     */
    public static void main (String[] args) {
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
        super();
        //
        // Get the metadata from the parent class. We will then make
        /// copies of the metadata from the parent in order to implement 
        /// inheritance for this dynamic MBean. In our implementation of
        /// the DynamicMBean interface, we will delegate attributes and
        /// operations asked for but not located on this class to the
        /// parent class (parent class delegation).
        //
        MBeanInfo parentInfo = super.getMBeanInfo();
        //
        // Create attribute metadata:
        // - ConsumerQueue
        // - SupplierQueue
        //
        MBeanAttributeInfo[] parentAttributes = parentInfo.getAttributes();
        int numParentAtts = parentAttributes.length;
        MBeanAttributeInfo[] attributeInfo = new MBeanAttributeInfo[numParentAtts + 1];
        System.arraycopy(parentAttributes, 0, attributeInfo, 0, numParentAtts);
        //
        // Add one attribute: the Queue that holds the WorkUnit objects
        /// that are exchanged between consumer and supplier. The Queue
        /// object type is not supported by the HtmlAdaptorServer.
        //
        attributeInfo[numParentAtts + 0] = new MBeanAttributeInfo(
            "ConsumerSupplierQueue",// name
            "sample.dynamic.Queue", // type
            "The Queue.",           // description
            true,                   // isReadable?
            false,                  // isWritable?
            false                   // isIs?
        );
        //
        // Create constructor metadata
        //
        Constructor[] constructors = this.getClass().getConstructors();
        MBeanConstructorInfo[] constructorInfo = new MBeanConstructorInfo[constructors.length];
        for (int aa = 0; aa < constructors.length; aa++) {
            constructorInfo[aa] = new MBeanConstructorInfo(
                "Constructs a Controller MBean.",   // description
                constructors[aa]                    // java.lang.reflect.Constructor
            );
        }
        MBeanOperationInfo[] parentOperations = parentInfo.getOperations();
        int numParentOps = parentOperations.length;
        MBeanOperationInfo[] operationInfo = new MBeanOperationInfo[numParentOps + 1];
        System.arraycopy(parentOperations, 0, operationInfo, 0, numParentOps);
        //
        // Create operation info:
        // - createWorker
        //
        MBeanParameterInfo[] signature = new MBeanParameterInfo[2];
        signature[0] = new MBeanParameterInfo(
            "role",             // name
            "java.lang.String", // type
            "The role this new worker thread will take on."         // description
        );
        signature[1] = new MBeanParameterInfo(
            "workFactor", Integer.TYPE.getName(), 
            "The weighted work factor for this new worker thread."
        );
        operationInfo[numParentOps + 0] = new MBeanOperationInfo(
            "createWorker",             // name
            "Creates a new Worker thread.",         // description
            signature,                  // MBeanParameterInfo[]
            Void.TYPE.getName(),        // return type
            MBeanOperationInfo.ACTION   // impact
        );
        //
        // Create Notification metadata:
        // - none
        //
        MBeanNotificationInfo[] parentNotifications = parentInfo.getNotifications();
        int numParentNots = parentNotifications.length;
        MBeanNotificationInfo[] notificationInfo = new MBeanNotificationInfo[
                numParentNots + 0];
        System.arraycopy(parentNotifications, 0, notificationInfo, 0, numParentNots);
        //
        // Now (finally!) create the MBean info metadata object
        //
        _MBeanInfo = new MBeanInfo(
            this.getClass().getName(),  // DynamicMBean implementing class name
            "Controller Dynamic MBean.",// description
            attributeInfo,              // MBeanAttributeInfo[]
            constructorInfo,            // MBeanConstructorInfo[]
            operationInfo,              // MBeanOperationInfo[]
            notificationInfo            // MBeanNotificationInfo[]
        );
        // find reference to (or create) MBean server
        this.getMBeanServer();
        // Create the queues
        _queue = new Queue();
        // Register the queue as an MBean
        ObjectName objName = null;
        try {
            objName = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            trace("\tOBJECT NAME = " + objName);
            _server.registerMBean(_queue, objName);
        } catch (Exception e) {
            e.printStackTrace();
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
     * Retrieves a reference to the Queue.
     */
    public Queue getConsumerSupplierQueue () {
        return  _queue;
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
            Thread t = new Thread(worker);
            t.start();
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                _numberOfSuppliers++;
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                _numberOfConsumers++;
            }
        } catch (Exception e) {
            trace("Controller.main(): ERROR: " + "Could not register the Supplier MBean! Stack trace follows...");
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
    // nothing to do, have to implement, though...
    }

    //
    // DynamicMBean Implementation
    //
    /**
     * Obtains the value of a specific attribute of the Dynamic MBean.
     *
     * @param attribute The name of the attribute to be retrieved
     *
     * @return  The value of the attribute retrieved.
     *
     * @exception AttributeNotFoundException
     * @exception MBeanException  Wraps a <CODE>java.lang.Exception</CODE> thrown by the MBean's getter.
     * @exception ReflectionException  Wraps a <CODE>java.lang.Exception</CODE> thrown while trying to invoke the getter. 
     */
    public Object getAttribute (String attributeName) throws AttributeNotFoundException, 
            MBeanException, ReflectionException {
        Object ret = null;
        if (attributeName.equals("ConsumerSupplierQueue")) {
            ret = this.getConsumerSupplierQueue();
        } 
        else 
            ret = super.getAttribute(attributeName);
        return  ret;
    }

    /**
     * Sets the value of a specific attribute of the Dynamic MBean
     *
     * @param attribute The identification of the attribute to
     * be set and  the value it is to be set to.
     *
     * @exception AttributeNotFoundException 
     * @exception InvalidAttributeValueException 
     * @exception MBeanException Wraps a <CODE>java.lang.Exception</CODE> thrown by the MBean's setter.
     * @exception ReflectionException Wraps a <CODE>java.lang.Exception</CODE> thrown while trying to invoke the MBean's setter.
     */
    public void setAttribute (Attribute attribute) throws AttributeNotFoundException, 
            InvalidAttributeValueException, MBeanException, ReflectionException {
        //
        // No writeable attributes on the management interface.
        // Delegate to parent class to implement management interface
        /// inheritance.
        //
        super.setAttribute(attribute);
    }

    /**
     * Enables the values of several attributes of the Dynamic MBean.
     *
     * @param attributes A list of the attributes to be retrieved.
     *
     * @return  The list of attributes retrieved.
     *
     */
    public AttributeList getAttributes (String[] attributeNames) {
        // no attributes...delegate to parent class
        return  super.getAttributes(attributeNames);
    }

    /**
     * Sets the values of several attributes of the Dynamic MBean
     *
     * @param name The object name of the MBean within which the attributes are to
     * be set.
     * @param attributes A list of attributes: The identification of the
     * attributes to be set and  the values they are to be set to.
     *
     * @return  The list of attributes that were set, with their new values.
     *
     */
    public AttributeList setAttributes (AttributeList attributes) {
        //
        // No writeable attributes on the management interface...
        // Delegate to parent class.
        //
        return  super.setAttributes(attributes);
    }

    /**
     * Allows an action to be invoked on the Dynamic MBean.
     *
     * @param actionName The name of the action to be invoked.
     * @param params An array containing the parameters to be set when the action is
     * invoked.
     * @param signature An array containing the signature of the action. The class objects will
     * be loaded through the same class loader as the one used for loading the
     * MBean on which the action is invoked.
     *
     * @return  The object returned by the action, which represents the result of
     * invoking the action on the MBean specified.
     *
     * @exception MBeanException  Wraps a <CODE>java.lang.Exception</CODE> thrown by the MBean's invoked method.
     * @exception ReflectionException  Wraps a <CODEjava.lang.Exception</CODE thrown while trying to invoke the method
     */
    public Object invoke (String operationName, Object params[], String signature[]) throws MBeanException, 
            ReflectionException {
        Object ret = Void.TYPE;
        //
        // Turn the signature into a Class array so we can get the Method
        /// object and invoke the method, passing params
        //
        try {
            Class[] sign = new Class[signature.length];
            for (int aa = 0; aa < signature.length; aa++) {
                try {
                    sign[aa] = this.getClassFromString(signature[aa]);
                } catch (ClassNotFoundException e) {
                    throw  new RuntimeOperationsException(new IllegalArgumentException(
                            "Controller.invoke(): ERROR: " + "Bad argument \'"
                            + sign[aa] + " found when attempting to invoke operation \'"
                            + operationName + "\'!"));
                }
            }
            Method theMethod = this.getClass().getMethod(operationName, sign);
            //
            // If we got this far, then the requested method is on the
            /// Class, but is it on the management interface?
            //
            if (operationName.equals("createWorker")) {
                theMethod.invoke(this, params);
            } 
            else {
                // delegate to parent class
                ret = super.invoke(operationName, params, signature);
            }
        } catch (NoSuchMethodException e) {
            throw  new ReflectionException(e, e.getClass().getName());
        } catch (IllegalAccessException e) {
            throw  new ReflectionException(e, e.getClass().getName());
        } catch (InvocationTargetException e) {
            throw  new ReflectionException(e, e.getClass().getName());
        }
        return  ret;
    }

    /**
     * Provides the exposed attributes and actions of the Dynamic MBean using an MBeanInfo object.
     *
     * @return  An instance of <CODE>MBeanInfo</CODE> allowing all attributes and actions 
     * exposed by this Dynamic MBean to be retrieved.
     *
     */
    public MBeanInfo getMBeanInfo () {
        return  (_MBeanInfo);
    }
    private MBeanInfo _MBeanInfo;

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




package  sample.relation;

import  sample.utility.*;
import  com.sun.jdmk.comm.*;
import  javax.management.*;
import  javax.management.relation.*;
import  java.util.*;
import  sample.standard.*;


/**
 * Controls and coordinates all activities in the
 * sample application. Implemented as a standard MBean.
 */
public class Controller extends sample.standard.Basic implements ControllerMBean {

    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private HtmlAdaptorServer _html;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;
    private ArrayList _mbeans = new ArrayList();
    //
    // Relation service stuff
    //
    private static final String CONSUMERSUPPLIER_RELATIONTYPE_NAME = "ConsumerSupplierRelationType_Internal";
    private static final String CONSUMERSUPPLIER_RELATION_NAME = "ConsumerSupplierRelation_Internal";
    private static final String RELATIONSERVICE_OBJECT_NAME = "AgentServices:service=Relation";
    private RelationService _relationService;

    /**
     * The main method.
     *
     * @param args String array containing arguments
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
                //
                // Capture the object names for each of the worker
                /// MBeans so we can set up the initial relation
                //
                ObjectName supplierObjName = controller.createNewWorker(Supplier.ROLE, 
                        supplierWorkFactor, 1);
                //
                // Create the Role object for the Suppliers
                //
                ArrayList theMBeans = new ArrayList();
                theMBeans.add(supplierObjName);
                Role supplierRole = new Role(Supplier.ROLE, (List)(theMBeans.clone()));
                //
                // Create the Role object for the Consumers
                //
                ObjectName consumerObjName = controller.createNewWorker(Consumer.ROLE, 
                        consumerWorkFactor, 1);
                theMBeans.clear();
                theMBeans.add(consumerObjName);
                Role consumerRole = new Role(Consumer.ROLE, (List)(theMBeans.clone()));
                //
                // Now create the Relation for Consumer-Supplier.
                /// When the createRelation() method of the relation service
                /// object is used to do this, the relation is internal
                /// to the relation service. To create an external relation,
                /// pass a registered relation MBean to the addRelation()
                /// method of the relation service object.
                //
                RoleList roles = new RoleList();
                roles.add(supplierRole);
                roles.add(consumerRole);
                 // SCENARIO #1
                 // Create an internal relation using an internal
                 /// relation type
                 //
                 controller._relationService.createRelation(
                 CONSUMERSUPPLIER_RELATION_NAME,
                 CONSUMERSUPPLIER_RELATIONTYPE_NAME,
                 roles
                 );
                //
                // This is an example of an external relation type.
                /// Use the ConsumerSupplierRelationType object as the
                /// relation type.
                //
 //               RelationType relationType = new ConsumerSupplierRelationType();
 //               controller._relationService.addRelationType(relationType);
                /*                // SCENARIO #2
                 // Create an internal relation using an external
                 /// relation type
                 //
                 controller._relationService.createRelation(
                 CONSUMERSUPPLIER_RELATION_NAME,
                 relationType.getRelationTypeName(),
                 roles
                 );
                 */
                /*                // SCENARIO #3
                 // Create an external relation using an internal
                 /// relation type.
                 //
                 ConsumerSupplierRelation relation = new ConsumerSupplierRelation(
                 new ObjectName(RELATIONSERVICE_OBJECT_NAME),
                 CONSUMERSUPPLIER_RELATIONTYPE_NAME, 
                 roles
                 );
                 */
/*                // SCENARIO #4
                // Create an external relation using an external
                /// relation type.
                //
                ConsumerSupplierRelation relation = new ConsumerSupplierRelation(
                        new ObjectName(RELATIONSERVICE_OBJECT_NAME), relationType.getRelationTypeName(), 
                        roles);
                ObjectName relationObjName = new ObjectName(ConsumerSupplierRelation.OBJECT_NAME);
                controller._server.registerMBean(relation, relationObjName);
                System.out.println("\tOBJECT NAME = " + relationObjName);
                log.write("\tOBJECT NAME = " + relationObjName);
                controller._relationService.addRelation(relationObjName);
 */
                controller._mbeans.add(supplierObjName);
                controller._mbeans.add(consumerObjName);
                //
                // When the Queue is empty and end of input
                /// has been signalled, we're done.
                //
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
            System.out.println("Controller.main(): INFO: Execution complete.");
            log.write("Controller.main(): INFO: Execution complete.");
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
            Object queue = mbs.instantiate(queueClassName, params, signature);
            //      Object queue = mbs.instantiate(queueClassName);
            _queue = (Queue)queue;
            _queue.enableTracing();
            objName = new ObjectName(_server.getDefaultDomain() + ":name=Queue");
            trace("\tOBJECT NAME = " + objName);
            //        _server.registerMBean(_queue, objName);
            _server.registerMBean(queue, objName);
            _mbeans.add(objName);
            //
            // Create the relation service and set up the metadata
            /// for the relation between consumer and supplier MBeans.
            //
            _relationService = new RelationService(true);
            objName = new ObjectName(RELATIONSERVICE_OBJECT_NAME);
            _server.registerMBean(_relationService, objName);
            _mbeans.add(objName);            
            //
             // First, tell the relation service about the roles.
             /// This is an example of an internal relation type,
             /// which means the relation type is managed by the
             /// relation service.
             //
             RoleInfo[] roleInfo = new RoleInfo[2];
             roleInfo[0] = new RoleInfo(
             Consumer.ROLE,              // role name
             "sample.standard.Consumer", // class name
             true,                       // role can be read
             true,                       // role can be modified
             1,                          // must be at least one
             2,                          // no more than two
             "Consumer Role Information" // description
             );
             roleInfo[1] = new RoleInfo(
             Supplier.ROLE,              // role name
             "sample.standard.Supplier", // class name
             true,                       // role can be read
             true,                       // role can be modified
             1,                          // must be at least one
             1,                          // no more than two
             "Supplier Role Information" // description
             );
             _relationService.createRelationType(
             CONSUMERSUPPLIER_RELATIONTYPE_NAME,
             roleInfo
             );

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
    protected ObjectName createNewWorker (String role, int workFactor, int instanceId) {
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
            //        this.addWorkerThread(t);
        } catch (Exception e) {
            trace("Controller.main(): ERROR: " + "Could not register the worker MBean! Stack trace follows...");
            e.printStackTrace();
            trace(e);
        }
        return  objName;
    }

    /**
     * Creates and starts a worker thread
     */
    public void createWorker (String role, int workFactor) {
        int index = getNumberOfWorkers(role);
        ObjectName objName = createNewWorker(role, workFactor, index + 1);
        //
        // We need to update the relation service with the
        /// new MBean just added...if there is a problem of some
        /// kind with the relationship, then the relation service
        /// will honk out an exception at us...
        //
        try {
            //
            // For an internal relation, first retrieve the role 
            /// from the relation service. If the relation is external
            /// then we can get the role directly from the relation
            /// MBean itself.
            //
            List theRoleMBeans = _relationService.getRole(CONSUMERSUPPLIER_RELATION_NAME, 
                    role);
            //
            // Make a copy of the current list, since we don't know
            /// what the relation service will do to it once we
            /// update the role information.
            //
            List theNewRoleMBeans = new ArrayList();
            theNewRoleMBeans.addAll(theRoleMBeans);
            theNewRoleMBeans.add(objName);
            //
            // Call setRole() so that the relation service will
            /// check the validity of the newly-added MBean against
            /// the corresponding RoleInfo object.
            // Just doing theRoleMBeans.add(objName) would have
            /// added the new worker MBean to the list of MBeans
            /// for the role, but no consistency check would have
            /// been performed. This is not good, since the whole
            /// reason to use the relation service is to have those
            /// checks performed in the first place!!!
            //
            _relationService.setRole(CONSUMERSUPPLIER_RELATION_NAME, new Role(role, 
                    theNewRoleMBeans));
            _mbeans.add(objName);
        } catch (Exception e) {
            trace("Controller.createWorker(): ERROR: " + e.getMessage());
            trace(e);
            trace("Controller.createWorker(): the MBean \'" + objName + "\' will be unregistered and its stop() method invoked.");
            try {
                _server.invoke(objName, "stop", new Object[0], new String[0]);
                _server.unregisterMBean(objName);
            } catch (Exception e2) {
                trace("Controller.createWorker(): ERROR: " + e2.getMessage());
                trace(e2);
            }
            throw  new RuntimeException(e.getMessage());
        }
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
        if (isTraceOn())
            t.printStackTrace();
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




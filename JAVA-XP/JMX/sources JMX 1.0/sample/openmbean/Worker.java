/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.openmbean;

import  javax.management.*;
import com.sun.jdmk.openmbean.*;
import  java.lang.reflect.*;


/**
 * Base class for the workers in the sample application
 */
public abstract class Worker extends Basic
        implements Runnable {
    public static final String OBJECT_NAME = "UserDomain:name=Worker";
    Queue _queue;
    int _workFactor;
    long _numberOfUnitsProcessed;
    long _totalProcessingTime;
    boolean _stopCalled;
    boolean _suspended;

    /**
     * put your documentation comment here
     * @param   Queue queue
     * @param   int workFactor
     */
    public Worker (Queue queue, int workFactor) {
        super();
        _queue = queue;
        _workFactor = workFactor;
        // Attributes
        OpenMBeanAttributeInfo[] attributeInfo = new OpenMBeanAttributeInfo[6];
        attributeInfo[0] = new OpenMBeanAttributeInfoSupport(
            "WorkFactor",                 // name
            "The amount of work to do.",  // description
            SimpleType.INTEGER,           // type
            true,                         // isReadable?
            false,                        // isWritable?
            false                         // isIs?
        );
        attributeInfo[1] = new OpenMBeanAttributeInfoSupport(
            "NumberOfUnitsProcessed",     // name
            "The number of units processed.", // description
            SimpleType.LONG,              // type
            true,                         // isReadable?
            false,                        // isWritable?
            false                         // isIs?
        );
        attributeInfo[2] = new OpenMBeanAttributeInfoSupport(
            "AverageUnitProcessingTime",  // name
            "Avg. no. milliseconds to process each work unit.", // description
            SimpleType.FLOAT,             // type
            true,                         // isReadable?
            false,                        // isWritable?
            false                         // isIs?
        );
        attributeInfo[3] = new OpenMBeanAttributeInfoSupport(
            "Suspended",                  // name
            "Whether or not this thread is suspended.", // description
            SimpleType.BOOLEAN,           // type
            true,                         // isReadable?
            false,                        // isWritable?
            true                          // isIs?
        );
        attributeInfo[4] = new OpenMBeanAttributeInfoSupport(
            "TraceOn",                    // name
            "Whether or not tracing is on.", // description
            SimpleType.BOOLEAN,           // type
            true,                         // isReadable?
            false,                        // isWritable?
            true                          // isIs?
        );
        attributeInfo[5] = new OpenMBeanAttributeInfoSupport(
            "NumberOfResets",             // name
            "The number of times this MBean has been reset.", // description
            SimpleType.INTEGER,           // type
            true,                         // isReadable?
            false,                        // isWritable?
            false                         // isIs?
        );
        // Constructors
        OpenMBeanConstructorInfo[] constructorInfo = new OpenMBeanConstructorInfo[1];
        constructorInfo[0] = new OpenMBeanConstructorInfoSupport(
            "Constructor",
            "Constructs a Worker MBean.",        // description
            new OpenMBeanParameterInfoSupport[0]
        );
        // Operations
        OpenMBeanOperationInfo[] operationInfo = new OpenMBeanOperationInfo[3];
        operationInfo[0] = new OpenMBeanOperationInfoSupport(
            "stop",                        // name
            "Stops this Worker thread.",   // description
            new OpenMBeanParameterInfo[0], // signature
            SimpleType.VOID,               // return type
            MBeanOperationInfo.ACTION      // impact
        );
        operationInfo[1] = new OpenMBeanOperationInfoSupport(
            "suspend",                      // name
            "Suspends this Worker thread.", // description
            new OpenMBeanParameterInfo[0],  // signature
            SimpleType.VOID,                // return type
            MBeanOperationInfo.ACTION       // impact
        );
        operationInfo[2] = new OpenMBeanOperationInfoSupport(
            "resume",                      // name
            "Resumes this Worker thread.", // description
            new OpenMBeanParameterInfo[0], // signature
            SimpleType.VOID,               // return type
            MBeanOperationInfo.ACTION      // impact
        );
        // Notifications - NONE
        // MBeanInfo!
        _MBeanInfo = new OpenMBeanInfoSupport(
            this.getClass().getName(),  // DynamicMBean implementing class name
            "Worker Open MBean.",       // description
            attributeInfo,              // MBeanAttributeInfo[]
            constructorInfo,            // MBeanConstructorInfo[]
            operationInfo,              // MBeanOperationInfo[]
            null                        // MBeanNotificationInfo[]
        );
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public float getAverageUnitProcessingTime () {
        return  (_numberOfUnitsProcessed > 0) ? (float)_totalProcessingTime/(float)_numberOfUnitsProcessed :
                0.0f;
    }

    /**
     * put your documentation comment here
     */
    public abstract void run ();

    /**
     * put your documentation comment here
     * @return 
     */
    public int getWorkFactor () {
        return  _workFactor;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public long getNumberOfUnitsProcessed () {
        return  _numberOfUnitsProcessed;
    }

    /**
     * put your documentation comment here
     * @param value
     */
    public void setNumberOfUnitsProcessed (long value) {
        _numberOfUnitsProcessed = value;
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
     */
    public synchronized void stop () {
        _stopCalled = true;
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
        setNumberOfUnitsProcessed(0);
        setNumberOfResets(getNumberOfResets() + 1);
    }

    /**
     * In this method is where the "work" takes place. We need
     * a way to simulate the application doing something, so we
     * calculate the first workFactor primes, starting with zero.
     * So, the work factor is equal to the number of primes that
     * are calculated.
     */
    void calculatePrimes (int numberOfPrimesToCalculate) {
        // There is probably an easier way to do this,
        /// but it seemed a good way to chew up some CPU
        long startTime = System.currentTimeMillis();
        long number = 1;
        int numberOfPrimesCalculated = 0;
        while (numberOfPrimesCalculated < numberOfPrimesToCalculate) {
            long currentNumber = 1;             // start with 1
            long numberOfFactors = 0;
            while (currentNumber <= number) {
                if ((number%currentNumber) == 0) {
                    // This is *definitely* the long way around, but
                    /// remember, we *want* to eat up lots of CPU...
                    numberOfFactors++;
                }
                currentNumber++;
            }
            // The number is prime if it only has two factors
            /// (i.e., itself and 1)
            if (numberOfFactors == 2) {
                numberOfPrimesCalculated++;
                //                System.out.println("Supplier.calculatePrimes(): INFO: " +
                //                    "Prime number found - " + number);
            }
            number++;
        }
        _totalProcessingTime += (System.currentTimeMillis() - startTime);
    }

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
        //
        // Using exlicit superclass exposure as the means of
        /// management interface inheritance.
        //
        if (attributeName.equals("WorkFactor")) {
            ret = new Integer(getWorkFactor());
        } 
        else if (attributeName.equals("NumberOfUnitsProcessed")) {
            ret = new Long(getNumberOfUnitsProcessed());
        } 
        else if (attributeName.equals("AverageUnitProcessingTime")) {
            ret = new Float(getAverageUnitProcessingTime());
        } 
        else if (attributeName.equals("Suspended")) {
            ret = new Boolean(isSuspended());
        } 
        else if (attributeName.equals("TraceOn")) {
            ret = new Boolean(isTraceOn());
        } 
        else if (attributeName.equals("NumberOfResets")) {
            ret = new Integer(getNumberOfResets());
        } 
        else 
            throw  new AttributeNotFoundException("Worker.getAttribute(): ERROR: "
                    + "Attribute \'" + attributeName + "\' not found.");
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
    //
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
        // no attributes...
        AttributeList ret = new AttributeList();
        return  ret;
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
        //
        AttributeList ret = new AttributeList();
        return  ret;
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
            if (operationName.equals("stop") || operationName.equals("suspend")
                    || operationName.equals("resume") || operationName.equals("reset")
                    || operationName.equals("enableTracing") || operationName.equals("disableTracing")) {
                theMethod.invoke(this, params);
            } 
            else {
                // delegate to parent class
                throw  new ReflectionException(new NoSuchMethodException("Worker.invoke(): ERROR: "
                        + "Method \'" + operationName + "\' does not exist."));
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
}




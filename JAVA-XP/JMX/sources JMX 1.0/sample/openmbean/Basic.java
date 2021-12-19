/*
 * put your module comment here
 * formatted with JxBeauty (c) johann.langhofer@nextra.at
 */


package  sample.openmbean;

import  java.lang.reflect.*;
import  javax.management.*;
import sample.utility.*;

import com.sun.jdmk.openmbean.*;
/**
 * Base class for the sample application.
 * Implemented as an open MBean.
 */
public abstract class Basic
        implements DynamicMBean {

    /**
     * Construtor, used to expose the management interface
     * of this MBean through the dynamic MBean metadata
     * classes.
     */
    public Basic () {
        try {
            //
            // Build attribute metadata:
            // - NumberOfResets
            // - TraceOn
            // - DebugOn
            //
            OpenMBeanAttributeInfo[] attributeInfo = new OpenMBeanAttributeInfo[3];
            attributeInfo[0] = new OpenMBeanAttributeInfoSupport(
                "NumberOfResets",
                "The number of times reset() has been called.",
                SimpleType.INTEGER,
                true,
                false,  // not writable
                false,
                new Integer(0),
                null
            );
            attributeInfo[1] = new OpenMBeanAttributeInfoSupport(
                "TraceOn",
                "Indicates whether or not tracing is on.",
                SimpleType.BOOLEAN,
                true,
                false,  // not writable
                false,
                new Boolean(false),
                null
            );
            attributeInfo[2] = new OpenMBeanAttributeInfoSupport(
                "DebugOn",
                "Indicates whether or not debugging is on.",
                SimpleType.BOOLEAN,
                true,
                false,  // not writable
                false,
                new Boolean(false),
                null
            );
            //
            // Now build Constructor metadata
            //
            OpenMBeanConstructorInfo[] constructorInfo = new OpenMBeanConstructorInfo[0];
            //
            // Now build operation metadata:
            // - enableTracing
            // - disableTracing
            // - enableDebugging
            // - disableDebugging
            // - reset
            //
            OpenMBeanOperationInfo[] operationInfo = new OpenMBeanOperationInfo[0];
            //
            // Build Notification Metadata:
            // - none
            //
            MBeanNotificationInfo[] notificationInfo = new MBeanNotificationInfo[0];
            //
            // Now (finally!) create the MBean info metadata object
            //
            _MBeanInfo = new OpenMBeanInfoSupport(
                this.getClass().getName(),
                "Basic MBean instrumented as an open MBean",
                attributeInfo,
                constructorInfo,
                operationInfo,
                notificationInfo
            );
        } catch (Exception e) {
            trace(e);
        }
    }
    // backing stores
    private boolean _traceOn;
    private boolean _debugOn;
    private int _numberOfResets;

    // not on management interface
    public void setNumberOfResets (int value) {
        _numberOfResets = value;
    }

    // attributes on management interface
    public boolean isTraceOn () {
        return  _traceOn;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public boolean isDebugOn () {
        return  _debugOn;
    }

    /**
     * put your documentation comment here
     * @return 
     */
    public int getNumberOfResets () {
        return  _numberOfResets;
    }

    // operations on management interface
    public void enableTracing () {
        _traceOn = true;
    }

    /**
     * put your documentation comment here
     */
    public void disableTracing () {
        _traceOn = false;
    }

    /**
     * put your documentation comment here
     */
    public void enableDebugging () {
        _debugOn = true;
    }

    /**
     * put your documentation comment here
     */
    public void disableDebugging () {
        _debugOn = false;
    }

    /**
     * put your documentation comment here
     */
    public abstract void reset ();

    // DynamicMBean Implementation
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
        // See if attribute is recognized.
        //
        if (attributeName.equals("NumberOfResets")) {
            ret = new Integer(getNumberOfResets());
        } 
        else if (attributeName.equals("TraceOn")) {
            ret = new Boolean(isTraceOn());
        } 
        else if (attributeName.equals("DebugOn")) {
            ret = new Boolean(isDebugOn());
        } 
        // If attribute_name has not been recognized throw an AttributeNotFoundException
        else 
            throw  new AttributeNotFoundException("Basic.getAttribute(): ERROR: "
                    + "Cannot find " + attributeName + " attribute.");
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
    /// Nothing to do.
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
        AttributeList resultList = new AttributeList();
        for (int aa = 0; aa < attributeNames.length; aa++) {
            try {
                Object value = getAttribute((String)attributeNames[aa]);
                resultList.add(new Attribute(attributeNames[aa], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  resultList;
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
        /// Return an empty AttributeList.
        //
        AttributeList atts = new AttributeList();
        return  atts;
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
        // Check for a recognized operation name and call the corresponding operation
        if (operationName.equals("enableTracing")) {
            enableTracing();
        } 
        else if (operationName.equals("disableTracing")) {
            disableTracing();
        } 
        else if (operationName.equals("enableDebugging")) {
            enableDebugging();
        } 
        else if (operationName.equals("disableDebugging")) {
            disableDebugging();
        } 
        else if (operationName.equals("reset")) {
            reset();
        } 
        else {
            // unrecognized operation name:
            throw  new ReflectionException(new NoSuchMethodException(operationName), 
                    "Basic.invoke(): ERROR: " + "Cannot find the operation "
                    + operationName + "!");
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

    protected OpenMBeanInfo getOpenMBeanInfo() {
        return (OpenMBeanInfo)_MBeanInfo;
    }

    /**
     * put your documentation comment here
     * @param className
     * @return 
     * @exception ClassNotFoundException
     */
    Class getClassFromString (String className) throws ClassNotFoundException {
        Class ret = null;
        if (className.equals(Boolean.TYPE.getName()))
            ret = Boolean.TYPE; 
        else if (className.equals(Character.TYPE.getName()))
            ret = Character.TYPE; 
        else if (className.equals(Byte.TYPE.getName()))
            ret = Byte.TYPE; 
        else if (className.equals(Short.TYPE.getName()))
            ret = Short.TYPE; 
        else if (className.equals(Integer.TYPE.getName()))
            ret = Integer.TYPE; 
        else if (className.equals(Long.TYPE.getName()))
            ret = Long.TYPE; 
        else if (className.equals(Float.TYPE.getName()))
            ret = Float.TYPE; 
        else if (className.equals(Double.TYPE.getName()))
            ret = Double.TYPE; 
        else if (className.equals(Void.TYPE.getName()))
            ret = Void.TYPE;
        //
        // Not a primitive type, just load the class based
        /// on the name.
        //
        else 
            ret = Class.forName(className);
        return  ret;
    }
    private void trace (String message) {
        if (isTraceOn()) {
            System.out.println(message);
        }
        traceLog(message);
    }

    /**
     * put your documentation comment here
     * @param t
     */
    private void trace (Throwable t) {
        traceLog(t);
    }

    /**
     * put your documentation comment here
     * @param t
     */
    private void traceLog (Throwable t) {
        if (isTraceOn()) {
            _logger.write(t);
        }
    }
    MessageLog _logger = new MessageLog();

    /**
     * put your documentation comment here
     * @param message
     */
    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
}




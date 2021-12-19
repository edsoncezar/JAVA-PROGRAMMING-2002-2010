package  sample.timer;

import  java.util.Properties;
import  java.io.*;
import  java.util.Date;
import  javax.management.*;
import  javax.management.timer.*;
import  sample.utility.*;


/**
 * This class is responsible for scheduling the start of the
 * sample application (using the Timer Service), and setting
 * the interval for log messages to be written to the log.
 *
 */
public class Scheduler {

    /** Creates new Scheduler */
    public Scheduler () {
    }

    public static void main (String[] args) {
        try {
            Properties props = new Properties();
            FileInputStream propFile = new FileInputStream("scheduler.properties");
            props.load(propFile);
            // get the wait time before starting the controller...
            String controllerStartWaitTime = (String)props.get("controller.startWaitTime");
            trace("Controller start wait time: " + controllerStartWaitTime);
            // get the wait time between writing log messages...
            String logFlushWaitTime = (String)props.get("logger.flushWaitTime");
            trace("Log flush wait time: " + logFlushWaitTime);
            // get the consumer work factor...
            String consumerWorkFactor = (String)props.get("controller.consumer.workFactor");
            trace("Consumer work factor: " + consumerWorkFactor);
            // get the supplier work factor...
            String supplierWorkFactor = (String)props.get("controller.supplier.workFactor");
            trace("Supplier work factor: " + supplierWorkFactor);
            //
            // Create the timer...
            //
            Timer timer = new Timer();
            Listener listener = new Listener();
            long startTime = System.currentTimeMillis() + (new Long(controllerStartWaitTime)).longValue();
            Date startDate = new Date(startTime);
            trace("Scheduler: INFO: Controller will start after " + startDate);
            //
            // Add notification that starts the controller...
            timer.addNotification(Controller.CONTROLLER_START, null, props,                     // so the listener will have the parameter values...
            startDate);
            timer.addNotificationListener(listener, null, null);
            //
            // Add notification that flushes the log...
            //
            timer.addNotification(MessageLogQueue.FLUSH_LOG, "Time to flush the log to disk.", 
                    null, new Date(), (new Long(logFlushWaitTime)).longValue());
            timer.addNotificationListener(MessageLogQueue.instance(), null, 
                    null);
            trace("Scheduler: INFO: Log will flush every " + logFlushWaitTime
                    + "ms. starting at " + new Date());
            timer.start();
            //            ObjectName objName = new ObjectName("Timer:type=generic");
            //            MBeanServer mbs = MBeanServerFactory.createMBeanServer();
            //            mbs.registerMBean(timer, objName);
        } catch (Exception e) {
            trace("Scheduler.main(): ERROR: " + e.getMessage());
        }
    }
    static MessageLog _logger = new MessageLog(MessageLog.INCLUDE_THREAD_INFO);

    private static void traceLog (String message) {
        _logger.write(message);
    }

    private static void traceLog (Throwable t) {
        _logger.write(t);
    }

    private static void trace (String message) {
        System.out.println(message);
        traceLog(message);
    }

    private static void trace (Throwable t) {
        t.printStackTrace();
        traceLog(t);
    }
}
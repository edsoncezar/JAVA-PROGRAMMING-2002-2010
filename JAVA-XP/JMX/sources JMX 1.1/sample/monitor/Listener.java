package  sample.monitor;

import  javax.management.*;
import  javax.management.monitor.*;
import  sample.utility.*;


/**
 *
 * @author  sperry
 * @version 
 */
public class Listener implements javax.management.NotificationListener {
    
    private boolean _traceOn = true;

    /** 
     * Creates new Listener
     */
    public Listener () {
    }

    public void handleNotification (Notification notification, Object obj) {
        String type = notification.getType();
        if (notification instanceof MonitorNotification) {
            MonitorNotification notif = (MonitorNotification)notification;
            String attribute = notif.getObservedAttribute();
            ObjectName obsObj = notif.getObservedObject();
            Object derivedGauge = notif.getDerivedGauge();
            Object trigger = notif.getTrigger();
            if (type.equals(MonitorNotification.THRESHOLD_VALUE_EXCEEDED)) {
                trace("THRESHOLD EXCEEDED: Attribute: " + attribute + ", Object: "
                        + obsObj + ", Derived Gauge: " + derivedGauge + ", Trigger: "
                        + trigger);
            } 
            else if (type.equals(MonitorNotification.STRING_TO_COMPARE_VALUE_DIFFERED)) {
                trace("STRING DIFFERS: Attribute: " + attribute + ", Object: "
                        + obsObj + ", Derived Gauge: " + derivedGauge + ", Trigger: "
                        + trigger);
            } 
            else if (type.equals(MonitorNotification.STRING_TO_COMPARE_VALUE_MATCHED)) {
                trace("STRING MATCHES: Attribute: " + attribute + ", Object: "
                        + obsObj + ", Derived Gauge: " + derivedGauge + ", Trigger: "
                        + trigger);
            } 
            else if (type.equals(MonitorNotification.OBSERVED_ATTRIBUTE_ERROR)) {
                trace("ATTRIBUTE ERROR (" + attribute + "): " + notif.getMessage());
            } 
            else if (type.equals(MonitorNotification.OBSERVED_ATTRIBUTE_TYPE_ERROR)) {
                trace("ATTRIBUTE TYPE ERROR (" + attribute + "): " + notif.getMessage());
            } 
            else if (type.equals(MonitorNotification.OBSERVED_OBJECT_ERROR)) {
                trace("OBJECT ERROR (" + obsObj + "): " + notif.getMessage());
            } 
            else if (type.equals(MonitorNotification.RUNTIME_ERROR)) {
                trace("RUNTIME ERROR (" + obsObj + "): " + notif.getMessage());
            } 
            else if (type.equals(MonitorNotification.THRESHOLD_ERROR)) {
                trace("THRESHOLD ERROR (" + obsObj + "): " + notif.getMessage());
            }
        }
    }

    private boolean isTraceOn () {
        return  _traceOn;
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
}




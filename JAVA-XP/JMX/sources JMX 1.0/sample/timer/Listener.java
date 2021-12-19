package  sample.timer;

import  javax.management.*;
import  javax.management.timer.*;
import  java.util.Properties;
import  java.util.Date;
import  sample.utility.*;


public class Listener
        implements javax.management.NotificationListener {
    private boolean _traceOn = true;

    /** Creates new Listener */
    public Listener () {
    }

    public void handleNotification (Notification notification, Object obj) {
        String type = notification.getType();
        if (notification instanceof TimerNotification) {
            TimerNotification notif = (TimerNotification)notification;
            if (type.equals(Controller.CONTROLLER_START)) {
                Properties props = (Properties)notif.getUserData();
                final String cwf = (String)props.getProperty("controller.consumer.workFactor");
                final String swf = (String)props.getProperty("controller.supplier.workFactor");
                trace("Listener.handleNotification(): INFO: " + "Controller starting at "
                        + new Date() + ", parameters = {" + cwf + ", " + swf
                        + "}");
                //
                // Start the controller on a thread other than the
                /// one in the timer that is invoking this method.
                /// Otherwise no other notifications can be sent
                /// because of the join() calls inside the controller.
                //
                Thread t = new Thread(new Runnable() {

                    /**
                     * put your documentation comment here
                     */
                    public void run () {
                        Controller.main(new String[] {
                            cwf, swf
                        });
                    }
                });
                t.start();
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
    MessageLog _logger = MessageLogQueue.instance();

    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
}
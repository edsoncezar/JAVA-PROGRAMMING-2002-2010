package  sample.utility;

import  java.util.ArrayList;
import  java.util.Iterator;
import  java.util.Date;
import  javax.management.*;
import  javax.management.timer.*;
import  sample.utility.*;


/**
 * This is a log class that implements the NotificationListener
 * interface. May be a bit lame, but shows how an asynchronous log
 * facility can be constructed using JMX.
 */
public class MessageLogQueue extends MessageLog
        implements NotificationListener {
    public static final String FLUSH_LOG = "sample.timer.flushlog";
    private ArrayList _store = new ArrayList(10);

    private MessageLogQueue () {
        super(INCLUDE_THREAD_INFO);
    }
    private static MessageLogQueue _instance = null;

    public static MessageLogQueue instance () {
        if (_instance == null)
            _instance = new MessageLogQueue();
        return  _instance;
    }

    public synchronized void write (String message) {
        _store.add(message);
    }

    public synchronized void write (Throwable t) {
        _store.add(t);
    }

    public synchronized void handleNotification (Notification notification, 
            Object obj) {
        if (notification instanceof TimerNotification) {
            String type = notification.getType();
            System.out.println("MessageLogQueue.handleNotification(): INFO: "
                    + "Received a " + type + " notification.");
            if (type.equals(MessageLogQueue.FLUSH_LOG)) {
                if (_store.size() > 0) {
                    super.write("--- " + new Date() + " ---");
                    Iterator iter = _store.iterator();
                    while (iter.hasNext()) {
                        Object message = iter.next();
                        if (message instanceof String)
                            super.write((String)message); 
                        else if (message instanceof Throwable)
                            super.write((Throwable)message);
                    }
                    _store.clear();
                }
            }
        } 
        else 
            throw  new RuntimeException("MessageLogQueue.handleNotification(): ERROR: "
                    + "Only TimerNotification type supported. Received: " + 
                    notification.getClass().getName());
    }
}
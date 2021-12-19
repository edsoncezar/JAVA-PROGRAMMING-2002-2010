import java.beans.beancontext.*;
import java.io.*;
import java.util.*;

/**
 * A JavaBean that encapsulates a text file. When added to a bean context,
 * this bean listens for a WordCount service to become available. When
 * the service does become available, the DocumentBean requests an 
 * instance of the service. The service then counts the number of words in the file,
 * and prints a report to standard output.
 */
public final class DocumentBean extends BeanContextChildSupport {

    private File document; 
    private BeanContextServices context;

    /**
     * Creates a new DocumentBean given the name of the file to read from.
     * @param fileName the name of the file to read from
     */
    public DocumentBean(String fileName) {
        document = new File(fileName);
    }

    /**
     * Called when this bean detects that a new service
     * has been registered with its context.
     *
     * @param bcsae the BeanContextServiceAvailableEvent
     */
    public void serviceAvailable(BeanContextServiceAvailableEvent bcsae) {
        System.out.println("[Detected a service being added to the context]");

        // Get a reference to the context
        BeanContextServices context = bcsae.getSourceAsBeanContextServices();
        System.out.println("Is the context offering a WordCount service? "
                           + context.hasService(WordCount.class)); 

        // Use the service, if it's available
        if (context.hasService(WordCount.class)) {        
            System.out.println("Attempting to use the service...");
            try {
                WordCount service = (WordCount)context.getService(this, this,
		                                           WordCount.class, document, this);
                System.out.println("Got the service!");
                service.countWords();
            } catch(Exception e) { }
        }        
    }

    /**
     * Called when this bean detects that a service 
     * has been revoked from the context.
     *
     * @param bcsre the BeanContextServiceRevokedEvent
     */
    public void serviceRevoked(BeanContextServiceRevokedEvent bcsre) {
        System.out.println("[Detected a service being revoked from the context]");
    }
}

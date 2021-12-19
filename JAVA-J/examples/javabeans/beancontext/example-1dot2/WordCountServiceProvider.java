import java.beans.beancontext.*;
import java.util.*;
import java.io.*;

/**
 * This class is the factory that delivers a word counting service.
 * The 3 methods defined in this class are the concrete implementations
 * of the BeanContextServiceProvider interface. For this demonstration, the primary
 * method of interest is getService(). The getService() methods returns a new
 * WordCount instance. It is called by the bean context when a nested
 * JavaBean requests the service.
 */
public final class WordCountServiceProvider implements BeanContextServiceProvider {

    public Object getService(BeanContextServices bcs, 
                             Object requestor,
                             Class serviceClass,
                             Object serviceSelector) {

        // For this demo, we know that the cast from serviceSelector
        // to File will always work.
        final File document = (File)serviceSelector;

        /*  Return an instance of the service. The service itself is 
         *  the WordCount interface, which is implemented here using 
         *  an anonymous inner class.
         */
        return new WordCount() {
            public void countWords() {
                try {
                    // Create a Reader to the DocumentBean's File
                    BufferedReader br = new BufferedReader(new FileReader(document));
                    String line = null;
                    int wordCount = 0;
                    while ((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            System.out.println("Word " + (++wordCount)
                                               + " is: " + st.nextToken());
                        }
                    }
                    System.out.println("Total number of words in the document: "
                                       + wordCount);
                    System.out.println("[WordCount service brought to you by WordCountServiceProvider]");                
                    br.close();
                 } catch(Exception e) { }
            }
        };
    }

    public void releaseService(BeanContextServices bcs,
                               Object requestor,
                               Object service) {
        // do nothing
    }

    public Iterator getCurrentServiceSelectors(BeanContextServices bcs, Class serviceClass) {
        return null; // do nothing
    }
}

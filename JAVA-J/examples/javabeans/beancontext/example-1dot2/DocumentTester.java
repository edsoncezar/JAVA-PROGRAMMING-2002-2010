import java.beans.beancontext.*;
import java.util.*;

/**
 * A test program that creates all of the objects,
 * a tests the service capabilities. Run this program
 * from the command line using java DocumentTester
 */
public class DocumentTester {

     public static void main(String[] args) {       
          BeanContextServicesSupport context = new BeanContextServicesSupport(); // a bean context
          DocumentBean doc1 = new DocumentBean("Test.txt"); 
          context.add(doc1);
          context.addBeanContextServicesListener(doc1); // listen for new services
          WordCountServiceProvider provider = new WordCountServiceProvider();
          context.addService(WordCount.class, provider); // add the service to the context
     }
}

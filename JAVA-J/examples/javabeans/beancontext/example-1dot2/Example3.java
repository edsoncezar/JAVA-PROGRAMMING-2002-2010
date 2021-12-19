import java.beans.beancontext.*;
import java.io.*;

/**
 * An example of how to use the instantiateChild() convenience method
 * to create a bean automatically nested into a bean context.
 */
public class Example3 {
    public static void main(String[] args) {
        BeanContextSupport context = new BeanContextSupport();
        System.out.println("Number of children nested into the context: " + context.size());

        BeanContextChildSupport child = null;
        try {
            child = (BeanContextChildSupport)context.instantiateChild("java.beans.beancontext.BeanContextChildSupport");
        }
        catch(IOException e){
            System.out.println("IOException occurred: " + e.getMessage());
        }
        catch(ClassNotFoundException e){
            System.out.println("Class not found: " + e.getMessage());
        }
        System.out.println("Number of children nested into the context: " + context.size());
    }
}

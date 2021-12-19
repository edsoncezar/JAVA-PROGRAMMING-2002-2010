import java.beans.beancontext.*;

/**
 * Test program that adds 100 beans to a context,
 * and calls size() to report the number of beans
 * currently nested. Finally,
 * this test calls toArray() to get references to
 * all child beans. 
 */
public class Example2 {
    public static void main(String[] args) {

        // A BeanContext 
        BeanContextSupport context = new BeanContextSupport(); 

        // Many JavaBeans
        BeanContextChildSupport[] beans = new BeanContextChildSupport[100];

        System.out.println("Number of children in the context: " + context.size());

        // Create the beans and add them to the context
        for (int i = 0; i < beans.length; i++) {
            beans[i] = new BeanContextSupport();
            context.add(beans[i]);
        }
        System.out.println("Number of children in the context: " + context.size());

        // Context now has 100 beans in it, get references to them all
        Object[] children = context.toArray();
        System.out.println("Number of objects retrieved from the context: " + children.length);
    }
}

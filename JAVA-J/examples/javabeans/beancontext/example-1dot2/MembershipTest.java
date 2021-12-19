import java.beans.beancontext.*;

/**
 * A simple test program to illustrate delivery
 * of the BeanContextMembershipEvent.
 */
public class MembershipTest {
    public static void main(String[] args) {
        BeanContextSupport context = new BeanContextSupport(); // the context
        MyMembershipListener listener = new MyMembershipListener(); 
        BeanContextChildSupport bean = new BeanContextChildSupport(); // a JavaBean
        context.addBeanContextMembershipListener(listener); // now listening!
        context.add(bean);
        context.remove(bean);
    }
}

/**
 * A custom implementation of the BeanContextMembershipListener interface.
 */
class MyMembershipListener implements BeanContextMembershipListener {
    public void childrenAdded(BeanContextMembershipEvent bcme) {
        System.out.println("Another bean has been added to the context.");
    }

    public void childrenRemoved(BeanContextMembershipEvent bcme) {
        System.out.println("A bean has been removed from the context.");
    }
}

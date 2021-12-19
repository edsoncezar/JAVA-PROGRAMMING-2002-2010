import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import com.wrox.store.ejb.product.*;

/**
 * Simple test client to check whether the beans have been properly deployed.
 *
 * @author    Simon Brown
 */
public class Test {

  /**
   * Application entry point.
   *
   * @param args    the array of command line arguments
   */
  public static void main(String[] args) {

    // setup the JNDI properties
    System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
    System.setProperty("java.naming.provider.url", "localhost:1099");

    try {
      // look up the product home
      InitialContext ctx = new InitialContext();
      ProductHome home = (ProductHome)PortableRemoteObject.narrow (ctx.lookup("store/ProductHome"), ProductHome.class);

      // display all the products that are known to the system
      Product product;
      Collection coll = home.findAll();
      Iterator it = coll.iterator();
      while (it.hasNext()) {
        product = (Product)it.next();
        System.out.println(product.getName() + " - " + product.getPrice());
      }
    } catch (NamingException ne) {
      System.out.println(ne.getMessage());
    } catch (FinderException fe) {
      System.out.println(fe.getMessage());
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    }
  }

}
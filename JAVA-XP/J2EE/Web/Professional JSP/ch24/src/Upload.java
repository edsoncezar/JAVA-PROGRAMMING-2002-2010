import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import com.wrox.store.ejb.product.*;

/**
 * Simple test client to upload sample data and check whether the beans
 * have been properly deployed.
 *
 * @author    Simon Brown
 */
public class Upload {

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
      // lookup the home
      InitialContext ctx = new InitialContext();
      ProductHome home = (ProductHome)PortableRemoteObject.narrow (ctx.lookup("store/ProductHome"), ProductHome.class);
      Product product;

      // remove all of the existing products
      Collection coll = home.findAll();
      Iterator it = coll.iterator();
      while (it.hasNext()) {
        product = (Product)it.next();
        product.remove();
      }

      System.out.println("Removed all existing products.");

      // and create some sample products
      product = home.create("1");
      product.setName("Professional JSP");
      product.setPrice(43.99);

      product = home.create("2");
      product.setName("Professional Java Server Programming J2EE Edition ");
      product.setPrice(46.99);

      product = home.create("3");
      product.setName("Professional Java Programming");
      product.setPrice(45.99);

      System.out.println("Added products.");

      // ... finally, display them to make sure they got inserted okay
      coll = home.findAll();
      it = coll.iterator();
      while (it.hasNext()) {
        product = (Product)it.next();
        System.out.println(product.getName() + " - " + product.getPrice());
      }
    } catch (NamingException ne) {
      System.out.println(ne.getMessage());
    } catch (FinderException fe) {
      System.out.println(fe.getMessage());
    } catch (RemoveException rve) {
      System.out.println(rve.getMessage());
    } catch (CreateException ce) {
      System.out.println(ce.getMessage());
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    }
  }

}
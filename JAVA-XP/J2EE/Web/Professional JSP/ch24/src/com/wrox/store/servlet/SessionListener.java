package com.wrox.store.servlet;

import java.rmi.RemoteException;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.servlet.*;
import javax.servlet.http.*;

import com.wrox.store.ejb.cart.*;

/**
 * Used in our web application to listen for when sessions are created
 * and destroyed by the web container.
 *
 * @author    Simon Brown
 */
public class SessionListener implements HttpSessionListener {

  /**
   * Called upon start-up to set the appropriate naming properties.
   *
   * This is fine for this small example but not where different naming providers
   * may be used in the same JVM.
   */
  static {
    // setup the JNDI properties
    System.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
    System.setProperty("java.naming.provider.url", "localhost:1099");
  }

  /**
   * Called when the HTTP session is destoyed.
   *
   * @param e   the HttpSessionEvent signifying this
   */
  public void sessionCreated(HttpSessionEvent e) {
    Cart cart;

    try {
      // create a context and peform the JNDI lookup
      InitialContext ctx = new InitialContext();
      CartHome cartHome = (CartHome)PortableRemoteObject.narrow(ctx.lookup("store/CartHome"), CartHome.class);

      // create a cart and bind it to the session
      cart = cartHome.create();
      e.getSession().setAttribute("cart", cart);
    } catch (CreateException ce) {
      System.out.println(ce.getMessage());
    } catch (NamingException ne) {
      System.out.println(ne.getMessage());
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    }
  }

  /**
   * Called when the HTTP session is destoyed.
   *
   * @param e   the HttpSessionEvent signifying this
   */
  public void sessionDestroyed(HttpSessionEvent e) {
      try {
      // get the existing cart out of the session
      Cart cart = (Cart)e.getSession().getAttribute("cart");

      if (cart != null) {
        // and remove it from the EJB container
        cart.remove();
      }
    } catch (RemoveException rve) {
      System.out.println(rve.getMessage());
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    }
  }

}
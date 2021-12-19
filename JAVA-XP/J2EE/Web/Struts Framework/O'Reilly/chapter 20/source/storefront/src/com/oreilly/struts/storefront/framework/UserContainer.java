package com.oreilly.struts.storefront.framework;
import java.util.Locale;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionBindingEvent;
import com.oreilly.struts.storefront.customer.view.UserView;

/**
 * Used to store information about a specific user. This class is used
 * so that the information is not scattered throughout the HttpSession.
 * Only this object is stored in the session for the user. This class
 * implements the HttpSessionBindingListener interface so that it can
 * be notified of session timeout and perform the proper cleanup.
 */
public class UserContainer implements HttpSessionBindingListener {
  // The user's shopping cart
  private ShoppingCart cart = null;
  //  Data about the user that is cached
  private UserView userView = null;
  /**
   * The Locale object for the user. Although Struts stores a Locale for
   * each user in the session, the locale is also maintained here.
   */
  private Locale locale;

  /**
   * Default Constructor
   */
  public UserContainer() {
    super();
    initialize();
  }

  /**
   * The container calls this method when it is being unbound from the
   * session.
   */
  public void valueUnbound(HttpSessionBindingEvent event) {
    // Perform resource cleanup
    cleanUp();
  }

  public ShoppingCart getCart() {
    return cart;
  }

  public void setCart(ShoppingCart newCart) {
    cart = newCart;
  }

  /**
   * Set the locale for the user.
   */
  public void setLocale(Locale aLocale) {
    locale = aLocale;
  }

  /**
   * Retrieve the locale for the user.
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * The container calls this method when it is being bound to the
   * session.
   */
  public void valueBound(HttpSessionBindingEvent event) {
  // Don't need to do anything, but still have to implement the
  // interface method.
  }

  public UserView getUserView() {
    return userView;
  }

  public void setUserView(UserView newView) {
    userView = newView;
  }

  /**
   * Initialize all of the required resources
   */
  private void initialize() {
    // Create a new Shopping cart for this user
    cart = new ShoppingCart();
  }

  /**
   * Clean up any open resources. The shopping cart is left intact
   * intentionally.
   */
  public void cleanUp() {
    setUserView( null );
  }
}
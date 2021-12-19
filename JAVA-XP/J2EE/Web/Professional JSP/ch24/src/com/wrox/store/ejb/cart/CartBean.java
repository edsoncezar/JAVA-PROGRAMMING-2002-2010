package com.wrox.store.ejb.cart;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import com.wrox.store.ejb.customer.Customer;
import com.wrox.store.ejb.mail.*;
import com.wrox.store.ejb.product.Product;
import com.wrox.store.exception.*;

/**
 * Our implementation of the Cart interface as a session bean.
 *
 * @author    Simon Brown
 */
public class CartBean extends com.wrox.store.ejb.AbstractSessionBean {

  /** the collection of products in the cart */
  public HashMap products = new HashMap();

  /**
   * Adds the specified quantity of the supplied product to the cart.
   *
   * @param product     the Product to be added to the cart
   * @param newQuantity   the quantity of the product to be added
   */
  public void add(Product product, int newQuantity) throws RemoteException {
    int existingQuantity = getQuantity(product);

    products.put(product, new Integer(existingQuantity + newQuantity));
  }

  /**
   * Gets a reference to the collection of products in the cart.
   *
   * @return  a Collection of Product instances (or references)
   */
  public Collection getProducts() throws RemoteException {
    return new ArrayList(products.keySet());
  }

  /**
   * Determines how many of the specified product are in the cart - if any.
   *
   * @param product   the Product to find
   * @return  an int representing the quantity in the cart
   */
  public int getQuantity(Product product) throws RemoteException {
    Integer quantity = (Integer)products.get(product);

    if (quantity == null) {
      return 0;
    } else {
      return quantity.intValue();
    }
  }

  /**
   * Gets the total price for this order.
   *
   * @return  a double representing the total price
   */
  public double getTotal() throws RemoteException {
    double total = 0.0;
    Iterator it = getProducts().iterator();
    Product product;

    while (it.hasNext()) {
      product = (Product)it.next();

      total += (product.getPrice() * getQuantity(product));
    }

    return total;
  }

  /**
   * Submits the contents of this cart to be purchased. This involves a number
   * of steps including :
   * <OL>
   * <LI>authorising the credit card</LI>
   * <LI>sending a message to the fulfillment centre</LI>
   * <LI>sending a confirmation e-mail to the customer</LI>
   * </OL>
   *
   * @param customer    the Customer that this cart belongs to
   * @throws  AuthorizationException    if the credit card details were
   *                    incorrect or authorization was not given
   */
  public void submitOrder(Customer customer) throws AuthorizationException, RemoteException {
    // ask CreditCardAuthorization stateless session bean to authorize
    // credit card information

    // this stateless session bean would throw an AuthorizationException
    // if the credit card details were incorrect or the card was
    // not authorized

    // send a message (again through a stateless session bean) to the
    // fulfillment provider using JMS
    // this could be an XML message containing a list of the products,
    // quantities and the customer's name and address

    // send the user an e-mail with the contents of their order
    try {
      // look up the product home
      InitialContext ctx = new InitialContext();
      MailHome home = (MailHome)PortableRemoteObject.narrow (ctx.lookup("store/MailHome"), MailHome.class);

      // create a mail bean and send the e-mail
      Mail mail = home.create();
      mail.sendConfirmation(customer);
    } catch (NamingException ne) {
      System.out.println(ne.getMessage());
    } catch (CreateException ce) {
      System.out.println(ce.getMessage());
    } catch (RemoteException re) {
      System.out.println(re.getMessage());
    }

    // and empty the cart
    products.clear();
  }

}
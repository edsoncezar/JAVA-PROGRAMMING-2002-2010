package com.wrox.store.ejb.cart;

import java.rmi.RemoteException;
import java.util.*;

import com.wrox.store.ejb.customer.Customer;
import com.wrox.store.ejb.product.Product;
import com.wrox.store.exception.*;

/**
 * This represents the business interface for a shopping cart in
 * our on-line store.
 *
 * @author    Simon Brown
 */
public interface Cart extends javax.ejb.EJBObject {

  /**
   * Adds the specified quantity of the supplied product to the cart.
   *
   * @param product     the Product to be added to the cart
   * @param newQuantity   the quantity of the product to be added
   */
  public void add(Product product, int newQuantity)
    throws RemoteException;

  /**
   * Gets a reference to the collection of products in the cart.
   *
   * @return  a Collection of Product instances (or references)
   */
  public Collection getProducts() throws RemoteException;

  /**
   * Determines how many of the specified product are in the cart - if any.
   *
   * @param product   the Product to find
   * @return  an int representing the quantity in the cart
   */
  public int getQuantity(Product product)
    throws RemoteException;

  /**
   * Gets the total price for this order.
   *
   * @return  a double representing the total price
   */
  public double getTotal() throws RemoteException;

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
  public void submitOrder(Customer customer)
    throws AuthorizationException, RemoteException;

}
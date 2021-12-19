package com.wrox.store.ejb.cart;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * The home interface for cart instances in our on-line
 * shopping example. This interface only allows new carts
 * to be created.
 *
 * @author    Simon Brown
 */
public interface CartHome extends javax.ejb.EJBHome {

  /**
   * Creates a new Cart instance.
   *
   * @return    the new instance
   */
  public Cart create() throws CreateException, RemoteException;

}
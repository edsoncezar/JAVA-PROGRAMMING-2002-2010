package com.wrox.store.ejb.product;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * The home interface for creating and looking up instances of
 * the products in the system.
 *
 * @author    Simon Brown
 */
public interface ProductHome extends javax.ejb.EJBHome {

  /**
   * Creates a new product with the specified id.
   *
   * @param id    the primary key of the new product
   */
  public Product create(String id)
    throws CreateException, RemoteException;

  /**
   * Finds a product with the specified id.
   *
   * @param id    the primary key
   * @return  the corresponding Product instance
   */
  public Product findByPrimaryKey(String id)
    throws FinderException, RemoteException;

  /**
   * Gets a collection containing references to all the
   * products in the system.
   *
   * @return  a Collection of Product references
   */
  public Collection findAll()
    throws RemoteException, FinderException;

}
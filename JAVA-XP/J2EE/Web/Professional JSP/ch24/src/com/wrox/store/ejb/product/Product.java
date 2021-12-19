package com.wrox.store.ejb.product;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * This represents the business interface for a product in our on-line store.
 *
 * @author    Simon Brown
 */
public interface Product extends EJBObject {

  /**
   * Gets the id of this product. This is the primary key.
   *
   * @return  the id
   */
  public String getId() throws RemoteException;

  /**
   * Gets the name of this product.
   *
   * @return  the name
   */
  public String getName() throws RemoteException;

  /**
   * Sets the name of this product.
   *
   * @param newName   the new name of this product
   */
  public void setName(String newName) throws RemoteException;

  /**
   * Gets the price of this product.
   *
   * @return  the price as a double
   */
  public double getPrice() throws RemoteException;

  /**
   * Sets the price of this product.
   *
   * @param newPrice    the new price of this product
   */
  public void setPrice(double newPrice) throws RemoteException;

}
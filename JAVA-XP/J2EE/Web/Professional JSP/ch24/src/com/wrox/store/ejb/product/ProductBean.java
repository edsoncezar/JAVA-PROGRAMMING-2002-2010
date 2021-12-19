package com.wrox.store.ejb.product;

import java.rmi.RemoteException;

/**
 * The implementation of our product entity bean.
 *
 * @author    Simon Brown
 */
public class ProductBean extends com.wrox.store.ejb.AbstractEntityBean {

  /** the id of this product */
  public String id;

  /** the name of this product */
  public String name;

  /** the price of this product */
  public double price;

  /**
   * Called to create this bean.
   *
   * @param id    the primary key (id) of this product
   * @return  the primary key
   */
  public String ejbCreate(String id) {
    this.id = id;

    // return null as creation is performed by the container
    return null;
  }

  /**
   * Called after the bean has been created.
   *
   * @param id    the primary key (id) of this product
   */
  public void ejbPostCreate(String id) {
    // do nothing here
  }

  /**
   * Gets the id of this product.
   *
   * @return  the id
   */
  public String getId() throws RemoteException {
    return this.id;
  }

  /**
   * Gets the name of this product.
   *
   * @return  the name
   */
  public String getName() throws RemoteException {
    return this.name;
  }

  /**
   * Sets the name of this product.
   *
   * @param newName   the new name of this product
   */
  public void setName(String newName) throws RemoteException {
    this.name = newName;
  }

  /**
   * Gets the price of this product.
   *
   * @return  the price as a double
   */
  public double getPrice() throws RemoteException {
    return this.price;
  }

  /**
   * Sets the price of this product.
   *
   * @param newPrice    the new price of this product
   */
  public void setPrice(double newPrice) throws RemoteException {
    this.price = newPrice;
  }

}
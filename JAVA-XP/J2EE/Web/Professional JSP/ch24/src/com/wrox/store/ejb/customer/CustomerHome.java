package com.wrox.store.ejb.customer;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * The home interface for creating customer objects. The only
 * functionality this provides is to create new customers.
 * <p>
 * A full implementation might lookup customers by their
 * login id for example.
 *
 * @author    Simon Brown
 */
public interface CustomerHome extends javax.ejb.EJBHome {

  /**
   * Creates a new customer instance.
   *
   * @return  the new instance
   */
  public Customer create() throws CreateException, RemoteException;

}
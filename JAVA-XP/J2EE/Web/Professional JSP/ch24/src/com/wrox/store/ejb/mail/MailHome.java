package com.wrox.store.ejb.mail;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

/**
 * The home interface for mail bean instances in our on-line
 * shopping example. This interface only allows us to create
 * new mail session beans.
 *
 * @author    Simon Brown
 */
public interface MailHome extends javax.ejb.EJBHome {

  /**
   * Creates a new Mail instance.
   *
   * @return    the new instance
   */
  public Mail create() throws CreateException, RemoteException;

}
package com.wrox.store.ejb.mail;

import java.rmi.RemoteException;

import com.wrox.store.ejb.customer.Customer;

/**
 * The interface for a JavaMail wrapper used to send confirmation e-mails to
 * customers.
 *
 * @author    Simon Brown
 */
public interface Mail extends javax.ejb.EJBObject {

  /**
   * Sends an e-mail to the customer as a confirmation of their order.
   *
   * @param customer    the Customer
   */
  public void sendConfirmation(Customer customer)
    throws RemoteException;

}
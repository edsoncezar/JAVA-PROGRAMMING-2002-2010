package com.wrox.store.ejb.customer;

import java.rmi.RemoteException;

import com.wrox.store.exception.*;

/**
 * This represents the business interface for a customer in
 * our on-line store.
 *
 * @author    Simon Brown
 */
public interface Customer extends javax.ejb.EJBObject {

  /**
   * Gets the name of the customer.
   *
   * @return  the name of the customer
   */
  public String getName() throws RemoteException;
  /**
   * Sets the name of the customer.
   *
   * @param newName   the new name of the customer
   */
  public void setName(String newName) throws RemoteException;

  /**
   * Gets the address of the customer.
   *
   * @return    the address of the customer
   */
  public String getAddress() throws RemoteException;

  /**
   * Sets the address of the customer.
   *
   * @param newAddress    the new address of the customer
   */
  public void setAddress(String newAddress) throws RemoteException;

  /**
   * Gets the zip code of the customer.
   *
   * @return    the zip code of the customer
   */
  public String getZipCode() throws RemoteException;

  /**
   * Sets the zip code for the customer.
   *
   * @param newZipCode    the new zip code of the customer
   */
  public void setZipCode(String newZipCode) throws RemoteException;

  /**
   * Gets the e-mail address of the customer.
   *
   * @return  the e-mail address of the customer
   */
  public String getEmailAddress() throws RemoteException;

  /**
   * Sets the e-mail address of the customer.
   *
   * @param newEmailAddress   the new e-mail address of the customer
   */
  public void setEmailAddress(String newEmailAddress) throws RemoteException;

  /**
   * Gets the credit card number of the customer.
   *
   * @return    the credit card number
   */
  public String getCreditCardNumber() throws RemoteException;

  /**
   * Sets the credit card number of the customer.
   *
   * @param newCreditCardNumber   the new credit card number
   */
  public void setCreditCardNumber(String newCreditCardNumber) throws RemoteException;

}
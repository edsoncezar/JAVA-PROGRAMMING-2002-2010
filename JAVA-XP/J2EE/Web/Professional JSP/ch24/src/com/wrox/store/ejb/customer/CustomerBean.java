package com.wrox.store.ejb.customer;

import java.rmi.RemoteException;

import com.wrox.store.exception.*;

/**
 * This represents a customer in our on-line shopping example. For the
 * sake of this example, this is implemented as a stateful session bean.
 * In a real application though, this would probably be an entity bean
 * because the customer information would be persisted in some way.
 *
 * @author    Simon Brown
 */
public class CustomerBean extends com.wrox.store.ejb.AbstractSessionBean {

  /** the name of the customer */
  public String name;

  /** the address of the customer */
  public String address;

  /** the zipCode of the customer */
  public String zipCode;

  /** the e-mail address of the customer */
  public String emailAddress;

  /** the credit card number of the customer */
  public String creditCardNumber;

  /**
   * Gets the name of the customer.
   *
   * @return  the name of the customer
   */
  public String getName() throws RemoteException {
    return this.name;
  }

  /**
   * Sets the name of the customer.
   *
   * @param newName   the new name of the customer
   */
  public void setName(String newName) throws RemoteException {
    this.name = newName;
  }

  /**
   * Gets the address of the customer.
   *
   * @return    the address of the customer
   */
  public String getAddress() throws RemoteException {
    return this.address;
  }

  /**
   * Sets the address of the customer.
   *
   * @param newAddress    the new address of the customer
   */
  public void setAddress(String newAddress) throws RemoteException {
    this.address = newAddress;
  }

  /**
   * Gets the zip code of the customer.
   *
   * @return    the zip code of the customer
   */
  public String getZipCode() throws RemoteException {
    return this.zipCode;
  }

  /**
   * Sets the zip code for the customer.
   *
   * @param newZipCode    the new zip code of the customer
   */
  public void setZipCode(String newZipCode) throws RemoteException {
    this.zipCode = newZipCode;
  }

  /**
   * Gets the e-mail address of the customer.
   *
   * @return  the e-mail address of the customer
   */
  public String getEmailAddress() throws RemoteException {
    return this.emailAddress;
  }

  /**
   * Sets the e-mail address of the customer.
   *
   * @param newEmailAddress   the new e-mail address of the customer
   */
  public void setEmailAddress(String newEmailAddress) throws RemoteException {
    this.emailAddress = newEmailAddress;
  }

  /**
   * Gets the credit card number of the customer.
   *
   * @return    the credit card number
   */
  public String getCreditCardNumber() throws RemoteException {
    return this.creditCardNumber;
  }

  /**
   * Sets the credit card number of the customer.
   *
   * @param newCreditCardNumber   the new credit card number
   */
  public void setCreditCardNumber(String newCreditCardNumber) throws RemoteException {
    this.creditCardNumber = newCreditCardNumber;
  }

}
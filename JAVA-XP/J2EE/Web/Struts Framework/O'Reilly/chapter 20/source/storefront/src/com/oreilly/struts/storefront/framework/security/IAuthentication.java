package com.oreilly.struts.storefront.framework.security;

import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.framework.exceptions.InvalidLoginException;
import com.oreilly.struts.storefront.framework.exceptions.ExpiredPasswordException;
import com.oreilly.struts.storefront.framework.exceptions.AccountLockedException;
import com.oreilly.struts.storefront.framework.exceptions.DatastoreException;
/**
 * Defines the security methods for the system.
 */
public interface IAuthentication {

  /**
   * Log the user out of the system.
   */
  public void logout(String email);

  /**
   * Authenticate the user's credentials and either return a UserView for the
   * user or throw one of the security exceptions.
   */
  public UserView authenticate(String email, String password) throws
  InvalidLoginException,ExpiredPasswordException,AccountLockedException,DatastoreException;
}

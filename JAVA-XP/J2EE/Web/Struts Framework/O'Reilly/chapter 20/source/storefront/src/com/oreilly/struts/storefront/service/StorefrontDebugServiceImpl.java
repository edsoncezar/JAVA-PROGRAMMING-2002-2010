package com.oreilly.struts.storefront.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.catalog.view.ItemSummaryView;
import com.oreilly.struts.storefront.framework.security.IAuthentication;
import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.businessobjects.*;
// Import the exceptions used
import com.oreilly.struts.storefront.framework.exceptions.DatastoreException;
import com.oreilly.struts.storefront.framework.exceptions.InvalidLoginException;
import com.oreilly.struts.storefront.framework.exceptions.ExpiredPasswordException;
import com.oreilly.struts.storefront.framework.exceptions.AccountLockedException;


/**
 * This class is used as a debug version of the Storefront Service. It's
 * mainly used for testing the frontend without requiring a database or
 * an EJB application to be present. The data is hard-coded.
 */
public class StorefrontDebugServiceImpl  implements IStorefrontService {

  public StorefrontDebugServiceImpl(){
    super();
  }

  public List getFeaturedItems() throws DatastoreException{
    return new ArrayList();
  }

  public ItemDetailView getItemDetailView( String itemId )throws DatastoreException{
    return new ItemDetailView( "2", "Carburetor", "A really nice carburetor.", new Double(89.00), "../multimedia/carb.gif", "../multimedia/carb.gif", "Provides gas to your engine." );
  }

  public void logout(String email){
    // Do nothing for this example
  }

  /**
   * Authenticate the user's credentials and either return a UserView for the
   * user or throw one of the security exceptions.
   */
  public UserView authenticate(String email, String password) throws
    InvalidLoginException,ExpiredPasswordException,AccountLockedException,
    DatastoreException{
      UserView view = new UserView();
      view.setFirstName( "John" );
      view.setLastName( "Doe" );
      view.setEmailAddress( "" );
      view.setId( "13" );
      view.setTimeCreated( new Timestamp(System.currentTimeMillis()));
      return view;
    }

  public void destroy(){
    // Do nothing for this example
  }
}
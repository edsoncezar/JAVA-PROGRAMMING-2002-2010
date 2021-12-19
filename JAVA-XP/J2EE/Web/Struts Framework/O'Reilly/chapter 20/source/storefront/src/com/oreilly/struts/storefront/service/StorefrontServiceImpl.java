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

// Import the implementation specific packages
import org.odmg.*;
import ojb.odmg.*;

public class StorefrontServiceImpl implements IStorefrontService{

  // Implementation specific references
  Implementation odmg = null;
  Database db = null;

  /**
   * Create the service, which includes initializing the persistence
   * framework.
   */
  public StorefrontServiceImpl() throws DatastoreException {
    super();
    init();
  }
  /**
   * Return a list of items that are featured.
   */
  public List getFeaturedItems() throws DatastoreException {
    // Start a transaction
    Transaction tx = odmg.newTransaction();
    tx.begin();

    List results = null;
    try{
      OQLQuery query = odmg.newOQLQuery();
      // Set the OQL select statement
      query.create( "select featuredItems from " + ItemBO.class.getName() );
      results = (List)query.execute();
      tx.commit();
    }catch( Exception ex ){
      // Rollback the transaction
      tx.abort();
      ex.printStackTrace();
      throw DatastoreException.datastoreError(ex);
    }
    int size = results.size();
    List items = new ArrayList();
    for( int i = 0; i < size; i++ ){
      ItemBO itemBO = (ItemBO)results.get(i);
      ItemSummaryView newView = new ItemSummaryView();
      newView.setId( itemBO.getId().toString() );
      newView.setName( itemBO.getDisplayLabel() );
      newView.setUnitPrice( itemBO.getBasePrice() );
      newView.setSmallImageURL( itemBO.getSmallImageURL() );
      newView.setProductFeature( itemBO.getFeature1() );
      items.add( newView );
    }
    return items;
  }
  /**
   * Return an detailed view of an item based on the itemId argument.
   */
  public ItemDetailView getItemDetailView( String itemId )
    throws DatastoreException{
      // Start a transaction
      Transaction tx = odmg.newTransaction();
      tx.begin();

      List results = null;
      try{
        OQLQuery query = odmg.newOQLQuery();

        // Set the OQL select statement
        String queryStr = "select item from " + ItemBO.class.getName();
        queryStr += " where id = $1";
        query.create(queryStr);
        query.bind(itemId);

        // Execute the transaction
        results = (List)query.execute();
        tx.commit();
      }catch( Exception ex ){
        // Rollback the transaction
        tx.abort();
        ex.printStackTrace();
        throw DatastoreException.datastoreError(ex);
      }

      //
      if (results.isEmpty() ){
        throw DatastoreException.objectNotFound();
      }

      ItemBO itemBO = (ItemBO)results.get(0);

      // Build a ValueObject for the Item
      ItemDetailView view = new ItemDetailView();
      view.setId( itemBO.getId().toString() );
      view.setDescription( itemBO.getDescription() );
      view.setLargeImageURL( itemBO.getLargeImageURL() );
      view.setName( itemBO.getDisplayLabel() );
      view.setProductFeature( itemBO.getFeature1() );
      view.setUnitPrice( itemBO.getBasePrice() );
      view.setTimeCreated( new Timestamp(System.currentTimeMillis() ));
      view.setModelNumber( itemBO.getModelNumber() );
      return view;
  }

  /**
   * Authenticate the user's credentials and either return a UserView for the
   * user or throw one of the security exceptions.
   */
  public UserView authenticate(String email, String password) throws
    InvalidLoginException,ExpiredPasswordException,AccountLockedException,
    DatastoreException {

      // Start a transaction
      Transaction tx = odmg.newTransaction();
      tx.begin();

      // Query the database for a user that matches the credentials
      List results = null;
      try{
        OQLQuery query = odmg.newOQLQuery();
        // Set the OQL select statement
        String queryStr = "select customer from " + CustomerBO.class.getName();
        queryStr += " where email = $1 and password = $2";
        query.create(queryStr);

        // Bind the input parameters
        query.bind( email );
        query.bind( password );

        // Retrieve the results and commit the transaction
        results = (List)query.execute();
        tx.commit();
      }catch( Exception ex ){
        // Rollback the transaction
        tx.abort();
        ex.printStackTrace();
        throw DatastoreException.datastoreError(ex);
      }

      // If no results were found, must be an invalid login attempt
      if ( results.isEmpty() ){
        throw new InvalidLoginException();
      }

      // Should only be a single customer that matches the parameters
      CustomerBO customer  = (CustomerBO)results.get(0);

      // Make sure the account is not locked
      String accountStatusCode = customer.getAccountStatus();
      if ( accountStatusCode != null && accountStatusCode.equals( "L" ) ){
        throw new AccountLockedException();
      }

      // Populate the Value Object from the Customer business object
      UserView userView = new UserView();
      userView.setId( customer.getId().toString() );
      userView.setFirstName( customer.getFirstName() );
      userView.setLastName( customer.getLastName() );
      userView.setEmailAddress( customer.getEmail() );
      userView.setCreditStatus( customer.getCreditStatus() );

      return userView;
    }

  /**
   * Log the user out of the system.
   */
  public void logout(String email){
    // Do nothing with right now, but might want to log it for auditing reasons
  }

  public void destroy(){
    // Do nothing for this example
  }

  /**
   * Opens the database and prepares it for transactions
   */
  private void init() throws DatastoreException {
    // get odmg facade instance
    odmg = OJB.getInstance();
    db = odmg.newDatabase();
    //open database
    try{
      db.open("repository.xml", Database.OPEN_READ_WRITE);
    }catch( Exception ex ){
      throw DatastoreException.datastoreError(ex);
    }
  }
}
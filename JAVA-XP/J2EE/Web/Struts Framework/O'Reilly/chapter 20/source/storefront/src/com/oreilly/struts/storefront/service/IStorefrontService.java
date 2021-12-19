package com.oreilly.struts.storefront.service;

import java.util.List;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.catalog.view.ItemSummaryView;
import com.oreilly.struts.storefront.framework.exceptions.DatastoreException;
import com.oreilly.struts.storefront.framework.security.IAuthentication;
/**
 * The business interface for the Storefront Application. It defines all
 * of the methods that a client may call on the Storefront application.
 *
 * This interface extends the IAuthentication interface to create a
 * cohesive interface for the Storefront application.
 */
public interface IStorefrontService extends IAuthentication {

  public List getFeaturedItems() throws DatastoreException;

  public ItemDetailView getItemDetailView( String itemId )
    throws DatastoreException;

  public void destroy();
}
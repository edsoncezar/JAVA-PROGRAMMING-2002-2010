package com.oreilly.struts.storefront.framework;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import org.apache.struts.action.ActionServlet;
import com.oreilly.struts.storefront.service.IStorefrontServiceFactory;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.exceptions.DatastoreException;
/**
 * Extend the Struts ActionServlet to perform your own special
 * initialization.
 */
public class ExtendedActionServlet extends ActionServlet {

  public void init() throws ServletException {
    // Make sure to always call the super's init() first
    super.init();

    // Not performing any custom behavior yet!
  }
}
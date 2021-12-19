package com.oreilly.struts.storefront.framework;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.exceptions.*;
import com.oreilly.struts.storefront.service.IStorefrontService;
import com.oreilly.struts.storefront.service.IStorefrontServiceFactory;
/**
 * An abstract Action class that all store front action classes should
 * extend.
 */
abstract public class StorefrontBaseAction extends Action {
  protected IStorefrontService getStorefrontService(){
    IStorefrontServiceFactory factory = (IStorefrontServiceFactory)getApplicationObject( IConstants.SERVICE_FACTORY_KEY );
    return factory.createService();
  }

  /**
   * Retrieve a session object based on the request and the attribute name.
   */
  protected Object getSessionObject(HttpServletRequest req,
                                    String attrName) {
    Object sessionObj = null;

    // Don't create a session if one isn't already present
    HttpSession session = req.getSession(true);
    sessionObj = session.getAttribute(attrName);
    return sessionObj;
  }

  protected String getLoginToken(HttpSession session) {
    return (String)session.getAttribute(IConstants.LOGIN_TOKEN_KEY);
  }

  public boolean isLoggedIn( HttpServletRequest request ){
    UserContainer container = this.getUserContainer(request);
    return ( container != null && container.getUserView() != null );
  }
  /**
   * Return the instance of the ApplicationContainer object.
   */
  protected ApplicationContainer getApplicationContainer() {
    return (ApplicationContainer)getApplicationObject(IConstants.APPLICATION_CONTAINER_KEY);
  }

  protected void removeLoginToken(HttpSession session) {
    session.removeAttribute(IConstants.LOGIN_TOKEN_KEY);
  }

  /**
   * Retrieve the UserContainer for the user tier to the request.
   */
  protected UserContainer getUserContainer(HttpServletRequest request) {
    UserContainer userContainer = (UserContainer)getSessionObject(request, IConstants.USER_CONTAINER_KEY);

    // Create a UserContainer for the user if it doesn't exist already
    if(userContainer == null) {
      userContainer = new UserContainer();
      userContainer.setLocale(request.getLocale());
      HttpSession session = request.getSession();
      session.setAttribute(IConstants.USER_CONTAINER_KEY, userContainer);
    }
    return userContainer;
  }

  /**
   * Retrieve an object from the application scope by its name. This is
   * a convience method.
   */
  protected Object getApplicationObject(String attrName) {
    return servlet.getServletContext().getAttribute(attrName);
  }
  protected void setLoginToken(HttpSession session,
                               String path) {
    session.setAttribute(IConstants.LOGIN_TOKEN_KEY, path);
  }
}

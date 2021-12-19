package com.oreilly.struts.storefront.framework;

import javax.servlet.http.*;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;
import com.oreilly.struts.storefront.service.IStorefrontServiceFactory;
import org.apache.struts.actions.DispatchAction;

public class StorefrontDispatchAction extends DispatchAction {

  protected IStorefrontService getStorefrontService(){
    IStorefrontServiceFactory factory = (IStorefrontServiceFactory)getApplicationObject( IConstants.SERVICE_FACTORY_KEY );
    return factory.createService();
  }

  /**
   * Retrieve an object from the application scope by its name. This is
   * a convience method.
   */
  protected Object getApplicationObject(String attrName) {
    return servlet.getServletContext().getAttribute(attrName);
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
}
package com.oreilly.struts.storefront.security;

import java.util.Locale;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.exceptions.InvalidLoginException;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;

/**
 * Implements the logic to authenticate a user for the storefront application.
 */
public class LoginAction extends StorefrontBaseAction {
  /**
   * Called by the controller when the a user attempts to login to the
   * storefront application.
   */

  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
  throws Exception{

    // Get the user's login name and password. They should have already
    // validated by the ActionForm.
    String email = ((LoginForm)form).getEmail();
    String password = ((LoginForm)form).getPassword();

    // Login through the security service
    IStorefrontService serviceImpl = getStorefrontService();
    UserView userView = serviceImpl.authenticate(email, password);

    UserContainer existingContainer = null;
    HttpSession session = request.getSession(false);
    if ( session != null ){
      existingContainer = getUserContainer(request);
      session.invalidate();
    }else{
      existingContainer = new UserContainer();
    }

    // Create a new session for the user
    session = request.getSession(true);
    existingContainer.setUserView(userView);
    session.setAttribute(IConstants.USER_CONTAINER_KEY, existingContainer);

    return mapping.findForward(IConstants.SUCCESS_KEY);
  }
}
package com.oreilly.struts.storefront.security;

import java.util.Locale;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.customer.view.UserView;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;

/**
 * This action is called when a user checks out of the Storefront
 * application.
 */
public class LogoutAction extends StorefrontBaseAction {
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
    throws Exception {

    HttpSession session = request.getSession(false);
    // Make sure the user has a valid session
    if(session != null) {
      if(getUserContainer(request) != null) {
        // Login through the security service
        IStorefrontService serviceImpl = this.getStorefrontService();
        serviceImpl.logout( getUserContainer(request).getUserView().getEmailAddress());
        getUserContainer(request).cleanUp();
      }
    }
    return mapping.findForward( IConstants.SUCCESS_KEY );
  }
}

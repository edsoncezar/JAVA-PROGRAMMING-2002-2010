package com.oreilly.struts.storefront.order;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;

/**
 * An action that signs the user off of the system.
 */
public class CheckoutAction extends StorefrontBaseAction {
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
    throws Exception {

    // Check to make sure the user is logged in
    if(!isLoggedIn(request)) {
      // Remember the path of where the user was going so they can be
      // taken straight there after login is successful
      String path = mapping.findForward(IConstants.SUCCESS_KEY).getPath();
      setLoginToken(request.getSession(), path);

      // Forward the user to the signon page
      return mapping.findForward(IConstants.SIGNON_KEY);
    }
    // User is already logged in
    return mapping.findForward( IConstants.SUCCESS_KEY );
  }
}

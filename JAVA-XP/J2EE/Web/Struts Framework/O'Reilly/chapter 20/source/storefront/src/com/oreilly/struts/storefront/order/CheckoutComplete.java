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
public class CheckoutComplete extends StorefrontBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     UserContainer userContainer)
    throws BaseException {

    // Check to make sure the user is logged in
    if(!isLoggedIn(request)) {
      String path = mapping.findForward(IConstants.SUCCESS_KEY).getPath();
      setLoginToken(request.getSession(), path);
      return mapping.findForward(IConstants.SIGNON_KEY);
    }
    Integer checkoutCounter = (Integer)request.getSession().getAttribute("CheckoutCounter");
    if(checkoutCounter == null) {
      mapping.findForward(mapping.getInput());
    }
    else {
      if(checkoutCounter.intValue() < 100) {
        checkoutCounter = new Integer(checkoutCounter.intValue() + 1);
        request.getSession().setAttribute("CheckoutCounter", checkoutCounter);

        // Keep processing
        return mapping.findForward("StillProcessing");
      }
      else {

        // Processing is done
        request.getSession().removeAttribute("CheckoutCounter");
        return mapping.findForward("Success");
      }
    }
    request.getSession().setAttribute("CheckoutCounter", checkoutCounter);
    return null;
  }
}

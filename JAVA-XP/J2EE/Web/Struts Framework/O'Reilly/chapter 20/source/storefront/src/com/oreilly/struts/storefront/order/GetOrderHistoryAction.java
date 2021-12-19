package com.oreilly.struts.storefront.order;
import java.util.Locale;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;

/**
 * This action re
 */
public class GetOrderHistoryAction extends StorefrontBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
                                     ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     UserContainer userContainer)
    throws BaseException {

    // This action isn't fully implemented yet. It would normally need to

    // communicate with some service to get the order history data.
    if(!isLoggedIn(request)) {
      String path = mapping.findForward(IConstants.SUCCESS_KEY).getPath();
      setLoginToken(request.getSession(), path);
      return mapping.findForward(IConstants.SIGNON_KEY);
    }
    return null;
  }
}

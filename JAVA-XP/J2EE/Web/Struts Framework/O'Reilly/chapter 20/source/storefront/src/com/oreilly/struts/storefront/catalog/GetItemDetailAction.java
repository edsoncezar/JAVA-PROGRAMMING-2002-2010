package com.oreilly.struts.storefront.catalog;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.oreilly.struts.storefront.framework.exceptions.BaseException;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.StorefrontBaseAction;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.service.IStorefrontService;

/**
 * An action that gets an ItemView based on an id parameter in the request and
 * then inserts the item into an ActionForm and forwards to whatever
 * path is defined as Success for this action mapping.
 */
public class GetItemDetailAction extends StorefrontBaseAction {
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response )
   throws Exception {
    // Get the primary key of the item from the request
    String itemId = request.getParameter( IConstants.ID_KEY );

    UserContainer userContainer = getUserContainer(request);

    // Call the storefront service and ask it for an ItemView for the item
    IStorefrontService serviceImpl = getStorefrontService();
    ItemDetailView itemDetailView = serviceImpl.getItemDetailView( itemId );

    // Set the returned ItemView into the Dynamic Action Form
    // The parameter name 'view' is what is defined in the struts-config
    ((DynaActionForm)form).set("view", itemDetailView);

    // Return the ActionForward that is defined for the success condition
    return mapping.findForward( IConstants.SUCCESS_KEY );
  }
}

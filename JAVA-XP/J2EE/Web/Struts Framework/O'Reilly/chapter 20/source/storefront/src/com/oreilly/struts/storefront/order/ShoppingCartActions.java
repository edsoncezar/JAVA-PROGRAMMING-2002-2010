package com.oreilly.struts.storefront.order;

import java.io.IOException;
import java.text.Format;
import java.text.NumberFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import com.oreilly.struts.storefront.service.IStorefrontService;
import com.oreilly.struts.storefront.catalog.view.ItemDetailView;
import com.oreilly.struts.storefront.framework.UserContainer;
import com.oreilly.struts.storefront.framework.util.IConstants;
import com.oreilly.struts.storefront.framework.ShoppingCartItem;
import com.oreilly.struts.storefront.framework.ShoppingCart;
import com.oreilly.struts.storefront.framework.StorefrontDispatchAction;
/**
 * Implements all of the functionality for the ShoppingCart.
 */
public class ShoppingCartActions extends StorefrontDispatchAction {
  /**
   * This method just forwards to the success state, which should represent
   * the shoppingcart.jsp page.
   */
  public ActionForward view(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
    throws Exception {

    // Call to ensure that the user container has been created
    UserContainer userContainer = getUserContainer(request);
    return mapping.findForward(IConstants.SUCCESS_KEY);
  }
 /**
  * This method updates the items and quanties for the shopping cart from the
  * request.
  */
  public ActionForward update(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {

     updateItems(request);
     updateQuantities(request);
     return mapping.findForward(IConstants.SUCCESS_KEY);
  }

 /**
  * This method adds an item to the shopping cart based on an id and qty
  * parameters from the request.
  */
  public ActionForward addItem(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
    throws Exception {

    UserContainer userContainer = getUserContainer(request);

    // Get the id for the product to be added
    String itemId = request.getParameter( IConstants.ID_KEY );
    String qtyParameter = request.getParameter( IConstants.QTY_KEY );

    int quantity = 1; // Default value to add
    if(qtyParameter != null) {
      Locale userLocale = userContainer.getLocale();
      Format nbrFormat = NumberFormat.getNumberInstance(userLocale);
      try {
        Object obj = nbrFormat.parseObject(qtyParameter);
        quantity = ((Number)obj).intValue();
      }
      catch(Exception ex) {
        // Just use the default qty already set
      }
    }

    // Call the storefront service and ask it for an ItemView for the item
    IStorefrontService serviceImpl = getStorefrontService();
    ItemDetailView itemDetailView = serviceImpl.getItemDetailView( itemId );

    // Add the item to the cart and return
    userContainer.getCart().addItem(new ShoppingCartItem(itemDetailView, quantity));
    return mapping.findForward(IConstants.SUCCESS_KEY);
  }

 /**
  * Update the items in the shopping cart. Currently, only deletes occur
  * during this operation.
  */
  private void updateItems(HttpServletRequest request) {
    // Multiple checkboxes with the name "deleteCartItem" are on the
    // form. The ones that were checked are passed in the request.
    String[] deleteIds = request.getParameterValues("deleteCartItem");

    // Build a List of item ids to delete
    if(deleteIds != null && deleteIds.length > 0) {
      int size = deleteIds.length;
      List itemIds = new ArrayList();
      for(int i = 0;i < size;i++) {
        itemIds.add(deleteIds[i]);
      }
      // Get the ShoppingCart from the UserContainer and delete the items
      UserContainer userContainer = getUserContainer(request);
      userContainer.getCart().removeItems(itemIds);
    }
  }

 /**
  * Update the quantieis for the items in the shopping cart.
  */
  private void updateQuantities(HttpServletRequest request) {
    Enumeration enum = request.getParameterNames();
    // Iterate through the parameters and look for ones that begin with
    // "qty_". The qty fields in the page were all named "qty_" + itemId.
    // Strip off of the id of each item and the cooresponding qty value.
    while(enum.hasMoreElements()) {
      String paramName = (String)enum.nextElement();
      if(paramName.startsWith("qty_")) {
        String id = paramName.substring(4, paramName.length());
        String qtyStr = request.getParameter(paramName);
        if(id != null && qtyStr != null) {
          ShoppingCart cart = getUserContainer(request).getCart();
          cart.updateQuantity(id, Integer.parseInt(qtyStr));
        }
      }
    }
  }
}
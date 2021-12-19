package com.wrox.pjsp2.struts.shopping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.PropertyUtils;
import com.wrox.pjsp2.struts.common.CartItem;
import com.wrox.pjsp2.struts.common.CD;
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.ShoppingCart;

public class CheckoutAction extends Action {

   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      CheckoutForm checkoutForm = (CheckoutForm) form;
      int debugLevel = servlet.getDebug();

      ActionErrors errors = new ActionErrors();

      ShoppingCart cart = (ShoppingCart)session.getAttribute(Constants.SHOPPING_CART_KEY);
      if(cart == null) {
         // need to test this.
         cart = new ShoppingCart();
         session.setAttribute(Constants.SHOPPING_CART_KEY, cart);
      }
      CartItem item = null;
      CD aCD = null;
      if("update".equals(action) == true) {
         int titleId = Constants.UNSELECTED_VALUE;
         int newQuantity = Constants.UNSELECTED_VALUE;
         if(checkoutForm != null) {
            titleId = checkoutForm.getTitleId();
            newQuantity = checkoutForm.getQuantity();
         }
         item = cart.getCartItem(titleId);
         if(item != null) {
            int oldQuantity = item.getQuantity();
            if(newQuantity == oldQuantity) {
               servlet.log("no change in quantity.");
            } else if(newQuantity == 0) {
               cart.deleteCartItem(item);
               session.setAttribute(Constants.SHOPPING_CART_KEY, cart);
            } else {
               item.changeQuantity(newQuantity);
               session.setAttribute(Constants.SHOPPING_CART_KEY, cart);
            }
         } else {
            errors.add("item", new ActionError("error.item.invalid"));
         }
      } else {
        servlet.log("must be displayCart.");
      }
      if("request".equals(mapping.getScope())) {
         request.setAttribute(mapping.getAttribute(), form);
      }

      // Forward control to the showcategories page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return (mapping.findForward("success"));

   }
}

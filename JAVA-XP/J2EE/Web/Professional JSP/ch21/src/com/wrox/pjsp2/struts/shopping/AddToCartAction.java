package com.wrox.pjsp2.struts.shopping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
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
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyUtils;
import com.wrox.pjsp2.struts.common.CartItem;
import com.wrox.pjsp2.struts.common.CD;
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.ShoppingCart;

public class AddToCartAction extends Action {

   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      Locale locale = getLocale(request);
      MessageResources messages = getResources();
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();
      ServletContext servletContext = servlet.getServletContext();

      ActionErrors errors = new ActionErrors();

      HashMap cdTable =
         (HashMap)servletContext.getAttribute(Constants.CD_TABLE_KEY);
      
      if(cdTable == null) {
         errors.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.categoryTable.missing"));
         // add a mapping to an error page.
      }
      
      if(cdTable != null) {
         ShoppingCart cart = 
            (ShoppingCart)session.getAttribute(Constants.SHOPPING_CART_KEY);
         if(cart == null) {
            cart = new ShoppingCart();
         }
         // Check to see if Item already exists
         int titleId = Integer.parseInt(request.getParameter(Constants.TITLE_ID));
         CartItem item = null;
         CD aCD = null;
         if(cart.isCartItemPresent(titleId) == true) {
            item = cart.getCartItem(titleId);
            item.incrementQuantity();
         } else {
            item = new CartItem();
            aCD = (CD)cdTable.get(new Integer(titleId));
            item.addCd(aCD, 1);  // quantity = 1
            cart.addCartItem(item);
         }
         session.setAttribute(Constants.SHOPPING_CART_KEY, cart);
      }
      
      // Forward control to the showcategories page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return (mapping.findForward("success"));

   }
   
}

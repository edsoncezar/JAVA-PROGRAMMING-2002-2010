package com.wrox.pjsp2.struts.order;

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
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.ShoppingCart;
import com.wrox.pjsp2.struts.common.User;

public class OrderAction  extends Action {
   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();
      OrderForm orderForm = (OrderForm) form;
      ActionErrors errors = new ActionErrors();

      if("createOrder".equals(action) == true) {
         servlet.log("create the order.");
         User user = (User)session.getAttribute(Constants.USER_KEY);
         if(user == null) {
            servlet.log("User was not logged on.");
            return mapping.findForward("logon");
         }
         ShoppingCart cart = (ShoppingCart)session.getAttribute(Constants.SHOPPING_CART_KEY);
         if(cart == null || cart.getItemCount() < 1) {
            servlet.log("empty cart.");
            errors.add("empty cart", new ActionError("error.emptycart"));
            return mapping.findForward("emptycart");
         }
         // populate billing information from user
         OrderInformation billing = new OrderInformation();
         billing.setFirstName(user.getFirstName());
         billing.setLastName(user.getLastName());
         billing.setAddress(user.getUserAddress());         

         // populate shipping information from user
         OrderInformation shipping = new OrderInformation();
         shipping.setFirstName(user.getFirstName());
         shipping.setLastName(user.getLastName());
         shipping.setAddress(user.getBillingAddress());         

         // pre-populate the orderForm
         orderForm.setBillingInformation(billing);
         orderForm.setShippingInformation(shipping);
         // add pre-populated orderForm to the request
         servlet.log("setting form with key=" + mapping.getAttribute());
         session.setAttribute(mapping.getAttribute(), orderForm);
      } else if("updateAddress".equals(action) == true) {
         // do next stuff.
         servlet.log("updating the address.");
         OrderInformation billing = orderForm.getBillingInformation();
         OrderInformation shipping = orderForm.getShippingInformation();
      } else if("placeOrder".equals(action) == true) {
         boolean error = false;
         if("UNKNOWN".equals(orderForm.getCreditCardType()) == true) {
            error = true;
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.creditCard.notSelected"));
         }
         String inputCC = orderForm.getCreditCardNumber();
         if(inputCC == null || inputCC.length() < 15) {
            error = true;
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.creditCardNumber.notEntered"));
         }
         if(error == false) {
            OrderNumberGenerator numGen = OrderNumberGenerator.getInstance();
            String orderNumber = numGen.getNextOrderNumber();
            orderForm.setOrderNumber(orderNumber);
            session.setAttribute(mapping.getAttribute(), orderForm);
         }
         // Report any errors we have discovered back to the original form
         if(!errors.empty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
         } else {
            return mapping.findForward("thankyou");
         }
      }
      // Forward control to the success page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return mapping.findForward("success");

   }
}

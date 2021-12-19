package com.wrox.pjsp2.struts.registration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.HashMap;
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
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;

public final class SaveRegistrationAction extends Action {


   public ActionForward perform(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
   throws IOException, ServletException {
      // Extract attributes and parameters we will need
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();
      ServletContext servletContext = servlet.getServletContext();
      RegistrationForm regform = (RegistrationForm) form;
      
      if(action == null) {
         action = "Create";
      }

      HashMap userTable = 
         (HashMap) servletContext.getAttribute(Constants.USER_TABLE_KEY);
      if(debugLevel >= 1) {
         servlet.log("SaveRegistrationAction:  Processing " + action + " action");
      }
      
      // Is there a currently logged on user (unless creating)?
      User user = (User) session.getAttribute(Constants.USER_KEY);
      if(!"Create".equals(action) && (user == null)) {
         if(debugLevel >= 1) {
            servlet.log(" User is not logged on in session " + session.getId());
         }
         return servlet.findForward("logon");
      }
      if("Create".equals(action)) {
         user = regform.getUser();
      }
      
      // Was this transaction cancelled?
      if(isCancelled(request)) {
         if(debugLevel >= 1) {
            servlet.log(" Transaction '" + action + "' was cancelled");
         }
         if(mapping.getAttribute() != null) {
            session.removeAttribute(mapping.getAttribute());
         }
         if("Create".equals(action) && (user == null)) {
           return mapping.findForward("cancel");
         } else {
            return mapping.findForward("success");
         }
      }
      
      // Validate the request parameters specified by the user
      if(debugLevel >= 1) {
         servlet.log(" Performing extra validations");
      }
      
      String value = null;
      ActionErrors errors = new ActionErrors();
      value = user.getUserName();      
      if("Create".equals(action) && (userTable.get(value) != null)) {
         errors.add("userName", new ActionError("error.userName.unique",
                                                user.getUserName()));
      }
      // Report any errors we have discovered back to the original form
      if(!errors.empty()) {
         saveErrors(request, errors);
         return (new ActionForward(mapping.getInput()));
      }
      
      // Log the user in if appropriate
      if("Create".equals(action)) {
         userTable.put(user.getUserName(), user);
         session.setAttribute(Constants.USER_KEY, user);
         if(debugLevel >= 1) {
            servlet.log(" User '" + user.getUserName() +
                         "' logged on in session " + session.getId());
         }
      }
      
      if(mapping.getAttribute() != null) {
         if("request".equals(mapping.getScope())) {
            request.removeAttribute(mapping.getAttribute());
         } else {
            session.removeAttribute(mapping.getAttribute());
         }
      }
      
      // Forward control to the specified success URI
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to success page");
      }
      return (mapping.findForward("success"));
   }
}

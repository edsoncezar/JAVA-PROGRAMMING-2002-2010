package com.wrox.pjsp2.struts.logon;

import java.io.IOException;
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
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;

public class LogonAction extends Action {
   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {
      // method variables
      HttpSession session = request.getSession();
      ServletContext servletContext = servlet.getServletContext();
      int debugLevel = servlet.getDebug();
      User user = null;
      ActionErrors errors = new ActionErrors();
      
      // Obtain the input values from the form
      String userName = ((LogonForm)form).getUserName();
      String password = ((LogonForm)form).getPassword();

      // Get the list of users currently known to the application
      HashMap userTable =
         (HashMap)servletContext.getAttribute(Constants.USER_TABLE_KEY);

      if(userTable == null) {
         errors.add(ActionErrors.GLOBAL_ERROR,
             new ActionError("error.userTable.missing"));
      } else {
         user = (User)userTable.get(userName);
         if((user != null) && !user.getPassword().equals(password)) {
            user = null;
         }
         if(user == null) {
            errors.add(ActionErrors.GLOBAL_ERROR,
                new ActionError("error.password.mismatch"));
         }
      }
      // Report any errors we have discovered back to the original form
      if(!errors.empty()) {
         saveErrors(request, errors);
         return (new ActionForward(mapping.getInput()));
      }
      // Save our logged-in user in the session
      session.setAttribute(Constants.USER_KEY, user);
      if(debugLevel >= 1) {
         servlet.log("LogonAction: User '" + user.getUserName() +
            "' logged on in session " + session.getId());
      }
      // Remove the obsolete form bean
      if(mapping.getAttribute() != null) {
         if("request".equals(mapping.getScope())) {
            request.removeAttribute(mapping.getAttribute());
         } else {
            session.removeAttribute(mapping.getAttribute());
         }
      }
      // Forward control to the specified success URI
      return mapping.findForward("success");
   }
}

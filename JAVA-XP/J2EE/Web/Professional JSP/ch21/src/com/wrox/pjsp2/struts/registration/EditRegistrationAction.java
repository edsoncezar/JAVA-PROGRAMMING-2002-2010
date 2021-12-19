package com.wrox.pjsp2.struts.registration;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.util.PropertyUtils;
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;

public final class EditRegistrationAction extends Action {


   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // setup method variables
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();

      if(action == null) {
         action = "Create";
      }
      if(debugLevel >= 1) {
         servlet.log("EditRegistrationAction:  Processing " + action + 
                     " action");
      }

      // Is there a currently logged on user?
      User user = null;
      if(!"Create".equals(action)) {
         user = (User) session.getAttribute(Constants.USER_KEY);
         if(user == null) {
            if(debugLevel >= 1) {
               servlet.log(" User is not logged on in session " + 
                           session.getId());
            }
            return servlet.findForward("logon");
         }
      }

      RegistrationForm regform = (RegistrationForm)form;

      // Populate the user if one exists
      if(user != null) {
         if(debugLevel >= 1) {
            servlet.log(" Populating form from " + user);
         }
         regform.setAction(action);
         regform.setUser(user);
         if("request".equals(mapping.getScope())) {
            request.setAttribute(mapping.getAttribute(), regform);
            // Tell the receiving page which option is to be selected
            String title = user.getTitle();
            request.setAttribute(Constants.SELECTED_OPTION_KEY, title);
         } 
      } //end if(user != null)

      // Forward control to the edit user registration page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return (mapping.findForward("success"));

   }
}

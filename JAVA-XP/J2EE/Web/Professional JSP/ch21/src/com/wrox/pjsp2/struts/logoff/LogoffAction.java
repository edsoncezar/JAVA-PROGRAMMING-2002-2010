package com.wrox.pjsp2.struts.logoff;

import java.io.IOException;
import java.util.Hashtable;
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
import com.wrox.pjsp2.struts.common.Constants;
import com.wrox.pjsp2.struts.common.User;

public class LogoffAction extends Action {
   
   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      HttpSession session = request.getSession();
      int debugLevel = servlet.getDebug();
      User user = (User)session.getAttribute(Constants.USER_KEY);

      // Process this user logoff
      if(user != null) {
         if(debugLevel >= 1) {
            servlet.log("LogoffAction: User '" + user.getUserName() +
               "' logged off in session " + session.getId());
         }
      } else {
         if(debugLevel >= 1) {
            servlet.log("LogoffActon: User logged off in session " +
               session.getId());
         }
      }
      session.removeAttribute(Constants.USER_KEY);
      session.invalidate();

      // Forward control to the specified success URI
      return (mapping.findForward("success"));

   }

}

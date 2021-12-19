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
import com.wrox.pjsp2.struts.common.CD;
import com.wrox.pjsp2.struts.common.Constants;

public class ShowCDsAction extends Action {

   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();

      ActionErrors errors = new ActionErrors();

      // Probably should handle the case if it's not present.
      int requestedCategoryId = Integer.parseInt(request.getParameter("categoryId"));
      
      HashMap cdTable = (HashMap)
         servlet.getServletContext().getAttribute(Constants.CD_TABLE_KEY);
      
      if(cdTable == null) {
         errors.add(ActionErrors.GLOBAL_ERROR,
            new ActionError("error.cdTable.missing"));
      } else {
         int size = cdTable.size();
         if(size > 0) {
            // Generate list of cds based on request parameters
            Iterator iterator = cdTable.values().iterator();
            ArrayList cdArrayList = new ArrayList();
            
            CD aCD = null;
            Object aObject = null;
            int categoryId = 0;
            while(iterator.hasNext()) {
               aObject = iterator.next();
               aCD = (CD)aObject;
               categoryId = aCD.getCategoryId();
               if(requestedCategoryId == categoryId) {
                  cdArrayList.add(aCD);
               }
               // Set the mapping for the form:link tag
               aCD.setMapping();
            }
            CD[] cdArray = new CD[0];
            cdArray = (CD[])cdArrayList.toArray(cdArray);
            session.setAttribute(Constants.CD_ARRAY_KEY, cdArray);
         }
      }

      // Forward control to the showcategories page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return (mapping.findForward("success"));

   }
   
}

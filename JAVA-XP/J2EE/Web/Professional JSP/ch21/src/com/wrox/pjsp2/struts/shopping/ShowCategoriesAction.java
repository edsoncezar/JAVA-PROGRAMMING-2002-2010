package com.wrox.pjsp2.struts.shopping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import org.apache.struts.util.PropertyUtils;
import com.wrox.pjsp2.struts.common.Category;
import com.wrox.pjsp2.struts.common.Constants;

public final class ShowCategoriesAction extends Action {


   public ActionForward perform(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
   throws IOException, ServletException {

      // Extract attributes we will need
      HttpSession session = request.getSession();
      String action = request.getParameter("action");
      int debugLevel = servlet.getDebug();
      ServletContext servletContext = servlet.getServletContext();

      ActionErrors errors = new ActionErrors();

      HashMap categoryTable = (HashMap)
         servletContext.getAttribute(Constants.CATEGORY_TABLE_KEY);
      
      if(categoryTable == null) {
         errors.add(ActionErrors.GLOBAL_ERROR,
            new ActionError("error.categoryTable.missing"));
      } else {
         int size = categoryTable.size();
         if(size > 0) {
            Iterator iterator = categoryTable.values().iterator();
            Category[] categoryArray = new Category[size];
            Category aCategory = null;
            Object aObject = null;
            int position = 0;
            while(iterator.hasNext()) {
             aCategory = (Category)iterator.next();
             // Set the mapping for the html:link tag
             aCategory.setMapping();
             position = aCategory.getCategoryId() - 1;
             categoryArray[position] = aCategory;
            }
            servlet.log("setting categoryArray in the application scope.");
            servlet.log("number of categories = " + size);
            servletContext.setAttribute(Constants.CATEGORIES_ARRAY_KEY, categoryArray);
            
         } //end if(size > 0)
      }
      // Forward control to the showcategories page
      if(debugLevel >= 1) {
         servlet.log(" Forwarding to 'success' page");
      }
      return (mapping.findForward("success"));

   }
}

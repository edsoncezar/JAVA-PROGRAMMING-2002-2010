/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */


import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Dispatcher extends HttpServlet {
   public void doGet(HttpServletRequest request, HttpServletResponse response) {
      HttpSession session = request.getSession();
      ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");
      if (messages == null) {
         Locale locale=request.getLocale();
         messages = ResourceBundle.getBundle("messages.BookstoreMessages", locale); 
         session.setAttribute("messages", messages);
      }
      request.setAttribute("selectedScreen", request.getServletPath());
      try {
         request.getRequestDispatcher("/template.jsp").forward(request, response);
      } catch(Exception ex) {
         ex.printStackTrace();
      }
   }
   public void doPost(HttpServletRequest request, HttpServletResponse response) {     
      request.setAttribute("selectedScreen", request.getServletPath());
      try {
         request.getRequestDispatcher("/template.jsp").forward(request, response);
      } catch(Exception ex) {
         ex.printStackTrace();
      }
   }
}

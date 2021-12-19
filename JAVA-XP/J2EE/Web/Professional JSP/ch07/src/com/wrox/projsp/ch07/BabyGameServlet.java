package com.wrox.projsp.ch07;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BabyGameServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) {
    processRequest(request, response);
  } 

  public void doPost(HttpServletRequest request, 
                     HttpServletResponse response) {
    processRequest(request, response);
  } 

  protected void processRequest(HttpServletRequest request, 
                                HttpServletResponse response) {
    try {

      // If we added actual authentication, this is where it would go
      // For example purposes, if the parameters exist, we consider the
      // auth successful.
      if (!(request.getParameter("guesser").equals("")) && !(request.getParameter("password").equals(""))) {

        // Note: Based on the successful authentication of this user we may
        // want to consider writing something into the session state that
        // signifies that this is the case.  For example, if we wanted to
        // limit direct access to certain JSP pages only to authenticated
        // users, then we could include code that would check for this
        // session state before allowing such access.

        // In order to reuse this Servlet for multiple examples, we pass as
        // a request parameter the resource name to which we dispatch our
        // request.
        getServletContext()
          .getRequestDispatcher(request.getParameter("dispatchto"))
            .forward(request, response);
      } else {
        PrintWriter outy = response.getWriter();
        outy.println("Unable to authenticate, please try again.");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  } 
}


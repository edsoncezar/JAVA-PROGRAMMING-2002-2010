package com.wrox.projsp.ch07;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;


public class BabyGameServletSW extends HttpServlet {

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

      String guesser = request.getParameter("guesser");
      String password = request.getParameter("password");

      // Authenticate each request

      Authenticator auth = 
        AuthenticatorFactory.create(AuthenticatorFactory.SIMPLE);
      AuthContext authContext = 
        AuthenticatorFactory
          .createContext(AuthenticatorFactory.SIMPLE);
      authContext.addValue("guesser", guesser);
      authContext.addValue("password", password);

      auth.init(authContext);

      if (auth.authenticate()) {
        String dispatchto = request.getParameter("dispatchto");
        String delegateToBean = request.getParameter("delegatetobean");

        // Delegate to worker bean
        if (delegateToBean != null) {
          BabyGameWorkerSW worker = new BabyGameWorkerSW();
          worker.load(guesser);
          request.setAttribute("SWworker", worker);
        } 

        // In order to reuse this Servlet for multiple examples, we pass
        // as a request parameter the resource name to which we dispatch
        // our request
        getServletConfig().getServletContext()
          .getRequestDispatcher(dispatchto)
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

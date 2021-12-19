package com.wrox.projsp.ch03.time.controller;

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;

public class TimeServlet1 extends HttpServlet {
  public void init() {
    log("init was called");
  } 

  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) throws ServletException, 
                    IOException {
    log("doGet called");

    response.setContentType("text/html");

    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head><title>");
    out.println("Time Entry System");
    out.println("</title></head>");

    out.println("<body>");

    out.println("<h2>Welcome to the Time Entry System</h2>");

    out.println("<form action=Time1 method=POST>");

    String event = request.getParameter("EVENT");
    if (event == null) {

      // default case
    } else if (event.equals("ADMIN")) {
      out.println("<h3>Administration Information</h3>");
      out.println("<ul>");
      out.println("<li>Last Modified: " + getLastModified(request));
      out.println("<li>Servlet Info: " + getServletInfo());
      out.println("<li>Servlet Name: " + getServletName());
      out.println("<li>Init Parameters:");

      Enumeration initParams = getInitParameterNames();
      out.println("<ul>");
      while (initParams.hasMoreElements()) {
        String paramName = (String) initParams.nextElement();
        String paramValue = getInitParameter(paramName);
        out.println("<li>" + paramName + "=" + paramValue);
      } 
      out.println("</ul>");
      out.println("</ul>");

    } 

    out.println("</form>");

    out.println("</body>");
    out.println("</html>");

  } 
  public void doPost(HttpServletRequest request, 
                     HttpServletResponse response) throws ServletException, 
                     IOException {
    log("doPost called");
    doGet(request, response);
  } 

}

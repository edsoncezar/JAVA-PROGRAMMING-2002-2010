package com.wrox.projsp.ch03.time.controller;

import java.util.Enumeration;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class TimeServlet2 extends HttpServlet {
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

    out.println("<form action=Time2 method=POST>");

    String event = request.getParameter("EVENT");
    if (event == null) {

      // default case
    } else if (event.equals("ADMIN")) {
      out.println("<h2>Administration Information</h2>");

      out.println("<h3>Webapp and Servlet Engine Info</h3>");

      ServletContext context = getServletContext();

      out.println("<ul>");
      out.println("<li>Server Info: " + context.getServerInfo());
      out.println("<li>Major Version: " + context.getMajorVersion());
      out.println("<li>Minor Version: " + context.getMinorVersion());
      out.println("<li>Webapp Init Parameters:");

      Enumeration webappParams = context.getInitParameterNames();
      out.println("<ul>");
      while (webappParams.hasMoreElements()) {
        String paramName = (String) webappParams.nextElement();
        String paramValue = context.getInitParameter(paramName);

        // assume paramName and paramValue don't contain any
        // HTML formatting characters such as <, >, &, " or '
        out.println("<li>" + paramName + "=" + paramValue);
      } 
      out.println("</ul>");

      out.println("<h3>Servlet Info</h3>");

      ServletConfig config = getServletConfig();

      out.println("<li>Last Modified: " + getLastModified(request));
      out.println("<li>Servlet Info: " + getServletInfo());
      out.println("<li>Servlet Name: " + config.getServletName());

      out.println("<li>Servlet Init Parameters:");

      Enumeration ServletParams = config.getInitParameterNames();
      out.println("<ul>");
      while (ServletParams.hasMoreElements()) {
        String paramName = (String) ServletParams.nextElement();
        String paramValue = config.getInitParameter(paramName);
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

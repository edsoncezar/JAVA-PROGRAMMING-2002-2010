package com.wrox.projsp.ch03.time.controller;

import com.wrox.projsp.ch03.time.controller.beans.Charge;

import java.util.Enumeration;
import java.util.Hashtable;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class TimeServlet3 extends HttpServlet {
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

    out.println("<form action=Time3 method=POST>");

    HttpSession session = request.getSession();

    // find out what we were told to do (if anything)
    String event = request.getParameter("EVENT");
    if (event == null) {
      event = "ENTER_RECORD";   // catch this special case
    } else if (event.equals("")) {
      event = "ENTER_RECORD";   // catch this special case
    } 

    // main dispatch center
    if (event.equals("ENTER_RECORD")) {

      // default case
      out.println("<h3>Enter Charge Record</h3>");
      out.println("<p>User Name<input type=text name=name>");
      out.println("<p>Project<input type=text name=project>");
      out.println("<p>Hours<input type=text name=hours>");
      out.println("<p>Date<input type=text name=date>");
      out.println("<input type=hidden name=EVENT value=NEW_RECORD>");
      out.println("<p><input type=submit>");
      out.println("<input type=reset>");

    } else if (event.equals("NEW_RECORD")) {

      out.println("<h3>Your Charge Record has been saved</h3>");
      String name = request.getParameter("name");
      String project = request.getParameter("project");
      String hours = request.getParameter("hours");
      String date = request.getParameter("date");

      Charge c = new Charge();
      c.setName(name);
      c.setProject(project);
      c.setHours(hours);
      c.setDate(date);

      Hashtable h = (Hashtable) session.getAttribute("charges");
      if (h == null) {
        h = new Hashtable();   // first charge
        session.setAttribute("charges", h);
      } 
      h.put(project, c);       // use project as the key
      out.println("Record Details: <p>");
      out.println("Name = " + name + ", Project = " + project 
                  + ", Hours = " + hours + ", Date = " + date);

    } else if (event.equals("SUMMARY")) {

      out.println("<h3>Summary of your Charge Records</h3>");
      Hashtable h = (Hashtable) session.getAttribute("charges");
      if (h != null) {
        out.println("<ul>");
        Enumeration charges = h.keys();
        while (charges.hasMoreElements()) {
          String proj = (String) charges.nextElement();
          Charge ch = (Charge) h.get(proj);
          out.println("<li>");
          out.println("name = " + ch.getName());
          out.println(", project = " + proj);
          out.println(", hours = " + ch.getHours());
          out.println(", date = " + ch.getDate());
        } 
        out.println("</ul>");
      } 


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

    out.println("<hr>");


    out.println("<form action=Time3 method=POST>");
    out.println("<input type=hidden name=EVENT value=SUMMARY>");
    out.println("<input type=submit value=Summary>");
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

package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Calendar;

public final class StopGamesFilter implements Filter {

  private FilterConfig filterConfig = null;
  private int starthour = 0;
  private int stophour = 24;  // default is to allow all the time
  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
            throws IOException, ServletException {
    Calendar myCal = Calendar.getInstance();
    int curhour = myCal.get(Calendar.HOUR_OF_DAY);
     
    filterConfig.getServletContext().log("in StopGamesFilter cur:" +
              curhour + ", start: " + starthour + ", end: " + stophour );
 
    if (( curhour >= stophour)|| (curhour < starthour))  {
      PrintWriter out = response.getWriter();
      out.println("<html><head></head><body>");
      out.println("<h1>Sorry, game playing is not allowed at this time!</h1>");
      out.println("</body></html>");
      out.flush();
      filterConfig.getServletContext().log("Access to game page denied");
      return;
    }

    filterConfig.getServletContext().log("Access to game page granted");
    chain.doFilter(request, response);
    filterConfig.getServletContext().log("Getting out of StopGamesFilter");

  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    String tpString;
    if (( tpString = filterConfig.getInitParameter("starthour") ) != null)
      starthour = Integer.parseInt(tpString, 10);
    if (( tpString = filterConfig.getInitParameter("stophour") ) != null)
      stophour = Integer.parseInt(tpString, 10);

    this.filterConfig = filterConfig;
  }

  public String toString() {
    if (filterConfig == null)
      return ("StopGamesFilter()");
    StringBuffer sb = new StringBuffer("StopGamesFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}


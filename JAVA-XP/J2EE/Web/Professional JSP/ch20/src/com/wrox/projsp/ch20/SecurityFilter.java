package com.wrox.projsp.ch20;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class SecurityFilter implements Filter {
  public static final String PARAM_VALID_IP = "validIP";
  private static final String ERROR_PAGE = "/error.html";

  private FilterConfig filterConfig;
  private String strIP;

  public void doFilter(ServletRequest req, ServletResponse res, 
                       FilterChain chain) throws java.io.IOException, 
                       ServletException {
    boolean allowed;

    allowed = checkIP(req);
    if (allowed) {
      chain.doFilter(req, res);
    } else {
      RequestDispatcher rd = req.getRequestDispatcher(ERROR_PAGE);
      rd.forward(req, res);
    } 
  } 

  public void destroy(){}

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
    strIP = filterConfig.getInitParameter(this.PARAM_VALID_IP);
  } 

  /*
   * In a real-world implementation, we'd take into account a collection of
   * IP addresses for this type of restriction, or allow in a group of IP 
   * addresses. For our example, blocking all but one address will suffice.
   */
  private boolean checkIP(ServletRequest req) {
    boolean validIP = true;

    if (this.strIP != null) {
      String ip = req.getRemoteAddr();
      if (!ip.equals(this.strIP)) {
        validIP = false;
      } 
    } 

    return validIP;
  } 
}

package com.wrox.projsp.ch14.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class AuditFilter implements Filter {


  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
            throws IOException, ServletException {
       
    if (filterConfig == null)
      return;

    long startTime = System.currentTimeMillis();
    String remoteAddress =  request.getRemoteAddr();
    String remoteHost = request.getRemoteHost();
    HttpServletRequest myReq = (HttpServletRequest) request;
    String reqURI = myReq.getRequestURI();
        
    chain.doFilter(request, response);
    filterConfig.getServletContext().log("User at IP " + remoteAddress + "(" +
                 remoteHost + ") accessed resource " + reqURI + " and used " +
                 (System.currentTimeMillis() - startTime) + " ms"  );

  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }


  public String toString() {
    if (filterConfig == null)
      return ("WroxAuditFilter()");
      StringBuffer sb = new StringBuffer("WroxAuditFilter(");
      sb.append(filterConfig);
      sb.append(")");
      return (sb.toString());
  }

}


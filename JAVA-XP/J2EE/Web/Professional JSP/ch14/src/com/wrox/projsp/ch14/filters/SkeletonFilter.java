package com.wrox.projsp.ch14.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class SkeletonFilter implements Filter {


  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
            throws IOException, ServletException {
       
    if (filterConfig == null)
      return;
        
    filterConfig.getServletContext().log("in SkeletonFilter");
    chain.doFilter(request, response);
    filterConfig.getServletContext().log("Getting out of SkeletonFilter");

  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public String toString() {
    if (filterConfig == null)
      return ("SkeletonFilter()");
    StringBuffer sb = new StringBuffer("SkeletonFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}


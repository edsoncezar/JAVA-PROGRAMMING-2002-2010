package com.wrox.projsp.ch14.filters;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public final class SimpleFilter implements Filter {
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
            throws IOException, ServletException {

    if (filterConfig == null)
      return;

    filterConfig.getServletContext().log("in SimpleFilter");
    chain.doFilter(request, response);
    filterConfig.getServletContext().log("Getting out of SimpleFilter");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public String toString() {
    if (filterConfig == null)
      return ("SimpleFilter()");
    StringBuffer sb = new StringBuffer("SimpleFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}

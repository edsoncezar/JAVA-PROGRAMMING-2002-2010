package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

class LegacyAdapterRequestWrapper extends HttpServletRequestWrapper {

  String myDept = null;

  public LegacyAdapterRequestWrapper(HttpServletRequest inReq,
                                     String deptString) {
    super( inReq);
    myDept = deptString;
  }

  public Map getParameterMap() {
    Map tmpMap = super.getParameterMap();
    tmpMap.put("DEPT",myDept);
    return tmpMap;
  }

  public String [] getParameterValues(String paramName) {
    if (paramName.equalsIgnoreCase("DEPT")) {
      String [] tpAry = new String[1];
      tpAry[0] = myDept;
      return tpAry;
    } else
      return super.getParameterValues(paramName);
  }

  public String getParameter(String paramName) {
    if (paramName.equalsIgnoreCase("DEPT")) {
      return myDept;
    }    
    else
      return super.getParameter(paramName);
  }
}

public final class LegacyAdapterFilter implements Filter {

  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    LegacyAdapterRequestWrapper aCustomReq;
    if (filterConfig == null)
      return;
    String clientAddr = request.getRemoteAddr();
    System.out.println("the addr is " + clientAddr);
    int idx = clientAddr.indexOf(".");
    clientAddr = clientAddr.substring(idx + 1);
    idx = clientAddr.indexOf(".");
    clientAddr = clientAddr.substring(idx + 1);
    idx = clientAddr.indexOf(".");
    clientAddr = clientAddr.substring(0, idx);
    System.out.println("the subnet is " + clientAddr);
    String dept = null;
    if (clientAddr.equals("0"))
      dept = "Engineering";
    else
      dept = "Accounting";
    aCustomReq = new LegacyAdapterRequestWrapper((HttpServletRequest) request,
                                                 dept);

    filterConfig.getServletContext().log("in LegacyAdapterFilter");

    chain.doFilter(aCustomReq, response);
    filterConfig.getServletContext().log("Getting out of LegacyAdapterFilter");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public String toString() {

    if (filterConfig == null)
      return ("LegacyAdapterFilter()");
    StringBuffer sb = new StringBuffer("LegacyAdapterFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}

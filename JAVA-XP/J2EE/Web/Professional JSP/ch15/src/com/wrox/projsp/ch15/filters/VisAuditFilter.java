package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

class VisAuditOutStream extends ReplaceContentOutputStream {
  String Addr;
  String Host;
  public VisAuditOutStream(OutputStream outStream, String inAddr,
                           String inHost) {
    super(outStream);
    Addr = inAddr;
    Host = inHost;
  }

  public byte [] replaceContent(byte [] inBytes) {
    String retVal ="";
    String firstPart="";
        
    String tpString = new String(inBytes);
    String srchString = (new String(inBytes)).toLowerCase();

    int endBody = srchString.indexOf("</body>");
 
    if (endBody != -1) {
      firstPart = tpString.substring(0, endBody);
      retVal = firstPart + "<br><small><i>Big Brother is watching you. You have accessed our page from " + Addr + " and on a machine called " + Host + "</i></small></br>" + 
      tpString.substring(endBody); 
    } else {
      retVal=tpString;
    }
                       
    return retVal.getBytes();
  }

}

class VisAuditResponseWrapper extends HttpServletResponseWrapper {
  private PrintWriter tpWriter; 
  private VisAuditOutStream tpStream;

  public VisAuditResponseWrapper(ServletResponse inResp, String inAddr,
                                 String inHost) throws java.io.IOException { 
    super((HttpServletResponse) inResp);
    tpStream = new VisAuditOutStream(inResp.getOutputStream(), inAddr, inHost);
    tpWriter = new PrintWriter(tpStream);
  }

  public ServletOutputStream getOutputStream() throws java.io.IOException {
    return tpStream;
  }

  public PrintWriter getWriter() throws java.io.IOException {
    return tpWriter;
  }
}

public final class VisAuditFilter implements Filter {
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain)
            throws IOException, ServletException {
    if (filterConfig == null)
      return;
    String clientAddr = request.getRemoteAddr();
    String clientHost = request.getRemoteHost();

    filterConfig.getServletContext().log("in VisAuditFilter");
    VisAuditResponseWrapper myWrappedResp =
            new VisAuditResponseWrapper(response, clientAddr, clientHost);
    chain.doFilter(request,  myWrappedResp);
    myWrappedResp.getOutputStream().close(); // force close - container may use
                                             // just setContentLength()
    filterConfig.getServletContext().log("Getting out of VisAuditFilter");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public String toString() {
    if (filterConfig == null)
      return ("VisAuditFilter()");
    StringBuffer sb = new StringBuffer("VisAuditFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}


package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.zip.*;

class KryptOutStream extends ReplaceContentOutputStream {
  public KryptOutStream(OutputStream outStream) {
    super(outStream);
    setTransformOnCloseOnly();
  }

  public byte []  replaceContent(byte [] inBytes) throws java.io.IOException {
    ByteArrayOutputStream tpBaStream = new ByteArrayOutputStream();
    GZIPOutputStream tpZipStream = new GZIPOutputStream(tpBaStream);

    tpZipStream.write(inBytes, 0, inBytes.length);
    tpZipStream.close();
    tpBaStream.close();
    return tpBaStream.toByteArray();
  }

}

class ResponseWrapper extends HttpServletResponseWrapper {
  private PrintWriter tpWriter; 
  private KryptOutStream tpStream;

  public ResponseWrapper(ServletResponse inResp) throws java.io.IOException { 
    super((HttpServletResponse) inResp);
    tpStream = new KryptOutStream(inResp.getOutputStream());
    tpWriter = new PrintWriter(tpStream);
  }

  public ServletOutputStream getOutputStream() throws java.io.IOException {
    return tpStream;
  }

  public PrintWriter getWriter() throws java.io.IOException {
    return tpWriter;
  }

}

public final class KryptPressFilter implements Filter {
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    if (filterConfig == null)
      return;
    boolean gzipSupportedByClient = false;
    String allowedEncoding =
            ((HttpServletRequest) request).getHeader("Accept-Encoding");
    if (allowedEncoding != null) {
      if (allowedEncoding.indexOf("gzip") != -1) {
        gzipSupportedByClient = true;
        ((HttpServletResponse) response).addHeader("Content-Encoding","gzip");
      }
    }
    filterConfig.getServletContext().log("in KryptPressFilter");

    if (gzipSupportedByClient) {
      ResponseWrapper myWrappedResp = new ResponseWrapper( response);
      chain.doFilter(request,  myWrappedResp);
      myWrappedResp.getOutputStream().close();  
    }
    else 
      chain.doFilter(request, response);

    filterConfig.getServletContext().log("Getting out of KryptPressFilter");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
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


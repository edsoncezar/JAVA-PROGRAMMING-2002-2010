package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.*;
import org.apache.xalan.transformer.*;
import org.apache.xalan.templates.*;
import java.util.zip.*;

class XSLTXformStream extends ReplaceContentOutputStream {

  private Templates templatesXSLT = null;

  public XSLTXformStream(OutputStream outStream, Templates inTp)  {
    super(outStream);
    setTransformOnCloseOnly();
    templatesXSLT = inTp;   
  }

  public byte []  replaceContent(byte [] inBytes) throws java.io.IOException {
    ByteArrayOutputStream outBaStream = new ByteArrayOutputStream();
    ByteArrayInputStream inBaStream = new ByteArrayInputStream(inBytes);

    try {
      Transformer transformerXSLT = templatesXSLT.newTransformer();
      transformerXSLT.transform(new StreamSource(inBaStream),
      new StreamResult(outBaStream));
    } catch (Exception ex) {
        throw (new java.io.IOException());
      }
    inBaStream.close();
    outBaStream.close();
    return outBaStream.toByteArray();
  }
}

class XSLTResponseWrapper extends HttpServletResponseWrapper {

  private PrintWriter tpWriter; 
  private XSLTXformStream tpStream;

  public XSLTResponseWrapper(ServletResponse inResp, Templates inTp)
            throws IOException { 
    super((HttpServletResponse) inResp);
    tpStream = new XSLTXformStream(inResp.getOutputStream(), inTp);
    tpWriter = new PrintWriter(tpStream);
  }

  public ServletOutputStream getOutputStream() throws java.io.IOException {
    return  tpStream;
  }

  public PrintWriter getWriter() throws java.io.IOException {
    return tpWriter;
  }
}

public final class XSLTFilter implements Filter {

  private FilterConfig filterConfig = null;
  private TransformerFactory xsltFactory = null;
  private Templates xsltTemplates = null;

  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    if (filterConfig == null)
      return;
    if (xsltFactory == null) {
      try {
        xsltFactory = TransformerFactory.newInstance();
        String xsltfile = filterConfig.getInitParameter("xsltfile");
        if (xsltfile != null)
          xsltTemplates = xsltFactory.newTemplates(new StreamSource
              (filterConfig.getServletContext().getResourceAsStream(xsltfile)));
      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    filterConfig.getServletContext().log("in XSLTFilter");

    if (xsltTemplates != null) {
      XSLTResponseWrapper myWrappedResp = null;
      try {
        myWrappedResp = new XSLTResponseWrapper( response, xsltTemplates);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      chain.doFilter(request,  myWrappedResp);
      myWrappedResp.getOutputStream().close(); 
    } else {
      chain.doFilter(request, response);
    }
    filterConfig.getServletContext().log("Getting out of XSLTFilter");
  }

  public void destroy() { }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public String toString() {
    if (filterConfig == null)
      return ("XSLTFilter()");
    StringBuffer sb = new StringBuffer("XSLTFilter(");
    sb.append(filterConfig);
    sb.append(")");
    return (sb.toString());
  }

}

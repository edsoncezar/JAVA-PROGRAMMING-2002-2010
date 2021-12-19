<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.URL" %>

<%@ page import="org.apache.xalan.xslt.XSLTInputSource" %>
<%@ page import="org.apache.xalan.xslt.XSLTProcessor" %>
<%@ page import="org.apache.xalan.xslt.XSLTProcessorFactory" %>
<%@ page import="org.apache.xalan.xslt.XSLTResultTarget" %>


<% 
    String hostname = "127.0.0.1";
    int portNumber = 8080;
    String wml_stylesheet = "xsl/Hangman-WML.xsl";
    String htm_stylesheet = "xsl/Hangman-HTML.xsl";

    XSLTInputSource stylesheet = null;
    String userAgent = request.getHeader("User-Agent");
    String action = request.getParameter("action");
    String file = "/ch22/Hangman_XMLGen.jsp";
    URL Hangman_XMLGenURL;

    /* Select the XSL stylesheet appropriate for the client's browser
     * by examining the HTTP request User-Agent header.
     */
    if (userAgent.indexOf("UP.Browser")>=0){
      // Requesting browser is UP.Browser 
      response.setContentType("text/vnd.wap.wml");
      InputStream ios = application.getResourceAsStream(wml_stylesheet);
      stylesheet = new XSLTInputSource(ios);
    }
    else if (userAgent.indexOf("MSIE")>=0){
      // We are running Internet Explorer
      response.setContentType("text/html");
      InputStream ios = application.getResourceAsStream(htm_stylesheet);
      stylesheet = new XSLTInputSource(ios);
    }

    Hangman_XMLGenURL = new URL("http", hostname, portNumber, file+"?action="+action);
    InputStream in = Hangman_XMLGenURL.openStream();

    try {
      XSLTProcessor processor = XSLTProcessorFactory.getProcessor();

      // Transform XML into HTML with the appropriate style sheet
      processor.process(new XSLTInputSource(in),
                        stylesheet,
                        new XSLTResultTarget(out));
    }
    catch (Exception ignored){}
%>

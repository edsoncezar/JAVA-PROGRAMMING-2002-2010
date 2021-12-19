package xsl;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.xalan.xslt.*;

public class UseXSLTag extends BodyTagSupport {


  /**
   * If no xml attribute is specified, the body contains the
   * XML.  Store it here.
   */
  private String body = null;


  /**
   * The xml attribute is the resource to where the
   * XML is, if it's not in the body.
   */
  private String xml = null;

  public String getXml() {
    return (this.xml);
  }

  public void setXml(String xml) {
    this.xml = xml;
  }


  /**
   * The xsl attribute is the resource to where the
   * XSL stylesheet is, it is mandatory.
   */
  private String xsl = null;

  public String getXsl() {
    return (this.xsl);
  }

  public void setXsl(String xsl) {
    this.xsl = xsl;
  }


  /**
   * Make sure the XSL attribute is specified.
   * Evaluate the body of the tag only if no xml attribute 
   * is specified.
   */
  public int doStartTag() throws JspException {
	
    if (xsl == null) {
      throw new JspException("Must have an xsl property");
    }

    if (xml == null) {
      return (EVAL_BODY_AGAIN);
    } else {
      return (SKIP_BODY);
    }

  }


  /**
   * Save the body content in the body private variable.
   */
  public int doAfterBody() throws JspException {

    if (bodyContent == null)
      body = "";
    else
      body = bodyContent.getString().trim();
    return (SKIP_BODY);

  }


  /**
   * Transform the XML via XSL, and output.
   */
  public int doEndTag() throws JspException {
	
    // Make an XSLTInputSource from either the body or the xsl attribute
    XSLTInputSource data;
    if (body != null) {
      data = new XSLTInputSource(new StringReader(body));
    } else {
      data = new XSLTInputSource(pageContext.getServletContext().getResourceAsStream(xml));
    }

	 
    // Make an XSLTInputSource from the xsl attribute.
    XSLTInputSource style = new XSLTInputSource(pageContext.getServletContext().getResourceAsStream(xsl));
	
    // Make the target the Writer object for this tag...
    XSLTResultTarget result = new XSLTResultTarget(pageContext.getOut());
	
    // Create an XSLT processor and use it to perform the transformation
    XSLTProcessor processor;
    try {
	    
      // Create a XSLTProcessor and transform the xml.
      processor = XSLTProcessorFactory.getProcessor();
      processor.process(data, style, result);
	
    } catch (Exception e) {
      throw new JspException(e.toString());
    }

    return (EVAL_PAGE);

  }


  /**
   * Release any allocated resources.
   */
  public void release() {
    this.body = null;
    this.xml = null;
    this.xsl = null;
  }

}

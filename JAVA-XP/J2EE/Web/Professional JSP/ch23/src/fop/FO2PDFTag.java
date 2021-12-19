package fop;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import org.apache.xalan.xslt.*;

import org.apache.fop.apps.*;

public class FO2PDFTag extends BodyTagSupport {

  /**
   * If no fo attribute is specified, the body contains the
   * fo XML.  Store it here.
   */
  private String body = null;


  /**
   * The fo attribute is the resource to where the
   * fo XML is, if it's not in the body.
   */
  private String fo = null;

  public String getFo() {
    return (this.fo);
  }

  public void setFo(String fo) {
    this.fo = fo;
  }


  /**
   * Evaluate the body of the tag only if no fo attribute 
   * is specified.
   */
  public int doStartTag() throws JspException {	
    if (fo == null) {
      return (EVAL_BODY_AGAIN);
    } else {
      return (SKIP_BODY);
    }
  }


  /**
   * Save the body content in the body private variable.
   */
  public int doAfterBody() throws JspException {
    if (bodyContent == null) {
      body = "";
    } else {
      body = bodyContent.getString().trim();
    }
    return (SKIP_BODY);

  }


  /**
   * Render the PDF using FOP.  This is where all the
   * work of the tag takes place.
   */
  public int doEndTag() throws JspException {
	
    // Make an XSLTInputSource from either the body or the fo attribute
    XSLTInputSource data;
    if (body != null) {
      data = new XSLTInputSource(new StringReader(body));
    } else {
      data = new XSLTInputSource(pageContext.getServletContext().getResourceAsStream(fo));
    }

	
    try {
      Driver driver = new Driver();
      driver.setRenderer("org.apache.fop.render.pdf.PDFRenderer",
                         Version.getVersion());
      driver.addElementMapping("org.apache.fop.fo.StandardElementMapping");
      driver.addElementMapping("org.apache.fop.svg.SVGElementMapping");
      driver.addPropertyList("org.apache.fop.fo.StandardPropertyListMapping");
      driver.addPropertyList("org.apache.fop.svg.SVGPropertyListMapping");
      driver.buildFOTree(new org.apache.xerces.parsers.SAXParser(), new XSLTInputSource(data));
      driver.format();

      // create a ByteArrayOutputStream to store the PDF data in
      ByteArrayOutputStream ostr = new ByteArrayOutputStream(); 
      driver.setOutputStream(ostr);

      // render the PDF to the ByteArrayOutputStream
      driver.render();

      // print the PDF bytes in the ByteArrayOutputStream to the JspWriter.
      pageContext.getOut().print(ostr);
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
    this.fo = null;
  }

}

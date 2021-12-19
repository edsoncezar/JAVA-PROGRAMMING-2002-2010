package com.wrox.projsp.ch11.jsp;

import java.io.*;
import java.lang.reflect.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.w3c.dom.*; 
import com.wrox.projsp.ch11.utils.*;

public final class XPathValueTag extends BodyTagSupport {

  private String xpath;
  public void setXpath(String xpath) { this.xpath = xpath; }
  public String getXpath() { return xpath; }

  public int doStartTag() throws JspException, JspTagException {
    try {
      XPathForEachNodeTag parent = (XPathForEachNodeTag) findAncestorWithClass(this, XPathForEachNodeTag.class);
      if(parent == null) 
        throw new JspTagException("XPathValueTag outside of XPathForEachNodeTag");
      Node context = parent.getNode();   
      JspWriter out = pageContext.getOut();
      out.print(XPathUtil.getValue(context, xpath));
    } catch(IOException e) {
      System.out.println("XPathValueTag.doStartTag(): exeception" + e);
    } catch(org.xml.sax.SAXException e) {
      System.out.println("XPathValueTag.doStartTag(): exeception" + e);
    } 
    return SKIP_BODY;
  }
  
  public void release() {
    xpath = null;
  }
}

package com.wrox.projsp.ch11.jsp;

import java.io.*;
import java.lang.reflect.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.w3c.dom.*; 
import org.xml.sax.*; 
import org.apache.xalan.xpath.*; 
import org.apache.xalan.xpath.xml.*; 
import org.apache.xerces.parsers.*;
import com.wrox.projsp.ch11.utils.*;

public class XPathForEachNodeTag extends BodyTagSupport {

  /* attritbute properties */
  
  private String xpath;
  
  public void setXpath(String xpath) { 
    this.xpath = xpath; 
  }
  public String getXpath() { return xpath; }

  private String id;
  
  public void setId(String id) { 
    this.id = id; 
  }
  public String getId() { 
    return id; 
  }

  private String document;
  
  public void setDocument(String document) { 
    this.document = document; 
  }
  
  private Node context;
  
  public void setContext(Node context) { 
    this.context = context; 
  }
  
  public Node getContext() { 
    return context; 
  }
  
  /* scripting properties accessed from the JSP */

  private NodeList nodes;
  
  public NodeList getNodes() { 
    return nodes; 
  }
  
  private int index;
  
  public int getIndex() { 
    return index; 
  }
  
  public Node getNode() { 
    return nodes.item(index); 
  }
  
  public String getPathValue(String xpath) 
  throws org.xml.sax.SAXException {
      return XPathUtil.getValue(getNode(), xpath);
  }

  /* life cycle methods */

  XMLParserLiaison xpathSupport =  new XMLParserLiaisonDefault(); 
  XPathProcessor xpathParser = new XPathProcessorImpl(xpathSupport); 

  public int doStartTag() throws JspException {
    try {
      if(document != null) {
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(new FileInputStream(document)));
        context = parser.getDocument().getDocumentElement();
      }  
      PrefixResolver prefixResolver = new PrefixResolverDefault(context); 
      XPath xp = new XPath(); 
      xpathParser.initXPath(xp, xpath, prefixResolver); 
      XObject list = xp.execute(xpathSupport, context, prefixResolver); 
      nodes = list.nodeset(); 
      index = 0;
    } catch(SAXException e) {
      e.printStackTrace();
    } catch(IOException e) {
      e.printStackTrace();
    }
    if (nodes.getLength() > 0) {
      pageContext.setAttribute(id, this);
      return (EVAL_BODY_TAG);
    } else
      return (SKIP_BODY);
  }

  public int doAfterBody() throws JspException {
    if (++index < nodes.getLength()) {
      pageContext.setAttribute(id, this);
      return (EVAL_BODY_TAG);
    } else
      return (SKIP_BODY);
  }
  
  public int doEndTag() throws JspException {
    if (bodyContent != null) {
      try {
        JspWriter out = getPreviousOut();
        out.print(bodyContent.getString());
      } catch (IOException e) {
        throw new JspException(getClass().getName() + ".doEndTag(): " + e);
      }
    }
    return (EVAL_PAGE);
  }
  
  public void release() {
    xpath = null;
    id = null;
    document = null;
    context = null;
    nodes = null;
    index = 0;
    xpathSupport = null; 
    xpathParser = null; 
  }
    
}

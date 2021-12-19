package com.jspinsider.jspkit.jaxp;

import java.io.IOException;
import java.io.File;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.servlet.*;

public class JAXPTransformerTag extends BodyTagSupport 
{
   JAXPTransformer transformer = new JAXPTransformer();
   JAXPTransformerResources resources = new JAXPTransformerResources();
   boolean isResources = false;

public void setTransformerResources(String new_resources)
   {
   resources = (JAXPTransformerResources)pageContext.getAttribute(new_resources, pageContext.getAttributesScope(new_resources));
   isResources = true;
   }

public void setXSLStreamSource(StreamSource new_XSLStreamSource)
   { resources.setXSLStreamSource(new_XSLStreamSource); }
public void setXSLSAXSource(SAXSource new_XSLSAXSource)
   { resources.setXSLSAXSource(new_XSLSAXSource); }
public void setXSLDOMSource(DOMSource new_XSLDOMSource)
   { resources.setXSLDOMSource(new_XSLDOMSource); }
public void setXMLStreamSource(StreamSource new_XMLStreamSource)
   { resources.setXMLStreamSource(new_XMLStreamSource); }
public void setXMLSAXSource(SAXSource new_XMLSAXSource)
   { resources.setXMLSAXSource(new_XMLSAXSource); }
public void setXMLDOMSource(DOMSource new_XMLDOMSource)
   { resources.setXMLDOMSource(new_XMLDOMSource); }

public void setStreamResult(StreamResult new_StreamResult)
   { resources.setStreamResult(new_StreamResult); }
public void setSAXResult(SAXResult new_SAXResult)
   { resources.setSAXResult(new_SAXResult); }
public void setDOMResult(DOMResult new_DOMResult)
   { resources.setDOMResult(new_DOMResult); }

public int doStartTag() throws JspTagException
   {
   if (!isResources) resources.setStreamResult(new StreamResult(pageContext.getOut()));
   // Have the JSP Container continue processing the JSP page as normal.
	return EVAL_PAGE;
 }

public int doEndTag() throws JspTagException
   {
   try
   {
   transformer.transform(resources);
   }
   catch (Exception e)
   {
   throw new JspTagException("JSP Kit JAXPTransformerTag Error:" + e.toString());
   }
   // Have the JSP Container continue processing the JSP page as normal.
   return EVAL_PAGE;
 }
}

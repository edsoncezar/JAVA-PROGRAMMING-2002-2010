package com.jspinsider.jspkit.jaxp;

import java.io.IOException;
import java.io.File;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import javax.servlet.*;
import java.io.StringReader;
import java.io.StringWriter;

public class JAXPTransformerXMLFileTag extends BodyTagSupport
{
  /* The JSP container calls this function when it encounters the 
     end of the Tag. */
 public int doEndTag() throws JspTagException
	{
	try
	{

	BodyContent lbc_bodycurrent = getBodyContent();
	StringWriter sw = new StringWriter();
	lbc_bodycurrent.writeOut(sw);
	File f = new File(sw.toString());
	JAXPTransformerTag tag = (JAXPTransformerTag)findAncestorWithClass(this, com.jspinsider.jspkit.jaxp.JAXPTransformerTag.class);
	tag.setXMLStreamSource(new StreamSource(f));
	}
	catch (Exception e)
	{
	throw new JspTagException("JSP Kit JAXPTransformerXMLFileTag Error:" + e.toString());
	}

	// Have the JSP Container continue processing the JSP page as normal.
	return EVAL_PAGE;
 }
}


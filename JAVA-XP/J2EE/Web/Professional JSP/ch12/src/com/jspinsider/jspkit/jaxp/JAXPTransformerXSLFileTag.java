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

public class JAXPTransformerXSLFileTag extends BodyTagSupport
{
public int doEndTag() throws JspTagException
	{
	try
	{   /* Gather up the contents between the start and end of our tag */
	BodyContent lbc_bodycurrent = getBodyContent();
	StringWriter sw = new StringWriter();
	lbc_bodycurrent.writeOut(sw);
	File f = new File(sw.toString());
	JAXPTransformerTag tag = (JAXPTransformerTag)findAncestorWithClass(this, com.jspinsider.jspkit.jaxp.JAXPTransformerTag.class);

	tag.setXSLStreamSource(new StreamSource(f));
	}
	catch (Exception e)
	{
	throw new JspTagException("JSP Kit JAXPTransformerXSLFileTag Error:" + e.toString());
	}
	// Have the JSP Container continue processing the JSP page as normal.
	return EVAL_PAGE;
 }
}

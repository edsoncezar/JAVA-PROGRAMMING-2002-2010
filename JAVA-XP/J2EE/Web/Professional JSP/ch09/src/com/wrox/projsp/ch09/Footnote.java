package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class Footnote extends TagSupport {

    public int doStartTag() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();

	    out.print("<TABLE ALIGN=\"right\" BGCOLOR=\"#CCCCCC\">");
	    out.print("<TR><TD><I>");
	} catch (Exception ioException) {
	    System.err.println("Exception thrown in Footnote.doStartTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_BODY_INCLUDE;
    } 

    public int doEndTag() throws JspException {
	try {
	    pageContext.getOut().print("</I></TD></TR></TABLE>");
	} catch (Exception ioException) {
	    System.err.println("Exception thrown in Footnote.doEndTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_PAGE;
    } 

}

package com.wrox.projsp.ch10;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class CallTrace extends BodyTagSupport {
    private int		     iterationCounter = 0;
    private static final int NUMBER_OF_ITERATIONS = 3;
    private String	     name = "";

    public String getName() throws JspException {
	try {
	    pageContext.getOut().print("<h6>getName() called.</h6>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.getName():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return name;
    } 

    public void setName(String newName) throws JspException {
	name = newName;

	try {
	    pageContext.getOut().print("<h6>setName() called.</h6>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.setName():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 
    } 

    public int doStartTag() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();

	    out.print("<h2>doStartTag() called - ");
	    out.print("iterating " + NUMBER_OF_ITERATIONS + " times</h2>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.doStartTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_BODY_BUFFERED;
    } 

    public void setBodyContent(BodyContent body) {
	super.setBodyContent(body);

	try {
	    pageContext.getOut().print("<h3>setBodyContent() called</h3>");
	} catch (Exception ioException) {
	    System.err.println("Exception thrown in CallTrace.setBodyContent()");
	    System.err.println(ioException.toString());

	    // Can't throw JspException from this method - it's not part of
	    // the method signature.
	} 
    } 

    public void doInitBody() throws JspException {
	try {
	    pageContext.getOut().print("<h4>doInitBody() called</h4>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.doInitBody():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 
    } 

    public int doAfterBody() throws JspException {
	int repeatBody = SKIP_BODY;

	try {
	    JspWriter out = pageContext.getOut();

	    out.print("<h5>doAfterBody() called - ");
	    out.print("iteration " + iterationCounter);

	    if (iterationCounter < NUMBER_OF_ITERATIONS) {
		repeatBody = EVAL_BODY_AGAIN;
		iterationCounter++;
	    } else {
		out.print(", stopping.");
	    } 

	    out.print("</h5>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.doAfterBody():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return repeatBody;
    } 

    public int doEndTag() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();

	    this.getBodyContent().writeOut(out);
	    out.print("<h2>doEndTag() called</h2>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in CallTrace.doEndTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_PAGE;
    } 

}

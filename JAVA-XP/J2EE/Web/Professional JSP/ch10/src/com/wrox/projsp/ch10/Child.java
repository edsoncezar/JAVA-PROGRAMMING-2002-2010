package com.wrox.projsp.ch10;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class Child extends BodyTagSupport {
    private int      arrayCounter = 0;
    private String[] array = null;

    public int doStartTag() throws JspException {
	Parent parentTag = (Parent) this.findAncestorWithClass(this, 
		Parent.class);

	this.array = parentTag.getArray();

	return EVAL_BODY_BUFFERED;
    } 

    public int doAfterBody() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();

	    out.print(" [" + this.arrayCounter + "]: ");
	    out.print(this.array[this.arrayCounter] + "<br>");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in Child.doAfterBody():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	int repeatOrSkip = SKIP_BODY;

	if (this.arrayCounter < (this.array.length - 1)) {
	    repeatOrSkip = EVAL_BODY_AGAIN;
	    this.arrayCounter++;
	} 

	return repeatOrSkip;
    } 

    public int doEndTag() throws JspException {
	try {
	    this.getBodyContent().writeOut(pageContext.getOut());
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in Child.doEndTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_PAGE;
    } 

}

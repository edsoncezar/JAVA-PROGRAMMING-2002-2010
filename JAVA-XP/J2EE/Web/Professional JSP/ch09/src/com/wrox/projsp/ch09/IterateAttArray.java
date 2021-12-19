package com.wrox.projsp.ch09;

import java.lang.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class IterateAttArray extends TagSupport {
    private String[] array = null;
    private int      arrayCounter = 0;
    private String   arrayName;

    public String getName() {
	return this.arrayName;
    } 

    public void setName(String newArrayName) {
	this.arrayName = newArrayName;
    } 

    public int doStartTag() throws JspException {
	this.array = (String[]) pageContext.getAttribute(this.getName());

	return EVAL_BODY_INCLUDE;
    } 

    public int doAfterBody() throws JspException {
	try {
	    JspWriter out = pageContext.getOut();

	    out.print(" [" + this.arrayCounter + "]: ");
	    out.print(this.array[this.arrayCounter] + "<BR>");
	} catch (Exception ioException) {
	    System.err.println("Exception thrown in doAfterBody():");
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

}

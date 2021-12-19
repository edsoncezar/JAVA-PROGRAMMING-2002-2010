package com.wrox.projsp.ch10;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class Validation extends TagSupport {
    String firstName = "";

    public String getFirstName() {
	return this.firstName;
    } 

    public void setFirstName(String newFirstName) {
	this.firstName = newFirstName;
    } 

    public int doStartTag() throws JspException {
	try {
	    pageContext.getOut().print("First name is: " + this.firstName);
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in Validation.doStartTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return SKIP_BODY;
    } 

}

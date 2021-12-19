package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class HelloUser extends TagSupport {
    private String username = "";

    public String getUsername() {
	return this.username;
    } 

    public void setUsername(String newUsername) {
	this.username = newUsername;
    } 

    public int doStartTag() throws JspException {
	try {
	    pageContext.getOut().print("Hello " + this.getUsername() + "!");
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in HelloUser.doStartTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return SKIP_BODY;
    } 

}

package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ShowAttArray extends TagSupport {
    private String arrayName;

    public String getName() {
	return this.arrayName;
    } 

    public void setName(String newArrayName) {
	this.arrayName = newArrayName;
    } 

    public int doStartTag() throws JspException {
	String[] myArray;

	myArray = (String[]) pageContext.getAttribute(this.getName());

	String html = "<H1>Array values from page attributes</H1><UL>";

	for (int counter = 0; counter < myArray.length; counter++) {

	    // Naturally a StringBuffer.append () would be used in
	    // production code for improved performance.
	    html += "<LI>" + myArray[counter];
	} 

	html += "</UL>";

	try {
	    pageContext.getOut().print(html);
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in doStartTag():");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return SKIP_BODY;
    } 

}


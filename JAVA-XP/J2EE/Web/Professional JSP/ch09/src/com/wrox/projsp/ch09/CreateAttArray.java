package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class CreateAttArray extends TagSupport {
    private String arrayName = "";

    public String getName() {
	return this.arrayName;
    } 

    public void setName(String newArrayName) {
	this.arrayName = newArrayName;
    } 

    public int doStartTag() throws JspException {
	String[] myArray = new String[5];

	myArray[0] = "Homer";
	myArray[1] = "Marge";
	myArray[2] = "Bart";
	myArray[3] = "Lisa";
	myArray[4] = "Maggie";

	pageContext.setAttribute(this.getName(), myArray);

	return SKIP_BODY;
    } 

}


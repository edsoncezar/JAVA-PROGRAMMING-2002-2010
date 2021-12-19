package com.wrox.projsp.ch10;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class Parent extends TagSupport {
    private String[] array = null;

    public String[] getArray() {
	return array;
    } 

    public void setArray(String[] newArray) {
	this.array = newArray;
    } 

    public int doStartTag() throws JspException {
	this.array = new String[5];
	this.array[0] = "Homer";
	this.array[1] = "Marge";
	this.array[2] = "Bart";
	this.array[3] = "Lisa";
	this.array[4] = "Maggie";

	return EVAL_BODY_INCLUDE;
    } 

    public int doEndTag() throws JspException {
	this.array = null;

	return EVAL_PAGE;
    } 

}

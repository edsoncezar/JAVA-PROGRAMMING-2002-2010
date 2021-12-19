package com.wrox.projsp.ch09;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class AdminOnly extends TagSupport {
    private String username = "";

    public String getUsername() {
        return this.username;
    } 

    public void setUsername(String newUsername) {
	this.username = newUsername;
    } 

    public int doStartTag() throws JspException {
	int processBodyOrNot = SKIP_BODY;

	if (this.getUsername().equals("Lisa")) {
	    processBodyOrNot = EVAL_BODY_INCLUDE;
	} 

	return processBodyOrNot;
    } 

}

package com.wrox.projsp.ch10;

import javax.servlet.jsp.tagext.*;

public class ValidationExtraInfo extends TagExtraInfo {

    public boolean isValid(TagData data) {
	String  myFirstNameAtt = (String) data.getAttribute("firstName");
	boolean isASimpson = false;

	if (myFirstNameAtt.equals("Homer") || myFirstNameAtt.equals("Marge") 
		|| myFirstNameAtt.equals("Bart") 
		|| myFirstNameAtt.equals("Lisa") 
		|| myFirstNameAtt.equals("Maggie")) {
	    isASimpson = true;
	} 

	return isASimpson;
    } 

}

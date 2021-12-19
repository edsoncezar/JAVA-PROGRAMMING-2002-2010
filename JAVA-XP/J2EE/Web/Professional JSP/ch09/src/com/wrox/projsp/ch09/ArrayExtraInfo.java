package com.wrox.projsp.ch09;

import javax.servlet.jsp.tagext.*;

public class ArrayExtraInfo extends TagExtraInfo {

    public VariableInfo[] getVariableInfo(TagData data) {
	String	       myArrayName = (String) data.getAttribute("name");
	VariableInfo   myArrayInfo = new VariableInfo(myArrayName, 
		"String []", true, VariableInfo.AT_END);
	VariableInfo[] myTagVariables = new VariableInfo[1];

	myTagVariables[0] = myArrayInfo;

	return myTagVariables;
    } 

}


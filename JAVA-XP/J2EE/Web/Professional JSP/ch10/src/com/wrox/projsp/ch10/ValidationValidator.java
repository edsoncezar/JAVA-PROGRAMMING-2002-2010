package com.wrox.projsp.ch10;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ValidationValidator extends TagLibraryValidator {
    private int PAGE_BUFFER_SIZE = 8192;

    private String getAttributeValue(String attribute, String tag, 
				     PageData page) throws IOException, 
				     JspException {
	String      attValue = null;
	InputStream pageStream = page.getInputStream();
	byte[]      pageText = new byte[PAGE_BUFFER_SIZE];

	pageStream.read(pageText, 0, PAGE_BUFFER_SIZE);

	String pageString = new String(pageText);
	int    foundAt = pageString.indexOf(tag);

	if (foundAt < 0) {
	    throw new JspException("Could not locate tag " + tag 
				   + " in XML document.");
	} 

	int attFoundAt = pageString.indexOf(attribute + "=\"", foundAt);

	if (attFoundAt < 0) {
	    throw new JspException("Tag " + tag + " contained no \"" 
				   + attribute + "\" attribute.");
	} 

	int closingDelimiterFoundAt = pageString.indexOf(">", foundAt);

	if (closingDelimiterFoundAt < attFoundAt) {
	    throw new JspException("Tag " + tag + " closed before finding \"" 
				   + attribute + "\" attribute.");
	} 

	int startAt = attFoundAt + attribute.length() + "=\"".length();
	int endAt = pageString.indexOf("\"", startAt);

	attValue = pageString.substring(startAt, endAt);

	return attValue;
    } 

    public String validate(String prefix, String uri, PageData page) {
	String returnedErrorOrSuccess;

	try {
	    String tag = prefix + ":validation";
	    String firstName = this.getAttributeValue("firstName", tag, page);

	    if (firstName.equals("Homer") || firstName.equals("Marge") 
		    || firstName.equals("Bart") || firstName.equals("Lisa") 
		    || firstName.equals("Maggie")) {
		returnedErrorOrSuccess = null;
	    } else {
		returnedErrorOrSuccess = "firstName attribute value \"" 
					 + firstName 
					 + "\" is not a valid value.";
	    } 
	} catch (Exception ioException) {
	    System.err.println("IO Exception thrown in " 
			       + "ValidationValidator.validate():");
	    System.err.println(ioException.toString());

	    returnedErrorOrSuccess = ioException.toString();
	} 

	return returnedErrorOrSuccess;
    } 

}


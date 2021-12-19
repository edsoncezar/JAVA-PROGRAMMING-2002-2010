package com.wrox.projsp.ch10;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ProfanityFilter extends BodyTagSupport {

    private String filterWord(String bodyToFilter, String wordToRemove, 
			      String wordToUse) {
	StringBuffer filteredContent = new StringBuffer();
	int	     previousMarker = 0;
	int	     foundWordAt = bodyToFilter.indexOf(wordToRemove, 
		previousMarker);

	while (foundWordAt != -1) {
	    filteredContent.append(bodyToFilter.substring(previousMarker, 
		    foundWordAt));
	    filteredContent.append(wordToUse);

	    previousMarker = foundWordAt + wordToRemove.length();
	    foundWordAt = bodyToFilter.indexOf(wordToRemove, previousMarker);
	} 

	filteredContent.append(bodyToFilter.substring(previousMarker));

	return filteredContent.toString();
    } 

    public int doEndTag() throws JspException {
	try {
	    String currentContent = this.getBodyContent().getString();
	    String filteredContent;

	    filteredContent = this.filterWord(currentContent, "D'oh", "****");
	    filteredContent = this.filterWord(filteredContent, "D'uh", 
					      "****");

	    this.getBodyContent().clear();
	    this.getBodyContent().print(filteredContent);
	    this.getBodyContent().writeOut(pageContext.getOut());
	} catch (Exception ioException) {
	    System.err.println("Exception thrown in ProfanityFilter.doEndTag()");
	    System.err.println(ioException.toString());

	    throw new JspException(ioException);
	} 

	return EVAL_PAGE;
    } 

}

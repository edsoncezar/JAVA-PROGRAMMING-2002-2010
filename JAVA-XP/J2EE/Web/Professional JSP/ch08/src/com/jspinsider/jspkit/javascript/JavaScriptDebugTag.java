/*
 * Amberjack Opensource Project : Java Server Pages Quick Start Class library
 * Copyright (C) 2001 AmberJack Software
 * http://www.jspinsider.com/jspkit/index.html
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * The full license is located at the root of this distribution
 * in the LICENSE file.
 * 
 * 
 * JavaScriptDebugTag.java  v 0.02.01012001  Casey Kochmer Casey@JspInsider.com
 * 
 * Created on Jan 01, 2001,
 */
package com.jspinsider.jspkit.javascript;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import javax.servlet.jsp.PageContext.*;
import javax.servlet.*;

public class JavaScriptDebugTag 
    extends BodyTagSupport {    /* Define the Tag Attributes we will use */

    /* Which collection to view in the alert box */
    private String view = "";

    public void setView(String as_object) {
	this.view = as_object.trim().toLowerCase();
    } 

    /* Prevent alert from displaying */
    private boolean display = true;

    public void setDisplay(boolean ab_display) {
	this.display = ab_display;
    } 

    /* The JSP container calls this function when it encounters the end of the Tag. */

    public int doEndTag() 
	    throws JspTagException {    /* Don't do anything if user prevents alert */
	if (display == false) {
	    return EVAL_PAGE;

	    /* initialize our variables */
	} 

	String ls_alert = "";
	String ls_message = "";
	String ls_data = "";
	String ls_current = "";
	int    li_scope = 0;

	/* Determine which implicit objects attributes user wants to see */
	if (view.equals("session")) {
	    li_scope = PageContext.SESSION_SCOPE;
	} 

	if (view.equals("request")) {
	    li_scope = PageContext.REQUEST_SCOPE;
	} 

	if (view.equals("application")) {
	    li_scope = PageContext.APPLICATION_SCOPE;
	} 

	if (view.equals("page")) {
	    li_scope = PageContext.PAGE_SCOPE;

	    /*
	     * If user requested to see implicit object loop thru
	     * and build list of variables stashed in the object
	     */
	} 

	if (li_scope > 0) {    /* Loop thru and get all attributes stashed away */
	    Enumeration enum_app = 
		pageContext.getAttributeNamesInScope(li_scope);

	    ls_data += " Data stored within " + view;

	    for (; enum_app.hasMoreElements(); ) {
		ls_current = enum_app.nextElement().toString();

		if (ls_current != null) {
		    ls_data += "\\n Attribute: " + ls_current + "  = ";

		    Object results = pageContext.getAttribute(ls_current, 
							      li_scope);

		    if (results != null) {
			ls_data += results.toString().trim();
		    } 
		} 
	    } 

	    /*
	     * If it's a Request object then also get passed parameters
	     * This simple loop won't catch multi element data elements
	     */
	    if (li_scope == PageContext.REQUEST_SCOPE) {
		ServletRequest lparms = pageContext.getRequest();
		Enumeration    req_data = lparms.getParameterNames();

		ls_data += "\\n Parameters within Request ";

		for (; req_data.hasMoreElements(); ) {
		    ls_current = req_data.nextElement().toString();

		    if (ls_current != null) {
			ls_data += "\\n element: " + ls_current + "  = ";

			Object results = lparms.getParameter(ls_current);

			if (results != null) {
			    ls_data += results.toString().trim();
			} 
		    } 
		} 
	    } 
	} 

	/*
	 * Get any data in the tag body. Then take everything and build an
	 * Javascript alertbox
	 */
	try {    /* Gather up the contents between the start and end of our tag */
	    BodyContent lbc_bodycurrent = getBodyContent();

	    /* Create our JavaScript object which will build the alert */
	    JavaScript  JS = new JavaScript();

	    if (lbc_bodycurrent != null) {
		ls_message = lbc_bodycurrent.getString().trim();
	    } 

	    /* Create our alert box */
	    ls_alert = JS.alert(ls_message + "\\n" + ls_data);

	    /* print out the alert box to the JSP Page */
	    pageContext.getOut().write(ls_alert);
	} catch (IOException e) {
	    throw new JspTagException("JavaScriptDebugTag Error:" 
				      + e.toString());
	} 

	// Have the JSP Container continue processing the JSP page as normal.
	return EVAL_PAGE;
    } 

}


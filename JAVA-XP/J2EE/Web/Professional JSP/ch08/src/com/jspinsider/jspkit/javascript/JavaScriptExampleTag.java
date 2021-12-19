/*  Amberjack Opensource Project : Java Server Pages Quick Start Class library
 *  Copyright (C) 2001 JspInsider
 *  http://www.jspinsider.com/jspkit/index.html
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  The full license is located at the root of this distribution
 *  in the LICENSE file.
 
/*
 *  JavaScriptExampleTag.java  v 0.02.01082001  Casey Kochmer Casey@JspInsider.com
 *
 *  Created on Jan 08, 2001,
 */

package com.jspinsider.jspkit.javascript;
import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;


public class JavaScriptExampleTag extends BodyTagSupport
{

    public int doEndTag() throws JspTagException
    { String ls_alert   = "";
    
    try
    {   BodyContent lbc_bodycurrent = getBodyContent();
        
        if(lbc_bodycurrent != null)
        { 
        String ls_message = lbc_bodycurrent.getString();
	   JavaScriptExample  JS = new JavaScriptExample();
        ls_alert = JS.alert(ls_message.trim());
        }
        pageContext.getOut().write(ls_alert);
    }

    catch (IOException e)
    { throw new JspTagException("Error" + e.toString());
    } 
    return EVAL_PAGE;
    }
}

/*  Amberjack Opensource Project : Java Server Pages Quick Start Class library 
 *  Copyright (C) 2000 JspInsider
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
 *  JavaScriptTag.java  v 0.02.01012001  Casey Kochmer Casey@JspInsider.com
 *
 *  Created on Jan 01, 2001, 
 */
 
package com.jspinsider.jspkit.javascript;

import java.io.IOException;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class JavaScriptTag extends BodyTagSupport 
{
    /* Used to indicate which event triggers the alert box */  
    private String event = "";
    
    public void setEvent(String as_event) 
    {
        this.event = as_event;
    }
       
    /* Used to indicate which element the event is associated with*/  
    private String element = "";
    
    public void setElement(String as_element) 
    {
        this.element = as_element;
    }
    
    /* Used to give the newly created alert function an unique name*/
    private String name = "";
    
    public void setName(String as_name) 
    {
        this.name = as_name;
    }
    
   /* The JSP container calls this function when it encounters the end of the Tag. */
  public int doEndTag() throws JspTagException
  { String ls_alert   = "";
    String ls_display = "";
    
    try
    {   /* Gather up the contents between the start and end of our tag */
        BodyContent lbc_bodycurrent = getBodyContent();
        
        if(lbc_bodycurrent != null)
        { JavaScript  JS = new JavaScript();
          String ls_message = lbc_bodycurrent.getString() + ls_display;
          if (event.length() > 0 && event.length() >0)
          {
            ls_alert = JS.alert(ls_message.trim(),name,element,event);
          }
          else
          {
            ls_alert = JS.alert(ls_message.trim());
          }
        }
        pageContext.getOut().write(ls_alert);
    }
    catch (IOException e)
    {
        throw new JspTagException("JSP Kit JavaScriptTag Error:" + e.toString());
    }
    
    // Have the JSP Container continue processing the JSP page as normal.
    return EVAL_PAGE;
  }
  
  

}
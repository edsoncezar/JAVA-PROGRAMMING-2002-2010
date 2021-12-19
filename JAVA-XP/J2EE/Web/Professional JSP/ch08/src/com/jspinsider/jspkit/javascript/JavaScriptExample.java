/*  Amberjack Opensource Project : Java Server Pages Quick Start Class library 
 *  Copyright © 2001 Amberjack Software llc
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
 */

/*
 * JavaScriptExample.java  v 0.2.01042001  Casey Kochmer Casey@JspInsider.com
 *
 * Created on Jan 04, 2001, 
 */
 
package com.jspinsider.jspkit.javascript;

public class JavaScriptExample extends java.lang.Object implements java.io.Serializable
{
  
  public JavaScriptExample()
  {
  }
  
private String  start_script   ="<script language=\"JavaScript\">";
private String  end_script     ="</script>";

public String alert(Object aobj_data)
{  return( start_script + 
           " alert(\""  + aobj_data.toString() + "\");" +
           end_script);
}


}
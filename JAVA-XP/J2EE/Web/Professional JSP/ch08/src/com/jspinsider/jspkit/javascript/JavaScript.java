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
 * JavaScript.java  v 0.2.01042001  Casey Kochmer Casey@JspInsider.com
 *
 * Created on Jan 04, 2001, 
 */
 
package com.jspinsider.jspkit.javascript;

import java.util.*;


public class JavaScript extends java.lang.Object implements java.io.Serializable
{
  
  /** Creates new JavaScript Object */
  public JavaScript()
  {
       broswer_validation =  "var isIE = (navigator.appName == \"Microsoft Internet Explorer\") ? 1 : 0;";
       broswer_validation += "var isNS = (navigator.appName == \"Netscape\") ? 1 : 0;";
       broswer_validation += "var isNS4 = (navigator.appName == \"Netscape\" && parseInt(navigator.appVersion) < 5);";
  }
  
/****************** General Functions *************************/
private String  start_script   ="<script language=\"JavaScript\" >";
private String  end_script   ="</script>";   
private String  broswer_validation = "";

/*******   Build a Client Side string to display a JavaScript Alert *****/

     public String alert(Object aobj_data)
     {  
        return( start_script + " alert(\"" + aobj_data.toString() + "\");" + end_script);
     }

     public String alert(Object aobj_data, String as_name, String as_element, String as_event)
     {  
        /* Since netscape 6.0 doesn't use "on" prefixes for events strip it out 
           if we find it */
        String ls_test = as_event.trim().substring(0,2).toLowerCase();
        if (ls_test.equals("on")) { as_event = as_event.substring(2,as_event.length());}
        
        String ls_start_alert = start_script + broswer_validation;
        String ls_event_alert = "function " + as_name + "(){alert(\"" + aobj_data.toString() + "\");}" ;
        String ls_end_alert   = end_script;
        
        String ls_alert = ls_start_alert;
        ls_alert += "if (isIE)  " + as_element + ".on" + as_event + " = " + as_name + "; ";
        ls_alert += "if (isNS && !isNS4) " +  as_element + ".addEventListener(\"" + as_event + "\","+  as_name + ", true);";
        ls_alert += ls_event_alert;
        ls_alert += ls_end_alert;
        
        return(ls_alert);
     }
       
}
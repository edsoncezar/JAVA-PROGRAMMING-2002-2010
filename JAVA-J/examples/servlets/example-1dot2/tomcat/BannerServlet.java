/*
Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.

This software is the confidential and proprietary information of Sun
Microsystems, Inc. ("Confidential Information").  You shall not
disclose such Confidential Information and shall use it only in
accordance with the terms of the license agreement you entered into
with Sun.

SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
THIS SOFTWARE OR ITS DERIVATIVES.

CopyrightVersion 1.0
*/

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import database.*;
import cart.*;

/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * method of the HTTP protocol.
 */
public class BannerServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
		}
    public void doPost (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> ");
		}
}
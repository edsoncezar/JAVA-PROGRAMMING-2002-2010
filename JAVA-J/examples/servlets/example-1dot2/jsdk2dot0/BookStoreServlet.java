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
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * An HTTP Servlet that overrides the service method to return a
 * simple web page.
 */
public class BookStoreServlet extends HttpServlet { 

    public void service (HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException
    {
        // set content-type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // then write the data of the response
        out.println("<html>" +
                    "<head><title>Duke's Bookstore</title></head>" +

                    "<body bgcolor=\"#FFFFFF\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> &nbsp;");
            
        //Align the parts of the page in a table (one row with two cells)
        out.println("<table border=0 cellspacing=5 cellpadding=5>" +
                    "<tr>");
        
        //Left cell -- the "book of choice"
        out.println("<td valign=\"TOP\" width=\"55%\">" +
                    "<h3>What We're Reading</h3>" +
                    "<p>" +
                    
                    "In <em><a href=\"" +
                    response.encodeUrl("/servlet/bookdetails?bookId=203") +
                    "\"> Servlets: A Web Developer's" +
                    "Dream Come True</a></em>, author Webster Masterson, "
                    + "a world renowned guru on" +
                    "web development, talks about how servlets can " +
                    "transform the way you develop" +
                    "applications for the web.  This is a must read for " +
                    "any self respecting web developer!" +
                    "</td>");
        
        //Right cell -- various navigation options
        out.println("<td valign=\"TOP\" bgcolor=\"#FFFFAA\">" +
                    "<p><font size=\"+1\"><a href=\"" +
                    response.encodeUrl("/servlet/catalog") +
                    "\">Catalog</a></font><br>" +
                    "Choose from our excellent selection of books." +
                    
                    "<p><font size=\"+1\"><a href=\"" +
                    response.encodeUrl("/servlet/showcart") +
                    "\">Shopping Cart</a></font><br>" +
                    "Look at your shopping cart to see the books " +
                    "you've chosen" +
                    
                    "<p>" +
                    "<font size=\"+1\"><a href=\"" +
                    response.encodeUrl("/servlet/cashier") +
                    "\">Buy your Books</a></font><br>" +
                    "Pay for the books you've put in your cart!" +
                    
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    
                    "<br> &nbsp;" +
                    "<br> &nbsp;" +
                    "<br> &nbsp;" +
                    "<center><em>Copyright &copy; " +
                    "1998 Sun Microsystems, Inc. </em></center>" +
                    "</body>" +
                    "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "The BookStore servlet returns the main web page " +
               "for Duke's Bookstore.";
    }
}

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
import cart.ShoppingCart;

/**
 * An HTTP servlet that responds to the POST method of the HTTP protocol.
 * It clears the shopping cart, thanks the user for the order,
 * and resets the page to the BookStore's main page.
 */

public class ReceiptServlet extends HttpServlet { 

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
	throws ServletException, IOException
    {
        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getValue(session.getId());
        
        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.putValue(session.getId(), cart);
        }
        
        // Payment received -- invalidate the session
        session.invalidate();
        
        // set content type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // then write the response
        out.println("<html>" +
                    "<head><title> Receipt </title>" +
                    "<meta http-equiv=\"refresh\" content=\"4; url=" +
                    "http://" + request.getHeader("Host") +
                    "/servlet/bookstore;\">" +
                    "</head>" +
                    
                    "<body  bgcolor=\"#FFFFFF\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> &nbsp;");
        
        out.println("<h3>Thank you for purchasing your books from us " +
                    request.getParameter("cardname") +
                    "<p>Please shop with us again soon!</h3>" +
                    
                    "<p><i>This page automatically resets.</i>" +

                    "</body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Receipt servlet clears the shopping cart, " +
               "thanks the user for the order, and resets the " +
               "page to the BookStore's main page.";
    }
}


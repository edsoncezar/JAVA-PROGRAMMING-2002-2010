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
import cart.*;

/**
 * An HTTP Servlet that responds to the GET method of the
 * HTTP protocol.  It returns a form to the user that gathers data.
 * The form POSTs to another servlet.
 */
public class CashierServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
        ShoppingCart cart =
            (ShoppingCart)session.getAttribute("examples.bookstore.cart");
        
        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("examples.bookstore.cart", cart);
   			}

   // set content-type header before accessing Writer
        response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<html>" +
                    "<head><title> Cashier </title></head>" );
                              
        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);

        // Determine the total price of the user's books
        double total = cart.getTotal();

        // Print out the total and the form for the user
        out.println("<p>Your total purchase amount is: " +
                    "<strong>" + Currency.format(total, request.getLocale()) + "</strong>" +
                    "<p>To purchase the items in your shopping cart," +
                    " please provide us with the following information:" +

                    "<form action=\"" +
                    response.encodeURL("/bookstore/receipt") +
                    "\" method=\"post\">" +

                    "<table>" +
                    "<tr>" +
                    "<td><strong>Name:</strong></td>" +
                    "<td><input type=\"text\" name=\"cardname\"" +
                    "value=\"Gwen Canigetit\" size=\"19\"></td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td><strong>Credit Card Number:</strong></td>" +
                    "<td>" +
                    "<input type=\"text\" name=\"cardnum\" " +
                    "value=\"xxxx xxxx xxxx xxxx\" size=\"19\"></td>" +
                    "</tr>" +

                    "<tr>" +
                    "<td></td>" +
                    "<td><input type=\"submit\"" +
                    "value=\"Submit Information\"></td>" +
                    "</tr>" +

                    "</table>" +
                    "</form>" +
                    "</body>" +
                    "</html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Cashier servlet takes the user's name and " +
               "credit card number so that the user can buy the books.";

    }
}

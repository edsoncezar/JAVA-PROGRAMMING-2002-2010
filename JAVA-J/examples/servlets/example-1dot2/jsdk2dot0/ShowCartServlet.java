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
import javax.servlet.*;
import javax.servlet.http.*;
import database.*;
import cart.*;

/**
 * An HTTP servlet that displays the contents of a customer's shopping
 * cart at Duke's Bookstore.  It responds to the GET and HEAD methods of
 * the HTTP protocol.  This servlet calls other servlets.
 */
public class ShowCartServlet extends HttpServlet { 

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
	throws ServletException, IOException
    {
        // Get the user's session and shopping cart
	HttpSession session = request.getSession(true);
	ShoppingCart cart = (ShoppingCart)session.getValue(session.getId());

        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.putValue(session.getId(), cart);
	}

	// set content type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Print out the response
        out.println("<html>" +
                    "<head><title>Your Shopping Cart</title></head>" +

                    "<body  bgcolor=\"#ffffff\">" +
                    "<center> <hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> &nbsp;");

        /* Handle any pending deletes from the shopping cart and
           indicate the outcome as part of the response */
        String bookId =request.getParameter("Remove");
        if (bookId != null) {
            cart.remove(bookId);

            BookDBServlet database = (BookDBServlet)
                getServletConfig().getServletContext().getServlet("bookdb");
            BookDetails book = database.getBookDetails(bookId);
            
            out.println("<font color=\"#ff00000\" size=\"+2\">" +
                        "You just removed: <strong>" + book.getTitle() +
                        "</strong> <br> &nbsp; <br>" +
                        "</font>");
            
        } else if (request.getParameter("Clear") != null) {
            cart.clear();
            
            out.println("<font color=\"#ff0000\" size=\"+2\"><strong>" +
                        "You just cleared your shopping cart!" +
                        "</strong> <br>&nbsp; <br> </font>");
        }

        // Print a summary of the shopping cart
        int num = cart.getNumberOfItems();
        if (num > 0) {
            out.println("<font size=\"+2\">" +
                        "You have " + num + (num==1 ? " item" : " items") +
                        " in your shopping cart </h2>" +
                        "<font>");

            // Return the Shopping Cart Nice and Pretty
            out.println("<table>" +
                        "<tr>" +
                        "<th align=left>Quantity</TH>" +
                        "<th align=left>Title</TH>" +
                        "<th align=left>Price</TH>" +
                        "</tr>");
            
            Enumeration e = cart.getItems();
            while (e.hasMoreElements()) {
                ShoppingCartItem item = (ShoppingCartItem) e.nextElement();
                BookDetails bookDetails = (BookDetails) item.getItem();
                
                out.println("<tr>" +
                            "<td align=\"right\" bgcolor=\"#ffffff\">" +
                            item.getQuantity() +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\">" +
                            "<strong><a href=\"" +
                            response.encodeUrl("/servlet/bookdetails?bookId=" +
                                               bookDetails.getBookId()) +
                            "\">" + bookDetails.getTitle() + "</a></strong>" +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\" align=\"right\">" +
                            Cashier.format(bookDetails.getPrice()) +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\">" +
                            "<strong>" + "<a href=\"" +
                            response.encodeUrl("/servlet/showcart?Remove=" +
                                               bookDetails.getBookId())
                            + "\">Remove Item</a></strong>" +
                            "</td></tr>");
            }

            // Print the total at the bottom of the table
            Cashier cashier = new Cashier(cart);
            out.println("<tr><td colspan=\"5\" bgcolor=\"#ffffff\">" +
                        "<br></td></tr>" +

                        "<tr>" +
                        "<td colspan=\"2\" align=\"right\"" +
                        "bgcolor=\"#ffffff\">" +
                        "Subtotal:</td>" +
                        "<td bgcolor=\"#ffffaa\" align=\"right\">" +
                        cashier.format(cashier.getAmount()) + "</td>" +
                        "</td><td><br></td></tr>" +

                        "<tr>" +
                        "<td colspan=\"2\" align=\"right\"" +
                        "bgcolor=\"#ffffff\">" +
                        "CA Sales Tax:</td>" +
                        "<td bgcolor=\"#ffffaa\" align=\"right\">" +
                        cashier.format(cashier.getTax()) + "</td>" +
                        "</td><td><br></td></tr>" +

                        "<tr>" +
                        "<td colspan=\"2\" align=\"right\"" +
                        "bgcolor=\"ffffff\">" +
                        "<font color=\"ff0000\">" +
                        "<strong>Grand Total:</strong></font></td>" +
                        "<td bgcolor=\"ffffaa\" align=\"right\">" +
                        cashier.format(cashier.getTotal()) + "</td>" +
                        "</td><td><br></td></tr>" +
                        "</table>");
            
            // Where to go and what to do next
            out.println("<p> &nbsp; <p><a href=\"" +
                        response.encodeUrl("/servlet/catalog") +
                        "\">See the Catalog</a> &nbsp; &nbsp; &nbsp;" +
                        
                        "<a href=\"" +
                        response.encodeUrl("/servlet/cashier") +
                        "\">Check Out</a> &nbsp; &nbsp; &nbsp;" +
                        
                        "<a href=\"" + 
                        response.encodeUrl("/servlet/showcart?Clear=clear") +
                        "\">Clear Cart</a>");
        } else {

            // Shopping cart is empty!
            out.println("<font size=\"+2\">" +
                        "There is nothing in your shopping cart.</font>" +
                        "<br> &nbsp; <br>" +
                        "<center><a href=\""+
                        response.encodeUrl("/servlet/catalog") +
                        "\">Back to the Catalog</a> </center>");
        }

        out.println("</body> </html>");
        out.close();
    }
        
    
    public String getServletInfo() {
        return "The ShowCart servlet returns information about" +
               "the books that the user is in the process of ordering.";
    }
}

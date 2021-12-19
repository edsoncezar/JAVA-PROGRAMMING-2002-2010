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

import database.*;
import cart.ShoppingCart;

/**
 * This is a simple example of an HTTP Servlet.  It responds to the GET
 * and HEAD methods of the HTTP protocol.  This servlet calls other
 * servlets.  This catalog calls other servlets.
 */
public class CatalogServlet extends HttpServlet { 

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

	// set content-type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

	// then write the data of the response
        out.println("<html>" +
                    "<head><title> Book Catalog </title></head>" +

                    "<body  bgcolor=\"#ffffff\">" +
                    "<center>" +
                    "<hr> <br> &nbsp;" +
                    "<h1>" +
                    "<font size=\"+3\" color=\"red\">Duke's </font>" +
                    "<font size=\"+3\" color=\"purple\">Bookstore</font>" +
                    "</h1>" +
                    "</center>" +
                    "<br> &nbsp; <hr> <br> &nbsp;");

        //Data on the books are from the database servlet
        BookDBServlet database = (BookDBServlet)
            getServletConfig().getServletContext().getServlet("bookdb");

        // Additions to the shopping cart
        String bookToAdd = request.getParameter("Buy");
        if (bookToAdd != null) {
            BookDetails book = database.getBookDetails(bookToAdd);

            cart.add(bookToAdd, book);
            out.println("<p><h3>" +
                        "<font color=\"#ff0000\">" +
                        "You just added <i>" + book.getTitle() + "</i> "+
                        "to your shopping cart</font></h3>");
        }

        //Give the option of checking cart or checking out if cart not empty
        if (cart.getNumberOfItems() > 0) {
            out.println("<table><tr>" +
                        "<th align=\"left\"><a href=\"" +
                        response.encodeUrl("/servlet/showcart") +
                        "\"> Check Shopping Cart</a></th>" +

                        "<th>&nbsp;</th>" +

                        "<th align=\"right\"><a href=\"" +
                        response.encodeUrl("/servlet/cashier") +
                        "\"> Buy your Books</a></th>" +
                        "</tr></table");
        }

        // Always prompt the user to buy more -- get and show the catalog
        out.println("<br> &nbsp;" +
                    "<h3>Please choose from our selections</h3>" +
                    "<center> <table>");

        BookDetails[] books = database.getBooksSortedByTitle();
        int numBooks = database.getNumberOfBooks();
        for(int i=0; i < numBooks; i++) {
            String bookId = books[i].getBookId();

            //Print out info on each book in its own two rows
            out.println("<tr>" +

                        "<td bgcolor=\"#ffffaa\">" +
                        "<a href=\"" +
                        response.encodeUrl("/servlet/bookdetails?bookId=" +
                                           bookId) +
                        "\"> <strong>" + books[i].getTitle() +
                        "&nbsp; </strong></a></td>" +

                        "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                        "$" + books[i].getPrice() +
                        "&nbsp; </td>" +

                        "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                        "<a href=\"" +
                        response.encodeUrl("/servlet/catalog?Buy=" + bookId)
                        + "\"> &nbsp; Add to Cart &nbsp;</a></td></tr>" +

                        "<tr>" +
                        "<td bgcolor=\"#ffffff\">" +
                        "&nbsp; &nbsp; by <em> " + books[i].getFirstName() +
                        " " + books[i].getSurname() + "</em></td></tr>");
        }

        out.println("</table></center></body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Catalog servlet adds books to the user's " +
               "shopping cart and prints the catalog.";

    }
}

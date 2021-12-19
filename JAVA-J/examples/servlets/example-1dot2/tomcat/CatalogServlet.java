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
public class CatalogServlet extends HttpServlet { 
      private BookDB bookDB;
   
    public void init() throws ServletException {
        bookDB =
            (BookDB)getServletContext().getAttribute("examples.bookstore.database");

        if (bookDB == null) {
        		bookDB = BookDB.instance();
                  getServletContext().setAttribute("examples.bookstore.database", bookDB);
        }		
    }

      public void destroy() {
            bookDB = null;
         }
      

    public void doGet (HttpServletRequest request,
                       HttpServletResponse response)
   throws ServletException, IOException
    {
        // Get the user's session and shopping cart
   HttpSession session = request.getSession(true);
   ShoppingCart cart = (ShoppingCart)session.getAttribute("examples.bookstore.cart");

        // If the user has no cart, create a new one
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("examples.bookstore.cart", cart);
        }

   // set content-type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

   // then write the data of the response
        out.println("<html>" +
                    "<head><title> Book Catalog </title></head>");
            
        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);
                                       
                                       
        //Information on the books is from the database through its front end

        // Additions to the shopping cart
        String bookId = request.getParameter("Add");
        if (bookId != null) {
            BookDetails book = bookDB.getBookDetails(bookId);
            cart.add(bookId, book);
            out.println("<p><h3>" +
                        "<font color=\"#ff0000\">" +
                        "You just added <i>" + book.getTitle() + "</i> "+
                        "to your shopping cart.</font></h3>");
        }

        //Give the option of checking cart or checking out if cart not empty
        if (cart.getNumberOfItems() > 0) {
            out.println("<p><strong><a href=\"" +
                        response.encodeURL("/bookstore/showcart") +
                        "\"> Check Shopping Cart</a>&nbsp;&nbsp;&nbsp;" +
                        "<a href=\"" +
                        response.encodeURL("/bookstore/cashier") +
                        "\"> Buy Your Books</a>" +
                        "</p></strong>");
        }

        // Always prompt the user to buy more -- get and show the catalog
        out.println("<br> &nbsp;" +
                    "<h3>Please choose from our selections:</h3>" +
                    "<center> <table>");
            
            Collection c = bookDB.getBooks();
            Iterator i = c.iterator();
            while (i.hasNext()) {
                  BookDetails book = (BookDetails)i.next();
                  bookId = book.getBookId();
            //Print out info on each book in its own two rows
            out.println("<tr>" +

                        "<td bgcolor=\"#ffffaa\">" +
                        "<a href=\"" +
                        response.encodeURL("/bookstore/bookdetails?bookId=" + bookId) +
                        "\"> <strong>" + book.getTitle() +
                        "&nbsp; </strong></a></td>" +

                        "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                        Currency.format(book.getPrice(), request.getLocale()) +
                        "&nbsp; </td>" +

                        "<td bgcolor=\"#ffffaa\" rowspan=2>" +
                        "<a href=\"" +
                        response.encodeURL("/bookstore/catalog?Add=" + bookId)
                        + "\"> &nbsp; Add to Cart &nbsp;</a></td></tr>" +

                        "<tr>" +
                        "<td bgcolor=\"#ffffff\">" +
                        "&nbsp; &nbsp; by <em> " + book.getFirstName() +
                        " " + book.getSurname() + "</em></td></tr>");
        }

        out.println("</table></center></body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Catalog servlet adds books to the user's " +
               "shopping cart and prints the catalog.";

    }
}

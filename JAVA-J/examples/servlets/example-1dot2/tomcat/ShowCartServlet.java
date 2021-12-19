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
 * An HTTP servlet that displays the contents of a customer's shopping
 * cart at Duke's Bookstore.  It responds to the GET and HEAD methods of
 * the HTTP protocol.  This servlet calls other servlets.
 */
public class ShowCartServlet extends HttpServlet { 

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

   // set content type header before accessing the Writer
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //Print out the response
        out.println("<html>" +
                    "<head><title>Your Shopping Cart</title></head>");

        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher(
                  "/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);

        /* Handle any pending deletes from the shopping cart and
           indicate the outcome as part of the response */
        String bookId =request.getParameter("Remove");
        if (bookId != null) {
            cart.remove(bookId);
                  
            	BookDetails book = bookDB.getBookDetails(bookId);
                            
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
                        " in your shopping cart" +
                        "</font><br>&nbsp;");

            // Return the Shopping Cart Nice and Pretty
            out.println("<table>" +
                        "<tr>" +
                        "<th align=left>Quantity</TH>" +
                        "<th align=left>Title</TH>" +
                        "<th align=left>Price</TH>" +
                        "</tr>");
            
            Iterator i = cart.getItems().iterator();
            while (i.hasNext()) {
                ShoppingCartItem item = (ShoppingCartItem) i.next();
                BookDetails bookDetails = (BookDetails) item.getItem();
                
                out.println("<tr>" +
                            "<td align=\"right\" bgcolor=\"#ffffff\">" +
                            item.getQuantity() +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\">" +
                            "<strong><a href=\"" + 
                                          response.encodeURL("/bookstore/bookdetails?bookId=" + bookDetails.getBookId()) +
                            "\">" + bookDetails.getTitle() + "</a></strong>" +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\" align=\"right\">" +
                            Currency.format(bookDetails.getPrice(), request.getLocale()) +
                            "</td>" +

                            "<td bgcolor=\"#ffffaa\">" +
                            "<strong>" +
                            "<a href=\"" + 
                                          response.encodeURL("/bookstore/showcart?Remove=" + bookDetails.getBookId()) +
                            "\">Remove Item</a></strong>" +
                            "</td></tr>");
            }

            // Print the total at the bottom of the table
            out.println("<tr><td colspan=\"5\" bgcolor=\"#ffffff\">" +
                        "<br></td></tr>" +

                        "<tr>" +
                        "<td colspan=\"2\" align=\"right\"" +
                        "bgcolor=\"#ffffff\">" +
                        "Total:</td>" +
                        "<td bgcolor=\"#ffffaa\" align=\"right\">" +
                        Currency.format(cart.getTotal(), request.getLocale()) + "</td>" +
                        "</td><td><br></td></tr></table>");

           
            // Where to go and what to do next
            out.println("<p> &nbsp; <p><strong><a href=\"" +
                        response.encodeURL("/bookstore/catalog") +
                        "\">Continue Shopping</a> &nbsp; &nbsp; &nbsp;" +
                        
                        "<a href=\"" +
                        response.encodeURL("/bookstore/cashier") +
                        "\">Check Out</a> &nbsp; &nbsp; &nbsp;" +
                        
                        "<a href=\"" + 
                        response.encodeURL("/bookstore/showcart?Clear=clear") +
                        "\">Clear Cart</a></strong>");
        } else {

            // Shopping cart is empty!
            out.println("<font size=\"+2\">" +
                        "There is nothing in your shopping cart.</font>" +
                        "<br> &nbsp; <br>" +
                        "<center><a href=\"" +
                        response.encodeURL("/bookstore/catalog") +
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

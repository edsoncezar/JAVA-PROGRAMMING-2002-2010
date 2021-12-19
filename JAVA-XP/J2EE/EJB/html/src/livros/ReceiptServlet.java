/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import cart.ShoppingCart;

/**
 * An HTTP servlet that responds to the POST method of the HTTP protocol.
 * It clears the shopping cart, thanks the user for the order,
 * and resets the page to the Bookstore's main page.
 */

public class ReceiptServlet extends HttpServlet { 

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException
    {
        // Get the user's session and shopping cart
        HttpSession session = request.getSession(true);
    		ResourceBundle messages = (ResourceBundle)session.getAttribute("messages");

        // Payment received -- invalidate the session
        session.invalidate();
        
        // set content type header before accessing the Writer
        response.setContentType("text/html");
            response.setBufferSize(8192);
            PrintWriter out = response.getWriter();
        
        // then write the response
        out.println("<html>" +
                    "<head><title>" + messages.getString("TitleReceipt") + "</title></head>" );

        // Get the dispatcher; it gets the banner to the user
        RequestDispatcher dispatcher =
               getServletContext().getRequestDispatcher("/banner");
                                       
            if (dispatcher != null)
               dispatcher.include(request, response);
               
        
      out.println("<h3>" + messages.getString("ThankYou") +
         request.getParameter("cardname") + ".");
      out.println("<p> &nbsp; <p><strong><a href=\"" +
         response.encodeURL(request.getContextPath()) +
         "/enter\">" + messages.getString("ContinueShopping") + "</a> &nbsp; &nbsp; &nbsp;" +
         "</body></html>");
        out.close();
    }

    public String getServletInfo() {
        return "The Receipt servlet clears the shopping cart, " +
               "thanks the user for the order, and resets the " +
               "page to the BookStore's main page.";
    }
}


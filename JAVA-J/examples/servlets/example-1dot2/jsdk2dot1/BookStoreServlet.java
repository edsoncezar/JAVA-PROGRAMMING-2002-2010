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
        // Get the dispatcher; it gets the main page to the user
        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher(
                "/bookstore/bookstore.html");

        if (dispatcher == null) {
System.out.println("There was no dispatcher");
            // No dispatcher means the html file could not be found.
            response.sendError(response.SC_NO_CONTENT);
        } else {
System.out.println("There is a dispatcher");
            // Get or start a new session for this user
            HttpSession session = request.getSession();
            // Send the user the bookstore's opening page
            dispatcher.forward(request, response);
        }
    }

    public String getServletInfo() {
        return "The BookStore servlet returns the main web page " +
               "for Duke's Bookstore.";
    }
}

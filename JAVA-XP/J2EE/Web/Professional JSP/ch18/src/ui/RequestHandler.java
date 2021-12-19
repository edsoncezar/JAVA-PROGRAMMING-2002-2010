
package ui;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

/**
*   Interface to be implemented by objects that can process requests
*/
public interface RequestHandler {
    
    /**
    *   Perform any processing requiring to support this request, and
    *   return the URL of the JSP view that should display the results
    *   of the request.
    *   @return the URL within the web application of the JSP view to which
    *   the controller should redirect the response
    */
    String handleRequest(PageContext pageContext, HttpServletRequest request) throws BrowseException;

}

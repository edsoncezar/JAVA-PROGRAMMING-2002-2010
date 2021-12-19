
package ui.requesthandlers;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import ui.*;

/**
*   Implementation of the RequestHandler interface to handle
*   table choice. The user will have submitted a form
*   selecting the index of the table the user would like to view
*   before being directed to this RequestHandler. We need to update
*   the user's session to record this selection, and forward to the
*   table view JSP of the user's current view mode,
*/
public class ChooseTable implements RequestHandler {

    public String handleRequest(PageContext pageContext, HttpServletRequest request) throws BrowseException {
        System.out.println(getClass() + ".handleRequest");
        String tableIndex = request.getParameter("tableIndex");
        if (tableIndex == null)
            // This shouldn't happen, but we'll check anyway
            throw new InvalidInputException("Please choose a table", "chooseTable.jsp");

        BrowseSession session = RequestController.FindSessionBean(pageContext, this);
        session.setTableIndex(Integer.parseInt(tableIndex));
        
        // Using a method here rather than hard coding the page name
        // means that the user can continue in his or her current view mode.
        return session.getTableViewJSP();
    }
}
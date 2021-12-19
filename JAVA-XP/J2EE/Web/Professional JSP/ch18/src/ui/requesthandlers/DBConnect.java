
package ui.requesthandlers;

import javax.servlet.http.*;
import javax.servlet.jsp.*;

import ui.*;

import datasources.*;
import datasources.db.*;

/**
*   Implementation of the RequestHandler interface to handle
*   connection to a database. The user will have submitted the login form
*   before being directed to this RequestHandler. We look at the user's
*   login details and database driver and URL and attempt to connect them
*   by creating a DatabaseDataSource.
*/
public class DBConnect implements RequestHandler {
    
    private static final String     TABLE_CHOICE_PAGE = "chooseTable.jsp";

    public String handleRequest(PageContext pageContext,HttpServletRequest request) throws BrowseException {
        System.out.println(getClass() + ".handleRequest");
        String url = request.getParameter("url");
        String driver = request.getParameter("driver");
        if (url == null || driver == null || url.equals("") || driver.equals("") )
            // Throw an exception that will land us on the system error page
            // with a link back to the login form to allow the user to try again
            throw new InvalidInputException("Both URL and driver must be supplied", RequestController.LOGIN_PAGE);
        
       // Some databases (like Access) allow null or empty authentication information
       // Others, like Oracle, won't. We'll leave it to the driver to throw an exception
       // if necessary.
       String login = request.getParameter("login");
       String password = request.getParameter("password");
       BrowseSession session = RequestController.FindSessionBean(pageContext, this);
       try {
            DataSource dbDataSource = new DatabaseDataSource(url, driver, login, password);
            session.setDataSource(dbDataSource);
        }
        catch (DataSourceException ex) {
            throw new BrowseException("Cannot connect to database: " + ex.getMessage());
        }
        
        // If we get to here, everything is fine, and the session state is ready
        // to underpin the table choice view JSP
        return TABLE_CHOICE_PAGE;
    }
}
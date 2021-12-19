
package ui.requesthandlers;

import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.swing.table.TableModel;

import datasources.*;
import ui.*;

public class RunQuery implements RequestHandler {

    public String handleRequest(PageContext pageContext, HttpServletRequest request) throws BrowseException {
        System.out.println(getClass() + ".handleRequest");
		String query = request.getParameter("query");
        if (query == null || "".equals(query))
            // We need to look at the value passed from the dropdown
            query = request.getParameter("oldquery");
        if (query == null || "".equals(query))
			throw new InvalidInputException("A query must be supplied", "chooseTable.jsp");
        System.out.println("RunQuery will execute query " + query);
        try {
           // Update the session bean appropriately
            BrowseSession session = RequestController.FindSessionBean(pageContext, this);
            session.getTableModelForSQL(query);         
            return session.getTableViewJSP();
        }
        catch (DataSourceException ex) {
            throw new BrowseException(ex.getMessage());
        }
    }
}

package ui.requesthandlers;

import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.swing.table.TableModel;

import datasources.*;
import ui.*;


public class UpdateTable implements RequestHandler {

    public String handleRequest(PageContext pageContext, HttpServletRequest request) throws BrowseException {
        System.out.println(getClass() + ".handleRequest");
        try {
            BrowseSession session = RequestController.FindSessionBean(pageContext, this);
            TableModel model = session.getTableModel();
            String row = request.getParameter("row");
            if (row == null)
                throw new InvalidInputException("Invalid or missing table index", "login.html");
            for (int col = 0; col < model.getColumnCount(); col++) {
                // Look for parameter value for this column
                String colval = request.getParameter("" + col);
                if (colval != null) {
                    System.out.println("Value of " + col + " is " + colval);
                    model.setValueAt(colval, Integer.parseInt(row), col);
                    // The following method will throw an exception if the last update failed
                    session.getDataSource().checkLastUpdate();
                }
            }
        }
        catch (DataSourceException ex) {
            throw new BrowseException(ex.getMessage());
        }

        return "/updateOK.jsp";
    }
}

package ui;

import java.io.Serializable;
import javax.servlet.http.*;
import javax.swing.AbstractListModel;
import javax.swing.table.TableModel;
import java.util.*;
import jspstyle.NameValueModel;

import datasources.*;
import datasources.db.DatabaseDataSource;

/**
*	JSP session bean to contain information about user
*	view preferences and models exposing user input history.
*	<br/>Implements HttpSessionBindingListener interface to
*	ensure that data source resources are freed
*   when the session terminates.
*   <br/>This bean allows the RequestHandler objects to communicate
*   with the JSP views in the system.
*/
public class BrowseSession implements Serializable, HttpSessionBindingListener {

    //---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
    // Current view page.
    private String viewPage = "tableView.jsp";
    
    // Current data source. 
    // This object can change data source during the life of the application
    private DataSource      dataSource;
    
    // Index of the current table the user is viewing
    // -1 if they haven't selected a table
    private int             currentTableIndex;
    
    // Current table model.
    private TableModel      tableModel;
    
    private String          lastQuery;
    
    // List of SQL queries the user has typed in
    // This will back a NameValueModel of query history
    private List            queries = new LinkedList();
    
    private NameValueModel  queryModel = new QueryNameValueModel();
  
    
    //---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
    /**
    *   @return the JSP view the user presently wants to use for viewing data
    */
    public String getTableViewJSP() {
        return viewPage;
    }

    /**
    *   Change the current view JSP
    */
    public void setTableViewJSP(String viewPage) {
        this.viewPage = viewPage;
    }
    
    /**
    *   Set the DataSource behind this session
    */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
     public DataSource getDataSource() throws DataSourceException, BrowseException {
        if (dataSource == null) {
            throw new BrowseException("No connection");
        }
        return dataSource;
    }
    
    /**
    *   Choose a table index in the DataSource
    */
    public void setTableIndex(int index) {
        currentTableIndex = index;
        // Throw away any table model based on a query:
        // the user wants to see a table from the data source
        tableModel = null;
    }
    
    /**
    *   Return the currently selected table index,
    *   or -1 if no table index has been selected
    */
    public int getTableIndex() {
        return currentTableIndex;
    }
    
    /**
    *   Return the name of the current table.
    *   If no table name is selected, the user must have
    *   typed in a query: return the query
    */
    public String getTableName() throws DataSourceException, BrowseException {
        if (currentTableIndex < 0)
            return lastQuery;
        return getDataSource().getName(currentTableIndex);
    }
    
    /**
    *   Return the current table model.
    */
    public TableModel getTableModel() throws DataSourceException, BrowseException {
        // If we hold a table model in this class, the user typed in a query
        // and isn't looking at one of the tables in the data source
        if (tableModel != null)
            return tableModel;
        return getDataSource().getTableModel(currentTableIndex);
    }
    
    /**
    *   Does the data source we are currently working with support SQL?
    */
    public boolean getDataSourceSupportsSQL() throws DataSourceException, BrowseException {
        return getDataSource() instanceof DatabaseDataSource;
    }
    
    /**
    *   If the current data source supports SQL queries, obtain and save a table model
    *   built from an SQL query
    */
    public TableModel getTableModelForSQL(String query) throws DataSourceException, BrowseException {
        if (!getDataSourceSupportsSQL())
            throw new BrowseException("The current data source does not support SQL queries");
        lastQuery = query;
        // Save this query in the history list if it's new
        if (!queries.contains(query))
            queries.add(query);
        // The user isn't looking at a table in the data source, so 
        // unset any table index value
        currentTableIndex = -1;
        tableModel = ((DatabaseDataSource) getDataSource()).getTableModelForSQL(query);
        return tableModel;
    }
    
    /**
    *   Return a NameValueModel exposing this session's query history.
    *   This model is implemented by an inner class.
    */
    public NameValueModel getQueryModel() {
        return queryModel;
    }
    
    public boolean isTableEditable() throws DataSourceException, BrowseException {
        return currentTableIndex >= 0 && getDataSource().isTableEditable(currentTableIndex);
    }


    //---------------------------------------------------------------------
    // Implemention of HttpSessionBindingListener
    //---------------------------------------------------------------------
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("OperatorSession object bound to a session");
    }

    /**
    *   Use this callback from the JSP engine (issued when this object
    *   is being removed from the session when the session ends)
    *   to ensure that the data source cleans up after itself
    */
    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            System.out.println("OperatorSession object unbound to a session: will cleanup data source");
            getDataSource().cleanup();
        }
        catch (Exception ex) {
            // It won't do anyone much good to throw this:
            // simply produce console output. In a real application,
            // this would go to a proper log
            ex.printStackTrace();
        }
    }
    
    //---------------------------------------------------------------------
    // Inner class to implement NameValueModel exposing
    // query history
    //---------------------------------------------------------------------
    private class QueryNameValueModel extends AbstractListModel implements NameValueModel {
        public int getSize() {
            return queries.size();
        }
        
        public Object getElementAt(int i) {
            return queries.get(i);
        }
        
        public String getName(int i) {
            return queries.get(i).toString();
        }
    }
    
}   // class BrowseSession
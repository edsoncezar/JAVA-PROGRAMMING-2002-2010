
package datasources.db;

import datasources.*;
import java.sql.*;
import java.util.*;

import javax.swing.AbstractListModel;
import javax.swing.table.TableModel;

/**
 * Implementation of DatabaseDataSource exposing a relational database.
 * Extends the Swing AbstractListModel for a useful partial implementation of
 * the Swing ListModel, which NameValueModel extends.
 * @author  johnsonr
 * @version 
 */
public class DatabaseDataSource extends AbstractListModel implements DataSource, ConnectionFactory {
    
    // Connection parameters
    private String  url;
    private String  driverName;
    private String  user;
    private String  passwd;
    
    // List of tables found in the database
    private List    tableList;
    
    // The object that will perform the actual data retrieval
    JDBCTableModel  model;

    //---------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------
    public DatabaseDataSource(String url, String driverName, String user, String passwd) throws DataSourceException {
        this.url = url;
        this.driverName = driverName;
        this.user = user;
        this.passwd = passwd;
        try {
            //load the driver
            Class.forName(driverName);
            // Test the connection
            Connection connection = getConnection();
            loadMetaData(connection);
            connection.close();
            model = new JDBCTableModel(this);
        }
        catch (ClassNotFoundException ex) {
            throw new DataSourceException("Driver class " + driverName + " cannot be loaded");
        }
        catch (SQLException ex) {
             throw new DataSourceException("Unable to connect to database: " + ex);
        }
     }  // constructor
    
    
    /** 
    *   Overridden method from Object to return some basic information about
    *   the current connection
    */
    public String toString() {
        return "relational database: driver is " + driverName;// + " url is " + url;
    }
  
    
    //---------------------------------------------------------------------
	// Methods from DataSource interface
	//---------------------------------------------------------------------
    public boolean isTableEditable(int i) throws DataSourceException {
        return ((JDBCTableModel) getTableModel(i)).isEditable();
    }
  
     public TableModel getTableModel(int i) throws DataSourceException {
        String sql = "SELECT * FROM " + getName(i);
       return getTableModelForSQL(sql);
    }
    
    
    public TableModel getTableModelForSQL(String sql) throws DataSourceException {
        try {
            model.executeSQL(sql);
            return model;
        }
        catch (SQLException ex) {
              throw new DataSourceException("getTableModelForSQL with SQL=[" + sql + "] threw SQLException :" + ex);
        }
    }   // getTableModelForSQL
    
    
     public void cleanup() throws DataSourceException {
       // The JDBCTableModel closes all resources,
       // So we don't need to do anything more
    }
    
    public void checkLastUpdate() throws DataSourceException {
        // Since our simple JDBCTableModel doesn't implement the setValueAt()
        // method, we always tell the user the update failed
        throw new DataSourceException("The JDBC table model does not support updates");
    }
    
    
    //---------------------------------------------------------------------
	// Methods from NameValueModel interface
    // This exposes the tables found by this class when it
    // opened a connection
	//---------------------------------------------------------------------
    /**
    *   Return the name of the ith table (indexed from 0)
    */
    public String getName(int i) {
        return tableList.get(i).toString();
    }
   
    /**
    *   Return the number of tables we found
    */
    public int getSize() {
        return tableList.size();
    }
    
    /**
    *   Return the index of the table:
    *   this will be a parameter to this class's getXXXX(int) methods
    */
    public Object getElementAt(int i) {
        return new Integer(i);
    }
    
   
    //---------------------------------------------------------------------
	// Methods from ConnectionFactory interface
	//---------------------------------------------------------------------
    /**
    *   The JDBCTableModel will use this
    */
    public Connection getConnection() throws SQLException {
        // If this application were guaranteed to run in a J2EE-compliant
        // JSP engine, this connection could be obtained from a connection
        // pool specified in the application's web.xml file and accessed
        // via JNDI
        return DriverManager.getConnection(url, user, passwd);
    }
    
    
    //---------------------------------------------------------------------
	// Implementation methods
	//---------------------------------------------------------------------
    /**
    *   Load and store information about the tables
    *   in the database. This will back the NameValueModel
    *   exposed by this class. This information need be
    *   retrieved only once during the life of this class.
    */
    private void loadMetaData(Connection connection) throws SQLException {
        DatabaseMetaData dbmd = connection.getMetaData();
        
        // JDBC exposes must meta data as ResultSets
        // Change the second parameter below to retrieve information
        // about a particular schema in the database
        ResultSet rs = dbmd.getTables(null, null, "%", null);
        
        tableList = new LinkedList();
        while (rs.next()) {
            /*
                Each table description row has the following columns: 
                    TABLE_CAT String => table catalog (may be null) 
                    TABLE_SCHEM String => table schema (may be null) 
                    TABLE_NAME String => table name 
                    TABLE_TYPE String => table type
                           Common types are "TABLE", "VIEW", "SYSTEM TABLE"
                    REMARKS String => explanatory comment on the table 
                We are only want columns 3 and 4 (table name and table type)
            */
            String tableName = rs.getString(3);
            String tableType = rs.getString(4);
            if (tableType.toUpperCase().equals("TABLE"))
                // NB: this check may not work for all databases
                // With Oracle, it successfully excludes views and the many
                // strange system tables that would otherwise appear
                tableList.add(tableName);
        }
        // Use the free sorting capability of the Java 2 Collections API
        // to sort the table list by name
        Collections.sort(tableList);
    }   // loadMetaData
   
}   // class DatabaseDataSource
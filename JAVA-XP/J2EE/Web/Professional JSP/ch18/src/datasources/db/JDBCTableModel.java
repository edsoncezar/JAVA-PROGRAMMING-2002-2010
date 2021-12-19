package datasources.db;

import java.util.*;
import java.sql.*;
import javax.swing.table.*;

/**
*   Class to present JDBC query results as Swing table models.
*   Based on code by Gary Watson.
*/
class JDBCTableModel extends AbstractTableModel {

    /** Names of the current columns */
    private List columnNames;
    
    /** Types of the current columns (contains Class objects) */
    private List columnTypes;
    
    /** Data in the current table. Each entry is a List of cell value objects. */
    private List rowList;
    
    /** Object used to obtain JDBC connections */
    private ConnectionFactory connectionFactory;

    /**
    *   Construct a new JDBCTableModel, using a DatabaseDataSource
    *   object to obtain connections to the database
    */
    public JDBCTableModel(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
    *   Update the table's structure based on this query
    */
    public void executeSQL(String query) throws SQLException {
       // Clear any data already in the table
       rowList = new LinkedList();
       columnTypes = new LinkedList();
       columnNames = new LinkedList();

       Connection connection = null;
       Statement statement = null;
        try {
			System.out.println("** About to execute: " + query);
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            updateFromResultSet(resultSet);
            resultSet.close();
        }
        // We don't need to catch SQL exceptions: let this method throw them
        // We do need to ensure that we clean up, however
       finally {
            try {
                 if (statement != null)
                    statement.close();
            }
            catch (SQLException ex) {        
                // Ignore this exception, but catch it so that we can
                // try to close the connection anyway
            }
            try {
                if (connection != null)
                    connection.close();
            }
            catch (SQLException ex) {
                throw new SQLException("JDBCTableModel threw SQLException in cleanup:" + ex);
            }
        }   // finally
    }   // executeSQL
      
    
    /**
    *   Is this table editable at all? For example, it might be a non-editable view
    *   or a query. This is a simple-minded implementation
    */
    public boolean isEditable() {
        return true;
    }

    /**
    *   This is backed by the ResultSetMetaData our query generated
    */
    public String getColumnName(int col) {
        String retVal;
        retVal = (String) columnNames.get(col);
        if(retVal == null) retVal = "";
        return retVal;
    }

    public Class getColumnClass(int col) {
        Class retVal;
        retVal = (Class) columnTypes.get(col);
        if(retVal == null) retVal = Object.class;
        return retVal;
    }

    public boolean isCellEditable(int row, int col) {
        // Should really check here whether the column is part of the primary key
        // If it is, it shouldn't be editable
        return true;
    }

    public int getColumnCount() {
        return columnNames.size();
    }

    public int getRowCount() {
        return rowList.size();
    }

    public Object getValueAt(int row, int col) {
        // Find the object for the correct row and look in
        // it for the value
        List rowData = (List) rowList.get(row);
        return rowData.get(col);
    }

    /**
    *   Note that Swing models index from 0, JDBC ResultSets from 1
    */
    public void setValueAt(Object value, int row, int col) {
        // Not implemented
        // This might be implemented by giving this class the ability to
        // execute an update, or by using a JDBC 2.0 updateable ResultSet
    }

    /**
    *   Set the table's contents based on this ResultSet
    */
    private void updateFromResultSet(ResultSet rs) throws SQLException {
        int curType;

        // We need the ResultSetMetaData to find out the number of
        // columns in this ResultSet and the column types and names
        ResultSetMetaData metaData = rs.getMetaData();
        int columns =  metaData.getColumnCount();

        for(int col = 0 ; col < columns; col++) {
            columnNames.add(metaData.getColumnLabel(col + 1));
            try {
                curType = metaData.getColumnType(col + 1);
            }
            catch (SQLException e) {
                // This will go to the default case in the switch below
                curType = -1;
            }

            switch(curType) {
                case Types.CHAR:
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                    columnTypes.add(String.class);
                    break;

                case Types.TINYINT:
                case Types.SMALLINT:
                case Types.INTEGER:
                    columnTypes.add(Integer.class);
                    break;

                case Types.BIGINT:
                    columnTypes.add(Long.class);
                    break;

                case Types.FLOAT:
                case Types.DOUBLE:
                    columnTypes.add(Double.class);
                    break;

                case Types.DATE:
                    columnTypes.add(java.sql.Date.class);
                    break;

                default:
                    columnTypes.add(Object.class);
                    break;
            }   // switch
        }   // for each column

        // Load the actual data
        while (rs.next()) {
            // We hold each row of data in a list
            List rowData = new LinkedList();
            for (int col = 0; col < columns; col++) {
                // Remember that ResultSet columns are indexed from 1!
                rowData.add(rs.getObject(col + 1));
            }
            rowList.add(rowData);
        }
        
        // Remember to let any listeners know the table has changed.
        // This table may be used to support Swing clients as well as
        // web applications
        fireTableChanged(null);
    }   // updateFromResultSet
    
}   // class JDBCTableModel



package datasources;

import javax.swing.table.TableModel;

import jspstyle.NameValueModel;

/**
*   Interface to be implemented by classes providing access to data
*   that exists in separate tables. This information is exposed by
*   the implementing class via a NameValueModel of available tables,
*   and a method to retrieve a table model for each table. 
*   The most obvious implementation would
*   expose a relational database. A JNDI directory structure might be another
*   example.
*/
public interface DataSource extends NameValueModel {
    
    /**
    *   Return the table model for this table. The user can find out
    *   how many tables are available throw the NameValueModel
    *   getSize() method.
    */
    TableModel getTableModel(int i) throws DataSourceException;


    /**
    *   Is this table editable?
    */
    boolean isTableEditable(int i) throws DataSourceException;
    
    /**
    *   Ensure that all resources associated with the data source
    *   are freed
    */
    void cleanup() throws DataSourceException;
    
    /**
    *   Was the last update successful? If not, throw the exception it
    *   threw. This method is necessary because the TableModel setValueAt()
    *   method, which users will use to update data,
    *   may not be overridden throw a checked exception.
    */
    public void checkLastUpdate() throws DataSourceException;
    
    // Additional methods could support hierarchical retrieval

}   // interface DataSource

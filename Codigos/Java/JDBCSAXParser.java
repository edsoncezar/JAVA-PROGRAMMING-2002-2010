// JDBCSAXParser.java
package dbxml.sax;

import java.io.IOException;
import java.sql.*;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributeListImpl;

/**
 * SAX parser that uses a JDBC data source a input instead of an XML file
 * of byte stream.
 *
 * This is a proof-of-concept implemention and does not address all
 * the issues. Many improvements to this parser are possible.
 * This parser treats a table in a database as a virtual XML
 * document.
 *
 * @author Ramnivas Laddad
 */
public class JDBCSAXParser extends ParserBase {
    /**
     * When generating any SAX startElement() event, we need to send
     * an attribute list. Because the attribute list is always empty
     * we reuse this "stock" class member.
     */
    private static final AttributeList _stockEmptyAttributeList 
        = new AttributeListImpl();

    //-----------------------------------------------------------------------
    // Methods from Parser interface
    //-----------------------------------------------------------------------
    /**
     * Implement the method from base interface.
     * If the argument input source is of type other than JDBCInputSource,
     * it throws an SAXException as this parser cannot deal with it
     *
     * @param source an input source (must be of type JDBCInputSource)
     * @exception SAXException if an error occurs or the argument is not
     *                         of type JDBCInputSource
     * @exception IOException if an error occurs
     */
    public void parse (InputSource source) throws SAXException, IOException {
        if (! (source instanceof JDBCInputSource)) {
            throw new SAXException("JDBCSAXParser can work only with source "
                                   + "of JDBCInputSource type");
        }
        parse((JDBCInputSource)source);
    }
    
    /**
     * Implement the method from base interface.
     * Always thows an SAXException as the information passed is not sufficient
     * to carry out parsing.
     *
     * @param systemId unused
     * @exception SAXException thrown always
     * @exception IOException never thrown
     */
    public  void parse (String systemId) throws SAXException, IOException {
        throw new SAXException("JDBCSAXParser needs more information to "
                               + "connect to database");
    }
    
    //-----------------------------------------------------------------------
    // Additional methods 
    //-----------------------------------------------------------------------
    /**
     * Parse the given JDBC source to generate SAX events.
     * Obtains a result set by executing a query returned by
     * getSelectorSQLStatement and then parses that result set.
     *
     * @param source a input source describing database table to be parsed
     * @exception SAXException if an error occurs
     * @exception IOException if an error occurs
     */
    public void parse(JDBCInputSource source) 
        throws SAXException, IOException {
        try {
            Connection connection = source.getConnection();
            if (connection == null) {
                throw new SAXException("Could not establish connection with "
                                       + "database");
            }
            
            String sqlQuery = getSelectorSQLStatement(source.getTableName());
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            
            ResultSet rs = pstmt.executeQuery();
            parse(rs, source.getTableName());
            rs.close();
            
            connection.close();
        } catch (SQLException ex) {
            throw new SAXException(ex);
        }
    }
    
    /**
     * Parse the given JDBC result set object to generate SAX events.
     *
     * @param rs result set object to be parsed
     * @param tableName the name of table name being parsed
     * @exception SAXException if an parsing error occurs
     * @exception SQLException if an SQL error occurs
     * @exception IOException if an i/o error occurs
     */
    public void parse(ResultSet rs, String tableName) 
        throws SAXException, SQLException, IOException {
        if (_documentHandler == null) {
            return; // nobody is intersted in me, no need to sweat!
        }
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int numCols = rsmd.getColumnCount();
        
        String tableMarker = getTableMarker(tableName);
        String rowMarker = getRowMarker();
        
        _documentHandler.startDocument();
        _documentHandler.startElement(tableMarker, _stockEmptyAttributeList);
        while(rs.next()) {
            _documentHandler.startElement(rowMarker, 
                                          _stockEmptyAttributeList);
            for (int i = 1; i <= numCols; i++) {
                generateSAXEventForColumn(rsmd, rs, i);
            }
            _documentHandler.endElement(rowMarker);
        }
        _documentHandler.endElement(tableMarker);
        _documentHandler.endDocument();
    }
    
    /**
     * A convenience method that creates a JDBCInputSource object from
     * its argument and parses it
     *
     * @param connectionURL a JDBC URL for the database
     * @param userName user name to connect to the database
     * @param passwd password to connect to the database
     * @param tableName the name of table name being parsed
     * @exception SAXException if an parsing error occurs
     * @exception IOException if an i/o error occurs
     */
    public void parse(String connectionURL, String userName, String passwd,
                      String tableName) throws SAXException, IOException {
        parse(new JDBCInputSource(connectionURL, userName, passwd, tableName));
    }
    
    //-----------------------------------------------------------------------
    // Protected methods that derived classes could override to 
    // customize the parsing
    //-----------------------------------------------------------------------
    /**
     * Generate SAX event when visting a column.
     * Fires startElement event followed by a 
     * characters event followed by endElement
     * event. No events are fired for a null data.
     * 

     * This method may be overriden to customize event generation for
     * columns. For example, if one wishes to use special attribute,
     * instead of no events, for indicating null column, then this is the
     * method to override. Also for handling binary data or using desired
     * format for special types such as date and currency, one may override
     * this method.
     *
     * @param rsmd meta data for the result set
     * @param rs the result set
     * @param columnIndex index of column being visited (1 for first column)
     * @exception SAXException if an parsing error occurs
     * @exception SQLException if an SQL error occurs
     */
    protected void generateSAXEventForColumn(ResultSetMetaData rsmd,
                                             ResultSet rs,
                                             int columnIndex) 
        throws SAXException, SQLException {

        String columnValue = rs.getString(columnIndex);

        if (columnValue == null) {
            return;
        }

        String columnMarker 
            = getColumnMarker(rsmd.getColumnLabel(columnIndex));
        char[] columnValueChars = columnValue.toCharArray();

        _documentHandler.startElement(columnMarker, 
                                      _stockEmptyAttributeList);
        _documentHandler.characters(columnValueChars, 
                                    0, columnValueChars.length);
        _documentHandler.endElement(columnMarker);
    }
    
    /**
     * Get the marker for indicating the start and end of the document.
     * By default, it is same as the name of the table. Override this
     * to use custom marker.
     *
     * @param tableName the name of table name being parsed
     * @return the marker desired
     */
    protected String getTableMarker(String tableName) {
        return tableName;
    }

    /**
     * Get the marker for indicating the start and end of a row.
     * By default it is "row". Override this to use custome marker.
     *
     * @return the marker desired
     */
    protected String getRowMarker() {
        return "row";
    }

    /**
     * Get the marker for indicating the start and end of a column.
     * By default it is same as the name of column. 
     * Override this to use custome marker.
     *
     * @param columnName a value of type 'String'
     * @return a value of type 'String'
     */
    protected String getColumnMarker(String columnName) {
        return columnName;
    }

    /**
     * Get the select query that will be used to obtain the result
     * set for parsing. By default it is "select * from ".
     * Override this to allow database-level filtering.
     *
     * @param tableName the name of table name being parsed
     * @return query string
     */
    protected String getSelectorSQLStatement(String tableName) {
        return "select * from " + tableName;    
    }
}
    
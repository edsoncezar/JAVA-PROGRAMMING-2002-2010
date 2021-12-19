/*
 * This sample applet runs a simple query from the database
 */

// Import the JDBC classes
import java.sql.*;

// Import the java classes used in applets
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class JDBCApplet extends JApplet
{
  // Main container
  Container content;

  // Output window
  JTextArea output;

  // The Oracle driver to load
  static final String driver_class = "oracle.jdbc.driver.OracleDriver";

  // The connect string 
  static final String connect_string = "jdbc:oracle:thin:www/www@sandcastle:1521:COSC";

  // The query we will execute
  // signin(firstname, surname, email)
  static final String query = "select * from cale.signin";

  // The connection to the database
  Connection conn;

  // Create the User Interface
  public void init ()
  {
    content = this.getContentPane();
    content.setLayout(new BorderLayout ());
    output = new JTextArea (10, 60);
    content.add ("Center", output);
    try {
      go();
    } catch (Exception e) {
      output.append("ERROR: " + e.getMessage() + "\n");
    }
    content.repaint();
  }

  public void go () throws java.sql.SQLException
  {

    // See if we need to open the connection to the database
    if (conn == null)
    {
      // Load the JDBC driver
      output.append ("Loading JDBC driver " + driver_class + "\n");
      try {
        Class.forName (driver_class);
      } catch (java.lang.ClassNotFoundException e) {
        output.append ("ERROR: caught ClassNotFoundException\n"); 
      }

      // Connect to the databse
      output.append ("Connecting to " + connect_string + "\n");
      conn = DriverManager.getConnection (connect_string);
      output.append ("Connected\n");
    }


    // Create a statement
    Statement stmt = conn.createStatement ();

    // Execute the query
    output.append ("Executing query " + query + "\n");
    ResultSet rset = stmt.executeQuery (query);


    // Dump the result
    output.append ("Printing results.\n");
    while (rset.next ()) {
      output.append (rset.getString (1) + " " + rset.getString(2) + "\t" + rset.getString(3) + "\n");
    }

    // We're done
    output.append ("done.\n");
  }
}

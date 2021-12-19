<%@page import="java.sql.*,java.text.*"%>
<html>
  <head>
    <title>Widget Industries</title>
    <link rel="stylesheet" media="all" title="Default Styles" href="widget.css">
  </head>
  <body>

  <%
    // The following lines are for using Access databases, with DSN of
    // WIDGET and FWIDGET
   
    String cs = "jdbc:odbc:WIDGET";
    String cs2 = "jdbc:odbc:FWIDGET";
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection(cs, "admin", "");
      
    // The following lines are for Oracle
    /*
    String cs = "jdbc:oracle:thin:@db.widget.com:1521:WIDGET";
    String cs2 = "jdbc:oracle:thin:@foreign.widget.com:1521:FWIDGET";
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Connection con = DriverManager.getConnection(cs, "wteam", "wteam");
    */

    Statement stmt = con.createStatement();

    // Note that Access SQL doesn't support  TO_CHAR - to reduce the amount of
    // code to be changed, we use the following:
    
    ResultSet rs = stmt.executeQuery("select WIDGET_ID, NAME, WIDGET_DESC, " +
                                     "(DATE_AVAILABLE) as STR_DATE_AVAILABLE " +
                                     "from WIDGETS");

    // Oracle, on the other hand, is happy with TO_CHAR:
    /*
    ResultSet rs = stmt.executeQuery("select WIDGET_ID, NAME, WIDGET_DESC, "+ 
                                     "TO_CHAR(DATE_AVAILABLE, 'MM/DD/YY') "+
                                     "STR_DATE_AVAILABLE from BEN_WIDGETS");
    */

  %>
  <p align="center" class="title">
  Here are the widgets we produce and distribute:
  </p>
  
  <p align="center">
  <table class="catalog_table" cellspacing="1" cellpadding="4">
    <tr class="catalog_header">
      <th>
        Widget ID
      </th>
      <th>
        Widget Name
      </th>
      <th>
        Widget Description
      </th>
      <th>
        Date Available
      </th>
    </tr>
  <% while (rs.next()) { %>
    <tr class="catalog_items">
      <td>
        D<%= rs.getString("WIDGET_ID") %>
      </td>
      <td>
        <%= rs.getString("NAME") %>
      </td>
      <td>
        <%= rs.getString("WIDGET_DESC") %>
      </td>
      <td>
        <%= rs.getString("STR_DATE_AVAILABLE") %>
      </td>
    </tr>
  <% }
    stmt.close();
    con.close();

    // The following line is for a second Access database
    con = DriverManager.getConnection(cs2, "admin", "");

    // For Oracle, use this:
    /*
    con = DriverManager.getConnection(cs2, "fwteam", "fwteam");
    */

    stmt = con.createStatement();

    // Again, Access SQL doesn't support TO_CHAR
    
    rs = stmt.executeQuery("select F_W_ID, F_WIDGET, F_WIDGET_DESC, "+ 
                           "(DATE_AVAIL) as STR_DATE_AVAIL " +
                           "from FOREIGN_WIDGETS");
        
    // Whereas for Oracle we use this:
    /*
    rs = stmt.executeQuery("select F_W_ID, F_WIDGET, F_WIDGET_DESC, " +
                                  "TO_CHAR(DATE_AVAIL, 'MM/DD/YY') " +
                                  "STR_DATE_AVAIL from FOREIGN_WIDGETS");
    */

  %>
  <% while (rs.next()) { %>
    <tr class="catalog_items">
      <td>
        F<%= rs.getString("F_W_ID") %>
      </td>
      <td>
        <%= rs.getString("F_WIDGET") %>
      </td>
      <td>
        <%= rs.getString("F_WIDGET_DESC") %>
      </td>
      <td>
        <%= rs.getString("STR_DATE_AVAIL") %>
      </td>
    </tr>
  <% } 
   stmt.close();
   con.close();
   %>

  </table>
  </p>
  
  </body>
</html>

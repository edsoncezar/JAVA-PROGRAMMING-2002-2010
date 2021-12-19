<%@ page import="java.util.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.sql.*" %>

<%

String url = "jdbc:odbc:countrydb";
String user = "";
String password = "";

try {

  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
  Connection con = DriverManager.getConnection(url, user, password);
  Statement stmt = con.createStatement();
  ResultSet rs = stmt.executeQuery("Select * from countries");
  
  while (rs.next()) {
    String result = rs.getString("name");
    %>
    <%= result %><BR>
    <%
    
  }
  rs.close();


} // end the try

catch (Exception e) {
  System.out.println(e);
} // end the catch


%>

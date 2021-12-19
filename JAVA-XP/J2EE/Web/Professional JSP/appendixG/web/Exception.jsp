<%@ page errorPage="ErrorPage.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.sql.*" %>

<%

String url = "jdbc:odbc:countryd";
String user = "";
String password = "";

  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
  Connection con = DriverManager.getConnection(url, user, password);
  Statement stmt = con.createStatement();
  ResultSet rs =  stmt.executeQuery("Select * from countries");
  
  while (rs.next()) {
    String result = rs.getString("name");
    %>
    <%= result %><BR>
    <%
    
  }
  rs.close();

%>

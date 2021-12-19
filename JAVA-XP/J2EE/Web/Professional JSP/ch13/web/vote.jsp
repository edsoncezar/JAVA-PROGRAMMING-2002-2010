<%-- vote.jsp 
     Displays a balot listing all of the candidates.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.sql.*" errorPage="error.jsp" %>
<html>
<head>
  <title>Pick a presidential candidate!</title>
</head>

<body>

<h1>Please pick a candidate:</h1>

<form method="post" action="savevote.jsp">
<table border="1" cellpadding="2" cellspacing="0">
<%  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection("jdbc:odbc:presidential_election", "", "");

    // Class.forName("org.gjt.mm.mysql.Driver");
    // Connection con = DriverManager.getConnection("jdbc:mysql:///presidential_election", "admin", "admin");

    Statement stmt = con.createStatement();
    String queryStr = "SELECT CANDIDATENUMBER, FIRSTNAME, LASTNAME, POLITICALPARTY FROM CANDIDATE";

    ResultSet rs = stmt.executeQuery(queryStr);
    while(rs.next()) {
%>

<tr>
<td><input type="radio" name="candidate"
           value="<%= rs.getString("CANDIDATENUMBER") %>"></td>
<td><%= rs.getString("FIRSTNAME")%>
    <%= rs.getString("LASTNAME")%></td>
<td><%= rs.getString("POLITICALPARTY") %></td>

</tr>
<%  } // end while()

    // clean up.
    if (rs!=null) rs.close();
    if (stmt!=null) stmt.close();
    if (con!=null) con.close();
%>
</table>
<p><input type="submit" value="Record your vote!"></p>
</form>
</body>
</html>

<%--  results.jsp 
      Displays current election results in real-time.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.sql.*, java.util.*" errorPage="error.jsp" %>
<html>
<head>
  <title>Current Election Results:</title>
</head>

<body>
<h2>Here are the latest voting results... </h2>
<%  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection
                             ("jdbc:odbc:presidential_election", "", "");

    // Class.forName("org.gjt.mm.mysql.Driver");
    // Connection con = DriverManager.getConnection
    //            ("jdbc:mysql:///presidential_election", "admin", "admin");

    Statement stmt = con.createStatement();
    String queryStr = "SELECT Candidate.FIRSTNAME, Candidate.LASTNAME, " +
            "Count(Votes.VOTENUMBER) AS CountOfVoteID FROM Candidate " +
            "LEFT JOIN Votes ON Candidate.CANDIDATENUMBER = " +
            "Votes.CANDIDATENUMBER GROUP BY Candidate.FIRSTNAME, " +
            "Candidate.LASTNAME"; 
    ResultSet rs = stmt.executeQuery(queryStr);
%>
<table border="0" cellspacing="0" cellpadding="2">
<tr bgcolor="lightgrey">
<th align="left">Candidate</th>
<th align="right">Total Votes</th>
</tr>

<%  while (rs.next()) {
      String firstname = rs.getString(1); 
      String lastname = rs.getString(2);
      int votes = rs.getInt(3); 
%>

<tr>
<td><%= firstname %> <%= lastname %></td>
<td align="right"><%= votes %></td>
</tr>   
<%
    }  // end while()

    // clean up.
    if (rs!=null) rs.close();
    if (stmt!=null) stmt.close();
    if (con!=null) con.close();
%>
</table>
</body>
</html>


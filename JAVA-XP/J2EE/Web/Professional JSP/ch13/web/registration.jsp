<%-- registration.jsp 
     A dynamically generated form used for entering information 
     about potential voters.
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.sql.*" errorPage="error.jsp" %>
<html>
<head>
  <title>Voter Registration Page</title>
</head>
<body>
<h1>Voter Registration Page:</h1>
<h5>Please only register to vote once.</h5>
<hr align="left" width="50%">
<form action="saveregistration.jsp" method="post">
<p>
<table>
<tr>
<td>Social Security Number:</td>
<td><input type="text" name="ssn" maxlength="9" size="9"></td>
</tr>
<tr>
<td>FirstName:</td>
<td><input type="text" name="firstname" maxlength="32" size="32"></td>
</tr>
<tr>
<td>LastName:</td>
<td><input type="text" name="lastname" maxlength="32" size="32"></td>
</tr>
<tr>
<td>County:</td>
<td>
<select name="county">

<%  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection("jdbc:odbc:presidential_election", "", "");
	
    // Class.forName("org.gjt.mm.mysql.Driver");
    // Connection con = 
    // DriverManager.getConnection("jdbc:mysql:///presidential_election", "admin", "admin");

    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT countynumber, countyname, state FROM COUNTY");
    while(rs.next()) {
%>

<option value="<%= rs.getString("countynumber") %>">
  <%= rs.getString("countyname") %>,
  <%= rs.getString("state") %>
</option>

<%  } // end while()
	
    // clean up.
    if (rs!=null) rs.close(); 
    if (stmt!=null) stmt.close();
    if (con!=null) con.close();
%>

</select>
</td>
</tr>
</table>
</p>
<input type="submit" value="Submit Voter Registration">
</form>
</body>
</html>

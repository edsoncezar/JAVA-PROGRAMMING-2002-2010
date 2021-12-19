<%-- 	saveregistration.jsp 
 		Inserts a user record into the database.
  --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.sql.*" errorPage="error.jsp" %>
<html>
<head>
  <title>Voter Registration Complete!!</title>
</head>

<body>


<%
    // Fetch the form data
    String ssn = request.getParameter("ssn");
    String firstName = request.getParameter("firstname");
    String lastName = request.getParameter("lastname");
    String county = request.getParameter("county");

    // save info into the user's session
    session.setAttribute("ssn",  ssn);
    session.setAttribute("firstname",  firstName);
    session.setAttribute("lastname",  lastName);
    session.setAttribute("county",  county);

    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection("jdbc:odbc:presidential_election", "", "");

    // Class.forName("org.gjt.mm.mysql.Driver");
    // Connection con = 
    // DriverManager.getConnection("jdbc:mysql:///presidential_election", "admin", "admin");

    String template = "INSERT INTO VOTERREGISTRATION (SSN, FIRSTNAME, LASTNAME, COUNTYNUMBER) VALUES( ?, ?, ?, ? )";
    PreparedStatement pstmt = con.prepareStatement(template);
    pstmt.setInt(1, Integer.parseInt(ssn));
    pstmt.setString(2, firstName);
    pstmt.setString(3, lastName);
    pstmt.setInt(4, Integer.parseInt(county));
    pstmt.executeUpdate();

    if (pstmt!=null) pstmt.close();
    if (con!=null) con.close();
%>

<h2>Hello <%= firstName %>, </h2><br>
<h3>thank you for registering to vote.</h3>
<hr align="left" width="50%">
<h3>Click <a href="vote.jsp">here</a> to vote.</h3>

</body>
</html>

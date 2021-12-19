<%-- savevote.jsp 
     records the vote into a database and invalidates the current user's session.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="java.sql.*" errorPage="error.jsp" %>
<html>
<head>
  <title>Thank you for voting.</title>
</head>

<body>

<h1>Thank you for taking the time to vote!</h1>
<hr align="left" width="50%">
<h4>Click <a href="results.jsp">here</a> to see current results.</h4>
<%  // Fetch the form data
    int candidate = Integer.parseInt((String)request.getParameter("candidate"));
    int county = Integer.parseInt((String)session.getAttribute("county"));
    int ssn = Integer.parseInt((String)session.getAttribute("ssn"));

    // connect to database.

    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    Connection con = DriverManager.getConnection("jdbc:odbc:presidential_election", "", "");

    // Class.forName("org.gjt.mm.mysql.Driver");
    // Connection con = 
    // DriverManager.getConnection("jdbc:mysql:///presidential_election", "admin", "admin");

    // record the vote (just the vote and county information)
    String template = "INSERT INTO VOTES(CANDIDATENUMBER, COUNTYNUMBER) VALUES( ?, ?)";
    PreparedStatement pstmt = con.prepareStatement(template);
    pstmt.setInt(1, candidate);
    pstmt.setInt(2, county);
    pstmt.executeUpdate();

    // invalidate the session.
    session.invalidate();

    // clean up
    if (pstmt!=null) pstmt.close();
    if (con!=null) con.close();
%>

</body>
</html>

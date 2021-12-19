<!-- JSP Directives -->
<%@ page 
		import="java.sql.*"
		errorPage="myError.jsp?from=customers.jsp"
%>

<html>
<head>
	<title>Insurance Quoting System</title>
</head>

<body>

<basefont face="Arial">

<!-- JSP Declarations -->
<%! ResultSet rs = null;%>

<!-- JSP Scriptlet -->	 
<%
	Class.forName("org.gjt.mm.mysql.Driver");
	Connection db = DriverManager.getConnection(
		"jdbc:mysql://localhost:3306/quoting");
	Statement s = db.createStatement();
	rs = s.executeQuery("select * from customer");
%>

<%@ include file="myHeader.html" %>

<!-- Template text -->

<br><br>
<table width="550" border="0" align="center">
  <tr>
    <td>
      <p align="center"><b>Customers</b></p>
	  
      <table width="320" border="0" align="center">
		
<%
	while (rs.next()) {
%>

<!-- JSP Expressions used within template text -->
	<tr>
		<td width="20"><%= rs.getInt(1) %></td>
		<td width="70"><%= rs.getString(2) %></td>
		<td width="70"><%= rs.getString(3) %></td>
		<td width="40">
			<a href="customerDetail.jsp?id=<%= rs.getString(1) %>&action=edit">
				edit
			</a>
		</td>
		<td width="40">
			<a href="custMaint.jsp?id=<%= rs.getString(1) %>&action=delete">
				delete
			</a>
		</td>
		<td width="80" nowrap>
			<a href="custMaint.jsp?id=<%= rs.getString(1) %>&action=newQuote">
				new quote
			</a>
		</td>
	</tr>
	
<%
	}
%>
      </table>
    </td>
  </tr>
  <tr>
	<td>
		<p>&nbsp;</p>
      	<p align="center"><a href="custMaint.jsp?action=add">New Customer</a></p>
	</td>
  </tr>
</table>

<br><br>
<%@ include file="myFooter.html" %>

</body>
</html>

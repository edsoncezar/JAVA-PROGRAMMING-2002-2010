<!-- JSP Directives -->
<%@ page 
		errorPage="myError.jsp?from=census.jsp"
%>

<html>
<head>
	<title>Insurance Quoting System</title>
</head>

<body bgcolor="#FFFF99">

<basefont face="Arial">

<%@ include file="/ch5/myHeader.html" %>

<form action="Main?action=submit" method="post">

<br><br>

<% 	String submitError = (String) session.getAttribute("submitError");
	if (submitError != null && submitError.equals("y")) {
%>
<center>
	<font color="#ff0000">Error recording survey data, please try again.</font>
</center>
<br><br>
<% 	}
%>


<center><b>Enter personal information:</b></center>
<br><br>
<table cellspacing="2" cellpadding="2" border="0" align="center">
<tr>
    <td align="right">First Name:</td>
    <td><input type="Text" name="fname" size="10"></td>
</tr>
<tr>
    <td align="right">Last Name:</td>
    <td><input type="Text" name="lname" size="10"></td>
</tr>
<tr>
    <td align="right">Age:</td>
    <td><input type="Text" name="age" size="2"></td>
</tr>
<tr>
    <td align="right">Sex:</td>
    <td>
		<input type="radio" name="sex" value="M" checked>Male</input>
		<input type="radio" name="sex" value="F">Female</input>
	</td>
</tr>
<tr>
    <td align="right">Married:</td>
    <td><input type="Text" name="married" size="2"></td>
</tr>
<tr>
    <td align="right">Children:</td>
    <td><input type="Text" name="children" size="2"></td>
</tr>
<tr>
    <td align="right">Smoker:</td>
    <td><input type="Text" name="smoker" size="2"></td>
</tr>
<tr>
    <td colspan="2" align="center"><input type="Submit" value="Submit"></td>
</tr>
</table>

<br><br>

</form>
<%@ include file="/ch5/myFooter.html" %>

</body>
</html>

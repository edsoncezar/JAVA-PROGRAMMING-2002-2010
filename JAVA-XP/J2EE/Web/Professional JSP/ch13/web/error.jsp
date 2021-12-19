<%-- 	error.jsp 
 		Displays a brief error message.
  --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page isErrorPage="true" %>
<html>
<head>
	<title>Error Page:</title>
</head>

<body>

<h1>Something has gone wrong.</h1>
<%= exception %>


</body>
</html>

<%@page import="java.util.*"%>
<html>
<head>
<title>
jsp1
</title>
</head>
<body bgcolor="#ffffff">
<h1>JSP Dinamico</h1>
<%
Date d=new Date();
out.println(d);
int x=0;
x++;
out.println(x);
%>
<%=x%>
</body>
</html>

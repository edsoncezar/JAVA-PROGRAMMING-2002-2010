<html>
<head>
<title>
Session3.jsp
</title>
</head>
<body>
<%
String word = (String) session.getValue("theSessionWord");
%>
The Magic Word was <br><%= word %>
</body>
</html>

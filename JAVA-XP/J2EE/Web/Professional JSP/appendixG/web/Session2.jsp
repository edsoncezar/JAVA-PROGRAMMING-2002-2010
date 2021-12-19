<html>
<head>
<title>
Session2.jsp
</title>
</head>
<body>
<%! String word=""; %>
<% 
word = request.getParameter("theWord");
session.putValue("theSessionWord", word); 
%>

Storing Word <i> <%= word %> </i> to the session object
<p>

</body>
</html>

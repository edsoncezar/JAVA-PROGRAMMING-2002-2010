<%@page import="calculo.*"%>
<html>
<head>
<title>
mostra
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Mostra Idade
</h1>
<%
Anos a=new Anos();
a.setIdade(request.getParameter("idade"));
%>
Idade: <%=a.getIdade() %><br>
Posi��o: <%=a.getPosicionamento()%> <br>
Dias Vividos: <%=a.getDias()%>

</body>
</html>

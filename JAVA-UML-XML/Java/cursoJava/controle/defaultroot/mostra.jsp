<%@page import="controle.*"%>
<html>
<head>
<title>
mostra
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Mostra Situacao
</h1>
<%
Avaliacao av=new Avaliacao();
av.setNota(request.getParameter("nota"));
%>
<h1>
Nota: <%=av.getNota() %><br>
Situacão Final: <%=av.getSituacao()%> <br>
</h1>
</body>
</html>

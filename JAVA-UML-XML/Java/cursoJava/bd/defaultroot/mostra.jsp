<html>
<head>
<title>
mostra
</title>
</head>
<body bgcolor="#ffffff">
<h1>
Informe o c�digo<br>
</h1>
<jsp:useBean id="id" scope="page" class="bd.Consulta"/>
<jsp:setProperty name="id" property="codigo" param="codigo"/>
C�digo: <jsp:getProperty name="id" property="codigo" /><br>
Assunto: <jsp:getProperty name="id" property="assunto" /><br>
Conte�do: <jsp:getProperty name="id" property="conteudo" /><br>
Prioridade: <jsp:getProperty name="id" property="prioridade" />

</body>
</html>

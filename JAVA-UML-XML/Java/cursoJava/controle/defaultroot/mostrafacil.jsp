<html>
<head>
<title>
mostrafacil
</title>
</head>
<body bgcolor="#ffffff">
<h1> Mostra F�cil a Situa��o</h1>
<jsp:useBean id="av" scope="page" class="controle.Avaliacao"/>
<jsp:setProperty name="av" property="nota" param="nota"/>
Nota: <jsp:getProperty name="av" property="nota" />
<br>
Situa��o: <jsp:getProperty name="av" property="situacao" />
<br>
</body>
</html>

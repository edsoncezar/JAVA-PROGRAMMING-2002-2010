<%@page contentType="text/html"%>
<%@taglib uri="mytaglib" prefix="mytag"%>
<html>
<head><title>Página JSP com custom tag</title></head>
<body>

<mytag:BasicTag includePage="true" includeBody="true" iterate="20">
    O item corrente é o de nr. <%=currentIter%> <br/>
</mytag:BasicTag>

</body>
</html>

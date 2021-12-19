<%@ page contentType="text/html"%>
<%@ page import="javax.xml.parsers.DocumentBuilderFactory,
		     javax.xml.parsers.DocumentBuilder,
		     org.w3c.dom.*"
%>
<%
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();
Document doc = db.parse("c:/xml/message.xml");
NodeList nl = doc.getElementsByTagName("message");
%>
<html>
<body>
<%= nl.item(0).getFirstChild().getNodeValue() %>
</body>
</html>
